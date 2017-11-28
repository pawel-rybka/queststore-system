package model;

public class Quest implements GetIdable{

    private Integer id;
    private String name;
    private String category;
    private Integer value;


    public Quest(Integer id, String name, String category, Integer value) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.value = value;
    }

    public Quest(String name, String category, Integer value) {
        this.name = name;
        this.category = category;
        this.value = value;
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

    public Integer getValue() {
        return value;
    }


    public void setId(Integer id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}