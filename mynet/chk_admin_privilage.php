<?php
	require('db_config.php');
	if( !(empty($_POST['user_name'])))
	{
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
		$user_name = $_POST['user_name'];
		//$pass_word = $_POST['pass_word'];
		
		//echo $user_name;
		$result = mysqli_query($con,"SELECT admin FROM mynet WHERE username = '$user_name'") or die(mysqli_error($con)); 
		
		if (mysqli_num_rows($result) > 0) 
		{
				while($row = mysqli_fetch_array($result))
			{
				echo $row['admin'];
			}
		}    
		
		
	}
?>