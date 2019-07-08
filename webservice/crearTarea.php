<?php
$id_tarea = $_REQUEST["id_tarea"];
$nombre = $_REQUEST["nombre"];
$descripcion = $_REQUEST["descripcion"];
$hora_inicio = $_REQUEST["hora_inicio"];
$hora_termino = $_REQUEST["hora_termino"];
$estado_tarea = $_REQUEST["estado_tarea"];
$monitor = $_REQUEST["monitor"];
$sub_tipo = $_REQUEST["sub_tipo"];
$seccion = $_REQUEST["seccion"];
$sala = $_REQUEST["sala"];
$tipo_tarea = $_REQUEST["tipo_tarea"];


if($id_tarea != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO tarea (`id_tarea`, `nombre`, `descripcion`, `hora_inicio`, `hora_termino`, `tipo_tarea`, `estado_tarea`, `monitor`, `sub_tipo`, `seccion`, `sala`)
    VALUES ('$id_tarea', '$nombre', '$descripcion', '$hora_inicio', '$hora_termino', '$tipo_tarea', '$estado_tarea', '$monitor', '$sub_tipo', '$seccion', '$sala'); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
