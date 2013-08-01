<?

class Driverdb extends Model {

    function Driverdb()
    {
        parent::Model();
    }
    
    
    /* load model in model class
    $CI =& get_instance();
  $CI->load->model('model_b');
  $CI->model_b->do_something(); 
    */
   
  function getFreeTimeslot($numOfDate) {
    
        $this->load->database();
        $query = $this->db->query("select * from driver_timeslot t, driver d where d.driver_id=t.driver_id and t.status = 'Y' order by weekday, timeslot_id, location_id");
        $data->regularTimeslot = $query->result();
    
        $query = $this->db->query("select * from free_timeslot t, driver d where d.driver_id=t.driver_id and t.status = 'Y' and timeslot_date >= CURRENT_DATE order by timeslot_date, timeslot_id, location_id");
        $data->adhocTimeslot = $query->result();
    
        return $data;
  }

   function getDriverList()
   {
       $this->load->database();
        $query = $this->db->query("select * from driver where status = 'Y' order by phone");
        return $query->result();
   }

   function getDriver($id)
   {
       $this->load->database();
        $query = $this->db->query("select * from driver where status = 'Y' and driver_id=" . $id);
        $data = $query->result();
        if ($data)
          return $data[0];
        else
          return NULL;
   }
  
   function getDriverIdByPhone($phone)
   {
       $this->load->database();
        $query = $this->db->query("select driver_id from driver where status = 'Y' and phone='" . $phone . "'");
        $data = $query->result();
        if ($data)
          return $data[0]->driver_id;
        else
          return NULL;
   }


   function addDriver($obj)
   {
      $this->load->database();
      // member_name, rating, url, member_id, photo, description, l.location, l.location_id, business_name, phone, email
       $this->db->set('name', $obj->name);
    $this->db->set('phone', $obj->phone);
    $this->db->set('line', $obj->line);
    $this->db->set('car_plate', $obj->car_plate);
    $this->db->set('remark', $obj->remark);
    $this->db->set('wechat', $obj->wechat);

      $result = $this->db->insert('driver');
      
      
    return $result;
      
   }

    function updateDriver($obj)
    {
    $this->load->database();
    
       $this->db->set('name', $obj->name);
    $this->db->set('phone', $obj->phone);
    $this->db->set('line', $obj->line);
    $this->db->set('car_plate', $obj->car_plate);
    $this->db->set('remark', $obj->remark);
    $this->db->set('wechat', $obj->wechat);
    
    $this->db->where('driver_id', $obj->driver_id);
    
    $result = $this->db->update('driver'); 

    return $result;
    }
   
  
   function updateTimeslot($obj)
   {
      $this->load->database();
      
      // delete all timeslot of driver
     //$query = $this->db->query("update driver_timeslot set status='N' where driver_id=" . $obj->driver_id);
     $query = $this->db->query("delete from driver_timeslot where driver_id=" . $obj->driver_id); 
      
      foreach ($obj as $key => $row) {
        // echo $key;
        if (0 === strpos($key, 'time_')) {
          $data = explode("_", $key);
          // echo "1=".$data[1];  echo "2=". $data[2];  echo "3=". $data[3]; // weekday
           $this->db->set('timeslot_id', $data[1]);
        $this->db->set('location_id', $data[2]);
        $this->db->set('weekday', $data[3]);          
        $this->db->set('driver_id', $obj->driver_id);

          $result = $this->db->insert('driver_timeslot');
        }
      
      }
      
    return $result;
      
   }
  
   function updateAdhocTimeslot($obj)
   {
      $this->load->database();
      
      // delete all timeslot of driver
     //$query = $this->db->query("update driver_timeslot set status='N' where driver_id=" . $obj->driver_id);
     $query = $this->db->query("delete from free_timeslot where driver_id=" . $obj->driver_id); 
      
      foreach ($obj as $key => $row) {
        // echo $key;
        if (0 === strpos($key, 'time_')) {
          $data = explode("_", $key);
          // echo "1=".$data[1];  echo "2=". $data[2];  echo "3=". $data[3]; // weekday
           $this->db->set('timeslot_id', $data[1]);
        $this->db->set('location_id', $data[2]);
        $this->db->set('timeslot_date', $data[3]);          
        $this->db->set('driver_id', $obj->driver_id);

          $result = $this->db->insert('free_timeslot');
        }
      
      }
      
    return $result;
      
   } 
  
}

?>