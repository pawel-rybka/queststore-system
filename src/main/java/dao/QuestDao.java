package dao;

import model.Quest;

import java.sql.*;
import java.util.ArrayList;

public class QuestDao extends AbstractDao<Quest> {
    private Connection c = null;
    private Statement stmt = null;

    public QuestDao() {
        this.c = DBConnection.getC();
    }

    public ArrayList<Quest> getQuests() throws SQLException {
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

    public void addObject(Quest quest) throws SQLException {
        String sql = "INSERT INTO quests (name, category)" +
                "VALUES (?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, quest.getName());
        pstmt.setString(2, quest.getCategory());
        pstmt.executeUpdate();
    }

    public void updateData(Quest quest) throws SQLException {

        String sql = "UPDATE quests SET name = ?, category = ? WHERE id = ?;";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, quest.getName());
            pstmt.setString(2, quest.getCategory());
            pstmt.setInt(3, quest.getId());

            pstmt.executeUpdate();
        }
    }
}
