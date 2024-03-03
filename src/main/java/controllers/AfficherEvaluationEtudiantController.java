package controllers;

import entities.Evaluation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceEvaluation;

import java.sql.*;

import utils.MyDB;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import services.PdfApi;


public class AfficherEvaluationEtudiantController implements Initializable {

    @FXML
    private ListView<Evaluation> listevaluation;

    @FXML
    private Button ModifierEvaluation;

    @FXML
    private Button Retour;

    @FXML
    private Button progres;

    @FXML
    private Button SupprimerEvaluation;

    @FXML
    private TextField searchField;
    @FXML
    private Button AfficherQuestion;

    @FXML
    private Button certificat;

    private ObservableList<Evaluation> evaluationList;

    private Connection cnx;
    int idd=1;
    private Statement st;
    private ResultSet rs;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listevaluation.setCellFactory(listView -> new ListCell<Evaluation>() {
            @Override
            protected void updateItem(Evaluation evaluation, boolean empty) {
                super.updateItem(evaluation, empty);
                if (empty || evaluation == null) {
                    setText(null);
                } else {
                    setText(String.format(" %-25s %-25s %-25s", evaluation.getTitre(), evaluation.getDate_limite(), evaluation.getDuree()));
                }
            }
        });

        loadEvaluation();
    }




    private void loadEvaluation() {
        ServiceEvaluation serviceEvaluation = new ServiceEvaluation();
        try {
            evaluationList = FXCollections.observableArrayList(serviceEvaluation.afficher());
            listevaluation.setItems(evaluationList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des evaluations", e.getMessage());
        }}



    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Main Window");

            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void AfficherQuestion() {
        Evaluation selectedEvaluation = listevaluation.getSelectionModel().getSelectedItem();
        if (selectedEvaluation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherQuestionEtudiant.fxml"));
                Parent root = loader.load();
                AfficherQuestionEtudiantController questionEtudiantController = loader.getController();
                questionEtudiantController.setSelectedEvaluation(selectedEvaluation);
                questionEtudiantController.shit();
                Stage currentStage = (Stage) listevaluation.getScene().getWindow();
                currentStage.setScene(new Scene(root));

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'afficher les questions.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une evaluation pour afficher les questions.");
        }
    }
    @FXML
    void trierParTitre() {
        evaluationList.sort(Comparator.comparing(Evaluation::getTitre));
    }

    @FXML
    void rechercherParTitre() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Evaluation> filteredList = FXCollections.observableArrayList();

        for (Evaluation evaluation : evaluationList) {
            if (evaluation.getTitre().toLowerCase().contains(keyword)) {
                filteredList.add(evaluation);
            }
        }

        listevaluation.setItems(filteredList);
    }


    @FXML
    void evalStat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EvalStat.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) listevaluation.getScene().getWindow();
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'afficher votre progrès.");
        }
    }



    @FXML
    void imprimerCertif(ActionEvent event) {
        cnx = MyDB.getInstance().getConnection();
        try{String query = "SELECT note FROM note WHERE id_etudiant = ?";
        PreparedStatement PreparedStatement = cnx.prepareStatement(query);
        PreparedStatement.setInt(1, idd);
        rs = PreparedStatement.executeQuery();
            int i=0;
            int total = 0;
            while (rs.next()) {
                int result = rs.getInt("note");
                total +=result;
                i++;
            }
            total = (int) ((double) total / i);
            if(total>=70){
                PdfApi pdfApi = new PdfApi();
                pdfApi.certif("mohamed", String.valueOf(total));
            }
            else{
                showAlert(Alert.AlertType.INFORMATION, "", "Completez vos évaluations pour obtenir un certificat");

            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void showAlert(Alert.AlertType type, String title, String contentText) {
        showAlert(type, title, null, contentText);
    }

    private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void actualiserEvaluation() {
        loadEvaluation();
    }
}