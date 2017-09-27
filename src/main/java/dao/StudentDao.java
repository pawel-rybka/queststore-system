package dao;

import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDao {

    private ArrayList<Student> students = new ArrayList<Student>();
    private Connection c = null;
    private Statement stmt = null;

    public StudentDao(){
        c = Dao.getC();
    }

    public void createObjectFromDatabase() throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM Students;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String password = rs.getString("password");
            int coins = rs.getInt("coins");
            int totalCoins = rs.getInt("total_coins");
            String level = rs.getString("level");

            Student newStudent = new Student(id, firstName, lastName,
            students.add(newStudent);
            phoneNumber, email, password, coins, totalCoins, level);
        }
        stmt.close();
    }

}