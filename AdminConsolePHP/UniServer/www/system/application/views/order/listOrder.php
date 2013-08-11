<BR>
Order List:
<table cellpadding="5" cellspacing="2">
  <tr><th>ID</th><th>Cust Phone</th><th>Date</th><th>Timeslot</th><th>From</th><th>To</th><th>Price</th><th>Driver</th><th></th><th>remark</th></tr>
<?
if (isset ($orderList))
  foreach ($orderList as $row) {
?>  
     <tr><td><?=$row->order_id?></td><td><?=$row->cust_phone?></td>
       <td><?=$row->order_date?></td><td><?=$row->timeslot?></td><td><?=$row->from_location?></td>
     <td><?=$row->to_location?></td><td>$<?=$row->price?></td><td><?=$row->name?>(<?=$row->phone?>)</td><td><?=nl2br($row->remark)?></td><td><?=$row->status?></td>
     <td><a href="/order/matchDriver/<?=$row->order_id?>">Match Driver</a></td>
     <td><a href="/order/editOrder/<?=$row->order_id?>">Edit Order</a></td></tr>
<? } ?> 
</table>