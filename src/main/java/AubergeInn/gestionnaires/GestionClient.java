package AubergeInn.gestionnaires;

import AubergeInn.collections.TableChambre;
import AubergeInn.collections.TableClient;
import AubergeInn.collections.TableReservationChambre;
import AubergeInn.tuples.LigneClient;
import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GestionClient {

    private final Connexion connexion;
    private final TableClient clients;
    private final TableChambre chambres; //(ex: pour la suppression on aura besoin)
    private final TableReservationChambre reservations;

    public GestionClient(TableChambre collectionChambre, TableClient collectionClient, TableReservationChambre tableReservationChambre) throws IFT287Exception {
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
    public void ajouterClient(int idClient, String prenom, String nom, int age) throws IFT287Exception, SQLException {
        try {
            // Vérifie si le client existe déja
            if (!(clients.getClient(idClient) == null))
                throw new IFT287Exception("Le client : " + idClient + " existe deja.");
            // Ajout du client dans le document Client
            clients.ajouterClient(new LigneClient(idClient, prenom, nom, age));

        } catch (Exception e) {

            throw e;
        }
    }

    /*
    Cette transaction supprime un client s'il n'a pas de réservation en cours.
     */
    public void supprimerClient(int idClient) throws IFT287Exception, SQLException {
        try {
            LigneClient client = clients.getClient(idClient);
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
    public LigneClient afficherClient(int idClient) throws IFT287Exception, SQLException {
        try {
            // Vérifie que le client existe bien
            if (clients.getClient(idClient) == null)
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
    public List<LigneClient> getListeClientsAvecReservations() throws IFT287Exception {
        try {
            return clients.getClientsAvecReservations();
        } catch (SQLException e) {
            throw new IFT287Exception("Erreur lors de la récupération des clients avec réservations : " + e.getMessage());
        }
    }


}




