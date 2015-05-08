<?php
$nature = $_POST["nature"];
$user = $_POST["user"];

//$to = '3073592632@vtext.com';
//$to = '9706921711@vtext.com';
//$subject = strip_tags($nature);
//$body = strip_tags($user);
//mail($to, $subject, $body);

global $link_id;
$link_id = mysql_connect("cobi.cs.uwyo.edu","projectstart", "CarAlbLar7!");
if(! $link_id )
{
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("projectstart",$link_id);

$sql = "UPDATE Distress SET nature='$nature' WHERE user='$user'";

$retval = mysql_query( $sql, $link_id );
if(! $retval )
{
  die('Could not enter data: ' . mysql_error());
}

echo "Updated data successfully\n";
mysql_close($conn);

include 'notifyAll.php';

?>
