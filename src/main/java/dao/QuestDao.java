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
}
