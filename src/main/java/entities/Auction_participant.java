package entities;

import Services.ServiceParticipant;

public class Auction_participant {
    int id_participant , id_auction , id_user;
    float prix;
    public Auction_participant(int id_participant, int id_auction, int id_user) {
        this.id_participant = id_participant;
        this.id_auction = id_auction;
        this.id_user = id_user;

    }

    public Auction_participant(int id_participant, int id_auction, int id_user,float prix) {
        this.id_participant = id_participant;
        this.id_auction = id_auction;
        this.id_user = id_user;
        this.prix = prix;
    }

    public Auction_participant() {

    }

    public int getId_participant() {
        return id_participant;
    }

    public void setId_participant(int id_participant) {
        this.id_participant = id_participant;
    }

    public int getId_auction() {
        return id_auction;
    }

    public void setId_auction(int id_auction) {
        this.id_auction = id_auction;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    private int id; 

    public String getNom() {
        ServiceParticipant serviceParticipant = new ServiceParticipant();
        return serviceParticipant.getNom(id);
    }

    @Override
    public String toString() {
        return "Auction_participant{" +
                "id_participant=" + id_participant +
                ", id_auction=" + id_auction +
                ", id_user=" + id_user +
                ", prix=" + prix +
                '}';
    }
}
