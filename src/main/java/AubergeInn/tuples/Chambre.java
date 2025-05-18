package AubergeInn.tuples;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class Chambre {


    private int m_idChambre;
    private String m_nomChambre;
    private String m_typeLit;
    private double m_prixBase;
    private List<Commodite> m_commodites = new ArrayList<>();

    public Document toDocument(){
        return new Document()
                .append("idChambre", m_idChambre)
                .append("nomChambre", m_nomChambre)
                .append("typeLit", m_typeLit)
                .append("prixBase", m_prixBase)
                .append("commodites", getCommoditeDocuments());
    }


    public Chambre(Document d) {
        this.m_idChambre = d.getInteger("idChambre");
        this.m_nomChambre = d.getString("nomChambre");
        this.m_typeLit = d.getString("typeLit");
        this.m_prixBase = d.getDouble("prixBase");
        this.m_commodites =  new ArrayList<>();

        List<Document> commoditeDocs =  d.get("commodites",this.m_commodites.getClass());
        if (commoditeDocs != null) {
            for (Document commoditeDoc : commoditeDocs) {
                this.m_commodites.add(new Commodite(commoditeDoc));
            }
        }



    }
    public Chambre(int idChambre, String nomChambre, String typeLit, double prixBase) {
        this.m_idChambre = idChambre;
        this.m_nomChambre = nomChambre;
        this.m_typeLit = typeLit;
        this.m_prixBase = prixBase;
        this.m_commodites = new ArrayList<>();
    }

    public int getidChambre() {
        return m_idChambre;
    }

    public double getPrixTotal()
    {
        double total = m_prixBase;
        for (Commodite com : m_commodites) {
            total += com.getsurplusPrix();
        }
        return total;
    }

    public void tostring() {
        System.out.println("\n" + "Chambre : " + m_idChambre);
        System.out.println("Nom de la chambre : " + m_nomChambre);
        System.out.println("Type de lit : " + m_typeLit);
        System.out.println("prix base : " + m_prixBase);
        System.out.println("Prix (avec commodite): " + getPrixTotal());
        if (!(m_commodites.size() > 0))
            System.out.println("Aucune commodite.");
        else
        {
            afficherCommodite();
        }
    }
    private void afficherCommodite()
    {
        for (Commodite com :
                m_commodites) {
            com.tostring();
        }
    }
    public boolean inclureCommodite(Commodite commodite) {
        return this.m_commodites.add(commodite);
    }

    public List<Commodite> getcommodites() {
        return m_commodites;
    }
    public String getnomChambre() {
        return m_nomChambre;
    }


    public boolean contains(Commodite commodite) {
        return m_commodites.contains(commodite);
    }
    public void enleverCommodite(Commodite commodite) {
        m_commodites.remove(commodite);

    }

    public List<Document> getCommoditeDocuments() {
        List<Document> commoditeDocs = new ArrayList<>();
        for (Commodite commodite : m_commodites) {
            commoditeDocs.add(commodite.toDocument());
        }
        return commoditeDocs;
    }

    public int getM_idChambre() {
        return m_idChambre;
    }

    public String getM_nomChambre() {
        return m_nomChambre;
    }

    public String getM_typeLit() {
        return m_typeLit;
    }

    public double getM_prixBase() {
        return m_prixBase;
    }

    public List<Commodite> getM_commodites() {
        return m_commodites;
    }
}
