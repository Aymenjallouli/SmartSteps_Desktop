package entities;

public class Note {
    private int id_evaluation;
    private int id_etudiant;
    private int note;

    // Constructors
    public Note() {
        // Default constructor
    }

    public Note(int id_evaluation, int id_etudiant, int note) {
        this.id_evaluation = id_evaluation;
        this.id_etudiant = id_etudiant;
        this.note = note;
    }

    // Getters and Setters
    public int getId_evaluation() {
        return id_evaluation;
    }

    public void setId_evaluation(int id_evaluation) {
        this.id_evaluation = id_evaluation;
    }

    public int getId_etudiant() {
        return id_etudiant;
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = id_etudiant;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    // toString method (optional)
    @Override
    public String toString() {
        return "Note{" +
                "id_evaluation=" + id_evaluation +
                ", id_etudiant=" + id_etudiant +
                ", note=" + note +
                '}';
    }
}
