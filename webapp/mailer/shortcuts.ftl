<div class="screenlet">
    <div class="screenlet-header">
    	<div class="boxhead">${uiLabelMap.CrmShortcuts}</div>
    </div>
    <div class="screenlet-body">
      <ul class="shortcuts">
        <li><a href="<@ofbizUrl>findMarketingCampaigns</@ofbizUrl>">${uiLabelMap.CrmFindMarketingCampaigns}</a></li>
        <li><a href="<@ofbizUrl>findContactLists</@ofbizUrl>">${uiLabelMap.CrmFindContactLists}</a></li>
        <#if (security.hasEntityPermission("CRMSFA_CAMP", "_CREATE", session))>
        	<li><a href="<@ofbizUrl>createMarketingCampaignForm</@ofbizUrl>">${uiLabelMap.CrmCreateMarketingCampaign}</a></li>
        	<li><a href="<@ofbizUrl>createContactListForm</@ofbizUrl>">${uiLabelMap.CrmCreateContactList}</a></li>
        	<li><a href="<@ofbizUrl>manageMergeForms</@ofbizUrl>">${uiLabelMap.CrmFormLetterTemplates}</a></li>
        </#if>
        <li><a href="<@ofbizUrl>viewImportMappings</@ofbizUrl>">${uiLabelMap.ConfiguredMappings}</a></li>
      </ul>
    </div>
</div>