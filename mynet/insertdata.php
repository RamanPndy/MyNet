<?php
	require('db_config.php');
	if( !(empty($_POST['user_name'])) && !(empty($_POST['name'])) && !(empty($_POST['email'])) && !(empty($_POST['password'])))
	{
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
		$user_name=$_POST['user_name'];
		$name=$_POST['name'];
		$email=$_POST['email'];
		$password = $_POST['password'];

		$result = mysqli_query($con,"INSERT INTO mynet(username,Name,Email,Password) VALUES ('$user_name','$name','$email','$password')") or die(mysqli_error($con)); 

		if($result>0){
			   $response["success"] = 1;
			   echo json_encode($response);
			 }    
		 else{
			   $response["success"] = 0;
			 }
		 // echoing JSON response
		 
	}
?>