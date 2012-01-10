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

<div class="head1">${uiLabelMap.ProductProductPromotionsList}</div>
<div class="tabletext">
    <a href="<@ofbizUrl>EditProductPromo</@ofbizUrl>" class="buttontext">[${uiLabelMap.ProductCreateNewProductPromo}]</a>
    <#if manualOnly?if_exists == "Y">
        <a href="<@ofbizUrl>FindProductPromo?manualOnly=N</@ofbizUrl>" class="buttontext">[${uiLabelMap.ProductPromotionManualImported}]</a>
    <#else>
        <a href="<@ofbizUrl>FindProductPromo?manualOnly=Y</@ofbizUrl>" class="buttontext">[${uiLabelMap.ProductPromotionManual}]</a>
    </#if>
</div>
<div class="tabletext">
    <form method="post" action="<@ofbizUrl>EditProductPromoCode</@ofbizUrl>" style="margin: 0;">
        ${uiLabelMap.ProductPromotionCode}: <input type="text" size="10" name="productPromoCodeId" class="inputBox">
        <input type="submit" value="${uiLabelMap.CommonEdit}">
    </form>
</div>
<br/>
<table border="1" cellpadding="2" cellspacing="0">
    <tr>
        <td><div class="tabletext"><b>${uiLabelMap.ProductPromoNameId}</b></div></td>
        <td><div class="tabletext"><b>${uiLabelMap.ProductPromoText}</b></div></td>
        <td><div class="tabletext"><b>${uiLabelMap.ProductPromotionReqCode}?</b></div></td>
        <td><div class="tabletext"><b>${uiLabelMap.CommonCreated}</b></div></td>
        <td><div class="tabletext">&nbsp;</div></td>
    </tr>
    <#list productPromos as productPromo>
        <tr valign="middle">
            <td><div class='tabletext'>&nbsp;<a href="<@ofbizUrl>EditProductPromo?productPromoId=${(productPromo.productPromoId)?if_exists}</@ofbizUrl>" class="buttontext">${(productPromo.promoName)?if_exists} [${(productPromo.productPromoId)?if_exists}]</a></div></td>
            <td><div class='tabletext'>&nbsp;${(productPromo.promoText)?if_exists}</div></td>
            <td><div class='tabletext'>&nbsp;${(productPromo.requireCode)?default("N")}</div></td>
            <td><div class='tabletext'>&nbsp;${(productPromo.createdDate)?if_exists}</div></td>
            <td>
                <a href='<@ofbizUrl>EditProductPromo?productPromoId=${(productPromo.productPromoId)?if_exists}</@ofbizUrl>' class="buttontext">[${uiLabelMap.CommonEdit}]</a>
            </td>
        </tr>
    </#list>
</table>
