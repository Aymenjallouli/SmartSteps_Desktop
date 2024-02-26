package controllers;
/*
import entities.Cour;
import entities.Unite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceUnite;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;

import java.sql.SQLException;

public class AjouterUniteController {

    @FXML
    private TextField numUniteField;
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextField titreField;

    @FXML
    private TextField statutField;

    @FXML
    private TextArea contenuField;

    private Cour selectedCour;

    public void setCour(Cour selectedCour) {
        this.selectedCour = selectedCour;
    }
    private byte[] getContentBytes() throws IOException {
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            return Files.readAllBytes(selectedFile.toPath());
        }
        return null;
    }

    @FXML
    void ajouterUnite(ActionEvent event) {
        try {
            int numUnite = Integer.parseInt(numUniteField.getText());
            String titre = titreField.getText();
            String statut = statutField.getText();
            byte[] contenuBytes = contenuField.getText().getBytes(); // Récupérez les données de contenu sous forme de tableau d'octets

            Unite unite = new Unite(numUnite, titre, statut, contenuBytes); // Utilisez les données de contenu sous forme de tableau d'octets
            unite.setCour(selectedCour); // Définissez le cours associé

            ServiceUnite serviceUnite = new ServiceUnite();
            serviceUnite.ajouter(unite);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'unité a été ajoutée avec succès.");

            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir un numéro d'unité valide.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout de l'unité.");
        }
    }

    @FXML
    void importFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            contenuField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void clearFields() {
        numUniteField.clear();
        titreField.clear();
        statutField.clear();
        contenuField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
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


}*/
