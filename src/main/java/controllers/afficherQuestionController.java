package controllers;
import com.sun.prism.impl.QueuedPixelSource;
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
import javafx.scene.image.ImageView;
import services.ServiceQuestion;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class afficherQuestionController {

    @FXML
    private Button ModifierEvaluation;

    @FXML
    private Button Retour;

    @FXML
    private Button SupprimerEvaluation;

    @FXML
    private Button actualiserButton;

    @FXML
    private Button ajouterQuestion;

    @FXML
    private Label lbl_enonce;
    @FXML
    private Label lbl_num;

    @FXML
    private Label lbl_o1;

    @FXML
    private Label lbl_o2;

    @FXML
    private Label lbl_o3;

    @FXML
    private Label lbl_o4;

    @FXML
    private Button rechercherParTitre;

    @FXML
    private TextField searchField;

    @FXML
    private CheckBox tf_a;

    @FXML
    private CheckBox tf_b;

    @FXML
    private CheckBox tf_c;

    @FXML
    private CheckBox tf_d;

    @FXML
    void ModifierQuestion(ActionEvent event) {

    }
    @FXML
    private ImageView next;

    @FXML
    private ImageView previous;
    Evaluation selectedEvaluation;
    int num=1;
    public void shit(){
        loadQuestion();
    }
    private void loadQuestion() {
        try {
            ServiceQuestion serviceEvaluation = new ServiceQuestion();
            //System.out.println("hahahahhahahhfqhhahahahhahahahahahahahhahaha");
            //System.out.println(serviceEvaluation.toString());
            Question q= serviceEvaluation.afficherQuestion(selectedEvaluation.getId(), num);
            String[] resultArray = q.getOptions().split(",");
            lbl_num.setText(String.valueOf(q.getNum())+"."
            );
            lbl_enonce.setText(q.getEnonce());
            lbl_o1.setText(resultArray[0]);
            lbl_o2.setText(resultArray[1]);
            lbl_o3.setText(resultArray[2]);
            //lbl_o4.setText(resultArray[3]);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la question", e.getMessage());
        }}

    public void setSelectedEvaluation(Evaluation selectedEvaluation) {
        this.selectedEvaluation = selectedEvaluation;
        /*if (selectedEvaluation != null) {
            lblSelectedEvaluation.setText("Evaluation sélectionné : " + selectedEvaluation.getTitre());
            try {
                afficherEvaluations();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les questions pour cette evaluation.");
            }
        } else {
            lblSelectedEvaluation.setText("Aucun cours sélectionné.");
        }*/
    }
    @FXML
    void next(MouseEvent event) {
        num++;
        loadQuestion();
    }

    @FXML
    void previous(MouseEvent event) {
        num--;
        loadQuestion();
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
    private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
