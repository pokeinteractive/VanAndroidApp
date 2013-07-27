<form action="/driver/toEditTimeslot" method="post">
<input type="hidden" name="driver_id" value="<?=$driver->driver_id?>"/>

Name: <?=$driver->name?><BR>
Phone: <?=$driver->phone?><BR>
Car Plate: <?=$driver->car_plate?><BR>
Remark:<?=$driver->remark?><BR>

<BR>
<BR>
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
				?><td><input type="checkbox" name="time_<?=$row->timeslot_id?>_<?=$loc->location_id?>" value="Y" /></td><?
		}
		
		echo "</tr>";
	}
?>

</table>


<input type="submit" class="submit button" name="submit" value="Submit" />

</form>