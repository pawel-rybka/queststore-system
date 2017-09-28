package model;

public class Artifact {

    Integer id;
    String category;
    Integer price;

    public Artifact(Integer id, String category, Integer price) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }
}