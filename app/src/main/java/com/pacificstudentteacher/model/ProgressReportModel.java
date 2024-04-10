package com.pacificstudentteacher.model;

import java.util.ArrayList;

public class ProgressReportModel {
    String course,courseSection,teacher,runningAvg,totalGrade,totlWeightgrade;
    ArrayList<ProgressReportChildModel>itemList=new ArrayList<>();
    int totalpoints,totalassignpoints,size;
    boolean isExpanded;


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public ArrayList<ProgressReportChildModel> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<ProgressReportChildModel> itemList) {
        this.itemList = itemList;
    }


    public int getTotalpoints() {
        return totalpoints;
    }

    public void setTotalpoints(int totalpoints) {
        this.totalpoints = totalpoints;
    }

    public String getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(String totalGrade) {
        this.totalGrade = totalGrade;
    }

    public String getTotlWeightgrade() {
        return totlWeightgrade;
    }

    public void setTotlWeightgrade(String totlWeightgrade) {
        this.totlWeightgrade = totlWeightgrade;
    }

    public int getTotalassignpoints() {
        return totalassignpoints;
    }

    public void setTotalassignpoints(int totalassignpoints) {
        this.totalassignpoints = totalassignpoints;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getRunningAvg() {
        return runningAvg;
    }

    public void setRunningAvg(String runningAvg) {
        this.runningAvg = runningAvg;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
