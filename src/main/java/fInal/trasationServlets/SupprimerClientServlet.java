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

public class SupprimerClientServlet extends HttpServlet {
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

            // Validation et exécution de la transaction
            if (idClientStr != null) {
                int idClient = Integer.parseInt(idClientStr);
                gestionObergeInn.getGestionClient().supprimerClient(idClient);

                // Ajouter un message de succès à la session (si nécessaire)
                session.setAttribute("messageSucces", "Le client a été supprimé avec succès.");

                // Rediriger vers le tableau de bord ou une page de confirmation
                response.sendRedirect(request.getContextPath() + "/transaction?action=dashboard");
            } else {
                throw new IFT287Exception("L'ID du client est requis.");
            }
        } catch (IFT287Exception e) {
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/SupprimerClient.jsp");
            dispatcher.forward(request, response);


        } catch (Exception e) {
            listeMessageErreur.add("Erreur inattendue : " + e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/SupprimerClient.jsp");
            dispatcher.forward(request, response);
        }
    }
}
