package test;
import java.security.Provider;
import java.sql.*;
import utils.MyDB;
import entities.PostEntity;
import entities.ForumEntity;
import services.ServicePost;
import services.ServiceForum;

public class main {
    public static void main(String[] args) {
        //Connect to Database
        MyDB conn1 = MyDB.getInstance();
        /*===================ForumEntities======================*/
        ForumEntity f1 = new ForumEntity("Abstract Art is Good");
        ForumEntity f2 = new ForumEntity("Surrealisme is weird ");
        ForumEntity f3 = new ForumEntity(3,"Digital Art is Beautiful");
        /*===================ForumService=======================*/
        ServiceForum forumService = new ServiceForum();
//        //----------------Add Forum Test----------------------
//        try {
//            forumService.ajouter(f1);
//            forumService.ajouter(f2);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show Forum TEST----------------------
//        try {
//            System.out.println(forumService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //----------------EDIT Forum TEST----------------------
//        try {
//            forumService.modifier(f3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Delete Forum TEST----------------------
//        try {
//            forumService.supprimer(f3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show Forum AFTER TEST----------------------
//        System.out.println("AFTER EDIT : ");
//        try {
//            System.out.println(forumService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        /*================POST SERVICE====================*/
        ServicePost postService = new ServicePost();
        /*================POST Entities====================*/
        PostEntity p1 = new PostEntity(1,1,"It is Good ","I like Abstract Art a lot");
        PostEntity p2 = new PostEntity(2,1,"It really is","it gives me weird vibes");
        PostEntity p3 = new PostEntity(3,2,1,"Indeed","i Agree with the person above me");
        //----------------Add Test----------------------
//        try {
//            postService.ajouter(p1);
//            postService.ajouter(p2);
//            postService.ajouter(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show POST TEST----------------------
//        try {
//            System.out.println(postService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //----------------EDIT POST TEST----------------------
//        try {
//            postService.modifier(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Delete POST TEST----------------------
//        try {
//            postService.supprimer(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show POST AFTER TEST----------------------
//        System.out.println("AFTER EDIT : ");
//        try {
//            System.out.println(postService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
