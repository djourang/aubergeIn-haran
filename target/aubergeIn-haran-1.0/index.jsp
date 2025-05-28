<%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 5/16/2025
  Time: 2:19 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Index - Système de Gestion de l'AubergeIn</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/logoTabBord.css">
</head>
<body>
<div class="container">
    <h1 class="text-center">Bienvenue au Système de Gestion de l'Auberge</h1>
    <div class="col-md-6 offset-md-3 mt-5">
        <h3 class="text-center">Veuillez choisir votre type de connexion :</h3>
        <div class="d-flex justify-content-around mt-4">
            <form action="transaction" method="POST">
                <input type="hidden" name="action" value="initEmploye">
                <input class="btn btn-primary" type="submit" value="Employé">
            </form>
            <form action="transaction" method="POST">
                <input type="hidden" name="action" value="initClient">
                <input class="btn btn-success" type="submit" value="Client">
            </form>
        </div>
    </div>
</div>

<!-- Inclusion des fichiers JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>
