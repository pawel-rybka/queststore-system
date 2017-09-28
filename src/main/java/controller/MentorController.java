package controller;

import model.*;
import view.*;
import dao.*;
import java.lang.Exception;

class MentorController {
    
    private View view;
    
    public MentorController (View view) {
        this.view = view;
    }

    public void handleMenu() {
        String menu = "default";

        while (!menu.equals("0")) {
            view.printMenu("mentor");
            menu = view.getInput("Choose option.");

            if (menu.equals("1")) {
                createStudent();
            } else if (menu.equals("2")) {
                addQuest();
            } else if (menu.equals("3")) {
                addArtifact();
            } else if (menu.equals("4")) {
                updateQuest();
            } else if (menu.equals("5")) {
                updateArtifact();
            } else if (menu.equals("6")) {
                markQuest();   
            } else if (menu.equals("7")) {
                markArtifact();
            } else if (menu.equals("8")) {
                seeWallet();
            } 
        }
    }

    private void createStudent() {
        String firstName = view.getInput("Enter first name.");
        String lastName = view.getInput("Enter last name.");
        String phoneNumber = view.getInput("Enter phone number.");
        String email = view.getInput("Enter email.");
        String password = view.getInput("Enter password.");
        Student newStudent = new Student(firstName, lastName, phoneNumber, 
                                         email, password, 0, 0);
        // ToDo: Add student to the database
    }

    private void addQuest() {
        String category = view.getInput("Enter the category.");
        view.printMsg("QuestModel class not finished, operation aborted.");
        // new Quest(category); ???
    }

    private void addArtifact() {
        String category = view.getInput("Enter the category.");
        view.printMsg("ArtifactModel class not finished, operation aborted.");
        // new Artifact(category); ???
    }

    private void updateQuest() {
        Integer menu = -1;
        while (menu == -1) {
            // QuestDao questDao = new QuestDao();
            // view.showList(questDao.getQuests());
            view.printMsg("QuestDao class not finished, type from 0-4 range.");
            int size = 5;  // <-- tmp, to delete
            menu = validateOption(view.getInput("Choose quest."), size);
        }
        view.printMsg("QuestModel class not finished, operation aborted.");
        // ToDo:
        // 1. show list
        // 3. update
    }

    private void updateArtifact() {
        Integer menu = -1;
        while (menu == -1) {
            // ArtifactDao artifactDao = new ArtifactDao();
            // view.showList(artifactDao.getArtifact());
            view.printMsg("ArtifactDao class not finished, type from 0-4 range.");
            int size = 5;  // <-- tmp, to delete
            menu = validateOption(view.getInput("Choose quest."), size);
        }
        view.printMsg("ArtifactDao class not finished, operation aborted.");
        // ToDo:
        // 1. show list
        // 3. update
    }

    private void markQuest() {
        // TODO:
        // 1. show list
        // 2. get input
        // 3. mark
    }

    private void markArtifact() {
        // TODO:
        // 1. show list
        // 2. get input
        // 3. mark
    }

    private void seeWallet() {
        // TODO:
        // 1. show student list
        // 2. get input
        // 3. show wallet
    }

    private Integer validateOption(String text, int size) {
        Integer number = -1;
        try {
            number = Integer.parseInt(text);
            if (number < 0 || number >= size) {
                number = -1;
                throw new Exception("s");
            }
        } catch (Exception e) {
            view.printMsg("Wrong input.");
        }
        return number;
    }
}
