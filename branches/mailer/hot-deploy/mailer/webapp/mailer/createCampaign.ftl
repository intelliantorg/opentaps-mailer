<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>
<div class="allSubSectionBlocks">
	<div class="form">
		<form class="basic-form" name="createMarketingCampaignForm" action="<@ofbizUrl>createMarketingCampaign</@ofbizUrl>" method="post">
			<table width="100%">
				<tr>
					<td>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonName />
							</div>
							<div class="fieldContainer">
								<@inputText name="campaignName" size=35 />
							</div>							
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.FromEmailAddress />
							</div>
							<div class="fieldContainer">
								<@inputText name="fromEmailAddress" size=35 />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonFrom />
							</div>
							<div class="fieldContainer">
								<@inputDateTime name="fromDate" form="createMarketingCampaignForm" />
							</div>							
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.Template />
							</div>
							<div class="fieldContainer">
								<@inputText name="templateId" size=35 />	
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonThru />
							</div>
							<div class="fieldContainer">
								<@inputDateTime name="thruDate" form="createMarketingCampaignForm" />
							</div>					
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.ContactList />
							</div>
							<div class="fieldContainer">
								<@inputText name="contactListId" size=35 />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CrmMarketingBudgetedCost />
							</div>
							<div class="fieldContainer">
								<@inputText name="budgetedCost" size=35 />
							</div>
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonCurrency />
							</div>
							<div class="fieldContainer" style="padding: 2px;">
								<@inputCurrencySelect name="currencyUomId" defaultCurrencyUomId=configProperties.defaultCurrencyUomId useDescription=true />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<span class="tableheadtext">${uiLabelMap.CrmMarketingEstimatedCost}</span>
							</div>
							<div class="fieldContainer">
								<@inputText name="estimatedCost" size=35 />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonDescription />
							</div>
							<div class="fieldContainer">
								<@inputTextarea name="description" rows=4 cols=40 />
							</div>
						</div>						
						<div class="rowContainer">
							<div class="label">&nbsp;</div>
							<div class="fieldContainer">
								<@inputSubmit title=uiLabelMap.CrmCreateMarketingCampaign onClick="submitFormWithSingleClick(this)" class=smallSubmit />
							</div>
						</div>					
					</td>
				</tr>
			</table>			
		</form>
	</div>
</div>