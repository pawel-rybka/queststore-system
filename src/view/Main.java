package view;

import java.util.Scanner;
public class Main {

    private View view = new View(new Scanner(System.in));

    public static void main(String[] args) {
        
        Main test = new Main();
        test.testMenus();
        test.testInputs();

        
    }

    private void testMenus() {
        view.showMenu("admin");
        view.showMenu("mentor");
        view.showMenu("student");

        System.out.println();
    }

    private void testInputs() {
        System.out.print("Type login: ");
        // String login = view.getString();
        // System.out.println("Your login: " + login);

        System.out.println();
    }
}