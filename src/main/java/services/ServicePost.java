package services;

import entities.PostEntity;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePost implements IService<PostEntity>{
    private Connection con;

    public ServicePost(){
        con = MyDB.getInstance().getConnection();
    }
    @Override
    public void ajouter(PostEntity postEntity) throws SQLException {
        String req="Insert into post(id_Forum,id_User,textmessage,like_number,timeofcreation,dateofcreation) values(?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,postEntity.getId_forum());
        pre.setInt(2,postEntity.getId_user());
        pre.setString(3,postEntity.getText());
        pre.setInt(4,postEntity.getLike_num());
        pre.setTimestamp(5,postEntity.getTime());
        java.sql.Date mySQLDate = new java.sql.Date(postEntity.getDate().getTime());
        pre.setDate(6,mySQLDate);
        pre.executeUpdate();
    }

    @Override
    public void modifier(PostEntity postEntity) throws SQLException {
        String req = "Update post set id_forum=? , id_user=? ,textmessage=?,like_number=?,timeofcreation=?,dateofcreation=?  where id_post=?";

        PreparedStatement pre = con.prepareStatement(req);

        pre.setInt(1,postEntity.getId_forum());
        pre.setInt(2,postEntity.getId_user());
        pre.setString(3,postEntity.getText());
        pre.setInt(4,postEntity.getLike_num());
        pre.setTimestamp(5,postEntity.getTime());
        java.sql.Date mySQLDate = new java.sql.Date(postEntity.getDate().getTime());
        pre.setDate(6,mySQLDate);

        pre.setInt(7,postEntity.getId_post());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(PostEntity postEntity) throws SQLException {
        String req ="DELETE FROM post where id_post="+postEntity.getId_post()+";";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public List<PostEntity> afficher() throws SQLException {
        //Create List
        List<PostEntity> posts = new ArrayList<>();
        //
        String req = "SELECT * from post";

        PreparedStatement pre = con.prepareStatement(req);

        ResultSet res = pre.executeQuery();

        while(res.next())
        {
            PostEntity f = new PostEntity();
            f.setId_post(res.getInt(1));
            f.setId_forum(res.getInt(2));
            f.setId_user(res.getInt(3));
            f.setText(res.getString(4));
            f.setLike_num(res.getInt(5));
            f.setTime(res.getTimestamp(6));
            f.setDate(res.getDate(7));

            posts.add(f);
        }

        return posts;
    }
}
