package services;
import entities.ForumEntity;
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

    //PostEntity(int id_Post, int id_Form, int id_User, String title, String description, int comments_Nbr, int like_Nbr)
    @Override
    public void ajouter(PostEntity postEntity) throws SQLException {
//        String req = "insert into post (id_Forum,id_User,title,description,date,comments_Nbr,like_Nbr)"+
//                "values ('"+postEntity.getId_Forum()+"','"+postEntity.getId_User()+"','"+postEntity.getTitle()+"','"+postEntity.getDescription()+"','"+postEntity.getDate()+"','"+0+"','"+0+"')";
//        Statement ste = con.createStatement();
//        ste.executeUpdate(req);

        String req="Insert into post(id_Forum,id_User,title,description,date,comments_Nbr,like_Nbr) values(?,?,?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,postEntity.getId_Forum());
        pre.setInt(2,postEntity.getId_User());
        pre.setString(3,postEntity.getTitle());
        pre.setString(4,postEntity.getDescription());
        java.sql.Date mySQLDate = new java.sql.Date(postEntity.getDate().getTime());
        pre.setDate(5,mySQLDate);
        pre.setInt(6,postEntity.getComments_Nbr());
        pre.setInt(7,postEntity.getLike_Nbr());
        pre.executeUpdate();
    }

    @Override
    public void modifier(PostEntity postEntity) throws SQLException {
        String req = "Update post set id_forum=? , id_user=? , title=? , description=? , date=? , comments_Nbr=? , like_Nbr=?  where id_post=?";

        PreparedStatement pre = con.prepareStatement(req);

        pre.setInt(1,postEntity.getId_Forum());
        pre.setInt(2,postEntity.getId_User());
        pre.setString(3,postEntity.getTitle());
        pre.setString(4,postEntity.getDescription());
        java.sql.Date mySQLDate = new java.sql.Date(postEntity.getDate().getTime());
        pre.setDate(5,mySQLDate);
        pre.setInt(6,postEntity.getComments_Nbr());
        pre.setInt(7,postEntity.getLike_Nbr());

        pre.setInt(8,postEntity.getId_Post());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(PostEntity postEntity) throws SQLException{
        String req ="DELETE FROM post where id_post="+postEntity.getId_Post()+";";
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
            f.setId_Post(res.getInt(1));
            f.setId_Forum(res.getInt(2));
            f.setId_User(res.getInt(3));
            f.setTitle(res.getString(4));
            f.setDescription(res.getString(5));
            f.setDate(res.getDate(6));
            f.setComments_Nbr(res.getInt(7));
            f.setLike_Nbr(res.getInt(8));
            posts.add(f);
        }

        return posts;
    }
}
