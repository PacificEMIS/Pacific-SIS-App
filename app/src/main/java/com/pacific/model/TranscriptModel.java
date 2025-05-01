package com.pacific.model;

import java.util.ArrayList;

public class TranscriptModel {

    String gradeLevelTitle,schoolName,schoolYear;
    ArrayList<Transcriptmarkingperiodmodel>periodList;

    public String getGradeLevelTitle() {
        return gradeLevelTitle;
    }

    public void setGradeLevelTitle(String gradeLevelTitle) {
        this.gradeLevelTitle = gradeLevelTitle;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public ArrayList<Transcriptmarkingperiodmodel> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(ArrayList<Transcriptmarkingperiodmodel> periodList) {
        this.periodList = periodList;
    }
}
