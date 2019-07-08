<?php
$nombre = $_REQUEST["nombre"];
$descripcion = $_REQUEST["descripcion"];
$foto = $_REQUEST["foto"];
$fecha_inicio = $_REQUEST["fecha_inicio"];
$fecha_termino = $_REQUEST["fecha_termino"];
$usuario_id_usuario = $_REQUEST["usuario_id_usuario"];


if($nombre != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO proyecto (`nombre`, `descripcion`, `foto`, `fecha_inicio`, `fecha_termino`, `usuario_id_usuario`) VALUES ('$nombre', '$descripcion', '$foto', '$fecha_inicio', '$fecha_termino', '$usuario_id_usuario'); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
