<form action="/openform/submitReportIncorrect" method="post">
<input type="hidden" name="class_id" value="<?=$sick_id?>"/>
<input type="hidden" name="area_code" value="<?=$area_code?>"/>
<table cellpadding="3" align="center" width="40%">
<tr>
<td width="20%">Complaint about :</td>
<td><textarea name="comp_content"></textarea></td>
</tr>
<tr>
<td></td>
<td><input type="submit" name="submitComplaint" value="Submit" /></td>
</tr>
</table>
</form>
