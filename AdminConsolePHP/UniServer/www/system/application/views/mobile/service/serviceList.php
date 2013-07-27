<div data-role="content">	
<ul data-role="listview" data-theme="g">
<li data-role="list-divider"><?=$subject->subject?></li>
<? 
	$count=0;
	if (isset($serviceList['result']))
	foreach ($serviceList['result'] as $row)
	{  
		
		$count++;	
?>

			<li><a href="/service/serviceInfo/<?=$row->service_id?>">
				<img src="/upload/<?=$row->photo?>"  width="160" height="160"/>
				<h3><? if ($row->company_name_chi): ?>
                    	<?=$row->company_name_chi?><br><span style="font-size:0.7em;"><?=$row->company_name_eng?></span>
                    <? else: ?>
                    	<?=$row->company_name_eng?>
                    <? endif; ?></h3>
				<p><? if ($row->low_price): ?>
                    	<strong>$<?=$row->low_price?><span style="font-size:8px;">起</span></strong>
                	<? endif; ?></p>
			</a></li>
			
    
<? }   
   if (sizeof($serviceList['result']) == 0) {
?>
       <BR><BR>找不到記錄. 請嘗試其他的選擇.
<?
	} 
	
?>  
    
</ul>

	</div><!-- /content -->



