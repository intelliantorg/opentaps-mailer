package net.intelliant.marketing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import javolution.util.FastList;

import net.intelliant.util.UtilCommon;
import net.intelliant.util.XslFoConversion;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.UtilXml;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.webapp.view.ApacheFopFactory;
import org.opentaps.common.template.freemarker.FreemarkerUtil;
import org.w3c.dom.Document;

import freemarker.template.TemplateException;

public class CampaignExecutionEvents {
	private static final String module = CampaignExecutionEvents.class.getName();
	private static final String errorResource = "ErrorLabels";
	
	@SuppressWarnings("unchecked")
	public static String executeCampaign(HttpServletRequest request, HttpServletResponse response) {
		boolean transaction = false;
		EntityListIterator iterator = null;
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
					long count = UtilCommon.countAllCampaignLinesPendingTillDate(delegator, marketingCampaignId, null);
					if (count <= 0) {
						UtilCommon.addErrorMessage(request, errorResource, "errorExecutingCampaignNoScheduledCampaigns");
						return "success";
					}
					GenericValue templateGV = mailerMarketingCampaign.getRelatedOne("MailerMergeForm");
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
							if (Debug.infoOn()) {
								Debug.logInfo("[executePDFCampaign] will be generating PDF..", module);
							}
							StringBuilder xhtmls = new StringBuilder().append("<htmls>");
							String pdfTemplate = templateGV.getString("mergeFormText");
							String pdfHeaderImage = templateGV.getString("headerImageLocation");
							String pdfFooterImage = templateGV.getString("footerImageLocation");
							XslFoConversion conversion = new XslFoConversion();
							if (UtilValidate.isNotEmpty(pdfHeaderImage)) {
								conversion.setParameter("headerImage", pdfHeaderImage);
							}
							if (UtilValidate.isNotEmpty(pdfFooterImage)) {
								conversion.setParameter("footerImage", pdfFooterImage);
							}
							String stylesheetLocation = UtilProperties.getPropertyValue("mailer", "mailer.formMerge.stylesheetLocation");
				            EntityCondition conditions = new EntityConditionList( 
			            		UtilMisc.toList(
			        				new EntityExpr("statusId", EntityOperator.EQUALS, "MAILER_SCHEDULED"),
			        				new EntityExpr("marketingCampaignId", EntityOperator.EQUALS, marketingCampaignId),
			                        new EntityExpr("scheduledForDate", EntityOperator.LESS_THAN_EQUAL_TO, UtilDateTime.nowTimestamp())
			            		), EntityOperator.AND
			            	);
				            if (Debug.infoOn()) {
				            	Debug.logInfo("[executePDFCampaign] The conditions >> " + conditions, module);
				            }
							EntityFindOptions options = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
							transaction = TransactionUtil.begin();
							iterator = delegator.findListIteratorByCondition("MailerCampaignStatus", conditions, null, null, null, options);
					        GenericValue mailerCampaignStatusGV = null;
//							Iterate over each scheduled mailer,
					        List<String> campaignStatusIds = FastList.newInstance();
					        while ((mailerCampaignStatusGV = (GenericValue) iterator.next()) != null) {
					        	campaignStatusIds.add(mailerCampaignStatusGV.getString("campaignStatusId"));
					        	GenericValue relatedRecipientGV = mailerCampaignStatusGV.getRelatedOne("MailerRecipient");
					        	StringWriter emailBodyContent = new StringWriter();
					        	FreemarkerUtil.renderTemplateWithTags("relatedRecipientGV#" + relatedRecipientGV.getString("recipientId"), pdfTemplate, relatedRecipientGV.getAllFields(), emailBodyContent, false, false);
					        	xhtmls.append(conversion.convertHtml2Xhtml(emailBodyContent.toString()));
				            }
					        if (UtilValidate.isNotEmpty(campaignStatusIds)) {
					        	String reportType = "application/pdf";
						        xhtmls.append("</htmls>");
						        Document xslfo = conversion.convertXHtml2XslFo(xhtmls.toString(), stylesheetLocation);
						        String xslfoAsString = UtilXml.writeXmlDocument(xslfo);
					        	
					        	ByteArrayOutputStream out = new ByteArrayOutputStream();
					        	FopFactory fopFac = ApacheFopFactory.instance();
			                    if (Debug.infoOn()) {
			                    	Debug.logInfo("PDF renderer render() using base URL >> " + request.getAttribute("_SERVER_ROOT_URL_"), module);
			                    	Debug.logInfo("[executePDFCampaign] using stylesheet >> " + stylesheetLocation, module);
							        Debug.logInfo("[executePDFCampaign] xhtmls >> " + xhtmls, module);
							        Debug.logInfo("[executePDFCampaign] xslfoAsString >> " + xslfoAsString, module);
			                    }
			                    fopFac.setBaseURL((String)request.getAttribute("_SERVER_ROOT_URL_"));
			                    Fop fop = fopFac.newFop(reportType, out);
			                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
			                    Source source = new StreamSource(new StringReader(xslfoAsString));
			                    Result result = new SAXResult(fop.getDefaultHandler());
			                    transformer.transform(source, result);
			                    fopFac.getImageFactory().clearCaches();
			                    response.setContentType(reportType);
			                    response.setContentLength(out.size());
			                    response.setHeader("Content-Disposition", String.format("attachment; filename=Campaign_%1$s.pdf", mailerMarketingCampaign.getString("marketingCampaignId")));
			                    out.writeTo(response.getOutputStream());
			                    response.getOutputStream().flush();
			                    dispatcher.runSync("mailer.markMailersAsExecuted", UtilMisc.toMap("campaignStatusIds", UtilMisc.toList(campaignStatusIds)));
			                    return "pdfGenerationSuccess"; /** required to prevent "java.lang.IllegalStateException: getOutputStream() has already been called for this response" */
					        }
						}
					} else {
						Debug.logError(String.format("[executeCampaign] To template configured on campaign with Id [%1$s]", marketingCampaignId), module);
						UtilCommon.addErrorMessage(request, errorResource, "errorNoTemplateConfiguredOnCampaign");
						return "error";
					}
				}
			} catch (GenericEntityException e) {
				Debug.logError(String.format("[executeCampaign] Invalid campaign with Id [%1$s]", marketingCampaignId), module);
				Debug.logError(e, module);
				UtilCommon.addErrorMessage(request, errorResource, "errorInvalidCampaign");
				return "error";
			} catch (TemplateException e) {
				Debug.logError(e, module);
				return "error";
			} catch (IOException e) {
				Debug.logError(e, module);
				return "error";
			} catch (FOPException e) {
				Debug.logError(e, module);
				return "error";
			} catch (TransformerException e) {
				Debug.logError(e, module);
				return "error";
			} catch (GenericServiceException e) {
				Debug.logError(e, module);
				return "error";
			} finally {
				if (iterator != null) {
					try {
						iterator.close();
						if (transaction) {
							TransactionUtil.commit(transaction);
						}
					} catch (GenericEntityException e) {
						iterator = null;
					}
				}
			}
        }
		return "success";
	}
}