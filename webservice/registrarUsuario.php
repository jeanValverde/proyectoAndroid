<?php
$nombre = $_REQUEST["nombre"];
$correo = $_REQUEST["correo"];
$contrasena = $_REQUEST["contrasena"];
$foto = $_REQUEST["foto"];

if($nombre != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO usuario (`nombre`, `correo`, `contrasena`, `foto`) VALUES ('$nombre', '$correo', '$contrasena', '$foto'); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
