<?php
$userName = $_POST["userName"];
$passName = $_POST["passName"];

$userName = strip_tags($userName);
$passName = strip_tags($passName);

global $link_id;
$link_id = mysql_connect("cobi.cs.uwyo.edu","projectstart", "CarAlbLar7!");
if(! $link_id )
{
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("projectstart",$link_id);

$sql = "INSERT INTO `login` (`id`, `username`, `password`) VALUES (NULL,'$userName', '$passName');";

$retval = mysql_query( $sql, $link_id );
if(! $retval )
{
  die('Could not enter data: ' . mysql_error());
}

echo "Entered data successfully\n";
mysql_close($conn);
 
?>
