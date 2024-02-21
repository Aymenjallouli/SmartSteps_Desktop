package services;

import entities.Cour;
import utils.MyDB;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceCour implements IService<Cour> {

    private final Connection con;

    public ServiceCour() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Cour cour) throws SQLException {
        String req = "insert into Cour ( Matiere, Date_Debut, Date_fin) values (?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(2, cour.getMatiere());
        pre.setDate(3, new java.sql.Date(cour.getDate_debut().getTime()));
        pre.setDate(4, new java.sql.Date(cour.getDate_fin().getTime()));
        pre.executeUpdate();
    }

    @Override
    public void modifier(Cour cour) throws SQLException {
        try {
            String req = "update Cour set Matiere=?, Date_Debut=?, Date_fin=? where id_cour=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, cour.getMatiere());
            pre.setDate(2, new java.sql.Date(cour.getDate_debut().getTime()));
            pre.setDate(3, new java.sql.Date(cour.getDate_fin().getTime()));
            pre.setInt(4, cour.getId_cour());
            pre.executeUpdate();

            int rowsAffected = pre.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Le Cour est modifié !");
            } else {
                System.out.println("Aucun Cour n'a été modifié.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Cour cour) throws SQLException {

    }

   /* @Override
    public void supprimer(Cour cour) throws SQLException {
        String req = "DELETE FROM Cour WHERE id_cour = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, cour.getId_cour());
        pre.executeUpdate();
    }*/


    @Override
    public Set<Cour> afficher() throws SQLException {
        Set<Cour> cours = new HashSet<>();
        String query = "SELECT * FROM `Cour`";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                Cour a = new Cour();
                a.setId_cour(rs.getInt("id_cour"));
                a.setMatiere(rs.getString("matiere"));
                a.setDate_debut(rs.getDate("date_debut"));
                a.setDate_fin(rs.getDate("date_fin"));
                cours.add(a);
            }
        } catch (SQLException e) {
            // Gérer l'exception ici ou la lancer à l'appelant
            throw e;
        }
        return cours;
    }


    public void supprimer(int id_cour) throws SQLException {
        String sql = "delete from Cour where id_cour = ?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id_cour);
        preparedStatement.executeUpdate();
    }
}
