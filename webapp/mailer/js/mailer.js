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
	var footerImageLocation = document.getElementById('footerImageLocation');
	
	if(value == "PRINT"){  	
		emailAddressContainer.style.display = "none";
		headerFooterImageContainer.style.display = "block";
		document.getElementById('headerImageLocation').value = "";
		document.getElementById('footerImageLocation').value = "";
	}else{
		emailAddressContainer.style.display = "block";
		headerFooterImageContainer.style.display = "none";
		document.getElementById('fromEmailAddress').value = "";
	}
}