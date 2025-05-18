package com.aubergeServlet.fInal.trasationServlets;

import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;
import AubergeInn.utils.Securite;
import com.aubergeServlet.fInal.AubergeConstantes;
import com.aubergeServlet.fInal.AubergeHelper;
import com.mongodb.MongoSecurityException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.servlet.ServletContext;
import org.bson.Document;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

public class LoginClient extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Servlet LoginClient : POST");

            // Lecture des paramètres du formulaire index.jsp
            String userIdBD = request.getParameter("userIdBD");
            String motDePasseBD = request.getParameter("motDePasseBD");
            String serveur = request.getParameter("serveur");
            String bd = request.getParameter("bd");

            // Validation des paramètres
            if (userIdBD == null || userIdBD.equals("")) {
                throw new IFT287Exception("Vous devez entrer un nom d'utilisateur de la base de données.");
            }

            if (motDePasseBD == null || motDePasseBD.equals("")) {
                throw new IFT287Exception("Vous devez entrer un mot de passe de la base de données.");
            }

            if (serveur == null || serveur.equals("")) {
                throw new IFT287Exception("Vous devez choisir un serveur.");
            }

            if (bd == null || bd.equals("")) {
                throw new IFT287Exception("Vous devez entrer un nom de base de données.");
            }

            try {
                // Tentative de connexion à la base de données MongoDB
                Connexion connexion = new Connexion(serveur, bd, userIdBD, motDePasseBD);

                // Hachage du mot de passe
                String motDePasseHash = Securite.toHexString(Securite.getSHA(motDePasseBD));

                // Accéder à la base de données
                MongoDatabase database = connexion.getDatabase();

                // Créer une collection "user" si elle n'existe pas
                if (!database.listCollectionNames().into(new LinkedList<>()).contains("user")) {
                    database.createCollection("user");
                }

                // Récupérer la collection "user"
                MongoCollection<Document> userCollection = database.getCollection("user");

                // Vérifier si l'utilisateur existe déjà
                Document existingUser = userCollection.find(Filters.eq("username", userIdBD)).first();

                if (existingUser == null) {
                    // Si l'utilisateur n'existe pas, l'ajouter
                    Document newUser = new Document("username", userIdBD)
                            .append("password", motDePasseHash)
                            .append("role", "CLIENT");
                    userCollection.insertOne(newUser);
                }

                // Stocker les informations de connexion dans le contexte de l'application
                ServletContext context = getServletContext();
                context.setAttribute("serveur", serveur);
                context.setAttribute("bd", bd);
                context.setAttribute("user", userIdBD);
                context.setAttribute("pass", motDePasseBD);

                // Créer les gestionnaires
                HttpSession session = request.getSession();
                AubergeHelper.creerGestionnaire(context, session);

                // Stocker les informations utilisateur dans la session
                session.setAttribute("etat", AubergeConstantes.CONNECTE);
                session.setAttribute("bd", bd);
                session.setAttribute("role", "CLIENT");

                // Rediriger vers le tableau de bord client
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/TableauDeBordClient.jsp");
                dispatcher.forward(request, response);

                // Fermeture de la connexion après redirection
                connexion.fermer();

            } catch (MongoSecurityException e) {
                List<String> listeMessageErreur = new LinkedList<>();
                listeMessageErreur.add("Erreur d'authentification : Veuillez vérifier votre nom d'utilisateur et mot de passe.");
                request.setAttribute("listeMessageErreur", listeMessageErreur);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                List<String> listeMessageErreur = new LinkedList<>();
                listeMessageErreur.add("Erreur lors du hachage du mot de passe : " + e.getMessage());
                request.setAttribute("listeMessageErreur", listeMessageErreur);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
                e.printStackTrace();
            } catch (Exception e) {
                List<String> listeMessageErreur = new LinkedList<>();
                listeMessageErreur.add("Erreur inattendue : " + e.getMessage());
                request.setAttribute("listeMessageErreur", listeMessageErreur);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
                e.printStackTrace();
            }
        } catch (IFT287Exception e) {
            List<String> listeMessageErreur = new LinkedList<>();
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet LoginClient : GET");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
