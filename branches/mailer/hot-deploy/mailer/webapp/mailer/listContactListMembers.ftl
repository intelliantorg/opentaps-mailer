<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<#if hasUpdatePermission?exists>
	<#assign removeLink>
		<input type="submit" value="${uiLabelMap.ButtonRemoveSelected}" name="submitButton" class="subMenuButton">
	</#assign>
</#if>

<form name="removeMembersFromList" id='removeMembersFromList' method="post" action="<@ofbizUrl>removeMembersFromList</@ofbizUrl>">
	<@inputHidden name="contactListId" value="${contactList.contactListId}" /> <#-- This is required for redirection etc. -->
	<@inputHidden name="_useRowSubmit" value="Y" />
	<@inputHidden name="_rowCount" value="${contactListMembers?if_exists?size}" />	
	<a name="ListContactListParties"></a>
	<div class="subSectionHeader">
		<div class="subSectionTitle">${uiLabelMap.CrmContactListParties}</div>
		<div class="subMenuBar">
			<a class="subMenuButton" href="importContactListForm?contactListId=${contactList.contactListId}">${uiLabelMap.ButtonImportContacts}</a>${removeLink?if_exists}
		</div>
	</div>

	<#macro tableNav>
	    <div class="button-bar">
	        <ul>
	            <#if (viewIndex > 1)> 
	                <li><a href='<@ofbizUrl>viewContactList?${curFindString}VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndexPrevious}</@ofbizUrl>' class="nav-previous">${uiLabelMap.CommonPrevious}</a></li>
	                <li>|</li>
	            </#if>
	            <#if (totalRecordsCount > 0)>
	                <li>${viewIndex} - ${highIndex} ${uiLabelMap.CommonOf} ${totalRecordsCount}</li>
	            </#if>
	            <#if (totalRecordsCount > highIndex)>
	                <li>|</li>
	                <li><a href='<@ofbizUrl>viewContactList?${curFindString}VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndexNext}</@ofbizUrl>' class="nav-next">${uiLabelMap.CommonNext}</a></li>
	            </#if>
	        </ul>
	        <br class="clear"/>
	    </div>
	</#macro>
	<#if (totalRecordsCount > 0)>
	    <@tableNav/>
	</#if>
	<table class="listTable">
		<tr class="listTableHeader" style="border:none">
			<#list contactListHeaders as contactListHeader>
				<th align="left">${contactListHeader}</th>
			</#list>
			<th align="left">${uiLabelMap.ImportByHeader}</th>
			<th align="left">${uiLabelMap.ImportOnHeader}</th>
			<th align="left">
				<input name="selectAll" value="Y" onclick="javascript:toggleAll(this, 'removeMembersFromList');" type="checkbox">
			</th>
		</tr>
		<#list contactListMembers as contactListMember>
			<@inputHidden name="contactListId_o_${contactListMember_index}" value=contactListMember.contactListId?if_exists />
			<@inputHidden name="recipientId_o_${contactListMember_index}" value=contactListMember.recipientId?if_exists />
			<@inputHidden name="recipientListId_o_${contactListMember_index}" value=contactListMember.recipientListId?if_exists />
			<tr class="${tableRowClass(contactListMember_index)}">
				<#list contactListFields as contactListField>
					<td>${contactListMember.get(contactListField)?if_exists}</td>
				</#list>
				<td>${contactListMember.importedByUserLogin?if_exists}</td>
				<td>${contactListMember.importedOnDateTime?if_exists?string("dd/MM/yyyy")}</td>
				<td><input type="checkbox" name="_rowSubmit_o_${contactListMember_index}" value="Y"/></td>
			</tr>
		</#list>
	</table>
	<#if (totalRecordsCount > 0)>
		<@tableNav/>
	</#if>
</form>