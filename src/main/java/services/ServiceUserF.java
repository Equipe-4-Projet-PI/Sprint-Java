package services;

import entities.UserEntity;
import utils.MyDBF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUserF implements IServiceF<UserEntity> {
    private Connection con;

    public ServiceUserF(){
        con = MyDBF.getInstance().getConnection();
    }
    @Override
    public void ajouter(UserEntity  userEntity) throws SQLException {
        String req="Insert into user(name,role) values(?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,userEntity.getUsername());
        pre.setString(2,userEntity.getRole());
        pre.executeUpdate();
    }

    @Override
    public void modifier(UserEntity userEntity) throws SQLException {
        String req = "Update user set name=?,role=?  where user_id=?";

        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1,userEntity.getUsername());
        pre.setString(2,userEntity.getRole());
        pre.setInt(3,userEntity.getUser_id());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(UserEntity userEntity) throws SQLException {
        String req ="DELETE FROM user where user_id="+userEntity.getUser_id()+";";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public List<UserEntity> afficher() throws SQLException {
        //Create List
        List<UserEntity> posts = new ArrayList<>();
        //
        String req = "SELECT * from user";

        PreparedStatement pre = con.prepareStatement(req);

        ResultSet res = pre.executeQuery();

        while(res.next())
        {
            UserEntity f = new UserEntity();
            f.setUser_id(res.getInt(1));
            f.setUsername(res.getString(2));
            posts.add(f);
        }

        return posts;
    }
    public UserEntity getbyid(int id) throws SQLException {
        UserEntity f = null;
        String req = "SELECT * FROM user WHERE user_id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            f = new UserEntity();
            f.setUser_id(res.getInt("user_id"));
            f.setUsername(res.getString("name"));
            f.setRole(res.getString("role"));
        }
        return f;
    }


}
