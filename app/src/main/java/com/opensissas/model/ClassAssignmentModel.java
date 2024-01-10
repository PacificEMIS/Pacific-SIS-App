package com.opensissas.model;

public class ClassAssignmentModel {
    String assignmentName,assignmentDetails,weightage;
    int assignmentTypeId,courseSectionId;

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDetails() {
        return assignmentDetails;
    }

    public void setAssignmentDetails(String assignmentDetails) {
        this.assignmentDetails = assignmentDetails;
    }

    public int getAssignmentTypeId() {
        return assignmentTypeId;
    }

    public void setAssignmentTypeId(int assignmentTypeId) {
        this.assignmentTypeId = assignmentTypeId;
    }

    public int getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(int courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public String getWeightage() {
        return weightage;
    }

    public void setWeightage(String weightage) {
        this.weightage = weightage;
    }
}
