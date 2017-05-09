<?php
include_once('config.php');
include_once('administradorBD.php');
date_default_timezone_set('America/Mexico_City');
$db = new administradorBD();
$response = array();

if($_SERVER['REQUEST_METHOD'] == 'GET'){
	$username = $_GET['usuario'];
	$pass = $_GET['password'];

	if (!empty($username) && !empty($pass)) {
		$query = "SELECT id, nombre, appaterno, apmaterno FROM EF_Usuarios WHERE usuario = '$username' AND password = '$pass'";
		$qresult = $db->executeQuery($query);
    if(mysql_num_rows($qresult) == 1) {
      $row = mysql_fetch_assoc($qresult);
  		$response['code'] = "01";
  		$response['message'] = "Bienvenido ";
      $response['user_data'] = $row;
  	} else if(mysql_num_rows($qresult) == 0) {
  		$response['code'] = "03";
  		$response['message'] = "Usuario o contraseña incorrectos";
  	} else {
      $response['code'] = "04";
  		$response['message'] = "Usuario no existe, registrate.";
    }
	} else {
    $response['code'] = "02";
    $response['message'] = "Missing mandatory parameters";
  }
	echo json_encode($response);
}

if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$nombre = $_POST['nombre'];
	$appaterno = $_POST['appaterno'];
  $apmaterno = $_POST['apmaterno'];
  $usuario = $_POST['usuario'];
  $password = $_POST['password'];
  $tipo = 2;
	if (!empty($nombre) && !empty($appaterno) && !empty($apmaterno) && !empty($usuario) && !empty($password)) {
    $verifyQuery = "SELECT id FROM EF_Usuarios WHERE usuario = '$usuario'";
    $resultVerify = $db->executeQuery($verifyQuery);
    if(mysql_num_rows($resultVerify) > 0) {
      $response['code'] = "03";
  		$response['message'] = "Usuario ya esta registrado";
		} else {
      $query = "INSERT INTO EF_Usuarios (nombre, appaterno, apmaterno, usuario, password, tipo) VALUES ('$nombre','$appaterno','$apmaterno','$usuario','$password','$tipo')";
  		$qresult = $db->executeQuery($query);
      if($qresult){
    		$response['code'] = "01";
    		$response['message'] = "Usuario creado";
    	} else {
    		$response['code'] = "04";
    		$response['message'] = "Seems that there was an error!";
    	}
    }
	} else {
    $response['code'] = "02";
    $response['message'] = "Missing mandatory parameters";
  }
	echo json_encode($response);
}
?>
