<div data-role="content">
<ul data-role="listview" data-theme="g">

<? 
	$count=0;
	if (isset($subjectList))
	foreach ($subjectList as $row)
	{  
		
		$count++;	
?>

			<li><a href="/service/serviceList/<?=$row->subject_id?>">
				<img src="/graphics/subject/s<?=$row->subject_id?>.jpg"  width="160" height="160"/>
				<h3><?= $row->subject?>&nbsp;&nbsp;(<?=$row->service_count?>)</h3>
				<p><?=$row->detail?></p>
			</a></li>
			
<?  
	} 
	
?>  
    
</ul>

	</div><!-- /content -->





