<@import location="component://mailer/webapp/mailer/commonFormMacros.ftl"/>
<div class="allSubSectionBlocks">
	<div class="form">
		<#assign actionURL>
			<@ofbizUrl>configureImportMapping</@ofbizUrl>
		</#assign>
		<#if contentId?exists>
			<#assign actionURL>
				<@ofbizUrl>createConfiguredMapping</@ofbizUrl>
			</#assign>
		</#if>
		<form id="configureImortMappingForm" name="configureImortMappingForm" action="${actionURL}" method="post" enctype="multipart/form-data" class="basic-form">
			<@inputHidden name="ofbizEntityName" value="${nameOfEntity}"/>
			<table width="100%">
				<tr>
					<td>
						<#if !contentId?exists>
							<div class="rowContainer">
								<div class="label">
									<@display class="tableheadtext requiredField" text=uiLabelMap.ImportSampleFile />
								</div>
								<div class="fieldContainer">
									<@inputFile name="uploadedFile" class="inputBox" /><br>
									<span>Supported file type : .xls</span>
								</div>
							</div>
							<div class="rowContainer">
								<div class="label">
									<@display class="tableheadtext" text=uiLabelMap.LabelTreatFirstRowAsHeader />
								</div>
								<div class="fieldContainer">
									<input type="checkbox" name="isFirstRowHeader" value="true"/>
								</div>
							</div>
						<#else>
							<@inputHidden name="contentId" value="${contentId}"/>
							<@inputHidden name="isFirstRowHeader" value='${isFirstRowHeader?string}'/>
							<div class="rowContainer">
								<div class="label">
									<@display class="tableheadtext requiredField" text=uiLabelMap.CommonName />
								</div>
								<div class="fieldContainer">
									<@inputText name="importMapperName" size=35 class="inputBox required"/>
								</div>
							</div>
							<div class="rowContainer">
								<div class="label">
									<@display class="tableheadtext" text=uiLabelMap.CommonDescription />
								</div>
								<div class="fieldContainer">
									<@inputTextarea name="description" rows=4 cols=35 />
								</div>
							</div>
							<div class="rowContainer">
								<div class="label">&nbsp;</div>
								<div class="fieldContainer">
									<div>
										<#list lhsColumns as lhsColumn>
											<input type="hidden" name="entityColName_${lhsColumn_index}" value='${lhsColumn.get("entityColName")}'>
											<div>
												<div style="width:150px; float:left;">${lhsColumn.get("entityColDesc")}:</div>
												<div style="float:left;">
													<select class="dropDown" name="importFileColIdx_${lhsColumn_index}" id="importFileColIdx_${lhsColumn_index}">
														<option value='_NA_'>--select--</option>
														<#list rhsColumns as rhsColumn>
															<option value='${rhsColumn}'>${rhsColumn}</option>
														</#list>
													</select>
												</div>
												<br style="clear:both" />
											</div>
										</#list>
									</div>
								</div>
							</div>
						</#if>
						<div class="rowContainer">
							<div class="label">&nbsp;</div>
							<div class="fieldContainer">
								<@inputSubmit title=uiLabelMap.ButtonConfigureMapping onClick="" class=smallSubmit />
							</div>
						</div>					
					</td>
				</tr>
			</table>			
		</form>
	</div>
</div>