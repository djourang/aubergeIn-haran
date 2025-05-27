package AubergeInn.tuples;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LigneChambre {

    private int idChambre;
    private String nomChambre;
    private String typeLit;
    private double prixBase;
    private List<LigneCommodite> commodites;


    public LigneChambre(int idChambre, String nomChambre, String typeLit, double prixBase) {
        this.idChambre = idChambre;
        this.nomChambre = nomChambre;
        this.typeLit = typeLit;
        this.prixBase = prixBase;
        this.commodites = new LinkedList<>();
    }


    public int getIdChambre() {
        return idChambre;
    }

    public void setIdChambre(int idChambre) {
        this.idChambre = idChambre;
    }

    public double getPrixTotal()
    {
        double total = prixBase;
        for (LigneCommodite com :
                commodites) {
            total += com.getSurplusPrix();
        }
        return total;
    }

    public void afficher() {
                double prixTotal = prixBase + getPrixTotal();
                System.out.println("\nChambre ID: " + idChambre);
                System.out.println("Nom Chambre: " + nomChambre);
                System.out.println("Prix de Base: " + prixBase);
                System.out.println("Prix de s Commodites: " + getPrixTotal());
                System.out.println("Prix Total: " + prixTotal);
                System.out.println("---------------------------");
    }


    public void tostring() {
        System.out.println("\n" + "Chambre : " + idChambre);
        System.out.println("Nom de la chambre : " + nomChambre);
        System.out.println("Type de lit : " + typeLit);
        System.out.println("Prix : " + getPrixTotal());
        if (!(commodites.size() > 0))
            System.out.println("Aucune commodité.");
        else
        {
            System.out.println("Commodités : ");
            afficherCommodite();
        }
    }
    private void afficherCommodite()
    {
        for (LigneCommodite  com :
                commodites) {
            com.tostring();
        }
    }

    public Collection<LigneCommodite> getcommodites() {
        return commodites;
    }

    public String getM_nomChambre() {
        return this.nomChambre;
    }

    public String getM_typeLit() {
        return this.typeLit;
    }

    public double getM_prixBase() {
        return this.prixBase;
    }
}
