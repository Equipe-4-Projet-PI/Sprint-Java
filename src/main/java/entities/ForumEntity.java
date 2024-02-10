package entities;

public class ForumEntity {
    private  int id_Forum;
    private String name;

    public ForumEntity() {

    }

    public ForumEntity(String name) {
        this.name = name;
    }

    public ForumEntity(int id_Forum, String name) {
        this.id_Forum = id_Forum;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ForumEntity{" +
                "id_Forum=" + id_Forum +
                ", Name =" + name+
                '}';
    }

    public int getId_Forum() {
        return id_Forum;
    }

    public void setId_Forum(int id_Forum) {
        this.id_Forum = id_Forum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
