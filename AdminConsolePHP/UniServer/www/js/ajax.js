function doAjaxGetRequest(url) {

 // notice the use of a proxy to circumvent the Same Origin Policy. 
 new Ajax.Request(url, {
    method: 'get',  
    onSuccess: function(transport) {     
	  	var notice = $('notice');    
		if (transport.responseText.match("success")) {
			refreshThePage();
		} else {
			alert("You have linked to it.");
		}
	} 
 }); 
 
}