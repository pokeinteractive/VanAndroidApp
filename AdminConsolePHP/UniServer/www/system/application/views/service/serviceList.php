<!-- Middle Side -->		
<div class="main_navigation">

	<div class="main_content">
		<h2><?=$subject->subject?> 
		<? if ($subject->subject_id && isCompany()): ?>
			<span class="btn-sub"><a href="/service/serviceAdd/<?=$subject->subject_id?>" rel="nofollow">加入新服務</a></span>
		<? endif; ?>
		</h2>
		
			<? 
			$count=0;
			if (isset($serviceList['result']))
			foreach ($serviceList['result'] as $row)
			{  
				
				$count++;	
			?>    
			<div class="service-parent-outer hoverable">
			<div class="service-parent corner">
				<div class="service-item">
					<div class="service-list">
                        <div class="service-img">
                            <a href="/service/serviceInfo/<?=$row->service_id?>" alt="<?=$row->member_name?>">
                                <img alt="" src="/upload/<?=$row->photo?>" width="140" height="105" />
                            </a>
                        </div>
                        <div class="service-text">
                            <div class="service-dtl">
                                <h3><a href="/service/serviceInfo/<?=$row->service_id?>">
                                <? if ($row->company_name_chi): ?>
                                	<?=$row->company_name_chi?><br><span style="font-size:0.7em;"><?=$row->company_name_eng?></span>
                                <? else: ?>
                                	<?=$row->company_name_eng?>
                                <? endif; ?>
                                </a></h3>
                                <div class="description">
                                    <span style="color:#888888;"><?=$row->service_name?></span>
                                    <dl class="description-dtl" style="padding-top:5px">
                                        <dd ><?=$row->description_short?></dd>
                                        <dd style="color:#777777;"><?=$row->price_desc_short?></dd>
                                        <dt style="color:#AAAAAA;" class="date">更新日期:</dt><dd class="date"><?= date('d/m/Y',strtotime($row->created))?></dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="price">
                                <? if ($row->low_price): ?>
                                <strong>$<?=$row->low_price?><span style="font-size:8px;">起</span></strong>
                            	<? endif; ?>
                            		<? /*
                                	<div style="margin-top:13px"><strong style="text-align:right; font-size:0.7em;"><a href="/service/commentList/<?=$row->service_id?>">
                            		<? if ($row->comment > 0): ?>                                	
                                	<?=$row->comment?>個意見
                                	<? endif; ?>
                                	<? if ($row->comment == 0): ?>
                                		未有意見
   	                                <? endif; ?>
                                	</a></strong></div>
                                	
                                	<br>
									    <div class="rating-box"><img src="/graphics/heart1.gif" width="32" height="32"><br><?=$row->good?></div>
										<div class="rating-box"><img src="/graphics/heart2.gif" width="32" height="32"><br><?=$row->fair?></div>
										<div class="rating-box"><img src="/graphics/heart3.gif" width="32" height="32"><br><?=$row->bad?></div>
										
									*/	?>
                                	
                            </div>
                        </div>
                       
                        
                    </div>
			    </div>
			 </div>   
			 </div>
			 <hr class="dashing" />
			    
			    
			<? }   
			   if (sizeof($serviceList['result']) == 0) {
			?>
		           <BR><BR>找不到記錄. 請嘗試其他的選擇.
			<?
				} 
				
			?>  
	    <div>
	      <?
	      
		  showPostMethodPagation("/service/serviceList/".$subject->subject_id, $currentPage, $serviceList['count'], true);
		  //showPagation("/service/serviceList/" . $subject->subject_id, $currentPage, $serviceList['count'], $location_id);
		  
		  ?>
		</div>  
	</div>
	
	
</div> <!-- article_middle -->

<!-- end Middle Side -->


<?
/*
			    <li>
			       	<div class="member-photo"><img src="/upload/<?=$row->photo?>" title="<?=mb_substr($row->description, 0, 50)?>" alt="<?=$row->member_name?>" class="thumb-med" width="48" height="48"></div>
			       	<div class="member-info">
			        <a href="/service/serviceInfo/<?=$row->service_id?>"><?=$row->service_name?></a>
			        <?=$row->member_name?>
			        </div>
			    </li>
			    
				<div class="service-item">
					<div class="service-list">
                        <div class="service-img">
                            <a href="/New_York/New_York/e-52nd-st:5d3cd764a68818357487af53d8b6020;_ylt=AjXVINz72YCRbhH8.Bjl40Nn47Qs" alt="re045">
                                <img alt="" src="/upload/<?=$row->photo?>" width="160" height="105" />
                            </a>
                        </div>
                        <div class="service-text">
                            <div class="service-dtl">
                                <h3><a href="/service/serviceInfo/<?=$row->service_id?>"><?=$row->service_name?></a></h3>
                                <div class="description">
                                    New York, NY
                                    <dl class="description-dtl">
                                        <dt class="beds">Beds:</dt><dd class="beds">10</dd>
                                        <dt class="bath">Bath:</dt><dd class="bath">6</dd>
                                        <dt class="sqft">Sq. Feet:</dt><dd class="sqft">7,338</dd>
                                        <dt class="type">Type:</dt><dd class="type">Single Family Home</dd>
                                        <dt class="neighborhood">Neighborhood:</dt><dd class="neighborhood">Manhattan</dd>
                                        <dt class="date">Listed on Yahoo!:</dt><dd class="date">06/05/2009</dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="price">
                                <strong>$3,500,000</strong>
                            </div>
                            
                        </div>
                        
                        
                    </div>
			    </div>			    
			    
*/
?>
