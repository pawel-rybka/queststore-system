package view;

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
}
