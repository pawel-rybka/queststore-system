package dao;

import model.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class MentorDao {

    private ArrayList<Mentor> mentors = new ArrayList<Mentor>();
    private Connection c = null;
    private Statement stmt = null;

    public MentorDao(){
        c = Dao.getC();
    }



}
