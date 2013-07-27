<a href="/driver/addDriver/">Add New Driver</a>
<BR>
<BR>
Driver List:
<table cellpadding="5" cellspacing="2">
<tr><th>ID</th><th>name</th><th>phone</th><th>wechat</th><th>line</th><th>car_plate</th><th>remark</th><th>status</th></tr>
<?
if (isset ($driverList))
  foreach ($driverList as $row) {
?>  
     <tr><td><?=$row->driver_id?></td><td><?=$row->name?></td><td><?=$row->phone?></td><td><?=$row->wechat?></td>
     <td><?=$row->line?></td><td><?=$row->car_plate?></td><td><?=nl2br($row->remark)?></td><td><?=$row->status?></td>
     <td><a href="/driver/editDriver/<?=$row->driver_id?>">Edit</a></td>
     <td><a href="/driver/editTimeslot/<?=$row->driver_id?>">TimeSlot</a></td></tr>
<? } ?> 
</table>