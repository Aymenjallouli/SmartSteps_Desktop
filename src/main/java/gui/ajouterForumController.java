package gui;

import entities.Forum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import services.ForumService;
import utils.MyDB;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class ajouterForumController implements Initializable {

    @FXML
    private AnchorPane ajouterForum;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private TextArea txtDescriptionForum;

    @FXML
    private TextField txtTitreForum;

    @FXML
    private Button btnImport;

    @FXML
    private ImageView imgInput;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private File selectedImageFile;
    private String imageName = null ;


    @FXML
    void ajouterImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imgInput.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imgInput.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "uploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        }
    }

    @FXML
    void AjouterForum(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAjouter){
            if (txtTitreForum.getText().isEmpty() || txtDescriptionForum.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Forum.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                ajoutForum();
                send_SMS();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre forum a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();

                clearFieldsForum();
            }
        }
        if(event.getSource() == btnAnnuler){
            clearFieldsForum();
        }
    }

    @FXML
    void clearFieldsForum() {
        txtTitreForum.clear();
        txtDescriptionForum.clear();
    }


    void ajoutForum(){
        String titreForum = txtTitreForum.getText();
        String img = imageName;
        Date dateCreation = null;
        try {
            // Get the current system date
            LocalDate currentDate = LocalDate.now();
            // Convert LocalDate to ZonedDateTime
            ZonedDateTime zonedDateTime = currentDate.atStartOfDay(ZoneId.systemDefault());
            // Convert ZonedDateTime to Instant
            Instant instant = zonedDateTime.toInstant();
            // Convert Instant to Date
            dateCreation = Date.from(instant);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String descriptionForum = txtDescriptionForum.getText();

        MyDB db = MyDB.getInstance();
        Forum forum = new Forum(
                titreForum, img, dateCreation, descriptionForum);
        ForumService fs = new ForumService();
        fs.ajouter(forum);
    }

    void send_SMS (){
        // Initialisation de la bibliothèque Twilio avec les informations de votre compte
        String ACCOUNT_SID = "AC1181a9c37f5f02f520c0d0a5aa9809ab";
        String AUTH_TOKEN = "86f1be2c90331fb3482989f4e8a5254d";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String recipientNumber = "+21625985364";
        String message = "Bonjour Mr ,\n"
                + "Nous sommes ravis de vous informer qu'un forum a été ajouté.\n "
                + "Veuillez contactez l'administration pour plus de details.\n "
                + "Merci de votre fidélité et à bientôt.\n"
                + "Cordialement";

        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+16185075229"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
}
