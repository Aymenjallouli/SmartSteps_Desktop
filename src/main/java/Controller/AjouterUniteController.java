package Controller;

import entities.Cour;
import entities.Unite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceUnite;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;


public class AjouterUniteController {



    @FXML
    private TextField titreField;

    @FXML
    private ChoiceBox<String> statutField;

    @FXML
    private TextArea contenuField;

    private Cour selectedCour;

    public void setCour(Cour selectedCour) {
        this.selectedCour = selectedCour;
    }


    private boolean validateFields() {
        if ( statutField.getValue().isEmpty() || titreField.getText().isEmpty() || contenuField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "OUPS ! Vous Avez Oublié Des Champs Vides ");
            return false;
        }
        return true;
    }

    @FXML
    void ajouterUnite(ActionEvent event) {
        if (!validateFields()) {
            return;
        }

        try {
            String titre = titreField.getText();
            String statut = statutField.getValue();
            String Contenue = contenuField.getText();

            Unite unite = new Unite(titre, statut, Contenue);
            unite.setCour(selectedCour);

            ServiceUnite serviceUnite = new ServiceUnite();
            serviceUnite.ajouter(unite);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'unité a été ajoutée avec succès.");

            clearFields();
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

        titreField.clear();
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
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}