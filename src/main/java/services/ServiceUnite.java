package services;
import entities.Cour;
import entities.Unite;
import utils.MyDB;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ServiceUnite implements IService<Unite> {
    private final Connection con;

    public ServiceUnite() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Unite unite) throws SQLException {
        String req = "insert into Unite (num_unite,Titre, Statut, Contenu,id_cour) values (?, ?, ?, ?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, Unite.getNum_unite());
        pre.setString(2, Unite.getTitre());
        pre.setString(3, Unite.getStatut());
        pre.setString(4, Unite.getContenue());
        pre.setInt(5, Cour.getId_cour());


        pre.executeUpdate();
    }

    @Override
    public void modifier(Unite unite) throws SQLException {
        String req = "update Unite set num_unite=?, Titre=?, Statut=?,Contenue=? where id_cour=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, Unite.getNum_unite());
        pre.setString(2,Unite.getTitre());
        pre.setString(3, Unite.getStatus());
        pre.setString(4, Unite.getContenue());
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
        Set<Unite> unites= new HashSet<>();
        String req = "select * from Unite";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        while (rs.next()) {
           Unite unite = new Unite();
            unite.setNum_unite(rs.getInt("num_unite"));
            unite.setTitre(rs.getString("Titre"));
            unite.setStatut(rs.getString("Statut"));
            unite.setContenue(rs.getString("Contenue"));
            unites.add(unite);
        }
        return unites;
    }
     }

