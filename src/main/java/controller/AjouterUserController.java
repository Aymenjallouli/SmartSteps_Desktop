package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.ServiceUser;

public class AjouterUserController {

    private final ServiceUser ST = new ServiceUser();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button affUser;

    @FXML
    private Button aj;

    @FXML
    private TextField email;

    @FXML
    private AnchorPane iv_photo;

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

    @FXML
    void affUser(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
        Parent root = loader.load();
        AfficherUserController a = loader.getController();
        nomUser.getScene().setRoot(root);

    }

    @FXML
    void ajt(ActionEvent event) throws IOException {

        ServiceUser sm = new ServiceUser() ;
        User c = new User() ;


        StringBuilder errors=new StringBuilder();

        if(numTel.getText().trim().isEmpty()&&nomUser.getText().trim().isEmpty()){
            errors.append("svp entrer le numTel et le nom de user\n");
        }

        if(errors.length()>0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(errors.toString());
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Activite is added successfully!");
            alert.show();

            try {
                int numTelValue = Integer.parseInt(numTel.getText());
                c.setNum_tel(numTelValue);
            } catch (NumberFormatException e) {
                System.err.println("Erreur de conversion de la chaîne en entier.");
                // Gérer l'erreur selon vos besoins, par exemple, afficher un message d'erreur à l'utilisateur
            }


            c.setNomUser(nomUser.getText());
            c.setPrenomUser(prenomUser.getText());
            c.setPwd(pwd.getText());
            c.setEmail(email.getText());
            c.setTypeUser(typeUser.getText());
            //c.setActivite_id(Integer.parseInt(aid.getText()));



            sm.ajouter(c);

            // Réinitialisation des champs
            numTel.setText("");
            nomUser.setText("");
            prenomUser.setText("");
            pwd.setText("");
            email.setText("");
            typeUser.setText("");
        }


    }



    @FXML
    void initialize() {
        assert affUser != null : "fx:id=\"affUser\" was not injected: check your FXML file 'AjouterUser.fxml'.";
        assert aj != null : "fx:id=\"aj\" was not injected: check your FXML file 'AjouterUser.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'AjouterUser.fxml'.";
        assert iv_photo != null : "fx:id=\"iv_photo\" was not injected: check your FXML file 'AjouterUser.fxml'.";
        assert nomUser != null : "fx:id=\"nomUser\" was not injected: check your FXML file 'AjouterUser.fxml'.";
        assert numTel != null : "fx:id=\"numTel\" was not injected: check your FXML file 'AjouterUser.fxml'.";
        assert prenomUser != null : "fx:id=\"prenomUser\" was not injected: check your FXML file 'AjouterUser.fxml'.";
        assert pwd != null : "fx:id=\"pwd\" was not injected: check your FXML file 'AjouterUser.fxml'.";
        assert typeUser != null : "fx:id=\"typeUser\" was not injected: check your FXML file 'AjouterUser.fxml'.";

    }

}
