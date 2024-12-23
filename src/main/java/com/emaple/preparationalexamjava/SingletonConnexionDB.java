package com.emaple.preparationalexamjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnexionDB {
    private static Connection connection;

    private SingletonConnexionDB() {}

    public static Connection getConnexion() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cabinet", "root", ""
            );
        }
        return connection;
    }
}
