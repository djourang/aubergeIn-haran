package AubergeInn.collections;
import AubergeInn.tuples.LigneChambre;
import AubergeInn.tuples.LigneCommodite;
import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//Chambre : Une chambre peut avoir zero ou plusieurs commodités.
public class TableChambre {

    Connexion connexion;
    PreparedStatement stmtExiste;
    PreparedStatement stmtAjouterChambre;
    PreparedStatement stmtSuprimerChambre;

    public TableChambre(Connexion connexion) throws SQLException {
        this.connexion = connexion;
        initStmt();
    }

    private void initStmt() throws SQLException {
        stmtExiste = connexion.getConnection().prepareStatement("select * from chambre where idchambre = ?");
        stmtAjouterChambre = connexion.getConnection().prepareStatement("insert into chambre(idchambre,nomChambre,typeLit,prixBase) values (?,?,?,?)");
        stmtSuprimerChambre = connexion.getConnection().prepareStatement("delete from  chambre where  idchambre = ?");

    }

    public void ajouterChambre(int idChambre, String nomChambre, String typeLit, double prixBase) throws SQLException {
        stmtAjouterChambre.setInt(1, idChambre);
        stmtAjouterChambre.setString(2, nomChambre);
        stmtAjouterChambre.setString(3, typeLit);
        stmtAjouterChambre.setDouble(4, prixBase);
        stmtAjouterChambre.executeUpdate();
    }

    public boolean existe(int idChambre) throws SQLException {
        stmtExiste.setInt(1, idChambre);
        ResultSet r = stmtExiste.executeQuery();
        boolean chambreExiste = r.next();
        r.close();
        return chambreExiste;
    }

    public void supprimerChambre(int idChambre) throws SQLException {
        stmtSuprimerChambre.setInt(1,idChambre);
        stmtSuprimerChambre.executeUpdate();
    }


    public LigneChambre afficherClient(int idChambre) throws IFT287Exception, SQLException {
        LigneChambre ligneChambre = getChambre(idChambre);
        if (ligneChambre == null)
            throw new IFT287Exception("Client inexistant: " + idChambre);
        else
            ligneChambre.tostring();

        return ligneChambre;
    }

    public LigneChambre getChambre(int idChambre) throws SQLException {

        stmtExiste.setInt(1, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            return new LigneChambre(idChambre,rset.getString(2),rset.getString(3),rset.getFloat(4));
        }
        else
            return null;

    }


    public void inclureCommodite(LigneChambre chambre, LigneCommodite commodite) throws SQLException {
        int idChambre = chambre.getIdChambre();
        int idCommodite = commodite.getidCommodite();
    }


    public void enleverCommodite(LigneCommodite commodite, LigneChambre chambre, TableInclusionCommodite tableInclusion) throws SQLException {
        int idChambre = chambre.getIdChambre();
        int idCommodite = commodite.getidCommodite();

        // Vérifie si la commodité est effectivement liée à cette chambre
        if (tableInclusion.existe(idChambre, idCommodite)) {
            // Supprimer dans la base de données
            tableInclusion.enleverCommodite(idChambre, idCommodite);

            // Supprimer dans l'objet Java
            chambre.getcommodites().removeIf(c -> c.getidCommodite() == idCommodite);
        }
    }

}


