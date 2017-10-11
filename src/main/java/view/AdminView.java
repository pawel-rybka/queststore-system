package view;

import model.*;

public class AdminView extends View {

    final private String MENU = "\nADMIN MENU: " +
            "\n1) Create Mentor account" +
            "\n2) See Mentor's profile" +
            "\n3) Edit Mentor's profile" +
            "\n4) Create Class" +
            "\n5) Create Level" +
            "\n0) Quit";

    public AdminView() {

    }

    public void printMenu() {
        System.out.println(MENU);
    }

    public void printPersonalData(Mentor mentor) {
        printMsg("Name: " + mentor.getFullName());
        printMsg("Phone: " + mentor.getPhoneNumber());
        printMsg("E-mail: " + mentor.getEmail());
        printMsg("Password: " + mentor.getPassword());

    }
}
