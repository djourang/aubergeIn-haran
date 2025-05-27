package fInal;

import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;
import AubergeInn.utils.Securite;

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
import java.util.LinkedList;
import java.util.List;

public class Login extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Servlet Login : POST");

            String userIdBD = request.getParameter("userIdBD");
            String motDePasseBD = request.getParameter("motDePasseBD");
            String serveur = request.getParameter("serveur");
            String bd = request.getParameter("bd");

            // ✅ Ces validations doivent être dans le try
            if (userIdBD == null || userIdBD.isEmpty())
                throw new IFT287Exception("Vous devez entrer un nom d'utilisateur.");
            if (motDePasseBD == null || motDePasseBD.isEmpty())
                throw new IFT287Exception("Vous devez entrer un mot de passe.");
            if (serveur == null || serveur.isEmpty())
                throw new IFT287Exception("Vous devez entrer un nom de serveur.");
            if (bd == null || bd.isEmpty())
                throw new IFT287Exception("Vous devez entrer un nom de base de données.");

            // Connexion PostgreSQL
            Connexion connexion = new Connexion();
            Connection sqlConn = connexion.getConnection();

            // Hachage du mot de passe
            String motDePasseHash = Securite.toHexString(Securite.getSHA(motDePasseBD));

            // Vérifie si utilisateur existe
            PreparedStatement stmtCheck = sqlConn.prepareStatement("SELECT * FROM utilisateur WHERE username = ?");
            stmtCheck.setString(1, userIdBD);
            ResultSet rs = stmtCheck.executeQuery();

            if (!rs.next()) {
                PreparedStatement stmtInsert = sqlConn.prepareStatement(
                        "INSERT INTO utilisateur(username, password, role) VALUES (?, ?, ?)"
                );
                stmtInsert.setString(1, userIdBD);
                stmtInsert.setString(2, motDePasseHash);
                stmtInsert.setString(3, "ADMIN");
                stmtInsert.executeUpdate();
                stmtInsert.close();
            }

            rs.close();
            stmtCheck.close();

            // Enregistrer session
            ServletContext context = getServletContext();
            context.setAttribute("serveur", serveur);
            context.setAttribute("bd", bd);
            context.setAttribute("user", userIdBD);
            context.setAttribute("pass", motDePasseBD);

            HttpSession session = request.getSession();
            AubergeHelper.creerGestionnaire(context, session);

            session.setAttribute("etat", AubergeConstantes.CONNECTE);
            session.setAttribute("bd", bd);
            session.setAttribute("role", "ADMIN");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/TableauDeBordAdmin.jsp");
            dispatcher.forward(request, response);

            connexion.fermer();

        } catch (IFT287Exception e) {
            redirigerAvecErreur(request, response, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            redirigerAvecErreur(request, response, "Erreur de hachage : " + e.getMessage());
        } catch (Exception e) {
            redirigerAvecErreur(request, response, "Erreur inattendue : " + e.getMessage());
        }
    }

    private void redirigerAvecErreur(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        List<String> erreurs = new LinkedList<>();
        erreurs.add(message);
        request.setAttribute("listeMessageErreur", erreurs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

}