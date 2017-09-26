package dao;

import model.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminDao {

    private ArrayList<Admin> admins = new ArrayList<Admin>();
    private Connection c = null;
    private Statement stmt = null;

    public AdminDao(){
        c = Dao.getC();
    }



}