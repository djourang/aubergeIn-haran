package AubergeInn.collections;

import AubergeInn.tuples.LigneClient;
import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableClient {

    private final Connexion connexion;
    PreparedStatement stmtInsertClient;
    PreparedStatement stmtDeleteClient;
    PreparedStatement stmtExiste;
    PreparedStatement stmtClientsAvecReservations;


    public TableClient(Connexion connexion) throws SQLException {
        this.connexion = connexion;
        initStatement();
    }

    public void ajouterClient(LigneClient client) throws SQLException {
        stmtInsertClient.setInt(1, client.getidClient());
        stmtInsertClient.setString(2, client.getnom());
        stmtInsertClient.setString(3, client.getprenom());
        stmtInsertClient.setInt(4, client.getage());
        stmtInsertClient.executeUpdate();
    }
    public Connexion getConnexion() {

        return connexion;

    }
    public boolean existe(int idClient) throws SQLException {
        stmtExiste = connexion.getConnection().prepareStatement("select * from Client where idclient = ?");
        stmtExiste.setInt(1,idClient);
        ResultSet r = stmtExiste.executeQuery();
        boolean ClientExiste = r.next();
        r.close();
        return ClientExiste;
    }

    public void supprimerClient(LigneClient client) throws SQLException {
        stmtDeleteClient.setInt(1,client.getidClient());
        stmtDeleteClient.executeUpdate();
    }

    public void initStatement() throws SQLException {
        stmtInsertClient =  this.connexion.getConnection().prepareStatement("insert into client (idClient, nom, prenom, age) "+ "values (?,?,?,?)");
        stmtDeleteClient = this.connexion.getConnection().prepareStatement("delete from client where idclient = ?");
//        stmtSelectClient = this.connexion.getConnection().prepareStatement("select * from chambre");
        stmtClientsAvecReservations = connexion.getConnection().prepareStatement(
                "SELECT DISTINCT c.idClient, c.nom, c.prenom " +
                        "FROM client c JOIN reservationChambre r ON c.idClient = r.idClient"
        );

    }

    public LigneClient afficherClient(int idClient) throws SQLException, IFT287Exception {
        LigneClient tupleClient = getClient(idClient);
        if (tupleClient == null)
            throw new IFT287Exception("Client inexistant: " + idClient);
        else
            return tupleClient;
    }

    public LigneClient getClient(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            return new LigneClient(
                    (rset.getInt(1)),
                    (rset.getString(2)),
                    (rset.getString(3)),
                    (rset.getInt(4)));
        }
        else
            return null;
    }
    public List<LigneClient> getClientsAvecReservations() throws SQLException {
        List<LigneClient> result = new ArrayList<>();
        ResultSet rs = stmtClientsAvecReservations.executeQuery();
        while (rs.next()) {
            result.add(new LigneClient( rs.getInt("idClient"),  rs.getString("nom"), rs.getString("prenom"), rs.getInt("age")));
        }
        rs.close();
        return result;
    }





}
