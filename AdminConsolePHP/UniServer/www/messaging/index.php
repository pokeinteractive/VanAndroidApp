<?php

//TODO:  show error off

require_once("mysql.class.php");

$dbHost = "localhost";
$dbUsername = "vanapp";
$dbPassword = "passwd01";
$dbName = "poke_van";


$db = new MySQL($dbHost,$dbUsername,$dbPassword,$dbName);

// if operation is failed by unknown reason
define("FAILED", 0);

define("SUCCESSFUL", 1);
// when  signing up, if username is already taken, return this error
define("SIGN_UP_USERNAME_CRASHED", 2);  
// when add new friend request, if friend is not found, return this error 
define("ADD_NEW_USERNAME_NOT_FOUND", 2);

// TIME_INTERVAL_FOR_USER_STATUS: if last authentication time of user is older 
// than NOW - TIME_INTERVAL_FOR_USER_STATUS, then user is considered offline
define("TIME_INTERVAL_FOR_USER_STATUS", 60);

define("USER_APPROVED", 1);
define("USER_UNAPPROVED", 0);


$username = (isset($_REQUEST['username']) && count($_REQUEST['username']) > 0) 
              ? $_REQUEST['username'] 
              : NULL;
$password = isset($_REQUEST['password']) ? ($_REQUEST['password']) : NULL;
$port = isset($_REQUEST['port']) ? $_REQUEST['port'] : NULL;

$action = isset($_REQUEST['action']) ? $_REQUEST['action'] : NULL;
if ($action == "testWebAPI")
{
  if ($db->testconnection()){
  echo SUCCESSFUL;
  exit;
  }else{
  echo FAILED;
  exit;
  }
}

$out = NULL;

error_log($action."\r\n", 3, "error.log");
  
  // echo $action;
switch($action) 
{
  
  case "phonecheck":
    
    $driver_id  = checkphone($db, $_REQUEST['phone']);
    
    if ($driver_id > 0) {
      $out = $driver_id;
    } else {
     $out = ""; 
    }
    
    break;
    
    
  case "authenticateUser":
    
    
    if ($port != NULL  
        && ($userId = authenticateUser($db, $username, $password, $port)) != NULL) 
    {          
      
      $out = $userId;
      
    }
    else
    {
        // exit application if not authenticated user
        $out = FAILED;
    }
    
  
  
  break;
    
    
  case "loc":
    
     
       $sql = "update driver_gps set gps_lat= ".$_REQUEST['lat']." , gps_long= ".$_REQUEST['long']. " where driver_id = (select driver_id from driver where phone = '".$_REQUEST['id'] ."' )" ;
    // echo $sql;
       $db->query($sql); 
    
    $out = "updated:" . $sql;
  
  break;
  
  default:
    $out = FAILED;    
    break;  
}

echo $out;



///////////////////////////////////////////////////////////////
function authenticateUser($db, $username, $password, $port)
{
  
  $sql = "select driver_id from driver where username = '".$username."' and password = '".$password."' limit 1";
  // echo $sql;
  $out = NULL;
  if ($result = $db->query($sql))
  {
    if ($row = $db->fetchObject($result))
    {
        $out = $row->driver_id ;
        
        $sql = "update users set authenticationTime = NOW(), 
                                 IP = '".$_SERVER["REMOTE_ADDR"]."' ,
                                 port = ".$port." 
                where driver_id = ".$row->driver_id ."
                limit 1";
        
        $db->query($sql);        
      //$out = $sql;        
                
    }    
  }
  //echo "auth:" . $out;
  return $out;
}

function checkphone($db, $phone)
{
  
  $sql = "select driver_id from driver where phone= '".$username."' limit 1";
  // echo $sql;
  $out = NULL;
  if ($result = $db->query($sql))
  {
    if ($row = $db->fetchObject($result))
    {
        $out = $row->driver_id ;
        
                
    }    
  }
  //echo "auth:" . $out;
  return $out;
}  
?>