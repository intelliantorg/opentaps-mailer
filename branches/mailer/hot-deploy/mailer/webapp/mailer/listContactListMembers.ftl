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

<#assign dateFormat=Static["org.ofbiz.base.util.UtilProperties"].getMessage("mailer", "mailer.importDataDateFormat", locale)/>

<#if hasUpdatePermission?exists>
	<#assign removeLink>
		<input type="submit" value="${uiLabelMap.ButtonRemoveSelected}" name="submitButton" class="subMenuButton">
	</#assign>
</#if>

<form name="removeMembersFromList" id='removeMembersFromList' method="post" action="<@ofbizUrl>removeMembersFromList</@ofbizUrl>">
	<@inputHidden name="contactListId" value="${contactList.contactListId}" /> <#-- This is required for redirection etc. -->
	<@inputHidden name="_useRowSubmit" value="Y" />
	<@inputHidden name="_rowCount" value="${contactListMembers?if_exists?size}" />	
	<a name="ListContactListParties"></a>
	<div class="subSectionHeader" style="padding:11px;" >
		<div style="width:70%; float:left;">${uiLabelMap.CrmContactListParties}</div>
		<div class="subMenuBar" style="width:30%;float:left; margin-top: -5px;">
			<a class="subMenuButton" href="importContactListForm?contactListId=${contactList.contactListId}">${uiLabelMap.ButtonImportContacts}</a>${removeLink?if_exists}
		</div>
	</div>

	<#if (totalRecordsCount > 0)>
	    <@tableNav/>
	</#if>
	<table class="crmsfaListTable">
		<tr class="crmsfaListTableHeader" >
			<#list contactListHeaders as contactListHeader>
				<td align="left"><span class="tableheadtext">${contactListHeader}</span></td>
			</#list>
			<td align="left"><span class="tableheadtext">${uiLabelMap.ImportByHeader}</span></td>
			<td align="left"><span class="tableheadtext">${uiLabelMap.ImportOnHeader}</span></td>
			<td align="right"><span class="tableheadtext">
				<input name="selectAll" value="Y" onclick="javascript:toggleAll(this, 'removeMembersFromList');" type="checkbox"></span>
			</th>
		</tr>
		<#list contactListMembers as contactListMember>
			<@inputHidden name="contactListId_o_${contactListMember_index}" value=contactListMember.contactListId?if_exists />
			<@inputHidden name="recipientId_o_${contactListMember_index}" value=contactListMember.recipientId?if_exists />
			<@inputHidden name="recipientListId_o_${contactListMember_index}" value=contactListMember.recipientListId?if_exists />
			<tr class="${tableRowClass(contactListMember_index)}">
				<#list contactListFields as contactListField>
					<td>
						<span class="tabletext"><#if contactListFieldType.get(contactListField_index)?if_exists == "date" || contactListFieldType.get(contactListField_index)?if_exists == "date-time">
							${contactListMember.get(contactListField)?if_exists?string(dateFormat)}
						<#else>
							${contactListMember.get(contactListField)?if_exists}
						</#if>
					</span></td>
				</#list>
				<td><span class="tabletext">${contactListMember.importedByUserLogin?if_exists}</span></td>
				<td><span class="tabletext">${contactListMember.importedOnDateTime?if_exists?string(dateFormat)}</span></td>
				<td align="right"><span class="tabletext"><input type="checkbox" name="_rowSubmit_o_${contactListMember_index}" value="Y"/></span></td>
			</tr>
		</#list>
	</table>
	<#if (totalRecordsCount > 0)>
		<@tableNav/>
	</#if>
</form>