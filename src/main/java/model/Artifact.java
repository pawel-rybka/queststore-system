package model;

public class Artifact implements GetIdable{

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

    public Artifact(String name, String category, Integer price) {
        this.id = null;
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public String toString() {
        return this.category + " | " + this.name + " | " + this.price + "$";
    }
}