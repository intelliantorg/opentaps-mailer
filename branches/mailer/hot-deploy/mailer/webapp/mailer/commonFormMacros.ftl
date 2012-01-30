<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>

<#macro inputText name size=30 maxlength="" default="" index=-1 password=false readonly=false onChange="" id="" ignoreParameters=false errorField="" tabIndex="" class="inputBox">
  <#if id == ""><#assign idVal = name><#else><#assign idVal = id></#if>
  <input id="${getIndexedName(idVal, index)}" class="${class}" name="${getIndexedName(name, index)}" type="<#if password>password<#else>text</#if>" size="${size}" maxlength="${maxlength}" <#if !password>value="${getDefaultValue(name, default, index, ignoreParameters)}"</#if> <#if readonly>readonly="readonly"</#if> onChange="${onChange}" <#if tabIndex?has_content>tabindex="${tabIndex}"</#if>/>
  <#if errorField?has_content><@displayError name=errorField index=index /></#if>
</#macro>

<#macro tooltip text="" class="tooltip"><#if text?has_content><span class="tabletext"><span class="${class}">${text}</span></span></#if></#macro>


<#macro displayValidationError name class="error">
	<div>
		<label for="${name}" generated="true" class="${class}">This field is required.</label>
	</div>
</#macro>

<#macro inputStatusItemSelect list=[] name="statusId" defaultStatusId="" id="" class="dropDown">
	<#if id == ""><#assign idVal = name><#else><#assign idVal = id></#if>
  	<#assign statusItems = list />
  	<select id="${getIndexedName(idVal, index)}" name="${getIndexedName(name, index)}" class="${class}">
		<#list statusItems as option>
  			<#if option.statusId == defaultStatusId><#assign selected = "selected=\"selected\""><#else><#assign selected = ""></#if>
  			<option ${selected} value="${option.statusId}">${option.description}</option>
		</#list>
  	</select>
</#macro>

<#macro inputFile name size=30 maxlength="" default="" index=-1 onChange="" id="" errorField="" tabIndex="" class="inputBox">
	<#if id == ""><#assign idVal = name><#else><#assign idVal = id></#if>
  	<input id="${getIndexedName(idVal, index)}" name="${getIndexedName(name, index)}" type="file" size="${size}" maxlength="${maxlength}" class="${class}" onChange="${onChange}" <#if tabIndex?has_content>tabindex="${tabIndex}"</#if>/>
</#macro>

<#macro inputSelect name list key="" displayField="" default="" index=-1 required=true defaultOptionText="" onChange="" id="" ignoreParameters=false errorField="" tabIndex="" class="dropDown">
  <#if key == ""><#assign listKey = name><#else><#assign listKey = key></#if>
  <#if id == ""><#assign idVal = name><#else><#assign idVal = id></#if>
  <#assign defaultValue = getDefaultValue(name, default, index, ignoreParameters)>
  <select id="${getIndexedName(idVal, index)}" name="${getIndexedName(name, index)}" class="${class}" onChange="${onChange}" <#if tabIndex?has_content>tabindex="${tabIndex}"</#if>>
    <#if !required><option value="">${defaultOptionText}</option></#if>
    <#list list as option>
      <#if option.get(listKey) == defaultValue || listKey == defaultValue><#assign selected = "selected=\"selected\""><#else><#assign selected = ""></#if>
      <option ${selected} value="${option.get(listKey)}">
        <#if displayField==""><#nested option><#else>${option.get(displayField)?if_exists}</#if>
      </option>
    </#list>
  </select>
  <#if errorField?has_content><@displayError name=errorField index=index /></#if>
</#macro>