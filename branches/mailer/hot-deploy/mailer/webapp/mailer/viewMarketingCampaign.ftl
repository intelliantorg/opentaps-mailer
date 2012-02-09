<#if marketingCampaign.statusId?exists && marketingCampaign.templateId?exists>
	<#if marketingCampaign.statusId == "MKTG_CAMP_INPROGRESS">
		<#assign writeEmail>
			<a class='subMenuButton' href='writeEmail?emailType=MKTG_CAMPAIGN&marketingCampaignId=${marketingCampaign.marketingCampaignId}&donePage=viewMarketingCampaign'>
				${uiLabelMap.ButtonEmail}
			</a>
		</#assign>
		<#assign printlLink>
			<a class='subMenuButton' href='#'>${uiLabelMap.ButtonPrint}</a>
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
  <div class="subMenuBar">${printlLink?if_exists}${writeEmail?if_exists}${updateLink?if_exists}</div>
</div>
