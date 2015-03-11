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
package net.intelliant.tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.intelliant.util.UtilCommon;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelField;
import org.ofbiz.entity.model.ModelReader;
import org.ofbiz.entity.util.ByteWrapper;

public class ContactListImportTest extends MailerTests {
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	private ByteWrapper getFile(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[256];
		while (fis.read(data) != -1) {
			baos.write(data);
		}
		return new ByteWrapper(baos.toByteArray());
	}

	@SuppressWarnings("unchecked")
	private String insertIntoImportMapper(GenericValue admin) throws GenericEntityException {
		ImportTests it = new ImportTests();
		Map<String, Object> inputs = it.getCreateImportMapperData(delegator, admin);

		Map<String, Object> inputsEntityColNames = UtilMisc.toMap("0", "firstName");
		inputsEntityColNames.put("1", "middleName");
		inputsEntityColNames.put("2", "lastName");
		inputsEntityColNames.put("3", "emailAddress");
		inputsEntityColNames.put("4", "phoneNo");
		inputsEntityColNames.put("5", "mobileNo");
		inputsEntityColNames.put("6", "organisationName");
		inputsEntityColNames.put("7", "address1");
		inputsEntityColNames.put("8", "address2");
		inputsEntityColNames.put("9", "postalCode");
		inputsEntityColNames.put("10", "stateName");
		inputsEntityColNames.put("11", "countyName");
		inputsEntityColNames.put("12", "salesOrServiceDate");

		inputs.put("entityColName", inputsEntityColNames);

		Map<String, Object> inputsImportFileColIdx = UtilMisc.toMap("0", "0");
		inputsImportFileColIdx.put("1", "1");
		inputsImportFileColIdx.put("2", "2");
		inputsImportFileColIdx.put("3", "3");
		inputsImportFileColIdx.put("4", "4");
		inputsImportFileColIdx.put("5", "5");
		inputsImportFileColIdx.put("6", "6");
		inputsImportFileColIdx.put("7", "7");
		inputsImportFileColIdx.put("8", "8");
		inputsImportFileColIdx.put("9", "9");
		inputsImportFileColIdx.put("10", "10");
		inputsImportFileColIdx.put("11", "11");
		inputsImportFileColIdx.put("12", "12");
		inputs.put("importFileColIdx", inputsImportFileColIdx);

		Map<?, ?> results = runAndAssertServiceSuccess("mailer.configureImportMapping", inputs);
		String importMapperId = (String) results.get("importMapperId");

		return importMapperId;
	}

	public void testImportList() throws GenericEntityException, IOException {
		String ofbizHome = System.getProperty("ofbiz.home");
		if (!ofbizHome.endsWith(File.separator)) {
			ofbizHome += File.separator;
		}

		String tokenizedPath = "hot-deploy/mailer/src/net/intelliant/tests/xls/import_sample4.xls";
		String excelFilePath = ofbizHome + tokenizedPath.replaceAll("/", File.separator);

		contactListImportTest(excelFilePath, 4, 0, 0, 4);

		tokenizedPath = "hot-deploy/mailer/src/net/intelliant/tests/xls/import_sample4_witherrors.xls";
		excelFilePath = ofbizHome + tokenizedPath.replaceAll("/", File.separator);

		contactListImportTest(excelFilePath, 16, 12, 12, 4);
	}

	@SuppressWarnings("unchecked")
	private void contactListImportTest(String excelFilePath, int totalCount, int failure, int failureReportSize, int successfullInsertion) throws GenericEntityException, IOException, FileNotFoundException {
		String importMapperId;
		String contactListId;
		Map<String, Object> inputData = UtilMisc.toMap("_uploadedFile_fileName", "import_sample4.xls");
		inputData.put("userLogin", admin);
		inputData.put("importMapperId", (importMapperId = insertIntoImportMapper(admin)));
		inputData.put("contactListId", (contactListId = createContactList(null)));
		inputData.put("uploadedFile", getFile(excelFilePath));
		inputData.put("_uploadedFile_contentType", "EXCEL");
		// inputData.put("excelFilePath", excelFilePath);

		long noOfRecords = delegator.findCountByAnd("MailerRecipientContactList", UtilMisc.toMap("contactListId", contactListId));
		Map<String, Object> result = runAndAssertServiceSuccess("mailer.importContactList", inputData);
		long modifiedNoOfRecords = delegator.findCountByAnd("MailerRecipientContactList", UtilMisc.toMap("contactListId", contactListId));

		assertEquals(totalCount, result.get("totalCount"));
		assertEquals(failure, result.get("failureCount"));
		assertEquals(failureReportSize, ((Map<?, ?>) result.get("failureReport")).size());
		assertEquals(successfullInsertion, modifiedNoOfRecords - noOfRecords);
		errorReportTest((Map<String, Object>)result.get("failureReport"), failureReportSize);

		Debug.log("## - " + totalCount + " ^ " + failure + " ^ " + failureReportSize + " ^ " + successfullInsertion);
		imortedDataTest(importMapperId, contactListId, excelFilePath);
	}

	private void errorReportTest(Map<String, Object> insertStatus, int failureReportSize) {
		Set<Entry<String, Object>> listInsertStatus = insertStatus.entrySet();

		// first element of each array is rowIndex, from 2nd element are the column indexes consists error.
		String[][] expectedErrorReport = new String[][] { { "1", "12" }, 
														  { "3", "3" }, 
														  { "5", "0" }, 
														  { "6", "12" }, 
														  { "7", "3", "5" }, 
														  { "8", "7" },
														  { "9", "3" }, 
														  { "10", "2" }, 
														  { "12", "3" }, 
														  { "13", "3" }, 
														  { "14", "3" }, 
														  { "15", "3" } };

		int index = 0, itr = 1;
		int coloumnIndex = 1;
		int totalNoOfColoumnConsistsError = 0;
		for (Entry<String, Object> entry : listInsertStatus) {
			Object value = entry.getValue();			
			assertEquals(true, value instanceof java.util.Map<?, ?>);

			Map<Integer, String> report = (Map<Integer, String>) value;
			totalNoOfColoumnConsistsError = expectedErrorReport[index].length - 1; 
			assertEquals(totalNoOfColoumnConsistsError, report.size());
			assertEquals(entry.getKey(), expectedErrorReport[index][0]);

			Debug.log("### : check : "+expectedErrorReport[index][0] + " # " + entry.getKey() + " [" + totalNoOfColoumnConsistsError + "]" );

			Set<Entry<Integer, String>> entriesOfReportColoumnWise = report.entrySet();
			coloumnIndex = 1;
			for(Entry<Integer, String> coloumnEntry : entriesOfReportColoumnWise){
				assertEquals(expectedErrorReport[index][coloumnIndex], String.valueOf(coloumnEntry.getKey()));
				Debug.log("### : "+expectedErrorReport[index][coloumnIndex++] + " # " + coloumnEntry.getKey());
			}
			index++;
		}
	}

	@SuppressWarnings("unchecked")
	private void imortedDataTest(String importMapperId, String contactListId, String excelPath) throws GenericEntityException, FileNotFoundException, IOException {
		GenericValue importMapper = delegator.findByPrimaryKey("MailerImportMapper", UtilMisc.toMap("importMapperId", importMapperId));

		List<GenericValue> importMapperColumns = delegator.findByAnd("MailerImportColumnMapper", UtilMisc.toMap("importMapperId", importMapperId));
		List<GenericValue> mailerRecipients = delegator.findByAnd("MailerRecipientAndContactListView", UtilMisc.toMap("contactListId", contactListId), UtilMisc.toList("recipientListId"));

		HSSFWorkbook excelWorkbook = new HSSFWorkbook(new FileInputStream(excelPath));
		HSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

		Iterator<?> rows = excelSheet.rowIterator();
		HSSFRow row = null;

		if (importMapper.get("isFirstRowHeader").equals("Y")) {
			rows.next();
		}

		int counter = 0;

		Timestamp testTimeStamp = null;
		Timestamp getTimestamp = null;
		String testString = null;
		String getString = null;
		int testNumber = 0;
		int getNumber = 0;

		ModelReader reader = delegator.getModelReader();
		ModelEntity modelEntity = reader.getModelEntity("MailerRecipient");
		ModelField modelField = null;

		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();

			if (validateRow(row, importMapperColumns, modelEntity)) {
				GenericValue mailerRecipient = mailerRecipients.get(counter++);

				Debug.log("##### [Row] : " + toStringHssfRow(row));
				Debug.log("##### [Entity] : " + mailerRecipient);

				for (GenericValue importMapperColumn : importMapperColumns) {
					short columnIndex = Short.valueOf(String.valueOf(importMapperColumn.get("importFileColIdx")));
					String columnName = (String) importMapperColumn.get("entityColName");
					modelField = modelEntity.getField(columnName);
					String columnType = modelField.getType();

					HSSFCell cell = row.getCell(columnIndex);

					if (columnType.equals("date") || columnType.equals("date-time")) {
						testTimeStamp = new Timestamp(mailerRecipient.getDate(columnName).getTime());
						getTimestamp = new Timestamp(cell.getDateCellValue().getTime());
						assertEquals(testTimeStamp, getTimestamp);
					} else if (columnName.equals("numeric")) {
						testNumber = mailerRecipient.getInteger(columnName);
						getNumber = (int) cell.getNumericCellValue();
						assertEquals(testNumber, getNumber);
					} else {
						testString = mailerRecipient.getString(columnName);
						getString = getStringData(cell);
						Debug.log(testString + " # " + getString);
						assertEquals(testString, getString);
					}
				}
			}
		}
	}

	private boolean validateRow(HSSFRow excelRow, List<GenericValue> importMapperColumns, ModelEntity modelEntity) {
		String entityColumnName;
		short excelColumnIndex;
		ModelField modelField = null;
		for (GenericValue importMapper : importMapperColumns) {
			entityColumnName = importMapper.getString("entityColName");
			excelColumnIndex = Short.valueOf(importMapper.getString("importFileColIdx"));

			modelField = modelEntity.getField(entityColumnName);
			if (modelField.getIsNotNull() && (excelRow.getCell(excelColumnIndex) == null || excelRow.getCell(excelColumnIndex).toString().equals(""))) {
				return false;
			}
			if (modelField.getType().equals("email") && !UtilCommon.isValidEmailAddress(excelRow.getCell(excelColumnIndex).toString())) {
				return false;
			}
			if ((modelField.getType().equals("date") || modelField.getType().equals("date-time"))) {
				try {
					new java.sql.Date(excelRow.getCell(excelColumnIndex).getDateCellValue().getTime());
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}

	private String getStringData(HSSFCell cell) {
		if (cell != null) {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				return String.valueOf((int) cell.getNumericCellValue());
			}
			return cell.toString();
		}
		return "";
	}

	private String toStringHssfRow(HSSFRow row) {
		String rowString = "";
		int size = row.getLastCellNum();
		HSSFCell cell = null;

		for (short i = 1; i <= size; i++) {
			cell = row.getCell(i);
			rowString += ((cell != null) ? cell.toString() : "") + ", ";
		}

		return rowString;
	}
}
