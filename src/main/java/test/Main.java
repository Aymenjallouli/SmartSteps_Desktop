package test;

import java.util.Date;

import entities.User;
import services.ServiceUser;
import utils.MyDB;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        MyDB conn1 = MyDB.getInstance();

        Date maintenant = new Date();

       /* new Terrain(22, "Kenza","dsdssd","DSJKDBS",097.89);
        Terrain p3 = new Terrain(22, "Kenza","dsdssd","DSJKDBS",2131.2);
*/

        //  ServiceReservation r = new ServiceReservation();
        ServiceUser u = new ServiceUser();

        // System.out.println( t.afficher().toString());

        // User c = new User(2374,"nkklok","okdjjd","ldlakdkjj","ija@djdd","prof");

        //    u.ajouter(c);


        //Reservation z = new Reservation(3,1,2, maintenant,"sss");
        // Terrain k = new Terrain(2,2, "sss","aa","axxa",35.2);

        //r.ajouter(z);
        // r.modifierReservation(z);
        // t.modifier(k);
        // t.modifertr(k);

        // r.supprimer(3);

        //t.supprimer(1);
        //   System.out.println( t.afficher().toString());

        //System.out.println( t.afficher().toString());

        // t.supprimer(1);

      /*  try {
            t.modifier(p3);
        } catch (SQLException var7) {
            System.out.println(var7.getMessage());
        }
*/

    }}
