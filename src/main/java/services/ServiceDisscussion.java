package services;

import entities.Disscussion;
import entities.Message;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDisscussion implements IService<Disscussion> {

    private Connection conn;

    public ServiceDisscussion() {conn = MyDB.getInstance().getConnection();}

    @Override
    public void ajouter(Disscussion disscussion) throws SQLException {
        String req = "INSERT INTO disscussion ( idSender , idReceiver) VALUES (?, ? )";
        PreparedStatement pre = conn.prepareStatement(req);

        pre.setInt(1, disscussion.getIdSender());
        pre.setInt(2, disscussion.getIdReceiver());


        pre.executeUpdate();
        pre.close();
    }


    @Override
    public void modifier(Disscussion disscussion) throws SQLException {

        String req = "UPDATE disscussion SET  idSender=? , idReceiver =? WHERE idDis= ?";
        PreparedStatement pre = conn.prepareStatement(req);

        pre.setInt(1, disscussion.getIdSender());
        pre.setInt(2, disscussion.getIdReceiver());

        pre.setInt(3, disscussion.getIdDis());

        pre.executeUpdate();
        pre.close();
    }

    @Override
    public void supprimer(Disscussion disscussion) throws SQLException {
        String Req = "DELETE FROM disscussion WHERE idDis  = ?";
        try (PreparedStatement pre = conn.prepareStatement(Req)) {

            pre.setInt(1, disscussion.getIdDis());
            System.out.println(disscussion.getIdDis());
            pre.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Error deleting message: " + e.getMessage());
        }
    }

    @Override
    public List<Disscussion> afficher() throws SQLException {
        List<Disscussion> dis = new ArrayList<>();
        String req = "SELECT idDis, idSender, idReceiver  FROM disscussion";

        PreparedStatement pre = conn.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Disscussion d = new Disscussion() ;

            d.setIdDis(res.getInt("idDis"));
            d.setIdSender(res.getInt("idSender"));
            d.setIdReceiver(res.getInt("idReceiver"));

            dis.add(d);
    }
        return dis;

    }


}
