package model;


public class BoughtArtifact implements GetIdable{

    private Integer id;
    private Integer studentId;
    private Integer artifactId;
    private String usageDate;

    public BoughtArtifact(Integer id, Integer studentId, Integer artifactId, String usageDate) {
        this.id = id;
        this.studentId = studentId;
        this.artifactId = artifactId;
        this.usageDate = usageDate;
    }

    public BoughtArtifact(Integer studentId, Integer artifactId, String usageDate) {
        this.id = null;
        this.studentId = studentId;
        this.artifactId = artifactId;
        this.usageDate = usageDate;
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

    public String getUsageDate() {
        return usageDate;
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

    public void setUsageDate(String usageDate) {
        this.usageDate = usageDate;
    }
}