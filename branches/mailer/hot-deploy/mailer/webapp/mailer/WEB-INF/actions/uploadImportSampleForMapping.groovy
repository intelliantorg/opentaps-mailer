import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javolution.util.FastMap;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.util.ByteWrapper;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilValidate;
import net.intelliant.imports.UtilImport;

module = "uploadImportSampleForMapping.bsh";
dispatcher = request.getAttribute("dispatcher");
delegator = request.getAttribute("delegator");
userLogin = session.getAttribute("userLogin");
encoding = request.getCharacterEncoding();
fileAndPath = "";
multiPartMap = FastMap.newInstance();
/**
 * Ideally this enitre uploaded data file extraction should have been picked from org.ofbiz.content.layout.LayoutWorker()
 * The need to extract entity name prevents taking this approach.   
 */
isMultiPart = FileUpload.isMultipartContent(request);
if (isMultiPart) {
    DiskFileUpload upload = new DiskFileUpload();
    if (encoding != null) {
        upload.setHeaderEncoding(encoding);
    }
    upload.setSizeMax(-1);

    uploadedItems = null;
    try {
        uploadedItems = upload.parseRequest(request);
    } catch (FileUploadException e) {
        throw new EventHandlerException("Problems reading uploaded data", e);
    }
    if (uploadedItems != null) {
        Iterator i = uploadedItems.iterator();
        while (i.hasNext()) {
            FileItem item = (FileItem) i.next();
            String fieldName = item.getFieldName();
            if (item.isFormField() || item.getName() == null) {
                if (multiPartMap.containsKey(fieldName)) {
                    Object mapValue = multiPartMap.get(fieldName);
                    if (mapValue instanceof List) {
                        ((List) mapValue).add(item.getString());
                    } else if (mapValue instanceof String) {
                        List newList = new ArrayList();
                        newList.add((String) mapValue);
                        newList.add(item.getString());
                        multiPartMap.put(fieldName, newList);
                    } else {
                        Debug.logWarning("Form field found [" + fieldName + "] which was not handled!", module);
                    }
                } else {
                    if (encoding != null) {
                        try {
                            multiPartMap.put(fieldName, item.getString(encoding));
                        } catch (java.io.UnsupportedEncodingException uee){
                            Debug.logError(uee, "Unsupported Encoding, using deafault", module);
                            multiPartMap.put(fieldName, item.getString());
                        }
                    } else {
                        multiPartMap.put(fieldName, item.getString());
                    }
                }
            } else {
                String fileName = item.getName();
                if (fileName.indexOf('\\') > -1 || fileName.indexOf('/') > -1) {
                    // get just the file name IE and other browsers also pass in the local path
                    int lastIndex = fileName.lastIndexOf('\\');
                    if (lastIndex == -1) {
                        lastIndex = fileName.lastIndexOf('/');
                    }
                    if (lastIndex > -1) {
                        fileName = fileName.substring(lastIndex + 1);
                    }
                }
                input = UtilMisc.toMap("dataResourceId", delegator.getNextSeqId("DataResource"), "statusId", "CTNT_PUBLISHED");
        		input.put("createdDate", UtilDateTime.nowTimestamp());
        		input.put("createdByUserLogin", userLogin.get("userLoginId"));
        		dataResourceGV = delegator.makeValue("DataResource", input);
                dataResourceId = dataResourceGV.getString("dataResourceId");
                fileAndPath = org.ofbiz.content.data.DataResourceWorker.getDataResourceContentUploadPath();
                fileAndPath += File.separatorChar + dataResourceId;
                File parent = new File(fileAndPath);
        		if (!parent.exists()) {
        			parent.mkdir();
        		}
                fileAndPath += File.separatorChar + fileName;
                dataResourceGV.put("objectInfo", fileAndPath);
                dataResourceGV.create();
                input = UtilMisc.toMap("dataResourceId", dataResourceId, "binData", new ByteWrapper(item.get()), "dataResourceTypeId", "LOCAL_FILE", "objectInfo", fileAndPath);
            	results = dispatcher.runSync("createAnonFile", input);
		        contentId = delegator.getNextSeqId("Content");
            	input = UtilMisc.toMap("contentId", contentId, "createdDate", dataResourceGV.get("createdDate"));
		        input.put("createdDate", dataResourceGV.get("createdDate"));
		        input.put("dataResourceId", dataResourceGV.get("dataResourceId"));
		        input.put("statusId", dataResourceGV.get("statusId"));
		        input.put("contentName", dataResourceGV.get("dataResourceName"));
		        input.put("mimeTypeId", dataResourceGV.get("mimeTypeId"));
		        input.put("contentTypeId", "FILE");
		        input.put("contentName", fileName);
		        input.put("createdByUserLogin", dataResourceGV.get("createdByUserLogin"));
            	delegator.makeValue("Content", input).create();
            	context.put("contentId", contentId);
             }
        }
    }
}
if (UtilValidate.isNotEmpty(fileAndPath)) {
	entityColumnsToIgnore = UtilMisc.toList("lastUpdatedStamp", "lastUpdatedTxStamp", "createdStamp", "createdTxStamp", "importedOnDateTime", "importedByUserLogin");
	lhsColumns = UtilImport.getEntityColumns(multiPartMap.get("entityName"), entityColumnsToIgnore);
	context.put("lhsColumns", lhsColumns);
	
	rhsColumns = UtilImport.readExcelIndices(fileAndPath, 0);
	context.put("rhsColumns", rhsColumns);
} else {
	Debug.logWarning("fileAndPath was empty !", module);
}