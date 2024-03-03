package services;

import java.sql.SQLException;
<<<<<<< HEAD

import java.util.Set;
=======
import java.util.List;
>>>>>>> Gestion_Evaluation

public interface IService <T>{
    public  void ajouter(T t ) throws SQLException;
    public  void modifier(T t ) throws SQLException;
    public  void supprimer(T t ) throws SQLException;
<<<<<<< HEAD
    public Set<T> afficher() throws SQLException;
=======
    public List<T> afficher() throws SQLException;
>>>>>>> Gestion_Evaluation



}
