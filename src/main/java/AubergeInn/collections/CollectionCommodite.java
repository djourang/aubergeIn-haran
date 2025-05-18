package AubergeInn.collections;

import AubergeInn.tuples.Commodite;
import AubergeInn.utils.Connexion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;


public class CollectionCommodite {


    private MongoCollection<Document> commoditeCollection;


    public CollectionCommodite(Connexion connexion) {

        commoditeCollection = connexion.getDatabase().getCollection("commodite");
    }


    public void ajouterCommodite(Commodite commodite) {

        commoditeCollection.insertOne(commodite.toDocument());
    }

    public Commodite getCommoditeById(int idCommodite) {
        Document commoditeDoc = commoditeCollection.find(Filters.eq("idCommodite", idCommodite)).first();
        if (commoditeDoc != null) {
            return new Commodite(commoditeDoc);
        } else {
            return null;
        }
    }

    public void supprimerCommodite(Commodite commodite) {
        commoditeCollection.deleteOne(new Document("idCommodite",commodite.getidCommodite()));
    }


}
