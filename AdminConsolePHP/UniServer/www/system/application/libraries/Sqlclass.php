<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Sqlclass {


	var $itemPerPage = 10;


  function getServiceIdSeq($member_id) {
    	$CI =& get_instance();
   		$CI->load->database();
   	
   	  $CI->db->set('member_id', $member_id);
		  $CI->db->insert('service_id_seq');
		  
		  $query = $CI->db->query("select max(service_id) id from service_id_seq where member_id=". $member_id);
		  $data = $query->result();
      if ($data) {
          return $data[0]->id;
      }
   	  return NULL;
  }
  
  function getPlanIdSeq($service_id) {
    	$CI =& get_instance();
   		$CI->load->database();
   	
   	  $CI->db->set('service_id', $service_id);
		  $CI->db->insert('plan_id_seq');
		  
		  $query = $CI->db->query("select max(plan_id) id from plan_id_seq where service_id=". $service_id);
		  $data = $query->result();
      if ($data) {
          return $data[0]->id;
      }
   	  return NULL;
  }  

	function queryWhole($sqlSelect, $sqlWhere, $sqlOrder, $page=0, $shortFieldName="", $length=150, $returnNum=0) {
		$CI =& get_instance();
   		$CI->load->database();
   		$query = $CI->db->query( $sqlSelect . $sqlWhere . $sqlOrder );
   		$data = $query->result();
   		if ($data) {
		   	$returnList['result'] = $data;
		   	if ($shortFieldName <> "") {
		   		$this->shortField($returnList['result'] , $shortFieldName, $length);
		   	}
		   	
         	return $returnList;
        }
        else {
        
	      $returnList['result'] = null;
		  $returnList['count'] = 0;
		  
          return $returnList;
        }
	}
	

 	function queryPaging($sqlSelect, $sqlWhere, $sqlOrder, $page=1, $shortFieldName="", $length=150, $returnNum=0) {
   		$CI =& get_instance();
   		$CI->load->database();
   		
   		
   		
   		if ($returnNum == 0) 
	   		$perPage = $this->itemPerPage;
   		else 
	   		$perPage = $returnNum;   		
   		
   		$offset = $perPage * ($page - 1);
        $query = $CI->db->query( $sqlSelect . $sqlWhere . $sqlOrder . " limit $offset , " . $perPage);
        							        							        							
        $data = $query->result();
        if ($data) {
		   	$returnList['result'] = $data;
		   	$returnObj = $this->_queryPagingCount($sqlWhere);
		   	$returnList['count'] = $returnObj['count'];
		   	$returnList['total'] = $returnObj['total'];
		   	
		   	if ($shortFieldName <> "") {
		   		$this->shortField($returnList['result'] , $shortFieldName, $length);
		   	}
		   	
         	return $returnList;
        }
        else {
        
	      $returnList['result'] = null;
		  $returnList['count'] = 0;
		  
          return $returnList;
        }
  	
    }    


    function _queryPagingCount($sqlWhere) {
   		$CI =& get_instance();
   		$CI->load->database();
		
		$result['count'] = 0;
		$result['total'] = 0;
		
        $query = $CI->db->query("select count(*) total " . $sqlWhere);
        							        							        							
		$data = $query->result();
        if ($data) {
        	$output = $data[0]->total / $this->itemPerPage;
        	$result['count'] = ceil($output);
        	$result['total'] = $data[0]->total; 
        }
        
        return $result;
  	
    }  
    
    function shortField($queryList, $shortFieldName, $length=150) {
    	if ($queryList) {
			// covert to short description
			$shortName = $shortFieldName . "_short";
			foreach ($queryList as $row) {
				$tempstr = strip_tags($row->$shortFieldName, "<br><p>");
				if (mb_strlen($tempstr) > $length) {
			   		$row->$shortName = mb_substr($tempstr, 0, $length) . " ...";
		   		} else {
		          	$row->$shortName = $row->$shortFieldName;
		   		}                  
		   		
		   	}
        }
        
        return $queryList;
    	
    }
    
}

?>