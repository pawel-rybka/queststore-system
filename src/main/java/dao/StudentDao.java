package dao;

import model.*;

import java.sql.*;
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

            Student newStudent = new Student(id, firstName, lastName,
                    phoneNumber, email, password, coins, totalCoins);
            students.add(newStudent);
        }
        stmt.close();
    }

    public void removeObject(Student student) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("DELETE FROM Students WHERE id=%d;",student.getId());
        stmt.executeUpdate(sql);
        c.commit();
    }

    public void addObject(Student student)  {
        String sql = String.format("INSERT INTO Student (first_name, last_name, phone_number, email, password, coins, total_coins)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");

        try (Connection conn = Dao.getC(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getPhoneNumber());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPassword());
            pstmt.setInt(6, student.getCoins());
            pstmt.setInt(7, student.getTotalCoins());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            student.setId(selectLast("Students", c));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Integer selectLast(String table, Connection c) throws SQLException{

        Integer id = null;
        stmt = c.createStatement();
        ResultSet result = stmt.executeQuery( String.format("SELECT id FROM %s\n", table) +
                "ORDER BY id DESC\n" +
                "LIMIT 1;");

        while (result.next()){
            id = result.getInt("id");
        }
        return id;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}