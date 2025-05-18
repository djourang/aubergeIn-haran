    package AubergeInn.tuples;

    import org.bson.Document;

    import java.util.Date;
    import java.util.concurrent.TimeUnit;

    public class Reservation
    {

        private Client m_client;
        private Chambre m_chambre;
        private Date m_dateDebut;
        private Date m_dateFin;
        private double m_prixTotal;


        public Reservation(Document d) {
            this.m_client = new Client(d.get("client", Document.class));
            this.m_chambre = new Chambre(d.get("chambre", Document.class));
            this.m_dateDebut = d.getDate("dateDebut");
            this.m_dateFin = d.getDate("dateFin");
            this.m_prixTotal = d.getDouble("prixTotal");
        }
        public Document toDocument() {

            return new Document()
                    .append("client", m_client.toDocument())
                    .append("chambre", m_chambre.toDocument())
                    .append("dateDebut",m_dateDebut)
                    .append("dateFin",m_dateFin)
                    .append("prixTotal", m_prixTotal);

        }

        public Reservation(Chambre chambre, Client client, Date dateDebut, Date dateFin) {
            this.m_client = client;
            this.m_chambre = chambre;
            this.m_dateDebut = dateDebut;
            this.m_dateFin = dateFin;
            this.m_prixTotal = getPrixTotal(dateDebut,dateFin);
        }

        public double getPrixTotal(Date dateDebut, Date dateFin) {
            long diffInMillies = Math.abs(dateFin.getTime() - dateDebut.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            double prixNuits = diff * m_chambre.getPrixTotal();
            return prixNuits ;
        }



        public Date getdateDebut() {
            return m_dateDebut;
        }


        public Date getdateFin() {
            return m_dateFin;
        }

        public Client getM_client() {
            return m_client;
        }

        public Chambre getM_chambre() {
            return m_chambre;
        }

        public Date getM_dateDebut() {
            return m_dateDebut;
        }

        public Date getM_dateFin() {
            return m_dateFin;
        }

        public double getM_prixTotal() {
            return m_prixTotal;
        }
    }
