package controllers.Member;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import entities.ForumEntity;
import entities.PostEntity;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServicePostF;
import services.ServiceUser;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.embed.swing.SwingFXUtils;
import net.glxn.qrgen.javase.QRCode;

//PDF STUFF
import com.itextpdf.text.Font;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;

public class AddPostMembreController {

    @FXML
    private Button forumPage_id;
    private User userlogged;
    @FXML
    private Label forum_name_id;

    @FXML
    private VBox id_vbox_posts;

    private ForumEntity current_forum;

    //SERVICE POST
    ServicePostF sp = new ServicePostF();

    ServiceUser serviceUser= new ServiceUser();

    public void setData(ForumEntity forumEntity,User user)
    {

        this.current_forum = forumEntity;


        userlogged = new User();
        userlogged.setGender(user.getGender());
        userlogged.setDOB(user.getDOB());
        userlogged.setPhone(user.getPhone());
        userlogged.setAdress(user.getAdress());
        userlogged.setUsername(user.getUsername());
        userlogged.setEmail(user.getEmail());
        userlogged.setFirstName(user.getFirstName());
        userlogged.setPassword(user.getPassword());
        userlogged.setLastName(user.getLastName());
        userlogged.setId_User(user.getId_User());
        userlogged.setRole(user.getRole());
        userlogged.setImageURL(user.getImageURL());

    }
    private void SetDataAgain()
    {
        this.forum_name_id.setText(current_forum.getTitle());
    }

    @FXML
    void initialize()
    {
        try {
            //SET FORUM NAME
            SetDataAgain();
            DoMath();
            DoMoreMath();
            GenerateQR();
//            SetQr();

            box.getItems().setAll(choices);
            box.setValue("Oldest to Newest");

            ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

            // Filter the list based on the search text
            List<PostEntity> filteredList = observableList.stream()
                    .filter(e -> e.getId_forum() == current_forum.getId_forum())
                    .collect(Collectors.toList());

            //FILL THE LIST TO PRINT IF WANT TO PRINT
            ToPrintList = filteredList;
            // Load and display filtered data
            for (PostEntity f : filteredList) {
                if(f.getId_user() == userlogged.getId_User())
                {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumPages/Member/PostTemplateMembreOwner.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    PostTemplateMembreOwnerController cardController = fxmlLoader.getController();
                    cardController.setData(f,userlogged);
                    id_vbox_posts.getChildren().add(cardBox);
                }else {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumPages/Member/PostTemplateMembre.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    PostTemplateMembreController cardController = fxmlLoader.getController();
                    cardController.setData(f,userlogged);
                    id_vbox_posts.getChildren().add(cardBox);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void GotoforumPage(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Member/AfficherForumMembre.fxml"));
            forumPage_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    ///ADD BUTTON LOGIC

    @FXML
    private Button Add_Post_Butt_Id;
    @FXML
    void AddForum(ActionEvent event) {
        try{
            Parent root = loadRootLayoutForForum();
            Add_Post_Butt_Id.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/NewPostPageMembre.fxml"));
        NewPostPageMembreController controller = new NewPostPageMembreController();
        loader.setController(controller);
        controller.setData(current_forum,userlogged);
//        controller.initData(userlogged);
        Parent root = loader.load();
        return root;
    }

    //Statistics

    @FXML
    private Label top_likes_is;

    @FXML
    private Label top_post_user_id;

    private void DoMath() throws SQLException {
        ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

        // Filter the list based on the search text
        List<PostEntity> filteredList = observableList.stream()
                .filter(e -> e.getId_forum() == current_forum.getId_forum())
                .collect(Collectors.toList());
        Optional<PostEntity> mostLikedPost = filteredList.stream()
                .max(Comparator.comparingInt(PostEntity::getLike_num));
        if (mostLikedPost.isPresent())
        {
            top_likes_is.setText(""+mostLikedPost.get().getLike_num());
            top_post_user_id.setText(serviceUser.GetUserById(mostLikedPost.get().getId_user()).getUsername());
        }else {
            top_likes_is.setText("No One :(");
            top_post_user_id.setText("No One :(");
        }


    }

    @FXML
    private Label likes_tot_id;

    @FXML
    private Label posts_num_id;

    private void DoMoreMath() throws SQLException {
        ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

        // Filter the list based on the search text
        List<PostEntity> filteredList = observableList.stream()
                .filter(e -> e.getId_forum() == current_forum.getId_forum())
                .collect(Collectors.toList());

        int totalLikes = filteredList.stream()
                .mapToInt(PostEntity::getLike_num)
                .sum();
        long totalPosts = filteredList.size();

        likes_tot_id.setText(""+totalLikes);
        posts_num_id.setText(""+totalPosts);
    }

    /*======= ORDERING THE LIST =======*/
    @FXML
    private Button OrderButt;
    @FXML
    void OrderByLikeNumber(ActionEvent event) {
        try{
            // Retrieve the list of posts
            ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

            // Filter the list based on the current forum ID
            List<PostEntity> filteredList = observableList.stream()
                    .filter(e -> e.getId_forum() == current_forum.getId_forum())
                    .collect(Collectors.toList());

            // Order the posts by their rank number (likes number) in descending order
//            List<PostEntity> orderedPosts = filteredList.stream()
//                    .sorted(Comparator.comparingInt(PostEntity::getLike_num).reversed())
//                    .collect(Collectors.toList());


            String c = box.getValue().toLowerCase();
            Comparator<PostEntity> comparator = null;

            switch (c) {
                case "title":
                    comparator = Comparator.comparing(PostEntity::getTitle);
                    break;
                case "newest to oldest":
                    comparator = Comparator.comparing(PostEntity::getTime).reversed();
                    break;
                case "oldest to newest":
                    comparator = Comparator.comparing(PostEntity::getTime);
                    break;
                case "likes":
                    comparator = Comparator.comparing(PostEntity::getLike_num).reversed();
                    break;
                default:
                    break;
            }

            if (comparator != null) {
                // Sort the filteredList using the selected comparator
                filteredList.sort(comparator);
            }

            // Clear Dat VBox Mah Man
            id_vbox_posts.getChildren().clear();

            //FILL THE LIST TO PRINT IF WANT TO PRINT
            ToPrintList = filteredList;
            // Load and display filtered data
            for (PostEntity f : filteredList) {
                if(f.getId_user() == userlogged.getId_User())
                {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumPages/Member/PostTemplateMembreOwner.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    PostTemplateMembreOwnerController cardController = fxmlLoader.getController();
                    cardController.setData(f,userlogged);
                    id_vbox_posts.getChildren().add(cardBox);
                }else {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumPages/Member/PostTemplateMembre.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    PostTemplateMembreController cardController = fxmlLoader.getController();
                    cardController.setData(f,userlogged);
                    id_vbox_posts.getChildren().add(cardBox);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ChoiceBox<String> box;
    private String[] choices = {"Title","Oldest to Newest","Newest to oldest","Likes"};

    //PRINTING LOGIC
    @FXML
    private Button PrintPDF_butt;
    List<PostEntity> ToPrintList = new ArrayList<>();
    @FXML
    void PrintPDF(ActionEvent event) {
        if(ToPrintList != null)
        {
            Document document = new Document();
            document.setPageSize(PageSize.A4);

            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 21, Font.BOLD, BaseColor.BLACK);
            Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);

            Paragraph Ftitle = new Paragraph(this.forum_name_id.getText(), titleFont);
            Paragraph Fdesc = new Paragraph(this.current_forum.getDescription(), titleFont);
            Ftitle.setAlignment(Paragraph.ALIGN_CENTER);
            Fdesc.setAlignment(Paragraph.ALIGN_CENTER);

            try {
                PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
                document.open();
                document.add(Ftitle);
                document.add(Fdesc);
                for(PostEntity p : ToPrintList)
                {

                    Paragraph title = new Paragraph(p.getTitle(), titleFont);
                    title.setAlignment(Paragraph.ALIGN_LEFT);
                    document.add(title);

                    Paragraph user = new Paragraph(serviceUser.GetUserById(p.getId_user()).getUsername(), titleFont);
                    user.setAlignment(Paragraph.ALIGN_LEFT);
                    document.add(user);

                    Paragraph timestamp = new Paragraph(p.getTime().toString(), textFont);
                    timestamp.setAlignment(Paragraph.ALIGN_RIGHT);
                    document.add(timestamp);

                    Paragraph postText = new Paragraph(p.getText(), textFont);
                    postText.setAlignment(Paragraph.ALIGN_LEFT);
                    document.add(postText);

                    Paragraph likeNum = new Paragraph(""+p.getLike_num(), textFont);
                    likeNum.setAlignment(Paragraph.ALIGN_RIGHT);
                    document.add(likeNum);
                }

                document.close();
                System.out.println("PDF created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //QR
    @FXML
    private ImageView Qr_id;

    public void GenerateQR()
    {
        try {
            String data=current_forum.toString();
            String path="QrGen.jpg";
            BitMatrix matrix = null;
            matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,150,150);
            MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));

            // Convert BitMatrix to BufferedImage
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "JPG", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            javafx.scene.image.Image image = new Image(inputStream);
            Qr_id.setImage(image);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void SetQr()
    {
        Image img = new Image("QrGen.jpg");
        Qr_id.setImage(img);
    }

}
