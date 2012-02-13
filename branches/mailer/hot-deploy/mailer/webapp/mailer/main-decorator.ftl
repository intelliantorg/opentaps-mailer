<#include "header.ftl">
${screens.render("component://opentaps-common/widget/screens/common/CommonScreens.xml#applicationTabBar")}
<div id="nav1">
  ${screens.render("component://opentaps-common/widget/screens/common/CommonScreens.xml#navigationHistory")}
</div>

<div class="centerarea">
  <#include "messages.ftl">

  <div class="contentarea">

    <#if requiredPermission?exists>
      <#if !allowed?exists || (allowed?exists && allowed)>
        <#assign allowed = Static["org.opentaps.common.security.OpentapsSecurity"].checkSectionSecurity(opentapsApplicationName, sectionName, requiredPermission, request)>
      </#if>
    </#if>

    <#if !userLogin?exists>
      <#-- always render normally when no login exists, that way the login page gets rendered first thing. 
      In practice main-body should never happen because OFBIZ will always intercept you and re-direct to login page first -->
      ${screens.render("component://opentaps-common/widget/screens/common/CommonScreens.xml#main-body")}
    <#elseif allowed?exists && !allowed>
      <div class="tableheadtext">${uiLabelMap.OpentapsError_PermissionDenied}</div>
    <#elseif applicationSetupScreen?exists && !session.getAttribute("applicationContextSet")?default(false)>
      ${screens.render(applicationSetupScreen)}
    <#else>
      ${screens.render("component://opentaps-common/widget/screens/common/CommonScreens.xml#main-body")}
    </#if>

  </div>
</div>

</body>
</html>
