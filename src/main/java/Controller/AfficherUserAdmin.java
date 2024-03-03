package Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AfficherUserAdmin implements Initializable {

    @FXML
    private TableColumn<User, Integer> active;

    @FXML
    private TableColumn<User, Date> date;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, String> nom;

    @FXML
    private TableColumn<User, String> prenom;

    @FXML
    private TableColumn<User, String> role;

    @FXML
    private TableColumn<User, Integer> tel;
    @FXML
    private TableView<User> table;
    @FXML
    private TextField rech;

    public static int idU ;

    private final ObservableList<User> UserData = FXCollections.observableArrayList();

    ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<User> listb= new ArrayList<User>();

            listb = serviceUser.afficher();

            System.out.println("hello"+listb);
            UserData.clear();
            UserData.addAll(listb);
            table.setItems(UserData);

            nom.setCellValueFactory
                    (
                            new PropertyValueFactory<>("nomUser")
                    );
            email.setCellValueFactory
                    (
                            new PropertyValueFactory<>("email")
                    );
            prenom.setCellValueFactory
                    (
                            new PropertyValueFactory<>("prenomUser")
                    );
            role.setCellValueFactory
                    (
                            new PropertyValueFactory<>("role")
                    );
            date.setCellValueFactory
                    (
                            new PropertyValueFactory<>("dateNai")
                    );
            active.setCellValueFactory
                    (
                            new PropertyValueFactory<>("isEnabled")
                    );
            tel.setCellValueFactory
                    (
                            new PropertyValueFactory<>("num_tel")
                    );

        } catch (SQLDataException ex) {
            Logger.getLogger(AfficherUserAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void back(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AdminFirstPage.fxml")));
            Stage myWindow = (Stage) table.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("page name");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    void rechercher(ActionEvent event) throws SQLException {


        nom.setCellValueFactory
                (
                        new PropertyValueFactory<>("nomUser")
                );
        email.setCellValueFactory
                (
                        new PropertyValueFactory<>("email")
                );
        prenom.setCellValueFactory
                (
                        new PropertyValueFactory<>("prenomUser")
                );
        role.setCellValueFactory
                (
                        new PropertyValueFactory<>("role")
                );
        date.setCellValueFactory
                (
                        new PropertyValueFactory<>("dateNai")
                );
        active.setCellValueFactory
                (
                        new PropertyValueFactory<>("isEnabled")
                );
        tel.setCellValueFactory
                (
                        new PropertyValueFactory<>("num_tel")
                );




        //tableview.setItems(observablelist);

        FilteredList<User> filtredData= new FilteredList<>(UserData, b ->true);
        rech.textProperty().addListener((observable,oldValue,newValue) -> {
            Predicate<? super User> Reservation;
            filtredData.setPredicate((User user) -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if( user.getEmail().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else if (user.getNomUser().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }

                else
                    return false;
            } );
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<User> sortedData = new SortedList<>(filtredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);




    }


    private void resetTableData() throws SQLException {
        List<User> lisre = new ArrayList<>();
        lisre = serviceUser.afficher() ;
        ObservableList<User> data = FXCollections.observableArrayList(lisre);
        table.setItems(data);
    }


    @FXML
    void block(ActionEvent event) throws SQLException {
        User userSelec = table.getSelectionModel().getSelectedItem();
        idU = userSelec.getIdUser();
        if (serviceUser.getUserById(idU).getIsEnabled() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "L'utiliasteur est blocké déja", ButtonType.OK);
            alert.showAndWait();
        }else {
            serviceUser.block(idU);
            sendSMS("+21699687215");
            resetTableData();
        }
    }

    @FXML
    void deblock(ActionEvent event) throws SQLException {
        User userSelec = table.getSelectionModel().getSelectedItem();
        idU = userSelec.getIdUser();
        if (serviceUser.getUserById(idU).getIsEnabled() == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "L'utiliasteur est Activé déja", ButtonType.OK);
            alert.showAndWait();
        }else {
            serviceUser.Deblock(idU);
            sendSMS("+21699687215");
            resetTableData();
        }
    }


    @FXML
    void stat(ActionEvent event) {

        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Statistique.fxml")));
            Stage myWindow = new Stage();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Statistique");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    void sendSMS(String recipientNumber) {
        String ACCOUNT_SID = "AC1358688ef4a8790eb174dd42165d2837";
        String AUTH_TOKEN = "790e85b1f785ce8e64f2631789f89af1";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String message = "Bonjour Mr(s),\n"
                + "Votre Compt est blocker.\n "
                + "SmartSteps";

        // Send the SMS message
        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+12524295345"),
                message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());

    }
}
