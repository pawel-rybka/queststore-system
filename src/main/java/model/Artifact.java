package model;

public class Artifact {

    Integer id;
    String name;
    String category;
    Integer price;

    public Artifact(Integer id, String name, String category, Integer price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Artifact(String category, Integer price) {
        this.id = null;
        this.category = category;
        this.price = price;
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

    public Integer getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}