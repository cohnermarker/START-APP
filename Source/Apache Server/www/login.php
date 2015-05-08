<?php
$username = $_POST["username"];
$password = $_POST["password"];

global $link_id;
$link_id = mysql_connect("cobi.cs.uwyo.edu","projectstart", "CarAlbLar7!");
if(! $link_id )
{
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("projectstart",$link_id);

$query= mysql_query("select * from login where password='$password' AND username='$username'", $link_id);

$rows = mysql_num_rows($query);
if ($rows == 1) {

    print "Success";

} else {

    print "Failure";
}
mysql_close($link_id); // Closing Connection

?>
