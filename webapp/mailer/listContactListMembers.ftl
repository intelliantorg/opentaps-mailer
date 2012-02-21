<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<table class="listTable">
	<tr class="listTableHeader" style="border:none">
		<#list contactListHeaders as contactListHeader>
			<th align="left">${contactListHeader}</th>
		</#list>
		<th align="left">Added by</th>
		<th align="left">Added on</th>
		<th align="left"></th>
	</tr>
	<#list contactListMembers as contactListMember>
		<tr class="${tableRowClass(contactListMember_index)}">
			<#list contactListFields as contactListField>
				<td>${contactListMember.get(contactListField)}</td>
			</#list>
			<td>${contactListMember.importedByUserLogin}</td>
			<td>${contactListMember.importedOnDateTime?if_exists?string("dd/MM/yyyy")}</td>
			<td><input type="checkbox" name="select" value="${contactListMember.recipientId}" /></td>
		</tr>
	</#list>
</table>
