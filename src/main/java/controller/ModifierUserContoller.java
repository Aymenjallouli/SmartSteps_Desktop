package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceUser;

public class ModifierUserContoller {

    private User currentUser;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private Button modifierUser;

    @FXML
    private TextField nomUser;

    @FXML
    private TextField numTel;

    @FXML
    private TextField prenomUser;

    @FXML
    private TextField pwd;

    @FXML
    private TextField typeUser;

    public void setUserData(User user) {
        // Save the passed Terrain object
        this.currentUser = user;

        if (user != null) {
            numTel.setText(String.valueOf(user.getNum_tel()));
            nomUser.setText(user.getNomUser());
            prenomUser.setText(user.getPrenomUser());
            pwd.setText(user.getPwd());
            email.setText(user.getEmail());
        //    typeUser.setText(user.getTypeUser());
        }
    }

    @FXML
    void modifierUser(ActionEvent event) throws IOException {
        // Update the currentTerrain object with the new data from text fields
        try {
            int numTelValue = Integer.parseInt(numTel.getText());
            currentUser.setNum_tel(numTelValue);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion de la chaîne en entier.");
            // Gérer l'erreur selon vos besoins, par exemple, afficher un message d'erreur à l'utilisateur
        }

// Les autres champs
        currentUser.setNomUser(nomUser.getText());
        currentUser.setPrenomUser(prenomUser.getText());
        currentUser.setPwd(pwd.getText());
        currentUser.setEmail(email.getText());
      //  currentUser.setTypeUser(typeUser.getText());

        //  currentUser.getTypeUser(typeUser.getText());

        // Save the updated terrain to the database
        ServiceUser st = new ServiceUser();
        //st.modifertr(currentUser); // Assuming 'modifertr' is the method to save the changes

        // After saving, transition back to the AffichageTerrainController view
        returnToUserListView();

    }

    private void returnToUserListView() throws IOException {
        // Code to return to the AffichageTerrainController view
        // This can be done by loading the FXML for AffichageTerrainController
        // and setting it as the root, similar to how you've navigated to this controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
        Parent root = loader.load();
        nomUser.getScene().setRoot(root);
    }

    @FXML
    void initialize() {
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'ModifierUser.fxml'.";
        assert modifierUser != null : "fx:id=\"modifierUser\" was not injected: check your FXML file 'ModifierUser.fxml'.";
        assert nomUser != null : "fx:id=\"nomUser\" was not injected: check your FXML file 'ModifierUser.fxml'.";
        assert numTel != null : "fx:id=\"numTel\" was not injected: check your FXML file 'ModifierUser.fxml'.";
        assert prenomUser != null : "fx:id=\"prenomUser\" was not injected: check your FXML file 'ModifierUser.fxml'.";
        assert pwd != null : "fx:id=\"pwd\" was not injected: check your FXML file 'ModifierUser.fxml'.";
        assert typeUser != null : "fx:id=\"typeUser\" was not injected: check your FXML file 'ModifierUser.fxml'.";

    }

}
