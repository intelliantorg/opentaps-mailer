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
<div class="screenlet" style="  width: 235px !important;">
    <div class="screenlet-header">
    	<div class="boxhead">${uiLabelMap.CrmShortcuts}</div>
    </div>
    <div class="screenlet-body">
      <ul class="shortcuts">
        <li><a href="<@ofbizUrl>findMarketingCampaigns</@ofbizUrl>">${uiLabelMap.CrmFindMarketingCampaigns}</a></li>
        <li><a href="<@ofbizUrl>findContactLists</@ofbizUrl>">${uiLabelMap.CrmFindContactLists}</a></li>
        <#if (security.hasEntityPermission("CRMSFA_CAMP", "_CREATE", session))>
        	<li><a href="<@ofbizUrl>createMarketingCampaignForm</@ofbizUrl>">${uiLabelMap.CrmCreateMarketingCampaign}</a></li>
        	<li><a href="<@ofbizUrl>createContactListForm</@ofbizUrl>">${uiLabelMap.CrmCreateContactList}</a></li>
        	<li><a href="<@ofbizUrl>manageMergeForms</@ofbizUrl>">${uiLabelMap.CrmFormLetterTemplates}</a></li>
        </#if>
        <li><a href="<@ofbizUrl>viewImportMappings</@ofbizUrl>">${uiLabelMap.ConfiguredMappings}</a></li>
      </ul>
    </div>
</div>