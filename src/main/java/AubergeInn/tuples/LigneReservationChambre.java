package AubergeInn.tuples;

import java.sql.Date;

public class LigneReservationChambre {
    private int idClient;
    private int idChambre;
    private double prixTotal;
    private java.util.Date dateDebut;
    private java.util.Date dateFin;



    public LigneReservationChambre(int idClient, int idChambre, double prixTotal, java.util.Date dateDebut, java.util.Date dateFin) {
        this.idClient = idClient;
        this.idChambre = idChambre;
        this.prixTotal = prixTotal;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }




    public java.util.Date getDateFin() {
        return dateFin;
    }


    public java.util.Date getDateDebut() {
        return dateDebut;
    }

    public int getidClient() {
        return idClient;
    }

    public int getidChambre() {
        return idChambre;

    }

    public java.util.Date getdateDebut() {
        return dateDebut;
    }

    public java.util.Date getdateFin() {
        return dateFin;
    }
}
