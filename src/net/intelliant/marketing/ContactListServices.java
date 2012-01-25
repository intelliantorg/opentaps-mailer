package net.intelliant.marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.intelliant.dto.StatusReportOfImportContactList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;

public class ContactListServices {
	private static final String MODULE = ContactListServices.class.getName();

	/**
	 * Gets the path for uploaded files.
	 * @return a <code>String</code> value
	 */
	private static String getUploadPath() {
		return System.getProperty("user.dir") + File.separatorChar + "runtime" + File.separatorChar + "data" + File.separatorChar;
	}

	public static Map<String, Object> importContactList(DispatchContext dctx, Map<String, ? extends Object> context) {
		System.out.println("The inputs >> " + context);
		String fileFormat = "EXCEL";
		String fileName = (String) context.get("_uploadedFile_fileName");
//		String mimeTypeId = (String) context.get("_uploadedFile_contentType");
		String importMapperId = (String) context.get("importMapperId");
		
//		if (mimeTypeId != null && mimeTypeId.length() > 60) {
//			// XXX This is a fix to avoid problems where an OS gives us a mime
//			// type that is too long to fit in 60 chars (ex. MS .xlsx as
//			// application/vnd.openxmlformats-officedocument.spreadsheetml.sheet)
//			Debug.logWarning("Truncating mime type [" + mimeTypeId + "] to 60 characters.", MODULE);
//			mimeTypeId = mimeTypeId.substring(0, 60);
//		}
		String excelFilePath = getUploadPath() + fileName;
		// save the file to the system using the ofbiz service
		Map<String, Object> input = UtilMisc.toMap("dataResourceId", null, "binData", context.get("uploadedFile"), "dataResourceTypeId", "LOCAL_FILE", "objectInfo", excelFilePath);
		try {
			Map<String, Object> results = dctx.getDispatcher().runSync("createAnonFile", input);
			if (ServiceUtil.isError(results)) {
				return results;
			}
			// for now we only support EXCEL format
			if ("EXCEL".equalsIgnoreCase(fileFormat)) {
				System.out.println("Congratulation! You have traveled a long path : " + excelFilePath);
				GenericValue mailerImportMapper = dctx.getDelegator().findByPrimaryKey("MailerImportMapper", UtilMisc.toMap("importMapperId", importMapperId));
				insertIntoMailerRecipient(dctx.getDelegator(), mailerImportMapper.getString("ofbizEntityName"), excelFilePath, importMapperId);
				/*
				 * ExcelImportServices excelImportService; try { excelImportService
				 * = new ExcelImportServices(new Infrastructure(dispatcher), new
				 * User(userLogin), (Locale) context.get("locale"));
				 * excelImportService.setUploadedFileName(fileName);
				 * excelImportService.parseFileForDataImport(); } catch
				 * (ServiceException e) { return
				 * UtilMessage.createAndLogServiceError(e, MODULE); } catch
				 * (IllegalArgumentException e) { return
				 * UtilMessage.createAndLogServiceError(e, MODULE); }
				 */
			} else {
				return UtilMessage.createAndLogServiceError("[" + fileFormat + "] is not a supported file format.", MODULE);
			}

		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} catch (FileNotFoundException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		} catch (IOException e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		}
		return ServiceUtil.returnSuccess();
	}

	// Completed
	private static StatusReportOfImportContactList insertIntoMailerRecipient(GenericDelegator delegator, String entityName, String excelFilePath, String columnMapperId) throws GenericEntityException, FileNotFoundException, IOException {
		Map<String, Integer> columnMapper = getColumnMapper(delegator, columnMapperId);

		HSSFWorkbook excelDocument = new HSSFWorkbook(new FileInputStream(excelFilePath));
		HSSFSheet excelSheet = excelDocument.getSheetAt(0); 
		
		Iterator<HSSFRow> excelRowIterator = excelSheet.rowIterator();
		
		int index = 0;
		int successfulInsertion = 0;
		int failedInsertion = 0;
		Map<Integer,String> fullReport = new LinkedHashMap<Integer, String>();
		while(excelRowIterator.hasNext()){
			try{
				insertIntoMailerRecipient(delegator, entityName, excelRowIterator.next(), columnMapper);
				fullReport.put(index++, "Successful");
				successfulInsertion++;
			}catch (GenericEntityException gee) {
				Debug.log(gee, MODULE);
				fullReport.put(index++, "Failed : "+gee.getMessage());
				failedInsertion++;
			}
		}
		StatusReportOfImportContactList report = new StatusReportOfImportContactList(successfulInsertion, failedInsertion, fullReport);
		
		return report;
	}

	// Completed
	private static void insertIntoMailerRecipient(GenericDelegator delegator, String entityName, HSSFRow excelRowData, Map<String, Integer> columnMapper) throws GenericEntityException {
		Map<String, Object> rowData = null;

		String entityPrimaryKeyField = delegator.getModelEntity(entityName).getFirstPkFieldName();
		String entityPrimaryKey = delegator.getNextSeqId(entityName);
		rowData = UtilMisc.toMap(entityPrimaryKeyField, entityPrimaryKey);

		Set<String> keys = columnMapper.keySet();
		for(String key : keys){
			rowData.put(key, excelRowData.getCell(Short.parseShort(String.valueOf(columnMapper.get(key)))).toString());
		}
		System.out.println("The rowData >> " + rowData);
		delegator.create(entityName, rowData);
	}

	// Completed
	private static Map<String, Integer> getColumnMapper(GenericDelegator delegator, String columnMapperId) throws GenericEntityException {
		Map<String, Integer> data = new LinkedHashMap<String, Integer>();
		List<GenericValue> columnToIndexMappings = delegator.findByAnd("MailerImportColumnMapper", UtilMisc.toMap("importMapperId", "columnMapperId"));
		// Converting list to map.
		for (GenericValue columnToIndexMapping : columnToIndexMappings) {
			data.put(String.valueOf(columnToIndexMapping.get("entityColName")), Integer.parseInt(String.valueOf(columnToIndexMapping.get("importFileColIdx"))));
		}
		return data;
	}
}
