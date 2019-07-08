<?php
$descripcion = $_REQUEST["descripcion"];
$estado = $_REQUEST["estado"];
$id_tarea = $_REQUEST["id_tarea"];

if($descripcion != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO listas (`descripcion`, `estado`, `id_tarea`) VALUES ('$descripcion', '$estado', '$id_tarea'); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
