package dao;

import model.*;

import java.sql.*;
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

    public Admin createUserObject(String inputEmail, String inputPassword) throws SQLException{
        String sql = String.format("SELECT * FROM Admins WHERE email = ? AND password = ?;");

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

        Admin newAdmin = new Admin(id, firstName, lastName,
                phoneNumber, email, password);

        return newAdmin;
    }

    public void removeObject(Admin admin) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("DELETE FROM Admins WHERE id=%d;",admin.getId());
        stmt.executeUpdate(sql);
    }

    public void addObject(Admin admin) throws SQLException {
        String sql = "INSERT INTO Admins (first_name, last_name, phone_number, email, password)" +
                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = Dao.getC();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, admin.getFirstName());
        pstmt.setString(2, admin.getLastName());
        pstmt.setString(3, admin.getPhoneNumber());
        pstmt.setString(4, admin.getEmail());
        pstmt.setString(5, admin.getPassword());
        pstmt.executeUpdate();
        try {
            admin.setId(selectLast("Admins", c));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateData(Admin admin, String columnName, String value) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("UPDATE Admins SET %s = ? WHERE id = ?;", columnName);
        try (Connection conn = Dao.getC(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, value);
            pstmt.setInt(2, admin.getId());

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

    public ArrayList<Admin> getAdmins() {
        return admins;
    }
}