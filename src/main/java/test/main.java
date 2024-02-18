package test;
import java.security.Provider;
import java.sql.*;

import services.ServicePost;
import utils.MyDB;
import entities.PostEntity;
import entities.ForumEntity;
//import services.ServicePost;
import services.ServiceForum;

public class main {
    public static void main(String[] args) {
        //Connect to Database
        MyDB conn1 = MyDB.getInstance();
        /*===================ForumEntities======================*/
        ForumEntity f1 = new ForumEntity("Abstract Art","Isnt it Beautiful?");
        ForumEntity f2 = new ForumEntity("Surrealisme is weird","Just for Testing");
        ForumEntity f3 = new ForumEntity(3, "Digital Art is Beautiful","I would like to know more about your opinions",5);
        /*===================ForumService=======================*/
        ServiceForum forumService = new ServiceForum();
//        //----------------Add Test----------------------
//        try {
//            forumService.ajouter(f1);
//            forumService.ajouter(f2);
//            forumService.ajouter(f3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show TEST----------------------
//        try {
//            System.out.println(forumService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //----------------EDIT TEST----------------------
//        try {
//            forumService.modifier(f3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Delete TEST----------------------
//        try {
//            forumService.supprimer(f3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show AFTER TEST----------------------
//        System.out.println("AFTER EDIT : ");
//        try {
//            System.out.println(forumService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        /*================POST SERVICE====================*/
        ServicePost postService = new ServicePost();
        /*================POST Entities====================*/
        PostEntity p1 = new PostEntity(1,1,"Yes i like it");
        PostEntity p2 = new PostEntity(2,1,"It really is");
        PostEntity p3 = new PostEntity(3,2,1,"Indeed i do well ofc",5);
//        //----------------Add Test----------------------
//        try {
//            postService.ajouter(p1);
//            postService.ajouter(p2);
//            postService.ajouter(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show TEST----------------------
//        try {
//            System.out.println(postService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //----------------EDIT TEST----------------------
//        try {
//            postService.modifier(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Delete TEST----------------------
//        try {
//            postService.supprimer(p3);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        //----------------Show AFTER TEST----------------------
//        System.out.println("AFTER EDIT : ");
//        try {
//            System.out.println(postService.afficher());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
