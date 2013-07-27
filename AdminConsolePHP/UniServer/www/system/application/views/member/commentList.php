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
	   	        <? if ($member->website): ?>
			    <dt>網址:</dt>
			    <dd><p><a href="http://<?=$member->website?>"><?=$member->website?></a></p></dd>	   	        	        	        
		        <? endif; ?>	        
		      </dl>
	      </div><!-- end col2 --> 
	</div>	
	
	
   	<div class="main_content">
	<!-- service list -->
	    <h2>我的意見</h2>
		<p>
  		<? 
		if (isset($commentList))
		foreach ($commentList as $row)
		{         
		?>  
		  <div class="member-service-list">	
	  		<h1><a href="/service/serviceInfo/<?=$row->service_id?>"><?=$row->member_name?> - <?=$row->service_name?></a></h1>
	  		<p></p>		  
	  		<dl class="business-info">
			  	<dt>日期</dt><dd class="service-dd"><p><?=$row->comment_date?>&nbsp;</p></dd>
				<p><?=nl2br($row->comment_content)?></p>
		  	</dl>
		  </div>	
		<? } ?> 
	<!-- service list end -->      
		</p>    
	</div> <!-- END main_content -->
	
	
</div> <!-- article_middle -->

<!-- end Middle Side -->
