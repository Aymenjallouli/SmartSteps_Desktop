package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import services.NotifApi;

import java.io.IOException;


public class MainWindowControllerE {

    @FXML
    private Button Retour;

    @FXML
    private Button PROF;

    @FXML
    private Button Etudiant;



    @FXML
    void PROF(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvaluation.fxml"));
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
            NotifApi notifApi = new NotifApi();
            notifApi.scheduleNotification();
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvaluationEtudiant.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}