<?

class Subject extends Model {

    function Subject()
    {
        parent::Model();
    }
    
    
    /* load model in model class
    $CI =& get_instance();
	$CI->load->model('model_b');
	$CI->model_b->do_something(); 
    */
    

   function getCatalogList()
   {
//   		$this->load->database();
//        $query = $this->db->query("select cat_id, catalog_name, catalog_photo from catalog order by seq");
//        return $query->result();

   		$this->load->database();
        $query = $this->db->query("select distinct s.cat_id, s.subject_id, s.subject, s.popular, s.detail, count(ser.service_id) service_count from subject s left outer join service ser on (s.subject_id=ser.subject_id and ser.status='Y') group by s.subject_id order by seq");
        return $query->result();
   }

   function getAllSubject()
   {
   		$this->load->database();
        $query = $this->db->query("select subject_id, subject, popular, cat_id from subject order by cat_id, seq");
        return $query->result();
   }           
   
   function getSubjectByCatalog($cat_id)
   {
   		$this->load->database();
        $query = $this->db->query("select distinct s.cat_id, s.subject_id, s.subject, s.popular, count(ser.service_id) service_count from subject s left outer join service ser on (s.subject_id=ser.subject_id and ser.status='Y') where s.cat_id=" . $cat_id . " group by s.subject_id order by seq");
        return $query->result();
   }
   
   function getSubject($subject_id)
   {
   		$this->load->database();
        $query = $this->db->query("select s.cat_id, c.catalog_name, s.subject_id, s.subject, s.popular from subject s, catalog c where s.cat_id=c.cat_id and s.subject_id=" . $subject_id);

        $data = $query->result();
        if ($data) {
          return $data[0];
        }
        else 
          return NULL;
   }
   
   function searchSubject($subject) {
   
   		$this->load->database();
        $query = $this->db->query("select subject_id from subject where subject = '" . $subject . "'");

        $data = $query->result();
        if ($data) {
          return $data[0];
        }
        else 
          return NULL;
   }
   
   
    function insertSubject($obj) {
   		$this->load->database();
  
	    $this->db->set('subject', $obj->subject);
	    $this->db->insert('subject');
	    
	}  
   
  
}

?>