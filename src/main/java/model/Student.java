package model;

import dao.LevelDao;

import java.sql.SQLException;

public class Student extends User {

    private Integer coins;
    private Integer totalCoins;
    private String level;



    public Student(Integer id, String firstName, String lastName, String phoneNumber, String email,
                   String password, Integer coins, Integer totalCoins) {
        super(id, firstName, lastName, phoneNumber, email, password);
        this.coins = coins;
        this.totalCoins = totalCoins;
        this.level = setLevel(coins);
    }

    public Student(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.coins = 0;
        this.totalCoins = 0;
        this.level = setLevel(coins);
    }


    public Integer getCoins() {
        return coins;
    }

    public Integer getTotalCoins() {
        return totalCoins;
    }

    public String getLevel() {
        return level;
    }


    public void setCoins(Integer firstName) {
        this.coins = coins;
        this.checkLevel();
    }


    public void setTotalCoins(Integer totalCoins) {
        this.totalCoins = totalCoins;
    }


    private String setLevel(Integer coins) {

        Level actualLevel = null;

        LevelDao lvlDao = new LevelDao();
        try {
            actualLevel = lvlDao.getLevelByCoinAmount(coins);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualLevel.getName();
    }


    public void checkLevel() {
        this.level = setLevel(this.coins);
    }


}
