package com.opensis.model;

public class TeacherClassesModel {
    String time,className,subject,roomNo,period,grade,scheduleType,gradeScaleType;
    boolean attendanceTaken,isSunday,isMonday,isTuesDay,isWednesday,isThursDay,isFriday,isSaturday,isFixedSchedule;
    int courseId,courseSectionId,periodID,attendanceCategoryId;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
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

    public boolean isAttendanceTaken() {
        return attendanceTaken;
    }

    public void setAttendanceTaken(boolean attendanceTaken) {
        this.attendanceTaken = attendanceTaken;
    }

    public boolean isSunday() {
        return isSunday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
    }

    public boolean isMonday() {
        return isMonday;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public boolean isTuesDay() {
        return isTuesDay;
    }

    public void setTuesDay(boolean tuesDay) {
        isTuesDay = tuesDay;
    }

    public boolean isWednesday() {
        return isWednesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public boolean isThursDay() {
        return isThursDay;
    }

    public void setThursDay(boolean thursDay) {
        isThursDay = thursDay;
    }

    public boolean isFriday() {
        return isFriday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public boolean isSaturday() {
        return isSaturday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public boolean isFixedSchedule() {
        return isFixedSchedule;
    }

    public void setFixedSchedule(boolean fixedSchedule) {
        isFixedSchedule = fixedSchedule;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(int courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public int getPeriodID() {
        return periodID;
    }

    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }

    public int getAttendanceCategoryId() {
        return attendanceCategoryId;
    }

    public void setAttendanceCategoryId(int attendanceCategoryId) {
        this.attendanceCategoryId = attendanceCategoryId;
    }

    public String getGradeScaleType() {
        return gradeScaleType;
    }

    public void setGradeScaleType(String gradeScaleType) {
        this.gradeScaleType = gradeScaleType;
    }
}
