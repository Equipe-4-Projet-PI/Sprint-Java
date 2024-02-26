package Services;

import entities.Auction;
import entities.Auction_participant;
import utils.MyDB;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceParticipant implements IService<Auction_participant> {


    private Connection con;

    public ServiceParticipant(){
        con = MyDB.getInstance().getConnection();
    }
    @Override
    public void ajouter(Auction_participant auctionParticipant) throws SQLException {
        String req = "INSERT INTO auction_participant (id_Auction, id_Participant) VALUES ('"
                + auctionParticipant.getId_auction() + "','"
                + auctionParticipant.getId_participant() + "')";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    //la methode participer
    @Override
    public void modifier(Auction_participant auctionParticipant) throws SQLException {
        String req = "update auction_participant set prix=? , date=? where id_Participant=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setFloat(1,auctionParticipant.getPrix());
        pre.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
        pre.setInt(3,auctionParticipant.getId_participant());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(Auction_participant auctionParticipant) throws SQLException {
        String req = "delete from auction_participant where id_Participant=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,auctionParticipant.getId_participant());
        pre.executeUpdate();
    }

    @Override
    public List<Auction_participant> afficher() throws SQLException {
        List<Auction_participant> list = new ArrayList<>();
        String req = "select * from auction_participant";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            Auction_participant a = new Auction_participant();
            a.setId_participant(res.getInt("id_Participant"));
            a.setId_auction(res.getInt("id_Auction"));
            a.setPrix(res.getFloat("prix"));
            a.setDate(res.getTimestamp("date"));
            list.add(a);
        }
        return list;
    }

    public List<Auction_participant> list_by_auction(int id_auction ) throws SQLException{
        List<Auction_participant> list = new ArrayList<>();
        String req = "SELECT u.Username, ap.id_Participant, ap.id_Auction, ap.prix, ap.date FROM auction_participant ap JOIN User u ON u.id = ap.id_Participant WHERE ap.id_Auction = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,id_auction);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            Auction_participant a = new Auction_participant();
            a.setId_participant(res.getInt("id_Participant"));
            a.setId_auction(res.getInt("id_Auction"));
            a.setPrix(res.getFloat("prix"));
            a.setDate(res.getTimestamp("date"));
            list.add(a);
        }
        return list;
    }
    //get nom partcipant a partir de son ID
    public String getNom(int id) {
        String nom = null;

        try {
            String req = "SELECT user.username FROM user " +
                    "JOIN auction_participant ON user.id_user = auction_participant.id_participant " +
                    "WHERE auction_participant.id_auction = ?";

            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);

            ResultSet resultSet = pre.executeQuery();
            if (resultSet.next()) {
                nom = resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nom;
    }


    public InputStream getCheminImageParticipant(int id) {
        InputStream imageStream = null;

        try {
            String req = "SELECT user.image FROM user " +
                    "JOIN auction_participant ON user.id_user = auction_participant.id_user " +
                    "WHERE auction_participant.id_participant = ?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);

            ResultSet resultSet = pre.executeQuery();
            if (resultSet.next()) {
                imageStream = resultSet.getBinaryStream("image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imageStream;
    }

    public float getPrixAjoute(int id) {
        float prixAjoute = 0;

        try {
            String req = "SELECT prix FROM auction_participant WHERE participant_id = ? ORDER BY date DESC LIMIT 1";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);
            ResultSet resultSet = pre.executeQuery();
            if (resultSet.next()) {
                prixAjoute = resultSet.getFloat("prix");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prixAjoute;
    }

    public int countParticipant(int idAuction) throws SQLException {
        int count = 0;

        try {
            String req = "SELECT COUNT(*) as participant_count FROM auction_participant WHERE id_auction=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, idAuction);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                // Récupérer le nombre de participants
                count = resultSet.getInt("participant_count");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public Auction_participant getParticipantById(int id_Participant) throws SQLException {
        String req = "select * from auction_participant where id_Participant=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id_Participant);
        ResultSet res = pre.executeQuery();
        Auction_participant a = new Auction_participant();
        while (res.next()) {
            a.setId_participant(res.getInt("id_Participant"));
            a.setId_auction(res.getInt("id_Auction"));
            a.setPrix(res.getFloat("prix"));
            a.setDate(res.getTimestamp("date"));
            return a;
        }
        return  a;
    }

    public float getDernierPrix(int id_auction) throws SQLException {
        String req = "SELECT prix FROM auction_participant WHERE id_auction = ? ORDER BY date DESC LIMIT 1";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id_auction);
        ResultSet resultSet = pre.executeQuery();
        if (resultSet.next()) {
            float dernierPrixEffectue = resultSet.getFloat("prix");
            return dernierPrixEffectue;
        } else {
            return 0;
        }
    }

}
