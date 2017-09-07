
package view;

import java.util.Scanner;
public class View {

    final private String ADMIN = "1) create Mentor account\n"
            + "2) create Class\n3) edit Mentor's profile\n"
            + "4) see Mentor's profile\n5) create Level";

    final private String MENTOR = "1) create Codecooler account\n2) Add Quest\n"
            + "3) split Quests\n4) add Artifact\n5) update Quest/Artifact\n"
            + "6) split Artifacts\n7) mark Achieved Quest\n"
            + "8) mark Bought Artifact\n9) see codecoller's Wallet";

    final private String STUDENT = "1) see Wallet\n2) buy Artifact\n"
            + "3) group buying\n4) see Level";

    public void showMenu(String owner) {
        String menu = "\nChoose option:\n0) exit menu\n";

        if (owner.equals("admin")) menu += ADMIN;    
        else if (owner.equals("mentor")) menu += MENTOR;
        else if (owner.equals("student")) menu += STUDENT;

        System.out.println(menu);
    }

    public String getString() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }



}