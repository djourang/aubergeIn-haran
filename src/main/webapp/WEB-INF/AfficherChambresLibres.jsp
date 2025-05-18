<%@ page import="AubergeInn.tuples.Chambre" %>
<%@ page import="java.util.List" %>
<%@ page import="AubergeInn.tuples.Commodite" %><%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/16/2024
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
    <title>Afficher Chambres Libres - Gestion de l'Auberge</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Réduire la hauteur des lignes */
        .table td, .table th {
            padding: 0.25rem; /* Réduit l'espacement vertical */
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center">Afficher les Chambres Libres</h1>
    <div class="col-md-6 offset-md-3">
        <form action="AfficherChambresLibresServlet" method="POST">
            <div class="form-group">
                <label for="dateDebut">Date Début</label>
                <input class="form-control" type="date" name="dateDebut" required>
            </div>
            <div class="form-group">
                <label for="dateFin">Date Fin</label>
                <input class="form-control" type="date" name="dateFin" required>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <input class="btn btn-primary btn-block" type="submit" value="Afficher Chambres Libres">
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
        List<Chambre> chambresLibres = (List<Chambre>) request.getAttribute("chambresLibres");
        if (chambresLibres != null && !chambresLibres.isEmpty()) {
    %>
    <h2 class="text-center">Chambres Libres</h2>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID Chambre</th>
            <th>Nom</th>
            <th>Type de Lit</th>
            <th>Prix de Base</th>
            <th>Commodités</th>
            <th>Prix Total</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Chambre chambre : chambresLibres) {
                double prixTotal = chambre.getM_prixBase();
                for (Commodite commodite : chambre.getM_commodites()) {
                    prixTotal += commodite.getsurplusPrix();
                }
        %>
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
            <td>$CAD <%= prixTotal %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else if (chambresLibres != null) {
    %>
    <div class="alert alert-warning" role="alert">
        Aucune chambre n'est libre pour les dates sélectionnées.
    </div>
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

