    <script src="/js/jquery.tabs.pack.js" type="text/javascript"></script>
    <script src="/js/jquery.qtip-1.0.0-rc3.min.js" type="text/javascript"></script>    
<link rel="stylesheet" href="/css/jquery.tabs.css" type="text/css" media="print, projection, screen">
<!-- Additional IE/Win specific style sheet (Conditional Comments) -->
<!--[if lte IE 7]>
<link rel="stylesheet" href="/css/jquery.tabs-ie.css" type="text/css" media="projection, screen">
<![endif]-->

<script>

function mark() {
 $.get("/link/mark/<?=$service->service_id?>", 
   function(data){ 
		alert(data);
   });
}

// google map

   var service_lat = "<?=$service->map_lat?>";
   var service_long = "<?=$service->map_long?>";

   function initialize() {
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
			
	        
        } else {
        
    
        }
        
   }
   
   


</script>

<!-- Middle Side -->		
<div class="main_navigation">

	<div class="main_content">

		<h2><a href="/service/serviceList/<?=$service->subject_id?>"><?=$service->subject?></a> »&nbsp;<?=$member->member_name?> - <?=$service->service_name?><span class="btn-sub"><a href="/openform/email/wronginfo/<?=$service->service_id?>">報告錯誤</a></span>
		<? if (getCurrentUserId() == 132): ?><span class="btn-sub"><a href="/member/rootadminlogin/<?=$member->member_id?>">以這個身份登入</a></span><? endif; ?></h2>
		
	    <div class="col1">
		  	<!-- user photo -->
			<img src="/upload/<?=$member->photo?>" title="" alt="" class="thumb-large" width="132" height="99">
		    <? /*
		    <div class="rating-box"><img src="/graphics/heart1.gif" width="32" height="32"><br><?=$service->good?></div>
			<div class="rating-box"><img src="/graphics/heart2.gif" width="32" height="32"><br><?=$service->fair?></div>
			<div class="rating-box"><img src="/graphics/heart3.gif" width="32" height="32"><br><?=$service->bad?></div>
			*/ ?>
	    </div>

      	<div class="col2">

         <dl class="business-info">
	        <h3><?=$service->service_name?></h3>
	    	  <p>提供: <a href="/member/memberInfo/<?=$member->member_id?>/<?=str_replace("'", "", $member->company_name_eng)?>"><?=$member->member_name?></a></p>
	            <? if ($member->description <> $member->desc_short): ?>
		        <div id="long_member_desc" style="display: none;">
		          <?=nl2br($member->description)?>&nbsp;<span class="close-img">&nbsp;</span>
		        </div>
		        <? endif; ?>
		        <div id="short_member_desc" style="">
		          	<?=nl2br($member->desc_short)?>&nbsp;
			        <? if ($member->description <> $member->desc_short): ?>
						&nbsp;<span class="open-img">&nbsp;</span>
		            <? endif; ?>
		        </div>
	        <p style="padding-bottom:0px;"></p>
            <? if ($service->website): ?>
		    <dt>網址:</dt>
		    <dd><p><a href="http://<?=$service->website?>"><?=$service->website?></a></p></dd>
			<? endif; ?>
			<? if ($service->phone): ?>
		    <dt>電話:</dt><dd><p><?=$service->phone?> <?=$service->contact_person?></p></dd>
			<? endif; ?>
			<? if ($service->address): ?>
		    <dt>地址:</dt><dd><p><?=$service->address?></p></dd>
			<? endif; ?>
         </dl>
         	<BR>
			<!-- Place this tag in your head or just before your close body tag -->
			<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
			  {lang: 'zh-TW'}
			</script>

			<!-- AddThis Button BEGIN -->
			<div class="addthis_toolbox addthis_default_style ">
			
			<a class="addthis_button_facebook_like" fb:like:layout="button_count"></a>
			<a class="addthis_counter addthis_pill_style"></a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<g:plusone size="medium"></g:plusone>
			</div>
			<script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js#username=xa-4d0746e21030861c"></script>
			<!-- AddThis Button END -->

		</div> <!-- col2 end -->
		
		<script>
	      	$("#short_member_desc span").click( function(event)
			{
			   $("#short_member_desc").hide();
			   $("#long_member_desc").show();
			});
	      	$("#long_member_desc span").click( function(event)
			{
			   $("#short_member_desc").show();
			   $("#long_member_desc").hide();
			});
		</script>	
		
		 <!-- buttons -->
        <ul class="head-actions">
          <?php if (isWriter($member->member_id)): ?>
    	      <li class="btn-input"><a href="/gallery/manageGallery/service/<?=$service->service_id?>">管理相片</a></li>
    	      <?php if ($service->subject_id <> 66): ?>
    	      	<li class="btn-input"><a href="/service/serviceEdit/<?=$service->service_id?>" rel="nofollow">管理服務</a></li>
    	      <?php endif; ?>
              <li class="btn-input"><a href="/leaflet/manage/<?=$service->service_id?>">管理傳單</a></li>
    	      <li class="btn-input"><a href="/service/questionList/<?=$service->service_id?>">回答發問</a></li>
          <?php else: ?>
	          <li class="btn-input"><a href="/service/questionList/<?=$service->service_id?>">向商戶發問</a></li>
              <li class="btn-input"><a href="/openform/comment/service/<?=$service->service_id?>">發表意見</a></li>
              <?php /*
              <li class="btn-input"><a href="/openform/email/friendservice/<?=$service->service_id?>">電郵朋友</a></li>
              */?>
          <?php endif; ?>
          <?php if (!isWriter($member->member_id)): ?>
	          <li class="btn-input"><a href="javascript:mark();">記錄到我的最愛</a></li>
          <?php endif; ?>          
        </ul>
	</div>
	
	<? if ($service->main_point): ?>
	<div class="main_content">	
		 <h2>必看要點</h2>
		 <div class="service-description">
				<?= $service->main_point?>	
		 </div>
	</div>
	<? endif; ?>
	
	<div class="main_content">	
		 <h2>服務簡介</h2>
		 
		 <dl class="service-description">
		  <? if ($service->description): ?>
	      <dt>簡介</dt>
	      <dd>
	      	<? if ($service->description <> $service->desc_short): ?>
	        <div id="long_desc" style="display: none;">
	          <p><?=nl2br($service->description)?>&nbsp;<span class="close-img">&nbsp;</span></p>
	          
	        </div>
	        <? endif; ?>
	        <div id="short_desc" style="">
	          <p><?=nl2br($service->desc_short)?>&nbsp;
		        <? if ($service->description <> $service->desc_short): ?>
		            &nbsp;<span class="open-img">&nbsp;</span>
	            <? endif; ?>
	          </p>  
	        </div>
	      </dd>
	      <? endif; ?>
		  <? if ($service->price_desc): ?>
	      <dt>特別之處</dt>
	      <dd>
	      	<? if ($service->price_desc <> $service->price_desc_short): ?>
	        <div id="long_price_desc" style="display: none;">
	          <p><?=nl2br($service->price_desc)?>&nbsp;<span class="close-img">&nbsp;</span></p>
	        </div>
	        <? endif; ?>
	        <div id="short_price_desc" style="">
	          <p><?=nl2br($service->price_desc_short)?>&nbsp;
		        <? if ($service->price_desc <> $service->price_desc_short): ?>
		            &nbsp;<span class="open-img">&nbsp;</span>
	            <? endif; ?>
	          </p>  
	        </div>
	      </dd>
	      <? endif; ?>
	      <? if ($service->promo_desc): ?>
	      <dt>推廣及細節</dt>
	      <dd>
	      	<? if ($service->promo_desc <> $service->promo_desc_short): ?>
	        <div id="long_promo_desc" style="display: none;">
	          <p><?=nl2br($service->promo_desc)?>&nbsp;<span class="close-img">&nbsp;</span></p>
	        </div>
	        <? endif; ?>
	        <div id="short_promo_desc" style="">
	          <p><?=nl2br($service->promo_desc_short)?>&nbsp;
		        <? if ($service->promo_desc <> $service->promo_desc_short): ?>
		            &nbsp;<span class="open-img">&nbsp;</span>
	            <? endif; ?>
	          </p>  
	        </div>
	      </dd>
	      <? endif; ?>
		  <dt>相片集</dt>
	      <dd><p><a href="/gallery/displayGallery/service/<?=$service->service_id?>">按此</a></p></dd>
		  <? /*
		  <dt>Comment</dt>
	      <dd><a href="/service/commentList/<?=$service->service_id?>">按此</a></dd>
		  <dt>Q and A</dt>
	      <dd><a href="/service/questionList/<?=$service->service_id?>">按此</a></dd>	      	      
	      */ ?>
	      <dt>更新日期</dt>
	      <dd><?= date('d/m/Y',strtotime($service->created))?></dd> 	      		  
      </dl>
    </div>  
    
	      <script>
	      	$("#short_desc span").click( function(event)
			{
			   $("#short_desc").hide();
			   $("#long_desc").show();
			});
	      	$("#long_desc span").click( function(event)
			{
			   $("#short_desc").show();
			   $("#long_desc").hide();
			});
	      	$("#short_price_desc span").click( function(event)
			{
			   $("#short_price_desc").hide();
			   $("#long_price_desc").show();
			});
	      	$("#long_price_desc span").click( function(event)
			{
			   $("#short_price_desc").show();
			   $("#long_price_desc").hide();
			});
	      	$("#short_promo_desc span").click( function(event)
			{
			   $("#short_promo_desc").hide();
			   $("#long_promo_desc").show();
			});
	      	$("#long_promo_desc span").click( function(event)
			{
			   $("#short_promo_desc").show();
			   $("#long_promo_desc").hide();
			});						
	      </script>    
	
    <? if ($planList && sizeof($planList) > 0): ?>
   	<div class="main_content">
	    <script type="text/javascript">
		    $(function() {
		    
		    	var position = document.getElementById('plan_position_<?=$plan_id_selected?>');
		    	
		    	if (position) {
			    	var pos = parseInt(position.value);
			    	$('#servicePlanTab').tabs(pos);		    		    	
			    }	
		    	else {
			    	$('#servicePlanTab').tabs(1);		    
			    }	

		    });
    	</script>      
		<? if (isset($planList)): ?>
		
		<script>
		function submitCompare() {
			document.getElementById("comparePlan").submit();
		}
		</script>
		<form id="comparePlan" action="/service/serviceCompare/" method="post">
		<input type="hidden" name="service_id" value="<?=$service->service_id?>" />
		<?	$count=0;
		foreach ($planList as $row) { 
				if ($count==0)
					$plan_id_list = $row->plan_id; 
				else	
					$plan_id_list = $plan_id_list . "," . $row->plan_id; 
			 $count++; 
			 } 
		?>	
			<input type="hidden" name="plan_id" value="<?=$plan_id_list?>" />
		</form>
		
		<h2>服務計畫 <span class="btn-sub"><a href="javascript:submitCompare();">比較全部計劃</a></span></h2>
		
		<p>
		
		    <div id="servicePlanTab">
		      <ul>
		      	<? $i=0;
				if (isset($planList))
				foreach ($planList as $row)
				{  $i++;
				?> 
					<li><a href="#servicePlan-<?=$i?>"><span><?=$row->plan_name?></span></a></li>
						<input type="hidden" id="plan_position_<?=$row->plan_id?>" name="plan_position_<?=$row->plan_id?>" value="<?=$i?>" />
		        <? } ?>
		      </ul>
		        
		      	<? $i=0;
				if (isset($planList))
				foreach ($planList as $planRow)
				{  $i++;
				?> 
		          <div id="servicePlan-<?=$i?>" class="service-plan">
					 <dl class="service-description">
					    <dt>價格</dt>
					    <dd>
						    <h3>$<?=$planRow->price?></h3>
					    </dd>
					    <!--
					    <dt>推廣</dt>
					    <dd>
						    <?=nl2br($planRow->promo_desc)?>
					    </dd>
					    -->
						<? if (isset($planItemList) && sizeof($planItemList) > 0 ): ?>
		
					    <dt>項目</dt>
					    <dd>
					    <table width="90%">
			          	<? 
			          	$j=0; $isNotFilled = true;
						foreach ($planItemList as $row)
						{   $j++;
							if ($row->plan_id == $planRow->plan_id) {
							
								if ( ($row->is_qty <> 'Y' && $row->qty > 0) || ($row->is_qty == 'Y' && $row->qty <> "")) {
									$isNotFilled = false;
						?> 
							        <tr height="25"><td width="70%" class="showDesc"><a href="#1" title="<?=$row->description?>"><div id="desc<?=$j?>_thumb"><?=$row->item_name?></div></a></td>
							        	<? if ($row->is_qty == 'Y'): ?>
							        	<td></td><td><?=$row->qty?></td>
							        	<? elseif ($row->is_qty <> 'Y' && $row->qty > 0): ?>
							        	<td></td><td><img src="/graphics/have.jpg"></td>
							        	<? elseif ($row->is_qty <> 'Y' && $row->qty == 0): ?>
							        	<td></td><td><img src="/graphics/nothave.jpg"></td>					        	
							        	<? endif; ?>
									</tr>
				        <? 
				        		}
				        	}
				        }
				        
				        if ($isNotFilled) {
				        	echo "<tr><td>N/A</td></tr>";
				        }
				        				         
				        ?>
				        </table>
				        </dd>
				        <? endif; ?>
				        <? if ($planRow->description): ?>
		 			    <dt>備注</dt>
					    <dd>
						    <?=nl2br($planRow->description)?>
					    </dd>
					    <? endif; ?>
					    </dl> 
		          </div>
		          <? } ?>
		     </div><!-- end servicePlanTab -->
		   </p>  
		<? endif; ?>
				
	</div> <!-- END main_content -->
	<? endif; ?>
	
	<? if ($service->map_lat != ""): ?>
	<div class="main_content" >
		<div id="map_canvas" style="width:600px; height:200px"></div>
	</div>
	<? endif; ?>
	
<div class="main_content" >
		 <h2>
		          意見
        <span class="btn-sub"><a href="/openform/comment/service/<?=$service->service_id?>">發表意見</a></span>
        </h2>
        
        
	    <div id="comment-area">
			<div id="comment-box">
			
			
				<ul class="comment-list">
				<? 
				if (isset($commentList['result']))
				foreach ($commentList['result'] as $row)
				{  
				?>
				  
				<li class="comment-item">
				  
					<div class="comment-item-head">
					  <a href="/member/memberInfo/<?=$row->writer_member_id?>/<?=str_replace("'", "", $member->company_name_eng)?>"><img src="/upload/<?=$row->photo?>" class="thumb-med" title="<?=$row->member_name?>" width="48" height="36"></a>
					</div>
				  	<span class="comment-item-info"><a href="/member/memberInfo/<?=$row->writer_member_id?>/<?=str_replace("'", "", $member->company_name_eng)?>"><?=$row->member_name?></a>, <?=$row->comment_date?> </span>
					<span class="rating">
						&nbsp;&nbsp;<?=showRating($row->rating)?>
					</span>
					<div class="comment-item-content">
				      <p><?=nl2br($row->comment_content)?></p>
				    </div>

				    <?php
					/*
				    <ul>
				        <li class="report-link"><a href="/openform/reportIncorrect/<?=$sick->sick_id?>/0/REV<?=$row->comment_id?>">Report inappropriate content</a></li>
				    </ul>
				    */
				    ?>
				</li>
				<BR>
				<hr class="dashing" />
				
				<? } ?>
				
				<? if ($commentList['total'] > 10): ?>
				<p style="text-align:right"><a href="/service/commentList/<?=$service->service_id?>">更多意見 (共 <?=$commentList['total']?> 個)</a></p>
				
				<?
				  showPagation("/service/commentList/". $service->service_id, $currentPage, $commentList['count']);
				?>
				
				<? endif; ?>
				
				<? if (sizeof($commentList['result']) <=0): ?>
					未有意見, 歡迎你立即發表你的意見.
				<? endif; ?>
				
				</ul>
				
			</div>
	    </div>    

	</div> <!-- END main_content -->
	
<? /*		
		
	<?	if (isset($commentList['result']) && sizeof($commentList['result']) > 0): ?>	
	<div class="main_content">	
		<h2>意見<span class="btn-sub"><a href="/service/commentList/<?=$service->service_id?>">更多意見</a></span></h2>
	
			<?  $i=1;
				if (isset($commentList['result']))
				foreach ($commentList['result'] as $row)
				{  
			?>		 	
	 				<p class="service_info_comment">
	 				<?
	 					if (mb_strlen(strip_tags($row->comment_content)) > 1600) {
	 				?>
	 					<?= mb_substr(strip_tags($row->comment_content),0 ,1600) ?> ...
	 				<? } else { ?>
	 					<?= nl2br(strip_tags($row->comment_content)) ?>
	 				<? } ?>
	 				<br><div class="service_info_comment_name"><?=$row->member_name?> | <?=mb_substr($row->comment_date,0,10)?></div>
	 				</p><BR> 				
	 				
	 		<? $i++;  } ?> 	
	 		
	 		<p><a href="/service/commentList/<?=$service->service_id?>">更多意見</a></p>
	</div> 	
	<? endif; ?>
	
*/ ?>	
		
	
</div> <!-- article_middle -->

<!-- end Middle Side -->

<script type="text/javascript">
$(document).ready(function()
{
   $('td.showDesc a[title]').qtip({
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

<? if ($service->map_lat != ""): ?>
  // init google map
  initialize();
<? endif; ?>
	
</script>


