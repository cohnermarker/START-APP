<?php
// include("db_nclude.php");
     global $link_id;
     $link_id = mysql_connect("cobi.cs.uwyo.edu","projectstart", "CarAlbLar7!");
     mysql_select_db("projectstart",$link_id);

//     $sql = "SELECT switch,port FROM network WHERE name LIKE '%".$host."%' ORDER BY num";
//     $result = mysql_query($sql);
//     $ary = mysql_fetch_row($result);
//  mysql -u projectstart -p -h cobi.cs.uwyo.edu


  function getx($name) {
    if (isset($_REQUEST[$name])) {
      $x = $_REQUEST[$name];
    } else {
      $x = "";
    }
    return  $x;
  }


  function done() {
    mysql_close($link_id);
  }
?>

