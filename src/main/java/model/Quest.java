package model;

public class Quest {

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}