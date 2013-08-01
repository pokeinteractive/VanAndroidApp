<html>
<head>
<title>CallVan Report</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>﻿
<?php

function SQLResultTable($Query)
{
    $link = mysql_connect("localhost","vanapp","passwd01") or die('Could not connect: ' . mysql_error());      //build MySQL Link

    mysql_select_db("poke_van") or die('Could not select database');        //select database
    $Table = "";  //initialize table variable
   
    $Table.= "<table border='1' style=\"border-collapse: collapse;\">"; //Open HTML Table
   
              mysql_query("SET NAMES 'utf8'"); 
         mysql_query("SET CHARACTER_SET_CLIENT=utf8"); 
         mysql_query("SET CHARACTER_SET_RESULTS=utf8"); 
   
   
    $Result = mysql_query($Query); //Execute the query
    if(mysql_error())
    {
        $Table.= "<tr><td>MySQL ERROR: " . mysql_error() . "</td></tr>";
    }
    else
    {
        //Header Row with Field Names
        $NumFields = mysql_num_fields($Result);
        $Table.= "<tr style=\"background-color: #999999; color: #222222;\">";
        for ($i=0; $i < $NumFields; $i++)
        {    
            $Table.= "<th>" . mysql_field_name($Result, $i) . "</th>";
        }
        $Table.= "</tr>";
   
        //Loop thru results
        $RowCt = 0; //Row Counter
        while($Row = mysql_fetch_assoc($Result))
        {
            //Alternate colors for rows
            if($RowCt++ % 2 == 0) $Style = "background-color: #DDDDDD;";
            else $Style = "background-color: #EEEEEE;";
           
            $Table.= "<tr style=\"$Style\">";
            //Loop thru each field
            foreach($Row as $field => $value)
            {
                $Table.= "<td>$value</td>";
            }
            $Table.= "</tr>";
        }
        $Table.= "<tr style=\"background-color: #555555; color: #FFFFFF;\"><td colspan='$NumFields'>Query Returned " . mysql_num_rows($Result) . " records</td></tr>";
    }
    $Table.= "</table>";
   
    return $Table;
}
 
//Call the function like this:
//echo SQLResultTable("SELECT o.*, i.*, pd.name FROM fast_order o, fast_order_item i, oc_product_description pd where //o.fast_order_id=i.fast_order_id and pd.product_id=i.product_id and o.status='P' order by o.fast_order_id ");



?>

  <form action="/report/listReport" method="post">
Start Date : <input type="text" name="startdate" value="<?=$startdate?>" /> End Date: <input type="text" name="enddate" value="<?=$enddate?>" /> <input type="submit"/>
<BR>
Sale Item:<BR>
<?php 
$sql = "select o.order_id,  o.order_date, o.cust_phone, o.remark ,t.name as timeslot, l.name as from_location, l2.name as to_locaiton, d.name as driverName, d.phone as driverPhone, o.driver_id 
  from `order` o LEFT JOIN `driver` AS d ON d.driver_id = o.driver_id, timeslot t, location l, location l2 where o.timeslot_id=t.timeslot_id and o.to_location_id=l2.location_id and o.from_location_id=l.location_id 
  and o.status = 'Y' and  o.order_date>= '$startdate' and o.order_date<= '$enddate' 
order by o.order_date, t.seq ";  
  //  echo $sql;
    echo SQLResultTable($sql );
?>


</form> 