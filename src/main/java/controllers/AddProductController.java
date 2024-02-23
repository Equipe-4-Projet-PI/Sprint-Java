package controllers;

import entities.Product;
import entities.ProductOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;
import services.ServiceOrder;
import services.ServiceProduct;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddProductController {
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = currentDate.format(formatter);
    ServiceProduct s = new ServiceProduct();
    ServiceOrder o = new ServiceOrder();
    int userid,userid_ed,pr_pid;
    double price,price_ed;
    @FXML
    private TableColumn<Product, String> col_date;

    @FXML
    private TableColumn<Product, String> col_desc;

    @FXML
    private TableColumn<Product, Integer> col_fsale;

    @FXML
    private TableColumn<Product, Integer> col_id;

    @FXML
    private TableColumn<Product, Double> col_price;

    @FXML
    private TableColumn<Product, String> col_title;

    @FXML
    private TableColumn<Product, Integer> col_uid;

    @FXML
    private TableView<Product> tv_product;

    @FXML
    private Pane pr_add_panel;

    @FXML
    private Pane pr_command_panel;

    @FXML
    private TextArea pr_desc;

    @FXML
    private RadioButton pr_oui;

    @FXML
    private TextField pr_price;

    @FXML
    private Pane pr_show_panel;

    @FXML
    private TextField pr_title;

    @FXML
    private TextField pr_userid;

    @FXML
    private TableColumn<ProductOrder, String> com_date;

    @FXML
    private TableColumn<ProductOrder, Integer> com_id;

    @FXML
    private TableColumn<ProductOrder, Integer> com_pid;

    @FXML
    private TableColumn<ProductOrder, Double> com_price;

    @FXML
    private TableColumn<ProductOrder, String> com_title;
    @FXML
    private TableView<ProductOrder> tv_command;


    @FXML
    private Pane pr_modify_panel;
    @FXML
    private TextArea pr_desc_ed;
    @FXML
    private RadioButton pr_oui_ed;
    @FXML
    private TextField pr_price_ed;
    @FXML
    private TextField pr_title_ed;
    @FXML
    private TextField pr_userid_ed;
    @FXML
    private TextField pr_prodid_ed;
    int forsalevalue(){
        if (pr_oui.isSelected()){
            return 1;
        }
        else return 0;
    }

    @FXML
    void AddProduct(ActionEvent event) {
        try{
         userid = Integer.parseInt(pr_userid.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        try{
            price = Double.parseDouble(pr_price.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        try {
            s.ajouter(new Product(userid,forsalevalue(),price,pr_title.getText(),pr_desc.getText(),formattedDate));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succes");
            alert.setContentText("product added");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        clear();
    }

    void clear(){
        pr_userid.setText("");
        pr_title.setText("");
        pr_desc.setText("");
        pr_price.setText("");
        pr_oui.setSelected(false);
    }
    @FXML
    void CancelAddProduct(ActionEvent event) {
        clear();
    }

    public void DisplayAddBtn(MouseEvent mouseEvent) {
        pr_add_panel.setVisible(true);
        pr_show_panel.setVisible(false);
        pr_command_panel.setVisible(false);
        pr_modify_panel.setVisible(false);

    }
    void initializeCom(){
        try {
            ObservableList<ProductOrder> observableList= FXCollections.observableList(o.afficher());
            tv_command.setItems(observableList);
            com_id.setCellValueFactory(new PropertyValueFactory<>("Id_Order"));
            com_pid.setCellValueFactory(new PropertyValueFactory<>("Id_Product"));
            com_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
            com_price.setCellValueFactory(new PropertyValueFactory<>("Price"));
            com_date.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void DisplayCommandBtn(MouseEvent event) {
        pr_command_panel.setVisible(true);
        pr_show_panel.setVisible(false);
        pr_add_panel.setVisible(false);
        pr_modify_panel.setVisible(false);

        initializeCom();
    }

    void initialize(){
        try {
            ObservableList<Product> observableList= FXCollections.observableList(s.afficher());
            tv_product.setItems(observableList);
            col_id.setCellValueFactory(new PropertyValueFactory<>("Id_Product"));
            col_uid.setCellValueFactory(new PropertyValueFactory<>("Id_User"));
            col_title.setCellValueFactory(new PropertyValueFactory<>("Title"));
            col_desc.setCellValueFactory(new PropertyValueFactory<>("Description"));
            col_fsale.setCellValueFactory(new PropertyValueFactory<>("ForSale"));
            col_price.setCellValueFactory(new PropertyValueFactory<>("Price"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void DisplayShowBtn(MouseEvent event) {
        pr_show_panel.setVisible(true);
        pr_add_panel.setVisible(false);
        pr_command_panel.setVisible(false);
        pr_modify_panel.setVisible(false);

        initialize();

    }


    @FXML
    void AddCommand(ActionEvent event) {
        Product selectedItem = tv_product.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            int prod_id = selectedItem.getId_Product();
            double price = selectedItem.getPrice();
            String title = selectedItem.getTitle();
            String date = formattedDate;
            int forsale = selectedItem.getForSale();
            if (forsale!=0) {
                try {
                    o.ajouter(new ProductOrder(prod_id, title, date, price));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                pr_command_panel.setVisible(true);
                pr_show_panel.setVisible(false);
                pr_add_panel.setVisible(false);
                pr_modify_panel.setVisible(false);

                initializeCom();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Forbidden action");
                alert.setContentText("This item is Not for sale");
                alert.showAndWait();
            }
        }
    }
    @FXML
    void CancelOrder(ActionEvent event) {
        ProductOrder selectedItem = tv_command.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            try {
                o.supprimer(selectedItem);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initializeCom();
        }
    }

    @FXML
    void PayOrder(ActionEvent event) {
        ProductOrder selectedItem = tv_command.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stripe API Portal");
            alert.setContentText("i will add here the payement methode");
            alert.showAndWait();
        }
    }

    @FXML
    void ModifyArt(ActionEvent event) {
        Product selectedItem = tv_product.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            String id_usr = String.valueOf(selectedItem.getId_User());
            String title = selectedItem.getTitle();
            String desc = selectedItem.getDescription();
            int fsale = selectedItem.getForSale();
            String price = String.valueOf(selectedItem.getPrice());
            String id_prod = String.valueOf(selectedItem.getId_Product());

            pr_prodid_ed.setText(id_prod);
            pr_userid_ed.setText(id_usr);
            pr_title_ed.setText(title);
            pr_desc_ed.setText(desc);
            if (fsale!=0){
                pr_oui_ed.setSelected(true);
            }else {
                pr_oui_ed.setSelected(false);
            }
            pr_price_ed.setText(price);
        }

        pr_modify_panel.setVisible(true);
        pr_show_panel.setVisible(false);
        pr_add_panel.setVisible(false);
        pr_command_panel.setVisible(false);
    }

    @FXML
    void CancelEdit(ActionEvent event) {
        pr_userid_ed.setText("");
        pr_title_ed.setText("");
        pr_desc_ed.setText("");
        pr_price_ed.setText("");
        pr_oui_ed.setSelected(false);
        pr_modify_panel.setVisible(false);
        pr_show_panel.setVisible(true);
        pr_add_panel.setVisible(false);
        pr_command_panel.setVisible(false);
    }

    int forsalevalue_ed(){
        if (pr_oui_ed.isSelected()){
            return 1;
        }
        else return 0;
    }
    @FXML
    void EditSubmit(ActionEvent event) {
        try{
            userid_ed = Integer.parseInt(pr_userid_ed.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        try{
            pr_pid = Integer.parseInt(pr_prodid_ed.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        try{
            price_ed = Double.parseDouble(pr_price_ed.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        try {
            s.modifier(new Product(pr_pid,userid_ed,forsalevalue_ed(),price_ed,pr_title_ed.getText(),pr_desc_ed.getText(),formattedDate));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        pr_modify_panel.setVisible(false);
        pr_show_panel.setVisible(true);
        pr_add_panel.setVisible(false);
        pr_command_panel.setVisible(false);

    }
}
