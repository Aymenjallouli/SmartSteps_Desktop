package Controller;


import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceCour;

import java.io.IOException;
import java.sql.SQLException;

public class ajouterCoursController {
    ServiceCour serviceCour= new ServiceCour();



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
        String matiere = NomMatiere.getText();
        java.util.Date dateDebut = java.sql.Date.valueOf(DateDebut.getValue());
        java.util.Date dateFin = java.sql.Date.valueOf(DateFin.getValue());

        ServiceCour serviceCour = new ServiceCour();
        try {
            serviceCour.ajouter(new Cour(matiere, dateDebut, dateFin));
            Alert alert= new Alert(Alert.AlertType.INFORMATION) ;
            alert. setTitle ("Success");
            alert. setContentText("Cour ajoute");
            alert. showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();


            Alert alert= new Alert(Alert. AlertType. ERROR);
            alert. setTitle("Error");
            alert.setContentText(e.getMessage());
            alert. showAndWait();
    }
    }


    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
            NomMatiere.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

}