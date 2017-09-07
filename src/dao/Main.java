package dao;

import model.*;

public class Main{

    public static void main(String[] args){
        StudentDao studentDao = new StudentDao();
        studentDao.creatObjectsFromList();
        Student student1 = new Student("100","kamil","mail66","haslo");
        studentDao.addToList(student1);
        for (Student student : studentDao.students){
            System.out.println(student.getName());
        }

        studentDao.removeFromList(student1);

        for (Student student : studentDao.students){
            System.out.println(student.getName());
        }

    }
}
