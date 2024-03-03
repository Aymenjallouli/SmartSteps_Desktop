package Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCour;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class ajouterCoursController {
    ServiceCour serviceCour = new ServiceCour();

    @FXML
    private DatePicker DateDebut;

    @FXML
    private DatePicker DateFin;

    @FXML
    private TextField NomMatiere;


    @FXML
    void AjouterCour(ActionEvent event) throws SQLException {
        String matiere = NomMatiere.getText().trim();
        LocalDate dateDebutValue = DateDebut.getValue();
        LocalDate dateFinValue = DateFin.getValue();
        if (matiere.isEmpty() || dateDebutValue == null || dateFinValue == null) {
            showAlert(Alert.AlertType.ERROR, "Champs Obligatoires ", "OUPS ! Vous Avez Oublié Des Champs Vides");
            return;
        }
        Date dateDebut = Date.valueOf(dateDebutValue);
        Date dateFin = Date.valueOf(dateFinValue);

        if (dateFin.before(dateDebut)) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "La date de fin doit être après la date de début.");
            return;
        }

        try {
            Cour newCour = new Cour(matiere, dateDebut, dateFin);
            serviceCour.ajouter(newCour);
            sendSMS("+21629082917",newCour);
            String recipientEmail = "smartstepsaymen@gmail.com";
            String subject = "New Course Added: " + newCour.getMatiere();
            String body = "Dear User,\n\nA new course has been added with the following details:\n\n" +
                    "Matiere: " + newCour.getMatiere() + "\n" +
                    "Date Debut: " + newCour.getDate_debut() + "\n" +
                    "Date Fin: " + newCour.getDate_fin() + "\n\n" +
                    "Thank you.\n";


            //sendMail(newCour, recipientEmail, subject, body);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Cour ajouté avec succès.");
            afficherAfficherCour(event);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void sendSMS(String recipientNumber, Cour newCour) {
        // Initialize Twilio library with your account information
        String ACCOUNT_SID = "AC5a00099393bbfce3013249fdc7af6d60";
        String AUTH_TOKEN = "0c2b4cf15c2c093851446f54f2a05c57";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
       // recipientNumber = "+21629082917";
        String message = "Bonjour Mr(s),\n"
                + "Nous sommes ravis de vous informer qu'un nouveau cours a été ajouté.\n "
                + "Matière: " + newCour.getMatiere() + "\n "
                + "Date Debut: " + newCour.getDate_debut() + "\n "
                + "Date Fin: " + newCour.getDate_fin()+ "\n "
                + "Merci de votre fidélité et à bientôt.\n"
                + "Cordialement,\n"
                + "SmartSteps";

        // Send the SMS message
        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+17179705340\n" +
                        "\n"),
                message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }

    /* void sendMail(Cour newCour, String recipientEmail, String subject, String body) {
        // SMTP server configuration
        String host = "smtp.gmail.com"; // For Gmail
        String port = "465"; // SSL Port
        String username = "jallouliaymen97@gmail.com"; // Your email address
        String password = "++652100"; // Your email password

        // Email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Create a Session object with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);
            // Set the sender's email address
            message.setFrom(new InternetAddress(username));
            // Set the recipient's email address
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipientEmail));
            // Set the email subject
            message.setSubject(subject);
            // Set the email content
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException(e); // Re-throw exception for handling higher up in the call stack
        }
    }*/

    private void afficherAfficherCour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
