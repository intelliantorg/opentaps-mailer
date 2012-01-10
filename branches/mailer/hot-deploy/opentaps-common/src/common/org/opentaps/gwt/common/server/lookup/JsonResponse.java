/*
 * Copyright (c) 2009 - 2009 Open Source Strategies, Inc.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the Honest Public License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Honest Public License for more details.
 *
 * You should have received a copy of the Honest Public License
 * along with this program; if not, write to Funambol,
 * 643 Bair Island Road, Suite 305 - Redwood City, CA 94063, USA
 */

package org.opentaps.gwt.common.server.lookup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.opentaps.common.event.AjaxEvents;
import org.opentaps.common.util.ConvertMapToString;
import org.opentaps.common.util.UtilCommon;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.gwt.common.client.lookup.Permissions;
import org.opentaps.gwt.common.client.lookup.UtilLookup;

/**
 * A response wrapper to convert GWT lookup results into a JSON response.
 */
public class JsonResponse {

    private static final String MODULE = JsonResponse.class.getName();

    private HttpServletResponse response;

    /**
     * Creates a new <code>JsonResponse</code> instance.
     * @param response a <code>HttpServletResponse</code> value
     */
    public JsonResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * Makes the appropriate response for a Suggest service, assumes the ID field is the first field defined in the service.
     * @param service an <code>EntityLookupService</code> value
     * @return a <code>String</code> value
     */
    public String makeSuggestResponse(EntityLookupAndSuggestService service) {
        return makeSuggestResponse(service.getFields().get(0), service);
    }

    /**
     * Makes the appropriate response for a Suggest service.
     * @param entityIdFieldName the field name corresponding to the ID
     * @param service an <code>EntityLookupService</code> value
     * @return a <code>String</code> value
     */
    public String makeSuggestResponse(String entityIdFieldName, EntityLookupAndSuggestService service) {
        List<? extends EntityInterface> results = service.getResults();
        if (results == null) {
            return makeErrorResponse(service.getLastException());
        }

        return makeSuggestResponse(results, entityIdFieldName, service.getResultTotalCount(), service.allowBlank(), service);
    }

    /**
     * Makes the appropriate response for a Lookup service.
     * @param entityIdFieldName the field name corresponding to the ID
     * @param service an <code>EntityLookupService</code> value
     * @param servletContext a <code>ServletContext</code> value
     * @return a <code>String</code> value
     */
    public String makeLookupResponse(String entityIdFieldName, EntityLookupService service, ServletContext servletContext) {
        List<? extends EntityInterface> results = service.getResults();
        if (results == null) {
            return makeErrorResponse(service.getLastException());
        }

        if (service.isExportToExcel()) {
            return makeExcelExport(results, service.getFieldsOrdered(), servletContext);
        } else {
            return makeResponse(results, entityIdFieldName, service);
        }
    }

    /**
     * Makes a JSON response from the result of an entity lookup.
     * @param entities the <code>List</code> of <code>GenericValue</code> found
     * @param entityIdFieldName the field name corresponding to the ID
     * @param service the <code>EntityLookupService</code>
     * @return JSON response string
     */
    public String makeResponse(List<? extends EntityInterface> entities, String entityIdFieldName, EntityLookupService service) {

        List<Map<String, ConvertMapToString>> calculatedFields = service.getCalculatedFields();
        JSONArray jsonArray = new JSONArray();

        // add global permissions, consumer widgets will need to discard this record after reading (not to display in a list)
        JSONObject jsonObject = new JSONObject();
        Permissions permissions = service.getGlobalPermissions();
        jsonObject.put(Permissions.CREATE_FIELD_NAME, permissions.canCreate());
        jsonObject.put(Permissions.UPDATE_FIELD_NAME, permissions.canUpdate());
        jsonObject.put(Permissions.DELETE_FIELD_NAME, permissions.canDelete());
        jsonArray.put(jsonObject.toString());

        if (entities != null) {
            for (EntityInterface e : entities) {
                jsonObject = new JSONObject();
                for (String f : service.getFields()) {
                    jsonSerialize(f, e.get(f), jsonObject);
                }
                // add calculated fields
                if (calculatedFields != null) {
                    for (Map<String, ConvertMapToString> converters : calculatedFields) {
                        for (String fieldName : converters.keySet()) {
                            ConvertMapToString conv = converters.get(fieldName);
                            jsonObject.put(fieldName, conv.convert(e.toMap()));
                        }
                    }
                }
                // add permissions
                permissions = service.getEffectivePermissions(e);
                jsonObject.put(Permissions.CREATE_FIELD_NAME, permissions.canCreate());
                jsonObject.put(Permissions.UPDATE_FIELD_NAME, permissions.canUpdate());
                jsonObject.put(Permissions.DELETE_FIELD_NAME, permissions.canDelete());

                jsonArray.put(jsonObject.toString());
            }
        }

        Map<String, Object> retval = new HashMap<String, Object>();
        retval.put(UtilLookup.JSON_ROOT, jsonArray);
        retval.put(UtilLookup.JSON_ID, entityIdFieldName);
        retval.put(UtilLookup.JSON_TOTAL, service.getResultTotalCount());

        return AjaxEvents.doJSONResponse(response, JSONObject.fromMap(retval));
    }

    /**
     * Makes a JSON response from the result of an entity suggest lookup.
     * @param entities the <code>List</code> of <code>GenericValue</code> found
     * @param entityIdFieldName the field name corresponding to the ID
     * @param resultTotalCount the total number of entities that matched the lookup, which may differ from the total actually returned because of pagination
     * @param insertBlank set to <code>true</code> to insert a blank record at the beginning of the response
     * @param format the suggest formatting class used to make the display string
     * @return JSON response string
     */
    public String makeSuggestResponse(List<? extends EntityInterface> entities, String entityIdFieldName, Integer resultTotalCount, boolean insertBlank, EntityLookupAndSuggestService format) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        // insert blank record to allow blank selection in the client UI
        if (insertBlank) {
            jsonObject = new JSONObject();
            jsonObject.put(UtilLookup.SUGGEST_ID, "");
            jsonObject.put(UtilLookup.SUGGEST_TEXT, "");
            jsonArray.put(jsonObject.toString());
        }
        if (entities != null) {
            for (EntityInterface e : entities) {
                jsonObject = new JSONObject();
                jsonObject.put(UtilLookup.SUGGEST_ID, e.get(entityIdFieldName));
                jsonObject.put(UtilLookup.SUGGEST_TEXT, format.makeSuggestDisplayedText(e));
                jsonArray.put(jsonObject.toString());
            }
        }

        Map<String, Object> retval = new HashMap<String, Object>();
        retval.put(UtilLookup.JSON_ROOT, jsonArray);
        retval.put(UtilLookup.JSON_ID, UtilLookup.SUGGEST_ID);
        retval.put(UtilLookup.JSON_TOTAL, resultTotalCount);

        return AjaxEvents.doJSONResponse(response, JSONObject.fromMap(retval));
    }

    /**
     * Exports a list of <code>GenericValue</code> in an Excel spreadsheet.
     * @param entities the <code>List</code> of <code>GenericValue</code> to export
     * @param fields the <code>List</code> of fields that are exported from the entities, in order
     * @param servletContext a <code>ServletContext</code> used to determine where the file will be generated
     * @return the response string
     */
    @SuppressWarnings("unchecked")
    public String makeExcelExport(List<? extends EntityInterface> entities, List<String> fields, ServletContext servletContext) {

        // create a map for column header labels
        Map<String, String> columnHeaderMap = new HashMap<String, String>();
        for (String key : fields) {
            columnHeaderMap.put(key, key);
        }

        String fileName = "data.xls";
        List excelDataList = new ArrayList();
        excelDataList.add(columnHeaderMap);
        for (EntityInterface e : entities) {
            excelDataList.add(e.toMap(fields));
        }

        String excelFilePath = getAbsoluteFilePath(fileName, servletContext);
        try {
            UtilCommon.saveToExcel(excelFilePath, "data", fields, excelDataList);
        } catch (IOException ioe) {
            return "error";
        }

        return downloadExcel(fileName, servletContext);
    }

    /**
     * Makes a standard JSON error response from just an error message.
     * @param e an <code>Exception</code> value
     * @return JSON response string
     */
    public String makeErrorResponse(Exception e) {

        String errorMessage = "error";
        if (e != null) {
            errorMessage = e.getMessage();
        }

        Map<String, Object> retval = new HashMap<String, Object>();
        retval.put(UtilLookup.JSON_SUCCESS, false);
        retval.put(UtilLookup.JSON_TOTAL, 1);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(UtilMisc.toMap(UtilLookup.JSON_ERROR_MESSAGE, errorMessage));
        retval.put(UtilLookup.JSON_ERROR_EXCEPTION, jsonArray);

        return AjaxEvents.doJSONResponse(response, JSONObject.fromMap(retval));
    }

    private String downloadExcel(String filename, ServletContext servletContext) {
        File file = null;
        ServletOutputStream out = null;
        FileInputStream fileToDownload = null;

        try {
            out = response.getOutputStream();

            file = new File(getAbsoluteFilePath(filename, servletContext));
            fileToDownload = new FileInputStream(file);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setContentLength(fileToDownload.available());

            int c;
            while ((c = fileToDownload.read()) != -1) {
                out.write(c);
            }

            out.flush();
        } catch (FileNotFoundException e) {
            Debug.logError("Failed to open the file: " + filename, MODULE);
            return "error";
        } catch (IOException ioe) {
            Debug.logError("IOException is thrown while trying to download the Excel file: " + ioe.getMessage(), MODULE);
            return "error";
        } finally {
            try {
                out.close();
                if (fileToDownload != null) {
                    fileToDownload.close();
                    // Delete the file under /runtime/output/ this is optional
                    file.delete();
                }
            } catch (IOException ioe) {
                Debug.logError("IOException is thrown while trying to download the Excel file: " + ioe.getMessage(), MODULE);
                return "error";
            }
        }

        return "success";
    }

    private String getAbsoluteFilePath(final String filename, ServletContext servletContext) {
        String rootPath = servletContext.getRealPath("../../../../");
        String filePath = "/runtime/output/";
        return rootPath + filePath + filename;
    }

    @SuppressWarnings("unchecked")
    private void jsonSerialize(String field, Object object, JSONObject json) {
        if (object instanceof BigDecimal) {
            // special treatment for BigDecimal as they cannot be serialized properly
            json.put(field, ((BigDecimal) object).doubleValue());
        } else if (object instanceof EntityInterface) {
            // in some cases the retrieved value include EntityInterface as its members, but then avoid serializing all the accessors as this would result in fetching all the related entities
            json.put(field, ((EntityInterface) object).toMapNoStamps());
        } else if (object instanceof Iterable) {
            // convert list of EntityInterface to list of Maps for the same reason as above
            List values = new ArrayList();
            for (Object it : (Iterable) object) {
                if (it instanceof EntityInterface) {
                    values.add(((EntityInterface) it).toMapNoStamps());
                } else if (object instanceof BigDecimal) {
                    values.add(((BigDecimal) object).doubleValue());
                } else {
                    values.add(it);
                }
            }
            json.put(field, values);
        } else {
            json.put(field, object);
        }
    }

}
