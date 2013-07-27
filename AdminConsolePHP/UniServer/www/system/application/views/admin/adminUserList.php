<!-- Middle Side -->		
<div class="main_navigation" style="width:966px;">

	<div class="main_content" style="width:944px;">
		<table  border="1">
			<tr><td>Name</td><td width="300">Service</td><td>email</td><td>address</td><td>phone</td><td>website</td><td>Action</td></tr>		
  		<? 
		if (isset($memberList))
		foreach ($memberList as $row)
		{         
		?>  
			<tr><td><a href="/member/rootadminlogin/<?=$row->member_id?>"><?=$row->member_name?></a></td>
			<td width="300"><a href="/service/serviceInfo/<?=$row->service_id?>"><?=$row->service_name?></a></td><td><?=$row->email?></td><td><?=$row->address?></td>
			<td><?=$row->phone?></td><td><?=$row->website?></td><td><a href="/admin/editServiceDetail/<?=$row->service_id?>">Edit Detail</a></td></tr>

		
		<? } ?> 		
		</table>
	</div>
</div>		