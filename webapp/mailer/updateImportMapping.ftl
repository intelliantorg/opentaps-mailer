<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<div class="allSubSectionBlocks">
	<div class="form">
		<form id="updateImportMappingForm" name="updateImportMappingForm" action="<@ofbizUrl>updateImportMapping</@ofbizUrl>" method="post" enctype="multipart/form-data" class="basic-form">
			<@inputHidden name="importMapperId" value="${importMapping.importMapperId}"/>
			<table width="100%">
				<tr>
					<td>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext requiredField" text=uiLabelMap.CommonName />
							</div>
							<div class="fieldContainer">
								<@inputText name="importMapperName" size=35 class="inputBox required" default="${importMapping.importMapperName}" />
							</div>
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.LabelSampleImport />
							</div>
							<div class="fieldContainer">
								<@display class="tabletext" text='${importMapping.getRelatedOne("Content").get("contentName")} (${importMapping.contentId})' />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.LabelCreatedByUser />
							</div>
							<div class="fieldContainer">
								<@display class="tabletext" text="${importMapping.createdByUserLogin?if_exists}" />
							</div>
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.LabelLastUpdatedByUser />
							</div>
							<div class="fieldContainer">
								<@display class="tabletext" text="${importMapping.lastModifiedByUserLogin?if_exists}" />
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">
								<@display class="tableheadtext" text=uiLabelMap.CommonDescription />
							</div>
							<div class="fieldContainer">
								<@inputTextarea name="description" rows=4 cols=35 default="${importMapping.description?if_exists}"/>
							</div>
						</div>
						<div class="rowContainer">
							<div class="label">&nbsp;</div>
							<div class="fieldContainer">
								<@inputSubmit title=uiLabelMap.ButtonUpdateMapping onClick="" class=smallSubmit />
							</div>
						</div>					
					</td>
				</tr>
			</table>			
		</form>
	</div>
</div>