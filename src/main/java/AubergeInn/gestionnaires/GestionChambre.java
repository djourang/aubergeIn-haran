package AubergeInn.gestionnaires;


import AubergeInn.collections.CollectionChambre;
import AubergeInn.collections.CollectionCommodite;
import AubergeInn.collections.CollectionReservation;
import AubergeInn.tuples.Chambre;
import AubergeInn.utils.IFT287Exception;

import java.util.Date;
import java.util.List;


public class GestionChambre {

    private final CollectionChambre chambres;
    private final CollectionReservation reservations;


    public GestionChambre(CollectionChambre collectionChambre, CollectionReservation collectionReservation, CollectionCommodite collectionCommodite) {
        this.chambres = collectionChambre;
        this.reservations = collectionReservation;
    }

    /*
        Cette transaction ajoute une nouvelle chambre au système.
    */
    public void ajouterChambre(int idChambre, String nomChambre, String typeLit, double prixBase) throws IFT287Exception {
        try {
            // verifier si la chambre existe deja
            if (!(chambres.getChambreById(idChambre) == null)) throw new IFT287Exception("la chambre " + idChambre + " existe deja");
            //ajouterChambre <idChambre> <nomChambre> <type de lit> <prix de base>
            Chambre chambre = new Chambre(idChambre, nomChambre, typeLit, prixBase);
            chambres.ajouterChambre(chambre);
        } catch (Exception e) {
            throw e;
        }
    }

    /*
    Cette transaction affiche les informations sur une chambre, incluant les commodités offertes.
     */
    public Chambre afficherChambre(int idChambre) throws IFT287Exception {
        try {
            // Vérifie que le client existe bien
            if ((chambres.getChambreById(idChambre) == null))throw new IFT287Exception("La chambre : " + idChambre + " n'existe pas.");
            return chambres.afficherChambre(idChambre);
            // Commit
        } catch (Exception e) {
            throw e;
        }
    }
    /*
    Cette transaction affiche toutes les chambres qui sont disponibles entre ces 2 dates. La date de
    début est celle d'arrivée, et la date de fin est celle de départ (ex. une chambre libre du 29 au
    30 mars est libre la nuit entre le 29 et le 30 mars, et ne l'est pas nécessairement le 30 au soir).
    L'affichage doit inclure le prix de location de la chambre (prix de base, plus les commodités).
     */
    public List<Chambre> afficherChambresLibres(Date dateDebut, Date dateFin) throws Exception {
        try {

            //verifier si la date de debut est superieure a la date de fin
            if (!dateDebut.before(dateFin))throw new IFT287Exception("La date de debut [" + dateDebut + "] est avant la date de fin [" + dateFin + "]");
            //verifier que la date de debut n'est pas une date passer
            Date currentDate = new Date();
            //if (dateDebut.before(currentDate) || dateFin.before(currentDate)) {throw new IFT287Exception("La date de debut " + dateDebut + " est une date passer " + dateFin);}

            return reservations.afficherChambresLibres(new java.sql.Date(dateDebut.getTime()), new java.sql.Date(dateFin.getTime()));


        } catch (Exception e) {

            throw e;
        }
    }
    /*
  Cette transaction supprime une chambre si elle n'est pas réservée et n'a pas de réservation future.
   */
    public void supprimerChambre(int idChambre) throws IFT287Exception {
        try {
            Chambre chambre = chambres.getChambreById(idChambre);
            // Vérifie si la chambre existe
            if ((chambre == null))throw new IFT287Exception("la Chambre : " + idChambre + " n'existe pas.");
            //if (!(chambres.getChambreById(idChambre).estAccommoder()))throw new IFT287Exception("pour supprimer la chmabre " + idChambre + " assurez-vous qu'il n'inclut aucune Commodite");
            // verifier si la chambre est deja reserver par un client ?
            //if (!chambres.getChambreById(idChambre).chambreEstReserver()) throw new IFT287Exception("pour supprimer la chmabre assurez-vous qu'il n'est reserver par aucun Client");

            // verifier si cette chambre n'est pas lier a une reservation
            if (!reservations.chambreEstResrever(chambre)){throw new IFT287Exception("La chambre : " + idChambre + " est associer a une ou plusieur reservation en cours");}

            // suprimer le client de la table des Clients
            chambres.supprimerChambre(chambre);
            // Commit
        } catch (Exception e) {

            throw e;
        }
    }
}



