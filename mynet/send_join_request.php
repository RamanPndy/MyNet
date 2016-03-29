<?php
	require('db_config.php');
	
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
	$admin_name = $_POST['admin_name'];
	$follower_name = $_POST['follower_name'];
	$result = mysqli_query($con,"INSERT INTO mynetsocial(username,following_request,request_follow) VALUES('$admin_name','$follower_name','1')") or die(mysqli_error($con)); 
	
	if (mysqli_num_rows($result) > 0) 
	{
		$response["success"] = 1;
		echo json_encode($response);
		
	}    
	
?>