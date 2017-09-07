package dao;

import java.util.ArrayList;
import model.*;

public class StudentDao extends UserDao<Student> {
    String path = "./resources/students.csv";
    ArrayList<String> lines = readFromFile(path);
    // ArrayList<Student> students = new ArrayList<>();

    public void creatObjectsFromList(){
        for (String line: lines){
            String[] objectParameters = line.split("[,]");
            Student student = new Student(objectParameters[0], objectParameters[1],
                                        objectParameters[2], objectParameters[3]);
            addToList(student);
        }
    }

    // public ArrayList<Student> getAll(){
    //     // System.out.println(this.objects);
    //     return this.objects;
    // }


}
