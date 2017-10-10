package controller;

import java.lang.Exception;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;
import view.*;
import dao.*;

public class MentorController {
    
    private View view;
    
    public MentorController (View view) {
        this.view = view;
    }

    public void handleMenu() {
        String menu = "default";

        while (!menu.equals("0")) {
            view.printMenu("mentor");
            menu = view.getInput("Choose option.");

            switch (menu) {
                case "1":
                    createStudent();    break;
                case "2":
                    addQuest();         break;
                case "3":
                    addArtifact();      break;
                case "4":
                    updateQuest();      break;
                case "5":
                    updateArtifact();   break;
                case "6":
                    markQuest();        break;
                case "7":
                    markArtifact();     break;
                case "8":
                    seeWallet();        break;
            }
        }
    }

    private void createStudent() {
        String firstName = view.getInput("Enter first name.");
        String lastName = view.getInput("Enter last name.");
        String phoneNumber = view.getInput("Enter phone number.");
        String email = view.getInput("Enter email.");
        String password = view.getInput("Enter password.");
        
        StudentDao studentDao = new StudentDao();
        try {
            studentDao.createObjectFromDatabase();
        } catch (SQLException e) {
            view.printMsg("Database error in studentDao.createObjectFrom"
                          + "Database(). Operation aborted.");
        } 
        
        boolean studentNotExist = true;
        for (Student student : studentDao.getStudents()) {
            if (student.getFirstName().equals(firstName)
                    && student.getLastName().equals(lastName)
                    && student.getPhoneNumber().equals(phoneNumber)
                    && student.getEmail().equals(email)) {
                studentNotExist = false;
            }
        }

        if (studentNotExist) {
            Student newStudent = new Student(firstName, lastName, phoneNumber, 
                                             email, password, 0, 0);
            studentDao.getStudents().add(newStudent);
            try {
                studentDao.addObject(newStudent);
            } catch (SQLException e) {
                view.printMsg("Database error in studentDao.addObject(new"
                              + "Student). Operation aborted.");
            } 
        } else {
            view.printMsg("Student already exist. Aborted.");
        }
    }

    private void addQuest() {
        // String category = view.getInput("Enter the category.");
        view.printMsg("Not implemented yet, operation aborted.");
        // new Quest(--------);
    }

    private void addArtifact() {
        // String category = view.getInput("Enter the category.");
        view.printMsg("Not implemented yet, operation aborted.");
        // new Artifact(--------);
    }

    private void updateQuest() {
        view.printMsg("Not implemented yet, operation aborted.");
        // Integer menu = -1;
        // while (menu == -1) {
        //     // QuestDao questDao = new QuestDao();
        //     // view.showList(questDao.getQuests());
        //     view.printMsg("QuestDao class not finished, type from 0-4 range.");
        //     int size = 5;  // <-- tmp, to delete
        //     menu = validateOption(view.getInput("Choose quest."), size);
        // }
        // ToDo:
        // 1. show list
        // 3. update
    }

    private void updateArtifact() {
        ArtifactDao artifactDao = new ArtifactDao();
        ArrayList<Artifact> artifacts = artifactDao.getArtifacts();
        Integer menu = 0;
        while (menu == 0) {
            if (artifacts.size() == 0) {
                view.printMsg("Artifacts list empty, operation aborted.");
                return;
            }
            for (Artifact artifact : artifacts) {
                view.printNumbered(artifact.getId(), artifact.getName());
            }
            menu = validateOption(view.getInput("Choose quest."), 
                                  artifacts.size());
        }

        // ----------- code to refactoring:
        view.printMsg("Name = " + artifacts.get(menu-1).getName());
        if (view.getInput("Update name? (Y/N)").toLowerCase().equals("y")) {
            try {
                artifactDao.updateData(menu, "name", 
                    view.getInput("Enter new name:"));
            } catch (SQLException e) {
                view.printMsg("Database error in ArtifactDao.updateData(...)."
                + "Operation aborted.");
            }
        }
        view.printMsg("Category = " + artifacts.get(menu-1).getCategory());
        if (view.getInput("Update category? (Y/N)").toLowerCase().equals("y")) {
            try {
                artifactDao.updateData(menu, "category", 
                    view.getInput("Enter new category:"));
            } catch (SQLException e) {
                view.printMsg("Database error in ArtifactDao.updateData(...)."
                + "Operation aborted.");
            }
        }
        view.printMsg("Price = " + artifacts.get(menu-1).getPrice());
        if (view.getInput("Update price? (Y/N)").toLowerCase().equals("y")) {
            try {
                artifactDao.updateData(menu, "price", 
                    view.getInput("Enter new price:"));
            } catch (SQLException e) {
                view.printMsg("Database error in ArtifactDao.updateData(...)."
                + "Operation aborted.");
            }
        }
        // -------------- end of code to refactoring:
    }

    private void markQuest() {
        view.printMsg("Not implemented yet, operation aborted.");
        // TODO:
        // 1. show list
        // 2. get input
        // 3. mark
    }

    private void markArtifact() {
        view.printMsg("Not implemented yet, operation aborted.");
        // TODO:
        // 1. show list
        // 2. get input
        // 3. mark
    }

    private void seeWallet() {
        view.printMsg("Not implemented yet, operation aborted.");
        // TODO:
        // 1. show student list
        // 2. get input
        // 3. show wallet
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
