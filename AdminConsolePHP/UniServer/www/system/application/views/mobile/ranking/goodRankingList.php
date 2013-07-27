<div data-role="content">	
<ul data-role="listview" data-theme="g">
<li data-role="list-divider">評分排名</li>
<? 
	if (isset($subjectList))
	foreach ($subjectList as $rowSubject)
	{ 
		
?>
	<li style="background:#FFFFFF;color:red"><?= $rowSubject->subject ?></li>


				<?  $i=1;
					if (isset($rowSubject->topServiceList))
					foreach ($rowSubject->topServiceList as $row)
					{  
				?>		 	
	 				<li>
	 				<a href="/service/serviceInfo/<?=$row->service_id?>"><img src="/upload/<?=$row->photo?>"  width="160" height="160">
	 				<h3><?=mb_substr($row->member_name,0,20) ?></h3>
					<?=mb_substr($row->service_name,0,20) ?><span class="ui-li-aside"><img style="margin-top:0px;" src="/graphics/heart1.gif" width="20" height="20" />&nbsp;<?=$row->good?>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></li> 				
		 				
		 		<? $i++;  } ?> 
			
    
<? 
	}   
?>  
    
</ul>

	</div><!-- /content -->








