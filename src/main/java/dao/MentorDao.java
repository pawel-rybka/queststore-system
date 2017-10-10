package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class MentorDao extends AbstractDao<Mentor> {

    private Connection conn = null;

    public MentorDao(){
        conn = DBConnection.getC();
    }

    public ArrayList<Mentor> getMentors() throws SQLException {
        ArrayList<Mentor> mentors = new ArrayList<>();
        String sql = "SELECT * FROM Mentors;";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while ( rs.next() ) {
            Integer id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String password = rs.getString("password");

            Mentor newMentor = new Mentor(id, firstName, lastName, phoneNumber, email, password);
            mentors.add(newMentor);
        }
        stmt.close();
        return mentors;
    }

    public Mentor getMentorById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Mentors WHERE id = ?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String phoneNumber = rs.getString("phone_number");
        String email = rs.getString("email");
        String password = rs.getString("password");

        Mentor newMentor = new Mentor(id, firstName, lastName,
                phoneNumber, email, password);

        return newMentor;
    }

    public Mentor createUserObject(String inputEmail, String inputPassword) throws SQLException {
        Mentor newMentor = null;
        String sql = "SELECT * FROM Mentors WHERE email = ? AND password = ?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, inputEmail);
        pstmt.setString(2, inputPassword);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String password = rs.getString("password");

            newMentor = new Mentor(id, firstName, lastName, phoneNumber, email, password);
        }

        pstmt.close();
        rs.close();
        return newMentor;
    }

    public void addObject(Mentor mentor) throws SQLException {
        String sql = "INSERT INTO Mentors (first_name, last_name, phone_number, email, password)" +
                "VALUES (?, ?, ?, ?, ?);";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, mentor.getFirstName());
        pstmt.setString(2, mentor.getLastName());
        pstmt.setString(3, mentor.getPhoneNumber());
        pstmt.setString(4, mentor.getEmail());
        pstmt.setString(5, mentor.getPassword());
        pstmt.executeUpdate();

        pstmt.close();
    }

    public void updateData(Mentor mentor) throws SQLException {
        String sql = "UPDATE Mentors SET first_name = ?, last_name = ?, phone_number = ?, "
                     +"email = ?, password = ? WHERE id = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mentor.getFirstName());
            pstmt.setString(2, mentor.getLastName());
            pstmt.setString(3, mentor.getPhoneNumber());
            pstmt.setString(4, mentor.getEmail());
            pstmt.setString(5, mentor.getPassword());
            pstmt.setInt(4, mentor.getId());

            pstmt.executeUpdate();
        }
    }
}

