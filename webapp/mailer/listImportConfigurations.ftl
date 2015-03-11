<#--
 * Copyright (c) Intelliant
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (iaerp@intelliant.net)
 *
-->
<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<#assign hasCreatePermission = security.hasEntityPermission("MAILER_MAP", "_CREATE", session)/>
<#assign hasDeletePermission = security.hasPermission("MAILER_MAP_DELETE", session)/>
<div class="subSectionHeader" style="padding:11px;">
	<div style="width:70%; float:left;" >${uiLabelMap.ConfigureMappingHeader}</div>
	<div class="subMenuBar" style="width:30%;float:left; margin-top: -5px;">
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
	<@inputHidden name="_useRowSubmit" value="Y" />
	<@inputHidden name="_rowCount" value="${listOfMappings?if_exists?size}" />
	<table class="crmsfaListTable">
	  <tr class="crmsfaListTableHeader" >
	    <td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.LabelName}</span></td>
	    <td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.CommonDescription}</span></td>
	    <td  style="text-align:left"><span class="tableheadtext">${uiLabelMap.IsFirstRowHeader}</span></td>
		  <#if hasDeletePermission>
	    	<td align="center" style="width:10%"><input type="checkbox" value="selectAll"/></td>
		  </#if>
	  </tr>
	  <#list listOfMappings as listOfMapping>
		<@inputHidden name="importMapperId_o_${listOfMapping_index}" value=listOfMapping.importMapperId?if_exists />
	    <tr class="${tableRowClass(listOfMapping_index)}">
	      <@displayLinkCell href="updateImportMappingForm?importMapperId=${listOfMapping.importMapperId}" text=listOfMapping.importMapperName/>
	      <td><span class="tabletext" style="">${listOfMapping.description?default("")}</span></td>
	      <td><span class="tabletext" style="">${listOfMapping.isFirstRowHeader?default("")}</span></td>
			<#if hasDeletePermission>
	      	  <td align="center" style="width:10%"><input type="checkbox" name="_rowSubmit_o_${listOfMapping_index}" value="Y"/></td>
			</#if>
	    </tr>
	  </#list>
	</table>
  </form>
<#else>
  <div class="tabletext">&nbsp;${uiLabelMap.LabelNoConfiguredMappings}</div>
</#if>
