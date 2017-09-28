package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class MentorDao {

    private ArrayList<Mentor> mentors = new ArrayList<Mentor>();
    private Connection c = null;
    private Statement stmt = null;

    public MentorDao(){
        c = Dao.getC();
    }

    public void createObjectFromDatabase() throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM Mentors;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String password = rs.getString("password");

            Mentor newMentor = new Mentor(id, firstName, lastName,
                    phoneNumber, email, password);
            mentors.add(newMentor);
        }
        stmt.close();
    }

    public Mentor createUserObject(String inputEmail, String inputPassword) throws SQLException{

        String sql = String.format("SELECT * FROM Mentors WHERE email = ? AND password = ?;");

        Connection conn = Dao.getC();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, inputEmail);
        pstmt.setString(2, inputPassword);

        ResultSet rs = pstmt.executeQuery();

        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String phoneNumber = rs.getString("phone_number");
        String email = rs.getString("email");
        String password = rs.getString("password");

        Mentor newMentor = new Mentor(id, firstName, lastName,
                phoneNumber, email, password);

        return newMentor;
    }

    public void removeObject(Integer id) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("DELETE FROM Mentors WHERE id=%d;", id);
        stmt.executeUpdate(sql);
    }

    public void addObject(Mentor mentor) throws SQLException {
        String sql = "INSERT INTO Mentors (first_name, last_name, phone_number, email, password)" +
                "VALUES (?, ?, ?, ?, ?);";

            Connection conn = Dao.getC();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mentor.getFirstName());
            pstmt.setString(2, mentor.getLastName());
            pstmt.setString(3, mentor.getPhoneNumber());
            pstmt.setString(4, mentor.getEmail());
            pstmt.setString(5, mentor.getPassword());

            pstmt.executeUpdate();

        try {
            mentor.setId(selectLast("Mentors", conn));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateData(Integer id, String columnName, String value) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("UPDATE Mentors SET %s = ? WHERE id = ?;", columnName);
        try (Connection conn = Dao.getC(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, value);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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



    public ArrayList<Mentor> getMentors() {
        return mentors;
    }

}

