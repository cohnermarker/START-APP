<?php
$message = $_POST["message"];

$to = '3073592632@vtext.com';
//$to = '9706921711@vtext.com';
$subject = 'Are You Safe?';
$body = strip_tags($message);
//echo "message: "+ $message;
//echo "gps: "+$gps;
mail($to, $subject, $body);

?>

