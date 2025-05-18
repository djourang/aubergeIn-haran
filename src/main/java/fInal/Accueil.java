package com.aubergeServlet.fInal;

import AubergeInn.gestionnaires.GestionObergeInn;
import AubergeInn.utils.IFT287Exception;
import AubergeInn.utils.Securite;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *Servlet qui gère la connexion d'un utilisateur au système de gestion de bibliothèque
 * <pre>
 * Haran Mourno
 * Université de Sherbrooke
 * Version 1.0 - 13 Août 2024
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * </pre>
 */

public class Accueil extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Accueil : POST");
        try
        {
            if (!AubergeHelper.peutProcederLogin(getServletContext(), request, response))
            {
                System.out.println("Servlet Accueil : POST ne peut pas procéder.");
                return;
            }

            HttpSession session = request.getSession();

            // Si c'est la première fois qu'on essaie de se logguer, ou d'inscrire quelqu'un
            if (!AubergeHelper.gestionnairesCrees(session))
            {
                System.out.println("Servlet Accueil : POST Création des gestionnaires");
                AubergeHelper.creerGestionnaire(getServletContext(), session);
            }

            if (request.getParameter("connecter") != null)
            {
                // Logique de connexion (non modifiée)
            }
            else if (request.getParameter("inscrire") != null)
            {
                System.out.println("Servlet Accueil : POST - Inscrire");
                try
                {
                    // Lecture des paramètres du formulaire de CreerCompte.jsp
                    String userId = request.getParameter("userId");
                    String motDePasse = request.getParameter("motDePasse");
                    String nom = request.getParameter("nom");
                    String prenom = request.getParameter("prenom");
                    String email = request.getParameter("email");
                    String telephoneS = request.getParameter("telephone");
                    String role = request.getParameter("role");

                    // Validation des paramètres
                    if (userId == null || userId.equals(""))
                        throw new IFT287Exception("Vous devez entrer un nom d'utilisateur!");
                    if (motDePasse == null || motDePasse.equals(""))
                        throw new IFT287Exception("Vous devez entrer un mot de passe!");
                    if (nom == null || nom.equals(""))
                        throw new IFT287Exception("Vous devez entrer un nom!");
                    if (prenom == null || prenom.equals(""))
                        throw new IFT287Exception("Vous devez entrer un prénom!");
                    if (email == null || email.equals(""))
                        throw new IFT287Exception("Vous devez entrer un email!");
                    if (telephoneS == null || telephoneS.equals(""))
                        throw new IFT287Exception("Vous devez entrer un numéro de téléphone!");
                    if (role == null || role.equals(""))
                        throw new IFT287Exception("Vous devez sélectionner un rôle!");

                    // Hashage du mot de passe
                    byte[] sha = Securite.getSHA(motDePasse);
                    String motDePasseSHA = Securite.toHexString(sha);

                    // Conversion des types si nécessaire
                    long telephone = AubergeHelper.ConvertirLong(telephoneS, "Le numéro de téléphone");
                    int acces = role.equals("ADMIN") ? 0 : 1;

                    // Enregistrement du nouveau membre
                    GestionObergeInn aubergeUpdate = AubergeHelper.getaubergeUpdate(session);
                    synchronized (aubergeUpdate)
                    {
                        aubergeUpdate.getGestionMembre().inscrire(userId, motDePasseSHA, acces, nom, prenom, email, role, telephone);
                    }

                    // Mise à jour de la session si nécessaire
                    if (session.getAttribute("userID") == null)
                    {
                        session.setAttribute("userID", userId);
                        if(acces == 0)
                            session.setAttribute("admin", true);
                        session.setAttribute("etat", AubergeConstantes.CONNECTE);

                        System.out.println("Servlet Accueil : POST dispatch vers accueil.jsp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/accueil.jsp");
                        dispatcher.forward(request, response);
                    }
                    else
                    {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/accueil.jsp");
                        dispatcher.forward(request, response);
                    }
                }
                catch (Exception e)
                {
                    List<String> listeMessageErreur = new LinkedList<String>();
                    listeMessageErreur.add(e.getMessage());
                    request.setAttribute("listeMessageErreur", listeMessageErreur);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/CreerCompte.jsp");
                    dispatcher.forward(request, response);
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
            dispatcher.forward(request, response);
            e.printStackTrace();
        }
    }

    // Dans les formulaires, on utilise la méthode POST
    // donc, si le servlet est appelé avec la méthode GET
    // c'est que l'adresse a été demandé par l'utilisateur.
    // On procéde si la connexion est actives seulement, sinon
    // on retourne au login
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Accueil : GET");
        if (AubergeHelper.peutProceder(getServletContext(), request, response))
        {
            System.out.println("Servlet Accueil : GET dispatch vers accueil.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
            dispatcher.forward(request, response);
        }
    }

} // class
