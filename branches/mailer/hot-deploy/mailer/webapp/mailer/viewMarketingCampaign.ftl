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
<#if marketingCampaign.statusId?exists && marketingCampaign.templateId?exists>
	<#if marketingCampaign.statusId == "MKTG_CAMP_INPROGRESS">
		<#assign execute>
			<a class='subMenuButton' href='<@ofbizUrl>executeCampaign?marketingCampaignId=${marketingCampaign.marketingCampaignId}</@ofbizUrl>'>
				${uiLabelMap.ButtonExecute}
			</a>
		</#assign>
	</#if>
</#if>
<#if hasUpdatePermission?exists && marketingCampaign.statusId != "MKTG_CAMP_CANCELLED">
	<#assign updateLink>
		<a class='subMenuButton' href='updateMarketingCampaignForm?marketingCampaignId=${marketingCampaign.marketingCampaignId}'>${uiLabelMap.ButtonEdit}</a>
	</#assign>
</#if>

<div class="subSectionHeader" style="padding:11px;">
  <div style="width:70%; float:left;">${uiLabelMap.CrmMarketingCampaign}</div>
  <div class="subMenuBar" class="subMenuBar" style="width:30%;float:left; margin-top: -5px;">${execute?if_exists}${updateLink?if_exists}</div>
</div>
