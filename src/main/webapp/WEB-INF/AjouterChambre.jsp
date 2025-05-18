<%--
  Created by IntelliJ IDEA.
  User: Haran
  Date: 8/18/2024
  Time: 4:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter Chambre - Gestion de l'Auberge</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/tp5/css/logoTabBord.css">

</head>
<body>
<div class="container">
    <h1 class="text-center">Ajouter une Chambre</h1>
    <div class="col-md-6 offset-md-3">
        <form action="AjouterChambreServlet" method="POST">
            <div class="form-group">
                <label for="idChambre">ID Chambre</label>
                <input class="form-control" type="number" name="idChambre" required>
            </div>
            <div class="form-group">
                <label for="nom">Nom de la Chambre</label>
                <input class="form-control" type="text" name="nom" required>
            </div>
            <div class="form-group">
                <label for="typeLit">Type de Lit</label>
                <input class="form-control" type="text" name="typeLit" required>
            </div>
            <div class="form-group">
                <label for="prixBase">Prix de Base</label>
                <input class="form-control" type="number" step="0.01" name="prixBase" required>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <input class="btn btn-primary btn-block" type="submit" value="Ajouter Chambre">
                </div>
                <div class="col-md-6">
                    <a href="transaction?action=dashboard" class="btn btn-secondary btn-block">Annuler</a>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/MessageErreur.jsp"/>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

