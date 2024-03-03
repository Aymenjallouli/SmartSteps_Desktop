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
    private Button rec;





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
