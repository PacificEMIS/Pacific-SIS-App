package com.pacific.model;

public class MedicalNourseVisitModel {
    String reason,nurseVisitDate,comment,timeIn,timeOut,result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNurseVisitDate() {
        return nurseVisitDate;
    }

    public void setNurseVisitDate(String nurseVisitDate) {
        this.nurseVisitDate = nurseVisitDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
