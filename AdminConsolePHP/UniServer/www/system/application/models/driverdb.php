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
    

   function getDriverList()
   {
   		$this->load->database();
        $query = $this->db->query("select * from driver where status = 'Y'");
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
    	$query = $this->db->query("update driver_timeslot set status='N' where driver_id=" . $obj->driver_id);
        
    	
    	foreach ($obj as $key => $row) {
    		echo $key;
    		if (0 === strpos($key, 'time_')) {
    			$data = explode("_", $key);
    			echo "1=".$data[1];
    			echo "2=". $data[2];
		   		$this->db->set('timeslot_id', $data[1]);
				$this->db->set('location_id', $data[2]);
				$this->db->set('driver_id', $obj->driver_id);

		    	$result = $this->db->insert('driver_timeslot');
    		}
    	
    	}
	    
		return $result;
      
   }
}

?>