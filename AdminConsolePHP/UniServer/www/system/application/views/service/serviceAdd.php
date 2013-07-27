    <script src="/js/jquery.tabs.pack.js" type="text/javascript"></script>
    <script src="/js/jquery.qtip-1.0.0-rc3.min.js" type="text/javascript"></script>
        
<link rel="stylesheet" href="/css/jquery.tabs.css" type="text/css" media="print, projection, screen">
<!-- Additional IE/Win specific style sheet (Conditional Comments) -->
<!--[if lte IE 7]>
<link rel="stylesheet" href="/css/jquery.tabs-ie.css" type="text/css" media="projection, screen">
<![endif]-->



<!-- Middle Side -->		
<div class="main_navigation" style="width:726px;">

<form action="/service/serviceToAdd" method="post">

<div class="input-form">

	<div class="main_content" style="width:704px;">
	
	  <div id="intro-message">
          <h2>如何撰寫給引的服務介紹</h2>
	
	      <ul>
	      <li><strong>- 令人易於找尋.</strong>加入大量的常用的關鍵字, 加入圖片令內容更豐富.</li>
	      <li><strong>- 加入清晰的價格資料.</strong></li>
	      <li><strong>- 鼓勵客戶加入評論.</strong></li>
	      </ul>
		  <br>
	  </div><!-- end intro-message -->
    
	  
<p><?php echo validation_errors(); ?></p>
    


<input type="hidden" name="subject_id" value="<?=$subject->subject_id?>" /> 

<input type="hidden" name="service_id" value="<?=$edit_service_id?>" />
            
      <h2>服務資料 <span class="required">必須填寫</span></h2>

      <div class="input-row">
		<div class="input-label">服務名稱 : </div><div class="input-text-box"><input autocomplete="off" class="text" id="service_name" maxlength="30" name="service_name" size="50" type="text" value="<?=set_value('service_name',$service->service_name)?>"></div>
	  </div>
      <div class="input-row">
		<div class="input-label">網址 : </div><div class="input-text-box"><input autocomplete="off" class="text" id="website" maxlength="50" name="website" size="50" type="text" value="<?=set_value('website',$service->website)?>" ></div>
	  </div>	  
      <div class="input-row">
		<div class="input-label">電話 : </div><div class="input-text-box"><input autocomplete="off" class="text" maxlength="30" id="phone" name="phone" size="50" type="text" value="<?=set_value('phone',$service->phone)?>"></div>
	  </div>
      <div class="input-row">
		<div class="input-label">地址 : </div><div class="input-text-box"><input autocomplete="off" class="text" maxlength="95" id="address" name="address" size="50" type="text" value="<?=set_value('address',$service->address)?>" ></div>
	  </div>	  
      <div class="input-row">
		<div class="input-label">聯絡人 : </div><div class="input-text-box"><input autocomplete="off" class="text" id="contact_person" maxlength="30" name="contact_person" size="50" type="text" value="<?=set_value('website',$service->contact_person)?>" ></div>
	  </div>	
	  <div class="input-row">
		<div class="input-label">地區 : </div><div class="input-text-box">
			<select name="location_id" size="1">
				<option value="">N/A</option>
				<? foreach ($locationList as $row) {  ?>
				<option value="<?=$row->location_id?>" <? if ($service->location_id == $row->location_id) echo "selected" ?> ><?=$row->name?></option>
				<? } ?>
			</select>
		</div>
	  </div>	  
  
  </div> <!-- end main_content-->
 
    <div class="main_content" style="width:704px;">
		<div class="section"> 
           <h2><a name="description"></a>可以形容一下你的服務嗎? <span class="optional">鼓勵填寫</span></h2>
           <p>提及服務的內容, 如以什麼形式提供的, 服務的用途是什麼, 所要用的時間是多少.

				<span id="trigger_Whatexplain" class="explanation-trigger">
				  更多的指引
				</span>
				
				<span id="tip_Whatexplain" style="display: none;" class="explanation">
				              回答以下常見的問題:
				            顧客會在你的服務中得到什麼?
				            為什麼顧客會選舉你的服務而不是其他的供應商?
				            你為什麼類型的客人提供服務?
				</span>
				
				<script type="text/javascript" charset="utf-8">
				  //new Tooltip('trigger_Whatexplain', 'tip_Whatexplain', {appearDuration:0, hideDuration:0, hoverableTip: false, mode: 'explanation'});
				</script>   
				       
		   </p>
           <p>
           		<textarea class="description text" cols="50" id="description" name="description" rows="20"><?=set_value('description',$service->description)?></textarea>
           </p>
	  </div>
      
      <div class="section"> 
               <h2>你所提供的服務計畫的突出之處是什麼? <span class="optional">鼓勵填寫</span>
               </h2>
               <p>簡單介紹不同服務計畫下的分別, 特色, 和比他人特出之處.
               <p><textarea class="description text" cols="50" id="price_desc" name="price_desc" rows="12"><?=set_value('price_desc',$service->price_desc)?></textarea></p>

      </div><!--end section -->	
    
	  <div class="section"> 
               <h2>有沒有任何的推廣及細節? <span class="optional">鼓勵填寫</span>
               </h2>
               <p>介紹現行的服務推廣, 如特價服務, 客人推薦計劃, 以及服務內容的細節等.
               <p><textarea class="description text" cols="50" id="promo_desc" name="promo_desc" rows="12"><?=set_value('promo_desc',$service->promo_desc)?></textarea></p>

      </div><!--end section -->	

    </div>
        
    <script>
    	function showServicePlanDIV(div) {
    		var divObj = document.getElementById('servicePlanDiv' + div);
    		if (divObj) {
    			if (divObj.style.display)
    				divObj.style.display = "";
    			else 
   					divObj.style.display = "none";
    		
    		}
    	}
    </script>  
    
    <script type="text/javascript">
	    $(function() {
	    	$('#servicePlanTab').tabs(1);
	    });
    </script>
    
    <div class="main_content" style="width:704px;">
        <h2>你的服務計劃詳細是怎樣? <span class="required">必須填寫</span></h2>
        
        <div id="servicePlanTab" class="bottom-margin">
		      <ul>
				<li><a href="#servicePlan-0"><span>服務計劃  1</span></a></li>
				<li><a href="#servicePlan-1"><span>服務計劃  2</span></a></li>
				<li><a href="#servicePlan-2"><span>服務計劃  3</span></a></li>
				<li><a href="#servicePlan-3"><span>服務計劃  4</span></a></li>
				<li><a href="#servicePlan-4"><span>服務計劃  5</span></a></li>
				<li><a href="#servicePlan-old"><span>已加入的服務計劃</span></a></li>
		      </ul>
		      
        <? for ($i=0; $i<5; $i++) { ?>
        <div id="servicePlan-<?=$i?>" class="service-plan">
        <p>名稱: &nbsp;&nbsp;&nbsp;
		  <input autocomplete="off" class="text" id="plan_name<?=$i?>" name="plan_name<?=$i?>" size="45" type="text" value="<?=set_value("plan_name$i", $planList[$i]->plan_name)?>" />
	    </p>
        <p>價格: &nbsp;
		  $<input autocomplete="off" class="text" id="price<?=$i?>" name="price<?=$i?>" size="20" type="text" value="<?=set_value("price$i", $planList[$i]->price)?>" onKeyPress="return onlyInputCurrency(event);" />
	    </p>
		<table width="70%"><tr><td width="10%"></td><td width="50%">包括項目</td><td></td><td>&nbsp;&nbsp;&nbsp;數量(如適用)</td></tr>
			   <?	$j=0;
			   		$planItem;
			  		if (isset($predefineItemList))
					foreach ($predefineItemList as $row)
					{ 
						$j++;
						
						// found out corresponding plan_item
						foreach ($planItemList as $plan_item_temp) {
							if ($plan_item_temp->plan_id == $planList[$i]->plan_id && $plan_item_temp->item_id == $row->item_id) {
								$planItem = $plan_item_temp;
								break;
							}
						}
						
						
					?><tr><td><?=$j?></td>
						<td>
							<input type="hidden" name="item<?=$i?>_<?=$j?>" value="<?=$row->item_id?>" />
							<span class="item-desc"><a href="#1" title="<?=$row->description?>"><?=$row->item_name?></a></span>
						</td>
						<td>&nbsp;&nbsp;</td>
						<td>
						<? if ($row->is_qty == 'Y'): ?>
							<input class="text" size="4" maxlength="4" style="width:40px;" type="text" name="qty<?=$i?>_<?=$j?>" value="<?=set_value("qty" .$i."_".$j, $planItem->qty)?>" onKeyPress="return onlyInputNumber(event);" />
						<? else: ?>
							<select name="qty<?=$i?>_<?=$j?>" style="width:100px">
								<option value="0" <? if ($planItem->qty <= 0) echo "selected"; ?>>否</option>
								<option value="1" <? if ($planItem->qty > 0) echo "selected"; ?> >是</option>
							</select>
						<? endif;?>
						</td>
					  </tr>		
					<?
			  	 	}
			  	 		for ($j++ ;$j <= 8; $j++) {
			  	 			echo "<input name='item_price".$i."_$j' type='hidden' /><input name='item".$i."_$j' type='hidden' />";
			  	 		}
			   		?>
			   		
		</table>
     	<p>附加項目及備注: <br><textarea class="description text" cols="50" id="plan_description<?=$i?>" name="plan_description<?=$i?>" rows="17"><?=set_value("plan_description$i", $planList[$i]->description)?></textarea></p>		
        
        </div>
        
        <? } // end of loop in DIV ?>
        
        <div id="servicePlan-old" class="service-plan">
        	<p>
        		<?php 
        		$a=0;
        		foreach ($oldPlanList as $row) {
        			echo "<input name=\"oldplan[]\" type=\"checkbox\" value=\"" . $row->plan_id . "\"/>" . "$row->service_name [$row->plan_name] <br>";
        			$a++;
        		} 
        		?>
        
        	</p>
        </div>
      </div><!-- end id="servicePlanTab" -->
	  
	  <div class="main_content" style="width:704px;">
			<h2>你的位置? </h2>
			<br>
				經度 Longitude : <span id="map_x"></span> &nbsp&nbsp;緯度 Latitude : <span id="map_y"></span>  
				<input id="map_long" type="hidden" name="map_long" value="<?=set_value('map_long',$service->map_long)?>" /> <input id="map_lat" type="hidden" name="map_lat" value="<?=set_value('map_lat',$service->map_lat)?>" />
			<br>
			<div id="map_canvas" style="width: 700px; height: 500px"></div>
	  </div>
        
      <ul class="class-submit submit">
        <li class="btn-input"><span><input name="commit" value="提交" type="submit"></span></li>
      </ul>
	  
	  
        
    </div><!-- end main_content div-->

  </div><!-- end input-form div-->
  
</form>  
	
</div> <!-- article_middle -->

<!-- end Middle Side -->
 
<script type="text/javascript">
$(document).ready(function()
{
   $('span.item-desc a[title]').qtip({
      position: {
         corner: {
            target: 'topLeft',
            tooltip: 'bottomLeft'
         }
      },
      style: {
         name: 'cream',
         padding: '7px 13px',
         width: {
            max: 210,
            min: 0
         },
         tip: true
      }
   });
});


// google map
   
   var service_lat = "<?=$service->map_lat?>";
   var service_long = "<?=$service->map_long?>";
 
   function initialize() {
   
   		var marker = null;
   		
   		var map = null;
	           
    	if (service_lat) {
    
	        var mapOptions = {
	          center: new google.maps.LatLng(service_lat, service_long),
	          zoom: 13,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
	        };
	        
	        map = new google.maps.Map(document.getElementById("map_canvas"),
	            mapOptions);
	        
 			marker = new google.maps.Marker({
			    position: new google.maps.LatLng(service_lat, service_long),
			    map: map,
			    title: '你的位置'
			  });
			  
			  document.getElementById("map_long").value=service_long;
			  document.getElementById("map_lat").value=service_lat;
			  document.getElementById("map_x").innerHTML=service_long;
	          document.getElementById("map_y").innerHTML=service_lat;	       
	        
        } else {
        
	        var mapOptions = {
	          center: new google.maps.LatLng(22.310219899222908, 114.17095184326172),
	          zoom: 13,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
	        };
	        
	        map = new google.maps.Map(document.getElementById("map_canvas"),
	            mapOptions);        
        }
	        

        
        
          
        // add an event listener
        google.maps.event.addListener(map, "click", function(e) {
	          if (marker) {
	            marker.setMap(null);
	          } 
				  marker = new google.maps.Marker({
				    position: new google.maps.LatLng(e.latLng.lat(), e.latLng.lng()),
				    map: map,
				    title: '你的位置'
			  }
		  );
          
		  
		  document.getElementById("map_long").value=e.latLng.lng();
		  document.getElementById("map_lat").value=e.latLng.lat();
		  document.getElementById("map_x").innerHTML=e.latLng.lng();
          document.getElementById("map_y").innerHTML=e.latLng.lat();
        });    
            
   }
   
   
google.maps.event.addDomListener(window, 'load', initialize);

</script>