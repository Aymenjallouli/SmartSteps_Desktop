module WorkshopJDB.JavaFX {
requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
    requires org.json;
    requires java.net.http;
    requires twilio;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires jbcrypt;
    requires java.mail;
    requires itextpdf;


    opens test to javafx.fxml;
    opens Controller to javafx.fxml;
    opens entities to javafx.base;
    opens services to javafx.base;
    opens utils to javafx.base;
    exports test;
    exports Controller;


}