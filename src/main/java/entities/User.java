package entities;

public class User {
    int idUser , num_tel;
    String nomUser , prenomUser , pwd , email , typeUser ;

    public User() {
        this.idUser = idUser;
        this.num_tel = num_tel;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.pwd = pwd;
        this.email = email;
        this.typeUser = typeUser;
    }

    public User(int idUser, int num_tel, String nomUser, String prenomUser, String pwd, String email, String typeUser) {
        this.idUser = idUser;
        this.num_tel = num_tel;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.pwd = pwd;
        this.email = email;
        this.typeUser = typeUser;
    }

    public User(int num_tel, String nomUser, String prenomUser, String pwd, String email, String typeUser) {
        this.num_tel = num_tel;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.pwd = pwd;
        this.email = email;
        this.typeUser = typeUser;
    }

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

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", num_tel=" + num_tel +
                ", nomUser='" + nomUser + '\'' +
                ", prenomUser='" + prenomUser + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", typeUser='" + typeUser + '\'' +
                '}';
    }
}
