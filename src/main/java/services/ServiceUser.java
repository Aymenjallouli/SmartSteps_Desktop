package services;

import entities.User;
import utils.MyDB;

import java.sql.*;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceUser implements IService<User> {

    private final Connection con = MyDB.getInstance().getConnection();

    public ServiceUser() {
    }

/*
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
*/

    @Override
    public User signIn(String email) {

        User user = new User();

        try {
            String requete = "select * from user where email='"+email+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(requete);
            int count = 0;
            while(rs.next()){
                count ++;
                user.setIdUser(rs.getInt("idUser"));
                user.setNomUser(rs.getString("nomUser"));
                user.setPrenomUser(rs.getString("prenomUser"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setDateNai(rs.getDate("dateNai"));
                user.setImage(rs.getString("image"));
                user.setNum_tel(rs.getInt("numTel"));
                user.setIsEnabled(rs.getInt("isEnable"));
                user.setPwd(rs.getString("pwd"));
            }

            if(count == 0){
                return null ;
            }else{
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override

    public boolean validerEmail(String s) {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(s);
        if (m.find() && m.group().equals(s)) {
            return false;
        } else {

            return true;


        }

    }

    @Override
    public boolean checkEmailExist(String email)
    {
        int count = 0;

        String requete="select * from user where email='"+email+"'";
        try{
            Statement st = con.createStatement();
            ResultSet rsl = st.executeQuery(requete);
            while(rsl.next())
            {
                count++;
            }
            if(count == 0){
                return false;
            }else{
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }



    @Override
    public boolean ajouter(User user) {

        int verf = 0 ;
        try{
            String req ;

            req="INSERT INTO user (nomUser,prenomUser,email,pwd,role,numTel,isEnable,dateNai,image) VALUES (?,?,?,?,?,?,?,?,1,?)";
            PreparedStatement res=con.prepareStatement(req);

            res.setString(1, user.getNomUser());
            res.setString(2, user.getPrenomUser());
            res.setString(3, user.getEmail());
            res.setString(4, user.getPwd());
            res.setString(5, user.getRole());
            res.setInt(6, user.getNum_tel());
            res.setInt(7, user.getIsEnabled());
            res.setDate(8, (Date) user.getDateNai());
            res.setString(9, user.getImage());

            verf=res.executeUpdate();


        }
        catch(SQLException e ){
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE,null,e);

        }
        if (verf==0)
        {return false;}
        else {return true;}
    }

    @Override
    public void modifier(User user) throws SQLException {

    }

    @Override
    public void supprimer(int idUser) throws SQLException {

    }

    @Override
    public Set<User> afficher() throws SQLException {
        return null;
    }
}



