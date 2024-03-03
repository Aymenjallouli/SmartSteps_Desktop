package Controller;

import entities.Commentaire;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.CommentaireService;
import services.ForumService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class itemCommentaireController implements Initializable {

    @FXML
    private Button btnModifierCommentaire;

    @FXML
    private Button btnSupprimerCommentaire;

    @FXML
    private AnchorPane itemCommentairePane;

    @FXML
    private Label labelDescriptionCommentaire;

    @FXML
    private Label labelForumCommentaire;


    Commentaire commentaire;
    public void setData (Commentaire commentaire){
        this.commentaire = commentaire ;

        labelDescriptionCommentaire.setText(commentaire.getDescription());
        labelForumCommentaire.setText(String.valueOf(commentaire.getId_forum()));
    }


    public Commentaire getData (Commentaire commentaire){
        this.commentaire = commentaire ;
        return this.commentaire;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_UpdateCommentaire(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/updateCommentaire.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Commentaire");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void supprimerCommentaire(ActionEvent event) throws SQLException {
        CommentaireService sc = new CommentaireService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce commentaire ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID du commentaire sélectionnée

            int id = this.commentaire.getId();

            // Supprimer le commentaire de la base de données
            sc.supprimer(id);
        }
    }
}
