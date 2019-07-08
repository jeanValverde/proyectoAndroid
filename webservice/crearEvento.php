<?php
$nombre = $_REQUEST["nombre"];
$descripcion = $_REQUEST["descripcion"];
$fecha_evento = $_REQUEST["fecha_evento"];
$usuario_id_usuario = $_REQUEST["usuario_id_usuario"];
$tipoEvento = $_REQUEST["tipoEvento"];

if($nombre != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO eventos (`nombre`, `descripcion`, `fecha_evento`, `usuario_id_usuario`, `tipoEvento`) VALUES ( '$nombre', '$descripcion', '$fecha_evento', '$usuario_id_usuario', '$tipoEvento'); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
