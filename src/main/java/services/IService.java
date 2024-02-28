package services;

import entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IService <U>{


    User signIn(String email);

    boolean validerEmail(String s);

    boolean checkEmailExist(String email);

    public  boolean ajouter(U u );
    public  void modifier(U u ) throws SQLException;
    public  void supprimer(int idUser )throws SQLException;
    public Set<U> afficher() throws SQLException ;





}
