package Controller;


import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCour;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ajouterCoursController  {
    ServiceCour serviceCour = new ServiceCour();



    @FXML
    private Button AjouterCour;

    @FXML
    private DatePicker DateDebut;

    @FXML
    private DatePicker DateFin;

    @FXML
    private TextField NomMatiere;

    @FXML
    private Button Retour;



    @FXML
    void AjouterCour(ActionEvent event) throws SQLException {
        String matiere = NomMatiere.getText().trim();
        LocalDate dateDebutValue = DateDebut.getValue();
        LocalDate dateFinValue = DateFin.getValue();

        // Vérifier si les champs ne sont pas vides
        if (matiere.isEmpty() || dateDebutValue == null || dateFinValue == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs.");
            return;
        }
                Date dateDebut = Date.valueOf(dateDebutValue);
                Date dateFin = Date.valueOf(dateFinValue);

        if (dateFin.before(dateDebut)) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "La date de fin doit être après la date de début.");
            return;
        }

        try {
            serviceCour.ajouter(new Cour(matiere, dateDebut, dateFin));
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Cours ajouté avec succès.");
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
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }}


