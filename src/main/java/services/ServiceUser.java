package services;

import entities.User;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceUser implements IServiceU<User> {

    private final Connection con = MyDB.getInstance().getConnection();

    public ServiceUser() {
    }

    @Override
    public void modifer(User u) {
        try {
            String query = "UPDATE User SET numTel=?, nomUser=?, prenomUser=?, email=?, dateNai=?,image=? WHERE idUser=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, u.getNum_tel());
            pstmt.setString(2, u.getNomUser());
            pstmt.setString(3, u.getPrenomUser());
            pstmt.setString(4, u.getEmail());
            pstmt.setDate(5, (Date) u.getDateNai());
            pstmt.setString(6, u.getImage());
            pstmt.setInt(7, u.getIdUser());
            System.out.println("REQ"+pstmt);
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

            req="INSERT INTO user (nomUser,prenomUser,email,pwd,role,numTel,isEnable,dateNai,image) VALUES (?,?,?,?,?,?,'1',?,?)";
            PreparedStatement res=con.prepareStatement(req);
            res.setString(1, user.getNomUser());
            res.setString(2, user.getPrenomUser());
            res.setString(3, user.getEmail());
            res.setString(4, user.getPwd());
            res.setString(5, user.getRole());
            res.setInt(6, user.getNum_tel());
            res.setDate(7, (Date) user.getDateNai());
            res.setString(8, user.getImage());
            System.out.println("hhhhhhhh"+res);

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
    public List<User> afficher() throws SQLException {
        List<User> users = new ArrayList<>();
        String querry ="SELECT * FROM User";
        Statement stm =con.createStatement();
        ResultSet rs= stm.executeQuery(querry);
        while(rs.next()){
            User user = new User();
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
            users.add(user);
        }

        return users;

    }


    public void supprimer(int id) {
        try {

            Statement st = con.createStatement();
            String req = "DELETE FROM user WHERE idUser =" + id;
            st.executeUpdate(req);

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);

        }
    }


    public void block(int u) {
        try {
            String query = "UPDATE user SET isEnable=0 WHERE idUser=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, u);

            System.out.println("REQ"+pstmt);
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

    public void Deblock(int u) {
        try {
            String query = "UPDATE user SET isEnable=1 WHERE idUser=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, u);

            System.out.println("REQ"+pstmt);
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


    public User getUserById(int id) {

        User user = new User();

        try {
            String requete = "select * from user where idUser="+id;
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

    public boolean modifiePassword(User d) {
        String query = "UPDATE `user` SET `pwd`=? WHERE `idUser` = ?";

        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, d.getPwd());
            st.setInt(2, d.getIdUser());
            st.executeUpdate();
            return true;
        } catch (SQLException var5) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, var5);
            return false;
        }
    }


    public int getNbrUserBlockOrDeblocker(int isEnable) throws SQLException {

        int count =0;

        String requete="select * from user where isEnable="+isEnable;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()){
            count++;
        }
        return count;



    }
}



