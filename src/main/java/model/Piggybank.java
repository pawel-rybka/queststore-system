package model;


public class Piggybank {

    Integer id;
    Integer artifactId;
    Integer counter;

    public Piggybank(Integer id, Integer artifactId, Integer counter) {
        this.id = id;
        this.artifactId = artifactId;
        this.counter = counter;
    }

    public Piggybank(Integer artifactId, Integer counter) {
        this.artifactId = artifactId;
        this.counter = counter;
    }

    public Integer getId() {
        return id;
    }

    public Integer getArtifactId() {
        return artifactId;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}