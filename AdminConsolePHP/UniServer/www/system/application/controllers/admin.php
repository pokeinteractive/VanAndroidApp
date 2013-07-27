<?php

class Admin extends Base_Controller {

	function getOrder() {
		$this->load->model('Shopdb');
		$data['orderList'] = $this->Shopdb->getOrderList();
		
		$this->load->view('admin/adminheader');
		$this->load->view('admin/orderlist', $data);
		$this->load->view("footer");
	}

	// for root login only	
	function rootadmin() {
		
		$this->load->model('Memberdb');
		$data['memberList'] = $this->Memberdb->getMemberListForAdmin("C");
		
		$this->load->view('admin/adminheader');
		$this->load->view('admin/adminlist', $data);
		$this->load->view("footer");
	
	}
	
	function rootadminuser() {
		
		$this->load->model('Memberdb');
		$data['memberList'] = $this->Memberdb->getMemberListForAdmin("U");
		
		$this->load->view('admin/adminheader');
		$this->load->view('admin/adminlist', $data);
		$this->load->view("footer");
	
	}
	
	
	function articleList() {
		$this->load->model('Articledb');
	  	$data['articleList'] = $this->Articledb->getArticleListByDate(6);
		$this->load->view('admin/adminheader');
		$this->load->view('admin/articlelist', $data);
		$this->load->view("footer");
	}
	
	function editServiceDetail($service_id) {
	
		$this->load->model('Servicedb');
	  	
	  	$result['detail'] = $this->Servicedb->getServiceDetail($service_id);
	  	$result['service_id'] = $service_id;		

		// pageTitle
		$header['pageTitle'] = "Weddingla.com";
		$header['menu'] = "article";	
   		$this->load->view("header", $this->headerTitle);
		$this->load->view('admin/editServiceDetail', $result);
		$this->load->view("footer");
	}
	
	function saveServiceDetail() {
	
		if ($_POST['passkey'] == 'alexwilliam') {
		
			$this->load->model('Servicedb');
			
			$detail = $_POST['detail'];
		  	
		  	$obj->main_point = $detail['main_point'];
		  	$obj->service_id = $detail['service_id'];	  		  	
		  	
		  	$testExist = $this->Servicedb->getServiceDetail($obj->service_id);
		  	
		  	if ($testExist) {
		  		$this->Servicedb->updateServiceDetail($obj);
				echo "Success, update service detail!";
		  	} else {
				$this->Servicedb->insertServiceDetail($obj);
				echo "Success, add service detail!";
			}
			
		} else {
		
			echo "Fail, key error!";
		
		}
	}

	
	// ==================
}