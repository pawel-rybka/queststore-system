package dao;

import model.Mentor;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        MentorDao mDao = new MentorDao();
        Mentor mentor = mDao.createUserObject("agnieszka.koszany@codecool.com", "1234");
        System.out.println(mentor.getFirstName() + " " +mentor.getLastName());
    }
}
