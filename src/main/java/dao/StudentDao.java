package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class StudentDao extends AbstractDao<T>{

    private Connection c = null;
    private Statement stmt = null;

    public StudentDao(){
        c = DBConnection.getC();
    }

    public ArrayList<Student> createObjectFromDatabase() throws SQLException {
        ArrayList<Student> students = new ArrayList<Student>();
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
            Student newStudent = new Student(id, firstName, lastName,
                    phoneNumber, email, password, coins, totalCoins);
            students.add(newStudent);
        }
        stmt.close();
        return students;
    }

    public Student createUserObject(String inputEmail, String inputPassword) throws SQLException{
        String sql = String.format("SELECT * FROM Students WHERE email = ? AND password = ?;");

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, inputEmail);
        pstmt.setString(2, inputPassword);
        ResultSet rs = pstmt.executeQuery();

        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String phoneNumber = rs.getString("phone_number");
        String email = rs.getString("email");
        String password = rs.getString("password");
        int coins = rs.getInt("coins");
        int totalCoins = rs.getInt("total_coins");

        Student newStudent = new Student(id, firstName, lastName,
                phoneNumber, email, password, coins, totalCoins);

        return newStudent;
    }

}