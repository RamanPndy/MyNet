<?php
	require('db_config.php');
	if( !(empty($_POST['admin_name'])) )
	{
		$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
		//$user_name = $_POST['user_name'];
		$admin_name = $_POST['admin_name'];
		//echo $user_name;
		$result = mysqli_query($con,"SELECT COUNT(request_follow) FROM mynetsocial WHERE username='$admin_name'") or die(mysqli_error($con)); 

		if (mysqli_num_rows($result) > 0) 
		{
			while($row = mysqli_fetch_array($result))
			{
				echo $row['COUNT(request_follow)'];
			}

			
		}    
		 
	}
?>