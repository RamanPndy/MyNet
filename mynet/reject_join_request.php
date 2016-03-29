<?php
	require('db_config.php');
	
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
	$admin_name = $_POST['admin_name'];
	$follower_name = $_POST['follower_name'];
	//echo $admin_name;
	$result = mysqli_query($con,"UPDATE mynetsocial SET following_request='none' , request_follow='3' WHERE username='$admin_name' AND following_request='$follower_name'") or die(mysqli_error($con)); 
	
	if (mysqli_num_rows($result) > 0) 
	{
		echo "succecss";
		
	}    
	
?>