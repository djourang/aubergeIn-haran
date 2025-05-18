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

public class AjouterClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> listeMessageErreur = new LinkedList<>();
        try {
            // Récupération de l'instance GestionObergeInn de la session
            HttpSession session = request.getSession();
            GestionObergeInn gestionObergeInn = AubergeHelper.getaubergeUpdate(session);

            // Lecture des paramètres du formulaire
            String idClientStr = request.getParameter("idClient");
            String prenom = request.getParameter("prenom");
            String nom = request.getParameter("nom");
            String ageStr = request.getParameter("age");

            // Validation et exécution de la transaction
            if (idClientStr != null && prenom != null && nom != null && ageStr != null) {
                int idClient = Integer.parseInt(idClientStr);
                int age = Integer.parseInt(ageStr);
                gestionObergeInn.getGestionClient().ajouterClient(idClient, prenom, nom, age);

                // Ajouter un message de succès à la session
                session.setAttribute("messageSucces", "Le client a été ajouté avec succès.");

                // Rediriger vers le tableau de bord
                response.sendRedirect(request.getContextPath() + "/transaction?action=dashboard");
            } else {
                throw new IFT287Exception("Tous les champs sont requis.");
            }
        } catch (IFT287Exception e) {
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AjouterClient.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            listeMessageErreur.add("Erreur inattendue : " + e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AjouterClient.jsp");
            dispatcher.forward(request, response);
        }
    }
}
