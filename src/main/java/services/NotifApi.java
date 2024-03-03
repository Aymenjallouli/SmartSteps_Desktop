package services;
import javafx.application.Platform;
import javafx.geometry.Pos;
import entities.Evaluation;
import services.ServiceEvaluation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.controlsfx.control.* ;
//import org.eclipse.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class NotifApi {
    private int id_etudiant=1;
    private List<Evaluation> evaluations = new ArrayList<>();
    public void scheduleNotification() {
        ServiceNote serviceNote = new ServiceNote();
        List<Integer> ids = new ArrayList<>();
        try{ ids = serviceNote.getIdEval(id_etudiant);}
        catch (SQLException e){}
        ServiceEvaluation serviceEvaluation = new ServiceEvaluation();
        evaluations = serviceEvaluation.afficherById(ids);
        System.out.println(ids);
        for(Evaluation eva:evaluations){System.out.println(eva);}
        checkEvaluationsDueToday(evaluations);
    }
    private void checkEvaluationsDueToday(List<Evaluation> evaluations) {
        Date today = new Date();

        for (Evaluation evaluation : evaluations) {
            Date deadline = evaluation.getDate_limite();

            // Use SimpleDateFormat to compare dates without considering the time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            if (dateFormat.format(deadline).equals(dateFormat.format(today))) {

                Notifications notificationBuilder = Notifications.create()
                        .title("Due Today").text(evaluation.getTitre()+" ("+evaluation.getNb_questions()+" Questions)").graphic(null).hideAfter(javafx.util.Duration.seconds(10)) .position(Pos.CENTER_LEFT)
                        .onAction(new EventHandler<ActionEvent>(){
                            public void handle (ActionEvent event){
System.out.println("clicked ON ");
                        }});
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            }
        }
    }


}
