package Controller;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceUser;
import utils.Sessions;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginUserController implements Initializable {
    @FXML
    private Button inscrire;
    static int IdOfUser;
    @FXML
    private Button login;

    @FXML
    private TextField tf_email;

    @FXML
    private PasswordField tf_pwd;

    @FXML
    void inscrire(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterUser.fxml")));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    @FXML
    void modifier(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ResetPassword.fxml"));
            Scene scene = new Scene(root);
            Stage myWindow = (Stage) tf_email.getScene().getWindow();
            myWindow.setScene(scene); // Change scene to the new one
            myWindow.setTitle("Reset Password");
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void login(ActionEvent event) {


        if(tf_email.getText().equals("") || tf_pwd.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Complete vos cordnner", ButtonType.OK);
            alert.showAndWait();
        }else {

            User U = new User();
            ServiceUser daoU = new ServiceUser();
            User result = daoU.signIn(tf_email.getText());
            System.out.println(result);
            if (result == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "vérifier vos cordoe", ButtonType.OK);
                alert.showAndWait();
            }
            else{
                //       System.out.println(result.getBanned());
                if (BCrypt.checkpw(tf_pwd.getText(), result.getPwd().replaceFirst("y","a")))
                {

                    if(result.getRole().equals("admin")){
                        Sessions.setLoggedInUser(result);
                        System.out.println(result.getRole());
                        // TODO: Proceed to other page
                        Parent root;
                        IdOfUser=result.getIdUser();
                        try {
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MenuGestionsAdmin.fxml")));
                            Stage myWindow = (Stage) tf_email.getScene().getWindow();
                            Scene sc = new Scene(root);
                            myWindow.setScene(sc);
                            myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
                            myWindow.show();
                        } catch (IOException ex) {
                            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    else if (result.getRole().equals("Enseignant")){
                        Sessions.setLoggedInUser(result);
                        System.out.println(result.getRole());
                        // TODO: Proceed to other page
                        Parent root;
                        IdOfUser=result.getIdUser();
                        try {
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MenuGestionsProf.fxml")));
                            Stage myWindow = (Stage) tf_email.getScene().getWindow();
                            Scene sc = new Scene(root);
                            myWindow.setScene(sc);
                            myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
                            myWindow.show();
                        } catch (IOException ex) {
                            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    else {
                        Sessions.setLoggedInUser(result);
                        // TODO: Proceed to other page
                        Parent root;
                        IdOfUser=0;
                        try {
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MenuGestionsEtudiant.fxml")));
                            Stage myWindow = (Stage) tf_email.getScene().getWindow();
                            Scene sc = new Scene(root);
                            myWindow.setScene(sc);
                            myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
                            myWindow.show();
                        } catch (IOException ex) {
                            Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }


                else{

                    Alert alert22 = new Alert(Alert.AlertType.ERROR, "vérifier vos cordoe", ButtonType.OK);
                    alert22.showAndWait();

                }

            }
        }


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
