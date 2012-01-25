package net.intelliant.marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
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
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;

public class ContactListServices {
	private static final String MODULE = ContactListServices.class.getName();

	/**
	 * Gets the path for uploaded files.
	 * 
	 * @return a <code>String</code> value
	 */
	private static String getUploadPath() {
		return System.getProperty("user.dir") + File.separatorChar + "runtime" + File.separatorChar + "data" + File.separatorChar;
	}

	public static Map<String, Object> importContactList(DispatchContext dctx, Map<String, ? extends Object> context) {
		System.out.println("The inputs >> " + context);
		String fileFormat = "EXCEL";
		String fileName = (String) context.get("_uploadedFile_fileName");
		// String mimeTypeId = (String)
		// context.get("_uploadedFile_contentType");

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String importMapperId = (String) context.get("importMapperId");
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
				GenericValue mailerImportMapper = dctx.getDelegator().findByPrimaryKey("MailerImportMapper", UtilMisc.toMap("importMapperId", importMapperId));

				insertIntoMailerRecipient(dctx.getDelegator(), userLogin.getString("userLoginId"), mailerImportMapper.getString("ofbizEntityName"), String.valueOf(context.get("contactListId")), excelFilePath, importMapperId);
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
	protected static StatusReportOfImportContactList insertIntoMailerRecipient(GenericDelegator delegator, String userLoginId, String entityName, String contactListId, String excelFilePath, String columnMapperId) throws GenericEntityException, FileNotFoundException, IOException {
		Map<String, Integer> columnMapper = getColumnMapper(delegator, columnMapperId);

		HSSFWorkbook excelDocument = new HSSFWorkbook(new FileInputStream(excelFilePath));
		HSSFSheet excelSheet = excelDocument.getSheetAt(0);

		Iterator<HSSFRow> excelRowIterator = excelSheet.rowIterator();

		String recipientId = null;

		int index = 0;
		int successfulInsertion = 0;
		int failedInsertion = 0;
		Map<Integer, String> fullReport = new LinkedHashMap<Integer, String>();
		while (excelRowIterator.hasNext()) {
			try {
				TransactionUtil.begin();

				recipientId = insertIntoMailerRecipient(delegator, userLoginId, entityName, excelRowIterator.next(), columnMapper);
				fetchCampaignForMailerCampaignStatus(delegator, contactListId, recipientId);

				fullReport.put(index++, "Successful");
				successfulInsertion++;
				TransactionUtil.commit();
			} catch (GenericEntityException gee) {
				TransactionUtil.rollback();
				Debug.log(gee, MODULE);
				fullReport.put(index++, "Failed : " + gee.getMessage());
				failedInsertion++;
			}
		}
		StatusReportOfImportContactList report = new StatusReportOfImportContactList(successfulInsertion, failedInsertion, fullReport);
		// System.out.println(report.toString());
		return report;
	}

	// Completed
	protected static String insertIntoMailerRecipient(GenericDelegator delegator, String userLoginId, String entityName, HSSFRow excelRowData, Map<String, Integer> columnMapper) throws GenericEntityException {
		Map<String, Object> rowData = null;

		String entityPrimaryKeyField = delegator.getModelEntity(entityName).getFirstPkFieldName();
		String entityPrimaryKey = delegator.getNextSeqId(entityName);
		rowData = UtilMisc.toMap(entityPrimaryKeyField, entityPrimaryKey);
		rowData.put("importedOnDateTime", new Timestamp(new Date().getTime()));
		rowData.put("importedByUserLogin", userLoginId);

		Set<String> keys = columnMapper.keySet();
		for (String key : keys) {
			rowData.put(key, excelRowData.getCell(Short.parseShort(String.valueOf(columnMapper.get(key)))).toString());
		}
		// System.out.println("Column mapper >> "+columnMapper);
		// System.out.println("The rowData >> " + rowData);
		delegator.create(entityName, rowData);
		return entityPrimaryKey;
	}

	// Completed
	protected static Map<String, Integer> getColumnMapper(GenericDelegator delegator, String columnMapperId) throws GenericEntityException {
		Map<String, Integer> data = new LinkedHashMap<String, Integer>();
		List<GenericValue> columnToIndexMappings = delegator.findByAnd("MailerImportColumnMapper", UtilMisc.toMap("importMapperId", columnMapperId));
		// Converting list to map.
		for (GenericValue columnToIndexMapping : columnToIndexMappings) {
			data.put(String.valueOf(columnToIndexMapping.get("entityColName")), Integer.parseInt(String.valueOf(columnToIndexMapping.get("importFileColIdx"))));
		}
		// System.out.println("ID : "+columnMapperId+"\nData : "+data);
		return data;
	}

	// Work on Progress
	protected static void fetchCampaignForMailerCampaignStatus(GenericDelegator delegator, String contactListId, String recipientId) throws GenericEntityException {
		List<GenericValue> mmcacl = delegator.findByAnd("MailerMarketingCampaignAndContactList", UtilMisc.toMap("contactListId", contactListId));

		String marketingCampaignId = null;
		for (GenericValue mmcaclDatum : mmcacl) {
			marketingCampaignId = String.valueOf(mmcaclDatum.get("marketingCampaignId"));

			GenericValue marketingCampaign = delegator.findByPrimaryKey("MarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
			String statusId = String.valueOf(marketingCampaign.get("statusId"));

			GenericValue mailerMarketingCampaign = marketingCampaign.getRelatedOne("MailerMarketingCampaign").getRelatedOne("MergeForm");
			String scheduleAt = String.valueOf(mailerMarketingCampaign.get("scheduleAt"));

			System.out.println("\nmarketingCampaignId:" + marketingCampaignId + "\nstatusId:" + statusId + "\nscheduleAt:" + scheduleAt);
			insertIntoMailerCampaignStatus(delegator, contactListId, recipientId, marketingCampaignId, statusId, scheduleAt);
		}
	}

	protected static void insertIntoMailerCampaignStatus(GenericDelegator delegator, String contactListId, String recipientId, String marketingCampaignId, String statusId, String scheduleAt) throws GenericEntityException {
		String campaignStatusId = delegator.getNextSeqId("MailerCampaignStatus");
		delegator.create("MailerCampaignStatus", UtilMisc.toMap("campaignStatusId", campaignStatusId, "recipientId", recipientId, "contactListId", contactListId, "marketingCampaignId", marketingCampaignId, "statusId", statusId, "scheduledForDate", new Timestamp(new Date().getTime())));
	}
}
