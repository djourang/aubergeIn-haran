package AubergeInn;

import AubergeInn.gestionnaires.GestionObergeInn;
import AubergeInn.utils.IFT287Exception;

import java.io.*;
import java.sql.Date;
import java.util.StringTokenizer;

public class AubergeInn {
    private static GestionObergeInn gestionObergeInn = null;

    public void fermer() throws Exception {
        gestionObergeInn.fermer();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("🔌 Connexion à la base PostgreSQL Render...");

        AubergeInn auberge = null;
        try {
            // 🔧 Connexion sans paramètre : Render par défaut
            gestionObergeInn = new GestionObergeInn();


            BufferedReader reader = ouvrirFichier(args);
            String transaction = lireTransaction(reader);

            while (!finTransaction(transaction)) {
                executerTransaction(transaction, gestionObergeInn);
                transaction = lireTransaction(reader);
            }
        } finally {
            if (gestionObergeInn != null)
                gestionObergeInn.fermer();
        }
    }

    static void executerTransaction(String transaction, GestionObergeInn gestionObergeInn) throws Exception {
        try {
            System.out.println("> " + transaction);
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
            if (!tokenizer.hasMoreTokens()) return;

            String command = tokenizer.nextToken();

            switch (command) {
                case "ajouterClient" -> {
                    int idClient = readInt(tokenizer);
                    String prenom = readString(tokenizer);
                    String nom = readString(tokenizer);
                    int age = readInt(tokenizer);
                    gestionObergeInn.getGestionClient().ajouterClient(idClient, prenom, nom, age);
                }
                case "supprimerClient" -> gestionObergeInn.getGestionClient().supprimerClient(readInt(tokenizer));
                case "ajouterChambre" -> {
                    int id = readInt(tokenizer);
                    String nom = readString(tokenizer);
                    String type = readString(tokenizer);
                    double prix = readDouble(tokenizer);
                    gestionObergeInn.getGestionChambre().ajouterChambre(id, nom, type, prix);
                }
                case "supprimerChambre" -> gestionObergeInn.getGestionChambre().supprimerChambre(readInt(tokenizer));
                case "ajouterCommodite" -> {
                    int id = readInt(tokenizer);
                    String desc = readString(tokenizer);
                    double prix = readDouble(tokenizer);
                    gestionObergeInn.getGestionCommodite().ajouterCommodite(id, desc, prix);
                }
                case "supprimerCommodite" -> gestionObergeInn.getGestionCommodite().supprimerCommodite(readInt(tokenizer));
                case "inclureCommodite" -> {
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
                    gestionObergeInn.getGestionCommodite().inclureCommodite(idChambre, idCommodite);
                }
                case "enleverCommodite" -> {
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
                    gestionObergeInn.getGestionCommodite().enleverCommodite(idChambre, idCommodite);
                }
                case "afficherChambresLibres" -> {
                    Date debut = readDate(tokenizer);
                    Date fin = readDate(tokenizer);
                    gestionObergeInn.getGestionChambre().afficherChambresLibres(debut, fin);
                }
                case "afficherClient" -> gestionObergeInn.getGestionClient().afficherClient(readInt(tokenizer));
                case "afficherChambre" -> gestionObergeInn.getGestionChambre().afficherChambre(readInt(tokenizer));
                case "reserver" -> {
                    int idClient = readInt(tokenizer);
                    int idChambre = readInt(tokenizer);
                    Date debut = readDate(tokenizer);
                    Date fin = readDate(tokenizer);
                    gestionObergeInn.getGestionReservation().reserver(idClient, idChambre, debut, fin);
                }
                case "enleverReservation" -> {
                    int idClient = readInt(tokenizer);
                    int idChambre = readInt(tokenizer);
                    Date debut = readDate(tokenizer);
                    Date fin = readDate(tokenizer);
                    gestionObergeInn.getGestionReservation().enleverReservation(idClient, idChambre, debut, fin);
                }
                default -> System.out.println("❌ Commande inconnue : " + command);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Erreur : " + e.getMessage());
            gestionObergeInn.getConnexion().rollback();
        }
    }

    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException {
        if (args.length > 0) {
            File fichier = new File(args[0]);
            if (fichier.exists()) {
                System.out.println("📄 Lecture du fichier : " + fichier.getName());
                return new BufferedReader(new InputStreamReader(new FileInputStream(fichier)));
            } else {
                throw new FileNotFoundException("Fichier introuvable : " + args[0]);
            }
        } else {
            System.out.println("⌨️ Lecture depuis le clavier (mode interactif).");
            return new BufferedReader(new InputStreamReader(System.in));
        }
    }

    static String lireTransaction(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    static boolean finTransaction(String ligne) {
        return ligne == null || ligne.trim().equalsIgnoreCase("quitter");
    }

    static String readString(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreTokens()) return tokenizer.nextToken();
        throw new Exception("Paramètre string attendu");
    }

    static int readInt(StringTokenizer tokenizer) throws Exception {
        if (!tokenizer.hasMoreTokens()) throw new Exception("Paramètre int manquant");
        try {
            return Integer.parseInt(tokenizer.nextToken());
        } catch (NumberFormatException e) {
            throw new Exception("Entier invalide");
        }
    }

    static double readDouble(StringTokenizer tokenizer) throws Exception {
        if (!tokenizer.hasMoreTokens()) throw new Exception("Paramètre double manquant");
        try {
            return Double.parseDouble(tokenizer.nextToken());
        } catch (NumberFormatException e) {
            throw new Exception("Nombre décimal invalide");
        }
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception {
        if (!tokenizer.hasMoreTokens()) throw new Exception("Paramètre date manquant");
        try {
            return Date.valueOf(tokenizer.nextToken());
        } catch (IllegalArgumentException e) {
            throw new Exception("Date invalide (format attendu : AAAA-MM-JJ)");
        }
    }
}
