package entities;

public class SavedUser extends User{
    int Id_SavedUser;
    String Name , LastName;
    String DOB ;

    public SavedUser(int id_User, String username, String email, String password, String role,  String name, String lastName, String DOB) {
        super(id_User, username, email, password, role);
        Id_SavedUser = id_User;
        Name = name;
        LastName = lastName;
        this.DOB = DOB;
    }

    public SavedUser(String username, String email, String password, String role, String name, String lastName, String DOB) {
        super(username, email, password, role);
        Name = name;
        LastName = lastName;
        this.DOB = DOB;
    }

    public SavedUser() {
    }

    public SavedUser( int id_User) {
        Id_SavedUser = id_User ;
    }


    public String toString() {
        return
                "User{" +
                        "SavedUserID=" + Id_SavedUser +
                        ", Name=" + Name +
                        ", LastName ='" + LastName + '\'' +
                        ", DOB='" + DOB + '\'' +
                        ", Username='" + Username + '\'' +
                        ", Email='" + Email + '\'' +
                        ", Password='" + Password + '\'' +
                        ", Role='" + Role + '\'' +
                        '}';
    }

    public int getId_SavedUser() {
        return Id_SavedUser;
    }

    public void setId_SavedUser(int Id_SavedUser) {
        this.Id_SavedUser = Id_SavedUser;
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
