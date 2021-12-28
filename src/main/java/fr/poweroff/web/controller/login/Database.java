package fr.poweroff.web.controller.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Connection loadDatabase() {
        // Chargement du driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }

        try {
            Connection connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/original", "root", "");
            return connexion;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
