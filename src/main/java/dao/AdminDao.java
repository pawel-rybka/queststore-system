package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class AdminDao extends AbstractDao<T> {

    private Connection c = null;
    private Statement stmt = null;

    public AdminDao(){
        c = DBConnection.getC();
    }

    public ArrayList<Admin> createObjectFromDatabase() throws SQLException {
        ArrayList<Admin> admins = new ArrayList<Admin>();
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
        return admins;
    }

    public Admin createUserObject(String inputEmail, String inputPassword) throws SQLException{
        String sql = String.format("SELECT * FROM Admins WHERE email = ? AND password = ?;");

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

        Admin newAdmin = new Admin(id, firstName, lastName,
                phoneNumber, email, password);

        return newAdmin;
    }
}