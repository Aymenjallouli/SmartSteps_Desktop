package gui;

import entities.Forum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ForumService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class itemForumController implements Initializable {

    @FXML
    private Button btnModifierForum;

    @FXML
    private Button btnSupprimerForum;

    @FXML
    private AnchorPane itemForumPane;

    @FXML
    private Label labelDateForum;

    @FXML
    private Label labelDescriptionForum;

    @FXML
    private Label labelTitreForum;

    @FXML
    private ImageView imgLabel;


    Forum forum;
    public void setData (Forum forum){
        this.forum = forum ;

        labelTitreForum.setText(forum.getTitre());
        labelDateForum.setText(String.valueOf(forum.getDateCreation()));
        labelDescriptionForum.setText(forum.getDescription());
        imgLabel.setImage(new Image("file:///" + "C:\\Users\\HP\\IdeaProjects\\AzizForum\\AzizForum\\ForumAziz\\src\\main\\java\\uploads\\"+forum.getImage()));
    }


    public Forum getData (Forum forum){
        this.forum = forum ;
        return this.forum;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_UpdateForum(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("updateForum.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Forum");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void supprimerForum(ActionEvent event) throws SQLException {
        ForumService sc = new ForumService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce forum ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de le forum sélectionnée

            int id = this.forum.getId();

            // Supprimer le forum de la base de données
            sc.supprimer(id);
        }
    }
}
