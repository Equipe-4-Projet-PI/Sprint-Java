package Services;

import entities.Auction;
import utils.MyDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceAuction implements IService<Auction>{

    private Connection con;

    public ServiceAuction(){
        con = MyDB.getInstance().getConnection();
    }




    @Override
    public void ajouter(Auction auction) throws SQLException {
        String query = "INSERT INTO auction (nom, date_cloture, date_lancement, prix_initial, id_produit) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, auction.getNom());
            preparedStatement.setDate(2, Date.valueOf(auction.getDate_cloture()));
            preparedStatement.setDate(3, Date.valueOf(auction.getDate_lancement()));
            preparedStatement.setInt(4, auction.getPrix_initial());
            preparedStatement.setInt(5, auction.getId_produit());
            preparedStatement.executeUpdate();
        }
    }



    /*public void ajouter(Auction auction) throws SQLException {
        String req = "insert into Auction (nom , date_lancement,prix_initial ,  id_produit )" +
                "values ('"+auction.getNom()+"','"+auction.getDuration()+"','"+auction.getFormattedDate()+"','"+auction.getPrix_initial()+"','"+auction.getPrix_final()+"' ,"+auction.getId_produit()+")";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);

    }*/

    @Override
    public void modifier(Auction auction) throws SQLException {
        String req = "update Auction set nom=? ,date_cloture=? , date_lancement=? , prix_initial=?  , prix_final=? ,  id_produit=? where id_Auction=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, auction.getNom());
        pre.setString(2,auction.getFormattedDate2());
        pre.setString(3,auction.getFormattedDate1());
        pre.setInt(4,auction.getPrix_initial());
        pre.setInt(5,auction.getPrix_final());
        pre.setInt(6,auction.getId_produit());
        pre.setInt(7, auction.getId());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(Auction auction) throws SQLException{
        String req = "delete from Auction where id_auction=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, auction.getId());
        pre.executeUpdate();
    }


    @Override
    public List<Auction> afficher() throws SQLException {
        List<Auction> list = new ArrayList<>();
        String req = "select id_Auction , nom , prix_initial, prix_final , id_produit from Auction";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            Auction a = new Auction();
            a.setId(res.getInt(1));
            a.setNom(res.getString("nom"));
            //a.setDate_cloture(LocalDate.from(res.getTimestamp("date_cloture").toLocalDateTime()));
            a.setPrix_final(res.getInt("prix_final"));
            a.setPrix_initial(res.getInt("prix_initial"));
            //a.setDate_lancement(LocalDate.from(res.getTimestamp("date_lancement").toLocalDateTime()));
            a.setId_produit(res.getInt("id_produit"));
            list.add(a);
        }
        return list;
    }

    public List<String> loadProductValuesFromDatabase(int id) throws SQLException {
        List<String> productNames = new ArrayList<>();
        String req = "SELECT Title FROM product WHERE Id_User=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, id);
            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    String productName = res.getString("Title");
                    productNames.add(productName);
                }
            }
        }
        return productNames;
    }

    public String loadProductFromDatabase(int encher_id) throws SQLException {
        String req = "SELECT Title FROM product JOIN auction ON auction.id_produit = product.id_product WHERE id_Auction = ?";
        String productName = null;
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, encher_id);
            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    productName = res.getString("Title");
                }
            }
        }
        return productName;
    }

    public int getProductID(String productName) throws SQLException {
        String query = "SELECT Id_Product FROM product WHERE Title = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, productName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("Id_Product");
                } else {
                    throw new SQLException("Product ID not found for product: " + productName);
                }
            }
        }
    }

    public List<Auction> getEncheresWithProductImage() {
        List<Auction> listAuctions = new ArrayList<>();
        try {
            String req = "SELECT auction.id_Auction, product.image8produit FROM auction JOIN product ON auction.id_produit = product.id_product";
            PreparedStatement preparedStatement = con.prepareStatement(req);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Auction auction = new Auction();
                auction.setId(resultSet.getInt("id_Auction"));
                String cheminImageProduit = resultSet.getString("image8produit");
                auction.setCheminImageProduit(cheminImageProduit);

                listAuctions.add(auction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAuctions;
    }

    public void modifierPrixFinal(Auction auction) throws SQLException {
        String req = "select prix_final from Auction where id_auction=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, auction.getId());

    }

    public Auction getById(int id) throws SQLException {
        String req = "SELECT a.id_auction,  p.image8produit, a.nom , adate_cloturue , a.date_lancement ,a.prix_initial , a.prix_final ,a.id_produit, a.id_artist FROM auction a " +
                "JOIN produit p ON a.id_produit = p.id_product " +
                "WHERE a.id_auction=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);

        ResultSet resultSet = pre.executeQuery();
        Auction auction = new Auction();

        if (resultSet.next()) {
            auction.setId(resultSet.getInt("id_auction"));
            auction.setNom(resultSet.getString("nom"));
            auction.setDate_lancement(resultSet.getObject("date_lancement", LocalDate.class));
            auction.setPrix_initial(resultSet.getInt("prix_initial"));
            auction.setPrix_final(resultSet.getInt("prix_final"));
            auction.setId_produit(resultSet.getInt("id_produit"));
            auction.setId_artist(resultSet.getInt("id_artist"));
            auction.setCheminImageProduit(resultSet.getString("image_produit"));

        }

        return auction;
    }
    public String getCheminImageProduit(int idProduit) throws SQLException {
        String req = "SELECT image8produit FROM product WHERE id_product=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, idProduit);

        ResultSet resultSet = pre.executeQuery();
        String cheminImageProduit = null;

        if (resultSet.next()) {
            cheminImageProduit = resultSet.getString("image_produit");
        }

        return cheminImageProduit;
    }

    public Auction getAuctionById(int id ) throws SQLException {
        String req= "select * from Auction where id_auction=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,id);
        ResultSet resultSet = pre.executeQuery();
        Auction auction = new Auction();
        while (resultSet.next()){
            auction.setId(resultSet.getInt("id_auction"));
            auction.setNom(resultSet.getString("nom"));
            auction.setDate_lancement(resultSet.getObject("date_lancement", LocalDate.class));
            auction.setPrix_initial(resultSet.getInt("prix_initial"));
            auction.setPrix_final(resultSet.getInt("prix_final"));
            auction.setId_produit(resultSet.getInt("id_produit"));
            auction.setId_artist(resultSet.getInt("id_artist"));
            auction.setCheminImageProduit(resultSet.getString("image_produit"));
        }
        return  auction;
    }
    public Auction getAuctionByIdNoImage(int id) throws SQLException {
        String req = "SELECT * FROM Auction WHERE id_auction=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, id);
            try (ResultSet resultSet = pre.executeQuery()) {
                if (resultSet.next()) {
                    Auction auction = new Auction();
                    auction.setId(resultSet.getInt("id_auction"));
                    auction.setNom(resultSet.getString("nom"));
                    auction.setDate_lancement(resultSet.getObject("date_lancement", LocalDate.class));
                    auction.setPrix_initial(resultSet.getInt("prix_initial"));
                    auction.setPrix_final(resultSet.getInt("prix_final"));
                    auction.setId_produit(resultSet.getInt("id_produit"));
                    auction.setId_artist(resultSet.getInt("id_artist"));
                    return auction;
                } else {
                    // Gérer le cas où aucune enchère n'est trouvée (lancer une exception ou renvoyer null)
                    throw new SQLException("Aucune enchère trouvée avec l'ID : " + id);
                    // ou
                    // return null;
                }
            }
        }
    }





}
