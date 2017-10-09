package dao;

import model.BoughtArtifact;

import java.sql.*;
import java.util.ArrayList;

public class BoughtArtifactDao {
    private Connection c = null;
    private Statement stmt = null;

    public BoughtArtifactDao() {
        this.c = DBConnection.getC();
    }

    private ArrayList<BoughtArtifact> loadBoughtArtifactsFromDB() throws SQLException {
        ArrayList<BoughtArtifact> boughtArtifacts = new ArrayList<BoughtArtifact>();
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM bought_artifacts;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            Integer studentId = rs.getInt("student_id");
            Integer artifactId = rs.getInt("artifact_id");
            String usageDate = rs.getString("usage_date");

            BoughtArtifact newBoughtArtifact = new BoughtArtifact(id, studentId, artifactId, usageDate);
            boughtArtifacts.add(newBoughtArtifact);
        }
        stmt.close();
        return boughtArtifacts;
    }

    public void removeObject(Integer id) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("DELETE FROM BougArtifacts WHERE id=%d;", id);
        stmt.executeUpdate(sql);
    }

    public void addObject(BoughtArtifact boughtArtifact) throws SQLException {
        String sql = "INSERT INTO bought_artifacts (student_id, artifact_id, usage_date)" +
                "VALUES (?, ?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, boughtArtifact.getStudentId());
        pstmt.setInt(2, boughtArtifact.getArtifactId());
        pstmt.setString(3, boughtArtifact.getUsageDate());
        pstmt.executeUpdate();
        try {
            boughtArtifact.setId(selectLast("bought_artifacts", c));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateData(Integer id, String columnName, String value) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("UPDATE bought_artifacts SET %s = ? WHERE id = ?;", columnName);
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, value);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Integer selectLast(String table, Connection c) throws SQLException {
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
}