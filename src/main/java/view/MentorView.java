package view;

public class MentorView extends View {

    final private String MENU = "\nSTUDENT MENU: " +
            "\n1) Create Codecooler account" +
            "\n2) Add Quest" +
            "\n3) Add Artifact" +
            "\n4) Update Quest" +
            "\n5) Update Artifact" +
            "\n6) Mark Achieved Quest" +
            "\n7) Mark Bought Artifact" +
            "\n8) See codecoller's Wallet" +
            "\n0) Quit";

    public MentorView() {

    }

    public void printMenu() {
        System.out.println(MENU);
    }
}