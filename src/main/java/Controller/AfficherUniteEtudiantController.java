package Controller;
import entities.Cour;
import entities.Unite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceUnite;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherUniteEtudiantController implements Initializable {
    @FXML
    private Label lblSelectedCour;

    @FXML
    private ListView<Unite> listViewUnites;
    @FXML
    private TextField searchField;
    private Cour selectedCour;
    public void setSelectedCour(Cour selectedCour) {
        this.selectedCour = selectedCour;
        if (selectedCour != null) {
            lblSelectedCour.setText("Cour sélectionné : " + selectedCour.getMatiere());
            try {
                afficherUnites();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les unités pour ce Cour.");
            }
        } else {
            lblSelectedCour.setText("Aucun Cour sélectionné.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private void afficherUnites() throws SQLException {
        ServiceUnite serviceUnite = new ServiceUnite();
        List<Unite> unites = serviceUnite.recupererUnitesPourCour(selectedCour);
        ObservableList<Unite> observableUnites = FXCollections.observableArrayList(unites);
        listViewUnites.setItems(observableUnites);


        listViewUnites.setCellFactory(param -> new ListCell<>() {
            private final Button downloadButton = new Button("Télécharger");

            @Override
            protected void updateItem(Unite unite, boolean empty) {
                super.updateItem(unite, empty);
                if (empty || unite == null) {
                    setText(null);
                    setGraphic(null);
                } else {

                    Label labelTitre = new Label(unite.getTitre());
                    Label labelStatut = new Label(unite.getStatut());

                    HBox hbox = new HBox(labelTitre, labelStatut);
                    hbox.setSpacing(50);

                    HBox hboxDownload = new HBox(downloadButton);
                    hboxDownload.setAlignment(Pos.CENTER_RIGHT);
                    hboxDownload.setFillHeight(true);


                    VBox vbox = new VBox(hbox, hboxDownload);
                    vbox.setSpacing(5);


                    setGraphic(vbox);

                    downloadButton.setOnAction(event -> {

                        downloadContent(unite.getContenuBytes(), unite.getTitre());
                    });
                }
            }
        });
    }


    private void downloadContent(byte[] content, String fileName) {
        try {

            File tempFile = File.createTempFile(fileName, ".pdf");
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                outputStream.write(content);
            }
            Desktop.getDesktop().open(tempFile);
            showAlert(Alert.AlertType.INFORMATION, "Téléchargement réussi", "Le Contenu a été téléchargé avec succès dans : " + tempFile.getAbsolutePath());
        } catch (IOException e) {

            showAlert(Alert.AlertType.ERROR, "Erreur de téléchargement", "Impossible de télécharger le Contenu.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void rechercherParTitre() {
        String recherche = searchField.getText().toLowerCase().trim();
        ObservableList<Unite> unites = listViewUnites.getItems();
        ObservableList<Unite> resultatRecherche = FXCollections.observableArrayList();
        for (Unite unite : unites) {
            if (unite.getTitre().toLowerCase().contains(recherche)) {
                resultatRecherche.add(unite);
            }
        }


        listViewUnites.setItems(resultatRecherche);
    }




    public void trierParNum() {
        ObservableList<Unite> unites = listViewUnites.getItems();
        unites.sort(Comparator.comparing(Unite::getNum_unite));
        listViewUnites.setItems(unites);
    }

    @FXML
    public void Retour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void actualiserIHM() {
        try {
            afficherUnites();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les unités pour ce Cour.");
        }
    }



}
