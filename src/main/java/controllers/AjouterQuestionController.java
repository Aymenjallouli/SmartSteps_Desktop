package controllers;
import entities.Evaluation;
import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceEvaluation;
import services.ServiceQuestion;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Button;

public class AjouterQuestionController {
    ServiceQuestion serviceQuestion= new ServiceQuestion();
    ServiceEvaluation serviceEvaluation= new ServiceEvaluation();

    @FXML
    private Button Retour;

    @FXML
    private CheckBox tf_a;

    @FXML
    private CheckBox tf_b;

    @FXML
    private CheckBox tf_c;

    @FXML
    private TextField tf_concept;

    @FXML
    private CheckBox tf_d;

    @FXML
    private TextField tf_enonce;

    @FXML
    private TextField tf_opt1;

    @FXML
    private TextField tf_opt2;

    @FXML
    private TextField tf_opt3;

    @FXML
    private TextField tf_opt4;

    @FXML
    private TextField tf_num;

    @FXML
    private TextField tf_max_score;
    Evaluation selectedEvaluation;

    @FXML
    void AjouterQuestion(ActionEvent event) {
        String options, solution;

        if(tf_a.isSelected()) {solution=tf_opt1.getText();}
        else if (tf_b.isSelected()) {solution=tf_opt2.getText();}
        else if (tf_c.isSelected()) {solution=tf_opt3.getText();}
        else {solution=tf_opt4.getText();}
        options=tf_opt1.getText()+','+tf_opt2.getText()+','+tf_opt3.getText()+','+tf_opt4.getText();
        try {
            serviceQuestion.ajouter(new Question(selectedEvaluation.getId(),Integer.parseInt(tf_num.getText()), "qcm",tf_enonce.getText(),options,solution,tf_concept.getText(),Integer.parseInt(tf_max_score.getText())));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("question ajoutee");
            selectedEvaluation.setNb_questions(selectedEvaluation.getNb_questions()+1);
            serviceEvaluation.modifier(selectedEvaluation);
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    public void Retour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvaluation.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void setSelectedEvaluation(Evaluation selectedEvaluation) {
        this.selectedEvaluation = selectedEvaluation;
    }
}
