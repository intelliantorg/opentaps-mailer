<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>
<div class="allSubSectionBlocks">
	<div class="form">
		<form class="basic-form" name="createMarketingCampaignForm" action="createMarketingCampaign">
			<table width="100%">
				<tr>
					<td>
						<div class="rowContainer">
							<div class="label"><span class="tableheadtext">${uiLabelMap.CommonName}</span></div>
							<div class="fieldContainer"><input class="inputBox" size="35" name="name"></div>							
							<div class="label"><span class="tableheadtext">From email</span></div>
							<div class="fieldContainer"><input class="inputBox" size="35" name="name"></div>
						</div>
						<div class="rowContainer">
							<div class="label"><span class="tableheadtext">${uiLabelMap.CommonFrom}</span></div>
							<div class="fieldContainer">
								<@inputDateTime name="fromDate" form="createMarketingCampaignForm" />
							</div>							
							<div class="label"><span class="tableheadtext">Template</span></div>
							<div class="fieldContainer">
								<select class="inputBox" style="width:180px;">
									<option value="">-- Select --</option>
								</select>
							</div>
						</div>
						<div class="rowContainer">
							<div class="label"><span class="tableheadtext">${uiLabelMap.CommonThru}</span></div>
							<div class="fieldContainer">
								<@inputDateTime name="thruDate" form="createMarketingCampaignForm" />
							</div>					
							<div class="label"><span class="tableheadtext">Contact List</span></div>
							<div class="fieldContainer"><input class="inputBox" size="35" name="name"></div>
						</div>
						<div class="rowContainer">
							<div class="label"><span class="tableheadtext">${uiLabelMap.CrmMarketingBudgetedCost}</span></div>
							<div class="fieldContainer"><input class="inputBox" name="budgetedCost"></div>
							
							<div class="label"><span class="tableheadtext">${uiLabelMap.CommonCurrency}</span></div>
							<div class="fieldContainer" style="padding: 2px;">
								<@inputCurrencySelect defaultCurrencyUomId=configProperties.defaultCurrencyUomId useDescription=true />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label"><span class="tableheadtext">${uiLabelMap.CrmMarketingEstimatedCost}</span></div>
							<div class="fieldContainer"><input class="inputBox" name="estimatedCost"></div>
						</div>
						<div class="rowContainer">
							<div class="label"><span class="tableheadtext">${uiLabelMap.CommonDescription}</span></div>
							<div class="fieldContainer"><textarea class="inputBox" name="description" rows="4" cols="40"></textarea></div>
						</div>						
						<div class="rowContainer">
							<div class="label">&nbsp;</div>
							<div class="fieldContainer"><input class="smallSubmit" value="${uiLabelMap.CrmCreateMarketingCampaign}" type="submit"></div>
						</div>					
					</td>
				</tr>
			</table>			
		</form>
	</div>
</div>