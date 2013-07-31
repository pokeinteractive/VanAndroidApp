<?php

class Driver extends Base_Controller {
	
	
	function addDriver() {
		$this->load->model('Driverdb');	
		
		$this->load->view("header", $this->headerTitle);
		$this->load->view('driver/addDriver', $data);
		$this->load->view("footer");
	}
	
	function editDriver($driver_id) {
		$this->load->model('Driverdb');	
		$data['driver'] = $this->Driverdb->getDriver($driver_id);
		$this->load->view("header", $this->headerTitle);
		$this->load->view('driver/addDriver', $data);
		$this->load->view("footer");
	}
	
	function editTimeslot($driver_id) {
		$this->load->model('Driverdb');
		$this->load->model('Timeslotdb');		
		$data['driver'] = $this->Driverdb->getDriver($driver_id);
		$data['timeslotList'] = $this->Timeslotdb->getTimeslotList();
		$data['locationList'] = $this->Timeslotdb->getLocationList();
		$this->load->view("header", $this->headerTitle);
		$this->load->view('driver/editTimeslot', $data);
		$this->load->view("footer");
	}
	
  	function toAddDriver() {

  		
  		// get catalog and subject list
  		$this->load->model('Driverdb');		

	  	$obj = $this->_convertToAdd($_POST);
		if ($obj->driver_id) {
			$this->Driverdb->updateDriver($obj);
		} else {
			$this->Driverdb->addDriver($obj);
		}
  	
   		$this->listDriver();
  	}
  	
 	function toEditTimeslot() {

  		
  		// get catalog and subject list
  		$this->load->model('Driverdb');		

	  	$obj = $this->_convertToAdd($_POST);
	  	
		if ($obj->driver_id) {
			$this->Driverdb->updateTimeslot($obj);
		} 
  	
   		$this->listDriver();
  	}  	
  	
  	function listDriver() {
  		$this->load->model('Driverdb');
		
  		$data['driverList'] = $this->Driverdb->getDriverList();

		$this->load->view("header", $this->headerTitle);
		$this->load->view('driver/listDriver', $data);
		$this->load->view("footer");
  	}

  	function _convertToAdd($post) {

  		foreach ($post as $key => $value) {
  		//echo "[key= $key " . $value . "]";
  			$obj->$key = ($value);
  		}

  		return $obj;
  	}
  	
  		
}
?>
  	