package fr.poweroff.web.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    public static final Connection CONNECTION;

    static {
        String user     = System.getenv("ORIGINAL_USER");
        String password = System.getenv("ORIGINAL_PASSWORD");
        String host     = System.getenv("ORIGINAL_HOST");

        try {
            // Why ?
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }

        try {
            CONNECTION = DriverManager.getConnection("jdbc:mysql://localhost:3306/original", "root", "");
            //CONNECTION = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306/original", host), user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
