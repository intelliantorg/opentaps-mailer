<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<table class="listTable">
	<tr class="listTableHeader" style="border:none">
		<#list contactListHeaders as contactListHeader>
			<th align="left">${contactListHeader}</th>
		</#list>
	</tr>
	<#list formattedContactListMembers as formattedContactListMember>
		<tr class="${tableRowClass(formattedContactListMember_index)}">
			<#list formattedContactListMember as coloum>
				<td>${coloum}</td>
			</#list>
		</tr>
	</#list>
</table>
