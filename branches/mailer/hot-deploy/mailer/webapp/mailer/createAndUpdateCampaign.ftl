<#--
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
 *
-->
<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="allSubSectionBlocks">
	<div class="form findcampaign1">
		<#assign isCreateMode = false />
		<#if !marketingCampaign?exists>
			<#assign isCreateMode = true />
			<#assign marketingCampaign=Static["org.ofbiz.base.util.UtilMisc"].toMap("", "") />
			<#assign actionUrl>
				<@ofbizUrl>createMarketingCampaign</@ofbizUrl>
			</#assign>
		<#else>
			<#assign actionUrl>
				<@ofbizUrl>updateMarketingCampaign</@ofbizUrl>
			</#assign>				
		</#if>
		<form class="basic-form" id="createOrUpdateMarketingCampaignForm" name="createOrUpdateMarketingCampaignForm" action="${actionUrl}" method="post">
			<#if !isCreateMode>
				<input type="hidden" value="${marketingCampaign.marketingCampaignId?if_exists}" name="marketingCampaignId" />
			</#if>
			<table width="100%">
				<tr>
					<td>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.CommonName />
							</div>
							<div class="fieldContainer">
								<@inputText name="campaignName" size=35 class="inputBox required" default=marketingCampaign.campaignName?if_exists />
							</div>
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.LabelTemplateId />
							</div>
							<div class="fieldContainer">
								<@inputSelect name="templateId" default=marketingCampaign.templateId?if_exists list=mergeFormList key="mergeFormId" displayField="mergeFormName" required=false class="dropDown required" />						
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.CommonFrom />
							</div>
							<div class="fieldContainer">
								<@inputDateTime default=marketingCampaign.fromDate?if_exists name="fromDate" class="inputBox required" />
								<label for="fromDate_c_date" generated="true" class="error" style="display:none">This field is required.</label>
							</div>
							<#if isCreateMode>
								<div class="label">
									<@display class="tableheadtext requiredField" text=uiLabelMap.LabelContactList />
								</div>
								<div class="fieldContainer">
									<#assign orderBy = Static["org.ofbiz.base.util.UtilMisc"].toList("contactListName")>
									<#assign templateItems = delegator.findAll("ContactList",orderBy)>
									<@inputSelect name="contactListId" list=templateItems key="contactListId" displayField="contactListName" required=false class="dropDown required" />								
								</div>
							<#else>
								<div class="label">
									<@display class="tableheadtext requiredField" text=uiLabelMap.CommonStatus />
								</div>
								<#assign statusChangeMessages = "{" />
								<select name="statusId" class="dropDown required" onChange="displayPreChangeMsg(this);">
							    	<option value="${marketingCampaign.statusId}" selected="selected">${currentStatus.description}</option>
							    	<option value="${marketingCampaign.statusId}">---</option>
							    	<#list allowedTransitions as allowedTransition>
							        	<option value="${allowedTransition.statusIdTo}">${allowedTransition.transitionName}</option>
							        	<#if allowedTransition.preChangeMessage?exists>
							        		<#if allowedTransition_index != 0 && statusChangeMessages?length gt 1>
							        			<#assign statusChangeMessages = ", " + statusChangeMessages />
							        		</#if>
							        		<#assign localizedMsg = Static["org.ofbiz.base.util.UtilProperties"].getMessage("UILabels", allowedTransition.preChangeMessage, locale)>
							        		<#assign statusChangeMessages = statusChangeMessages + "'${allowedTransition.statusIdTo}' : '${localizedMsg}'" />
							        	</#if>
							    	</#list>
							    	<#assign statusChangeMessages = statusChangeMessages + "}" />
								</select>
							</#if>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.CommonThru />
							</div>
							<div class="fieldContainer">
								<@inputDateTime default=marketingCampaign.thruDate?if_exists name="thruDate" class="inputBox required" />
								<label for="thruDate_c_date" generated="true" class="error" style="display:none">This field is required.</label>
							</div>
							<#if isCreateMode>
								<div class="label">
									<@display class="tableheadtext requiredField" text=uiLabelMap.CommonStatus />
								</div>
								<div class="fieldContainer" style="padding: 2px;">
									<@inputStatusItemSelect list=statusItems defaultStatusId="MKTG_CAMP_PLANNED" class="dropDown required"/>
								</div>
							</#if>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonDescription />
							</div>
							<div class="fieldContainer">
								<@inputTextarea default=marketingCampaign.description?if_exists name="description" rows=4 cols=33 />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">&nbsp;</div>
							<div class="fieldContainer">
								<#if isCreateMode>
									<@inputSubmit title=uiLabelMap.CrmCreateMarketingCampaign onClick="" class=smallSubmit />
								<#else>
									<@inputSubmit title=uiLabelMap.UpdateCampaignHeader onClick="" class=smallSubmit />
								</#if>
							</div>
						</div>					
					</td>
				</tr>
			</table>			
		</form>
	</div>
</div>
<#if !isCreateMode>
	<script type="text/javascript">
		var statusChangeMessages = ${statusChangeMessages};
		displayPreChangeMsg = function(selectedElement) {
			if (statusChangeMessages[selectedElement.value]) {
				alert(statusChangeMessages[selectedElement.value]);
			}
		}
	</script>
</#if>