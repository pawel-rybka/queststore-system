package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection c = null;

    private static void connectBase() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:target/database.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
//        System.out.println("Opened database successfully");

    }

    public static void closeDatabase() throws SQLException {
        if (c != null) {
            c.close();
        }
    }

    public static Connection getC() {
        if (c == null){
            connectBase();
        }
        return c;
    }
}
