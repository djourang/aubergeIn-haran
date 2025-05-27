package AubergeInn.collections;

import AubergeInn.utils.Connexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableInclusionCommodite {

    Connexion connexion;
    PreparedStatement stmtExist;
    PreparedStatement stmtInclusionCommodite;
    PreparedStatement stmtEnleverCommodite;
    PreparedStatement stmtChambreAvecCommodite;

    public TableInclusionCommodite(Connexion connexion) throws SQLException {
        this.connexion = connexion;
        initStmts();
    }

    private void initStmts() throws SQLException {
        stmtExist = connexion.getConnection().prepareStatement("select * from inclusionCommodite where idChambre = ? and idCommodite = ?") ;
        stmtInclusionCommodite = connexion.getConnection().prepareStatement("insert into inclusionCommodite(idChambre,idCommodite)" + "values (?,?)");
        stmtEnleverCommodite = connexion.getConnection().prepareStatement("delete from inclusioncommodite where idChambre = ? and idCommodite=?");
        stmtChambreAvecCommodite = connexion.getConnection().prepareStatement("select * from inclusionCommodite where idChambre = ?") ;

    }
    public boolean existe(int idChambre, int idCommodite) throws SQLException {
        stmtExist.setInt(1,idChambre);
        stmtExist.setInt(2,idCommodite);
        ResultSet r = stmtExist.executeQuery();
        boolean chambreIncluCommodite = r.next();
        r.close();
        return chambreIncluCommodite;
    }

    public void inclureCommodite(int idChambre, int idCommodite) throws SQLException {
        stmtInclusionCommodite.setInt(1,idChambre);
        stmtInclusionCommodite.setInt(2,idCommodite);
        stmtInclusionCommodite.executeUpdate();
    }
    public void enleverCommodite(int idChambre, int idCommodite) throws SQLException {
        stmtEnleverCommodite.setInt(1,idChambre);
        stmtEnleverCommodite.setInt(2,idCommodite);
        stmtEnleverCommodite.executeUpdate();
    }
    public Connexion getConnexion() {
        return connexion;
    }


    public boolean chambreAvecCommodite(int idChambre) throws SQLException {
        stmtChambreAvecCommodite.setInt(1,idChambre);
        ResultSet r = stmtChambreAvecCommodite.executeQuery();
        boolean chambreAvecCommodite = r.next();
        r.close();
        return chambreAvecCommodite;
    }
}
