package Controller;
import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCour;

import java.io.IOException;

public class SupprimerCoursController {
    @FXML
    private TextField ID_COUR;

    @FXML
    private Button Retour;

    @FXML
    private Button Supprimer;

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


    @FXML
    void Supprimer(ActionEvent event) {
        ServiceCour sc = new ServiceCour();

        StringBuilder errors = new StringBuilder();

        if (ID_COUR.getText().trim().isEmpty()) {
            errors.append("Please enter an id\n");
        }

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(errors.toString());
            alert.showAndWait();
        } else {
            try {
                int id = Integer.parseInt(ID_COUR.getText().trim());
                Cour courToDelete = new Cour();
                courToDelete.setId_cour(id);
                sc.supprimer(courToDelete); // Appel de la méthode supprimer avec l'objet Cour correspondant à l'ID du cours à supprimer

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Cour is deleted successfully!");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Please enter a valid ID");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("An error occurred while deleting the record: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }







}


