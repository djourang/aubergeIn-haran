package AubergeInn.collections;

import AubergeInn.tuples.Client;
import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class CollectionClient {

    private final Connexion connexion;


    private MongoCollection<Document> clientCollection;

    public CollectionClient(Connexion connexion) {
        this.connexion = connexion;
        clientCollection = connexion.getDatabase().getCollection("client");
    }


    public void ajouterClient(Client client) {
        clientCollection.insertOne(client.toDocument());
    }

    public Client getClientById(int idClient) {
        Document clientDoc = clientCollection.find(Filters.eq("idClient", idClient)).first();
        if (clientDoc != null) {
            return new Client(clientDoc);
        } else {
            return null;
        }
    }


    public Connexion getConnexion() {
        return connexion;
    }


    public void supprimerClient(Client client)  {
        clientCollection.deleteOne(new Document("idClient", client.getidClient()));
    }


    public Client afficherClient(int idClient) throws IFT287Exception {
        Client clientAafficher = getClientById(idClient);
        if (clientAafficher == null)
            throw new IFT287Exception("Client inexistant: " + idClient);
        else
           return clientAafficher;
    }
    // Méthode pour récupérer tous les clients
    public List<Client> getAllLesClients() {
        List<Client> clients = new ArrayList<>();
        FindIterable<Document> docs = clientCollection.find();  // Récupère tous les documents de la collection

        for (Document doc : docs) {
            clients.add(new Client(doc));  // Crée un objet Client pour chaque document et l'ajoute à la liste
        }

        return clients;
    }

}


