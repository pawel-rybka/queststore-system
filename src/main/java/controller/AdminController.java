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
    
    public AdminController (Admin admin) {
        this.admin = admin;
        this.adminView = new AdminView();
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
        MentorDao mentorDao = new MentorDao();
        try {
            mentorDao.addObject(newMentor);
        } catch (SQLException e) {
            adminView.printMsg("Database error, can't add a mentor.");
        }
    }

    private void seeMentor() {
        MentorDao mentorDao = new MentorDao();
        ArrayList<Mentor> mentors = null;

        try {
            mentors = mentorDao.getMentors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Mentor chosenMentor = null;

        while ((chosenMentor.getId() == null)) {
            if (mentors.size() == 0) {
                adminView.printMsg("Mentor list empty, operation aborted.");
                return;
            }
            for (Mentor mentor : mentors) {
                adminView.printNumbered(mentor.getId(), mentor.getFullName());
            }
            chosenMentor = pickMentor(adminView.getInput("Choose mentor."), mentors);
        }

        adminView.printMsg("Name: " + chosenMentor.getFullName());
        adminView.printMsg("Phone: " + chosenMentor.getPhoneNumber());
        adminView.printMsg("E-mail: " + chosenMentor.getEmail());
        adminView.printMsg("Password: " + chosenMentor.getPassword());
    }

    private void editMentor() {
        MentorDao mentorDao = new MentorDao();
        ArrayList<Mentor> mentors = null;

        try {
            mentors = mentorDao.getMentors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Mentor mentor = null;

        while ((mentor.getId() == null)) {
            if (mentors.size() == 0) {
                adminView.printMsg("Mentor list empty, operation aborted.");
                return;
            }
            mentor = pickMentor(adminView.getInput("Enter ID: "), mentors);
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

    private Mentor pickMentor(String text, ArrayList<Mentor> mentors) {
        Mentor pickedMentor = new Mentor(null, null, null, null, null);
        for (Mentor mentor : mentors) {
            if (mentor.getId().toString().equals(text)) {
                pickedMentor = mentor;
            }
        }
        return pickedMentor;
    }

    private String changeAttribute(String attributeName, String oldValue) {
        String newValue = adminView.getInput(attributeName + " = " + oldValue
                                        + ". Press Enter, to skip change or enter new value: ");
        if (newValue.equals("")) newValue = oldValue;
        return newValue;
    }
}