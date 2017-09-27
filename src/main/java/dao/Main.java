package dao;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        MentorDao mentorDao = new MentorDao();
        mentorDao.createObjectFromDatabase();
        System.out.println(mentorDao.getMentors());
        StudentDao studentDao = new StudentDao();
        studentDao.createObjectFromDatabase();
        System.out.println(studentDao.getStudents());
        AdminDao adminDao = new AdminDao();
        adminDao.createObjectFromDatabase();
        System.out.println(adminDao.getAdmins());
    }
}
