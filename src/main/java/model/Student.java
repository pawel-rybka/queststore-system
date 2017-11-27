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
        this.level = this.setLevel(this.totalCoins);
    }

    public Student(String firstName, String lastName, String phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.coins = 0;
        this.totalCoins = 0;
        this.level = this.setLevel(this.totalCoins);
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


    public void setCoins(Integer coins) {
        this.coins = coins;
        this.checkLevel();
    }


    public void setTotalCoins(Integer totalCoins) {
        this.totalCoins = totalCoins;
        this.setLevel(this.totalCoins);
    }


    public String setLevel(Integer coins) {
        LevelDao lvlDao = new LevelDao();
        try {
            String level = lvlDao.getLevelByCoinAmount(coins);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }


    public void checkLevel() {
        this.level = setLevel(this.totalCoins);
    }

}
