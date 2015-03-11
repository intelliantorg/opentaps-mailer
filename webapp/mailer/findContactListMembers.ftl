<#--
 * Copyright (c) Intelliant
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Intelliant (iaerp@intelliant.net)
 *
-->
<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>

<div class="form filterlist">
	<form id="findContactList" name="findContactList" action="<@ofbizUrl>filterContactListMembers</@ofbizUrl>" method="post">

		<@inputHidden name="contactListId" value=contactList.contactListId />
		<table width="100%">
			<tr>
				<#assign noOfColumns = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("mailer", "mailer.filterRecipients.noOfColumns")>
				<#if !noOfColumns?exists>
					<#assign noOfColumns = 2/> 
				</#if>
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
				<td colspan="${noOfColumns?number*2}" align="center">
					<input class="subMenuButton" type="submit" name="submit" value="${uiLabelMap.ButtonFind}" />
					<input class="subMenuButton" type="button" id="resetButton" name="resetButton" value="${uiLabelMap.ButtonReset}" />
				</td>
			</tr>
		</table>
	</form>
</div>
