<%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/17/2024
  Time: 3:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil Client - Gestion de l'Auberge</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="/tp5/css/logoTabBord.css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">
        <i class="fas fa-home"></i>
    </a>
    <div class="collapse navbar-collapse justify-content-center">
        <ul class="navbar-nav">
            <li class="nav-item">
                <form action="transaction" method="POST">
                    <input class="btn btn-outline-light nav-link" type="submit" value="Accueil" onclick="form.action='transaction?action=accueil';">
                </form>
            </li>
            <li class="nav-item">
                <form action="transaction" method="POST">
                    <input class="btn btn-outline-light nav-link" type="submit" value="Découvrir Auberge" onclick="form.action='transaction?action=decouvrirAuberge';">
                </form>
            </li>
            <li class="nav-item">
                <form action="transaction" method="POST">
                    <input class="btn btn-outline-light nav-link" type="submit" value="Consulter Chambres" onclick="form.action='transaction?action=consulterChambres';">
                </form>
            </li>
        </ul>
    </div>
    <div class="navbar-text text-white">
        <form action="LoginClient" method="POST">
            <button class="btn btn-success" type="submit">Connexion Client</button>
        </form>
    </div>
</nav>

<div class="container mt-4">
    <div class="alert alert-info" role="alert">
        Bienvenue à l'Auberge. Vous pouvez découvrir nos services et consulter les chambres disponibles.
    </div>
</div>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-4">
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Découvrir Auberge" onclick="form.action='transaction?action=decouvrirAuberge';">
            </form>
        </div>
        <div class="col-md-4">
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Consulter Chambres" onclick="form.action='transaction?action=consulterChambres';">
            </form>
        </div>
        <div class="col-md-4">
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Réserver" onclick="form.action='transaction?action=reserver';">
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
