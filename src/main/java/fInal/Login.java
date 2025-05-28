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


        HttpSession session = request.getSession(true);
        session.setAttribute("bd", request.getParameter("bd"));
        session.setAttribute("serveur", request.getParameter("serveur"));
        session.setAttribute("userIdBD", request.getParameter("userIdBD"));

        // Redirige toujours vers la page du tableau de bord
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/TableauDeBordAdmin.jsp");
        dispatcher.forward(request, response);
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