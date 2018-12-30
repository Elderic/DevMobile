<?php
db = new PDO("mysql:host=localhoost","dbname=ProjetApplication","root","");
$results["error"] = false;
$results["message"]= [];

if(isset($_POST)){

  if(!empty($_POST['pseudo']) && !empty($_POST['email']) && !empty($_POST['password']) !empty($_POST['password2'])) {

    $pseudo = $_POST['pseudo'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $password2 = $_POST['password2'];

    //vérification du pseudo
    if(strlen($pseudo)<2 || !preg_match("/^[a-zA-Z0-9_-]+$/", $pseudo) || strlen($pseudo)>50){
      $results['error']= true;
      $results['message']['pseudo']= "Pseudo invalide";
    }else {
      //vérifier que le pseudo n'existe pas
      $requete= $db->prepare("SELECT id FROM utilisateur WHERE pseudo= :pseudo");
      $requete->execute([':pseudo' => $pseudo]);

      $row = $requete->fetch();
      if ($row) {
        $results['error']= true;
        $results['message']['pseudo']= "Le pseudo existe déja";
      }

    }

    //vérification email
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
      $results['error']= true;
      $results['message']['email']= "Email invalide";
    }else {
      //vérifier que l'email n'existe pas
      $requete= $db->prepare("SELECT id FROM utilisateur WHERE email= :email");
      $requete->execute([':email' => $email]);

      $row = $requete->fetch();
      if ($row) {
        $results['error']= true;
        $results['message']['email']= "L'email existe déja";
      }
    }

    //vérification des passwords
    if ($password !== $password2) {
      $results['error']= true;
      $results['message']['password']= "Les mots de passes doivent être identiques";
    }

    if ($results['error'] == false) {

      $password = password_hash($password, PASSWORD_BCRYPT);

      //insertion
      $sql = $db->prepare("INSERT INTO utilisateur(pseudo, email, password) VALUES (:pseudo, :email, :password)");
      $sql->execute([':pseudo' => $pseudo, ':email' => $email, ':password' => $password]);

      if (!$sql) {
        $results['error']= true;
        $results['message']= "Erreur lors de l'inscription";
      }
    }

  }else {
    $results["error"]= true;
    $results["message"] = "Veuillez remplir tous les champs";
  }

  echo json_encode($results);
}

?>
