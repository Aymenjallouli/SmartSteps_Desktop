package Controller;



import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceUser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AjouterUserController  implements Initializable {


    @FXML
    private DatePicker date;

    @FXML
    private TextField email;

    @FXML
    private ImageView img;

    @FXML
    private AnchorPane iv_photo;

    @FXML
    private TextField nom;

    @FXML
    private TextField pas;

    @FXML
    private TextField prenom;

    @FXML
    private ChoiceBox<String> role;

    @FXML
    private TextField tel;
    int c;
    int file;
    File pDir;
    File pfile;
    String lien;

    ServiceUser serviceUser = new ServiceUser();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        tel.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
        role.getItems().addAll("Etudiant", "Enseignant");
        c = (int) (Math.random() * (300000 - 2 + 1)) + 2;
        lien = "user" + c + ".jpg";
        pDir = new File("src/main/resources/img/"+lien);
    }

    @FXML
    void ajt(ActionEvent event) {
        if (email.getText().equals("") || nom.getText().equals("") || date.getValue().equals("")|| file==0 || tel.getText().equals("0")|| prenom.getText().equals("")|| pas.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "vérifier votre champs", ButtonType.OK);
            alert.showAndWait();
        } else if (serviceUser.validerEmail(email.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "vérifier votre email", ButtonType.OK);
            alert.showAndWait();
        } else if(serviceUser.checkEmailExist(email.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "l'email existe déja", ButtonType.OK);
            alert.showAndWait();
        }
        else {


            System.out.println("Chemin du dossier resources : " + Objects.requireNonNull(getClass().getResource("/AjouterUser.fxml")).toString());
            User user = new User();
            String pass = pas.getText();
            pass = BCrypt.hashpw(pass, BCrypt.gensalt(13));
            pass= pass.replaceFirst("a", "y");
            user.setPwd(pass);
            user.setEmail(email.getText());
            user.setDateNai(new java.sql.Date(new Date(date.getEditor().getText()).getTime()));
            user.setImage(lien);
            user.setPrenomUser(prenom.getText());
            user.setNomUser(nom.getText());
            user.setNum_tel(Integer.parseInt(tel.getText()));
            user.setIsEnabled(1);
            user.setRole(role.getValue());
            serviceUser.ajouter(user);
            copier(pfile, pDir);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ajout Avec success", ButtonType.OK);
            alert.showAndWait();
            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginUser.fxml")));
                Stage myWindow = (Stage) role.getScene().getWindow();
                Scene sc = new Scene(root);
                myWindow.setScene(sc);
                myWindow.setTitle("Login");
                //myWindow.setFullScreen(true);
                myWindow.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }

    @FXML
    void uploadImage(ActionEvent event) throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image: ");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        Window stage = null;
        pfile = fileChooser.showOpenDialog(stage);

        /* - draw image */
        if (pfile != null) {
            file=1;
            Image image = new Image(pfile.toURI().toURL().toExternalForm());
            img.setImage(image);
        }
    }


    public static void copier(File source, File dest) {
        try (InputStream sourceFile = new java.io.FileInputStream(source);
             OutputStream destinationFile = new FileOutputStream(dest)) {
            // Lecture par segment de 0.5Mo
            byte[] buffer = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
