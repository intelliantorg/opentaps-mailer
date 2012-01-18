<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="subSectionHeader">
	<div class="subSectionTitle">Import column mapper listing</div>
</div>
<#if lhsColumns?has_content>
	<form id="updateImportColumnMapper" name="updateImportColumnMapper" action="" method="post">
		<table border="0" cellspacing="0" cellpadding="3" width="100%" style="border:1px solid #999999">
		<#list lhsColumns as lhsColumn>
			<tr  class="${tableRowClass(lhsColumn_index)}">
				<td>
					<input type="hidden" name="entityColName_${lhsColumn_index}" value='${lhsColumn.get("entityColName")}'>
					${lhsColumn.get("entityColDesc")}
				</td>
				<td>
					<select class="dropDown" name="importFileColIdx_${lhsColumn_index}" id="importFileColIdx_${lhsColumn_index}">
						<option value='_NA_'>--select--</option>
						<#list rhsColumns as rhsColumn>
							<option value='${rhsColumn}' <#if (selectedIndex.get(lhsColumn_index)?number==rhsColumn?number) >selected='selected'</#if> >Column Index ${rhsColumn}</option>
						</#list>
					</select>
				</td>
				<td width="60%">&nbsp;</td>
			</tr>
		</#list>
		</table>
		<@inputSubmit title=uiLabelMap.ButtonUpdateMapping onClick="" class=smallSubmit />
	</form>
<#else>
  <div class="tabletext">&nbsp;No Configured Mappings.</div>
</#if>