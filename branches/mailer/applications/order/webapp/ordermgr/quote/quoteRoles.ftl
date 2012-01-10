<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<#-- This file has been modified by Open Source Strategies, Inc. -->

<div class="screenlet">
    <div class="screenlet-header">
        <div class="boxhead">&nbsp;${uiLabelMap.OrderOrderQuoteRoles}</div>
    </div>
    <div class="screenlet-body">
        <table width="100%" border="0" cellpadding="1">
            <#list quoteRoles as quoteRole>
                <#assign roleType = quoteRole.getRelatedOne("RoleType")>
                <#assign party = quoteRole.getRelatedOne("Party")>
                <#assign rolePartyNameResult = dispatcher.runSync("getPartyNameForDate", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", quoteRole.partyId, "compareDate", quote.issueDate, "userLogin", userLogin))/>
                <tr>
                    <td align="right" valign="top" width="15%">
                        <div class="tabletext">&nbsp;<b>${roleType.get("description",locale)?if_exists}</b></div>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td align="left" valign="top" width="80%">
                        <div class="tabletext">${rolePartyNameResult.fullName?default("Name Not Found")}</div>
                    </td>
                </tr>
                <tr><td colspan="7"><hr class="sepbar"/></td></tr>
            </#list>
        </table>
    </div>
</div>
