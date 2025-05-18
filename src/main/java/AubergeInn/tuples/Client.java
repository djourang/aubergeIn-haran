package AubergeInn.tuples;

import org.bson.Document;




public class Client {

    private int m_idClient;
    private String m_nom;
    private String m_prenom;
    private int m_age;

    public Client(Document d) {
        this.m_idClient = d.getInteger("idClient");
        this.m_nom = d.getString("nom");
        this.m_prenom = d.getString("prenom");
        this.m_age = d.getInteger("age");
    }

    public Client(int idClient, String nom, String premom, int age) {
        this.m_idClient = idClient;
        this.m_nom = nom;
        this.m_prenom = premom;
        this.m_age = age;
    }

    public void tostring()
    {
        System.out.println("\n" + "Client : " + m_idClient);
        System.out.println("Nom : " + m_nom);
        System.out.println("Prenom : " + m_prenom);
        System.out.println("Age : " + m_age +"\n");

    }

    public int getidClient() {
        return m_idClient;
    }

    public Document toDocument() {

        return new Document()
                .append("idClient", m_idClient)
                .append("nom", m_nom)
                .append("prenom", m_prenom)
                .append("age", m_age);
    }


    public int getM_idClient() {
        return m_idClient;
    }

    public String getM_nom() {
        return m_nom;
    }

    public String getM_prenom() {
        return m_prenom;
    }

    public int getM_age() {
        return m_age;
    }
}
