<!-- Middle Side -->		
<div class="main_navigation" style="width:966px;">

	<div class="main_content" style="width:944px;">

		<h2><a href="/service/serviceList/<?=$service->subject_id?>"><?=$service->subject?></a> »&nbsp;<?=$member->member_name?> - <a href="/service/serviceInfo/<?=$service->service_id?>"><?=$service->service_name?></a></h2>
		
	    <div class="col1">
		  	<!-- user photo -->
			<img src="/upload/<?=$member->photo?>" title="" alt="" class="thumb-large" width="132" height="99">
		    <div class="rating-box"><img src="/graphics/heart1.gif" width="32" height="32"><br><?=$service->good?></div>
			<div class="rating-box"><img src="/graphics/heart2.gif" width="32" height="32"><br><?=$service->fair?></div>
			<div class="rating-box"><img src="/graphics/heart3.gif" width="32" height="32"><br><?=$service->bad?></div>
	    </div>

      	<div class="col2">

         <dl class="business-info">
	        <h3><a href="/service/serviceInfo/<?=$service->service_id?>"><?=$service->service_name?></a></h3>
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
	</div>
	
	<div class="main_content" style="width:944px;">
	  	<h2>問答</h2>
        <p></p>
        <? if (!isWriter($member->member_id)): ?>
	        <form action="/service/submitQuestion" method="POST">
	        <input type="hidden" name="key" value="<?=$service->service_id?>" />
	        <input type="hidden" name="type" value="service" />	
	        <div class="question">
	        	你的問題:
	        	<p><textarea class="description text" cols="40" rows="5" name="question_content"></textarea></p>
		      	<li class="btn-input"><span><input type="submit" name="sumbit" value="送出問題" /></span></li>
	        </div>
	        </form>
	
			<p></p>
		<? endif; ?>

		<div id="comment-area">
			<div id="comment-box">
			
			
				<ul class="comment-list">
				<? 
				if (isset($questionList['result']))
				foreach ($questionList['result'] as $row)
				{  
				?>
				
				
				  
				<li class="comment-item">
				  
					<div class="comment-item-head">
					  <a href="/member/memberInfo/<?=$row->writer_member_id?>/<?=str_replace("'", "", $member->company_name_eng)?>"><img src="/upload/<?=$row->photo?>" class="thumb-med" title="<?=$row->member_name?>" width="48" height="36"></a>
					</div>
				  	<span class="comment-item-info"><a href="/member/memberInfo/<?=$row->writer_member_id?>/<?=str_replace("'", "", $member->company_name_eng)?>"><?=$row->member_name?></a>, <?=$row->created?> 發表問題: </span>
					<div class="comment-item-content">
				      <p><?=nl2br($row->question_content)?></p>
				    </div>
				    <? if (!isWriter($member->member_id) && $row->ans_created): ?>
				    <div class="comment-item-answer-area">
						<div class="comment-item-answer-head">回覆 :</div>				    
						<div class="comment-item-answer">
					      <p><?=nl2br($row->ans_content)?></p>
					    </div>				    
					    <span class="comment-item-answer-info">回覆時間: <?=$row->ans_created?></span>
					</div>
 					<? elseif (isWriter($member->member_id)): ?>
 					<div class="comment-item-answer-area">
				       <form action="/service/submitAnswer" method="POST">
				       <input type="hidden" name="question_id" value="<?=$row->question_id?>" />
		               <input type="hidden" name="key" value="<?=$service->service_id?>" />
		               <input type="hidden" name="type" value="service" />	
		               <input type="hidden" name="page" value="<?=$currentPage?>" />
						<div class="comment-item-answer-area">
							<div class="comment-item-answer-head">你的回覆 :</div>				    
							<div class="comment-item-answer">
						      <p><textarea class="description text" cols="80" rows="5" name="ans_content"><?=$row->ans_content?></textarea></p>
						      <li class="btn-input"><span><input type="submit" name="sumbit" value="送出答覆" /></span></li> 
						    </div>				    
						</div>
				      	</form>
					<? endif; ?>
				    <?php
					/*
				    <ul>
				        <li class="report-link"><a href="/openform/reportIncorrect/<?=$sick->sick_id?>/0/REV<?=$row->comment_id?>">Report inappropriate content</a></li>
				    </ul>
				    */
				    ?>
				
				</li>
				
				<hr class="dashing" />
				
				<? } ?>
				
				</ul>
				
			</div>
	    </div>        

		<?
		  showPagation("/service/questionList/". $service->service_id, $currentPage, $questionList['count']);
		?>

	</div> <!-- END main_content -->
	
	
</div> <!-- article_middle -->

<!-- end Middle Side -->



