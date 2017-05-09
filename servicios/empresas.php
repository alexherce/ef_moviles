<?php
include_once('config.php');
include_once('administradorBD.php');
date_default_timezone_set('America/Mexico_City');
$db = new administradorBD();
$response = array();

if($_SERVER['REQUEST_METHOD'] == 'POST'){
  $nombre = $_POST['nombre'];
  $slogan = $_POST['slogan'];
  $oficina = $_POST['oficina'];
  $telefono = $_POST['telefono'];
  $fax = $_POST['fax'];
  $calle = $_POST['calle'];
  $ciudad = $_POST['ciudad'];
  $estado = $_POST['estado'];
  $codigopostal = $_POST['codigopostal'];

  if (!empty($nombre) && !empty($slogan) && !empty($oficina) && !empty($telefono) && !empty($calle) && !empty($ciudad) && !empty($estado) && !empty($codigopostal)) {
    $query = "INSERT INTO EF_Empresas (nombre, slogan, oficina, telefono, fax, calle, ciudad, estado, codigopostal) VALUES ('$nombre','$slogan','$oficina','$telefono','$fax','$calle','$ciudad','$estado', '$codigopostal')";
    $qresult = $db->executeQuery($query);
    if($qresult) {
      $response['code'] = "01";
      $response['message'] = "Business added successfully";
    } else {
      $response['code'] = "04";
      $response['message'] = "Seems that there was an error!";
    }
  } else {
    $response['code'] = "02";
    $response['message'] = "Missing mandatory values";
  }
  echo json_encode($response);
}

if($_SERVER['REQUEST_METHOD'] == 'GET') {
  $id_empresa = $_GET['id'];

  if (isset($id_empresa)) {
    $query = "SELECT * FROM EF_Empresas WHERE id = '$id_empresa'";
    $result = $db->executeQuery($query);
  } else {
    $query = "SELECT * FROM EF_Empresas";
    $result = $db->executeQuery($query);
  }

  $rows = array();
	while($r = mysql_fetch_assoc($result)) {
    	$rows[] = $r;
	}

  if($result) {
		$response['code'] = "01";
		$response['business_directory'] = $rows;
	} else {
		$response['code'] = "04";
		$response['message'] = "Possible error executing the query.";
	}
  echo json_encode($response);
}

if($_SERVER['REQUEST_METHOD'] == 'PUT') {

  parse_str(file_get_contents('php://input'), $_PUT);

  $id_empresa = $_PUT['id'];
  $nombre = $_PUT['nombre'];
  $slogan = $_PUT['slogan'];
  $oficina = $_PUT['oficina'];
  $telefono = $_PUT['telefono'];
  $fax = $_PUT['fax'];
  $calle = $_PUT['calle'];
  $ciudad = $_PUT['ciudad'];
  $estado = $_PUT['estado'];
  $codigopostal = $_PUT['codigopostal'];

  if (!empty($id_empresa) && !empty($nombre) && !empty($slogan) && !empty($oficina) && !empty($telefono) && !empty($calle) && !empty($ciudad) && !empty($estado) && !empty($codigopostal)) {
    $query = "UPDATE EF_Empresas SET nombre = '$nombre', slogan = '$slogan', oficina = '$oficina', telefono = '$telefono', fax = '$fax', calle = '$calle', ciudad = '$ciudad', estado = '$estado', codigopostal = '$codigopostal' WHERE id = $id_empresa";
    $qresult = $db->executeQuery($query);
    if($qresult){
      $response['code'] = "01";
      $response['message'] = "Business updated successfully";
    } else {
      $response['code'] = "04";
      $response['message'] = "Seems that there was an error!";
    }
  } else {
    $response['code'] = "02";
    $response['message'] = "Missing mandatory values";
  }
  echo json_encode($response);
}

if($_SERVER['REQUEST_METHOD'] == 'DELETE') {

  parse_str(file_get_contents('php://input'), $_DELETE);

	$id_empresa = $_DELETE['id'];

	if (isset($id_empresa)) {
		$query = "DELETE FROM EF_Empresas WHERE id = $id_empresa";
		$result = $db->executeQuery($query);

    if($result) {
  		$response['code'] = "01";
  		$response['message'] = "Business deleted";
  	} else {
  		$response['code'] = "04";
  		$response['message'] = "Possible error executing the query.";
  	}
	} else {
    $response['code'] = "02";
    $response['message'] = "Missing mandatory values";
  }
	echo json_encode($response);
}
?>
