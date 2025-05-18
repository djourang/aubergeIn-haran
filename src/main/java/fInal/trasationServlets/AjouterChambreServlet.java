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

public class AjouterChambreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> listeMessageErreur = new LinkedList<>();
        try {
            HttpSession session = request.getSession();
            GestionObergeInn gestionObergeInn = AubergeHelper.getaubergeUpdate(session);

            // Lecture des paramètres du formulaire
            String idChambreStr = request.getParameter("idChambre");
            String nom = request.getParameter("nom");
            String typeLit = request.getParameter("typeLit");
            String prixBaseStr = request.getParameter("prixBase");

            // Validation et exécution de la transaction
            if (idChambreStr != null && nom != null && typeLit != null && prixBaseStr != null) {
                int idChambre = Integer.parseInt(idChambreStr);
                double prixBase = Double.parseDouble(prixBaseStr);
                gestionObergeInn.getGestionChambre().ajouterChambre(idChambre, nom, typeLit, prixBase);

                // ajout d'un message de validation a la session
                session.setAttribute("messageSucces", "La chambre a été ajoutée avec succès.");

                // Rediriger vers le tableau de bord
                response.sendRedirect(request.getContextPath() + "/transaction?action=dashboard");
            } else {
                throw new IFT287Exception("Tous les champs sont requis.");
            }
        } catch (IFT287Exception e) {
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AjouterChambre.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            listeMessageErreur.add("Erreur inattendue : " + e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AjouterChambre.jsp");
            dispatcher.forward(request, response);
        }
    }
}
