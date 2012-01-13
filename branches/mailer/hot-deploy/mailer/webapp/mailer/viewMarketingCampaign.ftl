<#if hasUpdatePermission?exists>
	<#if marketingCampaign.statusId?exists && marketingCampaign.templateId?exists>
		<#if marketingCampaign.statusId == "MKTG_CAMP_INPROGRESS">
			<#assign writeEmail = "<a class='subMenuButton' href='writeEmail?emailType=MKTG_CAMPAIGN&marketingCampaignId=" + marketingCampaign.marketingCampaignId + "&donePage=viewMarketingCampaign'>" + uiLabelMap.ButtonEmail + "</a>">
			<#assign printlLink = "<a class='subMenuButton' href='#'>" + uiLabelMap.ButtonPrint + "</a>">
		</#if>
	</#if>
	<#assign updateLink = "<a class='subMenuButton' href='updateMarketingCampaignForm?marketingCampaignId=" + marketingCampaign.marketingCampaignId + "'>" + uiLabelMap.ButtonEdit + "</a>">	
</#if>

<div class="subSectionHeader">
  <div class="subSectionTitle">${uiLabelMap.CrmMarketingCampaign}</div>
  <div class="subMenuBar">${printlLink?if_exists}${writeEmail?if_exists}${updateLink?if_exists}</div>
</div>
