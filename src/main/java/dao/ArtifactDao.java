package dao;

import model.Admin;
import model.Artifact;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArtifactDao {
    private ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
    private Connection c = null;
    private Statement stmt = null;

    public ArtifactDao() {
        this.c = Dao.getC();
        try {
            loadArtifactsFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadArtifactsFromDB() throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM Artifacts;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String category = rs.getString("category");
            Integer price = rs.getInt("price");

            Artifact newArtifact = new Artifact(id, category, price);
            artifacts.add(newArtifact);
        }
        stmt.close();
    }
}