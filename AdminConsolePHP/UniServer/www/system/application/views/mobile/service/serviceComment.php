<div data-role="content">	
	<ul data-role="listview" data-theme="g">        
        	<li data-role="list-divider"><?=$member->member_name?> - <?=$service->service_name?></li>
	    			<? 
				if (isset($commentList['result']))
				foreach ($commentList['result'] as $row)
				{  
				?>
				
				
				  
				<li>
				  <table><tr><td width="50%"><font color="red"><?=$row->member_name?></font></td><td width="40%"><?=$row->comment_date?></td><td width="10%"><?=showRating($row->rating)?></td></tr>
				      <tr><td colspan="3" class="service-description"><?=nl2br($row->comment_content)?></td></tr>
				  </table>
				</li>
				
				<hr style="background:#FF4444" />
				
				<? } ?>
				
				<? if (sizeof($commentList['result']) <=0): ?>
					未有意見, 歡迎你立即發表你的意見.
				<? endif; ?>
				
				</ul>
		 
   
</div> <!-- END content -->
	



