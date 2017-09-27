package controller;

import model.*;
import view.*;

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

    public void createStudent() {
        String firstName = view.getInput("Enter first name.");
        String lastName = view.getInput("Enter last name.");
        String phoneNumber = view.getInput("Enter phone number.");
        String email = view.getInput("Enter email.");
        String password = view.getInput("Enter password.");
        // new Student(firstName, lastName, phoneNumber, email, password);
    }

    public void addQuest() {
        String category = view.getInput("Enter the category.");
        // new Quest(category);
    }

    public void addArtifact() {
        String category = view.getInput("Enter the category.");
        // new Artifact(category);
    }

    public void updateQuest() {
        // split this method to update artifact & update quest
        // TODO:
        // 1. show list
        // 2. get input
        // 3. update
    }

    public void updateArtifact() {
        // split this method to update artifact & update quest
        // TODO:
        // 1. show list
        // 2. get input
        // 3. update
    }

    public void markQuest() {
        // TODO:
        // 1. show list
        // 2. get input
        // 3. mark
    }

    public void markArtifact() {
        // TODO:
        // 1. show list
        // 2. get input
        // 3. mark
    }

    public void seeWallet() {
        // TODO:
        // 1. show student list
        // 2. get input
        // 3. show wallet
    }
}
