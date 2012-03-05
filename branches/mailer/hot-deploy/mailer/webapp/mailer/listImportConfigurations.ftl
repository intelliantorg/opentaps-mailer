<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<#assign hasCreatePermission = security.hasEntityPermission("MAILER_MAP", "_CREATE", session)/>
<#assign hasDeletePermission = security.hasEntityPermission("MAILER_MAP", "_DELETE", session)/>
<div class="subSectionHeader">
	<div class="subSectionTitle">${uiLabelMap.ConfigureMappingHeader}</div>
		<div class="subMenuBar">
			<#if hasCreatePermission>
				<a class="subMenuButton" href="<@ofbizUrl>configureImportMapping</@ofbizUrl>">${uiLabelMap.ConfigureNewMapping}</a>
			</#if>
			<#if hasDeletePermission>
				<a class="subMenuButton" href="javascript:document.deleteImportMapping.submit()">${uiLabelMap.ButtonDeleteSelected}</a>
			</#if>
		</div>
</div>
<#if listOfMappings?has_content>
  <form id="deleteImportMapping" name="deleteImportMapping" action="<@ofbizUrl>deleteImportMapping</@ofbizUrl>" method="post">
	<table class="listTable">
	  <tr class="listTableHeader" style="border:none">
	    <td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.LabelName}</td>
	    <td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.CommonDescription}</td>
	    <td class="titleCell titleCellAutoWidth" style="text-align:left">${uiLabelMap.IsFirstRowHeader}</td>
		  <#if hasDeletePermission>
	    	<td align="right"><input type="checkbox" value="selectAll"/></td>
		  </#if>
	  </tr>
	  <#list listOfMappings as listOfMapping>
	    <tr class="${tableRowClass(listOfMapping_index)}">
	      <@displayLinkCell href="updateImportMappingForm?importMapperId=${listOfMapping.importMapperId}" text=listOfMapping.importMapperName/>
	      <td>${listOfMapping.description?default("")}</td>
	      <td>${listOfMapping.isFirstRowHeader?default("")}</td>
			<#if hasDeletePermission>
	      	  <td align="right"><input type="checkbox" name="importMapperId_${listOfMapping_index}" value="${listOfMapping.importMapperId}"/></td>
			</#if>
	    </tr>
	  </#list>
	</table>
  </form>
<#else>
  <div class="tabletext">&nbsp;${uiLabelMap.LabelNoConfiguredMappings}</div>
</#if>
