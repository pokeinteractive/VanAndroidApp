<?php

class Jsonapi extends Base_Controller {

  // new API
  
  function register() {
    $uuid = $_POST['uuid'];

      $this->load->model('Memberdb');
      $this->load->model('Earnpointdb');
    if ($uuid <> '') {
    
      $member = $this->Memberdb->getMemberByUUID($uuid);
      if (!$member) {

        $obj->uuid = $uuid;
        $obj->memberType = 'U';
        $this->Memberdb->insertMember($obj);
        $member = $this->Memberdb->getMemberByUUID($uuid);
        $member->param1 = "0";
        $member->error_code = 0;
        $member->error_message = "";
         $member->return_code = "OK";
      } else {
        // already a member, find member total point
        $point= $this->Earnpointdb->getMemberPoint($member);

        $member->param1 = $point->point;
        $member->error_code = 0;
        $member->error_message = "";
        $member->return_code = "OK";
      }
    }
    

    $memberResult->result[0] = $member;
    $result->data = json_encode($memberResult);
    
    //$result->data = "<matchpoint><result>$member->member_id</result></matchpoint>";
    
    $this->load->view('xml/json', $result);
  }
  
  function rewardapply() {
    $uuid = $_POST['uuid'];
    $email= $_POST['email'];
    $reward_id = $_POST['reward_id'];    
    
    //    $uuid='b03eeb81-3904-3f07-b116-1f4632c32d41';
    //    $reward_id = 1;
    //$email= 'ee';

      $this->load->model('Memberdb');
      $this->load->model('Earnpointdb');
    
    $returnData= "";
    if ($uuid <> '') {
    
      $member = $this->Memberdb->getMemberByUUID($uuid);
      if ($member) {

        $obj->reward_id = $reward_id;
        $obj->member_id = $member->member_id;
        $obj->point = 100;        
        $obj->email = $email;
        // save the reward_txn
        $resultOfInsert = $this->Earnpointdb->addReward($obj);
        if ($resultOfInsert ) { 

        $returnData->param2 = $email;        
        $returnData->param1 = $reward_id ;
        $returnData->error_code = 0;
        $returnData->error_message = "";
        $returnData->return_code = "OK";
        } else {

        $returnData->error_code = 0;
        $returnData->error_message = "";
        $returnData->return_code = "FAIL";          
        }
      } else {

        $returnData->error_code = 301;
        $returnData->error_message = "NO MATCH MEMBER";
        $returnData->return_code = "FAIL";  
      }
    } else {

        $returnData->error_code = 302;
        $returnData->error_message = "NO UUID INPUT";
        $returnData->return_code = "FAIL";   
    }
   
    $memberResult->result[0] = $returnData;
    $result->data = json_encode($memberResult);

    $this->load->view('xml/json', $result);
  }
  
  function audioCheck() {
  
    $uuid = $_POST['uuid'];
    $audiokey= $_POST['audiokey'];
    $log = $_POST['log'];
    $lat = $_POST['lat'];
    
    //$uuid='b03eeb81-3904-3f07-b116-1f4632c32d41';
    //$audiokey='5142';
    //$log = 12.55161;
    //$lat = 21.23022;
      
//    $uuid = "232323";   $service_id = "1";    $point = "1";

      $this->load->model('Memberdb');
      $this->load->model('Earnpointdb');
      
    //$this->Earnpointdb->addlog($uuid ."-".$audiokey ."-".$log . "-".$lat);   
    
    if ($uuid <> '') {
      $member = $this->Memberdb->getMemberByUUID($uuid);
      if ($member) {
        
        // resolve the audio to service and point
        $audioObj = $this->Earnpointdb->matchAudioKey($audiokey);
        //        $this->Earnpointdb->addlog("audioObj-service_id:".$audioObj->service_id );   
        if ($audioObj) {
          $obj->service_id = $audioObj->service_id;
          $obj->member_id = $member->member_id;
          $obj->log = $log;
          $obj->lat = $lat;        
          $memberPoint = $this->Earnpointdb->getMemberPoint($obj);
          //$this->Earnpointdb->addlog("memberPoint -point:".$memberPoint->point );         
          $memberServicePoint = $this->Earnpointdb->getMemberPointByService($obj);          
      
          // $this->Earnpointdb->addlog("memberServicePoint -point:".$memberServicePoint->point );           
          //echo "memberPoint=".$memberPoint->point;
          $obj->point = $audioObj->point;
          if ($memberServicePoint <> NULL) {
            // $this->Earnpointdb->addlog("step 6:".$obj->service_id );           
            // $this->Earnpointdb->addlog("step 6.1:".$obj->point);
            // $this->Earnpointdb->addlog("step 6.2:".$obj->member_id);
            //$this->Earnpointdb->addlog("step 8.3".$obj->log . "l:".$obj->lat);            
            $this->Earnpointdb->addEarnpoint($obj);
          } else {
            //       $this->Earnpointdb->addlog("step 7".$obj->service_id );           
            $this->Earnpointdb->insertEarnpoint($obj);
          }
      
          $returnData->error_code = 0;
          $returnData->error_message = "";
          $returnData->return_code = "OK"; 
          $returnData->param1 = $audioObj->point; 
          $returnData->param2 = $audioObj->service_name;
          $returnData->param3 = "0". $memberPoint->point + $audioObj->point; 
		  $returnData->param4 = $audioObj->service_id; 
          
          // $this->Earnpointdb->addlog("Earnpointdb-point:".$returnData->param1 );     
        }
      }
    }
    
    if ($returnData->return_code <> "OK") {
          $returnData->error_code = 0;
          $returnData->error_message = "";
          $returnData->return_code = "FAIL"; 
      //$returnData->param1 = $memberPoint->point + $audioObj->point;
    }
    
   
    $memberResult->result[0] = $returnData;
    $result->data = json_encode($memberResult);

    //$result->data = "<matchpoint><result>$member->member_id</result></matchpoint>";
    
    $result->data = json_encode($memberResult);
    
    
    $this->load->view('xml/json', $result);
  }



  function matchPointCodeCheck() {
  
    $uuid = $_POST['uuid'];
    $codeKey= $_POST['matchpointcode'];
    $log = $_POST['log'];
    $lat = $_POST['lat'];
    
    //$uuid='b03eeb81-3904-3f07-b116-1f4632c32d41';
    //$audiokey='5142';
    //$log = 12.55161;
    //$lat = 21.23022;
      
//    $uuid = "232323";   $service_id = "1";    $point = "1";

      $this->load->model('Memberdb');
      $this->load->model('Earnpointdb');
      
    //$this->Earnpointdb->addlog($uuid ."-".$audiokey ."-".$log . "-".$lat);   
    
    if ($uuid <> '') {
      $member = $this->Memberdb->getMemberByUUID($uuid);
      if ($member) {
        
        // resolve the audio to service and point
        $audioObj = $this->Earnpointdb->matchMatchPointCode($codeKey);
        //        $this->Earnpointdb->addlog("audioObj-service_id:".$audioObj->service_id );   
        if ($audioObj) {
          $obj->service_id = $audioObj->service_id;
          $obj->member_id = $member->member_id;
          $obj->log = $log;
          $obj->lat = $lat;        
          $memberPoint = $this->Earnpointdb->getMemberPoint($obj);
          //$this->Earnpointdb->addlog("memberPoint -point:".$memberPoint->point );         
          $memberServicePoint = $this->Earnpointdb->getMemberPointByService($obj);          
      
          // $this->Earnpointdb->addlog("memberServicePoint -point:".$memberServicePoint->point );           
          //echo "memberPoint=".$memberPoint->point;
          $obj->point = $audioObj->point;
          if ($memberServicePoint <> NULL) {
            // $this->Earnpointdb->addlog("step 6:".$obj->service_id );           
            // $this->Earnpointdb->addlog("step 6.1:".$obj->point);
            // $this->Earnpointdb->addlog("step 6.2:".$obj->member_id);
            //$this->Earnpointdb->addlog("step 8.3".$obj->log . "l:".$obj->lat);            
            $this->Earnpointdb->addEarnpoint($obj);
          } else {
            //       $this->Earnpointdb->addlog("step 7".$obj->service_id );           
            $this->Earnpointdb->insertEarnpoint($obj);
          }
      
          $returnData->error_code = 0;
          $returnData->error_message = "";
          $returnData->return_code = "OK"; 
          $returnData->param1 = $audioObj->point; 
          $returnData->param2 = $audioObj->service_name;
          $returnData->param3 = $memberPoint->point + $audioObj->point; 
          $returnData->param4 = $audioObj->service_id; 
          
          // $this->Earnpointdb->addlog("Earnpointdb-point:".$returnData->param1 );     
        }
      }
    }
    
    if ($returnData->return_code <> "OK") {
          $returnData->error_code = 0;
          $returnData->error_message = "";
          $returnData->return_code = "FAIL"; 
      //$returnData->param1 = $memberPoint->point + $audioObj->point;
    }
    
   
    $memberResult->result[0] = $returnData;
    $result->data = json_encode($memberResult);

    //$result->data = "<matchpoint><result>$member->member_id</result></matchpoint>";
    
    $result->data = json_encode($memberResult);
    
    
    $this->load->view('xml/json', $result);
  }
  
  
    
  function getRewardList() {
  
      $this->load->model('Earnpointdb');

      $earnpointList->earnpoint = $this->Earnpointdb->getRewardList();
    
  
    $result->data = json_encode($earnpointList);
    
    $this->load->view('xml/json', $result);
  }
  
  function getEarnPointList() {
  
    $uuid = $_POST['uuid'];
  
      $this->load->model('Earnpointdb');
      $this->load->model('Memberdb');
      
    if ($uuid <> '') {
      $member = $this->Memberdb->getMemberByUUID($uuid);
      $earnpointList->earnpoint = $this->Earnpointdb->getMembershipList($member);
    }
  
    $result->data = json_encode($earnpointList);
    
    $this->load->view('xml/json', $result);
  }
  
  //=============================

  function getServiceList($subject_id=0, $page=1) {

    $this->load->model('Servicedb');
    
    $lat = $_POST['lat'];
    $log = $_POST['log'];      
    
    $serviceList = $this->Servicedb->getServiceListBySubject(0, $page, $location_id, $sort_id, $objParam);
    
    //$serviceFinal->services11[0] = "334#$";
    
    $serviceFinal->serviceList->service = $serviceList['result'];
    $serviceFinal->serviceList->total= $serviceList['total'];
    
    //print_r($serviceFinal);
    //$result->data = $this->simplejsonclass->toJSON($serviceFinal);
    //echo "fasdfsdfsdaf =========" . $result->data ; 
    
    $result->data = json_encode($serviceFinal->serviceList);
    
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
  
  function getSubjectList() {

    $this->load->model('Subject');
    
    $data = $this->Subject->getAllSubject();

    $serviceFinal->serviceList->subject = $data;
    
    $result->data = json_encode($serviceFinal->serviceList);
    
    $this->load->view('xml/json', $result);
  }
    
  // if service_id == 0, get all latest comment
  function getCommentList($service_id, $page=1) {

    $this->load->model('Commentdb');
    $this->load->model('Servicedb');
    
    
    if ($service_id > 0) {
      $obj->service_id = $service_id;
      //$data['service'] = $this->Servicedb->getService($service_id);
      $data = $this->Commentdb->getCommentList($obj, "S", $page);
    }
    else {
      $data = $this->Commentdb->getLatestCommentList("S", $page);
    }
    
    //print_r($serviceFinal);
    //$result->data = $this->simplejsonclass->toJSON($serviceFinal);
    //echo "fasdfsdfsdaf =========" . $result->data ; 

    //$serviceFinal->serviceList->service = $temp;
    $serviceFinal->serviceList->comment = $data['result'];
    
    $result->data = json_encode($serviceFinal->serviceList);
    
    $this->load->view('xml/json', $result);
  }
  
  
  // rankType = 1 : broswer, 2 = good comment
  function getRankList($rankType) {

    $this->load->model('Servicedb');
    $this->load->model('Subject');    
    
    $subjectList = $this->Subject->getCatalogList();
    $temp = "";
    $resultList = "";
    $count=0;
    $i=0;
      foreach ($subjectList as $row) {
        if ($rankType == 1)
          $temp[$count] = $this->Servicedb->getTopViewServiceList($row->subject_id);
        else if ($rankType == 2)
          $temp[$count] = $this->Servicedb->getTopRateServiceList($row->subject_id);
        
        foreach ($temp[$count] as $rowItem) {
          $resultList[$i++] = $rowItem;
        }
          
        $count=$count+1;  
      }
      
      $data['subjectList'] = $subjectList;
    
    //print_r($serviceFinal);
    //$result->data = $this->simplejsonclass->toJSON($serviceFinal);
    //echo "fasdfsdfsdaf =========" . $result->data ; 
    
    $serviceFinal->serviceList->service = $resultList;
    
    $result->data = json_encode($serviceFinal->serviceList);
    
    $this->load->view('xml/json', $result);
  }
  
  function getPhotoList($service_id) {

    $this->load->model('Gallerydb');
        
    $photoList = $this->Gallerydb->getAllPhotoListByService($service_id);

    $serviceFinal->serviceList->photo = $photoList;
    
    $result->data = json_encode($serviceFinal->serviceList);
    
    $this->load->view('xml/json', $result);
  }  
    
    
  function getLocationList($subject_id) {

    $this->load->model('Servicedb');
        
    $sList = $this->Servicedb->getServiceLocationList($subject_id);

    $serviceFinal->serviceList->service = $sList;
    
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

  // login to the website
  function login() {

    // check login
    $this->load->library('session');
    
    $this->load->model('Memberdb');
    
    $member = $this->Memberdb->getMemberLogin($_POST['email'], $_POST['password']);
    
    if ($member != NULL) {
      $this->load->library('securityclass');
      $this->securityclass->loginToSession($member);
      $respone->result = "OK";
    } else {
      $respone->result = "FAIL";
    }    
    
    $result->data = json_encode($respone);
    $this->load->view('xml/json', $result);  
  }

  function postComment() {
    //   check login
      checkLoginWithPageForward("mainact");
       
      // convert the POST parameter to DB
      $this->load->model('Commentdb');
    $this->load->library('session');

      
    $type = $_POST['type'];

      $obj->comment_content = trim($_POST['comment_content']); 
      $obj->rating = $_POST['rating'];      
      $obj->writer_member_id = $this->session->userdata("member_id");  // change to get usrID
    
    $insertResult = false;
    
    if ($obj->writer_member_id) {
      
        if ($type == 'service') {
          $obj->service_id = $_POST['key'];
        $insertResult = $this->Commentdb->insertServiceComment($obj);        
        }  
      }    
      
      if ($insertResult) {
      $respone->result = "OK";
    } else {
      $respone->result = "FAIL";
    }    
    
    $result->data = json_encode($respone);
    $this->load->view('xml/json', $result);  
  }
  
}