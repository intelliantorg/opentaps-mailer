<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<form name="findContactList" action="<@ofbizUrl>viewContactList</@ofbizUrl>" method="get">
<@inputHidden name="contactListId" value=contactList.contactListId />
<table>
	<tr>
		<td>Fist Name:</td>
		<td></td>
		<td><input type="text" name="firstName" /></td>
	</tr>
	<tr>
		<td>Last Name:</td>
		<td></td>
		<td><input type="text" name="lastName" /></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td><input type="submit" name="submit" value="Find" /></td>
	</tr>
</table>
</form>