package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.Scene;

import javafx.stage.Stage;
import utils.MyDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginUserController implements Initializable {
    @FXML
    private Button inscrire;

    @FXML
    private Button login;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_pwd;

    @FXML
    void inscrire(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void login(ActionEvent event) {
        PreparedStatement st= null;
        ResultSet rs = null;
        Connection con = MyDB.getInstance().getConnection();
        try{
            st= con.prepareStatement("SELECT * FROM User  WHERE email =? AND pwd =?");
            st.setString(1,tf_email.getText());
            st.setString(2,tf_pwd.getText());
            rs = st.executeQuery();
            if (rs.next()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"login succesfully ", ButtonType.OK);
                alert.show();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();


                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }


            }else {
                Alert alert=new Alert(Alert.AlertType.WARNING,"Login Error",ButtonType.OK);
                alert.show();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);

        }

}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
