package controller;

import model.*;
import view.*;

class MentorController {
    
    private View view;
    
    public MentorController (View view) {
        this.view = view;
    }

    public void handleMenu() {
        view.printMenu("mentor");


    }


    // public Student createStudent() {
    //     Student student = new Student("100","kamil","mail66","haslo");
    //     return student;
    // }

 
}
