package AubergeInn.collections;

import AubergeInn.tuples.Chambre;
import AubergeInn.tuples.Commodite;
import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;



//Chambre : Une chambre peut avoir zero ou plusieurs commodités.

public class CollectionChambre {

    Connexion connexion;


    private MongoCollection<Document> chambreCollection;


    public CollectionChambre(Connexion connexion)  {
        this.connexion = connexion;
        chambreCollection = connexion.getDatabase().getCollection("chambre");

    }




    public void ajouterChambre(Chambre chambre)  {
        chambreCollection.insertOne(chambre.toDocument());
    }


    public void supprimerChambre(Chambre chambre) throws IFT287Exception {
        chambreCollection.deleteOne(new Document("idChambre", chambre.getidChambre()));
    }


    public Chambre afficherChambre(int idChambre) throws IFT287Exception {
        Chambre ChambreAafficher = getChambreById(idChambre);
        if (ChambreAafficher == null)throw new IFT287Exception("Client inexistant: " + idChambre);
        else
           return ChambreAafficher;
    }


    public Chambre getChambreById(int idChambre){
        Document chambreDoc = chambreCollection.find(Filters.eq("idChambre", idChambre)).first();
        if(chambreDoc != null) {
            Chambre chambre = new Chambre(chambreDoc);
            return chambre;
        } else {
            return null;
        }
    }

    public Connexion getConnexion() {
        return connexion;
    }

    public void inclureCommodite(Chambre chambre, Commodite commodite) {
        chambre.inclureCommodite(commodite);
        chambreCollection.updateOne(
                eq("idChambre", chambre.getidChambre()),
                Updates.push("commodites", commodite.toDocument())
        );
    }

    public void enleverCommodite(Commodite commodite, Chambre chambre) {
        chambre.enleverCommodite(commodite);
        chambreCollection.updateOne(
                eq("chambre.idChambre",chambre.getidChambre()),
                Updates.pull("commodites",commodite.toDocument())
        );

    }


}


