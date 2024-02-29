package Controller;

import entities.Cour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceCour;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;


public class ajouterCoursController  {
    ServiceCour serviceCour = new ServiceCour();



    @FXML
    private Button AjouterCour;

    @FXML
    private DatePicker DateDebut;

    @FXML
    private DatePicker DateFin;

    @FXML
    private TextField NomMatiere;

    @FXML
    private Button Retour;



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

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Cour ajouté avec succès.");
            afficherAfficherCour(event);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*void sendSMS(String recipientNumber, Cour newCour) {
        // Initialize Twilio library with your account information
        String ACCOUNT_SID = "AC5a00099393bbfce3013249fdc7af6d60";
        String AUTH_TOKEN = "2efd9aed6b028eaeceed7944d9354c92";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Customize your SMS message
        String message = "Bonjour Mr(s),\n"
                + "Nous sommes ravis de vous informer qu'un nouveau cours a été ajouté.\n "
                + "Matière: " + newCour.getMatiere() + "\n "
                + "Date Debut: " + newCour.getDate_debut() + "\n "
                + "Date Fin: " + newCour.getDate_fin()+ "\n "
                + "Merci de votre fidélité et à bientôt.\n"
                + "Cordialement,\n"
                + "Votre établissement";

        // Send the SMS message
        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+17179705340\n" +
                        "\n"),
                message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }*/

    /* private void sendEmailToUsers(List<String> userEmails, Cour newCour) {
        // SMTP server configuration (change accordingly)
        String host = "smtp.gmail.com"; // For Gmail
        String port = "465"; // SSL Port
        String username = "aymenbog9@gmail.com"; // Your email address
        String password = "boog0914K+"; // Your email password

        // Email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Create a Session object with authentication
        Session session;
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        });

        try {
            for (String email : userEmails) {
                // Create a MimeMessage object
                Message message = new MimeMessage(session);
                // Set the sender's email address
                message.setFrom(new InternetAddress(username));
                // Set the recipient's email address
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                // Set the email subject
                message.setSubject("New Course Added: " + newCour.getMatiere());
                // Set the email content
                String messageBody = "Dear User,\n\nA new course has been added with the following details:\n\n" +
                        "Matiere: " + newCour.getMatiere() + "\n" +
                        "Date Debut: " + newCour.getDateDebut() + "\n" +
                        "Date Fin: " + newCour.getDateFin() + "\n\n" +
                        "Thank you.\n";
                message.setText(messageBody);

                // Send the email
                Transport.send(message);
                System.out.println("Email sent successfully to: " + email);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
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
    }}


