<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="subSectionHeader">
    <div class="subSectionTitle">
      ${uiLabelMap.CrmFormLetterTemplate} <#if mergeFormId?has_content>[${mergeFormId}]</#if>
    </div>
</div>

<#if mergeFormId?has_content>
	<#assign formName="updateMergeForm" />
	<#assign formSubmit=uiLabelMap.CommonSave />
	<form method="post" action="deleteMergeForm" name="deleteMergeForm">
		<@inputHidden name="mergeFormId" value=mergeFormId />
	</form>
<#else>
	<#assign formName="createMergeForm" />
 	<#assign formSubmit=uiLabelMap.CommonCreate />
</#if>

<div class="form">
  <form class="basic-form" id="mergedForm" method="post" action="<@ofbizUrl>${formName}</@ofbizUrl>" name="${formName}" style="margin: 0;" enctype="multipart/form-data">
	<#if mergeFormId?exists>
    	<@inputHidden name="mergeFormId" value=mergeFormId />
    </#if>
    <@inputHidden name="partyId" value=userLogin.partyId />  
	<div class="rowContainer formRow">
		<div class="labelRequired">${uiLabelMap.OpentapsTemplateName}</div>
		<div class="fieldContainer" style="width:35%; text-align:left;">
			<span class="formInputSpan" style="float:left;">
				<input style="margin-left:18px;" type="text" class="inputBox required" name="mergeFormName" value="${(MailerMergeForm.mergeFormName)?if_exists}" size="50" maxlength="100"/>
			</span>
		</div>
		<div class="labelRequired">${uiLabelMap.LabelTemplateType}</div>
		<div class="fieldContainer">
			<span class="formInputSpan">
				<@inputSelect  class="inputBox required" name="mergeFormTypeId" required=false default=MailerMergeForm.mergeFormTypeId?if_exists list=mergeFormTypesCombobox displayField="description" key="mergeFormTypeId" onChange="campaignTypeOnChange(this.value)" />  
			</span>	
		</div>
    </div>
    
    <div class="formRow" id="emailAddressContainer" <#if MailerMergeForm.mergeFormTypeId?if_exists != "EMAIL">style="display:none"</#if>>
      <span class="formLabelRequired">${uiLabelMap.LabelFromEmailAddress}</span>
      <span class="formInputSpan">
        <input type="text" class="inputBox required email" name="fromEmailAddress" value="${(MailerMergeForm.fromEmailAddress)?if_exists}" size="50" maxlength="100"/>
      </span>
    </div>
    <div id="headerFooterImageContainer" <#if MailerMergeForm.mergeFormTypeId?if_exists != "PRINT"> style="display:none"</#if>>
	    <div class="formRow">
			<span class="formLabel">${uiLabelMap.LabelTemplateHeaderImageLocation}</span>
			<div class="fieldContainer">
				<span class="formInputSpan">
					<input type="hidden" id="headerImageLocationRemove" name="headerImageLocationRemove" value="N" />
					<#if MailerMergeForm.headerImageLocation?exists && MailerMergeForm.headerImageLocation != "">
		      			<#assign headerLink = MailerMergeForm.headerImageLocation>
		      			<div id="headerImageControl">
		      				<a href="javascript:void()" onclick="preview('${headerLink}')">${uiLabelMap.LabelPreview}</a> <a href="javascript:void()" onclick="hideShowUploadImage('headerImageControl', 'headerImageLocationCont', 'headerImageLocationRemove')">${uiLabelMap.LabelRemove}</a>
		      			</div>
			      		<div id="headerImageLocationCont" style="display:none">
			      			<@inputFile name="headerImageLocation" class="inputBox" />
			      		</div>
		      		<#else>
		      			<@inputFile name="headerImageLocation" class="inputBox" />
		      		</#if>
		      	</span>
		    </div>
		    <#if mergeFormId?exists>
			    <div id="topMarginContainer">
					<div class="labelRequired">${uiLabelMap.LabelTopMargin}</div>
					<div class="fieldContainer">
						<span class="formInputSpan">
					  		<input title="${uiLabelMap.TooltipTopMargin}" type="text" class="inputBox smallTextfield required number" name="topMargin" size="50" value="${(MailerMergeForm.topMargin)?if_exists}"/>
					  		<span>${uiLabelMap.LabelInches}</span>
					  		<label for="topMargin" generated="true" class="error" style="display:none;">Please enter a valid number.</label>
						</span>	
					</div>
				</div>
			</#if>
	    </div>
	    <div class="formRow">
			<span class="formLabel">${uiLabelMap.LabelTemplateFooterImageLocation}</span>
			<div class="fieldContainer">
				<span class="formInputSpan">
					<input type="hidden" id="footerImageLocationRemove" name="footerImageLocationRemove" value="N" />
					<#if MailerMergeForm.footerImageLocation?exists  && MailerMergeForm.footerImageLocation != "">
		      			<#assign footerLink = MailerMergeForm.footerImageLocation>	      		
		      			<div id="footerImageControl">
		      				<a href="javascript:void()" onclick="preview('${footerLink}')">${uiLabelMap.LabelPreview}</a> <a href="javascript:void()" onclick="hideShowUploadImage('footerImageControl', 'footerImageLocationCont', 'footerImageLocationRemove')">${uiLabelMap.LabelRemove}</a>
		      			</div>
		      			<div id="footerImageLocationCont" style="display:none"><@inputFile name="footerImageLocation" class="inputBox" /></div>
		      		<#else>
		      			<@inputFile name="footerImageLocation" class="inputBox" />
		      		</#if>	      	
		      	</span>
			</div>
			<#if mergeFormId?exists>
				<div id="bottomMarginContainer">
					<div class="labelRequired">${uiLabelMap.LabelBottomMargin}</div>
					<div class="fieldContainer">
						<span class="formInputSpan">
					  		<input title="${uiLabelMap.TooltipTopMargin}" type="text" class="inputBox smallTextfield required number" name="bottomMargin" size="50" value="${(MailerMergeForm.bottomMargin)?if_exists}"/>
					  		<span>${uiLabelMap.LabelInches}</span>
					  		<label for="topMargin" generated="true" class="error" style="display:none;">Please enter a valid number.</label>
						</span>	
					</div>
				</div>
			</#if>
	    </div>
    </div>

    <div class="formRow">
		<span class="formLabel">${uiLabelMap.CommonDescription}</span>
		<span class="formInputSpan">
			<input type="text" class="inputBox" name="description" size="50" value="${(MailerMergeForm.description)?if_exists}" maxlength="255"/>
		</span>
	</div>

    <div class="formRow">
		<#if (MailerMergeForm.mergeFormTypeId?if_exists) == "EMAIL">
			<#assign labelClass="formLabelRequired">
			<#assign fieldClass="inputBox required">
		<#else>
      		<#assign labelClass="formLabel">
      		<#assign fieldClass="inputBox">
      	</#if>
      	<span class="${labelClass}" id="subjectLabel">${uiLabelMap.PartySubject}</span>
      	<span class="formInputSpan">
        	<input type="text" class="${fieldClass}" id="subject" name="subject" size="50" value="${(MailerMergeForm.subject)?if_exists}" maxlength="255"/>
      	</span>
    </div>

    <div class="formRow">
		<span class="formLabelRequired">${uiLabelMap.LabelScheduledAt}</span>
		<span class="formInputSpan">
			<input type="text" class="inputBox smallTextfield required digits" name="scheduleAt" size="50" value="${(MailerMergeForm.scheduleAt)?if_exists}" maxlength="255"/>
			<span>${uiLabelMap.LabelNoOfDays}</span>
			<label for="scheduleAt" generated="true" class="error" style="display:none;">Please enter only digits.</label>
		</span>
	</div>

    <div class="formRow">
		<span class="formLabelRequired">${uiLabelMap.OpentapsTemplate}</span>
		<span class="formInputSpan">
			<@htmlTextArea class="required" textAreaId="mergeFormText" value=(MailerMergeForm.mergeFormText)?if_exists tagFileLocation="component://mailer/webapp/mailer/crmsfaFormEditorTags.ftl" style="width: 100%; height: 100%"/>
		</span>
	</div>

    <div class="formRow">
		<span class="formInputSpan">
			<#if mergeFormId?has_content>
				<input type="submit" class="smallSubmit" name="submitButton" value="${uiLabelMap.ButtonCommonSaveAndClose}" onClick="this.form.action='<@ofbizUrl>updateAndCloseMergeForm</@ofbizUrl>'" />
			</#if>
			<input type="submit" class="smallSubmit" name="submitButton" value="${formSubmit}" onClick="" />
			<#if mergeFormId?has_content>
				<#assign displayStyle = "" />
				<#if MailerMergeForm.mergeFormTypeId?if_exists != "PRINT">
					<#assign displayStyle="display:none;" />	
				</#if>
				<a id="previewTemplateContainer" style="${displayStyle}" class='subMenuButton' href='previewPdfTemplate?mergeFormId=${mergeFormId}'>${uiLabelMap.ButtonPreviewPdfTemplate}</a>
			</#if>
		</span>
	</div>

    <div class="spacer">&nbsp;</div>
  </form>
</div>