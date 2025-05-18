package AubergeInn;
//   DOGOY, HARAN MOURNO - 19167713
//   ACHRAF AMINE BOUDEFFA -             Matricule
//   MOUHAMAD MOUSTAPHA MBAYE -          Matricule


import AubergeInn.gestionnaires.GestionObergeInn;
import AubergeInn.utils.IFT287Exception;

import java.io.*;
import java.sql.Date;
import java.util.StringTokenizer;

/**
 * Fichier de base pour le TP2 du cours IFT287
 *
 * <pre>
 *
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 7 juillet 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * Ce programme permet d'appeler des transactions d'un systeme
 * de gestion utilisant une base de donnees.
 *
 * Paramètres du programme
 * 0- site du serveur SQL ("local" ou "dinf")
 * 1- nom de la BD
 * 2- user id pour etablir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   - La base de donnees doit exister
 *
 * Post-condition
 *   - Le programme effectue les mises à jour associees à chaque
 *     transaction
 * </pre>
 */
public class AubergeInn
{
    private static GestionObergeInn gestionObergeInn = null;


    public void fermer() throws Exception
    {
        gestionObergeInn.fermer();
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        if (args.length < 4) {
            System.out.println("Usage: java AubergeInn.AubergeInn <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }
        AubergeInn auberge = null;
        try
        {
            AubergeInn.gestionObergeInn = new GestionObergeInn(args[0], args[1], args[2], args[3]);
            BufferedReader reader = ouvrirFichier(args);
            String transaction = lireTransaction(reader);

            while (!finTransaction(transaction))
            {
                executerTransaction(transaction,gestionObergeInn);
                transaction = lireTransaction(reader);
            }
        }
        finally
        {
            if (auberge != null)
                auberge.fermer();
        }
    }

    /**
     * Decodage et traitement d'une transaction
     */
    static void executerTransaction(String transaction,GestionObergeInn gestionObergeInn) throws Exception, IFT287Exception
    {
        try
        {
            System.out.print(transaction + '\n');
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
            if (tokenizer.hasMoreTokens())
            {
                String command = tokenizer.nextToken();
                // Vous devez remplacer la chaine "commande1" et "commande2" par
                // les commandes de votre programme. Vous pouvez ajouter autant
                // de else if que necessaire. Vous n'avez pas a traiter la
                // commande "quitter".

                if (command.equals("ajouterClient"))
                {
                    // Lecture des parametres de la transaction ajouterClient <idClient> <prenom> <nom> <age>
                    int idClient = readInt(tokenizer);
                    String prenom = readString(tokenizer);
                    String nom = readString(tokenizer);
                    int age = readInt(tokenizer);
                    // Appel de la methode des AubergeInn.gestionnaires qui traite la transaction specifique
                    gestionObergeInn.getGestionClient().ajouterClient(idClient,prenom,nom,age);
                }
                else if (command.equals("supprimerClient"))
                {
                    // Lecture des parametres de la transaction supprimerClient <idClient>
                    int idClient = readInt(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionClient().supprimerClient(idClient);
                }
                else if (command.equals("ajouterChambre"))
                {
                    // Lecture des parametres de la transaction ajouterChambre <idChambre> <nomChambre> <type de lit> <prix de base>
                    int idChambre = readInt(tokenizer);
                    String nom = readString(tokenizer);
                    String typeLit = readString(tokenizer);
                    double prixBase = readDouble(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionChambre().ajouterChambre(idChambre,nom,typeLit,prixBase);
                }
                else if (command.equals("supprimerChambre"))
                {
                    // Lecture des parametres de la transaction supprimerChambre <idChambre>
                    int idChambre = readInt(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionChambre().supprimerChambre(idChambre);
                }
                else if (command.equals("ajouterCommodite"))
                {
                    // Lecture des parametres de la transaction ajouterCommodite <idCommodite> <description> <surplus prix>
                    int idCommodite = readInt(tokenizer);
                    String description = readString(tokenizer);
                    double surplusPrix = readDouble(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionCommodite().ajouterCommodite(idCommodite,description,surplusPrix);
                }
                else if (command.equals("supprimerCommodite"))
                {
                    // Lecture des parametres de la transaction supprimerCcommodite <idCommodite>
                    int idCommodite = readInt(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionCommodite().supprimerCommodite(idCommodite);
                }
                else if (command.equals("inclureCommodite"))
                {
                    // Lecture des parametres de la transaction inclureCommodite <idChambre> <idCommodite>
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionCommodite().inclureCommodite(idChambre,idCommodite);
                }
                else if (command.equals("enleverCommodite"))
                {
                    // Lecture des parametres de la transaction enleverCommodite <idChambre> <idCommodite>
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionCommodite().enleverCommodite(idChambre,idCommodite);
                }
                else if (command.equals("afficherChambresLibres"))
                {
                    // Lecture des parametres de la transaction afficherChambresLibres <dateDebut> <dateFin>
                    java.util.Date dateDebut = readDate(tokenizer);
                    java.util.Date dateFin = readDate(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionChambre().afficherChambresLibres(dateDebut,dateFin);
                }
                else if (command.equals("afficherClient"))
                {
                    // Lecture des parametres de la transaction afficherClient <idClient>
                    int idClient = readInt(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionClient().afficherClient(idClient);
                }
                else if (command.equals("afficherChambre"))
                {
                    // Lecture des parametres de la transaction afficherChambre <idChambre>
                    int idChambre = readInt(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionChambre().afficherChambre(idChambre);
                }
                else if (command.equals("reserver"))
                {
                    // Lecture des parametres de la transaction reserver <idClient> <idChambre> <dateDebut> <dateFin>
                    int idClient = readInt(tokenizer);
                    int idChambre = readInt(tokenizer);
                    Date dateDebut = readDate(tokenizer);
                    Date dateFin = readDate(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionReservation().reserver(idClient,idChambre,dateDebut,dateFin);
                }
                else if (command.equals("enleverReservation"))
                {
                    // Lecture des parametres de la transaction reserver <idClient> <idChambre> <dateDebut> <dateFin>
                    int idClient = readInt(tokenizer);
                    int idChambre = readInt(tokenizer);
                    Date dateDebut = readDate(tokenizer);
                    Date dateFin = readDate(tokenizer);
                    // de traitement pour la transaction
                    gestionObergeInn.getGestionReservation().enleverReservation(idClient,idChambre,dateDebut,dateFin);
                }
                else
                {
                    System.out.println(" : Transaction non reconnue");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(" " + e.toString());
            // Ce rollback est ici seulement pour vous aider et éviter des problèmes lors de la correction
            // automatique. En théorie, il ne sert à rien et ne devrait pas apparaître ici dans un programme
            // fini et fonctionnel sans bogues.
            // gestionObergeInn.getConnexion().rollback();
        }
    }

    static double readDouble(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Double.valueOf(token).doubleValue();
            } catch (NumberFormatException e) {
                throw new Exception("Nombre decimal attendu à la place de \"" + token + "\"");
            }
        } else {
            throw new Exception("Autre parametre attendu");
        }
    }


    // ****************************************************************
    // *   Les methodes suivantes n'ont pas besoin d'etre modifiees   *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException
    {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException
    {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction)
    {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }

    /** Lecture d'une chaine de caracteres de la transaction entree a l'ecran */
    static String readString(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

    /**
     * Lecture d'un int java de la transaction entree a l'ecran
     */
    static int readInt(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Integer.valueOf(token).intValue();
            }
            catch (NumberFormatException e)
            {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Date.valueOf(token);
            }
            catch (IllegalArgumentException e)
            {
                throw new Exception("Date dans un format invalide - \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

}
