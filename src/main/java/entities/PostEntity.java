package entities;

import java.util.Date;

public class PostEntity {
    private int id_Post, id_Forum, id_User;
    private String title,description;
    private int comments_Nbr, like_Nbr;
    private Date date;



    //Constructors :
    public PostEntity(){
    };
    public PostEntity(int id_Post, int id_Forum, int id_User, String title, String description, int comments_Nbr, int like_Nbr) {
        this.id_Post = id_Post;
        this.id_Forum = id_Forum;
        this.id_User = id_User;
        this.title = title;
        this.description = description;
        this.comments_Nbr = comments_Nbr;
        this.like_Nbr = like_Nbr;

        Date now = new Date();
        this.date = now;
    }
    public PostEntity(int id_Post, int id_Forum, int id_User, String title, String description) {
        this.id_Post = id_Post;
        this.id_Forum = id_Forum;
        this.id_User = id_User;
        this.title = title;
        this.description = description;
        this.comments_Nbr = 0;
        this.like_Nbr = 0;

        Date now = new Date();
        this.date = now;
    }

    public PostEntity(int id_Forum, int id_User, String title, String description, int comments_Nbr, int like_Nbr) {
        this.id_Forum = id_Forum;
        this.id_User = id_User;
        this.title = title;
        this.description = description;
        this.comments_Nbr = comments_Nbr;
        this.like_Nbr = like_Nbr;
        Date now = new Date();
        this.date = now;
    }
    public PostEntity(int id_Forum, int id_User, String title, String description) {
        this.id_Forum = id_Forum;
        this.id_User = id_User;
        this.title = title;
        this.description = description;
        this.comments_Nbr = 0;
        this.like_Nbr = 0;
        Date now = new Date();
        this.date = now;
    }
    //Getters & Setters :
    public int getId_Post() {
        return id_Post;
    }

    public void setId_Post(int id_Post) {
        this.id_Post = id_Post;
    }

    public int getId_Forum() {
        return id_Forum;
    }

    public void setId_Forum(int id_Forum) {
        this.id_Forum = id_Forum;
    }
    public int getId_User() {
        return id_User;
    }
    public void setId_User(int id_User) {
        this.id_User = id_User;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getComments_Nbr() {
        return comments_Nbr;
    }
    public void setComments_Nbr(int comments_Nbr) {
        this.comments_Nbr = comments_Nbr;
    }
    public int getLike_Nbr() {
        return like_Nbr;
    }
    public void setLike_Nbr(int like_Nbr) {
        this.like_Nbr = like_Nbr;
    }

    // ToString

    @Override
    public String toString() {
        return "PostEntity{" +
                "id_Post=" + id_Post +
                ", id_Forum=" + id_Forum +
                ", id_User=" + id_User +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", comments_Nbr=" + comments_Nbr +
                ", like_Nbr=" + like_Nbr +
                '}';
    }
}
