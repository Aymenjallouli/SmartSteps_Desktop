package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserFirstPageController {


    @FXML
    private Button rec;

    @FXML
    private Button rec1;


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
}
