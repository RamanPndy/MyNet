<?php
	require('db_config.php');
	if( !(empty($_POST['user_name'])))
	{
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
		$user_name=$_POST['user_name'];
		
		//echo $user_name;
		$result = mysqli_query($con,"SELECT * FROM mynet WHERE username = '$user_name'") or die(mysqli_error($con)); 
		
		if (mysqli_num_rows($result) > 0) 
		{
  
			   $response["success"] = 1;
			   echo json_encode($response);
		}    
		 else
		{
			   $response["success"] = 0;
		}
	}
?>