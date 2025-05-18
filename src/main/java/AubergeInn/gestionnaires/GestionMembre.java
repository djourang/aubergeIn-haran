package AubergeInn.gestionnaires;

import AubergeInn.collections.CollectionChambre;
import AubergeInn.collections.CollectionMembre;
import AubergeInn.collections.CollectionReservation;
import AubergeInn.tuples.Membre;
import AubergeInn.utils.Connexion;
import AubergeInn.utils.IFT287Exception;

public class GestionMembre {

    private final Connexion cx;
    private final CollectionMembre membres;
    private final CollectionChambre chambres; // Par exemple, pour la suppression si le membre a des réservations
    private final CollectionReservation reservations;

    public GestionMembre(CollectionMembre collectionMembre,CollectionChambre collectionChambre, CollectionReservation collectionReservation) throws IFT287Exception {
        this.cx = collectionMembre.getConnexion();
        this.membres = collectionMembre;
        if (collectionMembre.getConnexion() != collectionReservation.getConnexion()) {
            throw new IFT287Exception("Les connexions pour les membres et les réservations doivent être identiques.");
        } else {
            this.chambres = collectionChambre;
            this.reservations = collectionReservation;
        }
    }

    /*
    Cette transaction ajoute un nouveau membre au système.
    */
    public void ajouterMembre(String userId, String motDePasseSHA,int acces, String nom, String prenom,String email, String role, String telephone) throws IFT287Exception {
        try {
            // Vérifie si le membre existe déjà
            if (membres.getMembreById(userId) != null) {
                throw new IFT287Exception("Le membre avec l'ID " + userId + " existe déjà.");
            }
            // Ajout du membre dans la collection des membres

                Membre membre = new Membre(userId, motDePasseSHA, acces, nom, prenom,email, role, telephone);
            membres.ajouterMembre(membre);
        } catch (Exception e) {
            throw new IFT287Exception("Erreur lors de l'ajout du membre : " + e.getMessage());
        }
    }

    /*
    Cette transaction supprime un membre s'il n'a pas de réservation en cours.
    */
    public void supprimerMembre(String userId) throws IFT287Exception {
        try {
            Membre membre = membres.getMembreById(userId);
            // Vérifie si le membre n'existe pas
            if (membre == null) {
                throw new IFT287Exception("Le membre avec l'ID " + userId + " n'existe pas.");
            }

            // Supprimer le membre de la collection des membres
            membres.supprimerMembre(membre);
        } catch (Exception e) {
            throw new IFT287Exception("Erreur lors de la suppression du membre : " + e.getMessage());
        }
    }

    /*
    Cette transaction affiche toutes les informations sur un membre, y compris les réservations
    présentes et passées.
    */
    public void afficherMembre(String userId) throws IFT287Exception {
        try {
            // Vérifie que le membre existe bien
            if (membres.getMembreById(userId) == null) {
                throw new IFT287Exception("Le membre avec l'ID " + userId + " n'existe pas.");
            }
            membres.afficherMembre(userId);
            // Vous pouvez ajouter ici d'autres informations comme les réservations, si nécessaire.
        } catch (Exception e) {
            throw new IFT287Exception("Erreur lors de l'affichage du membre : " + e.getMessage());
        }
    }


    public boolean informationsConnexionValide(String utilisateur, String motDePasseSHA)
            throws IFT287Exception
    {
        try
        {
            // Vérifie si le membre existe déja
            if (!membres.membreExiste(utilisateur))
                throw new IFT287Exception("Aucun utilisateur n'existe avec ce nom.");


            Membre user = membres.getMembreByUsername(utilisateur);
            if(!user.getMotDePasseSHA().equals(motDePasseSHA))
                throw new IFT287Exception("Mauvais mot de passe.");

            // Commit
            return true;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean utilisateurEstAdministrateur(String utilisateur)
            throws IFT287Exception
    {
        try
        {
            Membre m = membres.getMembreById(utilisateur);
            if(m == null)
                throw new IFT287Exception("L'utilisateur n'existe pas");
            return m.getNiveauAcces() == 0;
        }
        catch(Exception e)
        {
            throw e;
        }
    }


    public void inscrire(String userId, String motDePasseSHA, int acces, String nom, String prenom, String email, String role, long telephone) throws IFT287Exception {
        try {
            // Vérifie si le membre existe déjà
            if (membres.getMembreById(userId) != null) {
                throw new IFT287Exception("Le membre avec l'ID " + userId + " existe déjà.");
            }

            // Crée une nouvelle instance de Membre avec les informations fournies
            Membre nouveauMembre = new Membre(userId, motDePasseSHA, acces, nom, prenom, email, role, String.valueOf(telephone));

            // Ajoute le nouveau membre à la collection des membres (NoSQL)
            membres.ajouterMembre(nouveauMembre);

        } catch (Exception e) {
            throw new IFT287Exception("Erreur lors de l'inscription du membre : " + e.getMessage());
        }
    }

}





