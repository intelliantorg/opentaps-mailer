package net.intelliant.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.intelliant.imports.UtilImport;

import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
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
			List<String> expectedIndices = new ArrayList<String>();
			expectedIndices.add("0");
			expectedIndices.add("1");
			List<String> actualIndices = UtilImport.readExcelHeaderIndices(excelFilePath, 0);
			assertEquals(expectedIndices, actualIndices);

			expectedIndices = new ArrayList<String>();
			expectedIndices.add("1");
			expectedIndices.add("2");
			expectedIndices.add("3");
			actualIndices = UtilImport.readExcelHeaderIndices(excelFilePath, 1);
			assertEquals(expectedIndices, actualIndices);
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	public void testReadExcelHeaders() throws GeneralException {
		String ofbizHome = System.getProperty("ofbiz.home");
		if (!ofbizHome.endsWith(File.separator)) {
			ofbizHome += File.separator;
		}
		String tokenizedPath = "hot-deploy/mailer/src/net/intelliant/tests/xls/import_sample.xls";
		String excelFilePath = ofbizHome + tokenizedPath.replaceAll("/", File.separator);
		try {
			List<String> expectedIndices = new ArrayList<String>();
			expectedIndices.add("col1");
			expectedIndices.add("col2");
			List<String> actualIndices = UtilImport.readExcelHeaderValues(excelFilePath, 0);
			assertEquals(expectedIndices, actualIndices);

			expectedIndices = new ArrayList<String>();
			expectedIndices.add("col3");
			expectedIndices.add("col5");
			expectedIndices.add("col4");
			actualIndices = UtilImport.readExcelHeaderValues(excelFilePath, 1);
			assertEquals(expectedIndices, actualIndices);
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	private String getFileName(String fullFilePath) {
		if (fullFilePath != null) {
			return fullFilePath.substring(fullFilePath.lastIndexOf(File.separator) + 1);
		}
		return null;
	}

	private String createContentData(GenericValue userLogin) throws GenericEntityException {
		// Initializing excel file location
		String ofbizHome = System.getProperty("ofbiz.home");
		if (!ofbizHome.endsWith(File.separator)) {
			ofbizHome += File.separator;
		}
		String tokenizedPath = "hot-deploy/mailer/src/net/intelliant/tests/xls/import_sample.xls";
		String excelFilePath = ofbizHome + tokenizedPath.replaceAll("/", File.separator);

		// Creating DataResource entity entry.
		String dataResourceId = delegator.getNextSeqId("DataResource");
		Map<String, Object> input = UtilMisc.toMap("dataResourceId", dataResourceId);
		input.put("statusId", "CTNT_PUBLISHED");
		input.put("createdDate", UtilDateTime.nowTimestamp());
		input.put("createdByUserLogin", userLogin.get("userLoginId"));
		input.put("objectInfo", excelFilePath);
		GenericValue dataResourceGV = delegator.makeValue("DataResource", input).create();

		// Creating Content entity entry.
		String contentId = delegator.getNextSeqId("Content");
		input = UtilMisc.toMap("contentId", contentId, "createdDate", dataResourceGV.get("createdDate"));
		input.put("createdDate", dataResourceGV.get("createdDate"));
		input.put("dataResourceId", dataResourceGV.get("dataResourceId"));
		input.put("statusId", dataResourceGV.get("statusId"));
		input.put("contentName", dataResourceGV.get("dataResourceName"));
		input.put("mimeTypeId", dataResourceGV.get("mimeTypeId"));
		input.put("contentTypeId", "FILE");
		input.put("contentName", getFileName(excelFilePath));
		input.put("createdByUserLogin", dataResourceGV.get("createdByUserLogin"));
		delegator.makeValue("Content", input).create();

		return contentId;
	}

	private Map<String, Object> getCreateImportMapperData(GenericValue userLogin) throws GenericEntityException {
		Long timeStamp = System.currentTimeMillis();

		Map<String, Object> createData = UtilMisc.toMap("userLogin", admin);
		createData.put("importMapperName", "Name-" + timeStamp);
		createData.put("ofbizEntityName", "MailerRecipient");
		createData.put("contentId", createContentData(userLogin));
		createData.put("description", "Des-" + timeStamp);
		createData.put("isFirstRowHeader", false);

		return createData;
	}

	public void testConfigureImportMapper() throws GenericEntityException {
		Map<String, Object> inputs = getCreateImportMapperData(admin);

		Map<String, Object> inputsEntityColNames = UtilMisc.toMap("0", "firstName");
		inputsEntityColNames.put("1", "lastName");
		inputsEntityColNames.put("2", "emailAddress");
		inputs.put("entityColName", inputsEntityColNames);

		Map<String, Object> inputsimportFileColIdx = UtilMisc.toMap("0", "0");
		inputsimportFileColIdx.put("1", "1");
		inputsimportFileColIdx.put("2", "_NA_");
		inputs.put("importFileColIdx", inputsimportFileColIdx);

		Map<?, ?> results = runAndAssertServiceSuccess("mailer.configureImportMapping", inputs);
		String importMapperId = (String) results.get("importMapperId");

		testConfigureImportMapperData(importMapperId, inputs);
		testConfigureImportColumnMapperData(importMapperId, inputs);
	}

	// To test inserted data is expected or not?
	private void testConfigureImportMapperData(String importMapperId, Map<String, Object> inputs) throws GenericEntityException {
		GenericValue mailerImportMapperGV = delegator.findByPrimaryKey("MailerImportMapper", UtilMisc.toMap("importMapperId", importMapperId));

		assertEquals(mailerImportMapperGV.get("importMapperName"), inputs.get("importMapperName"));
		assertEquals(mailerImportMapperGV.get("ofbizEntityName"), inputs.get("ofbizEntityName"));
		assertEquals(mailerImportMapperGV.get("contentId"), inputs.get("contentId"));
		assertEquals(mailerImportMapperGV.get("description"), inputs.get("description"));
		assertEquals(mailerImportMapperGV.get("isFirstRowHeader"), "N");
	}

	// To test inserted data is expected or not?
	private void testConfigureImportColumnMapperData(String importMapperId, Map<String, Object> inputs) throws GenericEntityException {
		Map<String, Object> entityColNames = (Map<String, Object>) inputs.get("entityColName");
		Map<String, Object> importFileColIdxs = (Map<String, Object>) inputs.get("importFileColIdx");

		Set<String> keys = entityColNames.keySet();
		for (String key : keys) {
			GenericValue mailerImportColumnMapperGV = EntityUtil.getFirst(delegator.findByAnd("MailerImportColumnMapper", UtilMisc.toMap("importMapperId", importMapperId, "entityColName", entityColNames.get(key))));
			assertEquals(importFileColIdxs.get(key), mailerImportColumnMapperGV.get("importFileColIdx"));
		}
	}

	public void testUpdateImportMapper() throws GenericEntityException {
		// Create import mapper
		Map<String, Object> inputs = getCreateImportMapperData(admin);

		Map<String, Object> inputsEntityColNames = UtilMisc.toMap("0", "firstName");
		inputsEntityColNames.put("1", "lastName");
		inputsEntityColNames.put("2", "emailAddress");
		inputs.put("entityColName", inputsEntityColNames);

		Map<String, Object> inputsimportFileColIdx = UtilMisc.toMap("0", "0");
		inputsimportFileColIdx.put("1", "1");
		inputsimportFileColIdx.put("2", "_NA_");
		inputs.put("importFileColIdx", inputsimportFileColIdx);

		Map<?, ?> results = runAndAssertServiceSuccess("mailer.configureImportMapping", inputs);
		String importMapperId = (String) results.get("importMapperId");

		testConfigureImportMapperData(importMapperId, inputs);
		testConfigureImportColumnMapperData(importMapperId, inputs);

		// Update import mapper.
		Map<String, Object> updateInputs = getCreateImportMapperData(admin);
		updateInputs.put("importMapperId", importMapperId);
		updateInputs.put("isFirstRowHeader", "Y");
		updateInputs.remove("ofbizEntityName");
		updateInputs.remove("contentId");

		inputsEntityColNames = UtilMisc.toMap("0", "firstName");
		inputsEntityColNames.put("1", "lastName");
		inputsEntityColNames.put("2", "emailAddress");
		updateInputs.put("entityColName", inputsEntityColNames);

		inputsimportFileColIdx = UtilMisc.toMap("0", "1");
		inputsimportFileColIdx.put("1", "_NA_");
		inputsimportFileColIdx.put("2", "_NA_");
		updateInputs.put("importFileColIdx", inputsimportFileColIdx);

		// Run update service
		runAndAssertServiceSuccess("mailer.updateImportMapping", updateInputs);

		testUpdateImportMapperData(importMapperId, updateInputs);
		testUpdateImportColumnMapperData(importMapperId, inputs, updateInputs);
		testUpdateMustFail(importMapperId, updateInputs);
	}

	// To test inserted data is expected or not?
	private void testUpdateImportMapperData(String importMapperId, Map<String, Object> inputs) throws GenericEntityException {
		GenericValue mailerImportMapperGV = delegator.findByPrimaryKey("MailerImportMapper", UtilMisc.toMap("importMapperId", importMapperId));
		assertEquals(mailerImportMapperGV.get("importMapperName"), inputs.get("importMapperName"));
		assertEquals(mailerImportMapperGV.get("description"), inputs.get("description"));
		assertEquals(mailerImportMapperGV.get("isFirstRowHeader"), "Y");
	}
	
	private void testUpdateImportColumnMapperData(String importMapperId, Map<String, Object> createInputs, Map<String, Object> updateInputs) throws GenericEntityException {
		testConfigureImportColumnMapperData(importMapperId, updateInputs);
		
		Map<String, Object> createEntityColumnNames = (Map<String, Object>) createInputs.get("entityColName");
		Map<String, Object> createFileColumnIndexes = (Map<String, Object>) createInputs.get("importFileColIdx");
		
		Map<String, Object> updateEntityColumnNames = (Map<String, Object>) updateInputs.get("entityColName");
		Map<String, Object> updateFileColumnIndexes = (Map<String, Object>) updateInputs.get("importFileColIdx");
		
		Set<String> keys = createEntityColumnNames.keySet();
		GenericValue mailerImportColumnMapperGV = null;
		for(String key : keys){
			mailerImportColumnMapperGV =  EntityUtil.getFirst(delegator.findByAnd("MailerImportColumnMapper", UtilMisc.toMap("importMapperId", importMapperId, "entityColName", updateEntityColumnNames.get(key))));
			
			if(createFileColumnIndexes.get(key).equals(updateFileColumnIndexes.get(key))){
				assertEquals(null,mailerImportColumnMapperGV.get("lastModifiedByUserLogin"));
			}else{
				assertEquals(((GenericValue)updateInputs.get("userLogin")).get("userLoginId"),mailerImportColumnMapperGV.get("lastModifiedByUserLogin"));
			}
		}		
	}
	private void testUpdateMustFail(String importMapperId, Map<String, Object> inputs) throws GenericEntityException{
		inputs.put("importMapperId", importMapperId);
		inputs.remove("importMapperName");
		inputs.remove("ofbizEntityName");
		inputs.remove("contentId");

		Map<String,Object> inputsEntityColNames = UtilMisc.toMap("0", "firstName");
		inputsEntityColNames.put("1", "lastName");
		inputsEntityColNames.put("2", "emailAddress");
		inputs.put("entityColName", inputsEntityColNames);

		Map<String,Object> inputsimportFileColIdx = UtilMisc.toMap("0", "1");
		inputsimportFileColIdx.put("1", "_NA_");
		inputsimportFileColIdx.put("2", "_NA_");
		inputs.put("importFileColIdx", inputsimportFileColIdx);

		runAndAssertServiceFailure("mailer.updateImportMapping", inputs);
		
		inputs.clear();
		inputs = getCreateImportMapperData(admin);
		inputs.put("importMapperId", importMapperId);
		//inputs.remove("ofbizEntityName");
		//inputs.remove("contentId");
		inputsEntityColNames = UtilMisc.toMap("0", "firstName");
		inputsEntityColNames.put("1", "lastName");
		inputsEntityColNames.put("2", "emailAddress");
		inputs.put("entityColName", inputsEntityColNames);

		inputsimportFileColIdx = UtilMisc.toMap("0", "1");
		inputsimportFileColIdx.put("1", "_NA_");
		inputsimportFileColIdx.put("2", "_NA_");
		inputs.put("importFileColIdx", inputsimportFileColIdx);
		runAndAssertServiceFailure("mailer.updateImportMapping", inputs);

		inputs.clear();
		inputs = getCreateImportMapperData(admin);
		inputs.put("importMapperId", importMapperId);
		inputs.remove("ofbizEntityName");
		inputs.remove("contentId");

		inputsimportFileColIdx = UtilMisc.toMap("0", "1");
		inputsimportFileColIdx.put("1", "_NA_");
		inputsimportFileColIdx.put("2", "_NA_");
		inputs.put("importFileColIdx", inputsimportFileColIdx);

		runAndAssertServiceFailure("mailer.updateImportMapping", inputs);		
	}
}