package application;

import java.util.Iterator;
import java.util.Scanner;

import model.User;
import dao.MentorDao;
import view.View;

public class Application {

    private User loggedUser;
    private Scanner stdin;
    private View view;

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    Application () {
        this.loggedUser = null;
        this.stdin = new Scanner(System.in);
        this.view = new View(stdin);
    }

    public void run() {

//        this.loggedUser = login();

        if (loggedUser != null) {
            this.view.printMenu("mentor");
        }
        stdin.close();
    }

    private void login() {
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
    }
}
