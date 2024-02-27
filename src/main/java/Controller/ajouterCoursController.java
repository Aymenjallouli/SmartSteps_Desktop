package Controller;


import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCour;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
        if (matiere.isEmpty() || dateDebutValue == null || dateFinValue == null) {
            showAlert(Alert.AlertType.ERROR, "Champs Obligatoires ", "OUPS ! Vous Avez Oublié Des Champs Vides");
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
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Cour ajouté avec succès.");
            afficherAfficherCour(event);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void afficherAfficherCour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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


