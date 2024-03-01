package gui;

import entities.Forum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import services.ForumService;

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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class updateForumController implements Initializable {

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

    @FXML
    private TextArea txtDescriptionForum;

    @FXML
    private TextField txtTitreForum;

    @FXML
    private AnchorPane updateForum;

    @FXML
    private ImageView imgInput;



    private int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ForumService cs = new ForumService();
        List<Forum> forums = cs.Show();

        for(int i=0;i<forums.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemForum.fxml"));

            try {
                AnchorPane anchorPane = fxmlLoader.load();
                HBox hBox = (HBox) anchorPane.getChildren().get(0);
                itemForumController itemController = fxmlLoader.getController();
                txtTitreForum.setText(itemController.getData(forums.get(i)).getTitre());
                txtDescriptionForum.setText(itemController.getData(forums.get(i)).getDescription());
                this.id=itemController.getData(forums.get(i)).getId();
            } catch (IOException ex) {
                Logger.getLogger(itemForumController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    void clearFieldsForum() {
        txtTitreForum.clear();
        txtDescriptionForum.clear();
    }

    @FXML
    void updateForum(ActionEvent event) {
        if (txtTitreForum.getText().isEmpty() || txtDescriptionForum.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre forum.");
            Optional<ButtonType> option = alert.showAndWait();

        } else {
            modifForum();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre forum a été modifié avec succès.");
            Optional<ButtonType> option = alert.showAndWait();
        }
    }

    void modifForum(){
        String titre = txtTitreForum.getText();
        String img = imageName;
        Date date = null;
        try {
            LocalDate currentDate = LocalDate.now();
            ZonedDateTime zonedDateTime = currentDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            date = Date.from(instant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String description = txtDescriptionForum.getText();


        Forum c = new Forum(
                id,
                titre,img, date, description);
        ForumService sc = new ForumService();
        sc.modifier(c);
    }
}
