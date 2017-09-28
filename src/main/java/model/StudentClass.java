package model;

public class StudentClass {
    private Integer studentId;
    private Integer classId;

    public StudentClass(Integer studentId, Integer classId) {
        this.studentId = studentId;
        this.classId = classId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getClassId() {
        return classId;
    }
}
