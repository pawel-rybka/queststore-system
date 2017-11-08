package controller;

import java.lang.Exception;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;
import view.*;
import dao.*;

public class MentorController {

    private Mentor mentor;
    private MentorView mentorView;
    private ControllerView controllerView;
    
    public MentorController (Mentor mentor) {
        this.mentor = mentor;
        this.mentorView = new MentorView();

    }

    public void handleMenu() {
        String menu = "default";

        while (!menu.equals("0")) {
            mentorView.printMenu();
            menu = mentorView.getInput("Choose option.");

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
        String[] personalData = controllerView.getPersonalData();
        Student newStudent = new Student(personalData[0], personalData[1], personalData[2],
                                         personalData[3], personalData[4]);
        StudentDao studentDao = new StudentDao();
        try {
            studentDao.addObject(newStudent);
        } catch (SQLException e) {
            mentorView.printMsg("Database error,can't add a student.");
        } 
    }

    private void addQuest() {
        final String[] categories = {"Basic Quest", "Extra Quest"};
        QuestDao questDao = new QuestDao();
//        Integer expLevel = -1;
//
//        while (expLevel < 0) {
//            expLevel = validateExpLevel(mentorView.getInput("Enter experience level: "));
//        }
//        Quest newLevel = new Quest(mentorView.getInput("Enter level name: "), expLevel);
//        try {
//            levelDao.addObject(newLevel);
//        } catch (SQLException e) {
//            adminView.printMsg("Database error, can't add new level.");
//        }
    }

    private void addArtifact() {
        // String category = view.getInput("Enter the category.");
        mentorView.printMsg("Not implemented yet, operation aborted.");
        // new Artifact(--------);
    }

    private void updateQuest() {
        mentorView.printMsg("Not implemented yet, operation aborted.");
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
        ArrayList<Artifact> artifacts = null;

        try {
            artifacts = artifactDao.getArtifacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Integer menu = 0;
        while (menu == 0) {
            if (artifacts.size() == 0) {
                mentorView.printMsg("Artifacts list empty, operation aborted.");
                return;
            }
            for (Artifact artifact : artifacts) {
                mentorView.printNumbered(artifact.getId(), artifact.getName());
            }
            menu = validateOption(mentorView.getInput("Choose quest."),
                                  artifacts.size());
        }

    }

    private void markQuest() {
        mentorView.printMsg("Not implemented yet, operation aborted.");
        // TODO:
        // 1. show list
        // 2. get input
        // 3. mark
    }

    private void markArtifact() {
        mentorView.printMsg("Not implemented yet, operation aborted.");
        // TODO:
        // 1. show list
        // 2. get input
        // 3. mark
    }

    private void seeWallet() {
        mentorView.printMsg("Not implemented yet, operation aborted.");
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
            mentorView.printMsg("Wrong input.");
        }
        return number;
    }
}
