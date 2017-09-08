package dao;

import java.util.ArrayList;
import model.*;

public class StudentDao extends AbstractDao<Student> {
    String path = "./resources/students.csv";
    ArrayList<String> lines = readFromFile(path);

    public void creatObjectsFromList(){
        for (String line: lines){
            String[] objectParameters = line.split("[,]");
            Student student = new Student(objectParameters[0], objectParameters[1],
                                        objectParameters[2], objectParameters[3]);
            addToList(student);
        }
    }   
}
