package AubergeInn.gestionnaires;

import AubergeInn.collections.CollectionChambre;
import AubergeInn.collections.CollectionClient;
import AubergeInn.collections.CollectionReservation;
import AubergeInn.tuples.Client;
import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;

import java.util.ArrayList;
import java.util.List;


public class GestionClient {

    private final Connexion connexion;
    private final CollectionClient clients;
    private final CollectionChambre chambres; //(ex: pour la suppression on aura besoin)
    private final CollectionReservation reservations;

    public GestionClient(CollectionChambre collectionChambre, CollectionClient collectionClient, CollectionReservation tableReservationChambre) throws IFT287Exception {
        this.connexion = collectionClient.getConnexion();
        this.clients = collectionClient;
        if (collectionClient.getConnexion() != tableReservationChambre.getConnexion()) {
            throw new IFT287Exception(" connexion_table_client et connexion_table_reservationChambre doivent etre identiques ");
        } else {
            this.chambres = collectionChambre;
            this.reservations = tableReservationChambre;
        }
    }

    /*
    Cette transaction ajoute un nouveau client au système.
     */
    public void ajouterClient(int idClient, String prenom, String nom, int age) throws IFT287Exception {
        try {
            // Vérifie si le client existe déja
            if (!(clients.getClientById(idClient) == null))
                throw new IFT287Exception("Le client : " + idClient + " existe deja.");
            // Ajout du client dans le document Client
            clients.ajouterClient(new Client(idClient, prenom, nom, age));
        } catch (Exception e) {

            throw e;
        }
    }

    /*
    Cette transaction supprime un client s'il n'a pas de réservation en cours.
     */
    public void supprimerClient(int idClient) throws IFT287Exception {
        try {
            Client client = clients.getClientById(idClient);
            // Vérifie si le client n'existe pas
            if (client == null) {
                throw new IFT287Exception("Le client : " + idClient + " n'existe pas.");
            }
            // verifier si le client a une reservation en cours ?
            if (!reservations.clientSansResrvation(client)) {
                throw new IFT287Exception("Le client : " + idClient + " a une ou plusieur reservation en cours");
            }
            // suprimer le client de la table des Clients
            clients.supprimerClient(client);
        } catch (Exception e) {

            throw e;
        }
    }

    /*
    Cette transaction affiche toutes les informations sur un client, incluant les réservations
    présentes et passées. Les réservations contiennent le prix total de la réservation, sans les taxes.
     */
    public Client afficherClient(int idClient) throws IFT287Exception {
        try {


            // Vérifie que le client existe bien
            if (clients.getClientById(idClient) == null)
                throw new IFT287Exception("Le client : " + idClient + " n'existe pas.");
            return clients.afficherClient(idClient);
            // Commit

        } catch (Exception e) {

            throw e;
        }
    }

    /**
     * Retourne la liste des clients qui ont des réservations en cours.
     */
    public List<Client> getListeClientsAvecReservations() throws IFT287Exception {
        List<Client> clientsAvecReservations = new ArrayList<>();
        try {
            // Récupère tous les clients
            List<Client> tousLesClients = clients.getAllLesClients();

            // Parcourt tous les clients et ajoute ceux qui ont une réservation en cours à la liste
            for (Client client : tousLesClients) {
                if (!reservations.clientSansResrvation(client)) {
                    clientsAvecReservations.add(client);
                }
            }
        } catch (Exception e) {
            throw new IFT287Exception("Erreur lors de la récupération des clients avec réservations : " + e.getMessage());
        }

        return clientsAvecReservations;
    }


}




