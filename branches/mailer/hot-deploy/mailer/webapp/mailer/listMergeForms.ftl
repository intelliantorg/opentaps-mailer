<#--
 * Copyright (C) 2006  Open Source Strategies, Inc.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
-->
<#-- Copyright (c) 2005-2006 Open Source Strategies, Inc. -->
 
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
          <#-- <td>${template.mergeFormCategoryName?default("")}</td> -->
        <#else>
          <td>&nbsp;</td>
        </#if>
        <td>${template.description?default("")}</td>
        <td style="text-align:right"><@displayLink href="EditMergeForm?mergeFormId=${template.mergeFormId}" text=uiLabelMap.CommonEdit/></td>
      </tr>
    </#list>
  </table>
<#else>
  <div class="tabletext">&nbsp;${uiLabelMap.CrmNoTemplates}.</div>
</#if>
