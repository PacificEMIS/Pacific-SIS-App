package com.pacificstudentteacher.model;

public class ReportCardModel {
    String courseSectionName,gradeObtained,staffName,comments,teacherComments;
    double gpa,percentMarks;
    boolean isExpanded;
    public String getCourseSectionName() {
        return courseSectionName;
    }

    public void setCourseSectionName(String courseSectionName) {
        this.courseSectionName = courseSectionName;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getGradeObtained() {
        return gradeObtained;
    }

    public void setGradeObtained(String gradeObtained) {
        this.gradeObtained = gradeObtained;
    }

    public Double getPercentMarks() {
        return percentMarks;
    }

    public void setPercentMarks(Double percentMarks) {
        this.percentMarks = percentMarks;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTeacherComments() {
        return teacherComments;
    }

    public void setTeacherComments(String teacherComments) {
        this.teacherComments = teacherComments;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
