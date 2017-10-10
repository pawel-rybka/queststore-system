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

    public String[] getPersonalData() {
        String firstName = getInput("Enter first name. ");
        String lastName = getInput("Enter last name: ");
        String phoneNumber = getInput("Enter phone number: ");
        String email = getInput("Enter email: ");
        String password = getInput("Enter password: ");

        return new String[]{firstName, lastName, email, phoneNumber, password};
    }

}
