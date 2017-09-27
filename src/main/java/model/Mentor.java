package model;

public class Mentor extends User {

    public Mentor(Integer id, String firstName, String lastName, String phoneNumber, String email, String password) {
        super(id, firstName, lastName, phoneNumber, email, password);
    }

    public Mentor(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
    }
}