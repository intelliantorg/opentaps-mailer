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


<#if showPromoText>
<div class="screenlet">
    <div class="screenlet-header">
        <div class="boxhead">${uiLabelMap.EcommerceSpecialOffers}</div>
    </div>
    <div class="screenlet-body">
        <#-- show promotions text -->
        <#list productPromos as productPromo>
            <div class="tabletext"><a href="<@ofbizUrl>showPromotionDetails?productPromoId=${productPromo.productPromoId}</@ofbizUrl>" class="linktext">${uiLabelMap.EcommerceDetailsButton}</a> ${productPromo.promoText}</div>
            <#if productPromo_has_next>
                <div><hr class="sepbar"/></div>
            </#if>
        </#list>
        <div><hr class="sepbar"/></div>
        <div class="tabletext"><a href="<@ofbizUrl>showAllPromotions</@ofbizUrl>" class="buttontext">${uiLabelMap.EcommerceViewAllPromotions}</a></div>
    </div>
</div>
</#if>
