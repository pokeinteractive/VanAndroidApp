<!-- Middle Side -->		
<div class="main_navigation" style="width:966px;">

	<div class="main_content" style="width:944px;">
		<table>
			<tr><td width="50">order ID</td><td width="300">Prod. Name</td><td width="50">QTY</td><td width="70">price</td><td width="70">deposit</td><td width="150">email</td></tr>		
  		<? 
		if (isset($orderList))
		foreach ($orderList as $row)
		{         
		?>  
			<tr style="height:25px; border-bottom: 1px solid #CECECE;"><td><?=$row->order_id?></td>
			<td width="300"><?=$row->name?></td><td><?=$row->qty?></td><td><?=$row->price?></td>
			<td><?=$row->deposit?></td><td><?=$row->email?></td></tr>
		<? } ?> 		
		</table>
	</div>
</div>		