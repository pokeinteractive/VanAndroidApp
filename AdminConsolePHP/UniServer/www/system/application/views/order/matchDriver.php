<BR>
Order
<table cellpadding="5" cellspacing="2">
  <tr><th>ID</th><th>Cust Phone</th><th>Date</th><th>Timeslot</th><th>From</th><th>To</th><th>Driver</th><th></th><th>remark</th></tr>
     <tr><td><?=$order->order_id?></td><td><?=$order->cust_phone?></td>
       <td><?=$order->order_date?></td><td><?=$order->timeslot?></td><td><?=$order->from_location?></td>
     <td><?=$order->to_locaiton?></td><td><?=$order->name?></td><td><?=nl2br($order->remark)?></td><td><?=$order->status?></td>
  </tr>
</table>
<form action="/order/toMatchDriver" method=post >
  <input type=hidden name="order_id" value="<?=$order->order_id?>" />  
<select name="driver_id" size="30">
<? foreach ($driverList as $driver) { ?>

  <option value="<?=$driver->driver_id?>" ><?=$driver->name?> (<?=$driver->phone?>)</option>

<? } ?>
</select>
  <BR><BR>
<input type="submit" name="s" value="Sumbit"/>
</form>
  
