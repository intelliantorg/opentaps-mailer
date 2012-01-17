package net.intelliant.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.intelliant.imports.UtilImport;

import org.ofbiz.base.util.GeneralException;
import org.opentaps.tests.OpentapsTestCase;

public class ImportTests extends OpentapsTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testReadExcelIndices() throws GeneralException {
		String ofbizHome = System.getProperty("ofbiz.home");
		if (!ofbizHome.endsWith(File.separator)) {
			ofbizHome += File.separator;
		}
		String tokenizedPath = "hot-deploy/mailer/src/net/intelliant/tests/xls/import_sample.xls";
		String excelFilePath = ofbizHome + tokenizedPath.replaceAll("/", File.separator);
		try {
			List<Integer> expectedIndices = new ArrayList<Integer>();
			expectedIndices.add(0);
			expectedIndices.add(1);
			List<Integer> actualIndices = UtilImport.readExcelIndices(excelFilePath, 0);
			assertEquals(expectedIndices, actualIndices);
			
			expectedIndices = new ArrayList<Integer>();
			expectedIndices.add(1);
			expectedIndices.add(2);
			expectedIndices.add(3);
			actualIndices = UtilImport.readExcelIndices(excelFilePath, 1);
			assertEquals(expectedIndices, actualIndices);			
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}