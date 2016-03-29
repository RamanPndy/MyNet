<?php
	require('db_config.php');
	if( !(empty($_POST['broadcast_msg'])) && !(empty($_POST['broadcast_subject'])) )
	{
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
		$user_name=$_POST['user_name'];
		$broadcast_msg=$_POST['broadcast_msg'];
		$broadcast_subject = $_POST['broadcast_subject'];

		$result = mysqli_query($con,"INSERT INTO broadcast(username,message,subject) VALUES ('$user_name','$broadcast_msg','$broadcast_subject')") or die(mysqli_error($con)); 

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