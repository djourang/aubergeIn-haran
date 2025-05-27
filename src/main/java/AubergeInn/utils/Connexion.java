package AubergeInn.utils;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestionnaire d'une connexion avec une base de données relationnelle PostgreSQL.
 *
 * <pre>
 *
 * Vincent Ducharme
 * Université de Sherbrooke
 * Version 2.0 - 2025-05-19
 * IFT287 - Exploitation de BD relationnelles et orientées objet
 *
 * Ce programme permet d'ouvrir une connexion avec une base de données PostgreSQL via JDBC.
 *
 * Précondition :
 *   La base de données PostgreSQL doit être accessible via le réseau,
 *   et les identifiants (nom d'utilisateur, mot de passe) doivent être valides.
 *
 * Postcondition :
 *   Une connexion JDBC est établie avec la base de données PostgreSQL.
 *
 * </pre>
 */

public class Connexion {
    private final Connection conn;

    public Connexion() throws IFT287Exception, SQLException {
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://dpg-d0du4es9c44c73cdp2mg-a.oregon-postgres.render.com:5432/database_3k0t?ssl=true&sslmode=require";
            String user = "haran";
            String password = "Yl41kHZphd6vzjnvOyVXYyatqQaQQXLx";

            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);

            if (conn.getMetaData().supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)) {
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                System.out.println("✅ Connexion PostgreSQL Render établie (mode sérialisable).");
            } else {
                System.out.println("✅ Connexion PostgreSQL Render établie (mode par défaut).");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IFT287Exception("❌ Échec de la connexion à la BD : " + e.getMessage());
        }
    }



    public void fermer() throws SQLException {
        conn.rollback();
        conn.close();
        System.out.println("🔒 Connexion fermée.");
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }

    public void setAutoCommit(boolean mode) throws SQLException {
        conn.setAutoCommit(mode);
    }

    public Connection getConnection() {
        return conn;
    }
}
