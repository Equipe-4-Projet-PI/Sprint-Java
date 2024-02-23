package test;


import entities.Product;
import entities.ProductOrder;
import services.ServiceOrder;
import services.ServiceProduct;
import utils.MyDB;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main(String[] args) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        MyDB conn1 = MyDB.getInstance();

        ServiceProduct s = new ServiceProduct();

        Product p1 = new Product(1,0,50.25,"Mona liza","by leonardo davenci",formattedDate);
        Product p2 = new Product(2,1,25.35,"the statue of liberty","by donald trump",formattedDate);
        Product p3 = new Product(1,1,20.00,"pizza","by napolion",formattedDate);


        /* add products */
        /*try {
            s.ajouter(p1);
            s.ajouter(p2);
            s.ajouter(p3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        /* show products */
        /*try {
            System.out.println(s.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        /* modify a product */
        /*try {
            s.modifier(p3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        /* delete a product */
        /*try{
            s.supprimer(p2);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }*/
        /*try {
            System.out.println(s.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        ServiceOrder o = new ServiceOrder();
        ProductOrder o1=new ProductOrder(19,"Mona liza",formattedDate,50.25);
        ProductOrder o2=new ProductOrder(20,"the statue of liberty",formattedDate,25.35);
        ProductOrder o3=new ProductOrder(21,"pizza",formattedDate,20.00);
        try {
            o.ajouter(o1);
            o.ajouter(o2);
            o.ajouter(o3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        /*try{
            o.supprimer(o2);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }*/
        try {
            System.out.println(o.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
