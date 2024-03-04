/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.MyDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.XYChart;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EvaluationStatController implements Initializable {

    @FXML
    private Button Retour;

    @FXML
    private BarChart<String, Number> eval_stat;
    private Statement st;
    private ResultSet rs;
    private Connection cnx;
    int idd=1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cnx = MyDB.getInstance().getConnection();
            stat();
    }    

    private void stat() {
          try{
           // String query ="select COUNT(*),reservation_voyage.travel_class from voyage INNER JOIN reservation_voyage on reservation_voyage.voyage_id =voyage.id GROUP BY travel_class;";
           //String query ="select COUNT(*),`prix`  from voyage GROUP BY `destination`;";
              String query = "SELECT evaluation.titre, note.note FROM note " +
                      "INNER JOIN evaluation ON note.id_evaluation = evaluation.id " + "WHERE note.id_etudiant = ?";
           PreparedStatement PreparedStatement = cnx.prepareStatement(query);
              PreparedStatement.setInt(1, idd);
              rs = PreparedStatement.executeQuery();
              XYChart.Series<String, Number> series = new XYChart.Series<>();
              int i=0;
              int total = 0;
              while (rs.next()) {
                  String evaluationName = rs.getString("titre");
                  int result = rs.getInt("note");
                  total +=result;
                  series.getData().add(new XYChart.Data<>(evaluationName, result));
                  i++;
              }
              total = (int) ((double) total / i);
              series.getData().add(new XYChart.Data<>("total", total));
              eval_stat.getData().add(series);
              //series.getNode().setStyle("-fx-bar-fill: green;");

          } catch (SQLException ex) {
              System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void Retour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvaluationEtudiant.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
