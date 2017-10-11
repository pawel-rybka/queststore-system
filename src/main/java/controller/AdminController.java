package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.*;
import view.*;
import dao.*;

public class AdminController {

    private Admin admin;
    private AdminView adminView;
    private ControllerView controllerView;
    private MentorDao mentorDao = new MentorDao();
    
    public AdminController (Admin admin) {
        this.admin = admin;
        this.adminView = new AdminView();
        this.controllerView = new ControllerView();
    }

    public void handleMenu() {
        String menu = "default";

        while (!menu.equals("0")) {
            adminView.printMenu();
            menu = adminView.getInput("Choose option.");

            switch (menu) {
                case "1":
                    createMentor(); break;
                case "2":
                    seeMentor();    break;
                case "3":
                    editMentor();   break;
                case "4":
                    createClass();  break;
                case "5":
                    createLevel();  break;
            }
        }
    }

    private void createMentor() {
        String[] personalData = controllerView.getPersonalData();
        Mentor newMentor = new Mentor(personalData[0], personalData[1], personalData[2],
                                      personalData[3], personalData[4]);
        try {
            mentorDao.addObject(newMentor);
        } catch (SQLException e) {
            adminView.printMsg("Database error, can't add a mentor.");
        }
    }

    private void seeMentor() {
        ArrayList<Mentor> mentors = buildMentors();
        if (mentors == null) return;

        Mentor chosenMentor = null;
        while (chosenMentor == null) {
            for (Mentor mentor : mentors) {
                adminView.printNumbered(mentor.getId(), mentor.getFullName());
            }
            chosenMentor = pickMentor();
        }

        adminView.printPersonalData(chosenMentor);
    }

    private void editMentor() {
        ArrayList<Mentor> mentors = buildMentors();
        if (mentors == null) return;

        Mentor mentor = null;
        while (mentor == null) {
            mentor = pickMentor();
        }

        mentor.setFirstName(changeAttribute("First name", mentor.getFirstName()));
        mentor.setLastName(changeAttribute("Last name", mentor.getLastName()));
        mentor.setPhoneNumber(changeAttribute("Phone number", mentor.getPhoneNumber()));
        mentor.setEmail(changeAttribute("Email", mentor.getEmail()));
        mentor.setPassword(changeAttribute("Password", mentor.getPassword()));

        try {
            mentorDao.updateData(mentor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createClass() {
        adminView.printMsg("Not implemented yet, operation aborted.");
    }

    private void createLevel() {
        adminView.printMsg("Not implemented yet, operation aborted.");
    }

    private  ArrayList<Mentor> buildMentors() {
        ArrayList<Mentor> mentors = null;

        try {
            mentors = mentorDao.getMentors();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (mentors.size() == 0) {
            adminView.printMsg("Mentor list empty, operation aborted.");
        }
        return mentors;
    }

    private Mentor pickMentor() {
        Mentor mentor = null;
        try {
            mentor = mentorDao.getMentorById(Integer.parseInt(adminView.getInput("Enter ID: ")));
        } catch (Exception e) {
            adminView.printMsg("Wrong input!");
        }
        return mentor;
    }

    private String changeAttribute(String attributeName, String oldValue) {
        String newValue = adminView.getInput(attributeName + " = " + oldValue
                                        + ". Press Enter to skip change or type new value: ");
        if (newValue.equals("")) newValue = oldValue;
        return newValue;
    }
}