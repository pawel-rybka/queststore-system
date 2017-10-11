package dao;

import model.Mentor;

import java.sql.*;
import java.util.ArrayList;

public class ClassDao {
    private Connection c = null;
    private Statement stmt = null;

    public ClassDao() {
        this.c = DBConnection.getC();
    }

    public ArrayList<Mentor> getMentorsByClassId(Integer classId) throws SQLException {

        ArrayList<Integer> mentorsId = getMentorsId(classId);
        ArrayList<Mentor> mentors = new ArrayList<>();
        MentorDao mDao = new MentorDao();

        for (Integer mentorId: mentorsId) {
            Mentor mentor = mDao.getMentorById(mentorId);
            mentors.add(mentor);
        }
        return mentors;
    }

    private ArrayList<Integer> getMentorsId(Integer classId) throws SQLException {
        ArrayList<Integer> mentorsId = new ArrayList<>();
        String sql = "SELECT * FROM mentors_classes WHERE class_id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, classId);
        ResultSet rs = pstmt.executeQuery();

        while ( rs.next() ) {
            Integer mentorId = rs.getInt("mentor_id");
            mentorsId.add(mentorId);
        }
        return mentorsId;
    }
}
