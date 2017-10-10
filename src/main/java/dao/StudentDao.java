package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class StudentDao extends AbstractDao<Student>{

    private Connection conn = null;

    public StudentDao(){
        conn = DBConnection.getC();
    }

    public ArrayList<Student> getStudents() throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students;";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery( "" );

        while ( rs.next() ) {
            Integer id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String password = rs.getString("password");
            Integer coins = rs.getInt("coins");
            Integer totalCoins = rs.getInt("total_coins");
            Student newStudent = new Student(id, firstName, lastName, phoneNumber,
                                             email, password, coins, totalCoins);
            students.add(newStudent);
        }

        stmt.close();
        return students;
    }

    public Student getStudentById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Students WHERE id = ?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String phoneNumber = rs.getString("phone_number");
        String email = rs.getString("email");
        String password = rs.getString("password");
        Integer coins = rs.getInt("coins");
        Integer totalCoins = rs.getInt("total_coins");
        Student newStudent = new Student(id, firstName, lastName, phoneNumber,
                email, password, coins, totalCoins);

        return newStudent;
    }

    public Student createUserObject(String inputEmail, String inputPassword) throws SQLException{
        String sql = "SELECT * FROM students WHERE email = ? AND password = ?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, inputEmail);
        pstmt.setString(2, inputPassword);
        ResultSet rs = pstmt.executeQuery();

        Integer id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String phoneNumber = rs.getString("phone_number");
        String email = rs.getString("email");
        String password = rs.getString("password");
        Integer coins = rs.getInt("coins");
        Integer totalCoins = rs.getInt("total_coins");

        Student newStudent = new Student(id, firstName, lastName,
                phoneNumber, email, password, coins, totalCoins);

        return newStudent;
    }

    public void addObject(Student student) throws SQLException {
        String sql = "INSERT INTO students (first_name, last_name, phone_number, email, password, coins, total_coins)"
                     + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getPhoneNumber());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPassword());
            pstmt.setInt(6, student.getCoins());
            pstmt.setInt(7, student.getTotalCoins());
            pstmt.executeUpdate();
        }
    }



    public void updateData(Student student) throws SQLException {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, phone_number = ?, "
                     + "email = ?, password = ?, coins = ?, total_coins = ? WHERE id = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getPhoneNumber());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPassword());
            pstmt.setInt(6, student.getCoins());
            pstmt.setInt(7, student.getTotalCoins());
            pstmt.setInt(8, student.getId());
            pstmt.executeUpdate();
        }
    }

}