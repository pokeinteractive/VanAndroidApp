<?

class Orderdb extends Model {

    function Orderdb()
    {
        parent::Model();
    }
    
    
    /* load model in model class
    $CI =& get_instance();
  $CI->load->model('model_b');
  $CI->model_b->do_something(); 
    */
    

   function getOrderList()
   {
       $this->load->database();
        $query = $this->db->query("select o.order_id, '120' as price, o.remark, o.cust_phone, o.remark, o.order_date, t.name as timeslot, l.name as from_location, l2.name as to_locaiton, d.name, d.phone, o.driver_id from `order` o LEFT JOIN `driver` AS d ON d.driver_id = o.driver_id, timeslot t, location l, location l2 where o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id and o.status = 'Y' and order_date >= CURRENT_DATE order by order_date, t.seq");
        return $query->result();
   }
   
   function getPendingOrderList()
   {
       $this->load->database();
        $query = $this->db->query("select o.order_id, '120' as price, o.remark, o.cust_phone, o.remark, o.order_date, t.name as timeslot, l.name as from_location, l2.name as to_locaiton,  o.driver_id from `order` o, timeslot t, location l, location l2 where o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id and o.status = 'Y' and order_date >= CURRENT_DATE and driver_id is null order by order_date, t.seq");
        return $query->result();
   }
  
    
   function getOrderHistoryList($driverId)
   {
       $this->load->database();
        $query = $this->db->query("select o.order_id, '80' as price, o.remark, o.cust_phone, o.remark, o.order_date, t.name as timeslot, l.name as from_location, l2.name as to_locaiton from `order` o, timeslot t, location l, location l2 where o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id and o.status = 'Y' and o.driver_id=$driverId order by order_date, t.seq");
        return $query->result();
   }

   function getOrder($id)
   {
       $this->load->database();
        $query = $this->db->query("select o.order_id,  o.remark, o.cust_phone, o.remark, o.order_date, o.from_location_id, o.to_location_id, o.timeslot_id, t.name as timeslot, l.name as from_location, l2.name as to_locaiton, d.name, d.phone, o.driver_id from `order` o LEFT JOIN `driver` AS d ON d.driver_id = o.driver_id, timeslot t, location l, location l2 where o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id and o.status = 'Y' and o.order_id=" . $id);
        $data = $query->result();
        if ($data)
          return $data[0];
        else
          return NULL;
   }


    function updateOrder($obj)
    {
      $this->load->database();
    
          $data = explode("_", $obj->location_map);
          ///  echo "1=".$data[1];
          // echo "2=". $data[2];
           $this->db->set('from_location_id', $data[1]);
           $this->db->set('to_location_id', $data[2]);

           $this->db->set('cust_phone', $obj->cust_phone);
           $this->db->set('order_date', $obj->order_date);
           $this->db->set('timeslot_id', $obj->timeslot_id);
           $this->db->set('remark', $obj->remark);          
           $this->db->where('order_id', $obj->order_id);
          $result = $this->db->update('order');
       

    return $result;
    }
   
  
   function addOrder($obj)
   {
      $this->load->database();
      
 
       $data = explode("_", $obj->location_map);
          // echo "1=".$data[1];
          //  echo "2=". $data[2];
           $this->db->set('from_location_id', $data[1]);
           $this->db->set('to_location_id', $data[2]);

           $this->db->set('cust_phone', $obj->cust_phone);
           $this->db->set('order_date', $obj->order_date);
           $this->db->set('timeslot_id', $obj->timeslot_id);
           $this->db->set('remark', $obj->remark);          

          $result = $this->db->insert('order');
    
      
    return $result;
      
   }
  
  function updateDriverInOrder($obj) {
      $this->load->database();
    
      $query = $this->db->query("select o.driver_id from `order` o where o.driver_id IS NULL and o.order_id=" .  $obj->order_id);
      $data = $query->result();
      if ($data) {
        if ($data[0]->driver_id > 0) {
          return false;
        } else {
           $this->db->set('driver_id', $obj->driver_id);
           $this->db->where('order_id', $obj->order_id);
           $result = $this->db->update('order');
        }
      }
 
      return $result;
   }
  
}

?>