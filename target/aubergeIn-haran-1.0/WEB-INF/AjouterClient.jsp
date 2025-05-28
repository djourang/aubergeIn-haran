<%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/16/2024
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.*,java.text.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <title>Ajouter Client - Système de gestion de l'auberge</title>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="/tp5/css/logoTabBord.css">

</head>
<body>
<div class="container">
    <h1 class="text-center">Ajouter un Client</h1>
    <div class="col-md-4 offset-md-4">
        <form action="AjouterClient" method="POST">
            <div class="form-group">
                <label for="idClient">ID Client</label>
                <input class="form-control" type="number" name="idClient" value="<%= request.getAttribute("idClient") != null ? request.getAttribute("idClient") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="prenom">Prénom</label>
                <input class="form-control" type="text" name="prenom" value="<%= request.getAttribute("prenom") != null ? request.getAttribute("prenom") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="nom">Nom</label>
                <input class="form-control" type="text" name="nom" value="<%= request.getAttribute("nom") != null ? request.getAttribute("nom") : "" %>" required>
            </div>
            <div class="form-group">
                <label for="age">Âge</label>
                <input class="form-control" type="number" name="age" value="<%= request.getAttribute("age") != null ? request.getAttribute("age") : "" %>" required>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <input class="btn btn-primary btn-block" type="submit" value="Ajouter Client">
                </div>
                <div class="col-md-6">
                    <a href="TableauDeBordAdmin.jsp" class="btn btn-secondary btn-block">Annuler</a>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/MessageErreur.jsp"/>
</body>
</html>
