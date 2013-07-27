<style>
ul, ol {
	list-style: none;
}
li {
	position: relative;
	font-size: 1em;
}
ul {
	padding: 5px 0 5px 15px;
}
ul li {
	padding: 3px 0 3px 11px;
} 
ul.specs li{
		background: url(/graphics/dotted-line.gif) 0 bottom repeat-x;
		margin-bottom: 7px;
} 
.remark {
	font-size: 0.8em;
}
.normal {
	font-size: 1em;
}
</style>
<div class="main_navigation" style="width:946px;">

	<div class="main_content" style="width:944px;">
	
		<ul class="specs">
		<li>
			<table cellspacing="5" width="100%">
				<tr>
					<td valign="top" width="10%"><font color="#2089b3"><b></b></font></td>
					<?		for ($i=0; $i < sizeof($planIdList); $i++) {
							foreach ($compareList as $row)
							{  
									if ($planIdList[$i] == $row->plan_id) {
										echo "<td width=\"10%\" align=\"center\"><font color=\"#2089b3\"><b><center>".nl2br($row->plan_name)."</center></b></font></td>";
									}
							}
						}
					?>
				</tr>
			</table>
		</li>
		<li>
			<table cellspacing="5" width="100%">
				<tr>
					<td valign="top" width="10%"><font color="#c60"><b>價格</b></font></td>
					<? printDataRow("price", $compareList, $planIdList); ?>
				</tr>
			</table>
		</li>
		<li>
			<table cellspacing="5" width="100%">
				<tr>
					<td valign="top" width="10%"><font color="#c60"><b>供應者</b></font></td>
					<? printDataRow("member_name", $compareList, $planIdList); ?>
				</tr>
			</table>
		</li>
		<?
		
		if (isset($compareDefaultItemList))
		foreach ($compareDefaultItemList as $row)
		{  
		?>
		<li>
			<table cellspacing="5" width="100%">
				<tr>
					<td valign="top" width="10%"><font color="#c60"><b><?=$row->rowLabelDesc?></b></font></td>
					<? printDataItemRow($row->rowLabelId, $compareItemList, $planIdList, $row->is_qty); ?>
				</tr>
			</table>
		</li>	
		<? } ?>  
		<li>
			<table cellspacing="5" width="100%" class="remark">
				<tr>
					<td valign="top" width="10%"><font color="#c60"><b class="normal">備注</b></font></td>
					<? printDataRow("description", $compareList, $planIdList, false) ?>
				</tr>
			</table>
		</li>
		</ul>
		
		<form action="/service/serviceInfo/<?=$service_id?>" method="post">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<li class="btn-input"><span><input type="submit" name="login" value="返回" /></span></li>
		</form>
	</div>
</div>