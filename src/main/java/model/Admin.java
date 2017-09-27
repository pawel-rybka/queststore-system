package model;

public class Admin extends User {

    public Admin(Integer id, String firstName, String lastName, String phoneNumber, String email, String password) {
        super(id, firstName, lastName, phoneNumber, email, password);
    }

    public Admin(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
    }
}
