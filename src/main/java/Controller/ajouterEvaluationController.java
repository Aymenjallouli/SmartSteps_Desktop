package controllers;


import entities.Evaluation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceEvaluation;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class ajouterEvaluationController {
    ServiceEvaluation serviceEvaluation = new ServiceEvaluation();



    @FXML
    private Button AjouterEvaluation;

    @FXML
    private DatePicker DateLimite;

    @FXML
    private TextField Titre;
    @FXML
    private TextField Duree;

    @FXML
    private Button Retour;



    @FXML
    void AjouterEvaluation(ActionEvent event) throws SQLException {
        String titre = Titre.getText().trim();
        LocalDate dateLimiteValue = DateLimite.getValue();
        int duree = Integer.parseInt(Duree.getText().trim());

        // Vérifier si les champs ne sont pas vides
        if (titre.isEmpty() || dateLimiteValue == null || duree==0) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
            return;
        }
                Date dateLimite = Date.valueOf(dateLimiteValue);

        try {
            serviceEvaluation.ajouter(new Evaluation(titre, dateLimite, duree));
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Evaluation ajouté avec succès.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvaluation.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }}


