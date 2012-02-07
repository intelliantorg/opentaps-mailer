<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="subSectionHeader">
	<div class="subSectionTitle">Configured Mappings</div>
	<div class="subMenuBar"><a class="subMenuButton" href="#?contactListId=###">Remove Selected</a><a class="subMenuButton" href="importContactListForm?contactListId=###">Import List</a>
	</div>
</div>

<table class="listTable">
	<tr class="listTableHeader" style="border:none">
		<td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.CommonName}</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.CrmContactType}</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.CrmNumberOfMembers}</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.Select}</td>
	</tr>
	<#if contactLists?exists>
		<#list contactLists as contactList>
			<tr class="${tableRowClass(contactList_index)}">
				<td><a href="<@ofbizUrl>viewContactList</@ofbizUrl>?contactListId=${contactList.contactListId}">${contactList.contactListName} (${contactList.contactListId})</a></td>
				<td>${contactList.contactListId}</td>
				<td>${contactList.recipientId}</td>
				<td><input type="checkbox" name="select" value="${contactList.contactListId}" /></td>
			</tr>
		</#list>
	<#else>
		<tr><td colspan="4">No record found</td></tr>
	</#if>
</table>
