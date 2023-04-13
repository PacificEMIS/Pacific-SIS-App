package com.opensis.model;

public class TeacherGradeModel {
    String gradeName,gradeType,assignedDate,dueDate,points,studentList,gradeList;
    int graderID,assignmentTypeId;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public int getGraderID() {
        return graderID;
    }

    public void setGraderID(int graderID) {
        this.graderID = graderID;
    }

    public String getStudentList() {
        return studentList;
    }

    public void setStudentList(String studentList) {
        this.studentList = studentList;
    }

    public String getGradeList() {
        return gradeList;
    }

    public void setGradeList(String gradeList) {
        this.gradeList = gradeList;
    }

    public int getAssignmentTypeId() {
        return assignmentTypeId;
    }

    public void setAssignmentTypeId(int assignmentTypeId) {
        this.assignmentTypeId = assignmentTypeId;
    }
}
