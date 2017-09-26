package dao;

import model.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDao {

    private ArrayList<Student> students = new ArrayList<Student>();
    private Connection c = null;
    private Statement stmt = null;

    public StudentDao(){
        c = Dao.getC();
    }



}