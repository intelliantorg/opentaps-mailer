<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<div class="form">
	<form name="findContactList" action="<@ofbizUrl>viewContactList</@ofbizUrl>" method="get">
		<@inputHidden name="contactListId" value=contactList.contactListId />
		<table width="100%">
			<tr>
				<#assign noOfColumns = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("mailer", "findCampaignListingNoOfColumns")>
				<#list entityColumns as entityColumn>
					<#if entityColumn_index != 0 && entityColumn_index%noOfColumns?number == 0>
						</tr><tr>
					</#if>
					<td class="label">${entityColumn.entityColDesc}</td>		
					<td>
						<#if entityColumn.entityColType == "date" || entityColumn.entityColType == "date-time">
							<@inputDate default=parameters.get(entityColumn.entityColName)?if_exists name=entityColumn.entityColName />
						<#else>
							<input class="inputBox" type="text" name="${entityColumn.entityColName}" value="${parameters.get(entityColumn.entityColName)?if_exists}" />
						</#if>
					</td>
				</#list>
			</tr>
			<tr>
				<td colspan=${noOfColumns}><input class="smallSubmit" type="submit" name="submit" value="Find" /></td>
			</tr>
		</table>
	</form>
</div>
