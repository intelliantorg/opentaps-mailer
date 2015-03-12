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
package net.intelliant.marketing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javolution.util.FastList;
import net.intelliant.util.UtilCommon;
import net.intelliant.util.UtilImport;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelField;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;
import org.opentaps.common.util.UtilMessage;

public class ContactListServices {
	private static final String resource = "ErrorLabels";
	private static final String MODULE = ContactListServices.class.getName();
	private static final String dateOfOperationColumnName = UtilProperties.getPropertyValue("mailer", "mailer.dateOfOperationColumn");
	private static final DecimalFormat pattern = new DecimalFormat("#,#,#,#,#,#,#,#,#,#");

	@SuppressWarnings("unchecked")
	public static Map<String, Object> importContactList(DispatchContext dctx, Map<String, ? extends Object> context) {
		Locale locale = (Locale) context.get("locale");
		String fileName = (String) context.get("_uploadedFile_fileName");
		String fileFormat = "EXCEL";
		String mimeTypeId = (String) context.get("_uploadedFile_contentType");
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		String importMapperId = (String) context.get("importMapperId");
		String excelFilePath = UtilCommon.getUploadPath() + fileName;
		String contactListId = (String) context.get("contactListId");
		// save the file to the system using the ofbiz service
		Map<String, Object> input = UtilMisc.toMap("dataResourceId", null, "binData", context.get("uploadedFile"), "dataResourceTypeId", "LOCAL_FILE", "objectInfo", excelFilePath);
		try {
			Map<String, Object> results = dctx.getDispatcher().runSync("createAnonFile", input);
			if (ServiceUtil.isError(results)) {
				return results;
			}
			// for now we only support EXCEL format
			if ("EXCEL".equalsIgnoreCase(fileFormat) || mimeTypeId.equals("application/vnd.ms-excel")) {
				GenericValue mailerImportMapper = dctx.getDelegator().findByPrimaryKey("MailerImportMapper", UtilMisc.toMap("importMapperId", importMapperId));
				return createRecords(dctx.getDelegator(), locale, mailerImportMapper, userLogin.getString("userLoginId"), contactListId, excelFilePath);
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
		} catch (Exception e) {
			return UtilMessage.createAndLogServiceError(e, MODULE);
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> createRecords(GenericDelegator delegator, Locale locale, GenericValue mailerImportMapper, String userLoginId, String contactListId, String excelFilePath) throws GenericEntityException, FileNotFoundException, IOException {
		boolean transaction = false;
		int rowIndex = 0, totalCount = 0, failureCount = 0;
		String ofbizEntityName = mailerImportMapper.getString("ofbizEntityName");
		String importMapperId = mailerImportMapper.getString("importMapperId");
		String isFirstRowHeader = mailerImportMapper.getString("isFirstRowHeader");
		Map<String, Map<Integer, String>> failureReport = new LinkedHashMap<String, Map<Integer,String>>();
		Map<Integer, String> failureReportDetails = new LinkedHashMap<Integer, String>();
		Map<String, Object> columnMappings = UtilImport.getActiveColumnMappings(delegator, importMapperId);
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
				transaction = TransactionUtil.begin();
				rowIndex++;
				totalCount++;
				
				failureReportDetails = new HashMap<Integer, String>();
				GenericValue customEntityObj = insertIntoConfiguredCustomEntity(delegator, locale, userLoginId, ofbizEntityName, excelRowIterator.next(), columnMappings, failureReportDetails);
				String recipientId = customEntityObj.getString("recipientId");
				createCLRecipientRelation(delegator, contactListId, recipientId);
				createCampaignLines(delegator, contactListId, recipientId, customEntityObj.getDate(dateOfOperationColumnName));

			} catch (GenericEntityException gee) {
				Debug.logError(gee, MODULE);
				if (transaction) {
					TransactionUtil.rollback();
				}
				failureReport.put(String.valueOf(rowIndex - 1), failureReportDetails);
				failureCount++;
			} catch (Exception e) {
				Debug.logError(e, MODULE);
				if (transaction) {
					TransactionUtil.rollback();
				}
				failureReport.put(String.valueOf(rowIndex - 1), failureReportDetails);
				failureCount++;
			} finally {
				if (transaction) {
					TransactionUtil.commit();
				}
			}
		}
		Map<String, Object> results = ServiceUtil.returnSuccess();
		results.put("totalCount", totalCount);
		results.put("failureCount", failureCount);
		results.put("failureReport", failureReport);
		
		return results;
	}

	@SuppressWarnings("unchecked")
	private static GenericValue insertIntoConfiguredCustomEntity(GenericDelegator delegator, Locale locale, String userLoginId, String entityName, HSSFRow excelRowData, Map<String, Object> columnMapper, Map<Integer, String> errorDetails) throws GenericEntityException, ParseException {
		ModelEntity modelEntity = delegator.getModelEntity(entityName);
		String entityPrimaryKeyField = modelEntity.getFirstPkFieldName();
		String entityPrimaryKey = delegator.getNextSeqId(entityName);
		GenericValue rowToInsertGV = delegator.makeValue(entityName);
		rowToInsertGV.put(entityPrimaryKeyField, entityPrimaryKey);
		rowToInsertGV.put("importedOnDateTime", UtilDateTime.nowTimestamp());
		rowToInsertGV.put("importedByUserLogin", userLoginId);

		boolean isErrorFound = false;

		Set<Entry<String, Object>> entries = columnMapper.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			String columnName = entry.getKey();
			ModelField modelField = modelEntity.getField(columnName);
			HSSFCell excelCell = null;
			Object cellValue = null;
			short columnIndex = -1;
			try {
				columnIndex = Short.parseShort(String.valueOf(entry.getValue()));
				excelCell = excelRowData.getCell(columnIndex);
				cellValue = (excelCell != null) ? excelCell.toString() : "";
			} catch (NumberFormatException nfe) {
				cellValue = "";
			}
			if (Debug.infoOn()) {
				Debug.logInfo("[insertIntoConfiguredCustomEntity] Checking excel row No. >> " + excelRowData.getRowNum(), MODULE);
				Debug.logInfo("[insertIntoConfiguredCustomEntity] Checking excel columnIndex >> " + columnIndex, MODULE);
				if (excelCell != null) {
					Debug.logInfo("[insertIntoConfiguredCustomEntity] Checking excel column type >> " + excelCell.getCellType(), MODULE);
				} else {
					Debug.logInfo("[insertIntoConfiguredCustomEntity] excelCell found NULL for columnIndex >> " + columnIndex, MODULE);
				}
				Debug.logInfo("[insertIntoConfiguredCustomEntity] Checking model field >> " + modelField.getName(), MODULE);
				Debug.logInfo("[insertIntoConfiguredCustomEntity] Initial cellValue >> " + cellValue, MODULE);
			}
			if (modelField.getIsNotNull()) {
				if (!UtilValidate.isNotEmpty(cellValue)) {
					Map<String, Object> messageMap = UtilMisc.toMap("columnName", modelField.getDescription());
					errorDetails.put((int) columnIndex, UtilProperties.getMessage(resource, "ErrorImportMapperIsEmpty", messageMap, locale));
					isErrorFound = true;
				}
			}

			if (modelField.getType().equals("email")) {
				if (!(UtilValidate.isNotEmpty(cellValue) && UtilCommon.isValidEmailAddress(String.valueOf(cellValue)))) {
					Map<String, Object> messageMap = UtilMisc.toMap("columnName", cellValue);
					errorDetails.put((int) columnIndex, UtilProperties.getMessage(resource, "ErrorImportMapperNotValidEmail", messageMap, locale));
					isErrorFound = true;
				}
			} else if (modelField.getType().equals("tel-number")) {
				if (UtilValidate.isNotEmpty(cellValue)) {
					Map<String, Object> messageMap = UtilMisc.toMap("columnName", cellValue);
					if (excelCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						if (Debug.infoOn()) {
							Debug.logInfo("[insertIntoConfiguredCustomEntity] Cell type is numeric", MODULE);
						}
						NumberFormat testNumberFormat = NumberFormat.getNumberInstance();
						try {
							cellValue = (pattern.parse(testNumberFormat.format(excelCell.getNumericCellValue()))).longValue();
						} catch (ParseException e) {
							errorDetails.put((int) columnIndex, UtilProperties.getMessage(resource, "ErrorImportMapperNotValidPhoneNO", messageMap, locale));
							isErrorFound = true;
						}
					}
					if (!UtilValidate.isInternationalPhoneNumber(String.valueOf(cellValue))) {
						errorDetails.put((int) columnIndex, UtilProperties.getMessage(resource, "ErrorImportMapperNotValidPhoneNO", messageMap, locale));
						isErrorFound = true;
					}
				}
			} else if (modelField.getType().equals("date")) {
				try {
					cellValue = new java.sql.Date(excelCell.getDateCellValue().getTime());
				} catch (Exception e) {
					cellValue = excelCell.toString();
					Map<String, Object> messageMap = UtilMisc.toMap("columnName", cellValue);
					errorDetails.put((int) columnIndex, UtilProperties.getMessage(resource, "ErrorImportMapperNotValidDate", messageMap, locale));
					isErrorFound = true;
				}
				if (!UtilValidate.isNotEmpty(cellValue)) {
					Map<String, Object> messageMap = UtilMisc.toMap("columnName", cellValue);
					errorDetails.put((int) columnIndex, UtilProperties.getMessage(resource, "ErrorImportMapperNotValidDate", messageMap, locale));
					isErrorFound = true;
				}
			}
			if (Debug.infoOn()) {
				Debug.logInfo("[insertIntoConfiguredCustomEntity] Final cellValue >> " + cellValue, MODULE);
			}
			rowToInsertGV.put(columnName, cellValue);
		}
		if (isErrorFound) {
			throw new GenericEntityException("Errors found in spread sheet data");
		}else{
			delegator.storeAll(UtilMisc.toList(rowToInsertGV));
		}

		return rowToInsertGV;
	}

	@SuppressWarnings("unchecked")
	private static void createCLRecipientRelation(GenericDelegator delegator, String contactListId, String recipientId) throws GenericEntityException {
		String recipientListId = delegator.getNextSeqId("MailerRecipientContactList");
		Map<String, Object> values = UtilMisc.toMap("recipientListId", recipientListId, "contactListId", contactListId, "recipientId", recipientId, "validFromDate", UtilDateTime.nowTimestamp());
		delegator.create("MailerRecipientContactList", values);
	}

	@SuppressWarnings("unchecked")
	private static void createCampaignLines(GenericDelegator delegator, String contactListId, String recipientId, Date salesAndServiceDate) throws GeneralException {
		List<GenericValue> rowsToInsert = FastList.newInstance();
		EntityCondition condition1 = new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId);
		EntityCondition condition2 = EntityUtil.getFilterByDateExpr();
		EntityCondition condition3 = new EntityExpr("statusId", EntityOperator.NOT_EQUAL, "MKTG_CAMP_CANCELLED");
		/** ignore cancelled. */
		EntityConditionList conditions = new EntityConditionList(UtilMisc.toList(condition1, condition2, condition3), EntityOperator.AND);
		List<String> selectColumns = UtilMisc.toList("contactListId", "marketingCampaignId");
		List<GenericValue> rows = delegator.findByCondition("MarketingCampaignDetailsAndContactListView", conditions, selectColumns, UtilMisc.toList("fromDate"));
		for (GenericValue row : rows) {
			GenericValue rowToInsertGV = createCampaignLine(delegator, row.getString("marketingCampaignId"), contactListId, recipientId, salesAndServiceDate);
			if (UtilValidate.isNotEmpty(rowToInsertGV)) {
				rowsToInsert.add(rowToInsertGV);
			}
		}
		if (UtilValidate.isNotEmpty(rowsToInsert)) {
			delegator.storeAll(rowsToInsert);
		}
	}

	private static GenericValue createCampaignLine(GenericDelegator delegator, String marketingCampaignId, String contactListId, String recipientId, Date salesAndServiceDate) throws GeneralException {
		GenericValue mailerMarketingCampaign = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
		GenericValue marketingCampaign = mailerMarketingCampaign.getRelatedOne("MarketingCampaign");
		String marketingCampaignStatusId = marketingCampaign.getString("statusId");
		String campaignLineStatusId = "MAILER_HOLD";
		if (marketingCampaignStatusId.equals("MKTG_CAMP_INPROGRESS") || marketingCampaignStatusId.equals("MKTG_CAMP_APPROVED") || marketingCampaignStatusId.equals("MKTG_CAMP_COMPLETED")) {
			campaignLineStatusId = "MAILER_SCHEDULED";
		} else if (marketingCampaignStatusId.equals("MKTG_CAMP_PLANNED")) {
			campaignLineStatusId = "MAILER_HOLD";
		} else if (marketingCampaignStatusId.equals("MKTG_CAMP_CANCELLED")) {
			return null;
			/** No need to create for cancelled campaigns. */
		}
		GenericValue configuredTemplate = mailerMarketingCampaign.getRelatedOne("MailerMergeForm");
		if (UtilValidate.isNotEmpty(configuredTemplate)) {
			String scheduleAt = configuredTemplate.getString("scheduleAt");
			Timestamp scheduledForDate = null;
			if (UtilValidate.isNotEmpty(scheduleAt)) {
				scheduledForDate = UtilCommon.addDaysToTimestamp(new Timestamp(salesAndServiceDate.getTime()), Double.parseDouble(scheduleAt));
			} else {
				throw new GeneralException("scheduleAt must be set at Form Letter Template");
			}
			GenericValue rowToInsertGV = delegator.makeValue("MailerCampaignStatus");
			rowToInsertGV.put("campaignStatusId", delegator.getNextSeqId(rowToInsertGV.getEntityName()));
			rowToInsertGV.put("recipientId", recipientId);
			rowToInsertGV.put("contactListId", contactListId);
			rowToInsertGV.put("marketingCampaignId", marketingCampaignId);
			rowToInsertGV.put("statusId", campaignLineStatusId);
			rowToInsertGV.put("scheduledForDate", scheduledForDate);
			return rowToInsertGV;
		} else {
			Debug.logError("No configured template for Marketing campaign [" + marketingCampaignId + "]", MODULE);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> createCampaignLineForListMembers(DispatchContext dctx, Map<String, ? extends Object> context) {
		Locale locale = (Locale) context.get("locale");
		String contactListId = (String) context.get("contactListId");
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		List<GenericValue> rowsToInsert = FastList.newInstance();
		try {
			EntityCondition conditions = new EntityConditionList(UtilMisc.toList(new EntityExpr("contactListId", EntityOperator.EQUALS, contactListId), EntityUtil.getFilterByDateExpr("validFromDate", "validThruDate")), EntityOperator.AND);
			List<GenericValue> members = dctx.getDelegator().findByCondition("MailerRecipientContactList", conditions, UtilMisc.toList("recipientId"), null);
			if (UtilValidate.isNotEmpty(members)) {
				for (GenericValue member : members) {
					GenericValue mailerRecipeint = member.getRelatedOne("MailerRecipient");
					GenericValue rowToInsertGV = createCampaignLine(dctx.getDelegator(), marketingCampaignId, contactListId, member.getString("recipientId"), mailerRecipeint.getDate(dateOfOperationColumnName));
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
			return UtilMessage.createAndLogServiceError(e, "Encountered errors while scheduling campaigns.", locale, MODULE);
		} catch (GeneralException e) {
			return UtilMessage.createAndLogServiceError(e, "Encountered errors while scheduling campaigns. - " + e.getMessage(), locale, MODULE);
		}
		return ServiceUtil.returnSuccess();
	}

	/**
	 * Will be used to remove recipient from contact list and cancel
	 * non-executed mailers.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> removeRecipientFromContactList(DispatchContext dctx, Map<String, ? extends Object> context) {
		Locale locale = (Locale) context.get("locale");
		if (Debug.infoOn()) {
			Debug.logInfo("[mailer.removeRecipientFromContactList] The inputs >> " + context, MODULE);
		}
		String recipientListId = (String) context.get("recipientListId");
		try {
			GenericValue mailerRecipientContactListGV = dctx.getDelegator().findByPrimaryKey("MailerRecipientContactList", UtilMisc.toMap("recipientListId", recipientListId));
			mailerRecipientContactListGV.set("validThruDate", UtilDateTime.nowTimestamp());
			mailerRecipientContactListGV.store();
			if (Debug.infoOn()) {
				Debug.logInfo("[mailer.removeRecipientFromContactList] done with removal, going ahead with cancellations.", MODULE);
			}
			ModelService service = dctx.getModelService("mailer.cancelCreatedMailers");
			Map<String, Object> inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.putAll(service.makeValid(mailerRecipientContactListGV, ModelService.IN_PARAM));
			Map<String, Object> results = dctx.getDispatcher().runSync(service.name, inputs);
			if (ServiceUtil.isError(results)) {
				return UtilMessage.createAndLogServiceError("Encountered errors while removing recipient from list. - ", MODULE);
			}
		} catch (GenericEntityException e) {
			return UtilMessage.createAndLogServiceError(e, "Encountered errors while removing recipient from list. - " + e.getMessage(), locale, MODULE);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, "Encountered errors while removing recipient from list. - " + e.getMessage(), locale, MODULE);
		}
		return ServiceUtil.returnSuccess();
	}

	/**
	 * Will be used create contact list and setup optional association with
	 * mailer marketing campaign.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> createContactList(DispatchContext dctx, Map<String, ? extends Object> context) {
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> results;
		String marketingCampaignId = (String) context.get("marketingCampaignId");
		if (context.containsKey("marketingCampaignId")) {
			/**
			 * this to prevent opentaps mechanism of storing this in ContactList
			 * entity.
			 */
			context.remove("marketingCampaignId");
		}
		try {
			ModelService service = dctx.getModelService("createContactList");
			Map<String, Object> inputs = service.makeValid(context, ModelService.IN_PARAM);
			inputs.putAll(service.makeValid(context, ModelService.IN_PARAM));
			results = dctx.getDispatcher().runSync(service.name, inputs);
			if (ServiceUtil.isError(results)) {
				return UtilMessage.createAndLogServiceError("Encountered errors while creating contact list.", MODULE);
			}
			String contactListId = (String) results.get("contactListId");
			if (UtilValidate.isNotEmpty(marketingCampaignId)) {
				if (Debug.infoOn()) {
					Debug.logInfo("[mailer.createContactList] creating association with mailer marketing campaign.", MODULE);
				}
				service = dctx.getModelService("mailer.addContactListToCampaign");
				inputs = service.makeValid(context, ModelService.IN_PARAM);
				inputs.put("marketingCampaignId", marketingCampaignId);
				inputs.put("contactListId", contactListId);
				results = dctx.getDispatcher().runSync(service.name, inputs);
				if (ServiceUtil.isError(results)) {
					return UtilMessage.createAndLogServiceError("Encountered errors while creating contact list.", MODULE);
				}
			}
			results = ServiceUtil.returnSuccess();
			results.put("contactListId", contactListId);
		} catch (GenericServiceException e) {
			return UtilMessage.createAndLogServiceError(e, "Encountered errors while creating contact list. - " + e.getMessage(), locale, MODULE);
		}
		return results;
	}
}