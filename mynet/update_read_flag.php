<?php
	require('db_config.php');
	if( !(empty($_POST['broadcast_subject'])) )
	{
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
		//$user_name = $_POST['user_name'];
		$broadcast_subject = $_POST['broadcast_subject'];
		//echo $user_name;
		$result = mysqli_query($con,"UPDATE broadcast SET read_flag='1' WHERE subject='$broadcast_subject'") or die(mysqli_error($con)); 

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
		 
	}
?>