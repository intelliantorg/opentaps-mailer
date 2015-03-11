/*
 * Copyright (c) Intelliant
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (iaerp@intelliant.net)
 */
package net.intelliant.util;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.w3c.tidy.Configuration;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.location.FlexibleLocation;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import javolution.util.FastMap;

public class XslFoConversion {    
    public static final String module = XslFoConversion.class.getName();
    private Map<String, Object> parameters;

    public XslFoConversion() {
    	parameters = FastMap.newInstance();
    }

    public Document convertXHtml2XslFo(String xhtml, String stylesheetLocation) {
        return convertXml2XslFo(xhtml, stylesheetLocation);
    }
    
    public String convertHtml2Xhtml(String html) {
        String xhtml = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(html.getBytes("UTF-8"));
            Tidy tidy = new Tidy();
            tidy.setXmlOut(true);
            tidy.setXHTML(true); 
            tidy.setDocType("omit"); // will remove doc type declaration
            tidy.setMakeClean(true);
            tidy.setTidyMark(false);
            tidy.setUpperCaseTags(false);
            tidy.setUpperCaseAttrs(false);
            tidy.setQuoteAmpersand(false);
            tidy.setNumEntities(true);
            tidy.setCharEncoding(Configuration.UTF8);
            tidy.parse(bais, baos);
            xhtml = baos.toString();
        } catch (Exception e) {
            Debug.logError(e, module);
        } finally {
            try {
                baos.close();
                baos = null;
            } catch (Exception e) {
                Debug.logError(e, module);
            }
        }
        return xhtml;
    }

    private Document convertXml2XslFo(String xml, String stylesheetLocation) {
        Document xslfoDocument = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource insource = new InputSource(new StringReader(xml));
            Document document = builder.parse(insource);

            DOMSource xmlDomSource = new DOMSource(document);
            DOMResult domResult = new DOMResult();

            Transformer transformer = getTransformer(stylesheetLocation);
            Set<Map.Entry<String, Object>> entries = parameters.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
            	transformer.setParameter(entry.getKey(), entry.getValue());
            	if (Debug.infoOn()) {
            		Debug.logInfo("[XslFoConversion] setting parameters, key = " + entry.getKey() + ", value = " + entry.getValue(), module);
            	}
            }
            if (UtilValidate.isEmpty(transformer)) {
                Debug.logError(" Error creating transformer for " + stylesheetLocation, module);
            }
            transformer.transform(xmlDomSource, domResult);
            xslfoDocument = (Document) domResult.getNode();
        }
        catch (Exception e) {
            Debug.logError(e, module);
        }
        return xslfoDocument;
    }

    private static Transformer getTransformer(String styleSheetLocation) {
        try {
            URL styleSheetURL = FlexibleLocation.resolveLocation(styleSheetLocation);
            if (UtilValidate.isEmpty(styleSheetURL)) {
                throw new IllegalArgumentException("Stylesheet file not found at location: " + styleSheetLocation);
            }
            File stylesheetFile = new File(styleSheetURL.toURI());
            TransformerFactory tFactory = TransformerFactory.newInstance();
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            dFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document xslDoc = dBuilder.parse(stylesheetFile);
            DOMSource xslDomSource = new DOMSource(xslDoc);

            return tFactory.newTransformer(xslDomSource);
        }
        catch (Exception e) {
            Debug.logError(e, module);
            return null;
        }
    }

    public void setParameters(Map<String, Object> parameters) {
		this.parameters.putAll(parameters);
	}
    
    public void setParameter(String key, Object value) {
		this.parameters.put(key, value);
	}
}