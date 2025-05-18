package AubergeInn.collections;

import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Client;
import AubergeInn.tuples.Reservation;
import AubergeInn.utils.Connexion;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class CollectionReservation {

    private final Connexion connexion;
    private MongoCollection<Document> reservationCollection;
    private MongoCollection<Document> chambreCollection;

    public CollectionReservation(Connexion connexion)  {
        this.connexion = connexion;
        reservationCollection = connexion.getDatabase().getCollection("reservation");
        chambreCollection = connexion.getDatabase().getCollection("chambre");
    }
    public Connexion getConnexion() {
        return this.connexion;
    }
    public boolean clientSansResrvation(Client client) {
        // return false si not found
        List<Reservation> r = getReservationsByClientId(client.getidClient());
        return r.isEmpty();
    }

    public boolean chambreEstResrever(Chambre chambre) {
        // return false si not found
        return getReservationsByChambreId(chambre.getidChambre()).isEmpty();
    }
    public List<Chambre> afficherChambresLibres(Date dateDebut, Date dateFin) {
        List<Chambre> chambresLibres = new ArrayList<>();
        for (Document doc : stmtToutesLesChambres()) {
            Chambre chambre = new Chambre(doc);
            if (!estReserver(chambre, dateDebut, dateFin)) {
                chambresLibres.add(chambre);
            }
        }

        return chambresLibres;
    }
    // obtenir les reservations relier a une chambre
    public void reserver(Reservation reservation) {
        /* Ajout de la reservation */
        reservationCollection.insertOne(reservation.toDocument());

    }
    public Reservation getReservationSpecifique(Chambre chambre, Client client, Date dateDebut, Date dateFin) {

        // Creer un filtre pour les criteres
        Document Specification = new Document()
                .append("clientId", client.getidClient())
                .append("chambreId", chambre.getidChambre())
                .append("dateDebut", dateDebut)
                .append("dateFin", dateFin);
        Document reservationDoc = reservationCollection.find(Specification).first();
        if (reservationDoc != null) {
            return new Reservation(reservationDoc);
        } else {
            return null;
        }
    }
    public ArrayList<Document> stmtreservationsChevauchantes(Date dateDebut, Date dateFin) {

        return this.reservationCollection.find(
                and(
                        lt("dateDebut", dateFin),
                        gt("dateFin", dateDebut)
                )
        ).into(new ArrayList<>());
    }
    public List<Document> stmtToutesLesChambres() {
        return chambreCollection.find().into(new ArrayList<>());
    }
    public void supprimerReservation(int idChambre, int idClient, Date dateDebut, Date dateFin) {
        // Supprimer le document correspondant
        reservationCollection.deleteOne(new Document()
                .append("chambreId", idChambre)
                .append("clientId", idClient)
                .append("dateDebut", dateDebut)
                .append("dateFin", dateFin));
    }


    public boolean estReserver(Chambre chambre, java.util.Date dateDebut, java.util.Date dateFin) {
        List<Reservation> reservationOfChambre = getReservationsByChambreId(chambre.getidChambre());
        if (reservationOfChambre.size() > 0)
        {
            for (Reservation res : reservationOfChambre)
            {
                if (dateDebut.before(res.getdateFin()) && res.getdateDebut().before(dateFin))return true;
            }
            return false;
        }
        return false;
    }
    public List<Reservation> getReservationsByChambreId(int idChambre) {
        List<Reservation> reservations = new ArrayList<>();
        Document query = new Document("chambre.idChambre", idChambre);
        List<Document> documents = reservationCollection.find(query).into(new ArrayList<>());
        for (Document doc : documents) {
            reservations.add(new Reservation(doc));
        }
        return reservations;
    }
    public List<Reservation> getReservationsByClientId(int idClient) {
        List<Reservation> reservations = new ArrayList<>();
        Document query = new Document("client.idClient", idClient);
        List<Document> documents = reservationCollection.find(query).into(new ArrayList<>());
        for (Document doc : documents) {
            reservations.add(new Reservation(doc));
        }
        return reservations;
    }


}
