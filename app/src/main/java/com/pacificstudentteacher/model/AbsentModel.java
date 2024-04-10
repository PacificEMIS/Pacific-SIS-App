package com.pacificstudentteacher.model;

import java.util.ArrayList;

public class AbsentModel {
    String absenceDate,attendance,attendanceshortCode,adminComment,teacherComment;
    ArrayList<AbsentSummaryChildModel>itemList=new ArrayList<>();
    boolean isExpanded;
    public String getAbsenceDate() {
        return absenceDate;
    }

    public void setAbsenceDate(String absenceDate) {
        this.absenceDate = absenceDate;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getAttendanceshortCode() {
        return attendanceshortCode;
    }

    public void setAttendanceshortCode(String attendanceshortCode) {
        this.attendanceshortCode = attendanceshortCode;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public ArrayList<AbsentSummaryChildModel> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<AbsentSummaryChildModel> itemList) {
        this.itemList = itemList;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
