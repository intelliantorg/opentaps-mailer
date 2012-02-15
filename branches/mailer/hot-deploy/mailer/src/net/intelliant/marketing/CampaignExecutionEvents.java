package net.intelliant.marketing;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.intelliant.util.UtilCommon;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

public class CampaignExecutionEvents {
	private static final String module = CampaignExecutionEvents.class.getName();
	private static final String errorResource = "ErrorLabels";
	
	@SuppressWarnings("unchecked")
	public static String executeCampaign(HttpServletRequest request, HttpServletResponse response) {
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");
        String marketingCampaignId = request.getParameter("marketingCampaignId");
        GenericValue userLogin = org.opentaps.common.util.UtilCommon.getUserLogin(request);
        if (UtilValidate.isEmpty(marketingCampaignId)) {
        	UtilCommon.addErrorMessage(request, errorResource, "errorNoCampaignSpecified");
        	return "error";
        } else {
        	try {
				GenericValue mailerMarketingCampaign = delegator.findByPrimaryKey("MailerMarketingCampaign", UtilMisc.toMap("marketingCampaignId", marketingCampaignId));
				if (Debug.infoOn()) {
					Debug.logError("[executeCampaign] Found this campaign >> " + mailerMarketingCampaign, module);
				}
				if (UtilValidate.isEmpty(mailerMarketingCampaign)) {
					Debug.logError(String.format("[executeCampaign] No campaign with Id [%1$s]", marketingCampaignId), module);
					UtilCommon.addErrorMessage(request, errorResource, "errorInvalidCampaign");
					return "error";
				} else {
					GenericValue templateGV = mailerMarketingCampaign.getRelatedOne("MergeForm");
					if (UtilValidate.isNotEmpty(templateGV)) {
						if (UtilCommon.isEmailTemplate(templateGV)) {
							Map<String, Object> serviceInputs = UtilMisc.toMap("userLogin", userLogin);
							serviceInputs.put("marketingCampaignId", marketingCampaignId);
							try {
								Map<String, Object> results = dispatcher.runSync("mailer.executeEmailMailers", serviceInputs);
								if (ServiceUtil.isError(results)) {
									Debug.logError(String.format("[executeCampaign] Error executing campaign with Id [%1$s]", marketingCampaignId), module);
									UtilCommon.addErrorMessage(request, errorResource, "errorExecutingEmailCampaign");
									return "error";
								} else {
									ServiceUtil.setMessages(request, null, (String) results.get(ModelService.SUCCESS_MESSAGE), null);
								}								
							} catch (GenericServiceException e) {
								Debug.logError(String.format("[executeCampaign] Error executing campaign with Id [%1$s]", marketingCampaignId), module);
								UtilCommon.addErrorMessage(request, errorResource, "errorExecutingEmailCampaign");
								return "error";
							}
						} else if (UtilCommon.isPrintTemplate(templateGV)) {
							
						}
					} else {
						Debug.logError(String.format("[executeCampaign] To template configured on campaign with Id [%1$s]", marketingCampaignId), module);
						UtilCommon.addErrorMessage(request, errorResource, "errorNoTemplateConfiguredOnCampaign");
						return "error";
					}
				}
			} catch (GenericEntityException e) {
				Debug.logError(String.format("[executeCampaign] Invalid campaign with Id [%1$s]", marketingCampaignId), module);
				UtilCommon.addErrorMessage(request, errorResource, "errorInvalidCampaign");
				return "error";
			}
        }
		return "success";
	}
}