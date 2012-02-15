<#if marketingCampaign.statusId?exists && marketingCampaign.templateId?exists>
	<#if marketingCampaign.statusId == "MKTG_CAMP_INPROGRESS">
		<#assign execute>
			<a class='subMenuButton' href='<@ofbizUrl>executeCampaign?marketingCampaignId=${marketingCampaign.marketingCampaignId}</@ofbizUrl>'>
				${uiLabelMap.ButtonExecute}
			</a>
		</#assign>
	</#if>
</#if>
<#if hasUpdatePermission?exists && marketingCampaign.statusId != "MKTG_CAMP_CANCELLED">
	<#assign updateLink>
		<a class='subMenuButton' href='updateMarketingCampaignForm?marketingCampaignId=${marketingCampaign.marketingCampaignId}'>${uiLabelMap.ButtonEdit}</a>
	</#assign>
</#if>

<div class="subSectionHeader">
  <div class="subSectionTitle">${uiLabelMap.CrmMarketingCampaign}</div>
  <div class="subMenuBar">${execute?if_exists}${updateLink?if_exists}</div>
</div>
