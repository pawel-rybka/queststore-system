package model;

public class Student extends User {

    private Wallet wallet;
    private String level;


    public Student(String id, String name, String email, String password){
        super(id, name, email, password);
    }

    public Level calculateLevel() {
        Level level = null;

        // code here

        return level;

    }

}
