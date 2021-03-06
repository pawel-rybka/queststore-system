package dao;

import model.Level;

import java.sql.*;
import java.util.ArrayList;

public class LevelDao extends AbstractDao<Level> {
    private Connection c = null;
    private Statement stmt = null;

    public LevelDao() {
        this.c = DBConnection.getC();
    }

    public ArrayList<Level> getLevels() throws SQLException {
        ArrayList<Level> Levels = new ArrayList<Level>();
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM Levels;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int expLevel = rs.getInt("expLevel");

            Level newLevel = new Level(id, name, expLevel);
            Levels.add(newLevel);
        }
        stmt.close();
        return Levels;
    }

    public void addObject(Level level) throws SQLException {
        String sql = "INSERT INTO Levels (name, expLevel)" +
                "VALUES (?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, level.getName());
        pstmt.setInt(2, level.getExpLevel());
        pstmt.executeUpdate();
    }

    public void updateData(Level level) throws SQLException {

        String sql = "UPDATE Levels SET name = ?, expLevel = ? WHERE id = ?;";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, level.getName());
            pstmt.setInt(2, level.getExpLevel());
            pstmt.setInt(3, level.getId());

            pstmt.executeUpdate();
        }
    }

    public Level getLevelById(Integer id) throws SQLException {
        String sql = "SELECT * FROM levels WHERE id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        String name = rs.getString("name");
        Integer expLevel = rs.getInt("expLevel");

        Level level = new Level(id, name, expLevel);

        return level;
    }


    public String getLevelByCoinAmount(Integer coins) throws SQLException {
        String sql = "SELECT * " +
                    "FROM levels " +
                    "WHERE expLevel >= ?" +
                    "ORDER BY expLevel ASC " +
                    "LIMIT 1;";


        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, coins);
        ResultSet rs = pstmt.executeQuery();

//        Integer id = rs.getInt("id");
        String name = rs.getString("name");
//        Integer expLevel = rs.getInt("expLevel");

//        Level level = new Level(id, name, expLevel);

//        return level.getName();
        return name;
    }

}
