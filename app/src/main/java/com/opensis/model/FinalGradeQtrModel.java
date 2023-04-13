package com.opensis.model;

public class FinalGradeQtrModel {
    String gradeName,gradeTotalMarks,gradeMarks;
    int markingPeriodId,qtrUpdateID;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeTotalMarks() {
        return gradeTotalMarks;
    }

    public void setGradeTotalMarks(String gradeTotalMarks) {
        this.gradeTotalMarks = gradeTotalMarks;
    }

    public String getGradeMarks() {
        return gradeMarks;
    }

    public void setGradeMarks(String gradeMarks) {
        this.gradeMarks = gradeMarks;
    }

    public int getMarkingPeriodId() {
        return markingPeriodId;
    }

    public void setMarkingPeriodId(int markingPeriodId) {
        this.markingPeriodId = markingPeriodId;
    }

    public int getQtrUpdateID() {
        return qtrUpdateID;
    }

    public void setQtrUpdateID(int qtrUpdateID) {
        this.qtrUpdateID = qtrUpdateID;
    }
}
