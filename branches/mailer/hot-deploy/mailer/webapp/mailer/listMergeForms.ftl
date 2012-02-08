<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>

<div class="subSectionHeader">
  <div class="subSectionTitle">${uiLabelMap.CrmFormLetterTemplates}</div>
  <#if security.hasEntityPermission("CRMSFA_CAMP", "_CREATE", userLogin)>
    <div class="subMenuBar">
      <a class="subMenuButton" href="editMergeForms" title="${uiLabelMap.CrmCreateNewTemplate}">${uiLabelMap.CrmCreateNewTemplate}</a>
    </div>
  </#if>
</div>

<#if templates?has_content>
  <table class="listTable" style="border:none">
    <tr class="listTableHeader" style="border:none">
      <td class="titleCellAutoWidth" style="text-align:left">${uiLabelMap.OpentapsTemplateName}</td>
      <td class="titleCellAutoWidth" style="text-align:left">${uiLabelMap.FormFieldTitle_categoryName}</td>
      <td class="titleCellAutoWidth" style="text-align:left">${uiLabelMap.CommonDescription}</td>
      <td>&nbsp;</td>
    </tr>
    <#list templates as template>
      <tr class="${tableRowClass(template_index)}">
        <@displayLinkCell href="editMergeForms?mergeFormId=${template.mergeFormId}" text=template.mergeFormName/>
        <#if template.mergeFormCategoryId?has_content>
          <@displayLinkCell href="EditMergeFormCategory?mergeFormCategoryId=${template.mergeFormCategoryId?if_exists}" text=template.mergeFormCategoryName?if_exists/>
        <#else>
          <td>&nbsp;</td>
        </#if>
        <td>${template.description?default("")}</td>
        <td style="text-align:right"><@displayLink href="editMergeForms?mergeFormId=${template.mergeFormId}" text=uiLabelMap.CommonEdit/></td>
      </tr>
    </#list>
  </table>
<#else>
  <div class="tabletext">&nbsp;${uiLabelMap.CrmNoTemplates}.</div>
</#if>