package entities;

import java.util.Date;

public class User {
    private int idUser , num_tel,isEnabled;
    private String nomUser , prenomUser , pwd , email , role,image ;
    private Date dateNai ;


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateNai() {
        return dateNai;
    }

    public void setDateNai(Date dateNai) {
        this.dateNai = dateNai;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", num_tel=" + num_tel +
                ", isEnabled=" + isEnabled +
                ", nomUser='" + nomUser + '\'' +
                ", prenomUser='" + prenomUser + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", image='" + image + '\'' +
                ", dateNai=" + dateNai +
                '}';
    }
}
