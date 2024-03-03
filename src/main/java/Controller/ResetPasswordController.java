package Controller;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResetPasswordController implements Initializable {

    ServiceUser serviceUser = new ServiceUser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    @FXML
    private TextField email;

    @FXML
    void envoyer(ActionEvent event) throws Exception {

        if (email.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vérfier Vos données", ButtonType.OK);
            alert.showAndWait();
        }
        else if(!serviceUser.checkEmailExist(email.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ce mail ne correspandant a aucun compt", ButtonType.OK);
            alert.showAndWait();
        }
        else{

            User u = serviceUser.signIn(email.getText());

            String newMdp = generateRandomPhrase();
            String mdp = BCrypt.hashpw(newMdp, BCrypt.gensalt(13));
            mdp= mdp.replaceFirst("a", "y");
            u.setPwd(mdp);
            entities.mail.envoi(email.getText(), "Nouveau Password", " Votre Noveaux Password est :"+newMdp);

            serviceUser.modifiePassword(u);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vous recevrez un e-mail dans quelques secondes", ButtonType.OK);
            alert.showAndWait();
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginUser.fxml")));
                Stage myWindow = (Stage) email.getScene().getWindow();
                Scene sc = new Scene(root);
                myWindow.setScene(sc);
                myWindow.setTitle("");
                //myWindow.setFullScreen(true);
                myWindow.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }



    public static String generateRandomPhrase() {
        // Définir les caractères possibles dans la phrase
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // Initialiser un objet Random
        Random random = new Random();
        // Initialiser une chaîne pour stocker la phrase générée
        StringBuilder sb = new StringBuilder();
        // Générer une phrase de longueur 8
        for (int i = 0; i < 8; i++) {
            // Sélectionner un caractère aléatoire parmi les caractères possibles
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            // Ajouter le caractère à la phrase
            sb.append(randomChar);
        }
        // Retourner la phrase générée
        return sb.toString();
    }
}
