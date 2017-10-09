package dao;

import model.BoughtArtifact;

import java.sql.*;
import java.util.ArrayList;

public class BoughtArtifactDao extends AbstractDao<BoughtArtifact> {
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
}