package dao;

import model.Mentor;
import model.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
//        MentorDao mDao = new MentorDao();
//        Mentor mentor = mDao.createUserObject("agnieszka.koszany@codecool.com", "1234");
//        System.out.println(mentor.getFirstName() + " " +mentor.getLastName());
        StudentDao sDao = new StudentDao();
        ArrayList<Student> students = sDao.createObjectFromDatabase();
        for (Student student: students){
            if (student.getFirstName().equals("c")) {
                sDao.removeObject(student);
            }
        }
        students = sDao.createObjectFromDatabase();
        for (Student student: students) {
            System.out.println(student.getFirstName());
        }
    }
}
