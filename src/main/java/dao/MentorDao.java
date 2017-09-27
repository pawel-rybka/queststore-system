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

    private void createObjectFromDatabase() throws SQLException {
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

    public void removeObject(Mentor mentor) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("DELETE FROM Mentors WHERE id=%d;",mentor.getId());
        stmt.executeUpdate(sql);
        c.commit();
    }

    public void addObject(Mentor mentor)  {
        String sql = String.format("INSERT INTO Mentors (first_name, last_name, phone_number, email, password)" +
                "VALUES (?, ?, ?, ?, ?)");

        try (Connection conn = Dao.getC(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mentor.getFirstName());
            pstmt.setString(2, mentor.getLastName());
            pstmt.setString(3, mentor.getPhoneNumber());
            pstmt.setString(4, mentor.getEmail());
            pstmt.setString(5, mentor.getPassword());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            mentor.setId(selectLast("Mentors", c));
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



    public ArrayList<Mentor> getMentors() {
        return mentors;
    }

}

