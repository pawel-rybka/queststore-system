package dao;

import model.Mentor;
import model.Student;
import model.User;

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
        rs.close();
        return mentorsId;
    }

    public ArrayList<Student> getStudentByClassId(Integer classId) throws SQLException {

        ArrayList<Integer> studentsId = getStudentsId(classId);
        ArrayList<Student> students = new ArrayList<>();
        StudentDao sDao = new StudentDao();

        for (Integer studentId: studentsId) {
            Student student = sDao.getStudentById(studentId);
            students.add(student);
        }
        return students;
    }

    private ArrayList<Integer> getStudentsId(Integer classId) throws SQLException {
        ArrayList<Integer> studentsId = new ArrayList<>();
        String sql = "SELECT * FROM students_classes WHERE class_id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, classId);
        ResultSet rs = pstmt.executeQuery();

        while ( rs.next() ) {
            Integer studentId = rs.getInt("student_id");
            studentsId.add(studentId);
        }
        rs.close();
        return studentsId;
    }

    public void addUserToClass(User user, Integer classId) throws SQLException {
        if (user instanceof Mentor) {
            String sql = "INSERT INTO mentors_classes (class_id, mentor_id)" +
                    "VALUES (?, ?)";

            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, classId);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();
            pstmt.close();

        } else if (user instanceof Student) {
            String sql = "INSERT INTO students_classes (student_id, class_id)" +
                    "VALUES (?, ?)";

            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.setInt(2, classId);
            pstmt.executeUpdate();
            pstmt.close();
        }
    }

    public void removeUserFromClass(User user) throws SQLException {
        if (user instanceof Mentor) {
            String sql = "DELETE FROM mentors_classes WHERE mentor_id = ?";
            deleteUserFromBase(user, sql);

        } else if (user instanceof Student) {
            String sql = "DELETE FROM students_classes WHERE student_id = ?";
            deleteUserFromBase(user, sql);
        }
    }

    private void deleteUserFromBase(User user, String sql) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, user.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }
}
