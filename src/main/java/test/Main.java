package test;

import Services.ServiceAuction;
import Services.ServiceParticipant;
import entities.Auction;
import entities.Auction_participant;
import utils.MyDB;

import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        MyDB conn1 = MyDB.getInstance();

        /*Auction auction1 = new Auction( 1, 15, "kenzaTest1", date, 200, 500);
        Auction auction2 = new Auction( 12, 10, "kenzaTest2", date, 50, 350);
        Auction auction3 = new Auction(11,30, 0, "modifieee", date, 0, 0);
        Auction auction4 = new Auction(411, 6, "kenzaTest4", date, 650, 950);*/
        Auction_participant participant1 = new Auction_participant(1,9,1);
        Auction_participant participant2 = new Auction_participant(2,9,1);
        Auction_participant participant3 = new Auction_participant(3,9,1);
        Auction_participant participant4 = new Auction_participant(4,10 ,1);

        ServiceAuction s = new ServiceAuction();
        ServiceParticipant sp = new ServiceParticipant();

        /*try{
            s.ajouter(auction3);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      try{
            s.modifier(auction3);
        }catch(SQLException e){
            System.out.println(e);
        }
      try {
            s.supprimer(auction3);
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            System.out.println(s.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

*/
        /*try{
            sp.ajouter(participant4);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            sp.modifier(participant3);
        }catch(SQLException e){
            System.out.println(e);
        }
        try {
            sp.supprimer(participant3);
        } catch (SQLException e) {
            System.out.println(e);
        }*/
        try {
            System.out.println(sp.list_by_auction(10));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(s.loadProductValuesFromDatabase(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}