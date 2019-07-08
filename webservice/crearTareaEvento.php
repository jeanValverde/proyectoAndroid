<?php
$eventos_id_eventos = $_REQUEST["eventos_id_eventos"];
$id_tarea = $_REQUEST["id_tarea"];


if($eventos_id_eventos != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO tarea_evento (`eventos_id_eventos`, `id_tarea`) VALUES ('$eventos_id_eventos', '$id_tarea'); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
