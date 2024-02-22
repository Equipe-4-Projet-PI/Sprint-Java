package entities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Auction {
    int id_artist;

    public int getId_artist() {
        return id_artist;
    }

    public void setId_artist(int id_artist) {
        this.id_artist = id_artist;
    }

    private String cheminImageProduit;
    public String getCheminImageProduit() {
        return cheminImageProduit;
    }

    public void setCheminImageProduit(String cheminImageProduit) {
        this.cheminImageProduit = cheminImageProduit;
    }

    int id , id_produit;
    String nom;
    LocalDate date_lancement , date_cloture ;
    int prix_initial , prix_final ;

    public Auction( int id_produit, String nom, int prix_initial) {
        this.id_produit = id_produit;
        this.nom = nom;
        this.prix_initial = prix_initial;
    }

    public Auction(  String nom, LocalDate date_cloture,  LocalDate date_lancement,int prix_initial,int id_produit) {
        this.date_cloture = date_cloture;
        this.date_lancement = date_lancement;
        this.id_produit = id_produit;
        this.nom = nom;
        this.prix_initial = prix_initial;
    }

    public Auction(int id, int id_produit, LocalDate date_cloture, String nom, LocalDate date_lancement, int prix_initial, int prix_final) {
        this.id = id;
        this.id_produit = id_produit;
        this.date_cloture = date_cloture;
        this.nom = nom;
        this.date_lancement = date_lancement;
        this.prix_initial = prix_initial;
        this.prix_final = prix_final;
    }

    public Auction(int id_produit, LocalDate date_cloture, String nom, LocalDate date_lancement, int prix_initial, int prix_final) {
        this.id_produit = id_produit;
        this.date_cloture = date_cloture;
        this.nom = nom;
        this.date_lancement = date_lancement;
        this.prix_initial = prix_initial;
        this.prix_final = prix_final;
    }

    public Auction() {

    }

    public LocalDate getDate_cloture() {
        return date_cloture;
    }

    public void setDate_cloture(LocalDate date_cloture) {
        this.date_cloture = date_cloture;
    }

    public LocalDate getDate_lancement() {
        return date_lancement;
    }

    public void setDate_lancement(LocalDate date_lancement) {
        this.date_lancement = date_lancement;
    }

    public int getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(int prix_initial) {
        this.prix_initial = prix_initial;
    }

    public int getPrix_final() {
        return prix_final;
    }

    public void setPrix_final(int prix_final) {
        this.prix_final = prix_final;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFormattedDate1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(getDate_lancement());
    }

    public String getFormattedDate2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(getDate_cloture());
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", id_produit=" + id_produit +
                ", date_cloture=" + date_cloture +
                ", date_lancement=" + date_lancement +
                ", prix_initial=" + prix_initial +
                ", prix_final=" + prix_final +
                '}';
    }
}
