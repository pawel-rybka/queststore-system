package model;

public class Klass implements GetIdable {

    private Integer id;
    private String name;

    public Klass(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Klass(String name) {
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
