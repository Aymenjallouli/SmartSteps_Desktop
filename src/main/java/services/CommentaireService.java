package services;

import entities.Commentaire;
import entities.CrudCommentaire;
import entities.Forum;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService implements CrudCommentaire<Commentaire> {

    public Connection conx;
    public Statement stm;


    public CommentaireService() {
        conx = MyDB.getInstance().getConx();
    }


    @Override
    public void ajouter(Commentaire c) {
        String req =
                "INSERT INTO commentaire"
                        + "(description,id_forum)"
                        + "VALUES(?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, c.getDescription());
            ps.setInt(2, c.getId_forum());
            ps.executeUpdate();
            System.out.println("Commentaire Ajoutée !!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Commentaire c) {
        try {
            String req = "UPDATE commentaire SET description=?, id_forum=? WHERE id=?";
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(3, c.getId());
            pst.setString(1, c.getDescription());
            pst.setInt(2, c.getId_forum());
            pst.executeUpdate();
            System.out.println("Commentaire Modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM commentaire WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Commentaire suprimée !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Commentaire> Show() {
        List<Commentaire> list = new ArrayList<>();

        try {
            String req = "SELECT * from commentaire";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Commentaire(rs.getInt("id"), rs.getString("description"),
                        rs.getInt("id_forum")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Forum getById(int id) throws SQLException {
        return null;
    }
}
