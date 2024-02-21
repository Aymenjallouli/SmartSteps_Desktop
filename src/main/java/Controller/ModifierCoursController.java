package Controller;

import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.time.format.DateTimeFormatter;

public class ModifierCoursController {
    private Cour currentcour;


    @FXML
    private DatePicker Date_debut;

    @FXML
    private DatePicker Date_fin;

    @FXML
    private TextField ID_COUR;

    @FXML
    private Button Modifier;

    @FXML
    private TextField NOM_MATIERE;

    @FXML
    private Button Retour;


    public void SetCourData() {
        SetCourData(null);
    }

    public void SetCourData(Cour cour) {
        // Sauvegarder l'objet Cour passé
        this.currentcour = cour;

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



    /* @FXML
     void ModifierCour(ActionEvent event) {
         Cour c = new Cour();
         StringBuilder errors = new StringBuilder();

         if (ID_COUR.getText().trim().isEmpty()) {
             errors.append("Please enter an ID.\n");
         } else {
             try {
                 c.setId_cour(Integer.parseInt(ID_COUR.getText().trim()));
             } catch (NumberFormatException e) {
                 errors.append("ID must be a positive integer.\n");
             }
         }

         if (NOM_MATIERE.getText().trim().isEmpty()) {
             errors.append("Please enter a name.\n");
         } else {
             c.setMatiere(NOM_MATIERE.getText().trim());
         }

         if (Date_debut.getValue() == null) {
             errors.append("Please select a start date.\n");
         } else {
             c.setDate_debut(java.sql.Date.valueOf(Date_debut.getValue()));
         }

         if (Date_fin.getValue() == null) {
             errors.append("Please select an end date.\n");
         } else {
             c.setDate_fin(java.sql.Date.valueOf(Date_fin.getValue()));
         }



         if (errors.length() > 0) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erreur");
             alert.setHeaderText("Validation Error");
             alert.setContentText(errors.toString());
             alert.showAndWait();
         } else {
             try {
                 ServiceCour sc = new ServiceCour();
                 sc.modifier(c);

                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Success");
                 alert.setHeaderText(null);
                 alert.setContentText("The modification was successful.");
                 alert.showAndWait();

                 // Clear input fields
                 ID_COUR.setText("");
                 NOM_MATIERE.setText("");
                 Date_debut.setValue(null);
                 Date_fin.setValue(null);


                 // Refresh the table view here if necessary
                 // refreshTable();

             } catch (Exception e) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Erreur");
                 alert.setHeaderText("Modification Error");
                 alert.setContentText("An error occurred while modifying the record: " + e.getMessage());
                 alert.showAndWait();
             }
         }
     }*/
    @FXML
    void ModifierCour(ActionEvent event) throws IOException, SQLException {
        // Update the currentTerrain object with the new data from text fields
        currentcour.setMatiere(NOM_MATIERE.getText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateDebut = LocalDate.parse(Date_debut.getAccessibleText(), formatter);
        LocalDate dateFin = LocalDate.parse(Date_fin.getAccessibleText(), formatter);

        // Set the LocalDate objects to the currentcour
        currentcour.setDate_debut(Date.valueOf(dateDebut));
        currentcour.setDate_fin(Date.valueOf(dateFin));



        // Save the updated terrain to the database
        ServiceCour st = new ServiceCour();
        try {
            st.modifier(currentcour); // Assuming 'modifertr' is the method to save the changes
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // After saving, transition back to the AffichageTerrainController view
        returnToCourListView();
    }

    private void returnToCourListView() throws IOException {
        // Code to return to the AffichageTerrainController view
        // This can be done by loading the FXML for AffichageTerrainController
        // and setting it as the root, similar to how you've navigated to this controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageTerrain.fxml"));
        Parent root = loader.load();
        NOM_MATIERE.getScene().setRoot(root);
    }


    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
            Scene scene= new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

}
