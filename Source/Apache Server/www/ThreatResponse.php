  GNU nano 2.0.9            File: helpNeeded.php                                

<?php
$message = $_POST["message"];
$notify = $_POST["notify"];

$to = '3073592632@vtext.com';
//$to = '9706921711@vtext.com';
$subject = strip_tags($message);
$body = strip_tags($notify);
//echo "message: "+ $message;
//echo "gps: "+$gps;
mail($to, $subject, $body);

?>

