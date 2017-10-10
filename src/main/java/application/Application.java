package application;



import controller.*;

public class Application {

    Controller controller;

    Application () {
        this.controller = new Controller();

    }

    private void run(){
        this.controller.loginMenu();
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
