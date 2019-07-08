<?php
$id_proyecto = $_REQUEST["id_proyecto"];
$id_tarea = $_REQUEST["id_tarea"];


if($id_proyecto != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO tarea_proyecto (`id_proyecto`, `id_tarea`) VALUES ('$id_proyecto', '$id_tarea'); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
