<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>
<script type="text/javascript">
	function validateThisForm(){		
		var messageBox = document.getElementById("message");		
		var checkboxArray = document.executeBulkCampaign.select;
		var length = checkboxArray.length;
		var atleastOneCheckedFlag = false;
		for(var i = 0 ; i < length; i++){
			if(!atleastOneCheckedFlag && checkboxArray[i].checked){
				atleastOneCheckedFlag = true;
				break;
			}
		}
		//return atleastOneCheckedFlag?true:false;
		if(atleastOneCheckedFlag){
			messageBox.innerHTML="";
			return true;
		}else{
			messageBox.innerHTML="Select at least one checkbox";
			return false;
		}
	}
</script>
<div class="subSectionHeader">
	<div class="subSectionTitle">${uiLabelMap.MarketingCampaignHeader}</div>
	<div class="subMenuBar">
		<a class='subMenuButton' href='#' onclick='javascript:validateThisForm()'>
		${uiLabelMap.ButtonExecute}
		</a>
	</div>
</div>
<div>
	<div style="text-align:right;">
		<span id="message" class="tabletext" style="color:red;font-size: 12px;"></span>
	</div>
	<form action="" name="executeBulkCampaign" method="post">
		<table class="crmsfaListTable">
			<tr class="crmsfaListTableHeader">
				<td><span class="tableheadtext"><a class="orderByHeaderLink" href="${listSortTarget}?campaignsOrderBy=campaignName${findParams}#ListMarketingCampaigns">${uiLabelMap.CommonName}<a></span></td>
				<td><span class="tableheadtext"><a class="orderByHeaderLink" href="${listSortTarget}?campaignsOrderBy=statusId${findParams}#ListMarketingCampaigns">${uiLabelMap.CommonStatus}</a></span></td>
				<td><span class="tableheadtext">${uiLabelMap.CommonFrom}</span></td>
				<td><span class="tableheadtext">${uiLabelMap.CommonThru}</span></td>			
				<td><span class="tableheadtext">Select</span></td>
			</tr>
			<#list campaignsListIt as campaignsListItem>
			<tr class="${tableRowClass(campaignsListItem_index)}">
				<td>
					<a class="linktext" href="viewMarketingCampaign?marketingCampaignId=${campaignsListItem.marketingCampaignId}">
						${campaignsListItem.campaignName} (${campaignsListItem.marketingCampaignId})
					</a>
				</td>
				<td><span class="tabletext">${campaignsListItem.description?default("")}</span></td>
				<td><span class="tabletext"><@displayDate date=campaignsListItem.fromDate?default("") /></span></td>
				<td><span class="tabletext"><@displayDate date=campaignsListItem.thruDate?default("") /></span></td>			
				<td>
					<span class="tabletext">			
						<#if campaignsListItem.statusId?exists && campaignsListItem.templateId?exists && (campaignsListItem.statusId == "MKTG_CAMP_INPROGRESS") >
							<input type="checkbox" name="select_${campaignsListItem_index}" value="${campaignsListItem.marketingCampaignId}" />
						</#if>
					</span>
				</td>
			</tr>
			</#list>
		</table>
	</form>
</div>