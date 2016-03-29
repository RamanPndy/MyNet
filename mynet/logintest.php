<?php
	require('db_config.php');
	if( !(empty($_POST['user_name'])) && !(empty($_POST['pass_word'])))
	{
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
		$user_name = $_POST['user_name'];
		$pass_word = $_POST['pass_word'];
		
		//echo $user_name;
		$result = mysqli_query($con,"SELECT * FROM mynet WHERE username = '$user_name' and password = '$pass_word'") or die(mysqli_error($con)); 
		
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