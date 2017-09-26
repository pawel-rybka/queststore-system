package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
    private static Connection c = null;

    public static void connectBase() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

    }

    public static void closeDatabase(){

        try{
            c.close();
        } catch (SQLException e){
            System.out.println("Something went wrong!");
        }
    }

    public static Connection getC() {
        if (c == null){
            connectBase();
        }
        return c;
    }
}
