package model;

public class Quest implements GetIdable{

    private Integer id;
    private String name;
    private String category;

    public Quest(Integer id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Quest(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}