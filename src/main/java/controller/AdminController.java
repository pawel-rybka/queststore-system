package controller;

import java.lang.Exception;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;
import view.*;
import dao.*;

public class AdminController {
    
    private View view;
    
    public AdminController (View view) {
        this.view = view;
    }

    public void handleMenu() {
        String menu = "default";

        while (!menu.equals("0")) {
            view.printMenu("admin");
            menu = view.getInput("Choose option.");

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
        String firstName = view.getInput("Enter first name. ");
        String lastName = view.getInput("Enter last name: ");
        String phoneNumber = view.getInput("Enter phone number: ");
        String email = view.getInput("Enter email: ");
        String password = view.getInput("Enter password: ");

        MentorDao mentorDao = new MentorDao();
        Mentor newMentor = new Mentor(firstName, lastName, phoneNumber, email, password);
        try {
            mentorDao.addObject(newMentor);
        } catch (SQLException e) {
            view.printMsg("Database error, can't add a mentor.");
        }
    }

    private void seeMentor() {
        MentorDao mentorDao = new MentorDao();
        ArrayList<Mentor> mentors = mentorDao.getMentors();
        Mentor chosenMentor = null;

        while ((chosenMentor == null)) {
            if (mentors.size() == 0) {
                view.printMsg("Mentor list empty, operation aborted.");
                return;
            }
            for (Mentor mentor : mentors) {
                view.printNumbered(mentor.getId(), mentor.getFullName());
            }
            chosenMentor = pickMentor(view.getInput("Choose mentor."), mentors);
        }
        view.printMsg("Name: " + chosenMentor.getFullName());
        view.printMsg("Phone: " + chosenMentor.getPhoneNumber());
        view.printMsg("E-mail: " + chosenMentor.getEmail());
        view.printMsg("Password: " + chosenMentor.getPassword());
    }

    private void editMentor() {
        MentorDao mentorDao = new MentorDao();
        ArrayList<Mentor> mentors = mentorDao.getMentors();
        Mentor mentor = pickMentor(view.getInput("Enter ID: "), mentors);

        mentor.setFirstName(changeAttribute("First name", mentor.getFirstName()));
        mentor.setLastName(changeAttribute("Last name", mentor.getLastName()));
        mentor.setPhoneNumber(changeAttribute("Phone number", mentor.getPhoneNumber()));
        mentor.setEmail(changeAttribute("Email", mentor.getEmail()));
        mentor.setPassword(changeAttribute("Password", mentor.getPassword()));

        mentorDao.updateData(mentor);
    }

    private void createClass() {
        view.printMsg("Not implemented yet, operation aborted.");
    }

    private void createLevel() {
        view.printMsg("Not implemented yet, operation aborted.");
    }

    private Mentor pickMentor(String text, ArrayList<Mentor> mentors) {
        Mentor pickedMentor = new Mentor(-1, "", "", "", "", "");
        try {
            for (Mentor mentor : mentors) {
                if (mentor.getId().equals(text)) pickedMentor = mentor;
            }
        } catch (Exception e) {
            view.printMsg("Wrong input.");
            pickedMentor = null;
        }
        return pickedMentor;
    }

    private String changeAttribute(String attributeName, String oldValue) {
        String newValue = view.getInput(attributeName + " = " + oldValue
                                        + ". Press Enter, to skip change or enter new value: ");
        if (newValue.equals("")) newValue = oldValue;
        return newValue;
    }
}