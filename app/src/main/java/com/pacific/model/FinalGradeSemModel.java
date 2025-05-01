package com.pacific.model;

import java.util.ArrayList;

public class FinalGradeSemModel {
    String semName,qtrName,semMarks,qtrMarks;
    int semID,qtrID,semoUpdateID;
    ArrayList<GradeBokConfigSemQtrModel> qtrList=new ArrayList<>();
    public String getSemName() {
        return semName;
    }

    public void setSemName(String semName) {
        this.semName = semName;
    }

    public String getQtrName() {
        return qtrName;
    }

    public void setQtrName(String qtrName) {
        this.qtrName = qtrName;
    }

    public String getSemMarks() {
        return semMarks;
    }

    public void setSemMarks(String semMarks) {
        this.semMarks = semMarks;
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

    public int getQtrID() {
        return qtrID;
    }

    public void setQtrID(int qtrID) {
        this.qtrID = qtrID;
    }

    public int getSemoUpdateID() {
        return semoUpdateID;
    }

    public void setSemoUpdateID(int semoUpdateID) {
        this.semoUpdateID = semoUpdateID;
    }

    public ArrayList<GradeBokConfigSemQtrModel> getQtrList() {
        return qtrList;
    }

    public void setQtrList(ArrayList<GradeBokConfigSemQtrModel> qtrList) {
        this.qtrList = qtrList;
    }
}
