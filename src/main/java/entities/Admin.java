package entities;

public class Admin extends User{

    int Id_Admin;
    String Name , LastName;
    String DOB ;

    public Admin(int id_User, String username, String email, String password, String role, int Id_Admin, String name, String lastName, String DOB) {
        super(id_User, username, email, password, role);
        this.Id_Admin = Id_Admin;
        Name = name;
        LastName = lastName;
        this.DOB = DOB;
    }

    public Admin(String username, String email, String password, String role, String name, String lastName, String DOB) {
        super(username, email, password, role);
        Name = name;
        LastName = lastName;
        this.DOB = DOB;
    }

    public Admin() {
    }
    public String toString() {
        return
                "User{" +
                        "AdminID=" + Id_Admin +
                        ", Name=" + Name +
                        ", LastName ='" + LastName + '\'' +
                        ", DOB='" + DOB + '\'' +
                        '}';
    }

    public int getId_Admin() {
        return Id_Admin;
    }

    public void setId_Admin(int Id_Admin) {
        this.Id_Admin = Id_Admin;
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
