<?php

class Member extends Base_Controller {

	function rootadminlogin($member_id) {
		$this->load->model('Memberdb');	
		$this->load->library('session');		
		$member = $this->Memberdb->getMember($member_id);
		
		$this->load->library('securityclass');
		$this->securityclass->loginToSession($member);
		$this->profile($member->member_id);
		
	}
	
	// =====================================

  	function profile($member_id = 0) {
		$this->load->library('session');

	  	$this->load->model('Memberdb');
	  	$this->load->model('Subject');
	  	$this->load->model('Servicedb');
	  	$this->load->model('Photo');	  	
	  			
		$data['editMode'] = false;
  		$data['member'] = $this->Memberdb->getMember($member_id);
		
  		$session_member_id = $this->session->userdata("member_id"); // change to get usrID  	
  		if ($member_id == $session_member_id) {
	  		$data['editMode'] = true;

			// default show the current login user...
			if ($member_id == 0) {
				$member_id = $session_member_id;
			}

			// put the marked service list
			$this->load->model('Linkage');
			$right['showLogin'] = true;
	  		$right['markedList'] = $this->Linkage->getMarkedListByMember($member_id);	
	  		$memberData = $this->Memberdb->getMemberData($member_id);
	  		
	  		$data['member']->service_count = $memberData->service_count;
	  		$data['member']->comment_count = $memberData->comment_count;
	  		
  		} else {
  			if ($data['member']->memberType == 'U') {
  				$right['showPMBox'] = true;
  			} 
  			
  		}

  		$data['serviceList'] = $this->Servicedb->getServiceListByMember($member_id);
  		 		
		// pageTitle
		$header['pageTitle'] = $data['member']->member_name;
   		
   		$this->load->view("header", $this->headerTitle);
		$left['subjectList'] = $this->Subject->getCatalogList();
   		$this->load->view("leftSection", $left);  		
		$this->load->view('member/profile', $data);
		
		if ($data['member']->memberType == 'C')
			$right['photoList'] = $this->Photo->getPhotoListByMember($member_id);
			
		$this->load->view("rightSection", $right);
		
		$this->load->view("footer");  	
		
  	}
  	
  	function memberProduct($member_id) {
  	
		$this->load->model('Memberdb');
		$this->load->model('Subject');
		$this->load->model('Selldb');
		
  		$data['member'] = $this->Memberdb->getMember($member_id);
	  	$data['productList'] = $this->Selldb->getProductListByMember($member_id);

		$header['menu'] = "memberProduct";
		$this->load->view("header", $this->headerTitle);
		$left['subjectList'] = $this->Subject->getCatalogList();
   		$this->load->view("leftSection", $left);  		
		$this->load->view('member/memberProduct', $data);

		$this->load->view("rightSection", $right);
		$this->load->view("footer");
		
	}
  	
  	
  	function showMemberComment() {
  		$this->load->library('session');
  		$session_member_id = $this->session->userdata("member_id"); // change to get usrID  
  		$this->load->model('Subject');
  		
  		if ($session_member_id) {	
  			$this->load->model('Memberdb');
  			$commentList = $this->Memberdb->getMemberComment($session_member_id);
  			$data['commentList'] = $commentList;
  			$data['member'] = $this->Memberdb->getMember($session_member_id);
  		
	  		$memberData = $this->Memberdb->getMemberData($session_member_id);
	  		
	  		$data['member']->service_count = $memberData->service_count;
	  		$data['member']->comment_count = $memberData->comment_count;	
  		}
  		
  		$header['pageTitle'] = $data['member']->member_name;
  		
  		$this->load->view("header", $this->headerTitle);
		$left['subjectList'] = $this->Subject->getCatalogList();
   		$this->load->view("leftSection", $left);  		
		$this->load->view('member/commentList', $data);

		//$right['photoList'] = $this->Photo->getPhotoListByMember($member_id);
		$this->load->view("rightSection", $data);
		
		$this->load->view("footer");  	
  		
  		
  	}
  	
  	function previewProfileChange() {


	  	// 	check login
  		checkLoginWithPageForward("mainact");
    		
  		$this->load->library('session');
  		$session_member_id = $this->session->userdata("member_id"); // change to get usrID  
  		
  		if ($session_member_id) {
  			
  			$this->load->model('Memberdb');
	  		$member = $this->Memberdb->getMember($session_member_id);
	  		
	  		$obj = $this->_convertObj($_POST);
	  		
	  		$obj->memberType = $member->memberType;
	  		
			$obj->photo = "";
			// check for update user photo =====================
			// add file to directory
			if ((($_FILES["mphoto"]["type"] == "image/gif") || ($_FILES["mphoto"]["type"] == "image/jpeg") || ($_FILES["mphoto"]["type"] == "image/png")
				|| ($_FILES["mphoto"]["type"] == "image/pjpeg")) && ($_FILES["mphoto"]["size"] < 500000))
			{
			
			  if ($_FILES["photo"]["error"] > 0) {
			    echo "Return Code: " . $_FILES["photo"]["error"] . "<br />";
		      } else {
		      
		      	$filename = $_FILES["mphoto"]["name"];
		      	if ( mb_strlen($_FILES["mphoto"]["name"]) >= 20 )
		      		$filename = mb_substr(mb_strlen($_FILES["mphoto"]["name"] - 20));
		      
		      	$obj->photo = date('ydhms') . rand() . $filename;
		      	$obj->photo = date('yd') . md5($obj->photo);
		      	
		      	if ($_FILES["mphoto"]["type"] == "image/gif")
		      		 $obj->photo = $obj->photo . ".gif";	 
		      	else if ($_FILES["mphoto"]["type"] == "image/png")
		      		 $obj->photo = $obj->photo . ".png";	 
		      	else if ($_FILES["mphoto"]["type"] == "image/jpeg" || $_FILES["mphoto"]["type"] == "image/pjpeg")
		      		 $obj->photo = $obj->photo . ".jpg";	 
		      	
			    move_uploaded_file($_FILES["mphoto"]["tmp_name"], "upload/" . $obj->photo);
	
			  }
			}
			
			// original no photo and no photo upload
			if ( !($member->photo) && !($obj->photo) ) {
				$obj->photo = "no-profile-photo.jpg";
			} else if ( ($member->photo) && !($obj->photo) ) {
				$obj->photo = $member->photo;
			}
			
			//===============================  	
			
	  		$obj->member_id = $session_member_id;
			if ($obj->member_id <> "" && trim($obj->email) <> "") {
				$obj->description = $_POST['about_me'];
			}
			
			$data["member"] = $obj;
			
			
			// show the preview Page...
			$this->load->view("header", $this->headerTitle);     	
			$this->load->view('member/preview', $data);
			$this->load->view("footer");  	
			
		
		} else {
	  		// check login
	  		checkLoginWithPageForward("member/updateProfile/");
		}	
				
		
  	}
  	
  	function updateProfile() {

	  	// 	check login
  		checkLoginWithPageForward("mainact");
  
		$this->load->library('session');
  		$session_member_id = $this->session->userdata("member_id"); // change to get usrID  	

		if ($session_member_id) {
			$this->load->model('Memberdb');
			$this->load->model('Photo');	
			
	  		$data['member'] = $this->Memberdb->getMember($session_member_id);
	  		
			// pageTitle
			$this->headerTitle['pageTitle'] = $data['member']->member_name;
	
	   		$this->load->view("header", $this->headerTitle);
			$this->load->view('member/updateProfile', $data);
			
			//$right['photoList'] = $this->Photo->getPhotoListByMember($session_member_id);
			$this->load->view("rightSection", $right);
			
			$this->load->view("footer");  	  		
			
		} else {
	  		// check login
	  		checkLoginWithPageForward("member/updateProfile/");
		}
  	}

  	function signUpType() {

		// pageTitle
		$data['type'] = "";
   		$this->load->view("header", $this->headerTitle);     	
		$this->load->view('signUpType', $data);
		$this->load->view("footer");  
			
  	}
  	
  	function signUp($memberType = "U") {

		if ($memberType == "company") {
			$data['isServiceProvider'] = true;
   		} else {
   			$data['isServiceProvider'] = false;
   		}

		// pageTitle
		
   		$this->load->view("header", $this->headerTitle);     	
		$this->load->view('signUp', $data);
		$this->load->view("footer");  
			
  	}
  	
  	function memberToAdd() {

		$this->load->helper(array('form', 'url'));
		$this->load->library('form_validation');

		$this->form_validation->set_error_delimiters('<div class="error"><font color="red">', '</font></div>');
		  	
	  	$isValid = $this->form_validation->run('signUp');
		
		if ($_POST['password'] <> $_POST['password_confirmation']) {
			$this->form_validation->set_message('email', 'E-mail is not matched');			
			$isValid =false;
		}
	
		if ($_POST['password'] <> $_POST['password_confirmation']) {
			$this->form_validation->set_message('password', 'Password is not matched');			
			$isValid =false;
		}			

		$this->load->model('Memberdb');
		if ($_POST['email'] <> $_POST['email_confirmation']) {
			$this->form_validation->set_message('password', 'Email is not matched');			
			$isValid =false;
		} else {
			
			$member = $this->Memberdb->getMemberByEmail($_POST['email']);	
			
			if ($member && $member->member_id > 0)	{
				// member already exist, return false
				$this->form_validation->set_message('email', 'Email has been already registered');			
				$isValid =false;
			}
		}		
			
		if ($isValid) {
			
			$obj = $this->_convertObj($_POST);
			$result = $this->Memberdb->insertMember($obj);
			
			if ($result) {
				$member = $this->Memberdb->getMemberByEmail($obj->email);
				$this->load->library('securityclass');
				$this->securityclass->loginToSession($member);

				$this->profile($member->member_id);
			} else {
				$this->form_validation->set_message('email', 'E-mail is not correct.');	
				$this->signUp();
			}
		} else {
			$this->signUp();
		}
  	
  	}
  	
  	
  	
  	function updateMemberContact() {

	  	// 	check login
  		checkLoginWithPageForward("mainact");
  
		$this->load->library('session');
  		$session_member_id = $this->session->userdata("member_id"); // change to get usrID  

		$obj = $this->_convertObj($_POST);

//		$obj->photo = "";
//		// check for update user photo =====================
//		// add file to directory
//		if ((($_FILES["mphoto"]["type"] == "image/gif") || ($_FILES["mphoto"]["type"] == "image/jpeg") || ($_FILES["mphoto"]["type"] == "image/png")
//			|| ($_FILES["mphoto"]["type"] == "image/pjpeg")) && ($_FILES["mphoto"]["size"] < 500000))
//		{
//		
//		  if ($_FILES["photo"]["error"] > 0) {
//		    echo "Return Code: " . $_FILES["photo"]["error"] . "<br />";
//	      } else {
//	      
//	      	$filename = $_FILES["mphoto"]["name"];
//	      	if ( mb_strlen($_FILES["mphoto"]["name"]) >= 20 )
//	      		$filename = mb_substr(mb_strlen($_FILES["mphoto"]["name"] - 20));
//	      
//	      	$obj->photo = date('ydhms') . rand() . $filename;
//	      	$obj->photo = date('yd') . md5($obj->photo);
//	      	
//	      	if ($_FILES["mphoto"]["type"] == "image/gif")
//	      		 $obj->photo = $obj->photo . ".gif";	 
//	      	else if ($_FILES["mphoto"]["type"] == "image/png")
//	      		 $obj->photo = $obj->photo . ".png";	 
//	      	else if ($_FILES["mphoto"]["type"] == "image/jpeg" || $_FILES["mphoto"]["type"] == "image/pjpeg")
//	      		 $obj->photo = $obj->photo . ".jpg";	 
//	      	
//		    move_uploaded_file($_FILES["mphoto"]["tmp_name"], "upload/" . $obj->photo);
//
//		  }
//		}
//		//===============================  	
//		
  		$obj->member_id = $session_member_id;
		$this->load->model('Memberdb');
		

		if ($obj->member_id <> "" && trim($obj->email) <> "") {
			$this->Memberdb->updateMember($obj);
			
	  		$obj->description = $_POST['about_me'];
			$this->Memberdb->updateMemberDescription($obj);

		}
		
		$this->profile($session_member_id);		
  		
  	}  	
  	
  	// the member_name is for URL seach engine firendly
  	function memberInfo($member_id, $member_name="") {
	  	$this->profile($member_id);
  	}


	// private function with "_XXX"
	function _convertObj($dataObj)
	{
		$cat->first_name = $dataObj['first_name'];
		$cat->last_name = $dataObj['last_name'];		
		$cat->email = $dataObj['email'];
		$cat->member_name = $dataObj['screen_name'];
		if ($dataObj['password'] <> "") {
			$cat->password = ($dataObj['password']);
		} else {
			$cat->password = "";
		}
		$cat->address = $dataObj['address'];
		$cat->phone = $dataObj['phone'];
		$cat->fax = $dataObj['fax'];
		$cat->website = $dataObj['website'];		
		$cat->company_name_chi = $dataObj['company_name_chi'];
		$cat->company_name_eng = $dataObj['company_name_eng']; 
		
		if ($dataObj['memberType'] == 'company') {
			$cat->memberType = "C";
		} else {
			$cat->memberType = "U";
		}
		// default no profile photo for first create member.
		if ($dataObj['photo'] == "default") {
			$cat->photo = "no-profile-photo.jpg";
		}
		
		return $cat;
	}

	  	
	function email_check($email)
	{
		
	      $this->load->model('Memberdb');
	      $data = $this->Memberdb->hasMemberEmail($email);
	      if ($data) {
	        $this->form_validation->set_message('email_check', 'Email already exist');
			return FALSE;
	      }
	    return TRUE;
	   
	}

	function screen_name_check($name)
	{
		
	      $this->load->model('Memberdb');
	      $data = $this->Memberdb->hasMemberName($name);
	      if ($data) {
 	      	$this->form_validation->set_message('screen_name_check', 'Screen Name already exist');	      
	      	return FALSE;
	      }
	    
	      return TRUE;
	}

}
?>
