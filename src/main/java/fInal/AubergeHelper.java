package com.aubergeServlet.fInal;

import AubergeInn.gestionnaires.GestionObergeInn;
import AubergeInn.utils.IFT287Exception;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AubergeHelper
{
    public static boolean infoBDValide(ServletContext c)
    {
        return c.getAttribute("serveur") != null;
    }

    public static boolean peutProceder(ServletContext c, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        if(infoBDValide(c))
        {
            HttpSession session = request.getSession(false);
            if (AubergeHelper.estConnecte(session))
            {
                return true;
            }
            DispatchToLogin(request, response);
            return false;
        }
        else
        {
            DispatchToBDConnect(request, response);
            return false;
        }
    }

    public static boolean peutProcederLogin(ServletContext c, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        if(infoBDValide(c))
        {
            HttpSession session = request.getSession(false);
            if (session != null)
            {
                session.invalidate();
            }
            return true;
        }
        else
        {
            DispatchToBDConnect(request, response);
            return false;
        }
    }

    public static void DispatchToLoginServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(false);
        if (AubergeHelper.estConnecte(session))
        {
            session.invalidate();
        }
        // Afficher le menu de connexion principal de l'application
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
        dispatcher.forward(request, response);
    }

    public static void DispatchToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(false);
        if (AubergeHelper.estConnecte(session))
        {
            session.invalidate();
        }
        // Afficher le menu de connexion principal de l'application
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Login.jsp");
        dispatcher.forward(request, response);
    }

    public static void DispatchToBDConnect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(false);
        if (AubergeHelper.estConnecte(session))
        {
            session.invalidate();
        }
        // Afficher le menu de connexion principal de l'application
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    public static boolean estConnecte(HttpSession session)
    {
        if(session == null)
            return false;
        return session.getAttribute("etat") != null;
    }

    public static boolean gestionnairesCrees(HttpSession session)
    {
        if(session == null)
            return false;
        return session.getAttribute("aupergeInterrogation") != null;
    }

    public static void creerGestionnaire(ServletContext c, HttpSession s) throws Exception
    {
        String serveur = (String) c.getAttribute("serveur");
        String bd = (String) c.getAttribute("bd");
        String userIdBD = (String) c.getAttribute("user");
        String pass = (String) c.getAttribute("pass");

        GestionObergeInn aubergeInterrogation = new GestionObergeInn(serveur, bd, userIdBD, pass);
        s.setAttribute("aubergeInterrogation", aubergeInterrogation);
        GestionObergeInn aubergeUpdate = new GestionObergeInn(serveur, bd, userIdBD, pass);
        s.setAttribute("aubergeUpdate", aubergeUpdate);
    }

    public static GestionObergeInn getaubergeInterro(HttpSession session)
    {
        return (GestionObergeInn)session.getAttribute("aubergeInterrogation");
    }

    public static GestionObergeInn getaubergeUpdate(HttpSession session)
    {
        return (GestionObergeInn)session.getAttribute("aubergeUpdate");
    }


    public static int ConvertirInt(String v, String nom) throws IFT287Exception
    {
        try
        {
            return Integer.parseInt(v);
        }
        catch(Exception e)
        {
            throw new IFT287Exception(nom + " ne doit être composé que de chiffre.");
        }
    }

    public static long ConvertirLong(String v, String nom) throws IFT287Exception
    {
        try
        {
            return Long.parseLong(v);
        }
        catch(Exception e)
        {
            throw new IFT287Exception(nom + " ne doit être composé que de chiffre.");
        }
    }
}
