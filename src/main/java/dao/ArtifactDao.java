package dao;

import model.Artifact;

import java.sql.*;
import java.util.ArrayList;

public class ArtifactDao extends AbstractDao<Artifact> {
    private Connection c = null;
    private Statement stmt = null;

    public ArtifactDao() {
        this.c = DBConnection.getC();
    }

    private ArrayList<Artifact> loadArtifactsFromDB() throws SQLException {
        ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM Artifacts;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String category = rs.getString("category");
            Integer price = rs.getInt("price");
            Artifact newArtifact = new Artifact(id, name, category, price);
            artifacts.add(newArtifact);
        }
        stmt.close();
        return artifacts;
    }
}
