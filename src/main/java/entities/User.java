package entities;

public class User {
int Id_User;
String Username , Email , Password, Role;
  ;

    public User(int id_User, String username, String email, String password, String role) {
        Id_User = id_User;
        Username = username;
        Email = email;
        Password = password;
        Role = role;
    }
    public User(String username, String email, String password, String role) {

        Username = username;
        Email = email;
        Password = password;
        Role = role;
    }
    public  User(){}


    @Override
    public String toString() {
        return
                "User{" +
                        "UserId=" + Id_User +
                        ", Username=" + Username +
                        ", Email ='" + Email + '\'' +
                        ", Role='" + Role + '\'' +
                        '}';
    }

    public int getId_User() {
        return Id_User;
    }

    public void setId_User(int id_User) {
        Id_User = id_User;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
