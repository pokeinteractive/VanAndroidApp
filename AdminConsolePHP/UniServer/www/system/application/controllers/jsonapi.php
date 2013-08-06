<?php

class Jsonapi extends Base_Controller {

     function matchOrder($orderId=0, $driverId=0) {

      $this->load->model('Orderdb');    

       $obj->driver_id=$driverId;
       $obj->order_id=$orderId;
      if ($obj->driver_id) {
        if ($this->Orderdb->updateDriverInOrder($obj)) 
           echo "ok";
        else
            echo "fail";   
        
       
        return;
           
      } 
      
        echo "fail";
       return;
     
       
    }    
  
  function getDriverIdByPhone($driverPhone) {
    $this->load->model('Driverdb');
    $result->data = $this->Driverdb->getDriverIdByPhone($driverPhone);
    
    
      
    //  $result->data = "1";
    $this->load->view('xml/json', $result);
  }
  
  //=============================

  function getOrderList($driverId, $page=1) {

    $this->load->model('Orderdb');
    
    $order->order = $this->Orderdb->getPendingOrderList($driverId);
    
    //print_r($serviceFinal);
    //$result->data = $this->simplejsonclass->toJSON($serviceFinal);
    //echo "fasdfsdfsdaf =========" . $result->data ; 
    
    $result->data = json_encode($order);
    
    $this->load->view('xml/json', $result);
  }
  
  function getOrder($orderId) {

    $this->load->model('Orderdb');
    
    $order->order = $this->Orderdb->getOrder($orderId);
    
    //print_r($serviceFinal);
    //$result->data = $this->simplejsonclass->toJSON($serviceFinal);
    //echo "fasdfsdfsdaf =========" . $result->data ; 
    
    $result->data = json_encode($order);
    
    $this->load->view('xml/json', $result);
  }
  
  function getOrderHistory($driverId, $page=1) {

    $this->load->model('Orderdb');
    
    $order->order = $this->Orderdb->getOrderHistoryList($driverId);
    
    //print_r($serviceFinal);
    //$result->data = $this->simplejsonclass->toJSON($serviceFinal);
    //echo "fasdfsdfsdaf =========" . $result->data ; 
    
    $result->data = json_encode($order);
    
    $this->load->view('xml/json', $result);
  }


  function getService($service_id) {

    $this->load->model('Servicedb');
    $this->load->model('Memberdb');
    
    $data['service'] = $this->Servicedb->getService($service_id);
    
    $member_id = $data['service']->member_id;
    $data['member'] = $this->Memberdb->getMember($member_id);
    
    //print_r($serviceFinal);
    //$result->data = $this->simplejsonclass->toJSON($serviceFinal);
    //echo "fasdfsdfsdaf =========" . $result->data ; 
    $temp[0] = $data['service'];
    $temp[1] = $data['member'];
    
    $serviceFinal->serviceList->service = $temp;
    
    $result->data = json_encode($serviceFinal->serviceList);
    
    $this->load->view('xml/json', $result);
  }
  
  
  function sendEmail($emailType) {

    $subject = $_POST['subject'];
    $content = $_POST['content'];
    $email = $_POST['email'];
    
    $this->load->library('email');
    $this->email->from('enquiry@weddingido.com', 'Wedding, I do!');
    $this->email->to("weddingido@gmail.com");
    if ($subject == "") {
      $subject = "Enquiry";
    }
    
    if ($content == "") {
      $content = "Test";
    }
    
    $this->email->subject($subject);
    $this->email->message($content);
    
    $this->email->send();
    
    $respone->result = "OK";
    $result->data = json_encode($respone);
    
    $this->load->view('xml/json', $result);  
  
  }
  
  
  // get notication client UUID from iphone....
  function joinNoti($uuid) {
    $this->load->model('Notidb');
    
    $result = $this->Notidb->getNoti($uuid);
    
    if ($result) {
      // no need to add it, already exist
    } else {
      $obj->uuid = $uuid;
      $this->Notidb->insertNoti($obj);
    }
  }  
  
  function sendNoti($message) {
    $this->load->library('noticlass');
    
    $this->load->model('Notidb');
    
    $result = $this->Notidb->getAllNoti();
    
    foreach ($result as $row) {
      $this->noticlass->sendNoti($row->uuid, $message);  
    }
     
    
    
  }

  
  
}