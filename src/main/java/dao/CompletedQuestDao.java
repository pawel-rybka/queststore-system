package dao;

import model.CompletedQuest;

import java.sql.*;
import java.util.ArrayList;

public class CompletedQuestDao extends AbstractDao<CompletedQuest>{
    private Connection c = null;
    private Statement stmt = null;

    public CompletedQuestDao() {
        this.c = DBConnection.getC();
    }

    public ArrayList<CompletedQuest> getCompletedQuests() throws SQLException {
        ArrayList<CompletedQuest> completedQuests = new ArrayList<CompletedQuest>();
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
        return completedQuests;
    }

    public void addObject(CompletedQuest completedQuest) throws SQLException {
        String sql = "INSERT INTO completed_quests (student_id, quest_id, complete_date)" +
                "VALUES (?, ?, ?)";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, completedQuest.getStudentId());
        pstmt.setInt(2, completedQuest.getQuestId());
        pstmt.setString(3, completedQuest.getCompleteDate());
        pstmt.executeUpdate();
    }

    public void updateData(CompletedQuest completedQuest) throws SQLException {
        stmt = c.createStatement();
        String sql = "UPDATE completed_quests SET student_id = ?, quest_id = ?," +
                "complete_date = ? WHERE id = ?;";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, completedQuest.getStudentId());
            pstmt.setInt(2, completedQuest.getQuestId());
            pstmt.setString(3, completedQuest.getCompleteDate());
            pstmt.setInt(4, completedQuest.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<CompletedQuest> getUnmarkedQuests() throws SQLException {
        ArrayList<CompletedQuest> completedQuest = new ArrayList<>();
        String sql = "SELECT * FROM completed_quests WHERE complete_date = '0';";

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( sql );

        while ( rs.next() ) {
            Integer id = rs.getInt("id");
            Integer studentId = rs.getInt("student_id");
            Integer questId = rs.getInt("quest_id");
            String completeData = rs.getString("complete_date");
            CompletedQuest newCompletedQuest = new CompletedQuest(id, studentId, questId, completeData);
            completedQuest.add(newCompletedQuest);
        }

        stmt.close();
        return completedQuest;
    }

    public CompletedQuest getCompletedQuestById(Integer id) throws SQLException {
        String sql = "SELECT * FROM completed_quests WHERE id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        Integer studentId = rs.getInt("student_id");
        Integer questId = rs.getInt("quest_id");
        String completeData = rs.getString("complete_date");
        CompletedQuest newCompletedQuest = new CompletedQuest(id, studentId, questId, completeData);
        return newCompletedQuest;
    }


}
