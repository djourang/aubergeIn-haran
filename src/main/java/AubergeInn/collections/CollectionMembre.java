package AubergeInn.collections;


import AubergeInn.tuples.Membre;
import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CollectionMembre {

    private final Connexion connexion;
    private MongoCollection<Document> membreCollection;

    public CollectionMembre(Connexion connexion) {
        this.connexion = connexion;
        this.membreCollection = connexion.getDatabase().getCollection("membre");
    }

    // Ajouter un nouveau membre dans la collection
    public void ajouterMembre(Membre membre) {
        membreCollection.insertOne(membre.toDocument());
    }

    // Récupérer un membre par son ID
    public Membre getMembreById(String userId) {
        Document membreDoc = membreCollection.find(Filters.eq("userId", userId)).first();
        if (membreDoc != null) {
            return new Membre(membreDoc);
        } else {
            return null;
        }
    }

    // Supprimer un membre de la collection
    public void supprimerMembre(Membre membre) {
        membreCollection.deleteOne(new Document("userId", membre.getUserId()));
    }

    // Afficher les informations d'un membre par son ID
    public void afficherMembre(String userId) throws IFT287Exception {
        Membre membreAafficher = getMembreById(userId);
        if (membreAafficher == null)
            throw new IFT287Exception("Membre inexistant: " + userId);
        else
            System.out.println(membreAafficher);
    }

    // Récupérer tous les membres
    public List<Membre> getAllLesMembres() {
        List<Membre> membres = new ArrayList<>();
        FindIterable<Document> docs = membreCollection.find();  // Récupère tous les documents de la collection

        for (Document doc : docs) {
            membres.add(new Membre(doc));  // Crée un objet Membre pour chaque document et l'ajoute à la liste
        }

        return membres;
    }

    public Connexion getConnexion() {
        return connexion;
    }

    public Membre getMembreByUsername(String username) {
        Document membreDoc = membreCollection.find(Filters.eq("userId", username)).first();
        if (membreDoc != null) {
            return new Membre(membreDoc);
        } else {
            return null;
        }
    }

    // Méthode pour vérifier si un membre existe avec le nom d'utilisateur et le mot de passe
    public boolean  membreExiste(String username) {
        // Chercher un document qui correspond à la fois au nom d'utilisateur et au mot de passe haché
        Document membreDoc = membreCollection.find(Filters.eq("userId", username)).first();
        // Si le document existe, cela signifie que le membre existe avec ces identifiants
        return membreDoc != null;
    }
}







