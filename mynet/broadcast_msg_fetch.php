<?php
	require('db_config.php');
	
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	$user_name = $_POST['user_name'];

	$result = mysqli_query($con,"SELECT message FROM broadcast ORDER BY message_id DESC LIMIT 1") or die(mysqli_error($con)); 
	
	if (mysqli_num_rows($result) > 0) 
	{
		while ($row = mysqli_fetch_array($result)) 
		{
			$users = array();
            $users["message"] = $row["message"];
		   //$response["success"] = 1;
		   echo $users["message"];
		}
		
	}    
	
?>