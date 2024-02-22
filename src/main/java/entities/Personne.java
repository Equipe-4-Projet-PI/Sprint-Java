package entities;

import java.util.Date;

public class Personne {
    int prix;
    String titre ;
    Date date_initial , date_cloture;

    public Personne(int prix, String titre, Date date_initial, Date date_cloture) {
        this.prix = prix;
        this.titre = titre;
        this.date_initial = date_initial;
        this.date_cloture = date_cloture;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate_initial() {
        return date_initial;
    }

    public void setDate_initial(Date date_initial) {
        this.date_initial = date_initial;
    }

    public Date getDate_cloture() {
        return date_cloture;
    }

    public void setDate_cloture(Date date_cloture) {
        this.date_cloture = date_cloture;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "prix=" + prix +
                ", titre='" + titre + '\'' +
                ", date_initial=" + date_initial +
                ", date_cloture=" + date_cloture +
                '}';
    }
}
