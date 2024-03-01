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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import services.ServiceUnite;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import static java.nio.file.Paths.get;

public class AfficherUniteController implements Initializable {
    @FXML
    private Label lblSelectedCour;

    @FXML
    private ListView<Unite> listViewUnites;

    @FXML
    private Button ModifierCour;

    @FXML
    private Button Retour;

    @FXML
    private Button rechercherParMatiere;

    @FXML
    private TextField searchField;

    @FXML
    private Button SupprimerCour;

    @FXML
    private Button actualiserButton;
    @FXML
    private Button ajouterUnite;
    @FXML
    private TextArea chatOutput;

    @FXML
    private TextField chatInput;
    private Cour selectedCour;
    private static final String OPENAI_API_KEY = "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";
    private static final String MODEL_NAME = "gpt-3.5-turbo-0125";



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
    @FXML
    void Submit() {
        String userInput = chatInput.getText().trim();

        try {
            String botResponse = sendChatRequest(userInput);
            JSONObject jsonResponse = new JSONObject(botResponse);
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject message = choices.getJSONObject(0).getJSONObject("message");
                String botMessage = message.getString("content");
                chatOutput.appendText("You: " + userInput + "\n");
                chatOutput.appendText("SmartStepsBot: " + botMessage + "\n");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No response from the chatbot.");
            }
        } catch (IOException | InterruptedException | URISyntaxException | JSONException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while communicating with the chatbot.");
        }

        chatInput.clear();
    }

    public static String sendChatRequest(String userInput) throws IOException, InterruptedException, URISyntaxException {
        String url = "https://api.openai.com/v1/chat/completions";

        Map<Object, Object> data = new HashMap<>();
        data.put("model", MODEL_NAME);
        data.put("messages", new ArrayList<Map<String, String>>() {{
            add(new HashMap<String, String>() {{
                put("role", "user");
                put("content", userInput);
            }});
        }});

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(data).toString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }


    private void afficherUnites() throws SQLException {
        ServiceUnite serviceUnite = new ServiceUnite();
        List<Unite> unites = serviceUnite.recupererUnitesPourCour(selectedCour);
        ObservableList<Unite> observableUnites = FXCollections.observableArrayList(unites);
        listViewUnites.setItems(observableUnites);

        listViewUnites.setCellFactory(param -> new ListCell<>() {
            private final Button downloadButton = new Button("Ouvrir");

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
                        downloadContent(unite.getContenue(), unite.getTitre());
                    });
                }
            }
        });
    }



    // Example usage:
    private void downloadContent(String contenue, String titre) {
        String filePath = contenue;
        String fileName = titre + ".pdf";
        File file = new File(filePath);

        if (file.exists()) {
            try {

                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();

                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir le fichier.");
            }
        } else {

            showAlert(Alert.AlertType.WARNING, "Fichier introuvable", "Le fichier n'existe pas à l'emplacement spécifié.");
        }
    }


    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void rechercherParTitre() {
        String recherche = searchField.getText().toLowerCase();
        ObservableList<Unite> unites = listViewUnites.getItems();
        ObservableList<Unite> resultatRecherche = FXCollections.observableArrayList();
        for (Unite unite : unites) {
            if (unite.getTitre().toLowerCase().contains(recherche)) {
                resultatRecherche.add(unite);
            }
        }


        listViewUnites.setItems(resultatRecherche);
    }

    public void ModifierUnite() {
        Unite selectedUnity = listViewUnites.getSelectionModel().getSelectedItem();
        if (selectedUnity != null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUnite.fxml"));
                Parent root = loader.load();
                Scene scene = listViewUnites.getScene();

                scene.setRoot(root);
                ModifierUniteController modifierUniteController = loader.getController();


                modifierUniteController.setUnityData(selectedUnity);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier l'unite.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner L'unité à modifier.");
        }

    }

    public void SupprimerUnite() {
        Unite uniteSelectionnee = listViewUnites.getSelectionModel().getSelectedItem();

        if (uniteSelectionnee == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une unité à supprimer.");
            return;
        }

        try {
            ServiceUnite serviceUnite = new ServiceUnite();

            serviceUnite.supprimer(uniteSelectionnee);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'unité a été supprimée avec succès.");

            actualiserIHM();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la suppression de l'unité.");
        }

    }

    public void trierParNum() {
        ObservableList<Unite> unites = listViewUnites.getItems();


        unites.sort(Comparator.comparing(Unite::getNum_unite));

        listViewUnites.setItems(unites);
    }

    @FXML
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

    public void actualiserIHM() {
        try {
            afficherUnites();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les unités pour ce Cour.");
        }
    }

    public void ajouterUnite() {

        if (lblSelectedCour != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUnite.fxml"));
                Parent root = loader.load();

                Scene scene = lblSelectedCour.getScene();

                scene.setRoot(root);

                AjouterUniteController ajouterUniteController = loader.getController();

                ajouterUniteController.setCour(selectedCour);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter une unité.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un Cour pour ajouter une unité.");
        }
    }


}