<?php
$message = $_POST["message"];
$gps = $_POST["GPS"];

$to = '3073592632@vtext.com';
//$to = '9706921711@vtext.com';
$subject = strip_tags($message);
$body = strip_tags($gps);
//echo "message: "+ $message;
//echo "gps: "+$gps;
mail($to, $subject, $body);
 
?>

