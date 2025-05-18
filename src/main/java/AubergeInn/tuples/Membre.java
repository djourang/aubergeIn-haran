package AubergeInn.tuples;


import org.bson.Document;

import java.io.Serializable;

public class Membre implements Serializable {

    private String userId;
    private String motDePasseSHA;
    private int acces;
    private String nom;
    private String prenom;
    private String email;
    private String role;  // Par exemple, "ADMIN" ou "USER"
    private String telephone;

    // Constructeurs
    public Membre() {}

    public Membre(String userId, String motDePasseSHA, int acces,String nom, String prenom, String email, String role, String telephone) {
        this.userId = userId;
        this.motDePasseSHA = motDePasseSHA;
        this.acces = acces;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.telephone = telephone;
    }

    // Constructeur à partir d'un document MongoDB
    public Membre(Document doc) {
        this.userId = doc.getString("userId");
        this.motDePasseSHA = doc.getString("motDePasseSHA");
        this.nom = doc.getString("nom");
        this.prenom = doc.getString("prenom");
        this.email = doc.getString("email");
        this.role = doc.getString("role");
        this.telephone = doc.getString("telephone");
    }

    // Getters et Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMotDePasseSHA() {
        return motDePasseSHA;
    }

    public void setMotDePasseSHA(String motDePasseSHA) {
        this.motDePasseSHA = motDePasseSHA;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Conversion vers un document MongoDB
    public Document toDocument() {
        return new Document("userId", userId)
                .append("motDePasseSHA", motDePasseSHA)
                .append("nom", nom)
                .append("prenom", prenom)
                .append("email", email)
                .append("role", role)
                .append("telephone", telephone);
    }

    public void setNiveauAcces(int niveau)
    {
        this.acces = niveau;
    }
    public int getNiveauAcces() {
        return acces;
    }
}

