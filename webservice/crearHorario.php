<?php
$dia = $_REQUEST["dia"];
$usuario_id_usuario = $_REQUEST["idUsuario"];

if($dia != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " INSERT INTO horario (`dia`, `usuario_id_usuario`) VALUES ('$dia', '$usuario_id_usuario' ); ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
