package services;

import entities.Cour;
import entities.Unite;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceUnite implements IService<Unite> {
    private final Connection con;




    public ServiceUnite() {
        con = MyDB.getInstance().getConnection();
    }


    @Override
    public void ajouter(Unite unite) throws SQLException {
        String req = "INSERT INTO Unite (Titre, Statut, Contenu, id_cour, DateDepot) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, unite.getTitre());
            pre.setString(2, unite.getStatut());
            pre.setString(3, unite.getContenue());
            pre.setInt(4, unite.getCour().getId_cour());

            // Ajouter la date actuelle
            java.sql.Date dateDepot = new java.sql.Date(System.currentTimeMillis());
            pre.setDate(5, dateDepot);

            pre.executeUpdate();
        }
    }

    @Override
    public void modifier(Unite unite) throws SQLException {
        String req = "UPDATE Unite SET Titre=?, Statut=?, Contenu=? WHERE num_unite=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, unite.getTitre());
            pre.setString(2, unite.getStatut());
            pre.setString(3, unite.getContenue());
            pre.setInt(4, unite.getNum_unite());
            pre.executeUpdate();
        }
    }

    @Override
    public void supprimer(Unite unite) throws SQLException {
        String req = "DELETE FROM Unite WHERE num_unite = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, unite.getNum_unite());
        pre.executeUpdate();
    }

    @Override
    public Set<Unite> afficher() throws SQLException {
        Set<Unite> unites = new HashSet<>();
        String req = "select * from Unite";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        while (rs.next()) {
            Unite unite = new Unite();
            unite.setTitre(rs.getString("Titre"));
            unite.setStatut(rs.getString("Statut"));
            unite.setContenue(rs.getString("Contenu"));
            java.sql.Date dateDepot = rs.getDate("DateDepot");
            unite.setDateDepot(Date.valueOf(dateDepot.toLocalDate()));
            unites.add(unite);
        }
        return unites;
    }

    public void setCour(Cour selectedCour) {
    }

    public List<Unite> recupererUnitesPourCour(Cour selectedCour) {
        List<Unite> unites = new ArrayList<>();
        if (selectedCour != null) {
            String req = "SELECT * FROM Unite WHERE id_cour = ?";
            try (PreparedStatement pre = con.prepareStatement(req)) {
                pre.setInt(1, selectedCour.getId_cour());
                ResultSet rs = pre.executeQuery();
                while (rs.next()) {
                    Unite unite = new Unite();
                    unite.setNum_unite(rs.getInt("num_unite"));
                    unite.setTitre(rs.getString("Titre"));
                    unite.setStatut(rs.getString("Statut"));
                    unite.setContenue(rs.getString("Contenu"));
                    unites.add(unite);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return unites;

}
}