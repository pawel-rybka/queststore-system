package dao;

import java.util.ArrayList;
import model.*;

public class MentorDao extends UserDao{
    String path = "./resources/mentors.csv";
    ArrayList<String> lines = readFromFile(path);
    ArrayList<Mentor> mentors = new ArrayList<>();

    public void creatObjectsFromList(){
        for (String line: lines){
            String[] objectParameters = line.split("[,]");
            Mentor mentor = new Mentor(objectParameters[0], objectParameters[1],
                                        objectParameters[2], objectParameters[3]);
            mentors.add(mentor);
        }
    }

    public ArrayList getAll(){
        return this.mentors;
    }


}
