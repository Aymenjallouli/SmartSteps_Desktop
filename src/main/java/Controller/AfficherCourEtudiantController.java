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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import services.ServiceCour;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.*;


public class AfficherCourEtudiantController implements Initializable {
    @FXML
    private ListView<Cour> listcour;
    @FXML
    private TextField searchField;
    private ObservableList<Cour> coursList;
    @FXML
    private TextArea chatOutput;

    @FXML
    private TextField chatInput;
    private static final String OPENAI_API_KEY = "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";
    private static final String MODEL_NAME = "gpt-3.5-turbo-0125";
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
    public void REFRECH() {
        loadCours();
    }


}

