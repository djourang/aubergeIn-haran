package AubergeInn.gestionnaires;


import AubergeInn.collections.TableChambre;
import AubergeInn.collections.TableCommodite;
import AubergeInn.collections.TableInclusionCommodite;
import AubergeInn.tuples.LigneChambre;
import AubergeInn.tuples.LigneCommodite;
import AubergeInn.utils.IFT287Exception;

import java.sql.SQLException;


public class GestionCommodite {


    TableCommodite collectionCommodite;
    TableChambre collectionChambre;
    TableInclusionCommodite collectionInclusionCommodite;

    public GestionCommodite(TableCommodite collectionCommodite, TableChambre collectionChambre,TableInclusionCommodite collectionInclusionCommodite) {
        this.collectionCommodite = collectionCommodite;
        this.collectionChambre = collectionChambre;
        this.collectionInclusionCommodite = collectionInclusionCommodite;
    }

    /*
    Cette transaction ajoute un nouveau service offert par l'entreprise.
     */
    public void ajouterCommodite(int idCommodite, String description, double surplusPrix) throws IFT287Exception, SQLException {
        try {
            //verifier si le commodite existe deja
            if ( collectionCommodite.getCommodite(idCommodite)!=null){
                throw new IFT287Exception("le commodite " + idCommodite + " existe deja");
            }collectionCommodite.ajouterCommodite(new LigneCommodite(idCommodite,description,surplusPrix));
        }catch (Exception e){
            throw e;
        }
    }
    /*
   Cette transaction ajoute une commodité à une chambre.
    */
    public void inclureCommodite(int idChambre, int idCommodite) throws IFT287Exception, SQLException {
        try {
            LigneChambre chambre = collectionChambre.getChambre(idChambre);
            LigneCommodite commodite = collectionCommodite.getCommodite(idCommodite);
            //verifier si la Chambre existe
            if (chambre==null){throw new IFT287Exception("la Chambre " + idChambre +" n'existe pas dans la table Chambre");}
            //verifier si la Commodite exisste
            if (commodite==null){throw new IFT287Exception("la Commodite " + idCommodite +" n'existe pas dans la table Commodite");}
            //Lien commodite et chambre
            if (chambre.getcommodites().contains(commodite)) {throw new IFT287Exception("La chambre " + idChambre + " possède déjà la commodité " + idCommodite + ".");}
            collectionChambre.inclureCommodite(chambre,commodite);

        }catch (Exception e){

            throw e;
        }
    }

    public void supprimerCommodite(int idCommodite) throws IFT287Exception, SQLException {
        try
        {
            // Vérifie si la commodite existe
            if ((collectionCommodite.getCommodite(idCommodite)==null))throw new IFT287Exception("la Commodite : " + idCommodite + " n'existe pas.");
            //=================================================================================================================================================================================================================>
            //if (!(collectionCommodite.getCommoditeById(idCommodite).estAccommoder()))throw new IFT287Exception("pour supprimer la Commodite assurez-vous qu'il n'eat inclut a aucune Chambre");
            //=================================================================================================================================================================================================================>
            // suprimer la commodite de la collection des Commodite
            collectionCommodite.supprimerCommodite(collectionCommodite.getCommodite(idCommodite));
            // Commit

        }
        catch (Exception e)
        {
            throw  e;
        }
    }

    public void enleverCommodite(int idChambre, int idCommodite) throws IFT287Exception, SQLException {
        try {
            LigneChambre chambre = collectionChambre.getChambre(idChambre);
            LigneCommodite commodite = collectionCommodite.getCommodite(idCommodite);
            // verifier que la chambre exisste
            if(chambre==null)throw new IFT287Exception("peux pas enlever une commodite a la Chambre " + idChambre + " qui n'existe pas");
            // verifier que la commodite exisste
            if(commodite==null)throw new IFT287Exception("peux pas enlever la commodite " + idCommodite + " qui n'existe pas");

            //verifier si la chambre inclut bien la commondite pour pouvoir l'enlever avec succes
            if(!chambre.getcommodites().contains(commodite))throw new IFT287Exception("la Chambre " + idChambre + " n'inclus pas la commodite  "+idCommodite);
            collectionChambre.enleverCommodite(commodite,chambre,collectionInclusionCommodite);


        }
        catch (Exception e){

            throw e;
        }
    }
//==============================================================>

}
