/*
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
 */
$(document).ready(function() {
	var forms = document.forms;

	jQuery.each(forms, function(i, form) {
		//alert(i + " - " + form.id);
		if(form.id !== undefined){
			$("#" + form.id).validate();
		}
	});
	
	/* Reset feature of find contact list member module */
	$("#resetButton").click(function(){
		var itemsStr = "";
		$("#findContactList :input[type=text]").each(function(index,item){
			item.value = "";
		});
	});
});

/** This method required to hide/disply operation on onChange event of campaign type combobox. */
function campaignTypeOnChange(value) {
	var emailAddressContainer = document.getElementById('emailAddressContainer');
	var headerFooterImageContainer = document.getElementById('headerFooterImageContainer');
	var previewTemplateContainer = document.getElementById('previewTemplateContainer');
	var fromEmailAddress = document.getElementById('fromEmailAddress');
	var headerImageLocation = document.getElementById('headerImageLocation');
	var headerImageRemove = document.getElementById('headerImageLocationRemove');
	var footerImageLocation = document.getElementById('footerImageLocation');
	var footerImageRemove = document.getElementById('footerImageLocationRemove');
	var subjectLabelObj = document.getElementById('subjectLabel');
	var subjectObj = document.getElementById('subject');
	
	if (value == "PRINT") {  	
		emailAddressContainer.style.display = "none";
		headerFooterImageContainer.style.display = "block";
		if (previewTemplateContainer) {
			previewTemplateContainer.style.display = "";
		}
		document.getElementById('headerImageLocation').value = "";
		document.getElementById('footerImageLocation').value = "";
		
		subjectLabelObj.className="formLabel";
		subjectObj.className="inputBox";
	} else if (value == "EMAIL") {
		emailAddressContainer.style.display = "block";
		headerFooterImageContainer.style.display = "none";
		if (previewTemplateContainer) {
			previewTemplateContainer.style.display = "none";
		}
		headerImageRemove.value = "Y";
		footerImageRemove.value = "Y";
		
		subjectLabelObj.className="formLabelRequired";
		subjectObj.className="inputBox required";
	} else {
		if (previewTemplateContainer) {
			previewTemplateContainer.style.display = "none";
		}
		emailAddressContainer.style.display = "none";
		headerFooterImageContainer.style.display = "none";	
		subjectLabelObj.className="formLabel";
		subjectObj.className="inputBox";	
	}
}

/* Following set of methods are required to support MergeForm page */
function preview(url){
	window.open(url,'Preview','location=0,menubar=0,resizable=0,scrollbars=0,status=0,toolbar=0',false);
}
function hideShowUploadImage(controller, target, removeField){
	var controllerObj = document.getElementById(controller);
	var targetObj = document.getElementById(target);
	var removeFieldObj = document.getElementById(removeField);

	controllerObj.style.display = "none";
	targetObj.style.display = "block";
	removeFieldObj.value = "Y";
}

