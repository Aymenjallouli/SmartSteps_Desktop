package services;

import entities.Note;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceNote implements IService<Note> {

    private Connection con;

    public ServiceNote() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Note note) throws SQLException {
        String req = "INSERT INTO note (id_evaluation, id_etudiant, note) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, note.getId_evaluation());
            preparedStatement.setInt(2, note.getId_etudiant());
            preparedStatement.setInt(3, note.getNote());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception if needed
        }
    }

    @Override
    public void modifier(Note note) throws SQLException {

    }

    @Override
    public void supprimer(Note note) throws SQLException {
    }

    @Override
    public List<Note> afficher() throws SQLException {
        List<Note> ques = new ArrayList<>();
        return ques;
    }
    public Note getNote(int idEtudiant, int idEvaluation) throws SQLException {
        String query = "SELECT * FROM note WHERE id_etudiant = ? AND id_evaluation = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, idEtudiant);
            preparedStatement.setInt(2, idEvaluation);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have a Note class with appropriate constructor
                    return new Note(
                            resultSet.getInt("id_evaluation"),
                            resultSet.getInt("id_etudiant"),
                            resultSet.getInt("note")
                    );
                }
            }
        }

        return null; // Return null if no matching row is found
    }

}

