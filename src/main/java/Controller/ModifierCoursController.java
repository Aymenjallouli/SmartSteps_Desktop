package Controller;

import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCour;

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

    @FXML
    void ModifierCour(ActionEvent event) throws IOException, SQLException {
        // Update the currentTerrain object with the new data from text fields
        currentCour.setMatiere(NOM_MATIERE.getText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateDebut = LocalDate.parse(Date_debut.getAccessibleText(), formatter);
        LocalDate dateFin = LocalDate.parse(Date_fin.getAccessibleText(), formatter);

        // Set the LocalDate objects to the currentcour
        currentCour.setDate_debut(Date.valueOf(dateDebut));
        currentCour.setDate_fin(Date.valueOf(dateFin));



        // Save the updated terrain to the database
        ServiceCour st = new ServiceCour();
        try {
            st.modifier(currentCour); // Assuming 'modifertr' is the method to save the changes
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // After saving, transition back to the AffichageTerrainController view
        returnToCourListView();
    }

    private void returnToCourListView() throws IOException {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            NOM_MATIERE.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
            NOM_MATIERE.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        // Implémentez la logique pour retourner à la vue précédente (si nécessaire)
    }

    public void setCourData() {
        setCourData(null);
    }

    public void setCourData(Cour cour) {
        // Sauvegarder l'objet Cour passé
        this.currentCour = cour;

        if (cour != null) {
            // Afficher les données dans les champs de texte ou les étiquettes
            NOM_MATIERE.setText(cour.getMatiere());
            Date_debut.setAccessibleText(cour.getDate_debut().toString()); // Utiliser toString() pour afficher la date
            Date_fin.setAccessibleText(cour.getDate_fin().toString()); // Utiliser toString() pour afficher la date
        } else {
            // Effacer les champs de texte ou les étiquettes si cour est null
            NOM_MATIERE.setText("");
            Date_debut.setAccessibleText("");
            Date_fin.setAccessibleText("");
        }
    }

    // Méthode pour retourner à la vue précédente (AffichageCours.fxml)

}
