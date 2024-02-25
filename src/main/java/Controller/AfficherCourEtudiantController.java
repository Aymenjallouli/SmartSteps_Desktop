package Controller;
import entities.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceCour;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;


public class AfficherCourEtudiantController implements Initializable {
    @FXML
    private ListView<Cour> listcour;
    @FXML
    private Button REFRECH;





    @FXML
    private TextField searchField;


    private ObservableList<Cour> coursList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listcour.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Cour cour, boolean empty) {
                super.updateItem(cour, empty);
                if (empty || cour == null) {
                    setText(null);
                } else {
                    setText(String.format(" %-25s %-25s %-25s", cour.getMatiere(), cour.getDate_debut(), cour.getDate_fin()));
                }
            }
        });


        loadCours();
    }




    private void loadCours() {
        ServiceCour serviceCour = new ServiceCour();
        try {
            coursList = FXCollections.observableArrayList(serviceCour.afficher());
            listcour.setItems(coursList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des cours", e.getMessage());
        }}





    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Main Window");
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void AfficherUnite() {
        Cour selectedCour = listcour.getSelectionModel().getSelectedItem();
        if (selectedCour != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUniteEtudiant.fxml"));
                Parent root = loader.load();

                AfficherUniteEtudiantController uniteController = loader.getController();

                uniteController.setSelectedCour(selectedCour);


                Stage currentStage = (Stage) listcour.getScene().getWindow();
                currentStage.setScene(new Scene(root));

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'afficher les unités.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un cours pour afficher Ces unités.");
        }
    }

    @FXML
    void trierParMatiere() {
        coursList.sort(Comparator.comparing(Cour::getMatiere));
    }

    @FXML
    void rechercherParMatiere() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Cour> filteredList = FXCollections.observableArrayList();

        for (Cour cour : coursList) {
            if (cour.getMatiere().toLowerCase().contains(keyword)) {
                filteredList.add(cour);
            }
        }

        listcour.setItems(filteredList);
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        showAlert(type, title, null, contentText);
    }

    private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }



    public void REFRECH(ActionEvent actionEvent) {
        loadCours();
    }
}

