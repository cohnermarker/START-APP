<?php
$status = $_POST["status"];
$user = $_POST["user"];
$cellphone = $_POST["cellphone"];
$date = date('Y/m/d H:i:s');
$gps = $_POST["gps"];

$status = strip_tags($status);
$user = strip_tags($user);
$cellphone = strip_tags($cellphone);

//$to = '3073592632@vtext.com';
//$to = '9706921711@vtext.com';
//$subject = strip_tags($user);
//$body = strip_tags($cellphone);

mail($to, $subject, $body);


global $link_id;
$link_id = mysql_connect("cobi.cs.uwyo.edu","projectstart", "CarAlbLar7!");
if(! $link_id )
{
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("projectstart",$link_id);

$sql = "INSERT INTO `Distress` (`id`, `safe`, `status`, `nature`, `gps`, `user`, `cellphone`, `timerec`, `timeres`) VALUES (NULL, NULL, '$status', NULL, '$gps', '$user', '$cellphone', '$date', NULL);";

$retval = mysql_query( $sql, $link_id );
if(! $retval )
{
  die('Could not enter data: ' . mysql_error());
}

echo "Entered data successfully\n";
mysql_close($conn);
 
?>
