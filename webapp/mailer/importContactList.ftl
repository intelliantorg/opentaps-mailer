<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>

<div class="form">
  <form id="importContactListForm" method="post" action="<@ofbizUrl>importContactList</@ofbizUrl>" name="importContactListForm" style="margin: 0;" enctype="multipart/form-data">
  	<input type="hidden" name="contactListId" value="${parameters.contactListId}" />
    <div class="formRow">
      <span class="formLabelRequired">Contact List file (.xls)</span>
      <span class="formInputSpan">
        <input type="file" class="inputBox required" name="uploadedFile" size="50" maxlength="100"/>
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

    <div class="spacer">&nbsp;</div>
  </form>
</div>