package dao;

import model.CompletedQuest;

import java.sql.*;
import java.util.ArrayList;

public class CompletedQuestDao {
    private ArrayList<CompletedQuest> completedQuests = new ArrayList<CompletedQuest>();
    private Connection c = null;
    private Statement stmt = null;

    public CompletedQuestDao() {
        this.c = DBConnection.getC();
        try {
            loadCompletedQuestsFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCompletedQuestsFromDB() throws SQLException {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM completed_quests;" );

        while ( rs.next() ) {
            int id = rs.getInt("id");
            Integer studentId = rs.getInt("student_id");
            Integer questId = rs.getInt("quest_id");
            String completeDate = rs.getString("complete_date");

            CompletedQuest newCompletedQuest = new CompletedQuest(id, studentId, questId, completeDate);
            completedQuests.add(newCompletedQuest);
        }
        stmt.close();
    }

    public void removeObject(Integer id) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("DELETE FROM completed_quests WHERE id=%d;", id);
        stmt.executeUpdate(sql);
    }

    public void addObject(CompletedQuest completedQuest) throws SQLException {
        String sql = "INSERT INTO completed_quests (student_id, quest_id, complete_date)" +
                "VALUES (?, ?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, completedQuest.getStudentId());
        pstmt.setInt(2, completedQuest.getQuestId());
        pstmt.setString(3, completedQuest.getCompleteDate());
        pstmt.executeUpdate();
        try {
            completedQuest.setId(selectLast("completed_quests", c));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateData(Integer id, String columnName, String value) throws SQLException {
        stmt = c.createStatement();
        String sql = String.format("UPDATE completed_quests SET %s = ? WHERE id = ?;", columnName);
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
