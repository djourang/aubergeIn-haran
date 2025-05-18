package AubergeInn.gestionnaires;


import AubergeInn.collections.CollectionChambre;
import AubergeInn.collections.CollectionCommodite;
import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Commodite;
import AubergeInn.utils.IFT287Exception;


public class GestionCommodite {


    CollectionCommodite collectionCommodite;
    CollectionChambre collectionChambre;

    public GestionCommodite(CollectionCommodite collectionCommodite, CollectionChambre collectionChambre) {
        this.collectionCommodite = collectionCommodite;
        this.collectionChambre = collectionChambre;
    }

    /*
    Cette transaction ajoute un nouveau service offert par l'entreprise.
     */
    public void ajouterCommodite(int idCommodite, String description, double surplusPrix) throws IFT287Exception{
        try {
            //verifier si le commodite existe deja
            if ( collectionCommodite.getCommoditeById(idCommodite)!=null){
                throw new IFT287Exception("le commodite " + idCommodite + " existe deja");
            }collectionCommodite.ajouterCommodite(new Commodite(idCommodite,description,surplusPrix));
        }catch (Exception e){
            throw e;
        }
    }
    /*
   Cette transaction ajoute une commodité à une chambre.
    */
    public void inclureCommodite(int idChambre, int idCommodite) throws IFT287Exception {
        try {
            Chambre chambre = collectionChambre.getChambreById(idChambre);
            Commodite commodite = collectionCommodite.getCommoditeById(idCommodite);
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

    public void supprimerCommodite(int idCommodite) throws IFT287Exception {
        try
        {
            // Vérifie si la chambre existe
            if ((collectionCommodite.getCommoditeById(idCommodite)==null))throw new IFT287Exception("la Commodite : " + idCommodite + " n'existe pas.");
            //=================================================================================================================================================================================================================>
            //if (!(collectionCommodite.getCommoditeById(idCommodite).estAccommoder()))throw new IFT287Exception("pour supprimer la Commodite assurez-vous qu'il n'eat inclut a aucune Chambre");
            //=================================================================================================================================================================================================================>
            // suprimer la commodite de la collection des Commodite
            collectionCommodite.supprimerCommodite(collectionCommodite.getCommoditeById(idCommodite));
            // Commit

        }
        catch (Exception e)
        {
            throw  e;
        }
    }

    public void enleverCommodite(int idChambre, int idCommodite) throws IFT287Exception {
        try {
            Chambre chambre = collectionChambre.getChambreById(idChambre);
            Commodite commodite = collectionCommodite.getCommoditeById(idCommodite);
            // verifier que la chambre exisste
            if(chambre==null)throw new IFT287Exception("peux pas enlever une commodite a la Chambre " + idChambre + " qui n'existe pas");
            // verifier que la commodite exisste
            if(commodite==null)throw new IFT287Exception("peux pas enlever la commodite " + idCommodite + " qui n'existe pas");

            //verifier si la chambre inclut bien la commondite pour pouvoir l'enlever avec succes
            if(!chambre.contains(commodite))throw new IFT287Exception("la Chambre " + idChambre + " n'inclus pas la commodite  "+idCommodite);
            collectionChambre.enleverCommodite(commodite,chambre);


        }
        catch (Exception e){

            throw e;
        }
    }
//==============================================================>

}
