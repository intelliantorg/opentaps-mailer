<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<table class="listTable">
	<tr class="listTableHeader" style="border:none">
		<#list contactListHeaders as contactListHeader>
			<th align="left">${contactListHeader}</th>
		</#list>
		<th align="left">${uiLabelMap.ImportByHeader}</th>
		<th align="left">${uiLabelMap.ImportOnHeader}</th>
		<th align="left"></th>
	</tr>
	<#list contactListMembers as contactListMember>
		<tr class="${tableRowClass(contactListMember_index)}">
			<#list contactListFields as contactListField>
				<td>${contactListMember.get(contactListField)?if_exists}</td>
			</#list>
			<td>${contactListMember.importedByUserLogin?if_exists}</td>
			<td>${contactListMember.importedOnDateTime?if_exists?string("dd/MM/yyyy")}</td>
			<td><input type="checkbox" name="select" value="${contactListMember.recipientId}"/></td>
		</tr>
	</#list>
</table>
