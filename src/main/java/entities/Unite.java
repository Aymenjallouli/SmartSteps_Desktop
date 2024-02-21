package entities;

import java.util.Date;

public class Unite {
    public static int num_unite;
    public static String Titre;
    public static String Statut;
    public static String Contenue;

    public Unite(int num_unite, String titre, String statut, String contenue) {
        this.num_unite = num_unite;
        Titre = titre;
        Statut = statut;
        Contenue = contenue;
    }

    public Unite() {

    }

    public static int getNum_unite() {
        return num_unite;
    }

    public static String getStatus() {
        return Statut;
    }



    public void setNum_unite(int num_unite) {
        this.num_unite = num_unite;
    }

    public static String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public static String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    public static String getContenue() {
        return Contenue;
    }

    public void setContenue(String contenue) {
        Contenue = contenue;
    }

    @Override
    public String toString() {
        return "Unite{" +
                "num_unite=" + num_unite +
                ", Titre='" + Titre + '\'' +
                ", Statut='" + Statut + '\'' +
                ", Contenue='" + Contenue + '\'' +
                '}';
    }
}
