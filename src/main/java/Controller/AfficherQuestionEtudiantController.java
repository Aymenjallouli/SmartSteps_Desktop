package Controller;
//import com.sun.prism.impl.QueuedPixelSource;
import entities.Evaluation;
import entities.Question;
import entities.Note;
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
import services.ServiceNote;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherQuestionEtudiantController {

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
    private Button publier;

    @FXML
    void ModifierQuestion(ActionEvent event) {

    }
    @FXML
    private ImageView next;

    @FXML
    private ImageView previous;
    Evaluation selectedEvaluation;
    int id_etudiant=LoginUserController.IdOfUser;
    Note note;
    int num=1;
    Question q;
    Map<Integer, Boolean> questionAnswerMap = new HashMap<>();
    public void shit(){
        loadQuestion();
    }
    private void loadQuestion() {
        try {
            ServiceQuestion serviceQuestion = new ServiceQuestion();
            q= serviceQuestion.afficherQuestion(selectedEvaluation.getId(), num);
            System.out.println("hahahahhahahhfqhhahahahhahahahahahahahhahaha");
            System.out.println(q);
            String[] resultArray = q.getOptions().split(",");
            lbl_num.setText(String.valueOf(q.getNum())+"."
            );
            lbl_enonce.setText(q.getEnonce());
            lbl_o1.setText(resultArray[0]);
            lbl_o2.setText(resultArray[1]);
            lbl_o3.setText(resultArray[2]);
            lbl_o4.setText(resultArray[3]);
            tf_a.setSelected(false);
            tf_b.setSelected(false);
            tf_c.setSelected(false);
            tf_d.setSelected(false);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la question", e.getMessage());
            if(num==0)num++;
                else{num--;}
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
        addReponse();
        num++;
        loadQuestion();

    }

    @FXML
    void previous(MouseEvent event) {
        addReponse();
        num--;
        loadQuestion();
    }

    @FXML
    public void Retour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvaluationEtudiant.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void publierReponse(ActionEvent actionEvent){
        ServiceNote serviceNote = new ServiceNote();
        try{if(serviceNote.getNote(id_etudiant, selectedEvaluation.getId())!=null){
            showAlert(Alert.AlertType.INFORMATION, "", "Vous avez déjà publié votre réponse!", null);
            return;}
        }
        catch(SQLException e){}
        addReponse();
        int total=0;
        for (int questionNumber : questionAnswerMap.keySet()) {
            Boolean answer = questionAnswerMap.get(questionNumber);
            System.out.println("Question " + questionNumber + ": Answer " + answer);
            if(answer){total++;}
        }
        System.out.println(total);

        total = (int) (((double) total / selectedEvaluation.getNb_questions()) * 100);
        System.out.println(total);
        note=new Note(selectedEvaluation.getId(), id_etudiant, total);
        try{serviceNote.ajouter(note);}
        catch (SQLException e) {}
        showAlert(Alert.AlertType.INFORMATION, "Erreur", "Votre réponse a été enregistrée", null);

    }
    private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public void addReponse(){
        if(tf_a.isSelected()){
            questionAnswerMap.put(num, lbl_o1.getText().equals(q.getSolution()));}
        else if(tf_b.isSelected()){
            questionAnswerMap.put(num, lbl_o2.getText().equals(q.getSolution()));}
        else if(tf_c.isSelected()){
        questionAnswerMap.put(num, lbl_o3.getText().equals(q.getSolution()));}
        else if(tf_d.isSelected()){
            questionAnswerMap.put(num, lbl_o4.getText().equals(q.getSolution()));}
    }
}
