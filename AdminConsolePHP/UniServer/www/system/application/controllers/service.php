<?php

class Service extends Base_Controller {
	
  	function serviceCatList() {
	  	$this->load->model('Subject');
	  	$this->load->model('Memberdb');	  	
	  	$this->load->helper('Security');
	  	// get the catalog list
	  	$subjectList = $this->Subject->getCatalogList();
//	  	for ($i=0; $i < sizeof($catList); $i++) {
//		  	// get the subject list
//	  		$catList[$i]->subjectList = $this->Subject->getSubjectByCatalog($catList[$i]->cat_id);
//	  	}
	  	//echo "result=" . isDesktop();
	  	$right['featuredMemberList'] = $this->Memberdb->getFeaturedMemberListBySubject(0,8);
		  	  		
	  	$main['subjectList'] = $subjectList;
	  	
		if ( (!isDesktop()) || MOBILE_TEST) {
			//echo "start to out";
   			$this->load->view("mobile/header", $this->headerTitle);
   			$this->load->view('mobile/service/serviceCatList', $main);
			$this->load->view("mobile/footer");
			
   		} else {
	  	
	  		// pageTitle
			$this->load->view("header", $this->headerTitle);
   		
			$this->load->view('service/serviceCatList', $main);
			$this->load->view("rightSection", $right);  			
			$this->load->view("footer"); 
		} 	
  	}
  
  	function serviceAdd($subject_id = 0) {
  	
  		// check login
  		checkLoginWithPageForward("service/serviceAdd/" . $subject_id );
  	
  		// get catalog and subject list
  		$this->load->model('Subject');	
  		$this->load->model('Servicedb');
  		$this->load->model('Memberdb');		

	  	$predefineItemList = $this->Servicedb->getPredefineItemList($subject_id);
	  	
	  	$data = $this->_createEmptyServiceResult($predefineItemList);
	  	
	  	$edit_service_id = "";
	  	$data['subject'] = $this->Subject->getSubject($subject_id);
		$data['predefineItemList'] = $predefineItemList;
	  	$data['edit_service_id'] = $edit_service_id;
	  	
	  	
  		$member_id = $this->session->userdata("member_id");
		$member = $this->Memberdb->getMember($member_id);
		
		$data['service']->address = $member->address;
		$data['service']->phone = $member->phone;
		$data['service']->website = $member->website;
		
		$data['locationList'] = $this->Servicedb->getLocationList();
  	
	  	// pageTitle
		$header['pageTitle'] = $data['subject']->subject;   		
   		
   		$this->load->view("header", $header);
		$this->load->view('service/serviceAdd', $data);
		$this->load->view("footer");  	
  	}
  	
  	function _prepareServiceEdit($service_id) {
  		// get catalog and subject list
  		$this->load->model('Subject');	
  		$this->load->model('Servicedb');
	  	$this->load->model('Memberdb');
	  	
		$data['service'] = $this->Servicedb->getService($service_id);
		$data['planList'] = $this->Servicedb->getPlanList($service_id);
		$data['planItemList'] = $this->Servicedb->getPlanItemList($service_id);
	  	$data['subject'] = $this->Subject->getSubject($data['service']->subject_id);
	  	$data['locationList'] = $this->Servicedb->getLocationList();
	  	$predefineItemList = $this->Servicedb->getPredefineItemList($data['service']->subject_id);
	  	$data['predefineItemList'] = $predefineItemList;
		
		$data['planItemPrice'] = $planItemPrice;
		

		
  		$member_id = $data['service']->member_id;
		$data['member'] = $this->Memberdb->getMember($member_id);
		
		
		$data['oldPlanList'] = $this->Servicedb->getExistingPlanList($member_id);
				
				
		return $data;
  	}
  	
  	function serviceCopy($service_id) {

  		$edit_service_id = $service_id;
  		
  		$data = $this->_prepareServiceEdit($service_id);
  		
	  	// pageTitle
		$header['pageTitle'] = $data['subject']->subject;   		
   		
   		$this->load->view("header", $header);
		$this->load->view('service/serviceAdd', $data);
		$this->load->view("footer");	
  	
  	}
  	
  	function serviceEdit($service_id) {
  	
  		$edit_service_id = $service_id;
  		
  		$data = $this->_prepareServiceEdit($service_id);
  		
	  	$data['edit_service_id'] = $edit_service_id;
			  	
	  	// pageTitle
		$header['pageTitle'] = $data['service']->service_name;
		
   		$this->load->view("header", $header);
		$this->load->view('service/serviceAdd', $data);
		$this->load->view("footer");   		
  	}
  	
  	function serviceToAdd() {
  	
		$this->load->helper(array('form', 'url'));
		$this->load->library('form_validation');

		$this->form_validation->set_error_delimiters('<div class="error"><font color="red">', '</font></div>');
  	
  		// convert the POST parameter to DB
	  	$this->load->model('Servicedb');
	  	
	  	// check the input data validation
	  	$isValid = $this->form_validation->run('serviceToAdd');

		$this->load->library('validation');

	  	if ($isValid) {
	  	
		  	$this->load->library('session');  	

		  	// $service_id generate

		  	$obj = $this->_convertToAddService($_POST);
		  	$obj->member_id = $this->session->userdata("member_id"); // change to get usrID
		  	$insertResult = false;
			if ($obj->member_id) {
			
				// check if it is create service or edit service				
				if ($_POST['service_id'] == "") {
				
			     	$this->load->library('sqlclass');
				  	$service_id = $this->sqlclass->getServiceIdSeq($obj->member_id);
				  	$obj->service_id = $service_id;
			  		$insertResult = $this->Servicedb->insertService($obj);
		  		} else {
					$service_id = $obj->service_id;
			  		$insertResult = $this->Servicedb->updateService($obj);		  		
		  		}
	  		}
	  		
	  		if ($insertResult) {
	  			$this->serviceInfo($service_id);
	  		} else {
	  			$this->serviceAdd($_POST['subject_id']);
	  		}
		} else {
			$this->serviceAdd($_POST['subject_id']);
		}
  	}

//  	function mobileServiceInfo($service_id, $plan_id=0, $page=1, $questionPage=1) {
//  		$this->serviceInfo($service_id, $plan_id, $page, $questionPage, 1);
//  	}

  	function serviceInfo($service_id, $plan_id=0, $page=1, $questionPage=1, $mobile=0) {
  	
	  	$this->load->model('Servicedb');
	  	$this->load->model('Memberdb');   		
		$this->load->model('Photo');	
		$this->load->model('Commentdb');
		$this->load->model('Questiondb');
		$this->load->model('Leafletdb');
		$this->load->model('Subject');		
		$this->load->helper('Security');
		
		// increase view count of the service.
		$this->Servicedb->serviceStatistic($service_id, "view");
		
		$data['service'] = $this->Servicedb->getService($service_id);
		$data['planList'] = $this->Servicedb->getPlanList($service_id);
		$data['planItemList'] = $this->Servicedb->getPlanItemList($service_id);		
		
		$member_id = $data['service']->member_id;
		$data['member'] = $this->Memberdb->getMember($member_id);
		
		$obj->service_id = $service_id;
		// the contect of comment is get by the PHP view page... So, only total count is include
		$data['commentList'] = $this->Commentdb->getCommentList($obj, "S", $page);
		$data['questionList'] = $this->Questiondb->getQuestionList($obj, "S", $questionPage);		
		
		$data['currentPage'] = $page;
		$data['currentQuestionPage'] = $questionPage;
		
		$data['plan_id_selected'] = $plan_id;
		
	  	// pageTitle
		$this->headerTitle['pageTitle'] = $data['service']->subject . " » ". $data['member']->member_name ." » ". $data['service']->service_name;
		   		
   		if ( (!isDesktop()) || MOBILE_TEST) {
   			$data['photoList'] = $this->Photo->getPhotoListByService($service_id);
   			$this->load->view("mobile/header", $this->headerTitle);
   			$this->load->view('mobile/service/serviceInfo', $data);
			$this->load->view("mobile/footer");
   		} else {
   	
			$this->load->view("header", $this->headerTitle);
			$left['subjectList'] = $this->Subject->getCatalogList();
	   		$this->load->view("leftSection", $left);
			$this->load->view('service/serviceInfo', $data);
			$right['photoList'] = $this->Photo->getPhotoListByService($service_id);
			$right['leafletList'] = $this->Leafletdb->getLeafletPhotoList($service_id);
			$right['leafletPDF'] = $this->Leafletdb->getLeafletPDF($service_id);
			$this->load->view("rightSection", $right);
			$this->load->view("footer");
   		}
  	}
  	
  	function commentList($service_id, $page=1) {
  	
  		$this->load->model('Servicedb');
  		$this->load->model('Commentdb');
		$this->load->model('Memberdb');  

  		$obj->service_id = $service_id;
		$data['service'] = $this->Servicedb->getService($service_id);
		$data['member'] = $this->Memberdb->getMember($data['service']->member_id);
		// the contect of comment is get by the PHP view page... So, only total count is include
		$data['commentList'] = $this->Commentdb->getCommentList($obj, "S", $page);
		$data['currentPage'] = $page;

		if ( (!isDesktop()) || MOBILE_TEST) {
   			$this->load->view("mobile/header", $this->headerTitle);
   			$this->load->view('mobile/service/serviceComment', $data);
			$this->load->view("mobile/footer");
   		} else {		
		  	// pageTitle
			$this->headerTitle['pageTitle'] = $data['service']->subject ." » ". $data['member']->member_name ." » ". $data['service']->service_name;   	

			$this->load->view("header", $this->headerTitle);
			$this->load->view('service/serviceComment', $data);
			$this->load->view("footer");
		}
  	}
  	
  	function questionList($service_id, $page=1) {
  	
  		$this->load->model('Servicedb');
		$this->load->model('Questiondb');
		$this->load->model('Memberdb');  
				
  		$obj->service_id = $service_id;
		$data['service'] = $this->Servicedb->getService($service_id);  		
		$data['member'] = $this->Memberdb->getMember($data['service']->member_id);		
		// the contect of comment is get by the PHP view page... So, only total count is include
		$data['questionList'] = $this->Questiondb->getQuestionList($obj, "S", $page);		
		
		$data['currentPage'] = $page;
		
		
	  	// pageTitle
		$this->headerTitle['pageTitle'] = $data['service']->subject ." » ". $data['member']->member_name ." » ". $data['service']->service_name;   	

		$this->load->view("header", $this->headerTitle);
		$this->load->view('service/serviceQuestion', $data);
		$this->load->view("footer");
  	}
  	
  	function submitQuestion() {
  		
  		// convert the POST parameter to DB
	  	$this->load->model('Questiondb');
		$this->load->library('session');

	  	
		$type = $_POST['type'];
	

  		$obj->question_content = $_POST['question_content']; 
	  	$obj->question_member_id = $this->session->userdata("member_id");  // change to get usrID
		
		$insertResult = false;
		
		if ($obj->question_member_id) {
			
		  	if ($type == 'service') {
	  			$obj->service_id = $_POST['key'];
				$insertResult = $this->Questiondb->insertQuestion($obj);  			
	  		}	
  		}  	
  		
		$this->questionList($obj->service_id, 1);
  		
  	}
  	
  	function submitAnswer() {
  		
  		// convert the POST parameter to DB
	  	$this->load->model('Questiondb');
		$this->load->library('session');

	  	
		$type = $_POST['type'];
		$page = $_POST['page'];

  		$obj->ans_content = $_POST['ans_content']; 
	  	$obj->ans_member_id = $this->session->userdata("member_id");  // change to get usrID
		
		$insertResult = false;
		
		if ($obj->ans_member_id) {
			
		  	if ($type == 'service') {
	  			$obj->service_id = $_POST['key'];
	  			$obj->question_id = $_POST['question_id'];
				$insertResult = $this->Questiondb->updateQuestionAnswer($obj);  			
	  		}	
  		}  	
  		
		$this->questionList($obj->service_id, $page);
  		
  	}
  	
//  	function mobileServiceList($subject_id, $page=1) {
//  		$this->serviceList($subject_id, $page, 1);
//  	}
  	
  	function serviceList($subject_id=0 , $page = 1, $mobile=0) {
   		$this->load->model('Subject');
   		$this->load->model('Servicedb');
   		$this->load->model('Linkage');
   		$this->load->model('Memberdb');
   		$this->load->helper('Security');
   		
   		// parse POST variable
   		$location_id = $_POST['location_id'];
   		$sort_id = $_POST['sort_id'];
   		$param1 = $_POST['param1'];
   		$param2 = $_POST['param2'];
   		$param_range = $_POST['param_range'];
	  	$price_min = $this->input->get_post('price_min', TRUE);
	  	$price_max = $this->input->get_post('price_max', TRUE);
	  	
	  	$objParam->min_price = $price_min;
	  	$objParam->max_price = $price_max;
	  	$objParam->param1 = $param1;
	  	$objParam->param2 = $param2;
	  	$objParam->param_range = $param_range;

   		// show service filter
		$right['showfilter'] = true;   		
   		
   		if ($subject_id == 0) {
   			// not selected, show all the hotest service...
   			$data['subject']->subject = "熱門服務";
   			$data['serviceList']['result'] = $this->Servicedb->getTopRateServiceList();
   		
   		} else {
	   		$data['subject'] = $this->Subject->getSubject($subject_id);
	   		
	   		//$data['interestList'] = $this->Linkage->getInterestList($subject_id);   		
	   		$data['serviceList'] = $this->Servicedb->getServiceListBySubject($subject_id, $page, $location_id, $sort_id, $objParam);
	   		$data['currentPage'] = $page;
	   		$data['location_id'] = $location_id;
	
	   		//$data['featuredMemberList'] = $this->Memberdb->getFeaturedMemberListBySubject($subject_id);
	   		
	   		//$data['otherList'] = $this->Servicedb->getRelatedListBySubject($subject_id);
   		}
   		
   		$right['location_id'] = $location_id;
   		$right['sort_id'] = $sort_id;
   		$right['param1'] = $param1;
   		$right['param2'] = $param2;
   		$right['param_range'] = $param_range;   	
   		$right['price_min'] = $price_min;
   		$right['price_max'] = $price_max;   		
   		
	  	// pageTitle
	  	
		$this->headerTitle['pageTitle'] = $data['subject']->subject;   	
   		   		
   		if ( (!isDesktop()) || MOBILE_TEST) {
   			$data['serviceList'] = $this->Servicedb->getServiceListBySubject($subject_id, $page, $location_id, $sort_id, $objParam, true);
   			$this->load->view("mobile/header", $this->headerTitle);
   			$this->load->view('mobile/service/serviceList', $data);
			$this->load->view("mobile/footer");
   		} else {  
		
			$this->load->view("header", $this->headerTitle);
   			$left['subjectList'] = $this->Subject->getCatalogList();
   		
   			$this->load->view("leftSection", $left);
			$this->load->view('service/serviceList', $data);
			
			// top view and rate service in this subject_id...
		  	$right['topRateServiceList'] = $this->Servicedb->getTopRateServiceList($subject_id);	
		  	$right['topViewServiceList'] = $this->Servicedb->getTopViewServiceList($subject_id);	
			//$this->load->view("rightSearchSection", $right);
			$this->load->view("rightSection", $right);
			$this->load->view("footer");
   		}  	
  	}
  	
  	// plan_id is in "100227,100228,31431303,35529010" format
  	function serviceCompare() {
		$this->load->model('Servicedb');
		$this->load->model('Memberdb');  
		$planIdList = explode(",", $_POST['plan_id']);
		
		$plan_id = implode(",", $planIdList);
		
		$service_id = $_POST['service_id'];
		$data['service'] = $this->Servicedb->getService($service_id);
		$data['member'] = $this->Memberdb->getMember($data['service']->member_id);
		$data['compareDefaultItemList'] = $this->Servicedb->getCompareDefaultItemList($plan_id);
		$data['compareItemList'] = $this->Servicedb->getCompareItemList($plan_id);
		$data['compareList'] = $this->Servicedb->getCompareList($plan_id);		
		$data['planIdList'] = $planIdList;
		$data['service_id'] = $service_id;
		
	  	// pageTitle
		$this->headerTitle['pageTitle'] = $data['service']->subject ." » ". $data['member']->member_name." » ". $data['service']->service_name;
   		$this->load->view("header", $this->headerTitle);
		$this->load->view('service/servicePlanCompare', $data);
		$this->load->view("footer");   		
  	
  	}
  	

  	function _convertToAddService($post) {

  		foreach ($post as $key => $value) {
  		//echo "[key= $key " . $value . "]";
  			$obj->$key = ($value);
  		}

  		return $obj;
  	}
	
	function _createEmptyServiceResult($itemList) {
		$service->service_name = "";
		$service->phone = "";
		$service->website = "";
		$service->description = "";		
		$service->price_desc = "";
		$service->promo_desc = "";
		
		$data["service"] = $service;

		for ($i=0; $i < 5; $i++) {
			$plan->plan_name = "";
			$plan->description = "";
			$plan->price = "";		
			$plan->plan_id = "";	
			$data["planList"][$i] = $plan;
		}
		
		for ($i=0; $i < 5; $i++) {
			$planItem;
			foreach ($itemList as $row) {
				$planItem[$row->item_id]->item_price = "";
				$planItemPrice["p_" . $row->item_id] = "";
			}
			$data["planItemList"][$i] = $planItem;
			
		}
		$data['planItemPrice'] = $planItemPrice;

		return $data;
	}
}
?>
