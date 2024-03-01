package gui;

import entities.Commentaire;
import entities.Forum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.CommentaireService;
import services.ForumService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listCommentaireController implements Initializable {

    @FXML
    private AnchorPane listCommentaire;

    @FXML
    private VBox vBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column=0;
        int row=0;
        try {
            CommentaireService cs = new CommentaireService();
            List<Commentaire> comments = cs.Show();

            for(int i=0;i<comments.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("itemCommentaire.fxml"));

                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    HBox hBox = (HBox) anchorPane.getChildren().get(0);
                    itemCommentaireController itemController = fxmlLoader.getController();
                    itemController.setData(comments.get(i));
                    vBox.getChildren().add(hBox);

                } catch (IOException ex) {
                    Logger.getLogger(itemCommentaireController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
