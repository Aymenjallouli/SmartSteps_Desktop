package controller;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;

import java.util.Comparator;

public class AfficherUserController {

    @FXML
    private ListView<User> listUser;

    @FXML
    private Button modiUser;

    @FXML
    private TextField nomUser;

    @FXML
    private Button suppUser;

    @FXML
    private Button trieenom;

    @FXML
    private TextField nomrechercher;

    @FXML
    private Button recherchenom;

    @FXML
    void initialize() {
        ServiceUser st = new ServiceUser();
        try {
            ObservableList<User> users = FXCollections.observableArrayList(st.afficher());
            listUser.setItems(users);

            listUser.setCellFactory(listView -> new ListCell<User>() {
                @Override
                public void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (empty || user == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10); // HBox with spacing of 10
                        Label numTelLabel = new Label(String.format("%d", user.getNum_tel()));
                        numTelLabel.setMinWidth(100); // Set minimum width for the label
                        Label nomUserLabel = new Label(user.getNomUser());
                        nomUserLabel.setMinWidth(100); // Set minimum width for the label
                        Label prenomUserLabel = new Label(user.getPrenomUser());
                        prenomUserLabel.setMinWidth(100); // Set minimum width for the label
                        Label pwdLabel = new Label(user.getPwd());
                        pwdLabel.setMinWidth(100); // Set minimum width for the label
                        Label emailLabel = new Label(user.getEmail());
                        emailLabel.setMinWidth(100); // Set minimum width for the label
                     //   Label typeUserLabel = new Label(user.getTypeUser());
                     //   typeUserLabel.setMinWidth(100); // Set minimum width for the label

                        hbox.getChildren().addAll(numTelLabel, nomUserLabel, prenomUserLabel, pwdLabel, emailLabel, null);
                        setGraphic(hbox); // Set the custom layout as the graphic of the list cell
                    }
                }
            });

            // Set the on mouse clicked event handler
            listUser.setOnMouseClicked(event -> {
                User selectedItem = listUser.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    nomUser.setText(selectedItem.getNomUser());
                    // You can now use selectedItem to perform other actions as needed
                }
            });

        } catch (SQLException e) {
            // Handle the SQLException here
            e.printStackTrace(); // For debugging purposes, you might want to print the stack trace
            // Consider showing an alert to the user or logging the error to a file
        }
    }

    @FXML
    void modiUser(ActionEvent event) throws IOException {

        User selectedUser = listUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
            Parent root = loader.load();

            // Get the ModifierTrController and set the Terrain data
            ModifierUserContoller modifierUserController = loader.getController();
            modifierUserController.setUserData(selectedUser);

            // Now set the scene
            nomUser.getScene().setRoot(root);
        } else {
            // Handle case where no terrain is selected
            // Show alert or log the error
        }


    }

    @FXML
    void suppUser(ActionEvent event) {

        User selectedUser = listUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            ServiceUser st = new ServiceUser();
            try {
                // Call the method to delete the terrain, assuming there is a method like deleteTerrain
                st.supprimer(selectedUser.getIdUser()); // replace getID_Terrain() with your actual method name for getting ID

                // Remove the selected item from the ListView
                listUser.getItems().remove(selectedUser);

                // Clear the selection
                listUser.getSelectionModel().clearSelection();

                // Clear the TextField if needed
                nomUser.clear();

                // Show confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User has been successfully deleted.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                // Show error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to delete user");
                alert.setContentText("An error occurred: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Show warning alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
        }

    }

    @FXML
    void trieenom(ActionEvent event) {
        ObservableList<User> users = listUser.getItems();
        users.sort(Comparator.comparing(User::getNomUser));

        // Mettez à jour la liste triée dans le ListView
        listUser.setItems(users);

    }

    @FXML
    void recherchenom(ActionEvent event) {
        String nomRecherche = nomrechercher.getText().trim().toLowerCase(); // Obtenez le texte et supprimez les espaces inutiles

        ObservableList<User> users = listUser.getItems();
        ObservableList<User> result = FXCollections.observableArrayList();

        for (User user : users) {
            if (user.getNomUser().toLowerCase().contains(nomRecherche)) {
                result.add(user);
            }
        }

        // Mettez à jour la liste filtrée dans le ListView
        listUser.setItems(result);

    }

}
