package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {

    private static final String URL = "jdbc:mysql://localhost:3306/3a12";
    private static final String USERNAME = "root";

    private static MyDB instance;
    private Connection connection;

    public MyDB() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, null);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }

    // Méthode getInstance pour obtenir l'instance unique de MyDB
    public static MyDB getInstance() {
        if (instance == null) {
            instance = new MyDB();
        }
        return instance;
    }

    // Méthode pour obtenir la connexion à la base de données
    public Connection getConnection() {
        return connection;
    }

    // Méthode pour fermer la connexion à la base de données
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}
