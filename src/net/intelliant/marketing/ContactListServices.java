package net.intelliant.marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.util.FastList;
import net.intelliant.imports.UtilImport;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityUtil;
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

	@SuppressWarnings("unchecked")
	public static Map<String, Object> importContactList(DispatchContext dctx, Map<String, ? extends Object> context) {
		String fileFormat = "EXCEL";
		String fileName = (String) context.get("_uploadedFile_fileName");

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String importMapperId = (String) context.get("importMapperId");
		String excelFilePath = getUploadPath() + fileName;
		String contactListId = (String) context.get("contactListId");
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
				return createRecords(dctx.getDelegator(), mailerImportMapper, userLogin.getString("userLoginId"), contactListId, excelFilePath);
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
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> createRecords(GenericDelegator delegator, GenericValue mailerImportMapper, String userLoginId, String contactListId, String excelFilePath) throws GenericEntityException, FileNotFoundException, IOException {
		int rowIndex = 0;
		int totalCount = 0;
		int failureCount = 0;
		String ofbizEntityName = mailerImportMapper.getString("ofbizEntityName");
		String importMapperId = mailerImportMapper.getString("importMapperId");
		String isFirstRowHeader = mailerImportMapper.getString("isFirstRowHeader");
		Map<Integer, String> failureReport = new LinkedHashMap<Integer, String>();
		Map<String, Object> columnMappings = UtilImport.getColumnMappings(delegator, importMapperId);
		HSSFWorkbook excelDocument = new HSSFWorkbook(new FileInputStream(excelFilePath));
		HSSFSheet excelSheet = excelDocument.getSheetAt(0);
		Iterator<HSSFRow> excelRowIterator = excelSheet.rowIterator();		
		if (isFirstRowHeader.equalsIgnoreCase("Y")) {
			if (excelRowIterator.hasNext()) {
				excelRowIterator.next();
				rowIndex++;
			}
		}
		while (excelRowIterator.hasNext()) {
			try {
				TransactionUtil.begin();

				String recipientId = insertIntoConfiguredCustomEntity(delegator, userLoginId, ofbizEntityName, excelRowIterator.next(), columnMappings);
				createCLRecipientRelation(delegator, contactListId, recipientId);
				createAndScheduleCampaigns(delegator, contactListId, recipientId);
				totalCount++;

				TransactionUtil.commit();
			} catch (GenericEntityException gee) {
				TransactionUtil.rollback();
				failureReport.put(rowIndex++, "Reason - " + gee.getMessage());
				failureCount++;
			}
		}
		Map<String, Object> results = ServiceUtil.returnSuccess(); 
		results.put("totalCount", totalCount);
		results.put("failureCount", failureCount);
		results.put("failureReport", failureReport);
		return results;
	}

	@SuppressWarnings("unchecked")
	private static String insertIntoConfiguredCustomEntity(GenericDelegator delegator, String userLoginId, String entityName, HSSFRow excelRowData, Map<String, Object> columnMapper) throws GenericEntityException {
		String entityPrimaryKeyField = delegator.getModelEntity(entityName).getFirstPkFieldName();
		String entityPrimaryKey = delegator.getNextSeqId(entityName);
		Map<String, Object> rowData = UtilMisc.toMap(entityPrimaryKeyField, entityPrimaryKey);
		rowData.put("importedOnDateTime", UtilDateTime.nowTimestamp());
		rowData.put("importedByUserLogin", userLoginId);
		
		Set<String> keys = columnMapper.keySet();
		for (String key : keys) {
			rowData.put(key, excelRowData.getCell(Short.parseShort(String.valueOf(columnMapper.get(key)))).toString());
		}
		delegator.create(entityName, rowData);
		return entityPrimaryKey;
	}
	
	private static void createCLRecipientRelation(GenericDelegator delegator, String contactListId, String recipientId) throws GenericEntityException {
		delegator.create("MailerRecipientContactList", UtilMisc.toMap("contactListId", contactListId, "recipientId", recipientId));
	}

	@SuppressWarnings("unchecked")
	private static void createAndScheduleCampaigns(GenericDelegator delegator, String contactListId, String recipientId) throws GenericEntityException {
		List<GenericValue> rowsToInsert = FastList.newInstance();
		EntityConditionList conditions = new EntityConditionList(UtilMisc.toList(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId), EntityUtil.getFilterByDateExpr()), EntityOperator.AND);
		List<String> selectColumns = UtilMisc.toList("contactListId", "marketingCampaignId");
		List<GenericValue> rows = delegator.findByCondition("MailerMarketingCampaignAndContactList", conditions, selectColumns, UtilMisc.toList("fromDate"));

		for (GenericValue row : rows) {
			GenericValue rowToInsertGV = createAndScheduleCampaign(delegator, row.getString("marketingCampaignId"), contactListId, recipientId);
			if (UtilValidate.isNotEmpty(rowToInsertGV)) {
				rowsToInsert.add(rowToInsertGV);
			}
		}
		if (UtilValidate.isNotEmpty(rowsToInsert)) {
			delegator.storeAll(rowsToInsert);
		}
	}
	
	private static GenericValue createAndScheduleCampaign(GenericDelegator delegator, String marketingCampaignId, String contactListId, String recipientId) throws GenericEntityException {
		GenericValue marketingCampaign = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		GenericValue configuredTemplate = marketingCampaign.getRelatedOne("MergeForm");
		if (UtilValidate.isNotEmpty(configuredTemplate)) {
			String scheduleAt = configuredTemplate.getString("scheduleAt");
			Timestamp scheduledForDate = UtilDateTime.nowTimestamp();
			if (UtilValidate.isNotEmpty(scheduleAt)) {
				// scheduledForDate = Do something here.
			}
			GenericValue rowToInsertGV = delegator.makeValue("MailerCampaignStatus");
			rowToInsertGV.put("campaignStatusId", delegator.getNextSeqId(rowToInsertGV.getEntityName()));
			rowToInsertGV.put("recipientId", recipientId);
			rowToInsertGV.put("contactListId", contactListId);
			rowToInsertGV.put("marketingCampaignId", marketingCampaignId);
			rowToInsertGV.put("printStatusId", "MAILER_SCHEDULED");
			rowToInsertGV.put("emailStatusId", "MAILER_SCHEDULED");
			rowToInsertGV.put("scheduledForDate", scheduledForDate);
			return rowToInsertGV;
		} else {
			Debug.logError("No configured template for Marketing campaign [" + marketingCampaignId + "]", MODULE);
		}
		return null;
	}
	
	public static Map<String, Object> scheduleCampaignsForListMembers(DispatchContext dctx, Map<String, ? extends Object> context) {
		String contactListId = (String) context.get("contactListId");
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		List<GenericValue> rowsToInsert = FastList.newInstance();
		try {
			List<GenericValue> members = dctx.getDelegator().findByAnd("MailerRecipientContactList", UtilMisc.toMap("contactListId", contactListId));
			if (UtilValidate.isNotEmpty(members)) {
				for (GenericValue member : members) {
					GenericValue rowToInsertGV = createAndScheduleCampaign(dctx.getDelegator(), marketingCampaignId, contactListId, member.getString("recipientId"));
					if (UtilValidate.isNotEmpty(rowToInsertGV)) {
						rowsToInsert.add(rowToInsertGV);
					}
				}
				if (UtilValidate.isNotEmpty(rowsToInsert)) {
					dctx.getDelegator().storeAll(rowsToInsert);
				}
			} else {
				if (Debug.infoOn()) {
					Debug.logInfo("No recipients found for contact list [" + contactListId + "]", MODULE);
				}
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError("Encountered errors while scheduling campaigns.", MODULE);
		}
		return ServiceUtil.returnSuccess();
	}
}
