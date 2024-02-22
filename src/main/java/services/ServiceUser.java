package services;

import entities.User;
import utils.MyDB;
import utils.MyDB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceUser implements IService<User> {

    private Connection con = MyDB.getInstance().getConnection();

    public ServiceUser() {
    }


    @Override
    public void ajouter(User user) {
        try {
            // Mise à jour de la requête pour exclure id
            String query = "INSERT INTO User (idUser, numtel, nomUser, prenomUser, pwd, email, typeUser) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(query);

            // Mise à jour des paramètres pour correspondre aux attributs de l'objet Terrain, sans ID_Proprietaire
            stm.setDouble(1, user.getIdUser());
            stm.setDouble(2, user.getNum_tel());
            stm.setString(3, user.getNomUser());
            stm.setString(4, user.getPrenomUser());
            stm.setString(5, user.getPwd());
            stm.setString(6, user.getEmail());
            stm.setString(7, user.getTypeUser());

            stm.executeUpdate();
            System.out.println("User ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void modifertr(User u) {
        try {
            String query = "UPDATE User SET numTel=?, nomUser=?, prenomUser=?, pwd=?, email=?, typeUser=? WHERE idUser=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, u.getNum_tel());
            pstmt.setString(2, u.getNomUser());
            pstmt.setString(3, u.getPrenomUser());
            pstmt.setString(4, u.getPwd());
            pstmt.setString(5, u.getEmail());
            pstmt.setString(6, u.getTypeUser());
            pstmt.setInt(7, u.getIdUser());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Le User est modifié !");
            } else {
                System.out.println("Aucun User n'a été modifié.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(User user) throws SQLException {

    }


    @Override
    public void supprimer(int idUser) throws SQLException {
        String sql = "delete from User where idUser = ?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, idUser);
        preparedStatement.executeUpdate();
    }

    @Override
    public Set<User> afficher() throws SQLException {
        Set<User> user = new HashSet<>();
        String querry ="SELECT * FROM `User`";
        Statement stm =con.createStatement();
        ResultSet rs= stm.executeQuery(querry);
        while(rs.next()){
            User a = new User();
            a.setIdUser(rs.getInt(1));
            a.setNum_tel(rs.getInt(2));
            a.setNomUser(rs.getString(3));
            a.setPrenomUser(rs.getString(4));
            a.setPwd(rs.getString(5));
            a.setEmail(rs.getString(6));
            a.setTypeUser(rs.getString(7));
            user.add(a);
        }

        return user;

    }
    public User authentifier(String email, String pwd) throws SQLException {
        String req = "SELECT * FROM user WHERE email = ? AND pwd = ?";

        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, email);
            pre.setString(2, pwd);

            try (ResultSet result = pre.executeQuery()) {
                if (result.next()) {
                    return mapUser(result);
                } else {
                    return null;
                }
            }
        }
    }

    private User mapUser(ResultSet result) throws SQLException {
        return new User(
                result.getInt("idUser"),
                result.getInt("numTel"),
                result.getString("nomUser"),
                result.getString("prenomUser"),
                result.getString("pwd"),
                result.getString("email"),
                result.getString("typeUser")

        );
    }

}



