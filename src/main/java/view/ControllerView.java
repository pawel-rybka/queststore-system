package view;


public class ControllerView extends View {

    final private String MENU = "\nLogin as: " +
                                "\n1) Admin" +
                                "\n2) Mentor" +
                                "\n3) Student" +
                                "\n0) Quit";

    public ControllerView() {
    }


    public void printMenu() {
        System.out.println(MENU);
    }

}
