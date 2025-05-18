<%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/18/2024
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="AubergeInn.tuples.Client" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Afficher Client - Gestion de l'Auberge</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link rel="stylesheet" href="/tp5/css/logoTabBord.css">
  </style>
</head>
<body>
<div class="container">
  <h1 class="text-center">Afficher le Client</h1>
  <div class="col-md-6 offset-md-3">
    <form action="AfficherClientServlet" method="POST">
      <div class="form-group">
        <label for="idClient">ID Client</label>
        <input class="form-control" type="number" name="idClient" required>
      </div>
      <div class="row">
        <div class="col-md-6">
          <input class="btn btn-primary btn-block" type="submit" value="Afficher Client">
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
    Client client = (Client) session.getAttribute("client");
    session.removeAttribute("messageSucces");
    session.removeAttribute("messageErreur");
    session.removeAttribute("client");

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

    if (client != null) {
  %>
  <h2 class="text-center">Détails du Client</h2>
  <table class="table table-bordered table-hover">
    <thead>
    <tr>
      <th>ID Client</th>
      <th>Prénom</th>
      <th>Nom</th>
      <th>Âge</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td><%= client.getM_idClient() %></td>
      <td><%= client.getM_prenom() %></td>
      <td><%= client.getM_nom() %></td>
      <td><%= client.getM_age() %></td>
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
