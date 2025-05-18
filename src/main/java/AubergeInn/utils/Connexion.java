package AubergeInn.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Gestionnaire d'une connexion avec une BD NoSQL via MongoDB.
 *
 * <pre>
 *
 * Vincent Ducharme
 * Université de Sherbrooke
 * Version 1.0 - 18 juin 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * Ce programme permet d'ouvrir une connexion avec une BD via MongoDB.
 *
 * Pre-condition
 *   La base de données MongoDB doit etre accessible.
 *
 * Post-condition
 *   La connexion est ouverte.
 * </pre>
 */
public class Connexion
{
    private MongoClient client;
    private MongoDatabase database;

    /**
     * Ouverture d'une connexion
     *
     * @param serveur : Le type de serveur SQL à utiliser (Valeur : local, dinf).
     * @param bd : nom de la base de données
     * @param user : userid sur le serveur SQL
     * @param pass : mot de passe sur le serveur SQL
     */
    public Connexion(String serveur, String bd, String user, String pass) throws IFT287Exception
    {

        if (serveur.equals("local"))
        {
            client = MongoClients.create();
        }
        else if (serveur.equals("dinf"))
        {
            String connectionString = "mongodb://" + user + ":" + pass + "@bd-info2.dinf.usherbrooke.ca:27017/" + bd + "?ssl=true";
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();
            client = MongoClients.create(settings);
        }
        else
        {
            throw new IFT287Exception("Serveur inconnu");
        }

        database = client.getDatabase(bd);

        System.out.println("Ouverture de la connexion :\n"
                + "Connecté sur la BD MongoDB "
                + bd + " avec l'utilisateur " + user);
    }

    /**
     * Fermeture d'une connexion
     */
    public void fermer() {
        client.close();
        System.out.println("Connexion fermée");
    }


    /**
     * retourne la Connection MongoDB
     */
    public MongoClient getConnection()
    {
        return client;
    }

    /**
     * retourne la DataBase MongoDB
     */
    public MongoDatabase getDatabase()
    {
        return database;
    }
}
