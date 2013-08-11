<script language="javascript" type="text/javascript"  charset="utf8">
 function dropdownlist(listindex, select_id)
 {
   var selectBox = document.getElementById(select_id);
   selectBox.options.length = 0;
 switch (listindex)
 {
  <? 
    $currentAreaId = "";
    $count=0;
    foreach ($locationList as $row) { 
      if ($currentAreaId !=  $row->from_id) {
        if ($currentAreaId <> "")
          echo " break; \r\n";
     
         echo " case \"". $row->from_id . "\" :";
         $count = 0;
         $currentAreaId = $row->from_id;
      }
      echo "selectBox.options[$count]=new Option(\"".$row->from_name."\",\"".$row->from_name."\");";
   
      $count = $count + 1;
     }
  ?>

 
 }
 return true;
 }
</script>

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
  From Area:
  <select name="from_area" size='12' onchange="javascript:dropdownlist(this.options[this.selectedIndex].value, 'fromLocationID');">
    <? foreach ($areaList  as $row) { ?>
    <option <?php if ($order->from_location_id == $row->location_id) echo "selected"; ?> value='<?=$row->location_id?>'><?=$row->name?></option>
    <? } ?> 
  </select>
  From Location:
  <select name="from_location" id="fromLocationID" >
    <?php if ($order->from_location) { ?>     
    <option value="<?=$order->from_location?>"><?=$order->from_location?></option>
    <?php } else { ?>
    <option value="">Select Sub-Category</option>
    <?php } ?>
    
  </select>

  
  To Area:
  <select name="to_area" size='12' onchange="javascript:dropdownlist(this.options[this.selectedIndex].value, 'toLocationID');">>
    <? foreach ($areaList  as $row) { ?>
    <option  <?php if ($order->to_location_id == $row->location_id) echo "selected"; ?> value='<?=$row->location_id?>'><?=$row->name?></option>
    <? } ?> 
  </select>
  To Location:
  <select name="to_location" id="toLocationID" >
    <?php if ($order->to_location) { ?>     
    <option value="<?=$order->to_location?>"><?=$order->to_location?></option>
    <?php } else { ?>
    <option value="">Select Sub-Category</option>
    <?php } ?>
    

  </select>


<BR><BR>
<input type="submit" class="submit button" name="submit" value="Submit" />

</form>