package entities;

import java.util.Date;

public class Cour {
    private static int id_cour;
    private String matiere;
    private Date date_debut;
    private Date date_fin;

    public Cour(int id_cour, String matiere, Date date_debut, Date date_fin) {
        this.id_cour = id_cour;
        this.matiere = matiere;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Cour(String matiere, Date date_debut, Date date_fin) {
        this.matiere = matiere;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Cour() {
    }



    @Override
    public String toString() {
        return "Cour{" +
                "id_cour=" + id_cour +
                ", matiere='" + matiere + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }

    public static int getId_cour() {
        return id_cour;
    }

    public void setId_cour(int id_cour) {
        this.id_cour = id_cour;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }
}
