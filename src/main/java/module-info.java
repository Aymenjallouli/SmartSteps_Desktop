module WorkshopJDB.JavaFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires jbcrypt;
    exports test;
    exports controller;
    opens test to javafx.fxml; // Opens the test package for FXMLLoader
    opens controller to javafx.fxml;
    exports entities; // Supprimez le point-virgule à la fin de cette ligne
}
