package Controller;

import entities.Forum;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class listForumFrontCardController implements Initializable {

    @FXML
    private Label labelDateForum;

    @FXML
    private Label labelDescriptionForum;

    @FXML
    private Label labelTitreForum;

    Forum forum;
    MyListener myListener;

    public void setData (Forum forum, MyListener myListener){
        this.forum = forum ;
        this.myListener = myListener;

        labelTitreForum.setText(forum.getTitre());
        labelDateForum.setText("Date Creation : "+String.valueOf(forum.getDateCreation()));
        labelDescriptionForum.setText("Description : "+String.valueOf(forum.getDescription()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
