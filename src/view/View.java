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

    public void printMenu(String msg) {
        if (msg.equals("Log")){
            System.out.println("LOG IN AS: \n" + "1) Admin \n" + "2) Mentor \n" + "3) Student \n" + "0) Exit");
        }
        else if (msg.equals("admin")){
            System.out.println(ADMIN);
        }
        else if (msg.equals("mentor")){
            System.out.println(MENTOR);
        }
        else if (msg.equals("student")){
            System.out.println(STUDENT);
        }
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