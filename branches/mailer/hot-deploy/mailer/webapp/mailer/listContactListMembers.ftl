<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<table class="listTable">
	<tr class="listTableHeader" style="border:none">
		<td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.NameHeader}</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.EmailHeader}</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.ImportOnHeader}</td>
		<td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.ImportByHeader}</td>
	</tr>
	<#list contactListPartiesX as contactListParty>
	<tr class="${tableRowClass(contactListParty_index)}">
		<td>${contactListParty.firstName} ${contactListParty.lastName}</td>
		<td>${contactListParty.emailAddress}</td>
		<td>${contactListParty.importedOnDateTime?if_exists?string("dd/MM/yyyy")}</td>
		<td>${contactListParty.importedByUserLogin}</td>
	</tr>
	</#list>
</table>
