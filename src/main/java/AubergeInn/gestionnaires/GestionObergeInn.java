package AubergeInn.gestionnaires;

import AubergeInn.collections.*;
import AubergeInn.utils.Connexion;


public class GestionObergeInn {

    Connexion connexion;
    private final CollectionClient collectionClient;
    private final CollectionChambre collectionChambre;
    private final CollectionCommodite collectionCommodite;
    private  final CollectionReservation collectionReservation;
    private  final CollectionMembre collectionMembre;

    private GestionClient gestionClient;
    private GestionChambre gestionChambre;
    private GestionCommodite gestionCommodite;
    private GestionReservation gestionReservationChambre;
    private GestionMembre gestionMembre;



    public GestionObergeInn(String srv, String bd, String user, String ps) throws Exception {
        this.connexion = new Connexion(srv,bd,user,ps);
            this.collectionClient = new CollectionClient(connexion);
            this.collectionChambre = new CollectionChambre(connexion);
            this.collectionCommodite = new CollectionCommodite(connexion);
            this.collectionReservation = new CollectionReservation(connexion);
            this.collectionMembre = new CollectionMembre(connexion);

            this.setGestionClient(new GestionClient(collectionChambre, collectionClient, collectionReservation) );
            this.setGestionChambre(new GestionChambre(collectionChambre, collectionReservation, collectionCommodite ));
            this.setGestionCommodite(new GestionCommodite(collectionCommodite, collectionChambre) );
            this.setGestionReservation(new GestionReservation(collectionReservation, collectionClient, collectionChambre) );
            this.setGestionMembre(new GestionMembre(collectionMembre,collectionChambre,collectionReservation) );

    }

    private void setGestionMembre(GestionMembre gestionMembre) {
        this.gestionMembre=gestionMembre;
    }


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

    public void fermer()
    {
        // Fermeture de la connexion
        connexion.fermer();
    }


    public GestionMembre getGestionMembre() {
        return gestionMembre;
    }
}
