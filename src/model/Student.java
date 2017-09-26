package model;

public class Student extends User {

    private Wallet wallet;
    private String level;


    public Student(String id, String firstName, String lastName, String phoneNumber, String email, String password, Wallet wallet, String level) {
        super(id, firstName, lastName, phoneNumber, email, password);
        this.wallet = wallet;
        this.level = level;
    }

    public Level calculateLevel() {
        Level level = null;

        // code here

        return level;

    }

}
