package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MainWindowController {

    @FXML
    private Button Retour;
    @FXML
    private Button AfficherCour;

    @FXML
    private Button AjouterCour;

    @FXML
    private Button ModifierCour;

    @FXML
    private Button SupprimerCour;

    @FXML
    void AfficherCour(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

    @FXML
    void AjouterCour(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouterCours.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

    @FXML
    void ModifierCour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ModifierCours.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

    @FXML
    void Retour(ActionEvent event) {


    }
}