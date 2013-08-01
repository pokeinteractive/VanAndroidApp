<? 
  
  $today=date('Y-m-d');
  $i = 0;
  while( $i < 7 ) {
    
   $sdate = strtotime("+$i day",  strtotime ( $today )); 
    
   $weekday= date('N', $sdate);  
?>
<style>
 
 .timetable td {
    border:1px solid black;
border-collapse:collapse;
  }

</style>  
<BR>
<BR>
<B><?=date('Y-m-d',$sdate)?> : WEEKDAY <?=$weekday?> (1=Monday, 2=Tue, ... 7=Sunday)</B>  
<table class="timetable" style="border:1px solid black;border-collapse:collapse;">
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
       echo "<td>";
       foreach ($freeTimeList->regularTimeslot as $driverRegularTimeSlot) {
         //echo $driverRegularTimeSlot->location_id . " t=". $$driverRegularTimeSlot->timeslot_id . " w=". $driverRegularTimeSlot->weekday;
         if ($loc->location_id == $driverRegularTimeSlot->location_id && $row->timeslot_id == $driverRegularTimeSlot->timeslot_id && $weekday == $driverRegularTimeSlot->weekday) {
            echo $driverRegularTimeSlot->name . "(".$driverRegularTimeSlot->phone.")<BR>";
         }
       }
       foreach ($freeTimeList->adhocTimeslot as $driverAdhocTimeSlot) {
         //echo $driverRegularTimeSlot->location_id . " t=". $$driverRegularTimeSlot->timeslot_id . " w=". $driverRegularTimeSlot->weekday;
         if ($loc->location_id == $driverAdhocTimeSlot->location_id && $row->timeslot_id == $driverAdhocTimeSlot->timeslot_id && $driverAdhocTimeSlot->timeslot_date == date('Y-m-d',$sdate) ) {
            echo $driverAdhocTimeSlot->name . "[".$driverAdhocTimeSlot->phone."]<BR>";
         }
       }      
       echo "</td>";
       
    }
    
    echo "</tr>";
  }
?>

</table>

<? 
    $i= $i+1;
  } 
    
?>

