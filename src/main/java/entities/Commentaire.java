package entities;

public class Commentaire {
    private int id;
    private String description;
    private int id_forum;

    public Commentaire(int id, String description, int id_forum) {
        this.id = id;
        this.description = description;
        this.id_forum = id_forum;
    }

    public Commentaire(String description, int id_forum) {
        this.description = description;
        this.id_forum = id_forum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_forum() {
        return id_forum;
    }

    public void setId_forum(int id_forum) {
        this.id_forum = id_forum;
    }
}


