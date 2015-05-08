<?php

global $link_id;
$link_id = mysql_connect("cobi.cs.uwyo.edu","projectstart", "CarAlbLar7!");
if(! $link_id )
{
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("projectstart",$link_id);


$result = mysql_query("SELECT Email FROM UserInfo");
$emailArray = Array();
while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $emailArray[] =  $row['Email'];  
}
mysql_free_result($result);
//print_r(array_values($emailArray));

$result = mysql_query("SELECT CellPhone FROM UserInfo");
$phoneArray = Array();
while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
    $phoneArray[] =  $row['CellPhone'];
}
mysql_free_result($result);
//print_r(array_values($phoneArray));

$result = mysql_query("SELECT * FROM Distress WHERE status='UNRESOLVED'");
$distArray = Array();
while ($row = mysql_fetch_row($result)) {
    $distArray[] =  implode("\n",$row);
}
mysql_free_result($result);

//echo "<pre>";
//print_r(array_values($distArray));
//echo "</pre>";

foreach ($emailArray as &$valueE) {
    $to = $valueE;
    $subject = 'USER IN DISTRESS';
    $body = "ID | SAFE? | STATUS | NATURE | GPS | USER | PHONE | TIME SENT \n\n";
    foreach ($distArray as &$valueD){
        $body.=$valueD;
    }
   mail($to, $subject, $body);
}
unset($valueE);

foreach ($phoneArray as &$valueP) {
    $to = $valueP;
   $to .= '@vtext.com';
    //echo $to;
    $subject = 'USER IN DISTRESS';
    $body = "Incident Number: ";
    foreach ($distArray as &$valueD){
        $body.=$valueD;
	echo $valueD;
    }
    mail($to, $subject, $body);
}

echo "Done";
mysql_close($conn);
?>
