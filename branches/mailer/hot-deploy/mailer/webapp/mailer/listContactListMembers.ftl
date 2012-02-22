<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<#if hasUpdatePermission?exists>
	<#assign removeLink>
		<input type="submit" value="${uiLabelMap.ButtonRemoveSelected}" name="submitButton" class="subMenuButton">
	</#assign>
</#if>

<form name="removeMembersFromList" id='removeMembersFromList' method="post" action="<@ofbizUrl>removeMembersFromList</@ofbizUrl>">
	<input type="hidden" name="_useRowSubmit" value="Y"/>
	<a name="ListContactListParties"></a>
	<div class="subSectionHeader">
		<div class="subSectionTitle">${uiLabelMap.CrmContactListParties}</div>
		<div class="subMenuBar">
			<a class="subMenuButton" href="importContactListForm?contactListId=${contactList.contactListId}">${uiLabelMap.ButtonImportContacts}</a>${removeLink?if_exists}
		</div>
	</div>
	<table class="listTable">
		<tr class="listTableHeader" style="border:none">
			<#list contactListHeaders as contactListHeader>
				<th align="left">${contactListHeader}</th>
			</#list>
			<th align="left">${uiLabelMap.ImportByHeader}</th>
			<th align="left">${uiLabelMap.ImportOnHeader}</th>
			<th align="left">
				<input name="selectAll" value="Y" onclick="javascript:toggleAll(this, 'removeMembersFromList');" type="checkbox">
			</th>
		</tr>
		<#list contactListMembers as contactListMember>
			<tr class="${tableRowClass(contactListMember_index)}">
				<#list contactListFields as contactListField>
					<td>${contactListMember.get(contactListField)?if_exists}</td>
				</#list>
				<td>${contactListMember.importedByUserLogin?if_exists}</td>
				<td>${contactListMember.importedOnDateTime?if_exists?string("dd/MM/yyyy")}</td>
				<td><input type="checkbox" name="_rowSubmit_o_${contactListMember_index}" value=""/></td>
			</tr>
		</#list>
	</table>
</form>