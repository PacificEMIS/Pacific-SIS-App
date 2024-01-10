package com.opensissas.model;

public class MArkinPeriodSpinnerModel {
    String periodName,startDate,endDate;
    int periodID;

    public MArkinPeriodSpinnerModel(String periodName, String startDate, String endDate, int periodID) {
        this.periodName = periodName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodID = periodID;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
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

    public int getPeriodID() {
        return periodID;
    }

    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }
}
