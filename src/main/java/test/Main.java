package test;

import entities.Disscussion;
import entities.Message;
import services.ServiceDisscussion;
import services.ServiceMessage;
import utils.MyDB;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {

        MyDB conn = MyDB.getInstance();

       /* Message msg1 = new Message(1,"hey","Heart" );
        Message msg2 = new Message(2,"asma","asmaaa" );*/
        Message msg3 = new Message(38);

        ServiceMessage sm = new ServiceMessage() ;
        ServiceDisscussion sd = new ServiceDisscussion();

        //c
        /* try {
            s.ajouter(msg1);
            s.ajouter(msg2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        //R
//        try {
//            System.out.println(s.afficher());
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        //U
        /*try {
            s.modifier(msg2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


        //D
     /* try {
            //s.ajouter(msg2);
            s.supprimer(msg3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(s.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }/*



       //System.out.println(msg1.getIdMsg());



      */
        Disscussion disscussion1 = new Disscussion(1);
        Disscussion disscussion2 = new Disscussion(2);

        //C
        /*try {
            sd.ajouter(disscussion1);
            sd.ajouter(disscussion2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        //R
        try {
            System.out.println(sd.afficher());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //U
        /*try {
            sd.modifier(disscussion2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


        //D
     /*try {
            //s.ajouter(msg2);
            sd.supprimer(disscussion2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(sd.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
} }
