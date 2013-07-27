<form action="/driver/toAddDriver" method="post">
<input type="hidden" name="driver_id" value="<?=$driver->driver_id?>"/>

Name: <input type="text" class="text" name="name" value="<?=$driver->name?>" size="40" maxlength="40" /><BR>
Phone: <input type="text" class="text" name="phone" value="<?=$driver->phone?>" size="40" maxlength="40" /><BR>
wechat: <input type="text" class="text" name="wechat" value="<?=$driver->wechat?>" size="40" maxlength="40" /><BR>
LINE: <input type="text" class="text" name="line" value="<?=$driver->line?>" size="40" maxlength="40" /><BR>
Car Plate: <input type="text" class="text" name="car_plate" value="<?=$driver->car_plate?>" size="40" maxlength="40" /><BR>
Remark:<BR><textarea rows="4" cols="40" name="remark"><?=$driver->remark?></textarea ><BR>

<input type="submit" class="submit button" name="submit" value="Submit" />

</form>