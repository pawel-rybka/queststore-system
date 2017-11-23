package application;



import controller.*;

public class AppConsole {

    Controller controller;

    AppConsole() {
        this.controller = new Controller();

    }

    private void run(){
        this.controller.loginMenu();
    }

    public static void main(String[] args) {
        AppConsole app = new AppConsole();
        app.run();
    }
}
