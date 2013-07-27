<!-- Middle Side -->		
<div class="main_navigation">

	<div class="main_content">
	
	
		 <form id="contactForm" action="/member/updateMemberContact/" method="post" enctype="multipart/form-data">

	      <div class="col1">
	       <img src="/upload/<?=$member->photo?>" class="thumb-large" title="<?= $member->member_name?>" width="132" height="99">
	       <input type="hidden" name="photo" value="<?=$member->photo?>"  />
	      </div>
	      <div class="col2">
	        <h1><?= $member->member_name?></h1>
		      <dl class="business-info">
		        <? if ($member->company_name_chi): ?>
				<dt>中文:</dt>
			    <dd><p><?=$member->company_name_chi?></p></dd><input type="hidden" name="company_name_chi" value="<?=$member->company_name_chi?>"  />
		        <? endif; ?>
	   	        <? if ($member->company_name_eng): ?>
				<dt>英文:</dt>
			    <dd><p><?=$member->company_name_eng?></p></dd><input type="hidden" name="company_name_eng" value="<?=$member->company_name_eng?>"  />
		        <? endif; ?>
   				<p><?= $member->first_name?> <?= $member->last_name?></p><input type="hidden" name="first_name" value="<?=$member->first_name?>"  /><input type="hidden" name="last_name" value="<?=$member->last_name?>"  />
		        <? if ($member->address): ?>
		    	<p><?=$member->address?></p><input type="hidden" name="address" value="<?=$member->address?>"  />
		        <? endif; ?>   				
		        <? if ($member->phone): ?>	 
				<dt>電話:</dt>
			    <dd><p><?=$member->phone?></p></dd>		         <input type="hidden" name="phone" value="<?=$member->phone?>"  />      
		        <? endif; ?>	        
		        <? if ($member->fax): ?>
				<dt>傳真:</dt>
			    <dd><p><?=$member->fax?></p></dd>	<input type="hidden" name="fax" value="<?=$member->fax?>"  />               
		        <? endif; ?>
	   	        <? if ($member->email && $member->memberType == 'C'): ?>
				<dt>電郵:</dt>
			    <dd><p><?=$member->email?></p></dd>	<input type="hidden" name="email" value="<?=$member->email?>"  />	               
		        <? endif; ?>
	   	        <? if ($member->website): ?> 
			    <dt>網址:</dt>
			    <dd><p><a href="http://<?=$member->website?>"><?=$member->website?></a></p></dd> <input type="hidden" name="website" value="<?=$member->website?>"  />	   	        	        	        
		        <? endif; ?>	        
		      </dl>
	      </div><!-- end col2 --> 
	</div>	
	<!-- about me -->
	<? if ($member->description != "") { ?>
	<div class="main_content">
       <h2>關於我</h2>
	   <div class="about-me-description"><input type="hidden" name="about_me" value="<?=$member->description?>"  />
		    <p><? if ($member->description == "") { ?>
			    	未有"關於我"的資料.
		       <? } else { echo nl2br($member->description); } ?>
		    </p>
	   </div>
    </div>  
    <? } ?>
    
    <div class="main_content">
	    <ul class="head-actions" style="margin-top: 0px;">
	        <li class="btn-input"><a href="#" tabindex="15" onclick="$('#contactForm').submit();">確認儲存資料</a></li>
	        <li class="btn-input"><a href="javascript:back(-1);" tabindex="16">返回</a></li>
	  	</ul> 
	</div>
	
	
</div> <!-- article_middle -->

<!-- end Middle Side -->
