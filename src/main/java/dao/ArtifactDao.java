package dao;

import model.Admin;
import model.Artifact;

import java.sql.*;
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

    public void removeObject(Integer id) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("DELETE FROM Artifacts WHERE id=%d;", id);
        stmt.executeUpdate(sql);
    }

    public void addObject(Artifact artifact) throws SQLException {
        String sql = "INSERT INTO Artifacts (category, price)" +
                "VALUES (?, ?)";

        Connection conn = Dao.getC();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, artifact.getCategory());
        pstmt.setInt(2, artifact.getPrice());
        pstmt.executeUpdate();
        try {
            artifact.setId(selectLast("Artifacts", c));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateData(Integer id, String columnName, String value) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("UPDATE Artifacts SET %s = ? WHERE id = ?;", columnName);
        try (Connection conn = Dao.getC(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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