package services;

import entities.Evaluation;
import entities.Question;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvaluation implements IService<Evaluation> {

    private Connection con;

    public ServiceEvaluation(){
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Evaluation evaluation) throws SQLException {
        String req = "insert into evaluation (titre,date_limite,duree)"+
                "values ('"+evaluation.getTitre()+"','"+evaluation.getDate_limite()+"',"+evaluation.getDuree()+")";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void modifier(Evaluation evaluation) throws SQLException {
        String req = "update evaluation set titre=? , date_limite=? , duree=? where id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,evaluation.getTitre());
        pre.setDate(2,evaluation.getDate_limite());
        pre.setInt(3,evaluation.getDuree());
        pre.setInt(4,evaluation.getId());

        pre.executeUpdate();


    }

    @Override
    public void supprimer(Evaluation evaluation) throws SQLException {
        String req = "DELETE FROM evaluation WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,evaluation.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Evaluation> afficher() throws SQLException {
        List<Evaluation> evals = new ArrayList<>();

        String req = "select * from evaluation";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res= pre.executeQuery();
        while (res.next()){
            Evaluation r = new Evaluation();
            r.setId(res.getInt(1));
            r.setTitre(res.getString("titre"));
            r.setDate_limite(res.getDate(3));
            r.setDuree(res.getInt("duree"));
            evals.add(r);
        }


        return evals;
    }
}
