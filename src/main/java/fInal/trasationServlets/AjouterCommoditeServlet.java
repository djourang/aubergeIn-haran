package com.aubergeServlet.fInal.trasationServlets;

import AubergeInn.gestionnaires.GestionObergeInn;
import AubergeInn.utils.IFT287Exception;
import com.aubergeServlet.fInal.AubergeHelper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AjouterCommoditeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> listeMessageErreur = new LinkedList<>();
        try {
            // Récupération de l'instance GestionObergeInn de la session
            HttpSession session = request.getSession();
            GestionObergeInn gestionObergeInn = AubergeHelper.getaubergeUpdate(session);

            // Lecture des paramètres du formulaire
            String idCommoditeStr = request.getParameter("idCommodite");
            String description = request.getParameter("description");
            String surplusPrixStr = request.getParameter("surplusPrix");

            // Validation et exécution de la transaction
            if (idCommoditeStr != null && description != null && surplusPrixStr != null) {
                int idCommodite = Integer.parseInt(idCommoditeStr);
                double surplusPrix = Double.parseDouble(surplusPrixStr);
                gestionObergeInn.getGestionCommodite().ajouterCommodite(idCommodite, description, surplusPrix);

                // Ajouter un message de succès à la session
                session.setAttribute("messageSucces", "La commodité a été ajoutée avec succès.");

                // Rediriger vers le tableau de bord
                response.sendRedirect(request.getContextPath() + "/transaction?action=dashboard");
            } else {
                throw new IFT287Exception("Tous les champs sont requis.");
            }
        } catch (IFT287Exception e) {
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AjouterCommodite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            listeMessageErreur.add("Erreur inattendue : " + e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AjouterCommodite.jsp");
            dispatcher.forward(request, response);
        }
    }
}
