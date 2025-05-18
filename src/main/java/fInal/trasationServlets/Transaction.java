package com.aubergeServlet.fInal.trasationServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Transaction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String page = null;

        // rediriction vers la page correspondante
        if ("ajouterClient".equals(action)) {
            page = "/WEB-INF/AjouterClient.jsp";
        } else if ("supprimerClient".equals(action)) {
            page = "/WEB-INF/SupprimerClient.jsp";
        } else if ("ajouterChambre".equals(action)) {
            page = "/WEB-INF/AjouterChambre.jsp";
        } else if ("supprimerChambre".equals(action)) {
            page = "/WEB-INF/SupprimerChambre.jsp";
        } else if ("ajouterCommodite".equals(action)) {
            page = "/WEB-INF/AjouterCommodite.jsp";
        } else if ("supprimerCommodite".equals(action)) {
            page = "/WEB-INF/supprimerCommodite.jsp";
        } else if ("inclureCommodite".equals(action)) {
            page = "/WEB-INF/InclureCommodite.jsp";
        } else if ("enleverCommodite".equals(action)) {
            page = "/WEB-INF/EnleverCommodite.jsp";
        } else if ("afficherChambresLibres".equals(action)) {
            page = "/WEB-INF/AfficherChambresLibres.jsp";
        } else if ("afficherClient".equals(action)) {
            page = "/WEB-INF/AfficherClient.jsp";
        } else if ("afficherChambre".equals(action)) {
            page = "/WEB-INF/AfficherChambre.jsp";
        } else if ("reserver".equals(action)) {
            page = "/WEB-INF/Reserver.jsp";
        } else if ("enleverReservation".equals(action)) {
            page = "/WEB-INF/enleverReservation.jsp";
        } else if ("quitter".equals(action)) {
            page = "/WEB-INF/quitter.jsp";
        } else if ("dashboard".equals(action)) {
            page = "/WEB-INF/TableauDeBordAdmin.jsp";
        }else if ("Login".equals(action)) {
            page = "/WEB-INF/Login.jsp";
        }else if ("loginClient".equals(action)) {
            page = "/WEB-INF/loginClient.jsp";
        } else if ("TableauDeBordClient".equals(action)) {
            page = "/WEB-INF/TableauDeBordClient.jsp";
        } else {
            page = "/WEB-INF/MessageErreur.jsp";
        }

        if (page != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "La page demandée est introuvable.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
