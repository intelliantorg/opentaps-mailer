<#--
 * Copyright (c) Intelliant
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (open.ant@intelliant.net)
 *
-->
<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="subSectionHeader" style="padding:11px;">
  <div style="width:70%; float:left;">${uiLabelMap.CrmFormLetterTemplates}</div>
  <#if security.hasEntityPermission("CRMSFA_CAMP", "_CREATE", userLogin)>
    <div class="subMenuBar" style="width:30%;float:left; margin-top: -5px;" >
      <a class="subMenuButton" href="editMergeForms" title="${uiLabelMap.CrmCreateNewTemplate}">${uiLabelMap.CrmCreateNewTemplate}</a>
    </div>
  </#if>
</div>

<#if templates?has_content>
  <table class="crmsfaListTable">
    <tr class="crmsfaListTableHeader">
      <td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.OpentapsTemplateName}</span></td>
      <td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.LabelTemplateType}</span></td>
      <td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.CommonDescription}</span></td>
      <td>&nbsp;</td>
    </tr>
    <#list templates as template>
      <tr class="${tableRowClass(template_index)}">
        <@displayLinkCell href="editMergeForms?mergeFormId=${template.mergeFormId}" text=template.mergeFormName/>
        <td><span class="tabletext" style="">${template.mergeFormTypeDescription?if_exists}</span></td>
        <td><span class="tabletext" style="">${template.description?default("")}<span></td>
        <td style="text-align:center;width:10%"><@displayLink href="editMergeForms?mergeFormId=${template.mergeFormId}" text=uiLabelMap.CommonEdit/></td>
      </tr>
    </#list>
  </table>
<#else>
  <div class="tabletext">&nbsp;${uiLabelMap.CrmNoTemplates}.</div>
</#if>
