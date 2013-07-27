<?

class Servicedb extends Model {

  var $itemPerPage = 20;

    function Servicedb()
    {
        parent::Model();
    }
    
    
    /* load model in model class
    $CI =& get_instance();
  $CI->load->model('model_b');
  $CI->model_b->do_something(); 
    */
    
   function getService($service_id)
   {
       $this->load->database();
        $query = $this->db->query("select s.service_id, s.service_name, s.phone, s.website, s.member_id, s.subject_id, c.cat_id, s.description, s.price_desc, s.promo_desc, s.created, s.status, c.catalog_name, su.subject, r.good, r.fair, r.bad, s.address, s.contact_person, m.member_name, d.main_point, s.location_id, s.map_long, s.map_lat " . 
                      " from subject su, catalog c, rate_summary r, member m, service s left outer join service_detail d on (d.service_id=s.service_id and d.status='Y') " .
                      " where m.member_id=s.member_id and su.subject_id=s.subject_id and c.cat_id=su.cat_id and r.service_id=s.service_id and s.service_id=" . $service_id);

        $data = $query->result();
        if ($data) {
            // covert to short description
      if (mb_strlen($data[0]->description) > 150) {
           $data[0]->desc_short = mb_substr($data[0]->description, 0, 150);
         } else {
              $data[0]->desc_short = $data[0]->description;
         } 
      if (mb_strlen($data[0]->price_desc) > 150) {
           $data[0]->price_desc_short = mb_substr($data[0]->price_desc, 0, 150);
         } else {
              $data[0]->price_desc_short = $data[0]->price_desc;
         }  
      if (mb_strlen($data[0]->promo_desc) > 150) {
           $data[0]->promo_desc_short = mb_substr($data[0]->promo_desc, 0, 150);
         } else {
              $data[0]->promo_desc_short = $data[0]->promo_desc;
         }                             
           return $data[0];
        }
        else {
          return NULL;
      }
   }
   
   function getLocationList() {
       $this->load->database();
        $query = $this->db->query("select location_id, name from location order by location_id");
       
        return $query->result();
   }
   
   function getServiceDetail($service_id) {
       $this->load->database();
        $query = $this->db->query("select service_id, main_point from service_detail " .
                      " where service_id=" . $service_id);
       $data = $query->result();
        if ($data) {
           return $data[0];
        }
        else {
          return NULL;
      }
   }
   
   function insertServiceDetail($obj)
   {
      $this->load->database();
      $this->db->set('main_point', $obj->main_point);    
      $this->db->set('service_id', $obj->service_id);   
      $this->db->insert('service_detail');
      
   }
  
   function updateServiceDetail($obj)
   {
    $this->load->database();
      $this->db->set('main_point', $obj->main_point);    
    $this->db->where('service_id', $obj->service_id);
    $this->db->update('service_detail'); 
   }
      
   function getPlanList($service_id) {
       $this->load->database();
        $query = $this->db->query("select p.plan_id, p.plan_name, p.price, p.description, p.promo_desc from plan p, service_plan_map sp " .
                      " where sp.status='Y' and sp.plan_id=p.plan_id and p.status='Y' and sp.service_id=" . $service_id . " order by p.seq ");
        return $query->result();
   }   

   function getPlanItemList($service_id) {
       $this->load->database();
        $query = $this->db->query("select map.plan_id , i.item_id, i.item_name, i.description, map.item_price, i.is_qty, map.qty from service_plan_map sp, item_map map, item i " .
                      " where sp.status='Y' and sp.plan_id=map.plan_id and i.item_id=map.item_id and map.status='Y' and sp.service_id=" . $service_id . " order by i.item_id ");
        return $query->result();
   }      
   
   function getExistingPlanList($member_id) {
       $this->load->database();
        $query = $this->db->query("select distinct p.plan_id , s.service_name, p.plan_name from plan p, service_plan_map sp, service s " .
                      " where sp.status='Y' and p.status='Y' and sp.plan_id=p.plan_id and sp.service_id=s.service_id and member_id=" . $member_id . " order by s.service_id, p.plan_id ");
        return $query->result();   
   
   }
   
   function getRelatedListBySubject($subject_id) {
       $this->load->database();
       
        $query = $this->db->query("select s.service_id, s.service_name, su.subject, su.subject_id, m.member_name, m.company_name_eng, m.member_id, m.photo from service s, subject su, member m " .
                      " where s.status = 'Y' and s.subject_id=su.subject_id and m.member_id=s.member_id and s.subject_id <> " . $subject_id .
                      " limit 0,10 ");
        return $query->result();
   }      
   
   function getPredefineItemList($subject_id) {
       $this->load->database();
        $query = $this->db->query("select i.item_id, i.item_name, i.description, i.is_qty from item i " .
                      " where i.predefine = 'Y' and i.subject_id=" . $subject_id . " order by i.item_id ");
        return $query->result();
   }      
   
   function getPriceRangeList($subject_id) {
       $this->load->database();
        $query = $this->db->query("select code price_id, value price_range from sys_code " .
                      " where status = 'Y' and grp='PRICE' and type='" . $subject_id . "' order by seq ");
        return $query->result();   
   }
   
   function getSortSelectList() {
    $this->load->database();
        $query = $this->db->query("select code sort_id, value sort_value from sys_code " .
                      " where status = 'Y' and grp='SORT' order by seq ");
        return $query->result();   
   }
   
   function getSysCode($obj) {
       $this->load->database();
        $query = $this->db->query("select grp, code, value, min_value, max_value from sys_code " .
                      " where status = 'Y' and code='".$obj->code."'");
        return $query->result();   
   }   
   
   function getServiceListByMember($member_id)
   {
       $this->load->database();

    $query = $this->db->query("select s.service_id, s.service_name, s.phone, s.website, s.member_id, s.subject_id, s.description, s.address, s.price_desc, s.created, su.subject, r.good, r.fair, r.bad, r.view_count, s.low_price " . 
                      " from service s, subject su, rate_summary r " .
                      " where s.status='Y' and su.subject_id=s.subject_id and r.service_id=s.service_id and s.member_id=" . $member_id .
                      " order by s.created desc" );


       $data = $query->result();
        if ($data) {
      // covert to short description
      foreach ($data as $row) {
        if (mb_strlen($row->description) > 150) {
             $row->desc_short = mb_substr($row->description, 0, 150);
           } else {
                $row->desc_short = $row->description;
           }    
           
        if (mb_strlen($row->price_desc) > 150) {
             $row->price_desc_short = mb_substr($row->price_desc, 0, 150);
           } else {
                $row->price_desc_short = $row->price_desc;
           }  
                                  
         }
           return $data;
        }
        else 
          return NULL;
   }
   
   function getServiceListBySubject($subject_id, $page=1, $locaion_id="", $sort_id=1, $objParam, $isAll = false)
   {
   
       // parse parameter
       $param1=$objParam->param1;
       $param2=$objParam->param2;
       $param_range=$objParam->param_range;
       $min_price=$objParam->min_price;
       $max_price=$objParam->max_price;
          
     // find nearest in SQL
     //SELECT id, ( 3959 * acos( cos( radians(37) ) * cos( radians( lat ) ) * cos(radians( lng ) - radians(-122) ) + sin( radians(37) ) * sin( radians( lat ) ) ) ) AS distance FROM markers HAVING distance < 25 ORDER BY distance LIMIT 0 , 20;
          
       $sqlSelect = "select s.point, s.service_id, s.service_name, s.description, s.price_desc, su.subject, su.subject_id, m.member_name, m.company_name_chi, m.company_name_eng, m.member_id, m.photo, s.created, s.low_price, r.good, r.fair, r.bad, r.good + r.fair + r.bad comment ";
    $sqlWhere =  " from service s, subject su, member m, rate_summary r where r.service_id=s.service_id and s.status = 'Y' and s.subject_id=su.subject_id and m.member_id=s.member_id "; 
    
	if ($subject_id <> 0) { // 0 is all
	  $sqlWhere = $sqlWhere . " and s.subject_id=" . $subject_id;
	}
    
    if ($locaion_id) {
      $sqlWhere = $sqlWhere . " and s.location_id like '$locaion_id%' ";
    }
    
    if ($param1) {
      $sqlWhere = $sqlWhere . " and s.param1 =" . $param1;
    }
    if ($param2) {
      $sqlWhere = $sqlWhere . " and s.param2 =" . $param2;
    }
    if ($param_range) {
      $sqlWhere = $sqlWhere . " and s.param1 <= " . $param_range . " and s.param2 >= " . $param_range ;
    }
    if ($min_price) {
      $sqlWhere = $sqlWhere . " and s.low_price >= " . $min_price ;
    }
    if ($max_price) {
      $sqlWhere = $sqlWhere . " and s.low_price <= " . $max_price ;
    }        
    
    if ($sort_id == 2) {
        $sort_by = "r.good, r.fair";      
      } else if ($sort_id == 3) {
      $sort_by = "s.low_price asc";            
      } else if ($sort_id == 4) {
      $sort_by = "s.low_price desc";                  
      } else {
        $sort_by = "r.good desc, r.fair desc";
      }
    
    // echo  $sqlWhere;
        $sqlOrder =  " order by " . $sort_by;
        
        $this->load->library('sqlclass');
        if ($isAll) {
          $returnList = $this->sqlclass->queryWhole($sqlSelect, $sqlWhere, $sqlOrder);
        } else {
      $returnList = $this->sqlclass->queryPaging($sqlSelect, $sqlWhere, $sqlOrder, $page);
        }
    
    $returnList['result'] = $this->sqlclass->shortField($returnList['result'], "description", 50);
    $returnList['result'] = $this->sqlclass->shortField($returnList['result'], "price_desc", 50);    
    
    return $returnList;
    
   }
      
   function serviceStatistic($service_id, $type) {
       $this->load->database();
       
       if ($type == "view") {
          $this->db->query("update rate_summary set view_count=view_count+1 where service_id= " . $service_id);
        }
   }
   
   function getTopViewServiceList($subject_id=0, $returnNum = 5) {
       $this->load->database();
       $sql = "select r.view_count rating, r.view_count, s.service_id, su.subject, s.email, m.member_id, s.service_name, su.subject_id, m.member_name , m.company_name_eng, m.photo, s.low_price from " .
                      " service s, subject su, member m, rate_summary r where r.service_id=s.service_id and s.status = 'Y' and s.subject_id=su.subject_id and m.member_id=s.member_id ";

    if ($subject_id <> 0) 
      $sql = $sql . " and su.subject_id = $subject_id ";

        $sql = $sql . " order by r.view_count desc limit 0,$returnNum";
      
        $query = $this->db->query($sql);
        return $query->result();   
   }

   function getTopRateServiceList($subject_id=0, $returnNum = 5) {
       $this->load->database();
       $sql = "select r.good rating, r.good, s.service_id, su.subject, m.member_id, s.email, s.service_name, su.subject_id, m.member_name , m.company_name_eng, m.photo, s.low_price from " .
                      " service s, subject su, member m, rate_summary r where r.service_id=s.service_id and s.status = 'Y' and s.subject_id=su.subject_id and m.member_id=s.member_id ";
                      
    if ($subject_id <> 0) 
      $sql = $sql . " and su.subject_id = $subject_id ";
                      
        $sql = $sql . " order by r.good desc limit 0,$returnNum";
        $query = $this->db->query($sql);
      return $query->result();
   }
   
   function getServiceLocationList($subject_id=0) {
       $this->load->database();
       $sql = "select s.service_id, su.subject, m.member_id, s.service_name, su.subject_id, m.member_name , m.company_name_eng, m.photo, s.low_price, s.map_lat, s.map_long from " .
                      " service s, subject su, member m where s.status = 'Y' and s.subject_id=su.subject_id and m.member_id=s.member_id and s.map_lat is not null and s.map_long is not null  ";
                      
    if ($subject_id <> 0) 
      $sql = $sql . " and su.subject_id = $subject_id ";
                      
        //$sql = $sql . " order by r.good desc";
        $query = $this->db->query($sql);
      return $query->result();
   }   
        
   function getNewServiceList()
   {
       $this->load->database();
      
        $query = $this->db->query("select s.service_id, su.subject, m.member_id, s.service_name, su.subject_id, m.member_name , m.company_name_eng, m.photo from " .
                      " service s, subject su, member m where s.status = 'Y' and s.subject_id=su.subject_id and m.member_id=s.member_id " .
                      " order by s.created desc limit 0,10");
        return $query->result();
   }   
   
   // $plan_id in the 1,2,3,43,343,11 format
   function getCompareDefaultItemList($plan_id) {
   
       $sql = "select i.item_id rowLabelId, i.item_name rowLabelDesc, i.description, i.is_qty from item i " .
        " where i.subject_id in (select distinct s.subject_id from plan p, service_plan_map sp, service s where p.status='Y' and sp.status='Y' and p.plan_id=sp.plan_id and sp.service_id=s.service_id and p.plan_id in ($plan_id)) " .
        " order by i.seq ";
    //echo $sql;
    $this->load->database();
        $query = $this->db->query($sql);
        return $query->result();        
   }
   
   // $plan_id in the 1,2,3,43,343,11 format
   function getCompareItemList($plan_id) {
   
       $sql = "select i.plan_id, i.item_id, i.item_price, i.qty from item_map i " .
        " where i.plan_id in ($plan_id) ";

    $this->load->database();
        $query = $this->db->query($sql);
        return $query->result();        
   }   

   function getCompareList($plan_id) {
   
       $sql = "select p.plan_id, m.member_name, m.company_name_eng, p.price, p.description, p.plan_name from service_plan_map sp, plan p, service s, member m " .
             " where m.member_id=s.member_id and s.service_id=sp.service_id and sp.plan_id=p.plan_id and sp.status='Y' " .
        " and p.status='Y' and p.plan_id in ($plan_id) ";
//echo $sql;
    $this->load->database();
        $query = $this->db->query($sql);
        return $query->result();        
   }      
   
   function insertService($obj) {
       $this->load->database();
       
       $this->db->set('service_id', $obj->service_id);
      $this->db->set('service_name', $obj->service_name);      
      $this->db->set('member_id', $obj->member_id);
      $this->db->set('address', $obj->address);      
      $this->db->set('contact_person', $obj->contact_person);           
      $this->db->set('subject_id', $obj->subject_id);
    $this->db->set('price_desc', $obj->price_desc);
    $this->db->set('promo_desc', $obj->promo_desc);
    $this->db->set('description', $obj->description);
    $this->db->set('location_id', $obj->location_id);
    $this->db->set('map_long', $obj->map_long);
    $this->db->set('map_lat', $obj->map_lat);
    
    // find the lowest price plan
    $obj->low_price = 99999999;
    for ($i = 0; $i < 5; $i++) {
      $price_key = "price$i";
      if ($obj->$price_key <> "" && $obj->low_price > $obj->$price_key) {
        $obj->low_price = $obj->$price_key;
      }  
    }
    if ($obj->low_price != 99999999)
      $this->db->set('low_price', $obj->low_price);
      
    if ($obj->low_price == 99999999) {
      $this->db->set('low_price', NULL);
    }  

    $this->db->set('phone', $obj->phone);
    $this->db->set('website', $obj->website);        
    $this->db->set('status', "Y");    
      $this->db->insert('service');
      
      for ($i = 0; $i < 5; $i++) {
        
        // add plan if the plan name is not null
        $plan_name_key = "plan_name$i";
        if ($obj->$plan_name_key <> "") {
        
           $this->load->library('sqlclass');
          $plan_id = $this->sqlclass->getPlanIdSeq($obj->service_id);
        
        $plan_description_key = "plan_description$i";
        $price_key = "price$i";
        
          $this->db->set('service_id', $obj->service_id);
          $this->db->set('plan_id', $plan_id);
          $this->db->insert('service_plan_map');
          
          $this->db->set('plan_id', $plan_id);          
          $this->db->set('plan_name', $obj->$plan_name_key);
        $this->db->set('description', $obj->$plan_description_key);
        $this->db->set('price', $obj->$price_key);    
        $this->db->set('seq', ($i));
        $this->db->set('status', "Y");    
          $this->db->insert('plan');
    
          // add a plan with items ... 
          for ($j=1; $j <= 8; $j++) {
            $item_key = "item".$i."_$j";
            $item_price = "item_price".$i."_$j";
            $item_qty = "qty".$i."_$j";            
            //echo "item: $item_key" . $obj->$item_key . "] ";
            if ($obj->$item_key <> "") {
              $this->db->set('plan_id', $plan_id);
              $this->db->set('item_id', $obj->$item_key);
              $this->db->set('item_price', $obj->$item_price);
              $this->db->set('service_id', $obj->service_id);  
              if ($obj->$item_qty != "")
              $this->db->set('qty', $obj->$item_qty);
            else   
              $this->db->set('qty', NULL);
              
              $this->db->insert('item_map');      
            }
          
          }
          
        }
    }
    
    // add rate_summary record
    
       $this->db->set('service_id', $obj->service_id);
      $this->db->insert('rate_summary');
      
      return true;
  } 
   

    function updateService($obj)
    {
    
    $this->load->database();
       
      $this->db->set('service_name', $obj->service_name);      
    $this->db->set('price_desc', $obj->price_desc);
    $this->db->set('promo_desc', $obj->promo_desc);
      $this->db->set('address', $obj->address);          
      $this->db->set('contact_person', $obj->contact_person);           
    $this->db->set('description', $obj->description);    
    $this->db->set('phone', $obj->phone);
    $this->db->set('website', $obj->website);      
    $this->db->set('location_id', $obj->location_id);
    $this->db->set('map_long', $obj->map_long);
    $this->db->set('map_lat', $obj->map_lat);
    
    // find the lowest price plan
    $obj->low_price = 99999999;
    for ($i = 0; $i < 5; $i++) {
      $price_key = "price$i";
      if ($obj->$price_key <> "" && $obj->low_price > $obj->$price_key) {
        $obj->low_price = $obj->$price_key;
      }  
    }
    if ($obj->low_price != 99999999)
      $this->db->set('low_price', $obj->low_price);
      
    if ($obj->low_price == 99999999) {
      $this->db->set('low_price', NULL);
    }      

    // handle the old plan lowest price 
    if (sizeof($obj->oldplan) > 0) {
        // find lowest price in old plan...
        $oldplanSQLStr = implode(",", $obj->oldplan);
        
        $query = $this->db->query("select min(price) low from plan where plan_id in ($oldplanSQLStr)");
          $data = $query->result();

      if ($obj->low_price > $data[0]->low)
        $obj->low_price = $data[0]->low;
    }

    
    
    $this->db->where('service_id', $obj->service_id);    
      $this->db->update('service');
      
      
      // clear all the plan and item mapping
      $query = $this->db->query("update service_plan_map set status='N' where service_id = " . $obj->service_id);
         $query = $this->db->query("update item_map set status='N' where service_id = " . $obj->service_id);
      
      for ($i = 0; $i < 5; $i++) {
        
        // add plan if the plan name is not null
        $plan_name_key = "plan_name$i";
        if ($obj->$plan_name_key <> "") {
        
        $this->load->library('sqlclass');
          $plan_id = $this->sqlclass->getPlanIdSeq($obj->service_id);
        
        $plan_description_key = "plan_description$i";
        $price_key = "price$i";
        
          $this->db->set('service_id', $obj->service_id);
          $this->db->set('plan_id', $plan_id);
          $this->db->insert('service_plan_map');          

          $this->db->set('plan_id', $plan_id);          
          $this->db->set('plan_name', $obj->$plan_name_key);
        $this->db->set('description', $obj->$plan_description_key);
        $this->db->set('price', $obj->$price_key);
        $this->db->set('seq', ($i));  
        $this->db->set('status', "Y");    
          $this->db->insert('plan');
    
          // add a plan with items ... 
          for ($j=1; $j <= 8; $j++) {
            $item_key = "item".$i."_$j";
            $item_price = "item_price".$i."_$j";
            $item_qty = "qty".$i."_$j";  
            //echo "item: $item_key" . $obj->$item_key . "] ";
            if ($obj->$item_key <> "") {
              $this->db->set('plan_id', $plan_id);
              $this->db->set('item_id', $obj->$item_key);
              $this->db->set('item_price', $obj->$item_price);
              $this->db->set('service_id', $obj->service_id);  
              if ($obj->$item_qty != "")
              $this->db->set('qty', $obj->$item_qty);
            else   
              $this->db->set('qty', NULL);
              $this->db->insert('item_map');      
            }
          
          }
          
        }
    }    
    
    // map the old plan to service
    if (isset($obj->oldplan)) {
      foreach ($obj->oldplan as $oplan_id) {
          $this->load->database();
          $this->db->set('service_id', $obj->service_id);
            $this->db->set('plan_id', $oplan_id);
            $this->db->insert('service_plan_map');  
      }      
    }
    
  
    
    $this->load->database();
      $this->db->set('status', 'Y');
    $this->db->where('service_id', $obj->service_id);
    $this->db->update('service'); 
    
    return true;
    }  
    
}

?>