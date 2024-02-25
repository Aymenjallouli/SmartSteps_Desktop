package test;

import entities.Cour;
import entities.Unite;
import services.ServiceCour;
import services.ServiceUnite;
import utils.MyDB;

import java.sql.SQLException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws RuntimeException {
        MyDB conn1 = MyDB.getInstance();

        // Création des objets Date pour Date_debut et Date_fin
        Date dateDebutC1 = new Date(2024 - 1900, 02, 1); // 2024-01-01
        Date dateFinC1 = new Date(2024 - 1900, 04, 2);   // 2024-01-02
        Date dateDebutC2 = new Date(2024 - 1900, 05, 3); // 2024-01-03
        Date dateFinC2 = new Date(2024 - 1900, 05, 4);   // 2024-01-04

        // Création des objets Cour avec les dates

        Cour c1 = new Cour(1, "Java", dateDebutC2, dateFinC2);

        //Unite u1 = new Unite(1,"Chapitre1","encour","");
        ServiceCour s = new ServiceCour();
        try {

          s.ajouter(c1);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ServiceUnite u = new ServiceUnite();
        // u.ajouter(u1);

        try {
            s.modifier(c1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            MyDB.getInstance().closeConnection(); // Fermez la connexion à la base de données une fois les opérations terminées
        }
    }
}
