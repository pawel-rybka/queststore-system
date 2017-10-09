package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class MentorDao extends AbstractDao<T> {

    private Connection c = null;
    private Statement stmt = null;

    public MentorDao(){
        c = DBConnection.getC();
    }

    public ArrayList<Mentor> createObjectFromDatabase() throws SQLException {
        ArrayList<Mentor> mentors = new ArrayList<Mentor>();
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
        return mentors;
    }

    public Mentor createUserObject(String inputEmail, String inputPassword) throws SQLException{

        String sql = String.format("SELECT * FROM Mentors WHERE email = ? AND password = ?;");

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

        Mentor newMentor = new Mentor(id, firstName, lastName,
                phoneNumber, email, password);

        return newMentor;
    }
}

