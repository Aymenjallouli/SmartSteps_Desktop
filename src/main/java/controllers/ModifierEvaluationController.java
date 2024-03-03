package controllers;

import entities.Evaluation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceEvaluation;

import java.time.LocalDate;



import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class ModifierEvaluationController {

    private Evaluation currentEvaluation;

    @FXML
    private TextField ID_EVALUATION;

    @FXML
    private TextField TITRE;

    @FXML
    private DatePicker Date_limite;

    @FXML
    private TextField Duree ;


    private boolean validateFields() {
        if (TITRE.getText().isEmpty() || Date_limite.getValue() == null || Duree.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "Veuillez remplir tous les champs obligatoires.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    @FXML
    void ModifierEvaluation(ActionEvent event) throws IOException, SQLException {
        if (!validateFields()) {
            return;
        }

        currentEvaluation.setTitre(TITRE.getText());
        currentEvaluation.setTitre(TITRE.getText());

        LocalDate dateLimite = Date_limite.getValue();

        currentEvaluation.setDate_limite(Date.valueOf(dateLimite));
        int duree = Integer.parseInt(Duree.getText().trim());
        currentEvaluation.setDuree(duree);


        ServiceEvaluation st = new ServiceEvaluation();
        try {
            System.out.println("blablablablablablablablablablablablablablablablabla");
            System.out.println(currentEvaluation.getId());

            st.modifier(currentEvaluation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        returnToEvaluationListView(event);
    }

    private void returnToEvaluationListView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvaluation.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvaluation.fxml"));
            TITRE.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public void setEvaluationData(Evaluation evaluation) {
        this.currentEvaluation = evaluation;

        if (evaluation != null) {
            TITRE.setText(evaluation.getTitre());
            Date_limite.setValue(evaluation.getDate_limite().toLocalDate()); // Assuming date is stored as a string
            Duree.setText(String.valueOf(evaluation.getDuree())); // Assuming date is stored as a string
        } else {

            TITRE.setText("");
            Date_limite.setAccessibleText("");
            Duree.setAccessibleText("");
        }
    }

}
