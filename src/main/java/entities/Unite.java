package entities;

import java.util.Arrays;

public class Unite {

    private  int num_unite;
    private String Titre;
    private String Statut;
    private String Contenue;
    private int id_cour;
    private Cour cour;
    private byte[] contenuBytes;

    public byte[] getContenuBytes() {
        return contenuBytes;
    }

    public Unite(int num_unite, String titre, String statut, byte[] contenuBytes) {
        this.num_unite = num_unite;
        Titre = titre;
        Statut = statut;
        this.contenuBytes = contenuBytes;
    }

    public void setContenuBytes(byte[] contenuBytes) {
        this.contenuBytes = contenuBytes;
    }



    public Cour getCour() {
        return cour;
    }

    public void setCour(Cour cour) {
        this.cour = cour;
    }

    public Unite(int num_unite, String titre, String statut, String contenue) {
        this.num_unite = num_unite;
        this.Titre = titre;
        this.Statut = statut;
        this.Contenue = contenue;
    }

    public Unite(String titre, String statut, String contenue, int id_cour) {
        Titre = titre;
        Statut = statut;
        Contenue = contenue;
        this.id_cour = id_cour;
    }

    public Unite(String titre, String statut, byte[] contenuBytes) {
        Titre = titre;
        Statut = statut;
        this.contenuBytes = contenuBytes;
    }

    public Unite() {}

    public int getNum_unite() {
        return num_unite;
    }

    public void setNum_unite(int num_unite) {
        this.num_unite = num_unite;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        this.Titre = titre;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        this.Statut = statut;
    }



    public void setContenue(String contenue) {
        this.Contenue = contenue;
    }


    @Override
    public String toString() {
        return "Unite{" +
                "num_unite=" + num_unite +
                ", Titre='" + Titre + '\'' +
                ", Statut='" + Statut + '\'' +
                ", contenuBytes=" + Arrays.toString(contenuBytes) +
                '}';
    }
}
