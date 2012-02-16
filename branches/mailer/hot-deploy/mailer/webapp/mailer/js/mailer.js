$(document).ready(function() {
	var forms = document.forms;

	jQuery.each(forms, function(i, form) {
		//alert(i + " - " + form.id);
		if(form.id !== undefined){
			$("#" + form.id).validate();
		}
	});
});

/** This method required to hide/disply operation on onChange event of campaign type combobox. */
function campaignTypeOnChange(value){
	var emailAddressContainer = document.getElementById('emailAddressContainer');
	var headerFooterImageContainer = document.getElementById('headerFooterImageContainer');
	
	var fromEmailAddress = document.getElementById('fromEmailAddress');
	var headerImageLocation = document.getElementById('headerImageLocation');
	var haderImageRemove = document.getElementById('headerImageLocationRemove');
	var footerImageLocation = document.getElementById('footerImageLocation');
	var footerImageRemove = document.getElementById('footerImageLocationRemove');
	
	if(value == "PRINT"){  	
		emailAddressContainer.style.display = "none";
		headerFooterImageContainer.style.display = "block";
		document.getElementById('headerImageLocation').value = "";
		document.getElementById('footerImageLocation').value = "";
	}else{
		emailAddressContainer.style.display = "block";
		headerFooterImageContainer.style.display = "none";
		document.getElementById('fromEmailAddress').value = "";
		haderImageRemove.value = "Y";
		footerImageRemove.value = "Y";
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

