package services;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IService <U>{
    public  void ajouter(U u );
    public  void modifier(U u ) throws SQLException;
    public  void supprimer(int idUser )throws SQLException;
    public Set<U> afficher() throws SQLException ;





}
