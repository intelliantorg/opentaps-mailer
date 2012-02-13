<#if requestAttributes.errorMessageList?has_content><#assign errorMessageList=requestAttributes.errorMessageList></#if>
<#if requestAttributes.eventMessageList?has_content><#assign eventMessageList=requestAttributes.eventMessageList></#if>

<#-- display the error messages -->
<#if errorMessageList?has_content || opentapsErrors.toplevel?size != 0>
<div class="messages">
<div class="errorMessageHeader">${uiLabelMap.CommonFollowingErrorsOccurred}:</div>
<ul class="errorList">
  <#list opentapsErrors.toplevel as errorMsg>
      <li class="errorMessage">${errorMsg}</li>
  </#list>
  <#list errorMessageList?if_exists as errorMsg>
    <li class="errorMessage">${errorMsg}</li>
  </#list>
</ul>
</div>
</#if>
<#if eventMessageList?has_content>
<div class="messages">
<div class="eventMessageHeader">${uiLabelMap.CommonFollowingOccurred}:</div>
<ul class="eventList">
  <#list eventMessageList as eventMsg>
    <li class="eventMessage">${eventMsg}</li>
  </#list>
</ul>
</div>

</#if>

