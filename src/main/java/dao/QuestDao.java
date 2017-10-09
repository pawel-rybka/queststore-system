package dao;

import model.Quest;

import java.sql.*;
import java.util.ArrayList;

public class QuestDao {
    private Connection c = null;
    private Statement stmt = null;

    public QuestDao() {
        this.c = DBConnection.getC();
    }

    private ArrayList<Quest> loadQuestsFromDB() throws SQLException {
        ArrayList<Quest> quests = new ArrayList<Quest>();
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM Quests;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String category = rs.getString("category");

            Quest newQuest = new Quest(id, name, category);
            quests.add(newQuest);
        }
        stmt.close();
        return quests;
    }

    public void removeObject(Integer id) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("DELETE FROM Quests WHERE id=%d;", id);
        stmt.executeUpdate(sql);
    }

    public void addObject(Quest quest) throws SQLException {
        String sql = "INSERT INTO Quests (quest_name, category)" +
                "VALUES (?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, quest.getName());
        pstmt.setString(2, quest.getCategory());
        pstmt.executeUpdate();
        try {
            quest.setId(selectLast("Quests", c));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateData(Integer id, String columnName, String value) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("UPDATE Quests SET %s = ? WHERE id = ?;", columnName);
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
