<?

function isImage($filename) {

	if (trim($filename) == '') {
		return false;	
	}

	$name = explode(".", $filename);
	
	$ext = $name[sizeof($name) - 1] ;

	if ($ext == "jpg" || $ext == "jpeg" || $ext == "gif" || $ext == "png")
		return true;
	else 
		return false;	

}




?>