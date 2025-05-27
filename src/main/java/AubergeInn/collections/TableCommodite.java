package AubergeInn.collections;

import AubergeInn.tuples.LigneCommodite;
import AubergeInn.utils.Connexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableCommodite {

    Connexion connexion;
    PreparedStatement stmtExiste;
    PreparedStatement stmAjouterCommodite;
    PreparedStatement stmtGetCommoditeById;
    PreparedStatement stmtSupprimerCommodite;




    public TableCommodite(Connexion connexion) throws SQLException {
        this.connexion = connexion;
        initStmts();
    }

    private void initStmts() throws SQLException {
        stmtExiste = connexion.getConnection().prepareStatement("select from commodite where idCommodite = ? ");
        stmAjouterCommodite = connexion.getConnection().prepareStatement("insert  into commodite(idCommodite,description,surplusPrix)" + "values (?,?,?)");
        stmtGetCommoditeById = connexion.getConnection().prepareStatement(
                "SELECT * FROM commodite WHERE idCommodite = ?"
        );
        stmtSupprimerCommodite = connexion.getConnection().prepareStatement(
                "DELETE FROM commodite WHERE idCommodite = ?"
        );

    }

    public boolean existe(int idCommodite) throws SQLException {
        stmtExiste.setInt(1,idCommodite);
        ResultSet r = stmtExiste.executeQuery();
        boolean commoditeExiste = r.next();
        r.close();
        return commoditeExiste;
    }

    public void ajouterCommodite(LigneCommodite commodite) throws SQLException {
        stmAjouterCommodite.setInt(1,commodite.getidCommodite());
        stmAjouterCommodite.setString(2,commodite.getdescription());
        stmAjouterCommodite.setDouble(3,commodite.getSurplusPrix());
        stmAjouterCommodite.executeUpdate();
    }
    public LigneCommodite getCommodite(int idCommodite) throws SQLException {
        stmtGetCommoditeById.setInt(1, idCommodite);
        ResultSet rs = stmtGetCommoditeById.executeQuery();
        LigneCommodite commodite = null;
        if (rs.next()) {
            commodite = new LigneCommodite(
                    rs.getInt("idCommodite"),
                    rs.getString("description"),
                    rs.getFloat("surplusPrix")
            );
        }
        rs.close();
        return commodite;
    }

    public Connexion getConnexion() {
        return connexion;
    }

    public void supprimerCommodite(LigneCommodite commoditeById) throws SQLException {
        stmtSupprimerCommodite.setInt(1, commoditeById.getidCommodite());
        stmtSupprimerCommodite.executeUpdate();
    }

}
