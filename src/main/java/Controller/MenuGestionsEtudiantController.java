package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuGestionsEtudiantController {
    @FXML
    private TextArea chatOutput;
    @FXML
    private Button rec;

    @FXML
    private Button rec1;


    @FXML
    private TextField chatInput;
    private static final String OPENAI_API_KEY = "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";
    private static final String MODEL_NAME = "gpt-3.5-turbo-0125";


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
    @FXML
    void Profil(ActionEvent event) {

        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ProfilUser.fxml")));
            Stage myWindow = (Stage) rec.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Login");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(UserFirstPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void logout(ActionEvent event) {

        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginUser.fxml")));
            Stage myWindow = (Stage) rec.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Login");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(UserFirstPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


public void Evaluation(ActionEvent actionEvent) {
    }

    public void Forum(ActionEvent actionEvent) {
    }

    public void Cours(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Etudiant.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void Retour(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
