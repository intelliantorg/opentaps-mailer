<#function getTags>

  <#assign tags = []/>
  
	<#if entityColumns?has_content >
		<#list entityColumns as entityColumn>
			<#assign tags = tags + [{ "${entityColumn.entityColName}"                               : "${entityColumn.entityColDesc}"                 }]/>
		</#list>
	</#if>
	
	<#return tags>
</#function>
