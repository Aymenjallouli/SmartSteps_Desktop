package gui;

import entities.Commentaire;
import entities.Forum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import services.CommentaireService;
import services.ForumService;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class updateCommentaireController implements Initializable {

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

    @FXML
    private ComboBox<String> forum_id;

    @FXML
    private TextArea txtDescriptionCommentaire;

    @FXML
    private AnchorPane updateCommentaire;

    private int id;

    ForumService se = new ForumService();
    List<Forum> forums = se.Show();
    private int idForum=-1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, Integer> valuesMap = new HashMap<>();
        for(Forum c : forums){
            forum_id.getItems().add(c.getTitre());
            valuesMap.put(c.getTitre(),c.getId());
        }

        forum_id.setOnAction(event ->{
            String SelectedOption = null;
            SelectedOption = forum_id.getValue();
            int SelectedValue = 0;
            SelectedValue = valuesMap.get(SelectedOption);
            idForum = SelectedValue;
        });

        CommentaireService cs = new CommentaireService();
        List<Commentaire> commentaires = cs.Show();

        for(int i=0;i<commentaires.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemCommentaire.fxml"));

            try {
                AnchorPane anchorPane = fxmlLoader.load();
                HBox hBox = (HBox) anchorPane.getChildren().get(0);
                itemCommentaireController itemController = fxmlLoader.getController();
                txtDescriptionCommentaire.setText(itemController.getData(commentaires.get(i)).getDescription());
                this.id=itemController.getData(commentaires.get(i)).getId();
            } catch (IOException ex) {
                Logger.getLogger(itemForumController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void clearFieldsCommentaire() {
        txtDescriptionCommentaire.clear();
    }

    @FXML
    void updateCommentaire(ActionEvent event) {
        if (txtDescriptionCommentaire.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre commentaire.");
            Optional<ButtonType> option = alert.showAndWait();

        } else {
            modifCommentaire();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre commentaire a été modifié avec succès.");
            Optional<ButtonType> option = alert.showAndWait();
        }
    }

    void modifCommentaire(){
        String description = txtDescriptionCommentaire.getText();
        int id_forum = idForum;

        Commentaire c = new Commentaire(
                id,
                description, id_forum);
        CommentaireService sc = new CommentaireService();
        sc.modifier(c);
    }

}
