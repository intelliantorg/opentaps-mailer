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

<div class="subSectionHeader" style="padding:11px;">
	<div style="width:70%; float:left;">${uiLabelMap.ContactListHeader}</div>
	<div  class="subMenuBar" style="width:30%;float:left; margin-top: -5px;"></div>
	</div>
</div>

<table class="crmsfaListTable">
	<tr class="crmsfaListTableHeader">
		<td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.CommonName}</span></td>
		<td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.CrmContactType}</span></td>
		<td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.CrmNumberOfMembers}</span></td>
	</tr>
	<#if contactLists?exists>
		<#list contactLists as contactList>
			<#assign countRecipients = Static["net.intelliant.util.UtilCommon"].countContactListRecipients(delegator, contactList.contactListId)>
			<tr class="${tableRowClass(contactList_index)}">
				<td><a class="linktext" href="<@ofbizUrl>viewContactList</@ofbizUrl>?contactListId=${contactList.contactListId}">${contactList.contactListName} (${contactList.contactListId})</a></td>
				<td><span class="tabletext">${contactList.description?default("")}</span></td>
				<td><span class="tabletext">${countRecipients?default("0")}</span></td>
			</tr>
		</#list>
		${contactLists.close()}
	<#else>
		<tr><td colspan="4">No record found</td></tr>
	</#if>
</table>
