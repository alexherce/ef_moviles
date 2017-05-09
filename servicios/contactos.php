<?php
include_once('config.php');
include_once('administradorBD.php');
date_default_timezone_set('America/Mexico_City');
$db = new administradorBD();
$response = array();

if($_SERVER['REQUEST_METHOD'] == 'POST'){
  $nombre = $_POST['nombre'];
  $telefono = $_POST['telefono'];
  $imagen = $_POST['imagen'];
  $latitud = $_POST['latitud'];
  $longitud = $_POST['longitud'];
  $id_empresa = $_POST['id_empresa'];

  if (!empty($nombre) && !empty($imagen) && !empty($latitud) && !empty($longitud) && !empty($id_empresa)) {
    $query = "INSERT INTO EF_Contacto (nombre, imagen, latitud, longitud, id_empresa) VALUES ('$nombre','$imagen','$latitud','$longitud','$id_empresa')";
    $qresult = $db->executeQuery($query);
    if($qresult){
      $response['code'] = "01";
      $response['message'] = "Contact added successfully";
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
  $id_contacto = $_GET['id'];

  if (isset($id_contacto)) {
    $query = "SELECT c.id, c.nombre, c.imagen, e.telefono, c.latitud, c.longitud, e.calle, e.ciudad, e.estado, e.codigopostal  FROM EF_Contacto c INNER JOIN EF_Empresas e ON c.id_empresa = e.id WHERE c.id = $id_contacto";
    $result = $db->executeQuery($query);
    $row = mysql_fetch_assoc($result);
    if($result) {
  		$response['code'] = "01";
  		$response['contact_directory'] = $row;
  	} else {
  		$response['code'] = "04";
  		$response['message'] = "Possible error executing the query.";
  	}

  } else {
    $query = "SELECT c.id, c.nombre, c.imagen, e.telefono, c.latitud, c.longitud, e.calle, e.ciudad, e.estado, e.codigopostal  FROM EF_Contacto c INNER JOIN EF_Empresas e ON c.id_empresa = e.id";
    $result = $db->executeQuery($query);

    $rows = array();
  	while($r = mysql_fetch_assoc($result)) {
      	$rows[] = $r;
  	}
    if($result) {
  		$response['code'] = "01";
  		$response['contact_directory'] = $rows;
  	} else {
  		$response['code'] = "04";
  		$response['message'] = "Possible error executing the query.";
  	}
  }
  echo json_encode($response);
}

if($_SERVER['REQUEST_METHOD'] == 'PUT'){

  parse_str(file_get_contents('php://input'), $_PUT);

  $id_contacto = $_PUT['id'];
  $nombre = $_PUT['nombre'];
  $imagen = $_PUT['imagen'];
  $latitud = $_PUT['latitud'];
  $longitud = $_PUT['longitud'];
  $id_empresa = $_PUT['id_empresa'];

  if (!empty($id_contacto) && !empty($nombre) && !empty($imagen) && !empty($latitud) && !empty($longitud)) {
    $query = "UPDATE EF_Contacto SET nombre = '$nombre', imagen = '$imagen', latitud = '$latitud', longitud = '$longitud' WHERE id = '$id_contacto'";
    $qresult = $db->executeQuery($query);
    if($qresult){
      $response['code'] = "01";
      $response['message'] = "Contact updated successfully";
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

	$id_contacto = $_DELETE['id'];

	if (isset($id_contacto)) {
		$query = "DELETE FROM EF_Contacto WHERE id = $id_contacto";
		$result = $db->executeQuery($query);

    if($result) {
  		$response['code'] = "01";
  		$response['message'] = "Contact deleted";
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
