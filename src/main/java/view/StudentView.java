package view;

public class StudentView extends View {

    final private String MENU = "\nSTUDENT MENU: " +
                                "\n1) Check balance" +
                                "\n2) Buy Artifact" +
                                "\n3) Donate for artifact" +
                                "\n4) See my profile" +
                                "\n0) Quit";

    public StudentView() {

    }

    public void printMenu() {
        System.out.println(MENU);
    }
}
