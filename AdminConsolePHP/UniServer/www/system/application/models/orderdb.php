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
   
  function getOrderLocation($from_name, $to_name) {
       $this->load->database();
       $this->db->where('from_name', $from_name); 
       $this->db->where('to_name', $to_name); 
       $query = $this->db->get('order_location');
       $data = $query->result();
       if ($data) {
          return $data[0];
       } else {
          return NULL;
       }
  }
  
  function getDistinctLocation() {
       $this->load->database();
       $query = $this->db->query("select distinct from_id, from_name from order_location where status = 'Y' ");
       return $query->result();
  }  

   function getOrderList()
   {
       $this->load->database();
        $query = $this->db->query("select o.order_id,  lo.price, o.remark, o.cust_phone, o.remark, o.order_date, t.name as timeslot, lo.from_name as from_location, lo.to_name as to_location, d.name, d.phone, o.driver_id from `order` o LEFT JOIN `driver` AS d ON d.driver_id = o.driver_id, timeslot t, location l, location l2, order_location lo where lo.order_location_id=o.order_location_id and o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id and o.status = 'Y' and order_date >= CURRENT_DATE order by order_date, t.seq");
        return $query->result();
   }
   
   function getPendingOrderList()
   {
       $this->load->database();
        $query = $this->db->query("select o.order_id, lo.price, o.remark, o.cust_phone, o.remark, o.order_date, t.name as timeslot, lo.from_name as from_location, lo.to_name as to_location,  o.driver_id from `order` o, timeslot t, location l, location l2, order_location lo where lo.order_location_id=o.order_location_id and o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id and o.status = 'Y' and order_date >= CURRENT_DATE and driver_id is null order by order_date, t.seq");
        return $query->result();
   }
  
    
   function getOrderHistoryList($driverId)
   {
       $this->load->database();
        $query = $this->db->query("select o.order_id, lo.price, o.remark, o.cust_phone, o.remark, o.order_date, t.name as timeslot, lo.from_name as from_location, lo.to_name as to_location from `order` o, timeslot t, location l, location l2, order_location lo where lo.order_location_id=o.order_location_id and o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id and o.status = 'Y' and o.driver_id=$driverId order by order_date, t.seq");
        return $query->result();
   }

   function getOrder($id)
   {
       $this->load->database();
        $query = $this->db->query("select o.order_id,  lo.price, o.remark, o.cust_phone, o.remark, o.order_date, t.name as timeslot, lo.from_name as from_location, lo.to_name as to_location, d.name, d.phone, o.driver_id, o.timeslot_id, o.from_location_id, o.to_location_id from `order` o LEFT JOIN `driver` AS d ON d.driver_id = o.driver_id, timeslot t, location l, location l2, order_location lo where lo.order_location_id=o.order_location_id and o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id and o.status = 'Y' and o.order_id=" . $id);
        $data = $query->result();
        if ($data)
          return $data[0];
        else
          return NULL;
   }


    function updateOrder($obj)
    {
      $this->load->database();
    
           $orderLocation = $this->getOrderLocation($obj->from_location, $obj->to_location);
     
           $this->db->set('from_location_id', $obj->from_area);
           $this->db->set('to_location_id', $obj->to_area);
           $this->db->set('order_location_id', $orderLocation->order_location_id);
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
    
     $orderLocation = $this->getOrderLocation($obj->from_location, $obj->to_location);
     
     $this->db->set('from_location_id', $obj->from_area);
     $this->db->set('to_location_id', $obj->to_area);
     $this->db->set('order_location_id', $orderLocation->order_location_id);
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