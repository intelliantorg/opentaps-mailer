<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="allSubSectionBlocks">
	<div class="form">
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
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.CommonStatus />
							</div>
							<div class="fieldContainer" style="padding: 2px;">
								<#if isCreateMode>
									<@inputStatusItemSelect list=statusItems defaultStatusId="MKTG_CAMP_PLANNED" class="dropDown required"/>
								<#else>
									<select name="statusId" class="dropDown required">
									    <option value="${marketingCampaign.statusId}" selected="selected">${currentStatus.description}</option>
									    <option value="${marketingCampaign.statusId}">---</option>
									    <#list allowedTransitions as allowedTransition>
									        <option value="${allowedTransition.statusIdTo}">${allowedTransition.transitionName}</option>
									    </#list>
									</select>
								</#if>															
							</div>
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