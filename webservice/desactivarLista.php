<?php
$id_listas = $_REQUEST["id_listas"];


if($id_listas != "" ){

   $mysqli = new mysqli("localhost", "root", "root", "ordanizadordb");

	  $sql = " UPDATE ordanizadordb.listas
SET estado='desactivado'
WHERE id_listas =  $id_listas; ";

		$result = $mysqli->query($sql);

	}else{
		echo "-1";
	}
	?>
