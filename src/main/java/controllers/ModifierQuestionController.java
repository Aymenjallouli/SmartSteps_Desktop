package controllers;

import entities.Evaluation;
import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceEvaluation;
import services.ServiceQuestion;

import java.time.LocalDate;



import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class ModifierQuestionController {

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
    Question currrentQuestion;


    /*private boolean validateFields() {
        if (TITRE.getText().isEmpty() || Date_limite.getValue() == null || Duree.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "Veuillez remplir tous les champs obligatoires.");
            return false;
        }
        return true;
    }*/

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    @FXML
    void ModifierQuestion(ActionEvent event) throws IOException, SQLException {
        /*if (!validateFields()) {
            return;
        }*/
        ServiceQuestion serviceQuestion= new ServiceQuestion();

        String options, solution;

        if(tf_a.isSelected()) {solution=tf_opt1.getText();}
        else if (tf_b.isSelected()) {solution=tf_opt2.getText();}
        else if (tf_c.isSelected()) {solution=tf_opt3.getText();}
        else {solution=tf_opt4.getText();}
        options=tf_opt1.getText()+','+tf_opt2.getText()+','+tf_opt3.getText()+','+tf_opt4.getText();
        System.out.println("tchatchatchatchatchatchatchatchatchatchatcha");
        System.out.println(currrentQuestion.getId());

        serviceQuestion.modifier(new Question(currrentQuestion.getId(),currrentQuestion.getId_evaluation(),Integer.parseInt(tf_num.getText()), "qcm",tf_enonce.getText(),options,solution,tf_concept.getText(),Integer.parseInt(tf_max_score.getText())));
        Retour(event);



    }


    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvaluation.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public void setQuestionData(Question question) {
        this.currrentQuestion = question;

        if (question != null) {
            String[] resultArray = question.getOptions().split(",");
            tf_num.setText(String.valueOf(question.getNum())
            );
            tf_enonce.setText(question.getEnonce());
            tf_opt1.setText(resultArray[0]);
            tf_opt2.setText(resultArray[1]);
            tf_opt3.setText(resultArray[2]);
            tf_opt4.setText(resultArray[3]);
            tf_a.setSelected(false);
            tf_b.setSelected(false);
            tf_c.setSelected(false);
            tf_d.setSelected(false);
            if(question.getSolution().equals(resultArray[0])){tf_a.setSelected(true);}
            if(question.getSolution().equals(resultArray[1])){tf_b.setSelected(true);}
            if(question.getSolution().equals(resultArray[2])){tf_c.setSelected(true);}
            if(question.getSolution().equals(resultArray[3])){tf_d.setSelected(true);}
            tf_concept.setText(question.getConcept());
            tf_max_score.setText(String.valueOf(question.getMax_score()));
        } else {
        }
    }

}
