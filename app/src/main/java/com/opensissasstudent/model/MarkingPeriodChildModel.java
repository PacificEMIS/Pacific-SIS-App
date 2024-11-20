package com.opensissasstudent.model;

import java.util.ArrayList;

public class MarkingPeriodChildModel {
    String title,startDate,endDate,shortName,postEndDate,postStartDate;
    ArrayList<MarkingPeriodChildToChildModel>itemlist=new ArrayList<>();
    boolean doesComments,doesExam,doesGrades;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPostEndDate() {
        return postEndDate;
    }

    public void setPostEndDate(String postEndDate) {
        this.postEndDate = postEndDate;
    }

    public String getPostStartDate() {
        return postStartDate;
    }

    public void setPostStartDate(String postStartDate) {
        this.postStartDate = postStartDate;
    }

    public boolean isDoesComments() {
        return doesComments;
    }

    public void setDoesComments(boolean doesComments) {
        this.doesComments = doesComments;
    }

    public boolean isDoesExam() {
        return doesExam;
    }

    public void setDoesExam(boolean doesExam) {
        this.doesExam = doesExam;
    }

    public boolean isDoesGrades() {
        return doesGrades;
    }

    public void setDoesGrades(boolean doesGrades) {
        this.doesGrades = doesGrades;
    }

    public ArrayList<MarkingPeriodChildToChildModel> getItemlist() {
        return itemlist;
    }

    public void setItemlist(ArrayList<MarkingPeriodChildToChildModel> itemlist) {
        this.itemlist = itemlist;
    }
}
