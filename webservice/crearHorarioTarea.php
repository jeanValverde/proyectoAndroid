<?php
$id_horario = $_REQUEST["id_horario"];
$id_tarea = $_REQUEST["id_tarea"];


if($id_horario != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO tareas_horario (`id_horario`, `id_tarea`) VALUES ('$id_horario', '$id_tarea'); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
