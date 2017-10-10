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

    public ArrayList<Artifact> getArtifacts() throws SQLException {
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

    public Artifact getArtifactById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Artifacts WHERE id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        String name = rs.getString("name");
        String category = rs.getString("category");
        Integer price = rs.getInt("price");
        Artifact newArtifact = new Artifact(id, name, category, price);
        return newArtifact;
    }

    public void addObject(Artifact artifact) throws SQLException {
        String sql = "INSERT INTO Artifacts (name, category, price)" +
                "VALUES (?, ?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, artifact.getName());
        pstmt.setString(2, artifact.getCategory());
        pstmt.setInt(3, artifact.getPrice());
        pstmt.executeUpdate();
    }

    public void updateData(Artifact artifact) throws SQLException {

        String sql = "UPDATE Artifacts SET name = ?, category = ?," +
                "price = ? WHERE id = ?;";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, artifact.getName());
            pstmt.setString(2, artifact.getCategory());
            pstmt.setInt(3, artifact.getPrice());
            pstmt.setInt(4, artifact.getId());

            pstmt.executeUpdate();
        }
    }
}
