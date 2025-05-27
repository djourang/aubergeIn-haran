package AubergeInn.gestionnaires;


import AubergeInn.collections.TableChambre;
import AubergeInn.collections.TableCommodite;
import AubergeInn.collections.TableInclusionCommodite;
import AubergeInn.collections.TableReservationChambre;
import AubergeInn.tuples.LigneChambre;
import AubergeInn.utils.IFT287Exception;
import AubergeInn.utils.Connexion;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class GestionChambre {


    private final TableChambre tableChambre;
    private final TableReservationChambre tableReservationChambre;
    private final TableInclusionCommodite tableInclusionCommodite;

    private final Connexion connexion;


    public GestionChambre(TableChambre tableChambre, TableReservationChambre tableReservationChambre, TableInclusionCommodite tableInclusionCommodite, Connexion connexion) {
        this.connexion = connexion;
        this.tableChambre = tableChambre;
        this.tableReservationChambre= tableReservationChambre;
        this.tableInclusionCommodite = tableInclusionCommodite;
    }

    /*
        Cette transaction ajoute une nouvelle chambre au système.
    */
    public void ajouterChambre(int idChambre, String nomChambre, String typeLit, double prixBase) throws SQLException, IFT287Exception {

        try {
            // verifier si la chambre existe deja
            if (tableChambre.existe(idChambre)){throw new IFT287Exception("la chambre "+ idChambre + " existe deja");}
            //− ajouterChambre <idChambre> <nomChambre> <type de lit> <prix de base>
            tableChambre.ajouterChambre(idChambre,nomChambre,typeLit,prixBase);
            connexion.commit();
        }catch (Exception e){
            connexion.rollback();
            throw e;
        }

    }


    /*
    Cette transaction affiche les informations sur une chambre, incluant les commodités offertes.
     */
    public LigneChambre afficherChambre(int idChambre) throws SQLException, IFT287Exception {
        LigneChambre chambre ;
        try {
            // Vérifie que le client existe bien
            if (!tableChambre.existe(idChambre))
                throw new IFT287Exception("La chambre : " + idChambre + " n'existe pas.");
            chambre = tableChambre.afficherClient(idChambre);
            // Commit
            connexion.commit();
        } catch (Exception e) {
            connexion.rollback();
            throw e;
        }
        return chambre;
    }

    //    public void afficherChambresLibres(Date dateDebut, Date dateFin) throws SQLException, ParseException, IFT287Exception {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
//
//        try {
//            //verifier si la date de debut > a la date de fin
//            if (!dateDebut.before(dateFin)) throw new IFT287Exception("La date de debut ["+ dateDebut + "] est avant la date de fin ["+dateFin+"]");
//            //verifier que la date de debut n'eset pas une date passer
//            Date currentDate = new Date();
//            if (dateFin.after(currentDate)) throw new IFT287Exception("La date de debut "+ dateDebut + " est une date passer "+dateFin);
//            tableReservationChambre.afficherChambresLibres(new java.sql.Date(dateDebut.getTime()),new java.sql.Date(dateFin.getTime()));
//            connexion.commit();
//        }catch (Exception e){
//            connexion.rollback();
//            throw e;
//        }
//
//    }
    /*
    Cette transaction affiche toutes les chambres qui sont disponibles entre ces 2 dates. La date de
    début est celle d'arrivée, et la date de fin est celle de départ (ex. une chambre libre du 29 au
    30 mars est libre la nuit entre le 29 et le 30 mars, et ne l'est pas nécessairement le 30 au soir).
    L'affichage doit inclure le prix de location de la chambre (prix de base, plus les commodités).
     */
    public List<LigneChambre> afficherChambresLibres(Date dateDebut, Date dateFin) throws Exception
    {
        try
        {
            //verifier si la date de debut > a la date de fin
            if (!dateDebut.before(dateFin)) throw new IFT287Exception("La date de debut ["+ dateDebut + "] est avant la date de fin ["+dateFin+"]");
            //verifier que la date de debut n'eset pas une date passer
            Date currentDate = new Date();
            if (dateFin.after(currentDate)) throw new IFT287Exception("La date de debut "+ dateDebut + " est une date passer "+dateFin);
            tableReservationChambre.afficherChambresLibres(new java.sql.Date(dateDebut.getTime()),new java.sql.Date(dateFin.getTime()));
        }
        catch (Exception e)
        {
            connexion.rollback();
            throw e;
        }
        return List.of();
    }

    /*
  Cette transaction supprime une chambre si elle n'est pas réservée et n'a pas de réservation future.
   */
    public void supprimerChambre(int idChambre) throws SQLException, IFT287Exception {
        try
        {
            // Vérifie si la chambre existe
            if (!tableChambre.existe(idChambre))throw new IFT287Exception("la Chambre : " + idChambre + " n'existe pas.");
            //verifier si la chambre inclut une commodite
            if (tableInclusionCommodite.chambreAvecCommodite(idChambre))throw new IFT287Exception("pour supprimer la chmabre assurez-vous qu'il n'inclut aucune Commodite");
            // verifier si la chambre est deja reserver par un client ?
            if (tableReservationChambre.chambreEstReserver(idChambre)){throw new IFT287Exception("pour supprimer la chmabre assurez-vous qu'il n'est reserver par aucun Client");}

            // suprimer le client de la table des Clients
            tableChambre.supprimerChambre(idChambre);
            // Commit
            connexion.commit();
        }
        catch (Exception e)
        {
            connexion.rollback();
            throw  e;
        }
    }


}