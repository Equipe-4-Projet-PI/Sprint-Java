package entities;

public class Artist extends User {
    int Id_Artist;
    String Name , LastName;
    String DOB ;

    public Artist(int id_User, String username, String email, String password, String role,String name, String lastName, String DOB) {
        super(id_User, username, email, password, role);
        Id_Artist = id_User;
        Name = name;
        LastName = lastName;
        this.DOB = DOB;
    }

    public Artist(String username, String email, String password, String role, String name, String lastName, String DOB) {
        super(username, email, password, role);

        Name = name;
        LastName = lastName;
        this.DOB = DOB;
    }

    public Artist() {
    }

    public Artist(int id_User){
        Id_Artist = id_User ;

    }
    public String toString() {
        return
                "Artist{" +
                        "ArtistID=" + Id_Artist +
                        ", Name=" + Name +
                        ", LastName ='" + LastName + '\'' +
                        ", DOB='" + DOB + '\'' +
                        ", Username='" + Username + '\'' +
                        ", Email='" + Email + '\'' +
                        ", Password='" + Password + '\'' +
                        ", Role='" + Role + '\'' +
                        '}';
    }

    public int getId_Artist() {
        return Id_Artist;
    }

    public void setId_Artist(int id_Artist) {
        Id_Artist = id_Artist;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

}
