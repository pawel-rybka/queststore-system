package model;

public class Level implements GetIdable {


    private Integer id;
    private String name;
    private Integer expLevel;

    public Level(Integer id, String name, Integer expLevel) {
        this.id = id;
        this.name = name;
        this.expLevel = expLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(Integer expLevel) {
        this.expLevel = expLevel;
    }
}
