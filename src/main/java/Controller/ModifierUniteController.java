package Controller;
import entities.Unite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import services.ServiceUnite;

import java.io.File;
import java.io.IOException;

import java.sql.SQLException;


public class ModifierUniteController {
    @FXML
    private Button ModifierUnite;
    @FXML
    private Unite currentUnity;

    @FXML
    private Button Retour;

    @FXML
    private TextArea contenuField;

    @FXML
    private Label lblSelectedCour;



    @FXML
    private TextField statutField;

    @FXML
    private TextField titreField;


    private boolean validateFields() {
        if ( statutField.getText().isEmpty() || titreField.getText().isEmpty() || contenuField.getText().isEmpty()) {
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
    public void setUnityData(Unite unite) {
        this.currentUnity = unite;
        if (unite != null) {


            titreField.setText(unite.getTitre());
            statutField.setText(unite.getStatut());
            contenuField.setText(unite.getContenue());
        } else {


            titreField.clear();
            statutField.clear();
            contenuField.clear();
        }
    }



    public void ModifierUnite(ActionEvent actionEvent) throws IOException, SQLException {
        if (!validateFields()) {
            return;
        }





        currentUnity.setTitre(titreField.getText());
        currentUnity.setStatut(statutField.getText());


        currentUnity.setContenue(contenuField.getText());



        ServiceUnite st = new ServiceUnite();
        try {
            st.modifier(currentUnity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        returnToUnityListView(actionEvent);
    }

    private void returnToUnityListView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();


        stage.setScene(scene);
        stage.show();
    }

    public void Retour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCours.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    public void importFiles(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            contenuField.setText(selectedFile.getAbsolutePath());
        }
    }
}

