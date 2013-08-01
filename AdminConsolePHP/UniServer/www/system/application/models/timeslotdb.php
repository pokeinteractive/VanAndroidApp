<?

class Timeslotdb extends Model {

    function Timeslotdb()
    {
        parent::Model();
    }
    
    
    /* load model in model class
    $CI =& get_instance();
  $CI->load->model('model_b');
  $CI->model_b->do_something(); 
    */
    

   function getTimeslotList()
   {
       $this->load->database();
        $query = $this->db->query("select * from timeslot where status = 'Y' order by seq");
        return $query->result();
   }
   
   function getLocationList()
   {
       $this->load->database();
        $query = $this->db->query("select * from location where status = 'Y' order by location_id");
        return $query->result();
   }

   function getDriverRegularTimeSlot($driverId)
   {
       $this->load->database();
        $query = $this->db->query("select * from driver_timeslot where status = 'Y' and driver_id=" . $driverId);
        return $query->result();
   }

   function getDriverAdhocTimeSlot($driverId)
   {
       $this->load->database();
        $query = $this->db->query("select * from free_timeslot where status = 'Y' and driver_id=" . $driverId);
        return $query->result();
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
   
}

?>