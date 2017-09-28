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
            view.printMenu("mentor");
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


}