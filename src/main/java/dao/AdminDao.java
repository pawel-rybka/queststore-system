package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class AdminDao extends AbstractDao<Admin> {

    private Connection c = null;
    private Statement stmt = null;

    public AdminDao(){
        c = DBConnection.getC();
    }

    public ArrayList<Admin> getAdmins() throws SQLException {
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

    public Admin getAdminById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Admins WHERE id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String phoneNumber = rs.getString("phone_number");
        String email = rs.getString("email");
        String password = rs.getString("password");

        Admin newAdmin = new Admin(id, firstName, lastName,
                phoneNumber, email, password);

        return newAdmin;
    }

    public Admin getAdminByLogin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Admins WHERE email = ? AND password = ?;";
        Admin newAdmin = null;
        try(PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Integer id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phoneNumber = rs.getString("phone_number");

                newAdmin = new Admin(id, firstName, lastName,
                        phoneNumber, email, password);
            }
        }

        return newAdmin;
    }

    public Admin createUserObject(String inputEmail, String inputPassword) throws SQLException{
        String sql = "SELECT * FROM Admins WHERE email = ? AND password = ?;";

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

    public void addObject(Admin admin) throws SQLException {
        String sql = "INSERT INTO Admins (first_name, last_name, phone_number, email, password)" +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, admin.getFirstName());
        pstmt.setString(2, admin.getLastName());
        pstmt.setString(3, admin.getPhoneNumber());
        pstmt.setString(4, admin.getEmail());
        pstmt.setString(5, admin.getPassword());
        pstmt.executeUpdate();
    }

    public void updateData(Admin admin) throws SQLException {

        String sql = "UPDATE Admins SET first_name = ?, last_name = ?," +
                                    "phone_number = ?, email = ?," +
                                    "password = ? WHERE id = ?;";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, admin.getFirstName());
            pstmt.setString(2, admin.getLastName());
            pstmt.setString(3, admin.getPhoneNumber());
            pstmt.setString(4, admin.getEmail());
            pstmt.setString(5, admin.getPassword());
            pstmt.setInt(6, admin.getId());

            pstmt.executeUpdate();
        }
    }
}