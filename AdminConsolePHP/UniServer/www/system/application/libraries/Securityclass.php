<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Securityclass {

	function loginToSession($member) {
	
   		$CI = &get_instance();
	   	$CI->load->library('session');
	   	
   		$CI->session->set_userdata("member_id", $member->member_id);
		$CI->session->set_userdata("member_name", $member->member_name);
		$CI->session->set_userdata("memberType", $member->memberType);
	}
    
}

?>