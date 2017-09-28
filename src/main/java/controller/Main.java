package controller;

import view.View;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       Main main = new Main();
       main.test();
    }

//    private void test() {
//
//        MentorController mc = new MentorController(new View(stdin));
//        mc.handleMenu();
//    }
    private void test() {
       
        MentorController mc = new MentorController(
                            new View(new Scanner(System.in)));
        mc.handleMenu();
    }
}
