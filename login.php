<?php

db = new PDO("mysql:host=localhoost","dbname=ProjetApplication","root","");
$results["error"] = false;
$results["message"]= [];

if(!empty($_POST)){

  if(!empty($_POST['pseudo']) && !empty($_POST['password'])) {
    $pseudo = $_POST['pseudo'];
    $password = $_POST['password'];

    $sql = $db->prepare("SELECT * FROM utilisateur WHERE pseudo = :pseudo");
    $sql->execute(":pseudo" => $pseudo);
    $row = $sql->fetch(PDO : FETCH_OBJ);

    if ($row) {
       if (password_verify($password, $row->password)) {
         $results['error'] = false;
         $results['id'] = $row->id;
         $results['pseudo'] = $row->pseudo
       }else {
         $results['error'] = true;
         $results['message'] = "Pseudo ou mot de passe incorrect";
       }
    }else {
      $results['error'] = true;
      $results['message'] = "Pseudo ou mot de passe incorrect";
    }

  }else {
    $results['error'] = true;
    $results['message'] = 'Veuillez remplir tous les champs';
  }

  echo json_encode($results);
}

?>
