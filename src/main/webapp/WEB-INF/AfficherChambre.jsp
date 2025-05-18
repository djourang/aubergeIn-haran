<%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/18/2024
  Time: 10:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="AubergeInn.tuples.Chambre" %>
<%@ page import="java.util.List" %>
<%@ page import="AubergeInn.tuples.Commodite" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Afficher Chambre - Gestion de l'Auberge</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/tp5/css/logoTabBord.css">

</head>
<body>
<div class="container">
    <h1 class="text-center">Afficher la Chambre</h1>
    <div class="col-md-6 offset-md-3">
        <form action="AfficherChambreServlet" method="POST">
            <div class="form-group">
                <label for="idChambre">ID Chambre</label>
                <input class="form-control" type="number" name="idChambre" required>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <input class="btn btn-primary btn-block" type="submit" value="Afficher Chambre">
                </div>
                <div class="col-md-6">
                    <a href="transaction?action=dashboard" class="btn btn-secondary btn-block">Annuler</a>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="container mt-4">
    <%
        String messageSucces = (String) session.getAttribute("messageSucces");
        String messageErreur = (String) session.getAttribute("messageErreur");
        Chambre chambre = (Chambre) session.getAttribute("chambre");
        session.removeAttribute("messageSucces");
        session.removeAttribute("messageErreur");
        session.removeAttribute("chambre");

        if (messageSucces != null) {
    %>
    <div class="alert alert-success" role="alert">
        <%= messageSucces %>
    </div>
    <%
    } else if (messageErreur != null) {
    %>
    <div class="alert alert-warning" role="alert">
        <%= messageErreur %>
    </div>
    <%
        }

        if (chambre != null) {
    %>
    <h2 class="text-center">Détails de la Chambre</h2>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID Chambre</th>
            <th>Nom</th>
            <th>Type de Lit</th>
            <th>Prix de Base</th>
            <th>Commodités</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><%= chambre.getM_idChambre() %></td>
            <td><%= chambre.getM_nomChambre() %></td>
            <td><%= chambre.getM_typeLit() %></td>
            <td>$CAD <%= chambre.getM_prixBase() %></td>
            <td>
                <ul>
                    <%
                        for (Commodite commodite : chambre.getM_commodites()) {
                    %>
                    <li><%= commodite.getNom() %> ($CAD <%= commodite.getsurplusPrix() %>)</li>
                    <% } %>
                </ul>
            </td>
        </tr>
        </tbody>
    </table>
    <%
        }
    %>
</div>

<jsp:include page="/WEB-INF/MessageErreur.jsp"/>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
