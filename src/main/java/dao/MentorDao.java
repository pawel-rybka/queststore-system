package dao;

import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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



    public ArrayList<Mentor> getMentors() {
        return mentors;
    }

}

