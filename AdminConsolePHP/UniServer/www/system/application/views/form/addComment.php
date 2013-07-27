<!-- Middle Side -->		
<div class="main_navigation">

	<div class="main_content">

		<h2><a href="/service/serviceList/<?=$service->subject_id?>"><?=$service->subject?></a> »&nbsp;<?=$member->member_name?> - <?=$service->service_name?></h2>
		
	    <div class="col1">
		  	<!-- user photo -->
			<img src="/upload/<?=$member->photo?>" title="" alt="" class="thumb-large" width="132" height="99">
		    <div class="rating-box"><img src="/graphics/heart1.gif" width="32" height="32"><br><?=$service->good?></div>
			<div class="rating-box"><img src="/graphics/heart2.gif" width="32" height="32"><br><?=$service->fair?></div>
			<div class="rating-box"><img src="/graphics/heart3.gif" width="32" height="32"><br><?=$service->bad?></div>
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
         </dl>      
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
		
	</div>	<!-- END main_content -->

	<div class="main_content">
	
			<h2>意見</h2>
			    
			<form action="/openform/submitComment" method="POST">
			<input type="hidden" name="key" value="<?=$key?>" />
			<input type="hidden" name="type" value="<?=$type?>" />			
			<input type="hidden" name="forwardURL" value="<?=$forwardURL?>" />					
			
			      <p></p>
				      
				      <p>
				      	<textarea class="description text" cols="60" rows="15" name="comment_content"><?=$content?></textarea>
				      </p>
				      <p>評分</p>				      
				      <p>
				      	<input type="radio" name="rating" value="1"/> <img src="/graphics/heart1.gif" width="32" height="32">
				      	<input type="radio" name="rating" value="0" checked /> <img src="/graphics/heart2.gif" width="32" height="32">
				      	<input type="radio" name="rating" value="-1"/> <img src="/graphics/heart3.gif" width="32" height="32">
				      </p>

				      <li class="btn-input"><span><input type="submit" name="sumbit" value="送出意見" /></span></li>
 							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<li class="btn-input"><a href="/<?=$type?>/<?=$type?>Info/<?=$key?>">&nbsp;&nbsp;返回&nbsp;&nbsp;</a></li>
			</form>
				      
			

	</div> <!-- END main_content -->
	
	
</div> <!-- main_navigation -->