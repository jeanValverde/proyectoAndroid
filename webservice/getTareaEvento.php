<?php

$mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

/* check connection */
if (mysqli_connect_errno()) {
	printf("Connect failed: %s\n", mysqli_connect_error());
	exit();
}

$sql = " SELECT idtarea_evento, eventos_id_eventos, id_tarea FROM tarea_evento ";
$datos = array();

if ($result = $mysqli->query($sql)) {
	while($row = $result->fetch_row()){
		$dato[] = $row;

	}
	$result->close();
}

echo json_encode($dato)
?>
