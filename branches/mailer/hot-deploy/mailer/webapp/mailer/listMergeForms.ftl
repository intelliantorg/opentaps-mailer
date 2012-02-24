<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="subSectionHeader">
  <div class="subSectionTitle">${uiLabelMap.CrmFormLetterTemplates}</div>
  <#if security.hasEntityPermission("CRMSFA_CAMP", "_CREATE", userLogin)>
    <div class="subMenuBar">
      <a class="subMenuButton" href="editMergeForms" title="${uiLabelMap.CrmCreateNewTemplate}">${uiLabelMap.CrmCreateNewTemplate}</a>
    </div>
  </#if>
</div>

<#if templates?has_content>
  <table class="listTable">
    <tr class="listTableHeader" style="border:none">
      <td class="titleCellAutoWidth" style="text-align:left">${uiLabelMap.OpentapsTemplateName}</td>
      <td class="titleCellAutoWidth" style="text-align:left">${uiLabelMap.LabelTemplateType}</td>
      <td class="titleCellAutoWidth" style="text-align:left">${uiLabelMap.CommonDescription}</td>
      <td>&nbsp;</td>
    </tr>
    <#list templates as template>
      <tr class="${tableRowClass(template_index)}">
        <@displayLinkCell href="editMergeForms?mergeFormId=${template.mergeFormId}" text=template.mergeFormName/>
        <td>${template.mergeFormTypeDescription?if_exists}</td>
        <td>${template.description?default("")}</td>
        <td style="text-align:right"><@displayLink href="editMergeForms?mergeFormId=${template.mergeFormId}" text=uiLabelMap.CommonEdit/></td>
      </tr>
    </#list>
  </table>
<#else>
  <div class="tabletext">&nbsp;${uiLabelMap.CrmNoTemplates}.</div>
</#if>
