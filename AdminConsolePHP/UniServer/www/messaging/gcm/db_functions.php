<?php

class DB_Functions {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        include_once './db_connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $phone, $gcm_regid) {
        // insert user into database
        $result = mysql_query("INSERT INTO driver(name, phone, gcm_regid, created_at) VALUES('$name', '$phone', '$gcm_regid', NOW())");
        // check for successful store
        if ($result) {
            // get user details
            $id = mysql_insert_id(); // last inserted id
          
            mysql_query("INSERT INTO driver_gps(driver_id) VALUES('$id')");
          
            $result = mysql_query("SELECT * FROM driver WHERE driver_id = $id") or die(mysql_error());
            
            // return user details
            if (mysql_num_rows($result) > 0) {
                return mysql_fetch_array($result);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Get user by phone and password
     */
    public function getUserByPhoneUUID($phone) {
        $result = mysql_query("SELECT * FROM driver WHERE phoneUUID = '$phone' LIMIT 1");
        return $result;
    }

    /**
     * Getting all users
     */
    public function getAllUsers() {
        $result = mysql_query("select * FROM driver ");
        return $result;
    }

    /**
     * Check user is existed or not
     */
    public function isUserExisted($phone) {
        $result = mysql_query("SELECT phone from driver WHERE phone = '$phone'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
            return false;
        }
    }

}

?>