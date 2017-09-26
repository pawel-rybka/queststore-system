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
        view.printMenu("admin");
        view.printMenu("mentor");
        view.printMenu("student");

        System.out.println();
    }

    private void testInputs() {
        System.out.print("Type login: ");


        System.out.println();
    }
}