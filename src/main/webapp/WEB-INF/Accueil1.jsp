<%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/16/2024
  Time: 4:51 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.aubergeServlet.fInal.AubergeHelper, AubergeInn.gestionnaires.GestionObergeInn, AubergeInn.tuples.Client, AubergeInn.tuples.Reservation" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - Système de gestion de bibliothèque</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Auberge</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="Logout">Déconnexion</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container mt-4">
    <h1 class="text-center">Bienvenue, <%= session.getAttribute("userID") %>!</h1>

    <div class="alert alert-info" role="alert">
        Vous êtes connecté à la base de données <strong><%= session.getAttribute("bd") %></strong> sur le serveur <strong><%= session.getAttribute("serveur") %></strong>.
    </div>

    <div class="row">
        <div class="col-md-6">
            <h3>Actions Disponibles</h3>
            <ul class="list-group">
                <c:if test="${sessionScope.admin}">
                    <li class="list-group-item"><a href="GestionClients">Gérer les Clients</a></li>
                    <li class="list-group-item"><a href="GestionChambres">Gérer les Chambres</a></li>
                    <li class="list-group-item"><a href="GestionCommodites">Gérer les Commodités</a></li>
                </c:if>
                <li class="list-group-item"><a href="Reserver">Réserver une Chambre</a></li>
                <li class="list-group-item"><a href="VoirReservations">Voir Mes Réservations</a></li>
            </ul>
        </div>
        <div class="col-md-6">
            <h3>Informations du Compte</h3>
            <ul class="list-group">
                <li class="list-group-item"><strong>Nom d'utilisateur :</strong> <%= session.getAttribute("userID") %></li>
                <li class="list-group-item"><strong>Base de données :</strong> <%= session.getAttribute("bd") %></li>
                <li class="list-group-item"><strong>Serveur :</strong> <%= session.getAttribute("serveur") %></li>
                <li class="list-group-item"><strong>Statut :</strong> <c:choose>
                    <c:when test="${sessionScope.admin}">
                        Administrateur
                    </c:when>
                    <c:otherwise>
                        Utilisateur
                    </c:otherwise>
                </c:choose></li>
            </ul>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script
        src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
