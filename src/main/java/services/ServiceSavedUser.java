package services;

import entities.Artist;
import entities.SavedUser;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceSavedUser implements IService<SavedUser>{


    private Connection con;

    public ServiceSavedUser(){
        con = MyDB.getInstance().getConnection();
    }
    @Override
    public void ADD(SavedUser savedUser) throws SQLException {

        // First, add the artist as a user
        String userReq = "INSERT INTO User (Username, Email, Password, Role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement userStmt = con.prepareStatement(userReq, PreparedStatement.RETURN_GENERATED_KEYS)) {
            userStmt.setString(1, savedUser.getUsername());
            userStmt.setString(2, savedUser.getEmail());
            userStmt.setString(3, savedUser.getPassword());
            userStmt.setString(4, savedUser.getRole());
            userStmt.executeUpdate();

            // Retrieve the generated user ID
            int userId;
            try (var generatedKeys = userStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            // Now, add the artist with the obtained user ID
            String SavedUserReq = "INSERT INTO SavedUser (Id_SavedUser, Name, LastName, DOB) VALUES (?, ?, ?, ?)";
            try (PreparedStatement artistStmt = con.prepareStatement(SavedUserReq)) {
                artistStmt.setInt(1, userId);
                artistStmt.setString(2, savedUser.getName());
                artistStmt.setString(3, savedUser.getLastName());
                artistStmt.setString(4, savedUser.getDOB());
                artistStmt.executeUpdate();
            }
        }
    }

    @Override
    public void UPDATE(SavedUser savedUser) throws SQLException {
        String Userreq = "update User set UserName=? , Email=? , Password=?, Role=? where Id_User =?";
        try (PreparedStatement userStmt  = con.prepareStatement(Userreq)) {
            userStmt.setString(1, savedUser.getUsername());
            userStmt.setString(2, savedUser.getEmail());
            userStmt.setString(3, savedUser.getPassword());
            userStmt.setString(4, savedUser.getRole());
            userStmt.setInt(5, savedUser.getId_SavedUser());
            userStmt.executeUpdate();


            String SavedUserReq = "update SavedUser set Name=? , LastName=? , DOB=? where Id_SavedUser =?";
            try(PreparedStatement artistStmt  = con.prepareStatement(SavedUserReq)){
                artistStmt.setString(1, savedUser.getName());
                artistStmt.setString(2, savedUser.getLastName());
                artistStmt.setString(3, savedUser.getDOB());
                artistStmt.setInt(4, savedUser.getId_SavedUser());
                artistStmt.executeUpdate();
            }

        }
        catch (SQLException e) {
            System.out.println("Error Updating artist: " + e.getMessage());
        }
    }

    @Override
    public void DELETE(SavedUser savedUser) throws SQLException {

        String UserReq = "DELETE FROM User WHERE Id_User = ?";
        try (PreparedStatement userstmt = con.prepareStatement(UserReq)){
            userstmt.setInt(1, savedUser.getId_SavedUser());
            userstmt.executeUpdate();


            //Delete Artist
            String artistReq = "DELETE FROM SavedUser WHERE Id_SavedUser = ?";
            try (PreparedStatement pre = con.prepareStatement(artistReq)) {
                pre.setInt(1, savedUser.getId_SavedUser());
                pre.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println("Error deleting SavedUser: " + e.getMessage());
        }

    }

    @Override
    public List<SavedUser> DISPLAY() throws SQLException {
        List<SavedUser> savedUsers = new ArrayList<>();
        String SavedUserReq = "SELECT SavedUser.Id_SavedUser, SavedUser.Name, SavedUser.LastName, SavedUser.DOB, User.Username, User.Email, User.Password, User.Role " +
                "FROM SavedUser INNER JOIN User ON SavedUser.Id_SavedUser = User.Id_User";
        try (PreparedStatement SavedUserStmt = con.prepareStatement(SavedUserReq)) {
            ResultSet res = SavedUserStmt.executeQuery();
            while (res.next()) {
                SavedUser savedUser = new SavedUser();
                //Artist
                savedUser.setId_SavedUser(res.getInt(1));
                savedUser.setName(res.getString(2));
                savedUser.setLastName(res.getString(3));
                savedUser.setDOB(res.getString(4));
                //User
                savedUser.setUsername(res.getString(5));
                savedUser.setEmail(res.getString(6));
                savedUser.setPassword(res.getString(7));
                savedUser.setRole(res.getString(8));
                savedUsers.add(savedUser);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return savedUsers;
    }
}
