<?php

class Order extends Base_Controller {
  
  function addOrder() {
    $this->load->model('Timeslotdb');  
    
    $data['timeslotList'] = $this->Timeslotdb->getTimeslotList();
    $data['locationList'] = $this->Timeslotdb->getLocationList();
    
    $this->load->view("header", $this->headerTitle);
    $this->load->view('order/addOrder', $data);
    $this->load->view("footer");
  }
  
  function editOrder($order_id) {
    $this->load->model('Orderdb');
    $this->load->model('Timeslotdb');    
    $data['order'] = $this->Orderdb->getOrder($order_id);
    $data['timeslotList'] = $this->Timeslotdb->getTimeslotList();
    $data['locationList'] = $this->Timeslotdb->getLocationList();
    $this->load->view("header", $this->headerTitle);
    $this->load->view('order/addOrder', $data);
    $this->load->view("footer");
  }
  
    function toAddOrder() {

      
      // get catalog and subject list
      $this->load->model('Orderdb');    

      $obj = $this->_convertToAdd($_POST);
    if ($obj->order_id) {
      $this->Orderdb->updateOrder($obj);
    } else {
      $this->Orderdb->addOrder($obj);
    }
    
       $this->listOrder();
    }

   function matchDriver($orderId) {

      
      // get catalog and subject list
      $this->load->model('Orderdb'); 
      $this->load->model('Driverdb');      
      $data['order'] = $this->Orderdb->getOrder($orderId);
      $data['driverList'] = $this->Driverdb->getDriverList();     
     
      
      $this->load->view("header", $this->headerTitle);
      $this->load->view('order/matchDriver', $data);
      $this->load->view("footer");
      
    }    

  
   function toMatchDriver() {

 
      $this->load->model('Orderdb');    

      $obj = $this->_convertToAdd($_POST);

      if ($obj->driver_id) {
        $this->Orderdb->updateDriverInOrder($obj);
      } 
    
       $this->listOrder();
    }    
    
    function listOrder() {
      $this->load->model('Orderdb');
    
      $data['orderList'] = $this->Orderdb->getOrderList();

      $this->load->view("header", $this->headerTitle);
      $this->load->view('order/listOrder', $data);
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
    