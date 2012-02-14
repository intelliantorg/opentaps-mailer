package net.intelliant.util;

import java.io.File;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;

public final class UtilCommon {
	private static final String module = UtilCommon.class.getName();
	private UtilCommon() {}
	
	public static String parseHtmlAndGenerateCompressedImages(String html) {
		if (UtilValidate.isEmpty(html)) {
			return html;
		}
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements jpgs = doc.select("img[src~=(?i)\\.(jpg|jpeg)]");
		if (jpgs != null && jpgs.size() > 0) {
			for (Element jpg : jpgs) {
				if (Debug.infoOn()) {
					Debug.logInfo("Current image >> " + jpg.attr("src"), module);
				}
			}
		}
		return html;
	}

	/**
	 * Gets the path for uploaded files.
	 * 
	 * @return a <code>String</code> value
	 */
	public static String getUploadPath() {
		return System.getProperty("user.dir") + File.separatorChar + "runtime" + File.separatorChar + "data" + File.separatorChar;
	}
	
	@SuppressWarnings("unchecked")
	private static long countCampaignLines(GenericDelegator delegator, String statusId, String contactListId, String marketingCampaignId) throws GenericEntityException {
		Map<String, Object> conditions = UtilMisc.toMap("statusId", statusId);
		if (UtilValidate.isNotEmpty(contactListId)) {
			conditions.put("contactListId", contactListId);
		}
		if (UtilValidate.isNotEmpty(marketingCampaignId)) {
			conditions.put("marketingCampaignId", marketingCampaignId);
		}
		return delegator.findCountByAnd("MailerCampaignStatus", conditions);
	}
	
	public static long countScheduledCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, "MAILER_SCHEDULED", contactListId, marketingCampaignId);
	}
	
	public static long countCancelledCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, "MAILER_CANCELLED", contactListId, marketingCampaignId);
	}
	
	public static long countExecutedCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, "MAILER_EXECUTED", contactListId, marketingCampaignId);
	}
	
	public static long countOnHoldCampaignLines(GenericDelegator delegator, String contactListId, String marketingCampaignId) throws GenericEntityException {
		return countCampaignLines(delegator, "MAILER_HOLD", contactListId, marketingCampaignId);
	}
}
