<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="subSectionHeader">
	<div class="subSectionTitle">${uiLabelMap.ConfigureMappingHeader}</div>
	<#if (security.hasEntityPermission("MAILER_MAP", "_CREATE", session))>
		<div class="subMenuBar">
			<a class="subMenuButton" href="<@ofbizUrl>configureImportMapping</@ofbizUrl>">${uiLabelMap.ConfigureNewMapping}</a>
		</div>
	</#if>
</div>

<#if listOfMappings?has_content>
  <table class="listTable">
    <tr class="listTableHeader" style="border:none">
      <td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.LabelName}</td>
      <td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.CommonDescription}</td>
      <td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.IsFirstRowHeader}</td>
      <td>&nbsp;</td>
    </tr>
    <#list listOfMappings as listOfMapping>
      <tr class="${tableRowClass(listOfMapping_index)}">
        <@displayLinkCell href="updateImportMappingForm?importMapperId=${listOfMapping.importMapperId}" text=listOfMapping.importMapperName/>
        <td>${listOfMapping.description?default("")}</td>
        <td>${listOfMapping.isFirstRowHeader?default("")}</td>
        <#if (security.hasEntityPermission("MAILER_MAP", "_UPDATE", session))>
        	<td style="text-align:right">
        		<@displayLink href="updateImportMappingForm?importMapperId=${listOfMapping.importMapperId}" text=uiLabelMap.CommonEdit/>
        	</td>
        </#if>
      </tr>
    </#list>
  </table>
<#else>
  <div class="tabletext">&nbsp;No Configured Mappings.</div>
</#if>
