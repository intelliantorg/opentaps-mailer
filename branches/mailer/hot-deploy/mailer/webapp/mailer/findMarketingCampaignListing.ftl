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
<div class="subSectionHeader">
	<div class="">${uiLabelMap.MarketingCampaignHeader}</div>
	<div class="subMenuBar"></div>
</div>
<div>
	<div style="text-align:right;">
		<span id="message" class="tabletext" style="color:red;font-size: 12px;"></span>
	</div>
	<form action="" name="executeBulkCampaign" method="post">
		<table class="crmsfaListTable">
			<tr class="crmsfaListTableHeader">
				<td><span class="tableheadtext"><a class="orderByHeaderLink" href="${listSortTarget}?campaignsOrderBy=campaignName${findParams}#ListMarketingCampaigns">${uiLabelMap.CommonName}<a></span></td>
				<td><span class="tableheadtext"><a class="orderByHeaderLink" href="${listSortTarget}?campaignsOrderBy=statusId${findParams}#ListMarketingCampaigns">${uiLabelMap.CommonStatus}</a></span></td>
				<td><span class="tableheadtext">${uiLabelMap.CommonFrom}</span></td>
				<td><span class="tableheadtext">${uiLabelMap.CommonThru}</span></td>
				<td><span class="tableheadtext">${uiLabelMap.LabelNextRun}</span></td>
				<#list mailerCampaignStatusList as mailerCampaignStatusListItem>
					<td><span class="tableheadtext">${mailerCampaignStatusListItem.description}</span></td>
				</#list>
				<td><span class="tableheadtext">${uiLabelMap.ActionHeader}</span></td>
			</tr>
			<#assign contactListId = "" />
			<#list campaignsListIt as campaignsListItem>
				<tr class="${tableRowClass(campaignsListItem_index)}">
					<td>
						<a class="linktext" href="viewMarketingCampaign?marketingCampaignId=${campaignsListItem.marketingCampaignId}">
							${campaignsListItem.campaignName?default("")} (${campaignsListItem.marketingCampaignId})
						</a>
					</td>
					<td><span class="tabletext">${campaignsListItem.description?default("")}</span></td>
					<td><span class="tabletext"><@displayDate date=campaignsListItem.fromDate?default("") /></span></td>
					<td><span class="tabletext"><@displayDate date=campaignsListItem.thruDate?default("") /></span></td>
					<td>
						<#assign statusCount = Static["net.intelliant.util.UtilCommon"].countAllCampaignLinesPendingTillDate(delegator, campaignsListItem.marketingCampaignId, contactListId)>
						<span class="tabletext">${statusCount?default("0")}</span>
					</td>
					<#list mailerCampaignStatusList as mailerCampaignStatusListItem>
						<#assign statusCount = Static["net.intelliant.util.UtilCommon"].countCampaignLines(delegator, mailerCampaignStatusListItem.statusId, contactListId, campaignsListItem.marketingCampaignId)>
						<td>
							<span class="tabletext">${statusCount?default("0")}</span>
						</td>
					</#list>		
					<td>
						<span class="tabletext">			
							<#if campaignsListItem.statusId?exists && campaignsListItem.templateId?exists && (campaignsListItem.statusId == "MKTG_CAMP_INPROGRESS")>
								<a class='subMenuButton' href='<@ofbizUrl>executeCampaignFromFind?marketingCampaignId=${campaignsListItem.marketingCampaignId}</@ofbizUrl>'>
									${uiLabelMap.ButtonExecute}
								</a>
							</#if>
						</span>
					</td>
				</tr>
			</#list>
			<#if campaignsListIt?exists>
				${campaignsListIt.close()}
			</#if>
		</table>
	</form>
</div>