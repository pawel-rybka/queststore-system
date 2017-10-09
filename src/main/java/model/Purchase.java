package model;

public class Purchase {
    private Integer id;
    private Integer studentId;
    private Integer artifactId;

    public Purchase(Integer id, Integer studentId, Integer artifactId) {
        this.id = id;
        this.studentId = studentId;
        this.artifactId = artifactId;
    }

    public Purchase(Integer studentId, Integer artifactId) {
        this.studentId = studentId;
        this.artifactId = artifactId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getArtifactId() {
        return artifactId;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }
}


