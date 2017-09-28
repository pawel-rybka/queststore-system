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

            if (menu.equals("1")) {
                createMentor();
            } else if (menu.equals("2")) {
                seeMentor();
            } else if (menu.equals("3")) {
                editMentor();
            } else if (menu.equals("4")) {
                createClass();
            } else if (menu.equals("5")) {
                createLevel();
            } 
        }
    }

        private void createMentor() {
            view.printMsg("Not implemented yet, operation aborted.");
        }

        private void seeMentor() {
            MentorDao mentorDao = new MentorDao();
            try {
                mentorDao.createObjectFromDatabase();
            } catch (SQLException e) {
                view.printMsg("Database error in mentorDao.createObjectFrom"
                              + "Database(). Operation aborted.");
            } 
            ArrayList<Mentor> mentors = mentorDao.getMentors();

            Integer menu = 0;
            while (menu == 0) {
                if (mentors.size() == 0) {
                    view.printMsg("Mentor list empty, operation aborted.");
                    return;
                }
                for (Mentor mentor : mentors) {
                    view.printNumbered(mentor.getId(), 
                        mentor.getFirstName() + mentor.getLastName());
                }
                menu = validateOption(view.getInput("Choose mentor."), 
                                      mentors.size());
            }
            Mentor mentor = mentors.get(menu-1);
            view.printMsg("Name: " + mentor.getFirstName() + " "
                          + mentor.getLastName());
            view.printMsg("Phone: " + mentor.getPhoneNumber());
            view.printMsg("E-mail: " + mentor.getEmail());
            view.printMsg("Password: " + mentor.getPassword());
        }

        private void editMentor() {
            view.printMsg("Not implemented yet, operation aborted.");
        }

        private void createClass() {
            view.printMsg("Not implemented yet, operation aborted.");
        }

        private void createLevel() {
            view.printMsg("Not implemented yet, operation aborted.");
        }

        private Integer validateOption(String text, int size) {
            Integer number = 0;
            try {
                number = Integer.parseInt(text);
                if (number < 1 || number > size) {
                    number = 0;
                    throw new Exception();
                }
            } catch (Exception e) {
                view.printMsg("Wrong input.");
            }
            return number;
        }
    

}