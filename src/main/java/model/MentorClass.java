package model;

public class MentorClass {

    private Integer mentorId;
    private Integer classId;

    public MentorClass(Integer mentorId, Integer classId) {
        this.mentorId = mentorId;
        this.classId = classId;
    }

    public Integer getMentorId() {
        return mentorId;
    }

    public Integer getClassId() {
        return classId;
    }
}