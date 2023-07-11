package com.opensis.model;

public class MissingAttendanceModel {
    String courseSectionName,courseSection,period,grade,date;
    int courseID,courseSectionID,periodId,attendanceCategoryId;

    public String getCourseSectionName() {
        return courseSectionName;
    }

    public void setCourseSectionName(String courseSectionName) {
        this.courseSectionName = courseSectionName;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseSectionID() {
        return courseSectionID;
    }

    public void setCourseSectionID(int courseSectionID) {
        this.courseSectionID = courseSectionID;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public int getAttendanceCategoryId() {
        return attendanceCategoryId;
    }

    public void setAttendanceCategoryId(int attendanceCategoryId) {
        this.attendanceCategoryId = attendanceCategoryId;
    }
}
