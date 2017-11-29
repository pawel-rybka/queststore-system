package dao;

import model.Artifact;
import model.BoughtArtifact;
import model.Student;

import java.sql.*;
import java.util.ArrayList;

public class BoughtArtifactDao extends AbstractDao<BoughtArtifact> {
    private Connection c = null;
    private Statement stmt = null;

    public BoughtArtifactDao() {
        this.c = DBConnection.getC();
    }

    public ArrayList<BoughtArtifact> getBoughtArtifacts() throws SQLException {
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

    public ArrayList<BoughtArtifact> getBoughtArtifactsByUser(Student student) throws SQLException {
        ArrayList<BoughtArtifact> boughtByUser = new ArrayList<BoughtArtifact>();
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM bought_artifacts WHERE id = %d;", student.getId()));

        while (rs.next()) {
            int id = rs.getInt("id");
            Integer studentId = rs.getInt("student_id");
            Integer artifactId = rs.getInt("artifact_id");
            String usageDate = rs.getString("usage_date");

            BoughtArtifact newBoughtArtifact = new BoughtArtifact(id, studentId, artifactId, usageDate);
            boughtByUser.add(newBoughtArtifact);
        }
        stmt.close();
        return boughtByUser;
    }

    public void addObject(BoughtArtifact boughtArtifact) throws SQLException {
        String sql = "INSERT INTO bought_artifacts (student_id, artifact_id, usage_date)" +
                "VALUES (?, ?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, boughtArtifact.getStudentId());
        pstmt.setInt(2, boughtArtifact.getArtifactId());
        pstmt.setString(3, boughtArtifact.getUsageDate());
        pstmt.executeUpdate();
    }

    public void updateData(BoughtArtifact boughtArtifact) throws SQLException {

        String sql = "UPDATE bought_artifacts SET student_id = ?, artifact_id = ?," +
                "usage_date = ? WHERE id = ?;";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, boughtArtifact.getStudentId());
            pstmt.setInt(2, boughtArtifact.getArtifactId());
            pstmt.setString(3, boughtArtifact.getUsageDate());
            pstmt.setInt(4, boughtArtifact.getId());

            pstmt.executeUpdate();
        }
    }

    public ArrayList<BoughtArtifact> getUnmarkedArtifacts() throws SQLException {
        ArrayList<BoughtArtifact> boughtArtifact = new ArrayList<>();
        String sql = "SELECT * FROM bought_artifacts WHERE usage_date = '0';";

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( sql );

        while ( rs.next() ) {
            Integer id = rs.getInt("id");
            Integer studentId = rs.getInt("student_id");
            Integer artifactId = rs.getInt("artifact_id");
            String usageData = rs.getString("usage_date");
            BoughtArtifact newBoughtArtifact = new BoughtArtifact(id, studentId, artifactId, usageData);
            boughtArtifact.add(newBoughtArtifact);
        }

        stmt.close();
        return boughtArtifact;
    }

    public BoughtArtifact getBoughtArtifactById(Integer id) throws SQLException {
        String sql = "SELECT * FROM bought_artifacts WHERE id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        Integer studentId = rs.getInt("student_id");
        Integer artifactId = rs.getInt("artifact_id");
        String usageData = rs.getString("usage_date");
        BoughtArtifact newBoughtArtifact = new BoughtArtifact(id, studentId, artifactId, usageData);
        return newBoughtArtifact;
    }

}