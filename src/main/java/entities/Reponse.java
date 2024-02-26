package entities;
import java.sql.Date;

public class Reponse {
    int id , id_question, score;
    String choix;
    Boolean is_correct;

    public Reponse(int id, int id_question, String choix) {
        this.id = id;
        this.id_question = id_question;
        this.choix = choix;
    }

    public Reponse() {
    }

    public Reponse(int id_question, String choix) {
        this.id_question = id_question;
        this.choix = choix;
    }


    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", id_question=" + id_question +
                ", choix='" + choix + '\'' +
                ", is_correct='" + is_correct + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }
    public Boolean getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(Boolean is_correct) {
        this.is_correct = is_correct;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }
}