<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<table class="listTable">
	<tr class="listTableHeader" style="border:none">
		<td class="titleCell titleCellAutoWidth" style="text-align:left">Name</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">Email</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">Import on</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">Import by</td>
	</tr>
	<#list contactListPartiesX as contactListParty>
	<tr class="${tableRowClass(contactListParty_index)}">
		<td>${contactListParty.firstName} ${contactListParty.lastName} (${contactListParty.recipientId})</td>
		<td>${contactListParty.emailAddress}</td>
		<td>${contactListParty.importedOnDateTime}</td>
		<td>${contactListParty.importedByUserLogin}</td>
	</tr>
	</#list>
</table>
