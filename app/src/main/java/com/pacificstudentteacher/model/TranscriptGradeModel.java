package com.pacificstudentteacher.model;

public class TranscriptGradeModel {
    String gradeName;
    int gradeID;
    private boolean isSelected = false;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
