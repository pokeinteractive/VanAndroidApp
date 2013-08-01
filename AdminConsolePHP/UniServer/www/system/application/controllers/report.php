<?php

class Report extends Base_Controller {
  
  function listReport() {

    if ($_POST['startdate'])
      $data['startdate']=$_POST['startdate'];
    if ($_POST['enddate'])
      $data['enddate']=$_POST['enddate'];


if ($data['startdate'] == '') {
  $data['startdate']= date("Ymd", strtotime("-1 months"));
}

if ($data['enddate']  == '') {
  $data['enddate']= Date("Ymd");
}
    
    $this->load->view("header", $this->headerTitle);
    $this->load->view('report/reportMain', $data);
    $this->load->view("footer"); 
  }
 
}
?>
    