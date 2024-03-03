package entities;

public class UserEntity {
    int user_id;
    String username;
    String role;

    public UserEntity() {

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserEntity(int user_id, String username, String role) {
        this.user_id = user_id;
        this.username = username;
        this.role = role;
    }

    public UserEntity(int user_id, String username) {
        this.user_id = user_id;
        this.username = username;
    }

    public UserEntity(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
