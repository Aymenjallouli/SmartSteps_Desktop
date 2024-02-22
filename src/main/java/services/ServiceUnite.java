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
    private Cour cour;



    public ServiceUnite() {
        con = MyDB.getInstance().getConnection();
    }


    @Override
    public void ajouter(Unite unite) throws SQLException {
        String req = "INSERT INTO Unite (num_unite, Titre, Statut, Contenu, id_cour) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, unite.getNum_unite());
            pre.setString(2, unite.getTitre());
            pre.setString(3, unite.getStatut());
            pre.setBytes(4, unite.getContenuBytes()); // Utilisez les données de contenu sous forme de tableau d'octets
            pre.setInt(5, unite.getCour().getId_cour());
            pre.executeUpdate();
        }

    }

    @Override
    public void modifier(Unite unite) throws SQLException {
        String req = "update Unite set  Titre=?, Statut=?, Contenu=? where num_unite=?";
        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1, unite.getTitre());
        pre.setString(2, unite.getStatut());
        pre.setBytes(3, unite.getContenuBytes());
        pre.setInt(4, unite.getNum_unite());

        pre.executeUpdate();
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

            unite.setNum_unite(rs.getInt("num_unite"));
            unite.setTitre(rs.getString("Titre"));
            unite.setStatut(rs.getString("Statut"));
            unite.setContenue(rs.getString("Contenu"));
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
                    unite.setContenuBytes(rs.getBytes("Contenu")); // Récupérer le contenu sous forme de tableau d'octets
                    // Si nécessaire, vous pouvez également associer le cours à l'unité ici
                    unites.add(unite);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer correctement les exceptions dans votre application
            }
        }
        return unites;

}
}