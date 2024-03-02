package Services;

import entities.Auction;
import entities.Auction_participant;
import utils.MyDB;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
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
        String req = "INSERT INTO auction_participant (id_Auction, id_Participant,prix) VALUES ('"
                + auctionParticipant.getId_auction() + "','"
                + auctionParticipant.getId_participant()+"','"
                + auctionParticipant.getPrix() +"')";
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

    public List<Auction_participant> list_by_auction(int id_auction) throws SQLException {
        List<Auction_participant> list = new ArrayList<>();
        String req = "SELECT u.Username, ap.id_Participant, ap.id_Auction, ap.prix, ap.date FROM auction_participant ap JOIN User u ON u.id_User = ap.id_Participant WHERE ap.id_Auction = ? AND ap.prix != ? ORDER BY date DESC";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id_auction);
        pre.setInt(2, 0);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Auction_participant a = new Auction_participant();
            a.setId_participant(res.getInt("id_Participant"));
            a.setId_auction(res.getInt("id_Auction"));
            a.setPrix(res.getFloat("prix"));
            a.setDate(res.getTimestamp("date"));
            a.setName(res.getString("Username"));
            list.add(a);
        }
        return list;
    }



    //get nom partcipant a partir de son ID
//    public String getNom(int id) {
//        String nom = null;
//
//        try {
//            String req = "SELECT user.username FROM user " +
//                    "JOIN auction_participant ON user.id_user = auction_participant.id_participant " +
//                    "WHERE auction_participant.id_auction = ?";
//
//            PreparedStatement pre = con.prepareStatement(req);
//            pre.setInt(1, id);
//
//            ResultSet resultSet = pre.executeQuery();
//            if (resultSet.next()) {
//                nom = resultSet.getString("Username");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return nom;
//    }
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
                nom = resultSet.getString("username"); // Use lowercase "username"
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
            String req = "SELECT COUNT(*) as participant_count FROM auction_participant WHERE id_auction=? AND prix != ? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, idAuction);
            pre.setInt(2 , 0);
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


    public boolean search(int idUser, int id_Auction) throws SQLException {
        String req = "SELECT * FROM auction_participant WHERE id_Participant = ? AND id_Auction = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, idUser);
        pre.setInt(2, id_Auction);
        ResultSet resultSet = pre.executeQuery();
        return resultSet.next();
    }


    public int getEtatFavori(int idAuction, int idUser) {
        int favori = 0;

        try {
            String query = "SELECT Love FROM auction_participant WHERE id_auction = ? AND id_Participant = ?";

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setInt(1, idAuction);
                preparedStatement.setInt(2, idUser);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        favori = resultSet.getInt("Love");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favori;
    }

    public void deleteFavori(int id_auction, int idUser) {
        try {
            String query = "UPDATE auction_participant SET Love = 0 WHERE id_auction = ? AND id_Participant = ?";

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setInt(1, id_auction);
                preparedStatement.setInt(2, idUser);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Favori deleted successfully.");
                } else {
                    System.out.println("Favori not found for deletion.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFavori(int id_auction, int idUser) {
        try {
            String checkQuery = "SELECT * FROM auction_participant WHERE id_auction = ? AND id_Participant = ?";
            String updateQuery = "UPDATE auction_participant SET date = ?, prix = ?, Love = 1 WHERE id_auction = ? AND id_Participant = ?";
            String insertQuery = "INSERT INTO auction_participant (id_auction, id_Participant, date, prix, Love) VALUES (?, ?, ?, ?, 1)";

            try (PreparedStatement checkStatement = con.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, id_auction);
                checkStatement.setInt(2, idUser);

                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    // Participant existe déjà, effectuer une mise à jour
                    try (PreparedStatement updateStatement = con.prepareStatement(updateQuery)) {
                        updateStatement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                        updateStatement.setDouble(2, 0.0);
                        updateStatement.setInt(3, id_auction);
                        updateStatement.setInt(4, idUser);

                        int rowsAffected = updateStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Favori updated successfully.");
                        } else {
                            System.out.println("Failed to update favori.");
                        }
                    }
                } else {
                    // Participant n'existe pas, effectuer une insertion
                    try (PreparedStatement insertStatement = con.prepareStatement(insertQuery)) {
                        insertStatement.setInt(1, id_auction);
                        insertStatement.setInt(2, idUser);
                        insertStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                        insertStatement.setDouble(4, 0.0);

                        int rowsAffected = insertStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Favori added successfully.");
                        } else {
                            System.out.println("Failed to add favori.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
    }

    public int countFavori(int id) {
        int count = 0;

        try {
            String countQuery = "SELECT COUNT(*) FROM auction_participant WHERE id_auction = ? AND Love = 1";

            try (PreparedStatement countStatement = con.prepareStatement(countQuery)) {
                countStatement.setInt(1, id);

                try (ResultSet resultSet = countStatement.executeQuery()) {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }

        return count;
    }
}
