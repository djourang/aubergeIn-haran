package com.aubergeServlet.fInal.trasationServlets;

import AubergeInn.gestionnaires.GestionObergeInn;
import AubergeInn.tuples.Client;
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

public class AfficherClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> listeMessageErreur = new LinkedList<>();
        try {
            HttpSession session = request.getSession();
            GestionObergeInn gestionObergeInn = AubergeHelper.getaubergeUpdate(session);

            // Lecture des paramètres du formulaire
            String idClientStr = request.getParameter("idClient");

            // Validation et exécution de la transaction
            if (idClientStr != null) {
                int idClient = Integer.parseInt(idClientStr);
                Client client = gestionObergeInn.getGestionClient().afficherClient(idClient);

                if (client != null) {
                    // Stocker le résultat dans la session
                    session.setAttribute("client", client);
                    request.setAttribute("rechercheEffectuee", true);
                    session.setAttribute("messageSucces", "Client affiché avec succès.");
                } else {
                    session.setAttribute("messageErreur", "Aucun client trouvé pour l'ID spécifié.");
                }

                // Rediriger vers la page d'affichage
                response.sendRedirect(request.getContextPath() + "/transaction?action=afficherClient");
            } else {
                throw new IFT287Exception("L'ID du client est requis.");
            }
        } catch (IFT287Exception e) {
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AfficherClient.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            listeMessageErreur.add("Erreur inattendue : " + e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AfficherClient.jsp");
            dispatcher.forward(request, response);
        }
    }
}
