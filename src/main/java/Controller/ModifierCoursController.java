package Controller;

import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCour;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;



import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifierCoursController {

    private Cour currentCour;

    @FXML
    private TextField ID_COUR;

    @FXML
    private TextField NOM_MATIERE;

    @FXML
    private DatePicker Date_debut;

    @FXML
    private DatePicker Date_fin;


    private boolean validateFields() {
        if (NOM_MATIERE.getText().isEmpty() || Date_debut.getValue() == null || Date_fin.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "Veuillez remplir tous les champs obligatoires.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    @FXML
    void ModifierCour(ActionEvent event) throws IOException, SQLException {
        if (!validateFields()) {
            return;
        }

        currentCour.setMatiere(NOM_MATIERE.getText());

        LocalDate dateDebut = Date_debut.getValue();
        LocalDate dateFin = Date_fin.getValue();

        currentCour.setDate_debut(Date.valueOf(dateDebut));
        currentCour.setDate_fin(Date.valueOf(dateFin));

        ServiceCour st = new ServiceCour();
        try {
            st.modifier(currentCour);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        returnToCourListView(event);
    }

    private void returnToCourListView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            NOM_MATIERE.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public void setCourData(Cour cour) {
        this.currentCour = cour;

        if (cour != null) {
            NOM_MATIERE.setText(cour.getMatiere());
            Date_debut.setAccessibleText(cour.getDate_debut().toString()); // Assuming date is stored as a string
            Date_fin.setAccessibleText(cour.getDate_fin().toString()); // Assuming date is stored as a string
        } else {

            NOM_MATIERE.setText("");
            Date_debut.setAccessibleText("");
            Date_fin.setAccessibleText("");
        }
    }

}
