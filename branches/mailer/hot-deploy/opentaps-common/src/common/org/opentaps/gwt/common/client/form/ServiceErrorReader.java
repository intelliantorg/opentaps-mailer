/*
 * Copyright (c) 2006 - 2009 Open Source Strategies, Inc.
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

package org.opentaps.gwt.common.client.form;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.util.JSON;
import org.opentaps.gwt.common.client.UtilUi;
import org.opentaps.gwt.common.client.lookup.UtilLookup;

/**
 * An utility class to make reading JSON response from ajax services easier.
 * This is just a basic reader to get the exception message returned by the service.
 *
 * Note that the server may also return exceptions such as 404 error page, or exceptions thrown by the <code>RequestHandler</code>, this reader is also able to catch them.
 *
 * In order to catch all error scenarios the form should the response to {@link #readResponse} in both the {@link org.opentaps.gwt.common.client.form.base.BaseFormPanel#onActionComplete} and {@link org.opentaps.gwt.common.client.form.base.BaseFormPanel#onActionFailed} handlers.
 *
 * See how this class is used in {@link org.opentaps.gwt.common.client.form.base.BaseFormPanel}.
 */
public class ServiceErrorReader {

    private Store store;
    /** Stores the response type of the last parsed response. */
    private ResponseType responseType = ResponseType.EMPTY_RESPONSE;
    /** Stores the error message string corresponding to the last parsed response. */
    private String parsedErrorMessage = null;

    /** Describes the response type. */
    public static enum ResponseType {
        /** An empty response string was returned. */
        EMPTY_RESPONSE,
        /** An invalid XML / HTML was returned, and no error message could be parsed from it. */
        SERVER_INVALID_ERROR_PAGE,
        /** A valid XML / HTML was returned, the error message was parsed successfully. */
        SERVER_ERROR_PAGE,
        /** A valid JSON response was successfully parsed from the server. */
        JSON_ERROR_MESSAGE,
        /** A valid JSON response was successfully parsed from the server, and it did not contain any error message. */
        JSON_NO_ERROR_MESSAGE
    }

    /**
     * Default Constructor.
     * Initialize a reader for the standard JSON error message structure.
     */
    public ServiceErrorReader() {
        RecordDef recordDef = new RecordDef(new FieldDef[]{new StringFieldDef(UtilLookup.JSON_ERROR_MESSAGE)});
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot(UtilLookup.JSON_ERROR_EXCEPTION);
        reader.setTotalProperty(UtilLookup.JSON_TOTAL);
        store = new Store(reader);
    }

    /**
     * Reads the given response and parse the error message.
     * If the response is a valid JSON_ERROR_EXCEPTION message, it will extract the exception message.
     * If the response is a server error page (such as the unexpected exception error page) it will try to parse the HTML document and extract the error message.
     * @param response a <code>String</code> value
     * @see #getAsString
     */
    public void readResponse(String response) {
        // reset variables to initial state
        responseType = ResponseType.EMPTY_RESPONSE;
        parsedErrorMessage = null;

        // simple check for empty response
        if (response == null || response.equals("")) {
            return;
        }

        // try to parse the response as an XML document
        if (!response.startsWith("{")) {
            try {
                Document doc = XMLParser.parse(response);
                // checks if the response has an HTML tag, then it is probably an error page
                NodeList listTitles = doc.getElementsByTagName("html");
                if (listTitles.getLength() > 0) {
                    // error.jsp only define one span tag which contains the error message, try to parse it
                    NodeList listErrorElements = doc.getElementsByTagName("span");
                    if (listErrorElements.getLength() > 0) {
                        Node node = listErrorElements.item(0).getFirstChild();
                        parsedErrorMessage = node.getNodeValue();
                    }
                    if (parsedErrorMessage != null) {
                        responseType = ResponseType.SERVER_ERROR_PAGE;
                    } else {
                        responseType = ResponseType.SERVER_INVALID_ERROR_PAGE;
                    }
                    return;
                }
            } catch (DOMException e) {
                // invalid XML
                responseType = ResponseType.SERVER_INVALID_ERROR_PAGE;
                return;
            }
        }

        // check if it is a valid error JSON
        try {
            // we cannot load it directly because if the JSON does not have the expected root it will chock
            JavaScriptObject jsObj = JSON.decode(response);
            JSONObject jsonObj = new JSONObject(jsObj);
            // check if it is really an Exception message, else not an error and return
            if (jsonObj.containsKey(UtilLookup.JSON_ERROR_EXCEPTION)) {
                store.loadJsonData(response, false);
            } else {
                responseType = ResponseType.JSON_NO_ERROR_MESSAGE;
                return;
            }
        } catch (Exception e) {
            // not a JSON_ERROR_EXCEPTION message
            // Note that it might still be a "normal" form error response in which case
            //  it is automatically handled by the form itself
            responseType = ResponseType.JSON_NO_ERROR_MESSAGE;
            return;
        }

        // response fits the JSON_ERROR_EXCEPTION structure
        if (store.getTotalCount() > 0) {
            Record rec = store.getRecordAt(0);
            if (rec != null) {
                parsedErrorMessage = rec.getAsString(UtilLookup.JSON_ERROR_MESSAGE);
                if (parsedErrorMessage != null) {
                    // format in HTML (replace \n by html <br/>
                    parsedErrorMessage = parsedErrorMessage.replaceAll("\n", "<br/>");
                    responseType = ResponseType.JSON_ERROR_MESSAGE;
                    return;
                }
            }
        }

        // if it gets here, the message will be the default EMPTY_RESPONSE

    }

    /**
     * Checks if the response contained any error message.
     * @return a <code>boolean</code> value
     */
    public boolean isError() {
        return responseType != ResponseType.JSON_NO_ERROR_MESSAGE;
    }

    /**
     * Checks if the response contained an empty error.
     * @return a <code>boolean</code> value
     */
    public boolean isEmptyResponse() {
        return responseType == ResponseType.EMPTY_RESPONSE || responseType == ResponseType.SERVER_INVALID_ERROR_PAGE;
    }

    /**
     * Gets the error string that was previously read by {@link #readResponse}.
     * @return a <code>String</code> value
     */
    public String getAsString() {
        if (isError()) {
            return parsedErrorMessage;
        } else {
            return null;
        }
    }

    /**
     * Gets the parsed response type.
     * @return a <code>String</code> value
     */
    public String getResponseType() {
        return responseType.toString();
    }

    /**
     * Parse an HTTP response and check for errors.
     * @param response a <code>Response</code> value
     * @param url the requested URL, used to specify it in the error message
     * @return <code>true</code> if an error was present
     */
    public static boolean showErrorMessageIfAny(Response response, String url) {
        return showErrorMessageIfAny(response.getStatusCode(), response.getText(), url);

    }

    /**
     * Parse an HTTP response and check for errors.
     * @param httpStatus an <code>int</code> value
     * @param responseText a <code>String</code> value
     * @param url the requested URL, used to specify it in the error message
     * @return <code>true</code> if an error was present
     */
    public static boolean showErrorMessageIfAny(int httpStatus, String responseText, String url) {
        final ServiceErrorReader errorReader = new ServiceErrorReader();
        // HTTP status code 2XX represent success responses from the server
        // note that normal error messages are returned with 2XX as well as most Ofbiz framework error responses
        if (httpStatus >= 200 && httpStatus <= 299) {
            errorReader.readResponse(responseText);
            if (errorReader.isError()) {
                if (errorReader.isEmptyResponse()) {
                    UtilUi.errorMessage(UtilUi.MSG.serverEmptyResponseError(url));
                } else {
                    UtilUi.errorMessage(errorReader.getAsString());
                }
                return true;
            } else {
                return false;
            }
        } else {
            UtilUi.errorMessage(UtilUi.MSG.serverHttpError(String.valueOf(httpStatus), url));
            return true;
        }
    }

}
