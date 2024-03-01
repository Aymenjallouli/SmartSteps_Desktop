package gui;

import entities.Commentaire;
import entities.Forum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import services.CommentaireService;
import services.ForumService;
import utils.MyDB;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class ajouterCommentaireController implements Initializable {
    @FXML
    private AnchorPane ajouterCommentaire;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private TextArea txtDescriptionCommentaire;

    @FXML
    private ComboBox<String> forum_id;

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
    }

    @FXML
    void AjouterCommentaire(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAjouter){
            if (txtDescriptionCommentaire.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre commentaire.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                ajoutCommentaire();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre commentaire a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();

                clearFieldsCommentaire();
            }
        }
        if(event.getSource() == btnAnnuler){
            clearFieldsCommentaire();
        }
    }

    @FXML
    void clearFieldsCommentaire() {
        txtDescriptionCommentaire.clear();
    }

    void ajoutCommentaire(){
        String descriptionCommentaire = txtDescriptionCommentaire.getText();

        int id_forum = idForum;

        MyDB db = MyDB.getInstance();
        Commentaire c = new Commentaire(
                descriptionCommentaire,id_forum);
        CommentaireService cs = new CommentaireService();
        cs.ajouter(c);
    }
}
