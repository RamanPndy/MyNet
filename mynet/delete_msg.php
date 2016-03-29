<?php
	require('db_config.php');
	
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
	$admin_name = $_POST['admin_name'];
	$msg_subject = $_POST['msg_subject'];
	
	//echo $admin_name;
	$result = mysqli_query($con,"DELETE FROM broadcast WHERE subject='$msg_subject' AND username='$admin_name'") or die(mysqli_error($con)); 
	
	if (mysqli_num_rows($result) > 0) 
	{
		echo "succecss";
		
	}    
	
?>