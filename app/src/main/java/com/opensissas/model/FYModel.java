package com.opensissas.model;

public class FYModel {
    String semName,semMarks;
    int yearID,semID;

    public String getSemName() {
        return semName;
    }

    public void setSemName(String semName) {
        this.semName = semName;
    }

    public String getSemMarks() {
        return semMarks;
    }

    public void setSemMarks(String semMarks) {
        this.semMarks = semMarks;
    }

    public int getYearID() {
        return yearID;
    }

    public void setYearID(int yearID) {
        this.yearID = yearID;
    }

    public int getSemID() {
        return semID;
    }

    public void setSemID(int semID) {
        this.semID = semID;
    }
}
