package application;

import java.util.Iterator;
import java.util.Scanner;
import controller.Controller;
import model.Mentor;
import model.Student;
import model.User;
import dao.UserDao;
import dao.MentorDao;
import view.View;

public class Application {

    private User loggedUser;
    private UserDao userDao;
    private Controller controller;
    private Scanner stdin;
    // private View view;

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    Application () {
        this.loggedUser = null;
        this.controller = new Controller();
        this.stdin = new Scanner(stdin);
        // this.view = new View();
    }

    public void run() {
        // Scanner stdin = new Scanner(System.in);
        View view = new View();
        this.userDao = new MentorDao();
        ((MentorDao)userDao).createObjectsFromList();

        while (this.loggedUser == null) {
            this.loggedUser = login(view);
        }

        stdin.close();
    }

    private User login(View view) {
        String userEmail = view.getUserLogin(this.stdin);
        String userPassword = view.getUserPassword(this.stdin);
        Iterator iterator = userDao.getIterator();
        User user = null;
        User foundUser = null;

        while (iterator.hasNext()) {
            user = (User)iterator.next();
            if (user.getEmail().equals(userEmail) && user.getPassword().equals(userPassword)) {
                foundUser = user; 
            }
        }

        return foundUser;
    }
}