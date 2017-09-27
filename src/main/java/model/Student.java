package model;

public class Student extends User {

    private Integer coins;
    private Integer totalCoins;
    private String level;


    public Student(Integer id, String firstName, String lastName, String phoneNumber, String email,
                   String password, Integer coins, Integer totalCoins, String level) {
        super(id, firstName, lastName, phoneNumber, email, password);
        this.coins = coins;
        this.totalCoins = totalCoins;
        this.level = level;
    }

    public Level calculateLevel() {
        Level level = null;

        // code here

        return level;

    }

}
