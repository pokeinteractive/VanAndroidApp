function isValidEmail(str) {

   return (str.indexOf(".") > 2) && (str.indexOf("@") > 0);


}

function onlyInputNumber(evt) {
	var charCode = '';
	var e=evt;
	if (window.event) { // IE
		charCode = e.keyCode;
	} else if (e.which) {
		// safari 4 and firefox
		charCode = e.which;
	}
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
	
}

function onlyInputCurrency(evt) {
	var charCode = '';
	var e=evt;
	if (window.event) { // IE
		charCode = e.keyCode;
	} else if (e.which) {
		// safari 4 and firefox
		charCode = e.which;
	}
	if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
	
}