package services;

import entities.Product;
import entities.ProductOrder;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOrder implements IServiceOrder<ProductOrder> {
    private Connection con;
    public ServiceOrder(){
        con = MyDB.getInstance().getConnection();
    }
    @Override
    public void ajouter(ProductOrder productOrder) throws SQLException {
        String req = "insert into orderproduct (Id_Product,Price,Title,OrderDate,Prod_img)"+
                "values ('"+productOrder.getId_Product()+"','"+productOrder.getPrice()+"','"+productOrder.getTitle()+"','"+productOrder.getOrderDate()+"','"+productOrder.getProd_img()+"')";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void supprimer(ProductOrder productOrder) throws SQLException {
        String req ="delete from  orderproduct where Id_Order=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,productOrder.getId_Order());
        pre.execute();
        System.out.println("deleted");
    }

    @Override
    public List<ProductOrder> afficher() throws SQLException {
        List<ProductOrder> pro = new ArrayList<>();

        String req = "select * from orderproduct";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res= pre.executeQuery();
        while (res.next()){
            ProductOrder p = new ProductOrder();
            p.setId_Order(res.getInt(1));
            p.setId_Product(res.getInt(2));
            p.setPrice(res.getDouble("Price"));
            p.setTitle(res.getString("Title"));
            p.setOrderDate(res.getString("OrderDate"));
            p.setProd_img(res.getString("Prod_img"));
            pro.add(p);
        }


        return pro;
    }
}
