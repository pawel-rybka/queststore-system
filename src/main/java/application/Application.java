package application;

import java.util.Scanner;

import model.User;
import view.View;
import controller.*;

public class Application {

    private User loggedUser;
    private Scanner stdin;
    private View view;

    Application () {
        this.loggedUser = null;
        this.stdin = new Scanner(System.in);
        this.view = new View(stdin);
    }

    public static void main(String[] args) {
        Application app = new Application();
        // app.run();
        app.runDemo();
    }

    private void runDemo() {
        String menu = "default";
        while (!menu.equals("0")) {
            view.printMsg("\n0) exit\n1) Admin\n2) Mentor\n"
                          + "3) Student (preview menu only)");
            menu = view.getInput("Choose option.");

            if (menu.equals("1")) {
                login(); // fake login
                AdminController ac = new AdminController(new View(new 
                                                         Scanner(System.in)));
                ac.handleMenu();
            } else if (menu.equals("2")) {
                login(); // fake login
                MentorController mc = new MentorController(new View(new 
                                                           Scanner(System.in)));
                mc.handleMenu();
            } else if (menu.equals("3")) {
                login(); // fake login
                view.printMenu("student");
                view.printMsg("Student's options not available in demo "
                              +"version. Return to main menu.");
            }
        }
    }

    public void run() {

        this.loggedUser = login();

        if (loggedUser != null) {
            this.view.printMenu("mentor");
        }
        stdin.close();
    }

//        String userEmail = this.view.getUserLogin();
//        String userPassword = this.view.getUserPassword();
//        User user = null;
//        User foundUser = null;
//
//        while (iterator.hasNext()) {
//            user = (User)iterator.next();
//            if (user.getEmail().equals(userEmail) && user.getPassword().equals(userPassword)) {
//                foundUser = user;
//            }
//        }
//
//        return foundUser;
    private User login() {
        String userEmail = this.view.getUserLogin();
        String userPassword = this.view.getUserPassword();
        // Iterator iterator = this.dao.getIterator();
        User user = null;
        User foundUser = null;

        // while (iterator.hasNext()) {
        //     user = (User)iterator.next();
        //     if (user.getEmail().equals(userEmail) && user.getPassword().equals(userPassword)) {
        //         foundUser = user;
        //     }
        // }

        return foundUser;
    }
}
