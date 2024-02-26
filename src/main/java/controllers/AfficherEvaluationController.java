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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AfficherEvaluationController implements Initializable {

    @FXML
    private ListView<Evaluation> listevaluation;

    @FXML
    private Button Modifier;

    @FXML
    private Button Retour;


    @FXML
    private Button SupprimerEvaluation;

    @FXML
    private TextField searchField;
    @FXML
    private Button AfficherQuestion;
    @FXML
    private Button AjouterQuestion;

    private ObservableList<Evaluation> evaluationList;


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

    @FXML
    void AjouterEvaluation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouterEvaluation.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
    void SupprimerEvaluation() {
        Evaluation selectedEvaluation = listevaluation.getSelectionModel().getSelectedItem();
        if (selectedEvaluation != null) {
            try {
                ServiceEvaluation serviceEvaluation = new ServiceEvaluation();
                serviceEvaluation.supprimer(selectedEvaluation);

                listevaluation.getItems().remove(selectedEvaluation);
                showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "L'evaluation a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la suppression de l'evaluation : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une evaluation à supprimer.");
        }
    }

    @FXML
    void ModifierEvaluation() {
        Evaluation selectedEvaluation = listevaluation.getSelectionModel().getSelectedItem();
        if (selectedEvaluation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvaluation.fxml"));
                Parent root = loader.load();

                Scene scene = listevaluation.getScene();

                scene.setRoot(root);

                ModifierEvaluationController modifierEvaluationController = loader.getController();

                modifierEvaluationController.setEvaluationData(selectedEvaluation);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier l'évaluation.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une évaluation à modifier.");
        }
    }

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherQuestion.fxml"));
                Parent root = loader.load();
                afficherQuestionController questionController = loader.getController();
                questionController.setSelectedEvaluation(selectedEvaluation);
                questionController.shit();
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
    void AjouterQuestion() {
    Evaluation selectedEvaluation = listevaluation.getSelectionModel().getSelectedItem();
        if (selectedEvaluation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterQuestion.fxml"));
                Parent root = loader.load();
                AjouterQuestionController ajouterQuestionController = loader.getController();
                ajouterQuestionController.setSelectedEvaluation(selectedEvaluation);
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