package AubergeInn.gestionnaires;

import AubergeInn.collections.CollectionChambre;
import AubergeInn.collections.CollectionClient;
import AubergeInn.collections.CollectionReservation;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Client;
import AubergeInn.tuples.Reservation;
import AubergeInn.utils.IFT287Exception;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class GestionReservation {

    CollectionChambre colletionChambre;
    CollectionClient collectionClient;
    CollectionReservation collectionReservation;

    public GestionReservation(CollectionReservation collectionReservation, CollectionClient collectionClient, CollectionChambre colletionChambre) {
        this.colletionChambre = colletionChambre;
        this.collectionClient = collectionClient;
        this.collectionReservation =collectionReservation;
    }

    /*
    Cette transaction reserve une chambre pour un client de dateDebut (son arrivee) a
    dateFin (son depart). Par exemple, s'il reserve une chambre du 29 au 30 mars, il la
    reserve pour 1 nuit, celle entre le 29 et le 30 mars (et non 2 nuits).
     */
    public void reserver(int idClient, int idChambre, java.util.Date dateDebut, java.util.Date dateFin) throws IFT287Exception {
        try {

            //verifier si la date de debut > a la date de fin
            if (!dateDebut.before(dateFin)) throw new IFT287Exception("La date de debut ["+ dateDebut + "] est avant la date de fin ["+dateFin+"]");

            //verifier que la date de debut n'eset pas une date passer
            Date currentDate = new Date(System.currentTimeMillis());
            //if (dateFin.before(currentDate)) throw new IFT287Exception("La date de debut "+ dateDebut + " est une date passer ");

            // Verifier que le client existe
            Client client = collectionClient.getClientById(idClient);
            if (client == null) throw new IFT287Exception("Client inexistant: " + idClient);

            // Verifier que la chambre existe
            Chambre chambre = colletionChambre.getChambreById(idChambre);
            if (chambre == null) throw new IFT287Exception("Chambre inexistante: " + idChambre);

            // (chambreDisponible ?) Verifier que la chambre n'est pas deja reservee dans la periode donnee
            if (collectionReservation.estReserver(chambre,dateDebut,dateFin)) throw new IFT287Exception("Chambre " + idChambre + " deja reservee pour cette periode.");

            // Creation de la reservation
            Reservation reservation = new Reservation(chambre,client,dateDebut,dateFin);
            // ajout dans reservation
            collectionReservation.reserver(reservation);

        } catch (Exception e)
        {

            throw e;
        }
    }

    public void enleverReservation(int idClient, int idChambre, Date dateDebut, Date dateFin) throws IFT287Exception {
        try {
            //verifier si la date de debut > a la date de fin
            if (!dateDebut.before(dateFin)) throw new IFT287Exception("La date de debut ["+ dateDebut + "] est avant la date de fin ["+dateFin+"]");
            //verifier que la date de debut n'eset pas une date passer
            Date currentDate = new Date(System.currentTimeMillis());
            if (dateFin.before(currentDate)) throw new IFT287Exception("La date de debut "+ dateDebut + " est une date passer ");
            // Verifier que le client existe
            Client client = collectionClient.getClientById(idClient);
            if (client == null) throw new IFT287Exception("Client inexistant: " + idClient);
            // Verifier que la chambre existe
            Chambre chambre = colletionChambre.getChambreById(idChambre);
            if (chambre == null) throw new IFT287Exception("Chambre inexistante: " + idChambre);
            // Verifier que la chambre est reservee dans la periode donnee
            Reservation reservation = collectionReservation.getReservationSpecifique(chambre,client,dateDebut,dateFin);
            if (reservation==null) throw new IFT287Exception("pas de reervation avec ces criteres");
            collectionReservation.supprimerReservation(idChambre,idClient,dateDebut,dateFin);
        } catch (Exception e)
        {
            throw e;
        }
    }


    /**
     * Liste toutes les réservations d'un client donné par son ID.
     */
    public List<Reservation> listerReservationsClient(int idClient) throws IFT287Exception {
        List<Reservation> reservationsClient = new ArrayList<>();
        try {
            // Vérifier que le client existe
            Client client = collectionClient.getClientById(idClient);
            if (client == null) {
                throw new IFT287Exception("Client inexistant: " + idClient);
            }

            // Récupérer toutes les réservations du client
            reservationsClient = collectionReservation.getReservationsByClientId(client.getidClient());

        } catch (Exception e) {
            throw new IFT287Exception("Erreur lors de la récupération des réservations pour le client " + idClient + ": " + e.getMessage());
        }

        return reservationsClient;
    }
}






