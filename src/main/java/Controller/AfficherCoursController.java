package Controller;

import entities.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServiceCour;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;




public class AfficherCoursController implements Initializable {

    private ResourceBundle resources;
    @FXML
    private Button ModifierCour;
    @FXML
    private Button Retour;
    @FXML
    private TextField NomMatiere;

    @FXML
    private Button SupprimerCour;

    @FXML
    private ListView<Cour> listcour;


    @FXML
    void SupprimerCour(ActionEvent event) {
        Cour selectedCour = listcour.getSelectionModel().getSelectedItem();
        if (selectedCour != null) {
            ServiceCour sc = new ServiceCour();
            try {
                // Appeler la méthode pour supprimer le cours
                sc.supprimer(selectedCour.getId_cour());

                // Retirer l'élément sélectionné de la ListView
                listcour.getItems().remove(selectedCour);

                // Effacer la sélection
                listcour.getSelectionModel().clearSelection();

                // Effacer le contenu du champ texte si nécessaire
                NomMatiere.clear();

                // Afficher une alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le cours a été supprimé avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                // Afficher une alerte d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la suppression du cours");
                alert.setContentText("Une erreur est survenue : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Afficher une alerte d'avertissement si aucun cours n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un cours à supprimer.");
            alert.showAndWait();
        }
    }


    @FXML
    void initialize() {
        ServiceCour st = new ServiceCour();
        try {
            ObservableList<Cour> cours = FXCollections.observableArrayList(st.afficher());
            listcour.setItems(cours);

            listcour.setCellFactory(listView -> new ListCell<Cour>() {
                @Override
                public void updateItem(Cour cour, boolean empty) {
                    super.updateItem(cour, empty);
                    if (empty || cour == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10); // HBox avec un espacement de 10 pixels
                        Label nameLabel = new Label(cour.getMatiere());
                        Label typeLabel = new Label(cour.getDate_debut().toString());
                        Label locationLabel = new Label(cour.getDate_fin().toString());

                        // Ajouter les labels au layout HBox
                        hbox.getChildren().addAll(nameLabel, typeLabel, locationLabel);

                        // Définir le layout HBox comme contenu graphique de la cellule
                        setGraphic(hbox);
                    }
                }
            });

            // Set the on mouse clicked event handler
            listcour.setOnMouseClicked(event -> {
                Cour selectedItem = listcour.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    NomMatiere.setText(selectedItem.getMatiere());
                    // You can now use selectedItem to perform other actions as needed
                }
            });

        } catch (Exception e) {
            // Handle the SQLException here
            e.printStackTrace(); // For debugging purposes, you might want to print the stack trace
            // Consider showing an alert to the user or logging the error to a file
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceCour st = new ServiceCour();
        try {
            ObservableList<Cour> cours = FXCollections.observableArrayList(st.afficher());
            listcour.setItems(cours);

            listcour.setCellFactory(listView -> new ListCell<Cour>() {
                @Override
                public void updateItem(Cour cour, boolean empty) {
                    super.updateItem(cour, empty);
                    if (empty || cour == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Créer une chaîne avec des espaces pour ajuster l'espacement
                        String spacedName = String.format("%-30s", cour.getMatiere());
                        String spacedType = String.format("%-30s", cour.getDate_debut().toString());
                        String spacedLocation = String.format("%-30s", cour.getDate_fin().toString());

                        // Créer des labels avec les chaînes espacées
                        Label nameLabel = new Label(spacedName);
                        Label typeLabel = new Label(spacedType);
                        Label locationLabel = new Label(spacedLocation);

                        // Ajouter les labels à un layout HBox
                        HBox hbox = new HBox(10); // HBox avec un espacement de 10 pixels
                        hbox.getChildren().addAll(nameLabel, typeLabel, locationLabel);

                        // Définir le layout HBox comme contenu graphique de la cellule
                        setGraphic(hbox);
                    }
                }
            });

            // Set the on mouse clicked event handler
            listcour.setOnMouseClicked(event -> {
                Cour selectedItem = listcour.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    NomMatiere.setText(selectedItem.getMatiere());
                    // You can now use selectedItem to perform other actions as needed
                }
            });

        } catch (Exception e) {
            // Handle the SQLException here
            e.printStackTrace(); // For debugging purposes, you might want to print the stack trace
            // Consider showing an alert to the user or logging the error to a file
        }
    }


// The rest of your controller code...



    @FXML
    void ModifierCour(ActionEvent event) {
        Cour selectedCour = listcour.getSelectionModel().getSelectedItem();
        if (selectedCour != null) {
            try {
                // Charger le fichier FXML de la vue ModifierCours.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCours.fxml"));
                Parent root = loader.load();

                // Obtenir le contrôleur du fichier FXML chargé
                ModifierCoursController modifierCoursController = loader.getController();

                // Passer les données du cours sélectionné au contrôleur
                modifierCoursController.setCourData(selectedCour);

                // Afficher la scène
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
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
