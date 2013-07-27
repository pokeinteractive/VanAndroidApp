<?php

class Xmlapi extends Base_Controller {

	function getServiceList($subject_id, $page=1) {

		$this->load->model('Servicedb');
		$this->load->library('xmlclass');
		
		$serviceList = $this->Servicedb->getServiceListBySubject($subject_id, $page, $location_id, $sort_id, $objParam);
		
		//$serviceFinal->services11[0] = "334#$";
		
		$serviceFinal->serviceList->service = $serviceList['result'];
		
		//print_r($serviceFinal);
		
		$result->data = $this->xmlclass->array_to_xml($serviceFinal, "serviceRoot");
		
		echo $result->data ;
		
		$this->load->view('xml/xmlout', $result);
	}

	
}