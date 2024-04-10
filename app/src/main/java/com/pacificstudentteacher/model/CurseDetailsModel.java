package com.pacificstudentteacher.model;

import java.util.ArrayList;

public class CurseDetailsModel {
    String courseSectionName,meetingDays,scheduleType,markingPeriod,staffName,periodTitle,time,room;
    private ArrayList<CourseVeriableScheduleModel> variablescheduleList;

    public String getCourseSectionName() {
        return courseSectionName;
    }

    public void setCourseSectionName(String courseSectionName) {
        this.courseSectionName = courseSectionName;
    }

    public String getMeetingDays() {
        return meetingDays;
    }

    public void setMeetingDays(String meetingDays) {
        this.meetingDays = meetingDays;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getMarkingPeriod() {
        return markingPeriod;
    }

    public void setMarkingPeriod(String markingPeriod) {
        this.markingPeriod = markingPeriod;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPeriodTitle() {
        return periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<CourseVeriableScheduleModel> getVariablescheduleList() {
        return variablescheduleList;
    }

    public void setVariablescheduleList(ArrayList<CourseVeriableScheduleModel> variablescheduleList) {
        this.variablescheduleList = variablescheduleList;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
