package com.aubergeServlet.fInal;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Inscription extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirige vers la page de création de compte lorsqu'on arrive par GET
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/CreerCompte.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet Inscription : POST");

        // Ici vous pouvez gérer la logique d'inscription si besoin
        // Par exemple, vérifier les champs remplis sur CreerCompte.jsp et ajouter l'utilisateur à la base de données

        // Une fois l'inscription réussie, rediriger vers une page de confirmation ou de connexion
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inscriptionSuccess.jsp");
        dispatcher.forward(request, response);
    }
}
