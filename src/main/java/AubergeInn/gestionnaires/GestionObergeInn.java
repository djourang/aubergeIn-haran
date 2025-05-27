package AubergeInn.gestionnaires;

import AubergeInn.collections.*;
import AubergeInn.utils.Connexion;

import java.sql.SQLException;


public class GestionObergeInn {

    Connexion connexion;

    private final TableClient collectionClient;
    private final TableChambre collectionChambre;
    private final TableCommodite collectionCommodite;
    private  final TableReservationChambre collectionReservation;
    private final TableInclusionCommodite collectionInclusionCommodite;
    //private  final CollectionMembre collectionMembre;

    private GestionClient gestionClient;
    private GestionChambre gestionChambre;
    private GestionCommodite gestionCommodite;
    private GestionReservation gestionReservationChambre;
    //private GestionMembre gestionMembre;



    public GestionObergeInn() throws Exception {
            this.connexion = new Connexion();
            this.collectionClient = new TableClient(connexion);
            this.collectionChambre = new TableChambre(connexion);
            this.collectionCommodite = new TableCommodite(connexion);
            this.collectionReservation = new TableReservationChambre(connexion);
            this.collectionInclusionCommodite = new TableInclusionCommodite(connexion);

        //this.collectionMembre = new CollectionMembre(connexion);

            this.setGestionClient(new GestionClient(collectionChambre, collectionClient, collectionReservation) );
            this.setGestionChambre(new GestionChambre(collectionChambre, collectionReservation, collectionInclusionCommodite,connexion ));

            this.setGestionCommodite(new GestionCommodite(collectionCommodite, collectionChambre,collectionInclusionCommodite) );
            this.setGestionReservation(new GestionReservation(collectionReservation, collectionClient, collectionChambre) );
           // this.setGestionMembre(new GestionMembre(collectionMembre,collectionChambre,collectionReservation) );

    }




//    private void setGestionMembre(GestionMembre gestionMembre) {
//        this.gestionMembre=gestionMembre;
//    }


    public Connexion getConnexion() {
        return connexion;
    }

    public GestionClient getGestionClient() {
        return gestionClient;
    }

    public GestionChambre getGestionChambre() {
        return gestionChambre;
    }

    public GestionCommodite getGestionCommodite() {
        return gestionCommodite;
    }

    public GestionReservation getGestionReservation() {
        return gestionReservationChambre;
    }




    private void setGestionReservation(GestionReservation gestionReservationChambre) {
        this.gestionReservationChambre = gestionReservationChambre;
    }

    private void setGestionCommodite(GestionCommodite gestionCommodite) {
        this.gestionCommodite = gestionCommodite;
    }

    private void setGestionChambre(GestionChambre gestionChambre) {
        this.gestionChambre = gestionChambre;
    }

    private void setGestionClient(GestionClient gestionClient) {
        this.gestionClient = gestionClient;
    }

    public void fermer() throws SQLException {
        // Fermeture de la connexion
        connexion.fermer();
    }


//    public GestionMembre getGestionMembre() {
//        return gestionMembre;
//    }
}
