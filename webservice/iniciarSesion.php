<?php

$mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

/* check connection */
if (mysqli_connect_errno()) {
	printf("Connect failed: %s\n", mysqli_connect_error());
	exit();
}

$sql = " SELECT id_usuario , nombre , correo, contrasena, foto FROM usuario ; ";
$datos = array();

if ($result = $mysqli->query($sql)) {
	while($row = $result->fetch_row()){
		$dato[] = $row;

	}
	$result->close();
}

echo json_encode($dato)
?>
