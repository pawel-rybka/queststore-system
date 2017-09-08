package dao;

import java.util.ArrayList;
import model.*;

public class MentorDao extends AbstractDao<Mentor> {
    String path = "./resources/mentors.csv";
    ArrayList<String> lines = readFromFile(path);

    public void createObjectsFromList(){
        for (String line: lines){
            String[] objectParameters = line.split("[,]");
            Mentor mentor = new Mentor(objectParameters[0], objectParameters[1],
                                        objectParameters[2], objectParameters[3]);
            addToList(mentor);
        }
    }
}
