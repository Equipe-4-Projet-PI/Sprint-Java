package test;

import entities.Artist;
import entities.SavedUser;
import entities.User;
import services.ServiceArtist;
import services.ServiceSavedUser;
import utils.MyDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Create a sample Artist object
        Artist artist = new Artist("Yassine", "Yassine@gmail.com", "password", "Artist", "YASSINE", "MANAI", "10/10/2023");
        Artist artistDelete = new Artist(8);
        SavedUser savedUser = new SavedUser("Hamadi","Hamadi@gmail.com","123456","SavedUser","Hamadi","Hamouda","10/02/2023");
        SavedUser savedUserDelete = new SavedUser(9);
        Artist artistupdate = new Artist(10,"Yassinouyy", "Yassinaa@gmail.com", "password1234", "Artist", "YASSINEEEE", "MANAIrr", "10/10/2023");
        SavedUser savedUserupdate = new SavedUser(11,"Hamadi123","Hamadi55@gmail.com","123456789","SavedUser","Hamadia","Hamoudass","10/02/2023");
        // Create an instance of ServiceArtist
        ServiceArtist serviceArtist = new ServiceArtist();
        ServiceSavedUser serviceSavedUser = new ServiceSavedUser();

        try {
            // ADD
            /*serviceArtist.ADD(artist);
            System.out.println("Artist added successfully.");
            serviceSavedUser.ADD(savedUser);
            System.out.println("SavedUser added successfully.");*/


            // DELETE
           /* serviceArtist.DELETE(artistDelete);
            System.out.println("Artist Deleted Succefully");
            serviceSavedUser.DELETE(savedUserDelete);
            System.out.println("SavedUser Deleted Succefully");*/


            //UPDATE
            /*serviceArtist.UPDATE(artistupdate);
            System.out.println("Artist Updated Succefully");
            serviceSavedUser.UPDATE(savedUserupdate);
            System.out.println("SavedUser Updated Succefully");*/

            //DISPLAY
            System.out.println(serviceArtist.DISPLAY());
            System.out.println(serviceSavedUser.DISPLAY());


        } catch (SQLException e) {
            System.out.println("Error deleting artist: " + e.getMessage());
        }
    }
}
