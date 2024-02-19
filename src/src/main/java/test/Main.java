package test;

import entities.User;
import services.ServiceUser;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Create a sample Artist object
        //User user1 = new User("Yassine","Yassine@gmail.com","Password","Artist","Yassine","Manai","Tunis","10/10/2005");
        //User user2 = new User("Ali","aLI@gmail.com","Password","Artist","ALI","Msahli","Tunis","18/03/2003");
        //User user3 = new User("Hamdi","Hamdi@gmail.com","Password","Member","Hamdi","ben Hamdi","Tunis","18/03/2003");

        //User updateTest = new User(13,"Ali","aLI@gmail.com","Password","Member","ALI","Msahli","Tunis","18/03/2003");
        User DeleteTest = new User(13);
        // Create an instance of ServiceArtist
        ServiceUser serviceUser = new ServiceUser();

        try {
            // ADD
          /* serviceUser.ADD(user1);
            System.out.println("User added successfully.");
            serviceUser.ADD(user2);
            System.out.println("User added successfully.");
            serviceUser.ADD(user3);
            System.out.println("User added successfully.");*/

            // DELETE
          /* serviceUser.DELETE(DeleteTest);
            System.out.println("User Deleted Succefully");*/



            //UPDATE
            /*serviceUser.UPDATE(updateTest);
            System.out.println("User Updated Succefully");*/

          //DISPLAY

            System.out.println(serviceUser.DISPLAY());
            System.out.println(serviceUser.DISPLAYARTIST());



        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }

        if (serviceUser.authenticateUser("Yassine", "passwordo")) {
            System.out.println("Authentication successful!");
        } else {
            System.out.println("Authentication failed. Invalid username or password.");
        }
    }
}
