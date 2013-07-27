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
	    <h2>我的物品</h2>
	    <BR>
		<table width="100%" class="messageTable">
  		<? 
		if (isset($productList))
		foreach ($productList as $row)
		{         
		?>  
		  <tr>
			<td><a href="/sell/productInfo/<?=$row->product_id?>"><img src="/upload/sell/<?=$row->photo?>" title="" alt="" class="thumb-large" width="160" height="120"></a></td>
	  		<td><h1><a href="/sell/productInfo/<?=$row->product_id?>"><?=$row->cat_name ?> - <?=$row->name?></a></h1></td>
	  		<td><?=$row->update_date?>&nbsp;</p></td>
		  </tr>	
		<? } ?> 
	<!-- service list end -->      
		</table>    
	</div> <!-- END main_content -->
	
	
</div> <!-- article_middle -->

<!-- end Middle Side -->
