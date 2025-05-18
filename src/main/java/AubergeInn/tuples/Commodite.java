package AubergeInn.tuples;

import org.bson.Document;

import java.util.Objects;


public class Commodite {

    private int m_idCommodite;
    private String m_description;
    private double m_surplusPrix;


    public Commodite(int idCommodite, String description, double surplusPrix) {
        this.m_idCommodite = idCommodite;
        this.m_description = description;
        this.m_surplusPrix = surplusPrix;

    }


    public Commodite(Document d) {
        this.m_idCommodite = d.getInteger("idCommodite");
        this.m_description = d.getString("description");
        this.m_surplusPrix = d.getDouble("surplusPrix");
    }

    public void tostring() {
        System.out.println("\n" + "Commodite numero : " + m_idCommodite);
        System.out.println("Description : " + m_description);
        System.out.println("Prix en surplus : " + m_surplusPrix +"\n");
    }
    public double getsurplusPrix() {
        return m_surplusPrix;
    }
    public int getidCommodite() {
        return m_idCommodite;
    }



    public Document toDocument() {
        return new Document()
                .append("idCommodite",m_idCommodite)
                .append("description",m_description)
                .append("surplusPrix",m_surplusPrix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commodite commodite = (Commodite) o;
        return m_idCommodite == commodite.m_idCommodite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_idCommodite);
    }

    public String getNom() {
        return m_description;
    }
}
