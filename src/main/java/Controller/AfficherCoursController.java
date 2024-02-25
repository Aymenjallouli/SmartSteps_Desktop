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
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherCoursController implements Initializable {

    @FXML
    private ListView<Cour> listcour;

    @FXML
    private Button ModifierCour;

    @FXML
    private Button Retour;


    @FXML
    private Button SupprimerCour;

    @FXML
    private TextField searchField;
    @FXML
    private Button AfficherUnite;

    private ObservableList<Cour> coursList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listcour.setCellFactory(listView -> new ListCell<Cour>() {
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

    @FXML
    void AjouterCour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouterCours.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
    void SupprimerCour() {
        Cour selectedCour = listcour.getSelectionModel().getSelectedItem();
        if (selectedCour != null) {

            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Supprimer le cours ?");
            confirmationDialog.setContentText("Voulez-vous vraiment supprimer ce cours ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    ServiceCour serviceCour = new ServiceCour();
                    serviceCour.supprimer(selectedCour);


                    listcour.getItems().remove(selectedCour);
                    showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Le Cour a été supprimé avec succès.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la suppression du cour : " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un cour à supprimer.");
        }
    }

    @FXML
    void ModifierCour() {
        Cour selectedCour = listcour.getSelectionModel().getSelectedItem();
        if (selectedCour != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCours.fxml"));
                Parent root = loader.load();

                Scene scene = listcour.getScene();

                scene.setRoot(root);

                ModifierCoursController modifierCoursController = loader.getController();

                modifierCoursController.setCourData(selectedCour);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier le Cour.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un Cour à modifier.");
        }
    }

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUnite.fxml"));
                Parent root = loader.load();

                AfficherUniteController uniteController = loader.getController();

                uniteController.setSelectedCour(selectedCour);

                Stage currentStage = (Stage) listcour.getScene().getWindow();
                currentStage.setScene(new Scene(root));

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'afficher les unités.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un Cour pour afficher Ces unités.");
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

    public void actualiserCours() {
        loadCours();
    }
}