package Controller;

import entities.Forum;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import services.ForumService;

public class StatistiquesController implements Initializable {

    @FXML
    private LineChart<String, Integer> lineChartForum;

    @FXML
    private AnchorPane statPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statistique();
    }

    public void statistique() {
        ForumService cs = new ForumService();
        List<Forum> forums = null;
        forums = cs.Show();


        // Créer les axes pour le graphique
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Titre Forum");
        yAxis.setLabel("Nbr Commentaire");

        // Créer la série de données à afficher
        XYChart.Series series = new XYChart.Series();
        series.setName("Statistiques des forums selon leurs nombre des commentaires");
        for (Forum f : forums) {
            series.getData().add(new XYChart.Data<>(f.getTitre(), cs.getById(f.getId())));
        }

        // Créer le graphique et ajouter la série de données
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Statistiques des forums");
        lineChart.getData().add(series);

        // Afficher le graphique dans votre scène
        lineChartForum.setCreateSymbols(false);
        lineChartForum.getData().add(series);
    }

    @FXML
    void Back(ActionEvent event) throws IOException {
        Parent page2 = FXMLLoader.load(getClass().getResource("/listForumFront.fxml"));

        Scene scene2 = new Scene(page2);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();
    }

}
