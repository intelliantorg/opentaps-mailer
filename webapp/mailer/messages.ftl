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

