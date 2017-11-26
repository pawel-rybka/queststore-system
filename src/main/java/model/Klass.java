package model;

public class Class implements GetIdable {

    private Integer id;
    private String name;

    public Class(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Class(String name) {
        this.name = name;
    }

    public String getClassName() {
        return this.name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
