package services;

import entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IServiceU <U>{


    void modifer(User u);

    User signIn(String email);

    boolean validerEmail(String s);

    boolean checkEmailExist(String email);

    public  boolean ajouter(U u );

    public List<U> afficher() throws SQLException ;





}
