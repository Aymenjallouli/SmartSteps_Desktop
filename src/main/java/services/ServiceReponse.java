package services;

import entities.Question;
import entities.Reponse;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements IServiceE<Reponse> {

    private Connection con;

    public ServiceReponse(){
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reponse reponse) throws SQLException {
        String req = "insert into reponse (id_question,choix,is_correct,score)"+
                "values ('"+reponse.getId_question()+"','"+reponse.getChoix()+"',"+reponse.getIs_correct()+","+reponse.getScore()+")";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void modifier(Reponse reponse) throws SQLException {
        String req = "update reponse set id_question=? , choix=? , is_correct=? , score=? where id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,reponse.getId_question());
        pre.setString(2,reponse.getChoix());
        pre.setBoolean(3,reponse.getIs_correct());
        pre.setInt(3,reponse.getScore());
        pre.setInt(4,reponse.getId());

        pre.executeUpdate();


    }

    @Override
    public void supprimer(Reponse reponse) throws SQLException {
        String req = "delete from reponse where id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,reponse.getId());
    }

    @Override
    public List<Reponse> afficher() throws SQLException {
        List<Reponse> reps = new ArrayList<>();

        String req = "select * from reponse";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res= pre.executeQuery();
        while (res.next()){
            Reponse r = new Reponse();
            r.setId(res.getInt(1));
            r.setId_question(res.getInt("id_question"));
            r.setChoix(res.getString(3));
            r.setIs_correct(res.getBoolean("is_correct"));
            r.setScore(res.getInt("score"));
            reps.add(r);
        }


        return reps;
    }
}
