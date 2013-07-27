<form action="/order/toAddOrder" method="post">
<input type="hidden" name="order_id" value="<?=$order->order_id?>"/>

  Customer Phone: <input type="text" name="cust_phone" value="<?=$order->cust_phone?>"/><BR>
  <? if (!$order->order_date) $order->order_date=date('Y-m-d'); ?>
  Date : <input type="text" name="order_date" value="<?=$order->order_date?>"/><BR>
  
  Choose Timeslot  : <BR>
  <select name="timeslot_id" size="8">
  <? foreach ($timeslotList as $row) {
      $selected = "";
      if ($row->timeslot_id == $order->timeslot_id) $selected = "selected";
      echo "<option value='$row->timeslot_id' $selected >$row->name</td>";
     }
  ?>
  </select>
  
<BR>
  Remark: <textarea name="remark" cols=50 rows=3><?=$order->remark?></textarea>
<BR>
<table>
  <tr><td colspan="16"><center>Source</center></td></tr>
<tr>

<td>To \ From</td>
<?
  foreach ($locationList as $row) {
    echo "<td width=75>$row->name <BR>($row->area)</td>";
  }
?>
</tr>
<?
  foreach ($locationList  as $row) {
    echo "<tr height=50><td width='85'>$row->name <BR>($row->area)</td>";
    
    foreach ($locationList as $loc) {
      $checked = "";
      if ($loc->location_id == $order->from_location_id && $row->location_id == $order->to_location_id)
          $checked = "checked";
      
        ?><td><input <?=$checked?> type="radio"  name="location_map" value="loc_<?=$loc->location_id?>_<?=$row->location_id?>" /></td><?
    }
    
    echo "</tr>";
  }
?>

</table>


<input type="submit" class="submit button" name="submit" value="Submit" />

</form>