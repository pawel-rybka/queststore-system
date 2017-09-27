package dao;

import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminDao {

    private ArrayList<Admin> admins = new ArrayList<Admin>();
    private Connection c = null;
    private Statement stmt = null;

    public AdminDao(){
        c = Dao.getC();
    }

    public void createObjectFromDatabase() throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM Admins;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String password = rs.getString("password");

            Admin newAdmin = new Admin(id, firstName, lastName,
                    phoneNumber, email, password);
            admins.add(newAdmin);
        }
        stmt.close();
    }


}