package entities;
import java.sql.Date;

public class Evaluation {
    int id , duree, nb_questions;
    String titre;
    Date date_limite;

    public Evaluation(int id, String titre, Date date_limite, int duree) {
        this.id = id;
        this.titre = titre;
        this.date_limite = date_limite;
        this.duree = duree;
        this.nb_questions=0;
    }

    public Evaluation() {
    }

    public Evaluation(String titre, Date date_limite, int duree) {
        this.titre = titre;
        this.date_limite = date_limite;
        this.duree = duree;
    }


    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", titre=" + titre +
                ", date limite='" + date_limite + '\'' +
                ", duree='" + duree + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate_limite() {
        return date_limite;
    }

    public void setDate_limite(Date date_limite) {
        this.date_limite = date_limite;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
    public int getNb_questions() {
        return nb_questions;
    }
    public void setNb_questions(int nb_questions) {
        this.nb_questions = nb_questions++;
    }
}