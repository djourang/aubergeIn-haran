package fInal.trasationServlets;

import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;
import AubergeInn.utils.Securite;
import fInal.AubergeConstantes;
import fInal.AubergeHelper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LoginClient extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> listeMessageErreur = new LinkedList<>();

        try {
            System.out.println("Servlet LoginClient : POST");

            // 1. Lire les paramètres
            String userIdBD = request.getParameter("userIdBD");
            String motDePasseBD = request.getParameter("motDePasseBD");
            String serveur = request.getParameter("serveur");
            String bd = request.getParameter("bd");

            // 2. Validation
            if (userIdBD == null || userIdBD.isEmpty())
                throw new IFT287Exception("Vous devez entrer un nom d'utilisateur.");
            if (motDePasseBD == null || motDePasseBD.isEmpty())
                throw new IFT287Exception("Vous devez entrer un mot de passe.");
            if (serveur == null || serveur.isEmpty())
                throw new IFT287Exception("Vous devez entrer un nom de serveur.");
            if (bd == null || bd.isEmpty())
                throw new IFT287Exception("Vous devez entrer un nom de base de données.");

            // 3. Connexion à PostgreSQL
            Connexion connexion = new Connexion();
            Connection sqlConn = connexion.getConnection();

            try {
                // 4. Hachage du mot de passe
                String motDePasseHash = Securite.toHexString(Securite.getSHA(motDePasseBD));

                // 5. Vérifier si l'utilisateur existe
                PreparedStatement stmtCheck = sqlConn.prepareStatement(
                        "SELECT * FROM utilisateur WHERE username = ?"
                );
                stmtCheck.setString(1, userIdBD);
                ResultSet rs = stmtCheck.executeQuery();

                if (!rs.next()) {
                    // L'utilisateur n'existe pas → l'insérer
                    PreparedStatement stmtInsert = sqlConn.prepareStatement(
                            "INSERT INTO utilisateur(username, password, role) VALUES (?, ?, ?)"
                    );
                    stmtInsert.setString(1, userIdBD);
                    stmtInsert.setString(2, motDePasseHash);
                    stmtInsert.setString(3, "CLIENT");
                    stmtInsert.executeUpdate();
                    stmtInsert.close();
                }

                rs.close();
                stmtCheck.close();

                // 6. Stocker les infos dans le contexte
                ServletContext context = getServletContext();
                context.setAttribute("serveur", serveur);
                context.setAttribute("bd", bd);
                context.setAttribute("user", userIdBD);
                context.setAttribute("pass", motDePasseBD);

                // 7. Créer les gestionnaires
                HttpSession session = request.getSession();
                AubergeHelper.creerGestionnaire(context, session);

                // 8. Stocker dans session
                session.setAttribute("etat", AubergeConstantes.CONNECTE);
                session.setAttribute("bd", bd);
                session.setAttribute("role", "CLIENT");

                // 9. Rediriger
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/TableauDeBordClient.jsp");
                dispatcher.forward(request, response);

                // 10. Fermer la connexion
                connexion.fermer();

            } catch (NoSuchAlgorithmException e) {
                listeMessageErreur.add("Erreur de hachage : " + e.getMessage());
                redirigerAvecErreur(request, response, listeMessageErreur);
            } catch (SQLException e) {
                listeMessageErreur.add("Erreur SQL : " + e.getMessage());
                redirigerAvecErreur(request, response, listeMessageErreur);
            } catch (Exception e) {
                listeMessageErreur.add("Erreur inattendue : " + e.getMessage());
                redirigerAvecErreur(request, response, listeMessageErreur);
            }

        } catch (IFT287Exception e) {
            listeMessageErreur.add(e.getMessage());
            redirigerAvecErreur(request, response, listeMessageErreur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void redirigerAvecErreur(HttpServletRequest request, HttpServletResponse response, List<String> erreurs)
            throws ServletException, IOException {
        request.setAttribute("listeMessageErreur", erreurs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Servlet LoginClient : GET");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
