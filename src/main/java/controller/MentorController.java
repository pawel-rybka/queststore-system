package controller;

import model.*;
import view.*;

class MentorController {
    
    private View view;
    
    public MentorController (View view) {
        this.view = view;
    }

    public void handleMenu() {
        view.printMenu("mentor");
        String menu = "default";

        while (!menu.equals("0")) {
            if (menu.equals("1")) {

            } else if (menu.equals("1")) {
                createStudent();
            } else if (menu.equals("2")) {
                addQuest();
            } else if (menu.equals("3")) {
                splitQuests();  // <-- to remove/replace
            } else if (menu.equals("4")) {
                addArtifact();
            } else if (menu.equals("5")) {
                update();   // <-- split to update artifact & update quest
            } else if (menu.equals("6")) {
                splitArtifacts();   // <-- to remove/replace
            } else if (menu.equals("7")) {
                markQuest();
            } else if (menu.equals("8")) {
                markArtifact();
            } else if (menu.equals("9")) {
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
        new Student(firstName, lastName, phoneNumber, email, password);
    }

    public void addQuest() {
        String category = view.getInput("Enter the category.");
        new Quest(category);
    }

    public void splitQuests() {
        // this method and option is probably unnecesery
    }

    public void addArtifact() {
        String category = view.getInput("Enter the category.");
        new Artifact(category);
    }

    public void update() {
        // split this method to update artifact & update quest
        // TODO:
        // 1. show list
        // 2. get input
        // 3. update
    }

    public void splitArtifacts() {
        // this method and option is probably unnecesery
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
