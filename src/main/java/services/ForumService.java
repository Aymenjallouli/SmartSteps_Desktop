package services;

import entities.CrudForum;
import entities.Forum;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ForumService implements CrudForum<Forum> {
    public Connection conx;
    public Statement stm;


    public ForumService() {
        conx = MyDB.getInstance().getConx();

    }

    @Override
    public void ajouter(Forum f) {
        String req =
                "INSERT INTO forum"
                        + "(titre,image,dateCreation,description)"
                        + "VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, f.getTitre());
            ps.setString(2, f.getImage());
            ps.setDate(3, new java.sql.Date(f.getDateCreation().getTime()));
            ps.setString(4, f.getDescription());
            ps.executeUpdate();
            System.out.println("Forum Ajoutée !!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Forum f) {
        try {
            String req = "UPDATE forum SET titre=?,image=?, dateCreation=?, description=? WHERE id=?";
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(5, f.getId());
            pst.setString(1, f.getTitre());
            pst.setString(2, f.getImage());
            pst.setDate(3, new java.sql.Date(f.getDateCreation().getTime()));
            pst.setString(4, f.getDescription());
            pst.executeUpdate();
            System.out.println("Forum Modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM forum WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Forum suprimée !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Forum> Show() {
        List<Forum> list = new ArrayList<>();

        try {
            String req = "SELECT * from forum";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Forum(rs.getInt("id"), rs.getString("titre"),
                        rs.getString("image"), rs.getDate("dateCreation"),
                        rs.getString("description")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public int getById(int id) {
        int commentCount = 0;
        String req = "SELECT COUNT(*) FROM commentaire WHERE id_forum=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                commentCount = rs.getInt(1);
            }
            System.out.println("Number of comments where id_forum = " + id + ": " + commentCount);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return commentCount;
    }
}
