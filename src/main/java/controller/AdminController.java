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
    private ClassDao classDao = new ClassDao();
    
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
                    assignMentorToClass(); break;
                case "6":
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
        String className = controllerView.getInput("Enter class name: ");
        Klass newClass = new Klass(className);

        ClassDao classDao = new ClassDao();
        try {
            classDao.addObject(newClass);
        } catch (SQLException e) {
            adminView.printMsg("Database error, can't add a class.");
        }
    }


    private void assignMentorToClass() {

        ArrayList<Mentor> mentors = null;
        try{
            mentors = mentorDao.getMentors();
        } catch (SQLException e) {
            adminView.printMsg("Database error, can't load mentors.");
        }

        ArrayList<Klass> classes = null;
        try{
            classes = classDao.getClasses();
        } catch (SQLException e) {
            adminView.printMsg("Database error, can't load mentors.");
        }

        System.out.println(classes.size());


        Mentor mentor = chooseMentor(mentors);
        int classId = chooseClassId(classes);

        try {
            classDao.addUserToClass(mentor, classId);
        } catch (SQLException e) {
            adminView.printMsg("Database error, can't add a mentor to class.");
        }
    }



    private void createLevel() {
        LevelDao levelDao = new LevelDao();
        Integer expLevel = -1;
        while (expLevel < 0) {
            expLevel = validateExpLevel(adminView.getInput("Enter experience level: "));
        }
        Level newLevel = new Level(adminView.getInput("Enter level name: "), expLevel);
        try {
            levelDao.addObject(newLevel);
        } catch (SQLException e) {
            adminView.printMsg("Database error, can't add new level.");
        }
    }

    private Integer validateExpLevel(String input) {
        Integer expLevel = -1;
        try {
            expLevel = Integer.parseInt(input);
            if (expLevel < 0 ) {
                expLevel = -1;
                throw new Exception();
            }
        } catch (Exception e) {
            adminView.printMsg("Wrong input.");
        }
        return expLevel;
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


    private Integer validateOption(String text, int size) {
        Integer number = 0;
        Integer id = 0;
        try {
            id = Integer.parseInt(text);
            if (id < 1 || id > size) {
                number = 0;
                throw new Exception();
            }
        } catch (Exception e) {
            adminView.printMsg("Wrong input.");
        }
        return number;
    }


    private Mentor chooseMentor(ArrayList<Mentor> mentors) {

        int menu = 0;
        Mentor mentor = null;
        String mentorId = null;

        if (mentors.size() == 0) {
            adminView.printMsg("Mentors list empty, operation aborted.");
        } else {
            do {
                mentorId = controllerView.getInput("Enter mentor id: ");
                menu = validateOption(mentorId, mentors.size());
            } while (menu != 0);

            try {
                mentor = mentorDao.getMentorById(Integer.parseInt(mentorId));
            } catch (SQLException e) {
                System.out.println("Can't load mentor");
            }
        }
        return mentor;
    }


    private int chooseClassId (ArrayList<Klass> classes) {

        int menu = 0;
        String inputClassId = null;
        Integer classId = 0;

        if (classes.size() == 0) {
            adminView.printMsg("Classes list empty, operation aborted.");
        } else {
            do {
                inputClassId = controllerView.getInput("Enter class id: ");
                menu = validateOption(inputClassId, classes.size());
            } while (menu != 0);

            classId = Integer.parseInt(inputClassId);
        }
        return classId;
    }
}