package model;

import java.time.LocalDate;
public class CompletedQuest {

    private Integer id;
    private Integer studentId;
    private Integer questId;
    private LocalDate completeDate;

    public CompletedQuest(Integer id, Integer studentId, Integer questId, LocalDate completeDate) {
        this.id = id;
        this.studentId = studentId;
        this.questId = questId;
        this.completeDate = completeDate;
    }

    public CompletedQuest(Integer studentId, Integer questId, LocalDate completeDate) {
        this.id = null;
        this.studentId = studentId;
        this.questId = questId;
        this.completeDate = completeDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getQuestId() {
        return questId;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
