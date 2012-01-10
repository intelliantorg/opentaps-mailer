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


<#assign unselectedClassName = "tabButton">
<#assign selectedClassMap = {page.tabButtonItem?default("void") : "tabButtonSelected"}>

<div class='tabContainer'>
    <#if productionRun?has_content>
        <#if productionRun.getString("currentStatusId") == "PRUN_CREATED" || productionRun.getString("currentStatusId") == "PRUN_SCHEDULED">
        <a href="<@ofbizUrl>EditProductionRun?productionRunId=${productionRunId}</@ofbizUrl>" class="${selectedClassMap.edit?default(unselectedClassName)}">${uiLabelMap.ManufacturingEditProductionRun}</a>
        <a href="<@ofbizUrl>ProductionRunTasks?productionRunId=${productionRunId}</@ofbizUrl>" class="${selectedClassMap.tasks?default(unselectedClassName)}">${uiLabelMap.ManufacturingListOfProductionRunRoutingTasks}</a>
        <a href="<@ofbizUrl>ProductionRunComponents?productionRunId=${productionRunId}</@ofbizUrl>" class="${selectedClassMap.components?default(unselectedClassName)}">${uiLabelMap.ManufacturingMaterials}</a>
        <a href="<@ofbizUrl>ProductionRunFixedAssets?productionRunId=${productionRunId}</@ofbizUrl>" class="${selectedClassMap.fixedAssets?default(unselectedClassName)}">${uiLabelMap.AccountingFixedAssets}</a>
        <#else>
        <a href="<@ofbizUrl>ProductionRunDeclaration?productionRunId=${productionRunId}</@ofbizUrl>" class="${selectedClassMap.declaration?default(unselectedClassName)}">${uiLabelMap.ManufacturingProductionRunDeclaration}</a>
        <a href="<@ofbizUrl>ProductionRunActualComponents?productionRunId=${productionRunId}</@ofbizUrl>" class="${selectedClassMap.actualComponents?default(unselectedClassName)}">${uiLabelMap.ManufacturingActualMaterials}</a>
        </#if>
        <a href="<@ofbizUrl>ProductionRunAssocs?productionRunId=${productionRunId}</@ofbizUrl>" class="${selectedClassMap.assocs?default(unselectedClassName)}">${uiLabelMap.ManufacturingProductionRunAssocs}</a>
        <a href="<@ofbizUrl>ProductionRunCosts?productionRunId=${productionRunId}</@ofbizUrl>" class="${selectedClassMap.costs?default(unselectedClassName)}">${uiLabelMap.ManufacturingActualCosts}</a>
    </#if>
</div>
