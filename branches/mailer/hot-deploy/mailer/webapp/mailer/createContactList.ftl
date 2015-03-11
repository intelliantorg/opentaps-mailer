<#--
 * Copyright (c) Intelliant
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (iaerp@intelliant.net)
 *
-->
<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<div class="allSubSectionBlocks">
	<div class="form findcampaign1">
		<form class="basic-form" id="createMarketingCampaignForm" name="createMarketingCampaignForm" action="<@ofbizUrl>createContactList</@ofbizUrl>" method="post">
			<@inputHidden name="contactListTypeId" value="MARKETING" />
			<table width="100%">
				<tr>
					<td>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.CommonName />
							</div>
							<div class="fieldContainer">
								<@inputText name="contactListName" size=35 class="inputBox required"/>
							</div>
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.LabelContactType />
							</div>
							<div class="fieldContainer">
								<#assign emailAddress = Static["org.ofbiz.base.util.UtilMisc"].toMap("contactMechTypeId", "EMAIL_ADDRESS", "description", "Email Address")>							
								<#assign postalAddress = Static["org.ofbiz.base.util.UtilMisc"].toMap("contactMechTypeId", "POSTAL_ADDRESS", "description", "Postal Address")>
								<#assign phoneNumber = Static["org.ofbiz.base.util.UtilMisc"].toMap("contactMechTypeId", "TELECOM_NUMBER", "description", "Phone Number")>
								<#assign contactMechTypeItems = [emailAddress, postalAddress, phoneNumber]>								
								<@inputSelect name="contactMechTypeId" list=contactMechTypeItems displayField="description" key="contactMechTypeId" />            
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
								<@display class="tableheadtext" text=uiLabelMap.LabelMarketingCampaign />
							</div>
							<div class="fieldContainer">
								<@inputSelect name="marketingCampaignId" list=marketingCampaignList displayField="campaignName" key="marketingCampaignId" required=false />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">&nbsp;</div>
							<div class="fieldContainer">
								<@inputSubmit title=uiLabelMap.ButtonCreateContactList onClick="" class=smallSubmit />
							</div>
						</div>					
					</td>
				</tr>
			</table>			
		</form>
	</div>
</div>