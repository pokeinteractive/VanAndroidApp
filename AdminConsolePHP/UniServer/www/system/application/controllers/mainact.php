<?php

class Mainact extends Base_Controller {
  
  function index()
  {
    
    
    $this->main("");
    
  }
  
  function aboutus() {
    echo "under construction"; 
  }
  
  function main($message = "") {
    
      // pageTitle
      $this->load->view("header", $this->headerTitle);
      $this->load->view('main', $result);
      $this->load->view("footer");
    
  }
}
?>