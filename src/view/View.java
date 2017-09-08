package view;

import java.util.Scanner;
public class View {

    final private String ADMIN = "\n1) create Mentor account\n"
            + "2) create Class\n3) edit Mentor's profile\n"
            + "4) see Mentor's profile\n5) create Level";

    final private String MENTOR = "\n1) create Codecooler account\n2) Add Quest\n"
            + "3) split Quests\n4) add Artifact\n5) update Quest/Artifact\n"
            + "6) split Artifacts\n7) mark Achieved Quest\n"
            + "8) mark Bought Artifact\n9) see codecoller's Wallet";

    final private String STUDENT = "\n1) see Wallet\n2) buy Artifact\n"
            + "3) group buying\n4) see Level";

    private Scanner stdin;

    public View(Scanner stdin) {
        this.stdin = stdin;
    }
    public void showMenu(String owner) {
        String menu = "\nChoose option:";

        if (owner.equals("admin")) menu += ADMIN;    
        else if (owner.equals("mentor")) menu += MENTOR;
        else if (owner.equals("student")) menu += STUDENT;
        menu += "\n0) exit menu\n";
        System.out.println(menu);
    }

    public String getUserLogin() {
        System.out.print("Login: ");
        return this.stdin.nextLine();
    }

    public String getUserPassword() {
        System.out.print("Password: ");
        return this.stdin.nextLine();
    }


}