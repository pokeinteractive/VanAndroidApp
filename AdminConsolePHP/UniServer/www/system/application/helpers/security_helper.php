<?php

function setDesktop() {

	$CI = &get_instance();
	$CI->load->library('session');
	$CI->session->set_userdata("desktop", "0");

}

function setMobile() {

	$CI = &get_instance();
	$CI->load->library('session');
	$CI->session->set_userdata("desktop", "1");

}

function resetDesktop() {

	$CI = &get_instance();
	$CI->load->library('session');
	$CI->session->set_userdata("desktop", "");

}

function isDesktop() {

	$CI = &get_instance();
	$CI->load->library('session');
	$desktop = $CI->session->userdata("desktop");
	if ($desktop && $desktop >= 1)
		return false;
	else 
		return true;
}


function isLogin() {

	$CI = &get_instance();
	$CI->load->library('session');
  
	$session_member_id = $CI->session->userdata("member_id"); // change to get usrID
  
	if ($session_member_id == "") 
		return false;
	else 
		return true;	  

}

function checkLoginWithPageForward($target_url) {

	$CI = &get_instance();
	$CI->load->library('session');
  
	$session_member_id = $CI->session->userdata("member_id"); // change to get usrID
  
  	$host = $_SERVER['HTTP_HOST'];
  	
	if ($session_member_id == "") {
		$CI->session->set_userdata("url", $target_url);
		header("Location: http://$host/index.php/openform/login");
	}

}

function checkMatchLoginWithPageForward($target_url, $member_id) {

	$CI = &get_instance();
	$CI->load->library('session');
  
	$session_member_id = $CI->session->userdata("member_id"); // change to get usrID
	
	if ($session_member_id == "" || $member_id <> $session_member_id) {
				
		$host = $_SERVER['HTTP_HOST'];
  	
		$CI->session->set_userdata("url", $target_url);
		header("Location: http://$host/index.php/openform/login");
	
	}

}

function isWriter($member_id) {

	$CI = &get_instance();
	$CI->load->library('session');
  
	$session_member_id = $CI->session->userdata("member_id"); // change to get usrID
	
	if ($session_member_id == $member_id) 
		return true;
	else 
		return false;	  

}

function showUserName() {
  $CI = &get_instance();
  $CI->load->library('session');
  $session_member_name = $CI->session->userdata("member_name");
  if (strlen($session_member_name) > 22) {
  	echo mb_substr($session_member_name, 0, 20) . "..";
  } else {
	echo $session_member_name;
  }

}

function showUserId() {
  $CI = &get_instance();
  $CI->load->library('session');
  $session_member_id = $CI->session->userdata("member_id"); // change to get usrID 
  echo $session_member_id ;

}

function getCurrentUserId() {
  $CI = &get_instance();
  $CI->load->library('session');
  $session_member_id = $CI->session->userdata("member_id"); // change to get usrID 
  return $session_member_id ;

}

function getUserType() {
  $CI = &get_instance();
  $CI->load->library('session');
  return $CI->session->userdata("memberType"); // change to get usrID 
}

function isCompany() {

  if ( "C" == getUserType()) {
  	return true;
  }
  return false;
  	 
}
?>