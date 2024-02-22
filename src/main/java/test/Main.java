package test;

import entities.Message;
import services.ServiceMessage;
import utils.MyDB;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        MyDB conn = MyDB.getInstance();

        Message msg1 = new Message(1,"hey","Heart" );
        Message msg2 = new Message(2,"asma","asmaaa" );
        Message msg3 = new Message(38);

        //C
        ServiceMessage s = new ServiceMessage() ;
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
      try {
            //s.ajouter(msg2);
            s.supprimer(msg3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(s.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



       //System.out.println(msg1.getIdMsg());


    }
}
