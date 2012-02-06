<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>

<div class="form">
  <form id="importContactListForm" method="post" action="<@ofbizUrl>importContactList</@ofbizUrl>" name="importContactListForm" style="margin: 0;" enctype="multipart/form-data">
  	<input type="hidden" name="contactListId" value="${parameters.contactListId}" />
  	
    <div class="formRow">
      <span class="formLabelRequired">Contact List file</span>
      <span class="formInputSpan">
        <input type="file" class="inputBox required" name="uploadedFile" size="50" maxlength="100"/><br>
		<span>Supported file type : .xls</span>
      </span>
    </div>
    <div class="formRow">
      <span class="formLabelRequired">Mapper</span>
      <span class="formInputSpan">
      	<@inputSelect name="importMapperId" list=mailerImportMapperList displayField="importMapperName" key="importMapperId" />
      </span>
    </div>
    <div class="formRow">
      <span class="formInputSpan">
        <input type="submit" class="smallSubmit" name="submitButton" value="Submit" onClick="" />
      </span>
    </div>
	<#if parameters.totalCount?exists && parameters.failureCount?exists && parameters.failureReport?exists >
  	<div class="reportPanel" style="clear:both">
  		<div>Import status:</div>
  		<#assign successCount = parameters.totalCount?int - parameters.failureCount?int /> 
  		<div>Total records in XLS: ${parameters.totalCount}</div>
  		<div>Total records successfully imported: ${successCount}</div>
  		<div>Total records with failure: ${parameters.failureCount}</div>
		<#assign report = parameters.failureReport>
		<#assign keys = report.keySet()>
		<#if (keys?size > 0) >
			<div style="margin-top:10px;">
				<div><strong>Full report:</strong></div>
					<table class="listTable">
						<tr>
							<th align="left" width="60px">Sr No.</th>
							<th align="left" width="130px">Excel row #</th>
							<th align="left">Reason</th>						
						</tr>
						<#list keys as key>
							<tr>							
								<td align="left" x='key'>${key_index+1}</td>
								<td align="left" x='key'>${key}</td>
								<td align="left" y='value'>${report(key)}</td>						
							</tr>
						</#list>
					</table>
				</div>
		  	</div>
		</#if>
  	</#if>  	
    <div class="spacer">&nbsp;</div>
  </form>
</div>