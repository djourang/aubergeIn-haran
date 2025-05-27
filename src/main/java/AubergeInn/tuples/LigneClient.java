package AubergeInn.tuples;

public class LigneClient {

    private int idClient;
    private String nom;
    private String premom;
    private int age;


    public LigneClient(int idClient, String nom, String premom, int age) {
        this.idClient = idClient;
        this.nom = nom;
        this.premom = premom;
        this.age = age;
    }
    public String tostring()
    {
        return "\n" + "Client : " + idClient + "Nom : " + nom + "Prenom : " + premom + "Age : " + age;
    }

    public int getidClient() {
        return idClient;
    }


    public String getnom() {
        return nom;
    }

    public String getprenom() {
        return premom;
    }

    public int getage() {
        return age;
    }
}
