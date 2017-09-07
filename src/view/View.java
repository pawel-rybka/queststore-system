
package view;

public class View {

    String admin = "1) create Mentor account\n";

    public void showMenu(String owner) {
        String display = "\nChoose option:\n0) exit menu\n";

        if (owner.equals("admin")) display += admin;

    }


}