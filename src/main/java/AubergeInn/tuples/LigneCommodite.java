package AubergeInn.tuples;

public class LigneCommodite {


    private int idCommodite;
    private String description;
    private double surplusPrix;



    public LigneCommodite(int idCommodite, String description, double surplusPrix) {
        this.idCommodite = idCommodite;
        this.description = description;
        this.surplusPrix = surplusPrix;
    }

    public void tostring() {
        System.out.println("\n" + "Commodité numéro : " + idCommodite);
        System.out.println("Description : " + description);
        System.out.println("Prix en surplus : " + surplusPrix);
    }

    public double getSurplusPrix() {
        return surplusPrix;
    }

    public int getidCommodite() {
        return idCommodite;
    }

    public String getdescription() {
        return description;
    }

}
