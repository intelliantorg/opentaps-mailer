<#if hasUpdatePermission?exists>
  <#assign addLink = "<a class='subMenuButton' href='addContactListPartiesForm?contactListId=" + contactList.contactListId + "'>" + uiLabelMap.CommonAddNew + "</a>">
  <#if contactList.contactMechTypeId?default("none") == "POSTAL_ADDRESS">
    <#assign addDomestic = "<a class='subMenuButton' href='addNewCatalogRequestsToContactList?contactListId=" + contactList.contactListId + "&includeCountryGeoId=" + configProperties.defaultCountryGeoId + "'>" + uiLabelMap.CrmCatalogRequestAddDomestic + "</a>">
    <#assign addForeign = "<a class='subMenuButton' href='addNewCatalogRequestsToContactList?contactListId=" + contactList.contactListId + "&excludeCountryGeoId=" + configProperties.defaultCountryGeoId + "'>" + uiLabelMap.CrmCatalogRequestAddForeign + "</a>">
  </#if>
</#if>

<a name="ListContactListParties"></a>
<div class="subSectionHeader">
  <div class="subSectionTitle">${uiLabelMap.CrmContactListParties}</div>
  <div class="subMenuBar">
  	<a class="subMenuButton" href="importContactListForm?contactListId=${contactList.contactListId}" >Import List</a>${addLink?if_exists}${addDomestic?if_exists}${addForeign?if_exists}</div>
</div>
