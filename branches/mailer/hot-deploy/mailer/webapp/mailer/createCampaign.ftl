<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<div class="allSubSectionBlocks">
	<div class="form">
		<form class="basic-form" id="createMarketingCampaignForm" name="createMarketingCampaignForm" action="<@ofbizUrl>createMarketingCampaign</@ofbizUrl>" method="post">
			<table width="100%">
				<tr>
					<td>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.CommonName />
							</div>
							<div class="fieldContainer">
								<@inputText name="campaignName" size=35 class="inputBox required"/>
							</div>							
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.LabelFromEmailAddress />
							</div>
							<div class="fieldContainer">
								<@inputText name="fromEmailAddress" size=35 class="inputBox required email"/>
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonFrom />
							</div>
							<div class="fieldContainer">
								<@inputDateTime name="fromDate" />
							</div>							
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.LabelTemplateId />
							</div>
							<div class="fieldContainer">
								<@inputSelect name="templateId" list=mergeFormList key="mergeFormId" displayField="mergeFormName" required=false  class="dropDown required" />						
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonThru />
							</div>
							<div class="fieldContainer">
								<@inputDateTime name="thruDate" />
							</div>					
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.LabelContactList />
							</div>
							<div class="fieldContainer">
								<#assign orderBy = Static["org.ofbiz.base.util.UtilMisc"].toList("contactListName")>
								<#assign templateItems = delegator.findAll("ContactList",orderBy)>
								<@inputSelect name="contactListId" list=templateItems key="contactListId" displayField="contactListName" required=true />								
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonDescription />
							</div>
							<div class="fieldContainer">
								<@inputTextarea name="description" rows=4 cols=33 />
							</div>							
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.CommonStatus />
							</div>
							<div class="fieldContainer" style="padding: 2px;">
								<@inputStatusItemSelect list=statusItems defaultStatusId="MKTG_CAMP_PLANNED" class="dropDown required"/>
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">&nbsp;</div>
							<div class="fieldContainer">
								<@inputSubmit title=uiLabelMap.CrmCreateMarketingCampaign onClick="" class=smallSubmit />
							</div>
						</div>					
					</td>
				</tr>
			</table>			
		</form>
	</div>
</div>