package dao;

import model.Klass;
import model.Mentor;
import model.Student;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class ClassDao extends AbstractDao<Klass> {
    private Connection c = null;
    private Statement stmt = null;

    public ClassDao() {
        this.c = DBConnection.getC();
    }


    public ArrayList<Klass> getClasses() throws SQLException {
        ArrayList<Klass> classes = new ArrayList<>();
        String sql = "SELECT * FROM Classes;";

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while ( rs.next() ) {
            Integer id = rs.getInt("id");
            String className = rs.getString("class_name");

            Klass newClass = new Klass(id, className);
            classes.add(newClass);
        }
        stmt.close();
        return classes;
    }

    public Klass getClassById(Integer id) throws SQLException {
        String sql = "SELECT * FROM classes WHERE id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        String name = rs.getString("class_name");

        Klass klass = new Klass(id, name);
        rs.close();
        return klass;
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

        String sql = null;

        if (user instanceof Mentor) {
            sql = "INSERT INTO mentors_classes (mentor_id, class_id)" +
                    "VALUES (?, ?)";


        } else if (user instanceof Student) {
            sql = "INSERT INTO students_classes (student_id, class_id)" +
                    "VALUES (?, ?)";
        }

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, user.getId());
        pstmt.setInt(2, classId);
        pstmt.executeUpdate();
        pstmt.close();
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


    public void addObject(Klass klass) throws SQLException {
        String sql = "INSERT INTO Classes (class_name)" +
                "VALUES (?);";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, klass.getClassName());
            pstmt.executeUpdate();
        }
    }


    public void updateData(Klass klass) throws SQLException {

        String sql = "UPDATE classes SET class_name = ? WHERE id = ?;";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, klass.getClassName());
            pstmt.setInt(2, klass.getId());

            pstmt.executeUpdate();
        }
    }

    public void removeObject(Klass object) throws SQLException {
        String sql = "DELETE FROM classes WHERE id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, object.getId());
        pstmt.executeUpdate();

        sql = "DELETE FROM mentors_classes WHERE class_id = ?;";
        pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, object.getId());
        pstmt.executeUpdate();

    }

    public Klass getClassByMentor(Mentor mentor) throws SQLException {
        Klass klass = null;

        String sql = "SELECT * FROM classes INNER JOIN mentors_classes " +
                "ON classes.id = mentors_classes.class_id WHERE mentors_classes.mentor_id = ?;";

        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setInt(1, mentor.getId());
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String name = rs.getString("class_name");
            Integer id = rs.getInt("id");

            klass = new Klass(id, name);
        }
        rs.close();
        return klass;

    }
}
