package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyDB {


    public final String URL = "jdbc:mysql://localhost:3306/3a12";
    public final String USERNAME = "root";


    public static MyDB instance;

    private Connection connection;


    private MyDB() {

        try {
            connection = DriverManager.getConnection(URL,USERNAME,null);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // creer une methode getInstance
    public static MyDB getInstance(){
        if(instance==null){
            instance = new MyDB();
        }
        return instance;

    }

    public Connection getConnection() {
        return connection;
    }
}
