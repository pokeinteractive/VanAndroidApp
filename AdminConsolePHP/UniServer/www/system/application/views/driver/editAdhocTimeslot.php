<form action="/driver/toEditAdhocTimeslot" method="post">
<input type="hidden" name="driver_id" value="<?=$driver->driver_id?>"/>

Name: <?=$driver->name?><BR>
Phone: <?=$driver->phone?><BR>
Car Plate: <?=$driver->car_plate?><BR>
Remark:<?=$driver->remark?><BR>

<? 
  
  $today=date('Y-m-d');
  $i = 0;
  while( $i < 7 ) {
    
   $sdate = strtotime("+$i day",  strtotime ( $today )); 
    
   $weekday= date('N', $sdate);  
   $inputdate = date('Y-m-d',$sdate);
?>
<BR>
<BR>
<B><?=$inputdate ?> WEEKDAY <?=$weekday?> (1=Monday, 2=Tue, ... 7=Sunday)</B>  
<table>
<tr>
<td>Timeslot\Location</td>
<?
  foreach ($locationList as $row) {
    echo "<td width=75>$row->name <BR>($row->area)</td>";
  }
?>
</tr>
<?
  foreach ($timeslotList as $row) {
    echo "<tr height=40><td>$row->name</td>";
    
    foreach ($locationList as $loc) {
       $checkedbox = "";
       foreach ($driverTimeSlotList as $driverRegularTimeSlot) {
       
         if ($loc->location_id == $driverRegularTimeSlot->location_id && $row->timeslot_id == $driverRegularTimeSlot->timeslot_id && $inputdate == $driverRegularTimeSlot->timeslot_date) {
            $checkedbox="checked";
         }
       }
       ?><td><input <?=$checkedbox?> type="checkbox" name="time_<?=$row->timeslot_id?>_<?=$loc->location_id?>_<?=$inputdate?>" value="Y" /></td><?
       
    }
    
    echo "</tr>";
  }
?>

</table>

<? 
  $i= $i+1;
  } 
    
?>

<input type="submit" class="submit button" name="submit" value="Submit" />

</form>