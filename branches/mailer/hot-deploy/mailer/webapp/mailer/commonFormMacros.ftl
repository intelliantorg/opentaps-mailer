<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>

<#macro inputText name size=30 maxlength="" default="" index=-1 password=false readonly=false onChange="" id="" ignoreParameters=false errorField="" tabIndex="" class="inputBox">
  <#if id == ""><#assign idVal = name><#else><#assign idVal = id></#if>
  <input id="${getIndexedName(idVal, index)}" class="${class}" name="${getIndexedName(name, index)}" type="<#if password>password<#else>text</#if>" size="${size}" maxlength="${maxlength}" <#if !password>value="${getDefaultValue(name, default, index, ignoreParameters)}"</#if> <#if readonly>readonly="readonly"</#if> onChange="${onChange}" <#if tabIndex?has_content>tabindex="${tabIndex}"</#if>/>
  <#if errorField?has_content><@displayError name=errorField index=index /></#if>
</#macro>

<#macro tooltip text="" class="tooltip"><#if text?has_content><span class="tabletext"><span class="${class}">${text}</span></span></#if></#macro>


<#macro displayValidationError name class="error">
	<div>
		<label for="${name}" generated="true" class="${class}">This field is required.</label>
	</div>
</#macro>

<#macro inputStatusItemSelect list=[] name="statusId" defaultStatusId="" id="" class="dropDown">
	<#if id == ""><#assign idVal = name><#else><#assign idVal = id></#if>
  	<#assign statusItems = list />
  	<select id="${getIndexedName(idVal, index)}" name="${getIndexedName(name, index)}" class="${class}">
		<#list statusItems as option>
  			<#if option.statusId == defaultStatusId><#assign selected = "selected=\"selected\""><#else><#assign selected = ""></#if>
  			<option ${selected} value="${option.statusId}">${option.description}</option>
		</#list>
  	</select>
</#macro>

<#macro inputFile name size=30 maxlength="" default="" index=-1 onChange="" id="" errorField="" tabIndex="" class="inputBox">
	<#if id == ""><#assign idVal = name><#else><#assign idVal = id></#if>
  	<input id="${getIndexedName(idVal, index)}" name="${getIndexedName(name, index)}" type="file" size="${size}" maxlength="${maxlength}" class="${class}" onChange="${onChange}" <#if tabIndex?has_content>tabindex="${tabIndex}"</#if>/>
</#macro>

<#macro inputSelect name list key="" displayField="" default="" index=-1 required=true defaultOptionText="" onChange="" id="" ignoreParameters=false errorField="" tabIndex="" class="dropDown" listEmptyText=uiLabelMap.TextEmpty >
  <#if key == ""><#assign listKey = name><#else><#assign listKey = key></#if>
  <#if id == ""><#assign idVal = name><#else><#assign idVal = id></#if>
  <#assign defaultValue = getDefaultValue(name, default, index, ignoreParameters)>
  <select id="${getIndexedName(idVal, index)}" name="${getIndexedName(name, index)}" class="${class}" onChange="${onChange}" <#if tabIndex?has_content>tabindex="${tabIndex}"</#if>>
    <#if list?exists && list?size gt 0>
        <#if !required><option value="">${defaultOptionText}</option></#if>
	    <#list list as option>
	      <#if option.get(listKey) == defaultValue || listKey == defaultValue><#assign selected = "selected=\"selected\""><#else><#assign selected = ""></#if>
	      <option ${selected} value="${option.get(listKey)}">
	        <#if displayField==""><#nested option><#else>${option.get(displayField)?if_exists}</#if>
	      </option>
	    </#list>
	<#else>
		<#if listEmptyText?exists && listEmptyText?has_content>
			<option value="">${listEmptyText}</option>
		</#if>
    </#if>
  </select>
  <#if errorField?has_content><@displayError name=errorField index=index /></#if>
</#macro>

<#-- Parameter 'form' is deprecated and leaves here for compatibility w/ existent code. Don't use it any more. -->
<#macro inputDateTime name form="" default="" popup=true weekNumbers=false onUpdate="" onDateStatusFunc="" linkedName="" delta=0 ignoreParameters=false errorField="" class="inputBox">
  <#assign defaultValue = getDefaultValue(name, default, -1, ignoreParameters)>
  <#assign defaultTime = Static["org.opentaps.common.util.UtilDate"].timestampToAmPm(getLocalizedDate(defaultValue, "DATE_TIME"), Static["org.ofbiz.base.util.UtilHttp"].getTimeZone(request), Static["org.ofbiz.base.util.UtilHttp"].getLocale(request)) />
  <input type="hidden" name="${name}_c_compositeType" value="Timestamp"/>
      <input id="${name}_c_date" type="text" class="${class}" name="${name}_c_date" size="10" maxlength="10" value="${defaultTime.date?if_exists}"/>
      <#if defaultTime.date?exists>
        <#assign lookupDefault = default>
      <#else>
        <#assign lookupDefault = "">
      </#if>
      <a href="javascript:opentaps.toggleClass(document.getElementById('${name}-calendar-placeholder'), 'hidden');"><img id="${name}-button" src="/images/cal.gif" alt="View Calendar" title="View Calendar" border="0" height="16" width="16"/></a>
      &nbsp;
      <select name="${name}_c_hour" class="inputBox">
        <#list 1..12 as hour>
          <option <#if defaultTime.hour?default(12) == hour>selected="selected"</#if> value="${hour}">${hour}</option>
        </#list>
      </select>
      :
      <select name="${name}_c_minutes" class="inputBox">
        <#list 0..59 as min>
          <option <#if defaultTime.minute?default(-1) == min>selected="selected"</#if> value="${min}">${min?string?left_pad(2,"0")}</option>
        </#list>
      </select>
      <select name="${name}_c_ampm" class="inputBox">
        <option value="AM">AM</option>
        <option <#if defaultTime.ampm?default("") == "PM">selected="selected"</#if> value="PM">PM</option>
      </select>
      <table id="${name}-calendar-placeholder" style="border: 0px; width: auto;" class="hidden"></table>
      <#if errorField?has_content><@displayError name=errorField /></#if>
      <script type="text/javascript">
      /*<![CDATA[*/
        function ${name}_onDateChange(calendar) {
          if (calendar.dateClicked) {
            var input = document.getElementById('${name}_c_date');
            if (input) {
              input.value = opentaps.formatDate(calendar.date, '${Static["org.ofbiz.base.util.UtilDateTime"].getJsDateTimeFormat(Static["org.ofbiz.base.util.UtilDateTime"].getDateFormat(locale))}');
            }
            opentaps.addClass(document.getElementById('${name}-calendar-placeholder'), 'hidden');
          }
        };
        <#if linkedName?has_content && !onUpdate?has_content>
        function ${name}_calcAndApplyDifference(calendar) {
          var linkedInput = document.getElementById('${linkedName}_c_date');
          if (!linkedInput || linkedInput.nodeName != 'INPUT') {
            alert('Linked field with name ${linkedName} isn\'t accessible or have wrong type!');
            return;
          }
      
          var date = calendar.date;
          var time = date.getTime();
          time += (Date.DAY * ${delta});
      
          var linkedDate = new Date(time);
          linkedInput.value = opentaps.formatDate(linkedDate, '${Static["org.ofbiz.base.util.UtilDateTime"].getJsDateTimeFormat(Static["org.ofbiz.base.util.UtilDateTime"].getDateFormat(locale))}');
        };
        </#if>
        function currentDtFmt() {
	    	return '${Static["org.ofbiz.base.util.UtilDateTime"].getJsDateTimeFormat(Static["org.ofbiz.base.util.UtilDateTime"].getDateFormat(locale))}';  
		};
        Calendar.setup(
          {
          <#if !popup>
            flat: "${name}-calendar-placeholder",
            flatCallback: ${name}_onDateChange,
          <#else>
            inputField: "${name}_c_date",
            ifFormat: "${Static["org.ofbiz.base.util.UtilDateTime"].getJsDateTimeFormat(Static["org.ofbiz.base.util.UtilDateTime"].getDateFormat(locale))}",
            button: "${name}-button",
            align: "Bl",
          </#if>
          <#if onUpdate?has_content>
            onUpdate: ${onUpdate},
          </#if>
          <#if linkedName?has_content && !onUpdate?has_content>
            onUpdate: ${name}_calcAndApplyDifference,
          </#if>
          <#if weekNumbers?is_boolean>
            weekNumbers: <#if weekNumbers>true<#else>false</#if>,
          </#if>
            showOthers: true,
            cache: true
          }
      );
      /*]]>*/
      </script>
</#macro>

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

<#macro htmlTextArea textAreaId name="" value="" tagFileLocation="" rows=20 cols=80 class="" style="">
  <textarea name="${name?has_content?string(name, textAreaId)}" id="${textAreaId}" class="${class}" rows="${rows}" cols="${cols}" style="${style}">${value}</textarea>
  <#if tagFileLocation?has_content>
    <@import location = tagFileLocation/>
  </#if>
  <script type="text/javascript" src="/mailer/js/fckeditor/fckeditor.js"></script>
	<script type="text/javascript">
    opentaps.addOnLoad(function() {
      <#if tagFileLocation?has_content>
        tags = [];
        <#list getTags()?default([]) as tagMap>
          <#list tagMap?default({})?keys as tag>
            tags.push({ 'tag' : '${tag}' , 'description' : '${tagMap[tag]?js_string}' });
          </#list>
        </#list>
        insertTagsLabel = '${uiLabelMap.OpentapsHtmlEditorInsertTagsLabel?js_string}';
      </#if>
      insertTags = ${tagFileLocation?has_content?string};
      var oFCKeditor = new FCKeditor( '${textAreaId}' ) ;
      oFCKeditor.BasePath = '/mailer/js/fckeditor/' ;
      oFCKeditor.Height	= 400 ;
      oFCKeditor.ToolbarSet = '${tagFileLocation?has_content?string("OpentapsFormMerge", "OpentapsBasic")}';
      oFCKeditor.ReplaceTextarea() ;
    });
	</script>
</#macro>
