<?

class Memberdb extends Model {

    function Memberdb()
    {
        parent::Model();
    }
    
    
    /* load model in model class
    $CI =& get_instance();
	$CI->load->model('model_b');
	$CI->model_b->do_something(); 
    */
    

   function getMemberListBySubject($subject_id)
   {
   		$this->load->database();
        $query = $this->db->query("select t.member_name, t.company_name_eng, t.member_id, t.photo from class c, member t where c.member_id=c.member_id and c.subject_id=" . $subject_id);
        return $query->result();
   }


   function getFeaturedMemberListBySubject($subject_id=0, $numReturn=5)
   {
   		$this->load->database();
   		
   		$sql = "select distinct m.member_name, m.company_name_eng, m.member_id, m.photo, m.description, m.address, m.phone, m.address, m.fax, m.website, m.email from service s, member m where s.member_id=m.member_id and m.feature = 'Y'";

   		if ($subject_id > 0)
	   		$sql = $sql . " and s.subject_id=" . $subject_id;
   		
		$sql = $sql . " group by member_id";
   		
        $query = $this->db->query($sql);

        $data = $query->result();
        
   		if (sizeof($data) > 0)
			shuffle($data);
		
		$resultArray;
		$i=0;
		foreach ($data as $row) {
			$resultArray[$i] = $row;
			if ($i < $numReturn)
				break;
		}	
        
        if ($data) {
			// covert to short description
			foreach ($data as $row) {
				if (mb_strlen($row->description) > 50) {
			   		$row->desc_short = mb_substr($row->description, 0, 50);
		   		} else {
		          	$row->desc_short = $row->description;
		   		}                  
		   	}
         	return $data;
        }
        else 
          return NULL;
   } 
   
   function getNewMemberList()
   {
   		$this->load->database();
        $query = $this->db->query("select m.member_id, m.member_name, m.company_name_eng, m.photo from member m" .
        							" order by created desc limit 0, 5");
		return $query->result();
   }
   
   function getMemberListForAdmin($memberType)
   {
   		$this->load->database();
        $query = $this->db->query("select m.member_id, m.member_name, m.company_name_eng, service_id, service_name, m.phone, m.email, m.fax, m.website, m.address from member m left outer join service ser on (m.member_id=ser.member_id) " .
        							" where m.memberType = '". $memberType ."'  order by member_id");
		return $query->result();
   }   
   
   function getMemberSubjectList($member_id)
   {
   		$this->load->database();
        $query = $this->db->query("select s.subject_id, s.subject from class c, member t, subject s where c.member_id=c.member_id and c.subject_id=s.subject_id and t.member_id=" . $member_id);
		return $query->result();
   }
   
   function getMember($member_id) 
   {
   		$this->load->database();
        $query = $this->db->query("select member_name, m.memberType, m.company_name_chi, m.company_name_eng, member_id, photo, description, first_name, last_name, email, phone, address, fax, website from member m where member_id=" . $member_id);

        $data = $query->result();
        if ($data) {
			if (mb_strlen($data[0]->description) > 150) {
		   		$data[0]->desc_short = mb_substr($data[0]->description, 0, 150);
	   		} else {
	          	$data[0]->desc_short = $data[0]->description;
	   		}          
          return $data[0];
        }
        else 
          return NULL;
   }
   
   function getMemberByEmail($email) 
   {
   		$this->load->database();
        $query = $this->db->query("select memberType, member_name, m.company_name_chi, m.company_name_eng, member_id, photo, description, first_name, last_name, email, phone, address, fax, website from member m where email='" . $email . "'");

        $data = $query->result();
        if ($data) {
			if (mb_strlen($data[0]->description) > 150) {
		   		$data[0]->desc_short = mb_substr($data[0]->description, 0, 150);
	   		} else {
	          	$data[0]->desc_short = $data[0]->description;
	   		}          
          return $data[0];
        }
        else 
          return NULL;
   }   
   
   function getMemberByUUID($uuid) 
   {
   		$this->load->database();
        $query = $this->db->query("select memberType, member_name, m.company_name_chi, m.company_name_eng, member_id, photo, description, first_name, last_name, email, phone, address, fax, website from member m where uuid='" . $uuid . "'");

        $data = $query->result();
        if ($data) {
			if (mb_strlen($data[0]->description) > 150) {
		   		$data[0]->desc_short = mb_substr($data[0]->description, 0, 150);
	   		} else {
	          	$data[0]->desc_short = $data[0]->description;
	   		}          
          return $data[0];
        }
        else 
          return NULL;
   }  
   
   function getMemberData($member_id) {
		$this->load->database();
		
        $query = $this->db->query("select count(service_id) service_count from service s where status='Y' and s.member_id=" . $member_id);
        $data = $query->result();
        $result->service_count = $data[0]->service_count;
        
        $query = $this->db->query("select count(comment_id) comment_count from comment c where c.status='Y' and c.writer_member_id=" . $member_id);
        $data = $query->result();        
        $result->comment_count = $data[0]->comment_count;        
        
        return $result;
   }
   
   function getMemberComment($member_id) {
		$this->load->database();
		
        $query = $this->db->query("select comment_id, s.service_id, comment_content, comment_date, s.service_name, m.member_name from comment c, service s, member m where c.status='Y' and s.status='Y' and c.service_id=s.service_id and m.member_id=s.member_id and c.writer_member_id=" . $member_id . " order by comment_date desc");
        return $query->result();
        
   }
   
   
   function hasMemberName($name) 
   {
      	$this->load->database();
        $query = $this->db->query("select member_name, member_id from member where member_name='" . mysql_real_escape_string($name) . "'");

        $data = $query->result();
        if ($data) {
          return true;
        }
        else 
          return false;        
   }
   
   function hasMemberEmail($email) 
   {
      	$this->load->database();
        $query = $this->db->query("select member_name, member_id from member where email='" . $email . "'");

        $data = $query->result();
        if ($data) {
          return true;
        }
        else 
          return false;        
   }   
   
   function getMemberLogin($email, $password) 
   {
		if ($email && $password) {
   		
	      	$this->load->database();
	        $query = $this->db->query("select member_id, member_name, memberType, email, password from member where email='" . $email . "' and password='" . $password . "'" );
	
	        $data = $query->result();
	        if ($data <> NULL && $data[0]->member_id && $data[0]->email == $email && $data[0]->password == $password) {
	          return $data[0];
	        } else {
	          return NULL;        
	        }
	        
       } else {
       		return NULL;
       }		
   }    
   
   
   function insertMember($obj)
   {
    	$this->load->database();
    	// member_name, rating, url, member_id, photo, description, l.location, l.location_id, business_name, phone, email
	    $this->db->set('member_name', $obj->member_name);
	    $this->db->set('company_name_chi', $obj->company_name_chi);
	    $this->db->set('company_name_eng', $obj->company_name_eng);	    	    
	    $this->db->set('first_name', $obj->first_name);
	    $this->db->set('last_name', $obj->last_name);
	    $this->db->set('address', $obj->address);
		$this->db->set('phone', $obj->phone);
		$this->db->set('fax', $obj->fax);	    	    
	    $obj->photo = "no-profile-photo.jpg";
		$this->db->set('photo', $obj->photo);
		$this->db->set('memberType', $obj->memberType);
		$this->db->set('email', $obj->email);
		$this->db->set('password', $obj->password);
		$this->db->set('website', $obj->website);
		$this->db->set('uuid', $obj->uuid);

	    $result = $this->db->insert('member');
	    
	    
		return $result;
      
   }

    function updateMember($obj)
    {
		$this->load->database();
		//$this->db->set('member_name', $obj->member_name);
	    $this->db->set('company_name_chi', $obj->company_name_chi);
	    $this->db->set('company_name_eng', $obj->company_name_eng);	    	    		
	    $this->db->set('first_name', $obj->first_name);
	    $this->db->set('last_name', $obj->last_name);	    	    		
		$this->db->set('address', $obj->address);
		$this->db->set('phone', $obj->phone);
		$this->db->set('fax', $obj->fax);
		if ($obj->password <> "")
			$this->db->set('password', $obj->password);
		$this->db->set('website', $obj->website);		
		$this->db->set('email', $obj->email);
		
		if ($obj->photo <> "")
			$this->db->set('photo', $obj->photo);
			
		$this->db->where('member_id', $obj->member_id);
		
		$result = $this->db->update('member'); 

		return $result;
    }
   
  
    function updateMemberDescription($user)
    {
		$this->load->database();
		$this->db->set('description', $user->description);
		$this->db->where('member_id', $user->member_id);
		$result = $this->db->update('member'); 
		
		return $result;
    }
    
    function updateMemberPhoto($user)
    {
		$this->load->database();
		$this->db->set('photo', $user->photo);
		$this->db->where('member_id', $user->member_id);
		$result = $this->db->update('member'); 
		
		return $result;		
    }    
}

?>