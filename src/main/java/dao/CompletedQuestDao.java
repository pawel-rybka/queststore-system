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

    private ArrayList<CompletedQuest> loadCompletedQuestsFromDB() throws SQLException {
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
}
