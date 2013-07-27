<form action="notice.php" method="post" />

IP:<input type="text" name="ip" value="<?=$_POST['ip']?>"/><BR>
PORT:<input type="text" name="port" value="<?=$_POST['port']?>"/><BR>
MESSAGE:<input type="text" name="message" value="From TW to MK"/><BR>
<input type="submit" name="GO" value="GO" />
</form>

<?php
$fp = fsockopen($_POST['ip'], $_POST['port'], $errno, $errstr, 30);
if (!$fp) {
    echo "$errstr ($errno)<br />\n";
} else {
    $out = $_POST['message'];
    fwrite($fp, $out);
    while (!feof($fp)) {
        echo fgets($fp, 128);
    }
    fclose($fp);

}
?>