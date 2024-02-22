package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Lier le fichier CSS à la scène
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Gérer Cours");
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
