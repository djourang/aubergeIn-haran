<%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/18/2024
  Time: 1:15 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <title>Supprimer Client - Système de gestion de l'auberge</title>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="text-center">Supprimer un Client</h1>
    <div class="col-md-4 offset-md-4">
        <form action="SupprimerClient" method="POST">
            <div class="form-group">
                <label for="idClient">ID Client</label>
                <input class="form-control" type="number" name="idClient" value="<%= request.getAttribute("idClient") != null ? request.getAttribute("idClient") : "" %>" required>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <input class="btn btn-danger btn-block" type="submit" value="Supprimer Client">
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
