<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<@import location="component://opentaps-common/webapp/common/includes/lib/opentapsFormMacros.ftl"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Content-Script-Type" content="text/javascript"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <title><#if pageTitleLabel?exists>${uiLabelMap.get(pageTitleLabel)} |</#if> ${configProperties.get(opentapsApplicationName+".title")}</title>

    <#assign appName = Static["org.ofbiz.base.util.UtilHttp"].getApplicationName(request)/>

    <#list Static["org.opentaps.common.util.UtilConfig"].getStylesheetFiles(opentapsApplicationName) as stylesheet>
      <link rel="stylesheet" href="<@ofbizContentUrl>${stylesheet}</@ofbizContentUrl>" type="text/css"/>
    </#list>

    <#-- here is where the dynamic CSS goes, for changing theme color, etc. To activate this, define sectionName = 'section' -->
    <#if sectionName?exists>
      <#assign bgcolor = Static["org.opentaps.common.util.UtilConfig"].getSectionBgColor(opentapsApplicationName, sectionName)/>
      <#assign fgcolor = Static["org.opentaps.common.util.UtilConfig"].getSectionFgColor(opentapsApplicationName, sectionName)/>
      <style type="text/css">
h1, h2, .gwt-screenlet-header, .sectionHeader, .subSectionHeader, .subSectionTitle, .formSectionHeader, .formSectionHeaderTitle, .screenlet-header, .boxhead, .boxtop, div.boxtop {color: ${fgcolor}; background-color: ${bgcolor};}
div.sectionTabBorder, ul.sectionTabBar li.sectionTabButtonSelected a {color: ${fgcolor}; background-color: ${bgcolor};}
      </style>

      <script type="text/javascript">
        var bgColor = '${bgcolor?default("")?replace("#", "")}';
      </script>
      <script src="/${appName}/control/javascriptUiLabels.js" type="text/javascript"></script>

      <#assign javascripts = Static["org.opentaps.common.util.UtilConfig"].getJavascriptFiles(opentapsApplicationName, locale)/>

      <#if layoutSettings?exists && layoutSettings.javaScripts?has_content>
        <#assign javascripts = javascripts + layoutSettings.javaScripts/>
      </#if>

      <#list javascripts as javascript>
        <#if javascript?matches(".*dojo.*")>
          <#-- Unfortunately, due to Dojo's module-loading behaviour, it must be served locally -->
          <script src="${javascript}" type="text/javascript" djConfig="isDebug: false, parseOnLoad: true <#if Static["org.ofbiz.base.util.UtilHttp"].getLocale(request)?exists>, locale: '${Static["org.ofbiz.base.util.UtilHttp"].getLocale(request).getLanguage()}'</#if>"></script>
        <#else>
          <script src="<@ofbizContentUrl>${javascript}</@ofbizContentUrl>" type="text/javascript"></script>
        </#if>
      </#list>
    </#if>

    <#if gwtScripts?exists>
      <meta name="gwt:property" content="locale=${locale}"/>
    </#if>
</head>


<body>
  <#assign callInEventIcon = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("asterisk.properties", "asterisk.icon.callInEvent")>
  <#if gwtScripts?exists>
    <#list gwtScripts as gwtScript>
      <@gwtModule widget=gwtScript />
    </#list>
    <#-- Bridge between server data and GWT widgets -->
    <script type="text/javascript" language="javascript">
      <#-- expose base permissions to GWT -->
      <#if user?has_content>
        var securityUser = new Object();
        <#list user.permissions as permission>
          securityUser["${permission}"] = true;
        </#list>
      </#if>
      <#-- set up the OpentapsConfig dictionary (see OpentapsConfig.java) -->
      var OpentapsConfig = {
      <#if configProperties.defaultCountryCode?has_content>
        defaultCountryCode: "${configProperties.defaultCountryCode}",
      </#if>
      <#if configProperties.defaultCountryGeoId?has_content>
        defaultCountryGeoId: "${configProperties.defaultCountryGeoId}",
      </#if>
      <#if configProperties.defaultCurrencyUomId?has_content>
        defaultCurrencyUomId: "${configProperties.defaultCurrencyUomId}",
      </#if>
      <#if callInEventIcon?has_content>
        callInEventIcon: "${callInEventIcon}",
      </#if>      
        applicationName: "${opentapsApplicationName}"
      };
    </script>
  </#if>

  <div style="float: left; margin-left: 10px; margin-top: 5px; margin-bottom: 10px;">
    <img alt="${configProperties.get(opentapsApplicationName+".title")}" src="<@ofbizContentUrl>${configProperties.get("opentaps.logo")}</@ofbizContentUrl>"/>
  </div>
  <div align="right" style="margin-left: 300px; margin-right: 10px; margin-top: 10px;">

    <div class="insideHeaderText">
      <#if person?has_content>
        ${uiLabelMap.CommonWelcome}&nbsp;${person.firstName?if_exists}&nbsp;${person.lastName?if_exists}
      <#elseif partyGroup?has_content>
        ${uiLabelMap.CommonWelcome}&nbsp;${partyGroup.groupName?if_exists}
      <#else>
      </#if>
      <#if requestAttributes.userLogin?has_content>
      	<div>
			<a class="buttontext" href="<@ofbizUrl>myMessages</@ofbizUrl>">Inbox</a><span id="numMessages"></span>
        	<a href="<@ofbizUrl>logout</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonLogout}</a>
        </div>
      </#if>
    </div>
    <#if applicationSetupFacility?has_content>
      <div class="insideHeaderSubtext">
        <b>${uiLabelMap.OpentapsWarehouse}</b>:&nbsp;${applicationSetupFacility.facilityName}&nbsp; (<@displayLink text="${uiLabelMap.CommonChange}" href="selectFacilityForm"/>)
      </div>
    </#if>
    <#if applicationSetupOrganization?has_content>
      <div class="insideHeaderSubtext">
        <b>${uiLabelMap.ProductOrganization}</b>:&nbsp;${applicationSetupOrganization.groupName}&nbsp; (<@displayLink text="${uiLabelMap.CommonChange}" href="selectOrganizationForm"/>)
      </div>
    </#if>
    <div class="gwtAsteriskNotification" id="gwtAsteriskNotification">
    </div>
    <#--<#assign helpUrl = Static["org.opentaps.common.util.UtilCommon"].getUrlContextHelpResource(delegator, appName, parameters._CURRENT_VIEW_, screenState?default(""))!/>
    <#if helpUrl?exists && helpUrl?has_content>
      <div class="liveHelp"><a class="liveHelp" href="${helpUrl}" target="_blank" title="${uiLabelMap.OpentapsHelp}"><img src="/opentaps_images/buttons/help_ofbiz_svn.gif" width="20" height="20"/></a></div>
    </#if>-->
  </div>
  <div class="spacer"></div>