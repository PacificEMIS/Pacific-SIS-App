package com.pacific.model;

public class GradeBokConfigSemQtrModel {
    String title,qtrMarks;
    int markingPeriodId,semID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMarkingPeriodId() {
        return markingPeriodId;
    }

    public void setMarkingPeriodId(int markingPeriodId) {
        this.markingPeriodId = markingPeriodId;
    }

    public String getQtrMarks() {
        return qtrMarks;
    }

    public void setQtrMarks(String qtrMarks) {
        this.qtrMarks = qtrMarks;
    }

    public int getSemID() {
        return semID;
    }

    public void setSemID(int semID) {
        this.semID = semID;
    }
}
