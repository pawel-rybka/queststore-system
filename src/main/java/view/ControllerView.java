package view;

import java.util.Scanner;

public class ControllerView extends View {

    private Scanner csan;

    public ControllerView() {
        Scanner csan = new Scanner(System.in);
    }

    final private String MENU = "\nLogin as: " +
                                "\n1) Admin" +
                                "\n2) Mentor" +
                                "\n3) Student" +
                                "\n0) Quit";


    public void printMenu() {
        System.out.println(MENU);
    }

}
