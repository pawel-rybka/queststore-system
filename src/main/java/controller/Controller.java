package controller;

import dao.AdminDao;
import dao.MentorDao;
import dao.StudentDao;
import model.Admin;
import model.Mentor;
import model.Student;
import view.ControllerView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


public class Controller {

    private ControllerView controllerView;

    public Controller() {
        this.controllerView = new ControllerView();
    }

    public void loginMenu() {
        String userOption = "default";

        while(!userOption.equals("0")) {
            this.controllerView.printMenu();
            userOption = this.controllerView.getInput("\nChoose user: ");

            if (userOption.equals("1")) {
                loginAsAdmin();
            } else if (userOption.equals("2")) {
                loginAsMentor();
            } else if (userOption.equals("3")) {
                loginAsStudent();
            }
        }
    }



    private void loginAsAdmin() {

        Admin admin = null;

        String login = this.controllerView.getUserLogin();
        String password = this.controllerView.getUserPassword();

        AdminDao adminDao = new AdminDao();
        try {
            admin = adminDao.createUserObject(login, password);
        } catch (SQLException e) {
            controllerView.printMsg("Email or password is incorrect");
        }

        if(!(admin == null)) {
            System.out.println(admin.getFullName());
        }
    }

    private void loginAsMentor() {

        Mentor mentor = null;

        String login = this.controllerView.getUserLogin();
        String password = this.controllerView.getUserPassword();

        MentorDao mentorDao = new MentorDao();
        try {
            mentor = mentorDao.createUserObject(login, password);
        } catch (SQLException e) {
            controllerView.printMsg("Email or password is incorrect");
        }

        if(!(mentor == null)) {
            System.out.println(mentor.getFullName());
        }
    }

    private void loginAsStudent() {

        Student student = null;

        String login = this.controllerView.getUserLogin();
        String password = this.controllerView.getUserPassword();

        StudentDao studentDao = new StudentDao();
        try {
            student = studentDao.createUserObject(login, password);
        } catch (SQLException e) {
            controllerView.printMsg("Email or password is incorrect");
        }

        if(!(student == null)) {
            System.out.println(student.getFullName());
        }
    }
}

