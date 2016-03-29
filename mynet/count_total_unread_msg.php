<?php
	require('db_config.php');
	if( !(empty($_POST['user_name'])) )
	{
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
		//$user_name = $_POST['user_name'];
		$user_name = $_POST['user_name'];
		//echo $user_name;
		$result = mysqli_query($con,"SELECT COUNT(read_flag) FROM broadcast WHERE read_flag = '0'") or die(mysqli_error($con)); 

		if (mysqli_num_rows($result) > 0) 
		{
			while($row = mysqli_fetch_array($result))
			{
				echo $row['COUNT(read_flag)'];
			}

			
		}    
		 
	}
?>