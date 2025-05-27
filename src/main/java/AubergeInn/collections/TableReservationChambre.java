package AubergeInn.collections;
import AubergeInn.tuples.LigneChambre;
import AubergeInn.tuples.LigneClient;
import AubergeInn.tuples.LigneReservationChambre;
import AubergeInn.utils.Connexion;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableReservationChambre {

    private final Connexion connexion;
    PreparedStatement stmtClientAvecResrvation;
    PreparedStatement stmtChambreEstReserver;
    PreparedStatement stmtChambreLibre;
    PreparedStatement stmtSelectChById;
    PreparedStatement stmtInsertIntoReservation;
    PreparedStatement stmtChambreEstReserverPeriode;
    PreparedStatement stmtReservationSpecifique;
    PreparedStatement stmtSupprimerReservation;






    public TableReservationChambre(Connexion connexion) throws SQLException {
        this.connexion = connexion;
        initStmt();
    }
    public Connexion getConnexion() {
        return this.connexion;
    }


    public void initStmt() throws SQLException {
        stmtClientAvecResrvation = connexion.getConnection().prepareStatement("select * from reservationChambre where idClient = ?");
        stmtChambreEstReserver = connexion.getConnection().prepareStatement("select * from reservationChambre where idChambre = ?");
        stmtChambreLibre = connexion.getConnection().prepareStatement("SELECT DISTINCT c.idChambre, c.nomChambre, c.typeLit, c.prixBase + COALESCE(SUM(co.surplusPrix), 0) AS prixTotal, c.prixBase, co.surplusPrix FROM Chambre c LEFT JOIN reservationChambre rc ON c.idChambre = rc.idChambre LEFT JOIN InclusionCommodite ic ON c.idChambre = ic.idChambre LEFT JOIN Commodite co ON ic.idCommodite = co.idCommodite WHERE NOT EXISTS ( SELECT 1 FROM reservationChambre WHERE c.idChambre = reservationChambre.idChambre AND dateDebut <= ? AND dateFin >= ?) GROUP BY c.idChambre, c.nomChambre, c.typeLit, c.prixBase, co.surplusPrix");
        stmtSelectChById = connexion.getConnection()
                .prepareStatement("select * from reservationChambre" +
                        " where idChambre = ? " + "order by dateDebut");
        stmtInsertIntoReservation = connexion.getConnection()
                .prepareStatement("insert into reservationChambre (idClient, idChambre, dateDebut, dateFin) "
                        + "values (?,?,?,?)");
        stmtChambreEstReserverPeriode = connexion.getConnection().prepareStatement(
                "SELECT * FROM reservationChambre " +
                        "WHERE idChambre = ? AND dateDebut <= ? AND dateFin >= ?"
        );

        stmtReservationSpecifique = connexion.getConnection().prepareStatement(
                "SELECT * FROM reservationChambre " +
                        "WHERE idChambre = ? AND idClient = ? AND dateDebut = ? AND dateFin = ?"
        );

        stmtSupprimerReservation = connexion.getConnection().prepareStatement(
                "DELETE FROM reservationChambre WHERE idChambre = ? AND idClient = ? AND dateDebut = ? AND dateFin = ?"
        );



    }

    public boolean chambreEstReserver(int idChambre) throws SQLException {
        stmtChambreEstReserver.setInt(1, idChambre);
        ResultSet r = stmtChambreEstReserver.executeQuery();
        boolean chambreEstReserver = r.next();
        r.close();
        return chambreEstReserver;
    }

    public boolean clientAvecResrvation(int idClient) throws SQLException {
        stmtClientAvecResrvation.setInt(1, idClient);
        ResultSet r = stmtClientAvecResrvation.executeQuery();
        boolean clientAvecResrvation = r.next();
        r.close();
        return clientAvecResrvation;
    }


//    public void afficherChambresLibres(Date dateDebut, Date dateFin) throws SQLException {
//
//        // Définir les paramètres de la requête
//        stmtChambreLibre.setDate(1, dateFin);
//        stmtChambreLibre.setDate(2, dateDebut);
//        ResultSet rs = stmtChambreLibre.executeQuery();
//
//            // Traitement des résultats
//            while (rs.next()) {
//                int idChambre = rs.getInt("idChambre");
//                String nomChambre = rs.getString("nomChambre");
//                double prixBase = rs.getDouble("prixBase");
//                double surplusPrix = rs.getDouble("surplusPrix");

//                double prixTotal = prixBase + surplusPrix;
//
//                System.out.println("\nChambre ID: " + idChambre);
//                System.out.println("Nom Chambre: " + nomChambre);
//                System.out.println("Prix de Base: " + prixBase);
//                System.out.println("Prix de s Commodites: " + surplusPrix);
//                System.out.println("Prix Total: " + prixTotal);
//                System.out.println("---------------------------");
//        }
//        rs.close();
//
//    }

    public List<LigneChambre> afficherChambresLibres(Date dateDebut, Date dateFin) throws SQLException {
        // Définir les paramètres de la requête
        stmtChambreLibre.setDate(1, dateFin);
        stmtChambreLibre.setDate(2, dateDebut);
        ResultSet rset = stmtChambreLibre.executeQuery();
        List<LigneChambre> listeChambres = new LinkedList<>();
        while (rset.next()) {
            LigneChambre tupleChambre = new LigneChambre(
                    rset.getInt(1),
                    rset.getString(2),
                    rset.getString(3),
                    rset.getFloat(4));

            listeChambres.add(tupleChambre);
        }
        rset.close();
        if (!(listeChambres.size() > 0))
            System.out.println("Aucune chambre libre");
        for (LigneChambre ch : listeChambres) {
            ch.afficher();
        }
        return listeChambres;
    }
    public List<LigneReservationChambre> getReservationChambre(int idChambre) throws SQLException {
        stmtSelectChById.setInt(1, idChambre);
        ResultSet rset = stmtSelectChById.executeQuery();
        List<LigneReservationChambre> listReserv = new LinkedList<>();
        while (rset.next())
        {
            LigneReservationChambre tupleReservation = new LigneReservationChambre(
                    rset.getInt(1),
                    rset.getInt(2),
                    rset.getFloat(3),
                    rset.getDate(4),
                    rset.getDate(5));

            listReserv.add(tupleReservation);
        }
        rset.close();
        return listReserv;
    }
    public void reserver(LigneReservationChambre reservation) throws SQLException {
        /* Ajout de la reservation */
        stmtInsertIntoReservation.setInt(1, reservation.getidClient());
        stmtInsertIntoReservation.setInt(2, reservation.getidChambre());
        stmtInsertIntoReservation.setDate(3, (Date) reservation.getdateDebut());
        stmtInsertIntoReservation.setDate(4, (Date) reservation.getdateFin());
        stmtInsertIntoReservation.executeUpdate();
    }
    public boolean chambreEstReserverPeriode(int idChambre, java.util.Date debut, java.util.Date fin) throws SQLException {

        stmtChambreEstReserverPeriode.setInt(1, idChambre);
        stmtChambreEstReserverPeriode.setDate(2, (Date) debut);   // dateDebut <= fin
        stmtChambreEstReserverPeriode.setDate(3, (Date) fin); // dateFin >= debut

        ResultSet r = stmtChambreEstReserverPeriode.executeQuery();
        boolean estReservee = r.next();
        r.close();
        return estReservee;
    }
    public LigneReservationChambre getReservationSpecifique(LigneChambre chambre, LigneClient client, Date dateDebut, Date dateFin) throws SQLException {
        stmtReservationSpecifique.setInt(1, chambre.getIdChambre());
        stmtReservationSpecifique.setInt(2, client.getidClient());
        stmtReservationSpecifique.setDate(3, dateDebut);
        stmtReservationSpecifique.setDate(4, dateFin);

        ResultSet rs = stmtReservationSpecifique.executeQuery();
        LigneReservationChambre reservation = null;

        if (rs.next()) {
            reservation = new LigneReservationChambre(
                    rs.getInt("idClient"),
                    rs.getInt("idChambre"),
                    rs.getFloat("prix"),
                    rs.getDate("dateDebut"),
                    rs.getDate("dateFin")
            );
        }

        rs.close();
        return reservation;
    }
    public List<LigneReservationChambre> getReservationsByClientId(LigneClient client) throws SQLException {
        List<LigneReservationChambre> reservations = new LinkedList<>();

        stmtClientAvecResrvation.setInt(1, client.getidClient());
        ResultSet rs = stmtClientAvecResrvation.executeQuery();

        while (rs.next()) {
            LigneReservationChambre reservation = new LigneReservationChambre(
                    rs.getInt("idClient"),
                    rs.getInt("idChambre"),
                    rs.getFloat("prix"),
                    rs.getDate("dateDebut"),
                    rs.getDate("dateFin")
            );
            reservations.add(reservation);
        }

        rs.close();
        return reservations;
    }

    public void supprimerReservation(int idChambre, int idClient, Date dateDebut, Date dateFin) throws SQLException {
        stmtSupprimerReservation.setInt(1, idChambre);
        stmtSupprimerReservation.setInt(2, idClient);
        stmtSupprimerReservation.setDate(3, dateDebut);
        stmtSupprimerReservation.setDate(4, dateFin);
        stmtSupprimerReservation.executeUpdate();
    }

    public boolean clientSansResrvation(LigneClient client) {
        try {
            List<LigneReservationChambre> reservations = getReservationsByClientId(client);
            return reservations.isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
