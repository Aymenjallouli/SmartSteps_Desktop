package entities;

import java.util.Date;

public class Forum {
    private int id;
    private String titre;
    private String image;
    private Date dateCreation;
    private String description;

    public Forum(int id, String titre,String image, Date dateCreation, String description) {
        this.id = id;
        this.titre = titre;
        this.image = image;
        this.dateCreation = dateCreation;
        this.description = description;
    }

    public Forum(String titre,String image, Date dateCreation, String description) {
        this.titre = titre;
        this.image = image;
        this.dateCreation = dateCreation;
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
