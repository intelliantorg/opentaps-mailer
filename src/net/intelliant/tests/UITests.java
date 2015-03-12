/*
 * Copyright (c) Intelliant
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (open.ant@intelliant.net)
 */
package net.intelliant.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.ofbiz.base.location.FlexibleLocation;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.webapp.control.ConfigXMLReader;
import org.ofbiz.webapp.control.ConfigXMLReader.ControllerConfig;
import org.ofbiz.widget.screen.ModelScreen;
import org.ofbiz.widget.screen.ScreenFactory;
import org.xml.sax.SAXException;

public class UITests extends MailerTests {
	private final static String module = UITests.class.getName();

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testIfScreensExist() throws GeneralException {
		List<String> ignoreScreensList = new ArrayList<String>();
		ignoreScreensList.add("LookupPartner");
		URL urlLocation = null;
		try {
			urlLocation = FlexibleLocation.resolveLocation("component://mailer/webapp/mailer/WEB-INF/controller.xml");
			assertNotNull("Failed to resolve controller location !", urlLocation);
		} catch (MalformedURLException e) {
			fail("Failed to resolve location !");
		}
	    ControllerConfig config = ConfigXMLReader.getControllerConfig(urlLocation);
	    assertNotNull(config);
	    
	    Map<?, ?> viewMaps = config.viewMap;
	    for (Map.Entry entry : viewMaps.entrySet()) {
	    	Map uriMap = (Map) entry.getValue();
	    	String type = (String) uriMap.get(ConfigXMLReader.VIEW_TYPE);
	    	if (type.equals("screen") && !ignoreScreensList.contains(uriMap.get(ConfigXMLReader.VIEW_NAME))) {
	    		checkIfScreenExists(uriMap); 
	    	}
	    }
	    assertTrue(true);
	}

	private void checkIfScreenExists(Map uriMap) {
		try {
			ModelScreen screen = ScreenFactory.getScreenFromLocation((String) uriMap.get(ConfigXMLReader.VIEW_PAGE));
			assertNotNull(screen);
		} catch (IOException e) {
			fail("Error reading screen - " + uriMap.get(ConfigXMLReader.VIEW_PAGE));
		} catch (SAXException e) {
			fail("Error reading screen - " + uriMap.get(ConfigXMLReader.VIEW_PAGE));
		} catch (ParserConfigurationException e) {
			fail("Error reading screen - " + uriMap.get(ConfigXMLReader.VIEW_PAGE));
		} catch (Exception e) {
			fail("Error reading screen - " + uriMap.get(ConfigXMLReader.VIEW_PAGE));
		}
	}
	
//	public void testScreenNamesMustHaveSuffixScreen() throws GeneralException {
//		URL urlLocation = null;
//		try {
//			urlLocation = FlexibleLocation.resolveLocation("component://mailer/webapp/mailer/WEB-INF/controller.xml");
//			assertNotNull("Failed to resolve controller location !", urlLocation);
//		} catch (MalformedURLException e) {
//			fail("Failed to resolve location !");
//		}
//	    ControllerConfig config = ConfigXMLReader.getControllerConfig(urlLocation);
//	    assertNotNull(config);
//	    
//	    Map<?, ?> viewMaps = config.viewMap;
//	    for (Map.Entry entry : viewMaps.entrySet()) {
//	    	Map uriMap = (Map) entry.getValue();
//	    	String type = (String) uriMap.get(ConfigXMLReader.VIEW_TYPE);
//	    	if (type.equals("screen")) {
//    			String path = (String) uriMap.get(ConfigXMLReader.VIEW_PAGE);
//    			String name = (String) uriMap.get(ConfigXMLReader.VIEW_NAME);
//    			assertEquals(path + " must have suffix Screen", true, path.endsWith("Screen"));
//    			assertEquals(name + " must have suffix Screen", true, name.endsWith("Screen"));
//	    	}
//	    }
//	    assertTrue(true);
//	}
}
