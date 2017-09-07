package dao;

import java.util.ArrayList;
import model.*;

public class StudentDao extends UserDao{
    String path = "./resources/students.csv";
    ArrayList<String> lines = readFromFile(path);
    ArrayList<Student> students = new ArrayList<>();

    public void creatObjectsFromList(){
        for (String line: lines){
            String[] objectParameters = line.split("[,]");
            Student student = new Student(objectParameters[0], objectParameters[1],
                                        objectParameters[2], objectParameters[3]);
            students.add(student);
        }
    }

    public ArrayList getAll(){
        return this.students;
    }


}
