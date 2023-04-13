package com.opensis.model;

public class AssignmentDetailsModel {
    String assignmentdetailsName,points,assignedDate,dueDate;
    int assignmentId;

    public String getAssignmentdetailsName() {
        return assignmentdetailsName;
    }

    public void setAssignmentdetailsName(String assignmentdetailsName) {
        this.assignmentdetailsName = assignmentdetailsName;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
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

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }
}
