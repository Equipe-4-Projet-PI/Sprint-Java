package services;

import entities.User;
import utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ServiceUser implements IService<User> {

    private Connection conn;

    public ServiceUser(){
        conn = MyDB.getInstance().getConnection();
    }

    public List<User> DISPLAYARTIST() throws SQLException {
        List<User> users = new ArrayList<>();
        String artistReq = "SELECT User.Id_User, User.Username, User.Email, User.Password, User.Role, User.FirstName, User.Lastname, User.Adress,User.Phone ,User.Gender, User.DOB " +
                "FROM User WHERE User.Role = 'Artist'";
        try (PreparedStatement UserStmt = conn.prepareStatement(artistReq)) {
            ResultSet res = UserStmt.executeQuery();
            while (res.next()) {
                User user = new User();
                //Artist
                user.setId_User(res.getInt(1));
                user.setUsername(res.getString(2));
                user.setEmail(res.getString(3));
                user.setPassword(res.getString(4));
                //User
                user.setRole(res.getString(5));
                user.setFirstName(res.getString(6));
                user.setLastName(res.getString(7));
                user.setAdress(res.getString(8));
                user.setPhone(res.getInt(9));
                user.setGender(res.getString(10));
                user.setDOB(res.getString(11));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return users;

    }

    public List<User> DISPLAYMember() throws SQLException {
        List<User> users = new ArrayList<>();
        String artistReq = "SELECT User.Id_User, User.Username, User.Email, User.Password, User.Role, User.FirstName, User.Lastname, User.Adress,User.Phone ,User.Gender, User.DOB " +
                "FROM User WHERE User.Role = 'Member'";
        try (PreparedStatement UserStmt = conn.prepareStatement(artistReq)) {
            ResultSet res = UserStmt.executeQuery();
            while (res.next()) {
                User user = new User();
                //Artist
                user.setId_User(res.getInt(1));
                user.setUsername(res.getString(2));
                user.setEmail(res.getString(3));
                user.setPassword(res.getString(4));
                //User
                user.setRole(res.getString(5));
                user.setFirstName(res.getString(6));
                user.setLastName(res.getString(7));
                user.setAdress(res.getString(8));
                user.setPhone(res.getInt(9));
                user.setGender(res.getString(10));
                user.setDOB(res.getString(11));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return users;

    }
    public  boolean authenticateUser(String username, String password) {
        // SQL query to check if the provided username and password match a record in the database
        String UserReq = "SELECT * FROM User WHERE Username = ? AND Password = ?";

        try (PreparedStatement UserStmt = conn.prepareStatement(UserReq)) {
            UserStmt.setString(1, username);
            UserStmt.setString(2, password);

            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, authentication succeeds
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return false;
        }

    }

    public boolean checkAdmin(String username, String password) {
        // SQL query to check if the provided username and password match a record in the database and the role is "Admin"
        String UserReq = "SELECT * FROM User WHERE Username = ? AND Password = ? AND Role = ?";

        try (PreparedStatement UserStmt = conn.prepareStatement(UserReq)) {
            UserStmt.setString(1, username);
            UserStmt.setString(2, password);
            UserStmt.setString(3, "Admin"); // Set the role to "Admin"

            try (ResultSet resultSet = UserStmt.executeQuery()) {
                // If there is at least one matching record, authentication succeeds
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String UserReq = "INSERT INTO User (Username, Email, Password,Role,FirstName,Lastname,Adress,Phone,Gender,DOB) VALUES (?, ?, ?, ? , ?, ? , ? , ? , ? ,?)";
        try (PreparedStatement UserStmt = conn.prepareStatement(UserReq)) {
            UserStmt.setString(1, user.getUsername());
            UserStmt.setString(2, user.getEmail());
            UserStmt.setString(3, user.getPassword());
            UserStmt.setString(4, user.getRole());
            UserStmt.setString(5, user.getFirstName());
            UserStmt.setString(6, user.getLastName());
            UserStmt.setString(7, user.getAdress());
            UserStmt.setInt(8, user.getPhone());
            UserStmt.setString(9, user.getGender());
            UserStmt.setString(10, user.getDOB());

            UserStmt.executeUpdate();
        }

    }

    @Override
    public void modifier(User user) throws SQLException {
        String UserReq = "update User set Username=? , Email=? , Password=? , Role=? ,FirstName=? ,Lastname=? ,Adress=?  ,Phone=?  ,Gender=?,DOB=? where Id_User  =?";
        try(PreparedStatement artistStmt  = conn.prepareStatement(UserReq)){
            artistStmt.setString(1, user.getUsername());
            artistStmt.setString(2, user.getEmail());
            artistStmt.setString(3, user.getPassword());
            artistStmt.setString(4, user.getRole());
            artistStmt.setString(5, user.getFirstName());
            artistStmt.setString(6, user.getLastName());
            artistStmt.setString(7, user.getAdress());
            artistStmt.setInt(8, user.getPhone());
            artistStmt.setString(9, user.getGender());
            artistStmt.setString(10, user.getDOB());
            artistStmt.setInt(11, user.getId_User());
            artistStmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error Updating artist: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(User user) throws SQLException {
        String UserReq = "DELETE FROM User WHERE Id_User  = ?";
        try (PreparedStatement pre = conn.prepareStatement(UserReq)) {
            pre.setInt(1, user.getId_User());
            pre.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error deleting artist: " + e.getMessage());
        }
    }

    @Override
    public List<User> afficher() throws SQLException {
        List<User> users = new ArrayList<>();
        String artistReq = "SELECT User.Id_User, User.Username, User.Email, User.Password, User.Role, User.FirstName, User.Lastname, User.Adress ,User.Phone ,User.Gender , User.DOB " +
                "FROM User";
        try (PreparedStatement UserStmt = conn.prepareStatement(artistReq)) {
            ResultSet res = UserStmt.executeQuery();
            while (res.next()) {
                User user = new User();
                user.setId_User(res.getInt(1));
                user.setUsername(res.getString(2));
                user.setEmail(res.getString(3));
                user.setPassword(res.getString(4));
                user.setRole(res.getString(5));
                user.setFirstName(res.getString(6));
                user.setLastName(res.getString(7));
                user.setAdress(res.getString(8));
                user.setPhone(res.getInt(9));
                user.setGender(res.getString(10));
                user.setDOB(res.getString(11));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return users;
    }


    public User getbyid(int id) throws SQLException {
        User f = null;
        String req = "SELECT * FROM user WHERE Id_User=?";
        PreparedStatement pre = conn.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            f = new User();
            f.setId_User(res.getInt("Id_User"));
            f.setUsername(res.getString("Username"));
            f.setRole(res.getString("Role"));
        }
        return f;
    }


    public User getbyUsername(String username) throws SQLException {
        User f = null;
        String req = "SELECT * FROM user WHERE Username=?";
        PreparedStatement pre = conn.prepareStatement(req);
        pre.setString(1, username);
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            f = new User();
            f.setId_User(res.getInt("Id_User"));
            f.setUsername(res.getString("Username"));
            f.setRole(res.getString("Role"));
            f.setImageURL(res.getString("imageurl"));
//            f.= res.getString("ImageURL");
        }
        return f;
    }

    public int CountUsers() throws SQLException {
        String UserReq = "SELECT COUNT(*) FROM User";
        try (PreparedStatement pre = conn.prepareStatement(UserReq)) {
            try (ResultSet resultSet = pre.executeQuery()) {
                if (resultSet.next()) {
                    int total = resultSet.getInt(1);
                    return total;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error counting users: " + e.getMessage());
            // Optionally, you may throw an exception or return a default value here
        }
        return 0; // Return 0 if an error occurs
    }

    public List<User> DISPLAY() throws SQLException {
        List<User> users = new ArrayList<>();
        String artistReq = "SELECT User.Id_User, User.Username, User.Email, User.Password, User.Role, User.FirstName, User.Lastname, User.Adress ,User.Phone ,User.Gender , User.DOB , User.ImageURL " +
                "FROM User";
        try (PreparedStatement UserStmt = conn.prepareStatement(artistReq)) {
            ResultSet res = UserStmt.executeQuery();
            while (res.next()) {
                User user = new User();
                user.setId_User(res.getInt(1));
                user.setUsername(res.getString(2));
                user.setEmail(res.getString(3));
                user.setPassword(res.getString(4));
                user.setRole(res.getString(5));
                user.setFirstName(res.getString(6));
                user.setLastName(res.getString(7));
                user.setAdress(res.getString(8));
                user.setPhone(res.getInt(9));
                user.setGender(res.getString(10));
                user.setDOB(res.getString(11));
                user.setImageURL(res.getString(12));

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving artists: " + e.getMessage());
        }
        return users;

    }

    public void DELETEUser(int idUser) throws SQLException {
        String UserReq = "DELETE FROM User WHERE Id_User  = ?";
        try (PreparedStatement pre = conn.prepareStatement(UserReq)) {
            pre.setInt(1, idUser);
            pre.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Error deleting artist: " + e.getMessage());
        }
    }

}

