<?php
	require('db_config.php');
	
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');

	$result = mysqli_query($con,"SELECT username FROM mynet") or die(mysqli_error($con)); 
	
	if (mysqli_num_rows($result) > 0) 
	{
		while ($row = mysqli_fetch_array($result)) 
		{
			$users = array();
            $users["username"] = $row["username"];
		   //$response["success"] = 1;
		   echo json_encode($users);
		}
		
	}    
	
?>