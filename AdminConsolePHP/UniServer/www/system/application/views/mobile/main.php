<div data-role="content">
<? 
$randNum = rand(0,3) % 3;
if ($randNum == 0): ?>
<span class="center-wrapper"><A href="/service/serviceList/5"><img alt="" src="/graphics/carousel/p1.jpg" width="300" height="166" /></a></span>
<? elseif ($randNum == 1): ?>
<span class="center-wrapper"><A href="/service/serviceList/8"><img alt="" src="/graphics/carousel/p2.jpg" width="300" height="166" /></a></span>
<? elseif ($randNum == 2): ?>
<span class="center-wrapper"><A href="/service/serviceList/8"><img alt="" src="/graphics/carousel/p3.jpg" width="300" height="166" /></a></span>
<? endif; ?>
<ul data-role="listview" data-theme="g" data-inset="true">
<li data-role="list-divider">熱門瀏覽</li>
<? 
	$count=0;
	if (isset($topViewServiceList))
	foreach ($topViewServiceList as $row)
	{  
		
		$count++;	
?>

			<li><a href="/service/serviceInfo/<?=$row->service_id?>">
				<img src="/upload/<?=$row->photo?>"  width="160" height="160"/>
				<h3><?=$row->member_name?></h3>
				<p>提供: <?=mb_substr($row->service_name,0,20) ?></p>
			</a></li>
			
<?  
	} 
	
?>  
    
</ul>
</div><!-- /content -->
