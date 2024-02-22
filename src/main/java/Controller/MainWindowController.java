package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MainWindowController {

    @FXML
    private Button Retour;

    @FXML
    private Button PROF;

    @FXML
    private Button Etudiant;



    @FXML
    void PROF(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }





    @FXML
    void Retour(ActionEvent event) {


    }

    @FXML
    void Etudiant(ActionEvent event)throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Etudiant.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}