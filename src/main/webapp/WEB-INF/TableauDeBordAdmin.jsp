<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/18/2024
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Gestion de l'Auberge</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="/tp5/css/logoTabBord.css">

</head>
<body>


<nav class="navbar navbar-expand-lg navbar-light bg-light py-2 px-4 border-bottom">
    <div class="container-fluid">
        <!-- Logo / Home -->
        <a class="navbar-brand text-dark" href="#">
            <i class="fas fa-home fa-lg"></i>
        </a>
        <!-- Bouton hamburger -->
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarMain"
                aria-controls="navbarMain" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Contenu masqué sur petits écrans -->
        <div class="collapse navbar-collapse justify-content-between" id="navbarMain">
            <!-- Partie gauche -->
            <div class="d-flex flex-wrap align-items-center">
                <span class="mx-3 text-dark">
                    <i class="fas fa-phone-alt"></i> 1 819 943 8870
                </span>
                <span class="mx-3 text-dark">
                    <i class="fas fa-comments"></i> Chat with us
                </span>

                <div class="dropdown mx-3">
                    <a class="nav-link dropdown-toggle text-dark p-0" href="#" id="langDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-globe"></i> English
                    </a>
                    <div class="dropdown-menu" aria-labelledby="langDropdown">
                        <a class="dropdown-item" href="#">English</a>
                        <a class="dropdown-item" href="#">Français</a>
                    </div>
                </div>
            </div>

            <!-- Partie droite -->
            <div class="d-flex flex-wrap align-items-center">
                <span class="mx-3 text-dark">
                    <i class="fas fa-bed"></i> My stays
                </span>
                <span class="mx-3 text-dark">
                    <i class="fas fa-user-plus"></i> Join for free
                </span>

                <!-- Bloc connexion remplaçant "Sign in" -->
                <div class="dropdown mx-3">
                    <button class="btn btn-success dropdown-toggle" type="button" id="connectionStatus"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-check-circle"></i> Connecté
                    </button>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="connectionStatus">
                        <form action="logout.jsp" method="post">
                            <button class="btn btn-danger btn-block mb-2" type="submit">Déconnexion</button>
                        </form>
                        <h6 class="dropdown-header">Informations de Connexion</h6>
                        <p class="dropdown-item-text"><strong>État :</strong> Connecté</p>
                        <p class="dropdown-item-text"><strong>Type de BD :</strong> PostGre render</p>
                        <p class="dropdown-item-text"><strong>Nom de la BD :</strong> <%= session.getAttribute("bd") %></p>
                        <p class="dropdown-item-text"><strong>Utilisateur :</strong> <%= session.getAttribute("serveur") %></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <div class="alert alert-info" role="alert">
        Bonjour, Vous êtes connecté à <strong>Postgre de render</strong> (<%= (String) session.getAttribute("bd") %>).
    </div>
</div>
<div class="container mt-4">
    <div class="row">
        <div class="col-md-4">
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Ajouter Client" onclick="form.action='transaction?action=ajouterClient';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Supprimer Client" onclick="form.action='transaction?action=supprimerClient';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Ajouter Chambre" onclick="form.action='transaction?action=ajouterChambre';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Supprimer Chambre" onclick="form.action='transaction?action=supprimerChambre';">
            </form>
        </div>
        <div class="col-md-4">
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Ajouter Commodité" onclick="form.action='transaction?action=ajouterCommodite';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Inclure Commodité" onclick="form.action='transaction?action=inclureCommodite';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Enlever Commodité" onclick="form.action='transaction?action=enleverCommodite';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Réserver" onclick="form.action='transaction?action=reserver';">
            </form>
        </div>
        <div class="col-md-4">
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Afficher Chambre" onclick="form.action='transaction?action=afficherChambre';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Afficher Chambres Libres" onclick="form.action='transaction?action=afficherChambresLibres';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-primary btn-block mb-3" type="submit" value="Afficher Client" onclick="form.action='transaction?action=afficherClient';">
            </form>
            <form action="transaction" method="POST">
                <input class="btn btn-danger btn-block mb-3" type="submit" value="Quitter" onclick="form.action='transaction?action=quitter';">
            </form>
        </div>
    </div>
</div>
<div class="container mt-4">
    <div class="container mt-4">
        <%
            String messageSucces = (String) session.getAttribute("messageSucces");
            if (messageSucces != null) {
        %>
        <div class="alert alert-success" role="alert">
            <%= messageSucces %>
        </div>
        <%
                session.removeAttribute("messageSucces");
            }
        %>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
