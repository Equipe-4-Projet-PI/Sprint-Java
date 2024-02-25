package entities;

import Services.ServiceParticipant;

import java.util.Date;

public class Auction_participant {
    int id_participant , id_auction ;
    float prix;
    Date date ;

    public Auction_participant(int id_participant, int id_auction, float prix) {
        this.id_participant = id_participant;
        this.id_auction = id_auction;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Auction_participant{" +
                "id_participant=" + id_participant +
                ", id_auction=" + id_auction +
                ", prix=" + prix +
                '}';
    }
}
