package services;

import entities.ForumEntity;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceForum implements IService<ForumEntity>{

    private Connection con;

    public ServiceForum(){
        con = MyDB.getInstance().getConnection();
    }
    @Override
    public void ajouter(ForumEntity forumEntity) throws SQLException {
        String req="INSERT INTO forum (name)"+"values('"+forumEntity.getName()+"')";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void modifier(ForumEntity forumEntity) throws SQLException {
        String req = "Update forum set name=? where id_forum=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,forumEntity.getName());
        pre.setInt(2,forumEntity.getId_Forum());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(ForumEntity forumEntity) throws SQLException{
        String req ="DELETE FROM forum where id_forum="+forumEntity.getId_Forum()+";";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public List<ForumEntity> afficher() throws SQLException {
        //Create List
        List<ForumEntity> forums = new ArrayList<>();
        //
        String req = "SELECT * from forum";

        PreparedStatement pre = con.prepareStatement(req);

        ResultSet res = pre.executeQuery();

        while(res.next())
        {
            ForumEntity f = new ForumEntity();
            f.setId_Forum(res.getInt(1));
            f.setName(res.getString("Name"));
            forums.add(f);
        }

        return forums;
    }
}
