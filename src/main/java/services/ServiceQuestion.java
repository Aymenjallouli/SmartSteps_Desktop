package services;

import entities.Question;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceQuestion implements IService<Question> {

    private Connection con;

    public ServiceQuestion() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Question question) throws SQLException {
        String req = "insert into question (id_evaluation,num,type,enonce,options,solution,concept, max_score)" +
                "values ('" + question.getId_evaluation() + "','" + question.getNum() + "','" + question.getType() + "','" + question.getEnonce() + "','" + question.getOptions() + "'," +
                "'" + question.getSolution() + "','" + question.getConcept() + "'," + question.getMax_score() + ")";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void modifier(Question question) throws SQLException {
        String req = "update question set id_evaluation=?, num=?, type=? , enonce=?, options=?, solution=?, concept=?, max_score=? where id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, question.getId_evaluation());
        pre.setInt(1, question.getNum());
        pre.setString(1, question.getType());
        pre.setString(2, question.getEnonce());
        pre.setString(3, question.getOptions());
        pre.setString(4, question.getSolution());
        pre.setString(5, question.getConcept());
        pre.setInt(6, question.getMax_score());
        pre.setInt(7, question.getId());
        pre.executeUpdate();


    }

    @Override
    public void supprimer(Question question) throws SQLException {
        String req = "delete from question where id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, question.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Question> afficher() throws SQLException {
        List<Question> ques = new ArrayList<>();

        String req = "select * from question";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Question q = new Question();
            q.setId(res.getInt(1));
            q.setId_evaluation(res.getInt(2));
            q.setNum(res.getInt(3));
            q.setType(res.getString(4));
            q.setEnonce(res.getString("enonce"));
            q.setOptions(res.getString("options"));
            q.setSolution(res.getString("solution"));
            q.setConcept(res.getString("concept"));
            q.setMax_score(res.getInt("max_score"));
            ques.add(q);
        }


        return ques;
    }

    public Question afficherQuestion(int id, int num) throws SQLException {
        String req = "select * from question where id_evaluation=? AND num=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, id);
        pre.setInt(2, num);
        System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        System.out.println(num);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Question q = new Question();
            q.setId(res.getInt("id"));
            q.setId_evaluation(res.getInt("id_evaluation"));
            q.setNum(res.getInt("num"));
            q.setType(res.getString("type"));
            q.setEnonce(res.getString("enonce"));
            q.setOptions(res.getString("options"));
            q.setSolution(res.getString("solution"));
            q.setConcept(res.getString("concept"));
            q.setMax_score(res.getInt("max_score"));
            return q;
        }
        return null;
    }
}