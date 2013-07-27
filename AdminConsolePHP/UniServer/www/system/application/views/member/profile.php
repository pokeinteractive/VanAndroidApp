<!-- Middle Side -->		
<div class="main_navigation">

	<div class="main_content">

	      <div class="col1">
	       <img src="/upload/<?=$member->photo?>" class="thumb-large" title="<?= $member->member_name?>" width="132" height="99">
	      </div>
	      <div class="col2">
	        <h1><?= $member->member_name?></h1>
		      <dl class="business-info">
		        <? if ($member->company_name_chi): ?>
				<dt>中文:</dt>
			    <dd><p><?=$member->company_name_chi?></p></dd>
		        <? endif; ?>
	   	        <? if ($member->company_name_eng): ?>
				<dt>英文:</dt>
			    <dd><p><?=$member->company_name_eng?></p></dd>
		        <? endif; ?>
   				<p><?= $member->first_name?> <?= $member->last_name?></p>
		        <? if ($member->address): ?>
		    	<p><?=$member->address?></p>
		        <? endif; ?>   				
		        <? if ($member->phone): ?>	 
				<dt>電話:</dt>
			    <dd><p><?=$member->phone?></p></dd>		               
		        <? endif; ?>	        
		        <? if ($member->fax): ?>
				<dt>傳真:</dt>
			    <dd><p><?=$member->fax?></p></dd>		               
		        <? endif; ?>
	   	        <? if ($member->email && $member->memberType == 'C'): ?>
				<dt>電郵:</dt>
			    <dd><p><?=$member->email?></p></dd>		               
		        <? endif; ?>
	   	        <? if ($member->website): ?>
			    <dt>網址:</dt>
			    <dd><p><a href="http://<?=$member->website?>"><?=$member->website?></a></p></dd>	   	        	        	        
		        <? endif; ?>	        
		      </dl>
	      </div><!-- end col2 --> 
	</div>	
	<!-- about me -->
	<? if ($member->description != "" || $editMode) { ?>
	<div class="main_content">
       <h2>關於我</h2>
	   <div class="about-me-description">
		    <p><? if ($member->description == "") { ?>
			    	未有"關於我"的資料.
		       <? } else { echo nl2br($member->description); } ?>
		    </p>
	   </div>
	   
	  <? if ($editMode):  ?>
        <ul class="head-actions">
            <li class="btn-input"><a href="/member/updateProfile/">更改我的資料</a></li>
			&nbsp;&nbsp;            
            <li class="btn-input"><a href="/gallery/manageGallery/member/<?=$member->member_id?>">管理我的相簿</a></li>
        </ul>
      <? endif; ?> 
      
    </div>  
    <? } ?>

	<? if ($member->memberType == 'C'): ?>
   	<div class="main_content">
	<!-- service list -->
	    <h2>我的服務</h2>
		<p>
  		<? 
		if (isset($serviceList))
		foreach ($serviceList as $row)
		{         
		?>  
		  <div class="member-service-list">	
	  		<h1><a href="/service/serviceInfo/<?=$row->service_id?>"><?=$row->service_name?></a><? if ($editMode) { ?><span class="btn-sub"><a href="/service/serviceCopy/<?=$row->service_id?>">建立相似的服務</a></span><? } ?></h1>
	  		<p></p>		  
	  		<dl class="business-info">
			  	<dt>類別</dt><dd class="service-dd"><p><?=$row->subject?>&nbsp;</p></dd>
				<dt>電話</dt><dd class="service-dd"><p><?=$row->phone?>&nbsp;</p></dd>
				<dt>地址</dt><dd class="service-dd"><p><?=$row->address?>&nbsp;</p></dd>				
				<dt>服務計劃</dt><dd class="service-dd"><p>$<?=$row->low_price?> 起</p></dd>				
				<p><?=nl2br($row->description)?></p>
		  	</dl>
		  </div>	
		<? } ?> 
	<!-- service list end -->      
		</p>    
	</div> <!-- END main_content -->
	<? endif; ?>
	
	
</div> <!-- article_middle -->

<!-- end Middle Side -->
