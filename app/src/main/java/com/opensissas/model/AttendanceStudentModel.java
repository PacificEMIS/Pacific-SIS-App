package com.opensissas.model;

public class AttendanceStudentModel {
    String studentFrstName,studentLstName,altId,studentID,grade,section,comments,attendanceName,studentPhoto;
    int ID,attendanceCatCode,attendanceCode;
    boolean isSelected;


    public String getStudentFrstName() {
        return studentFrstName;
    }

    public void setStudentFrstName(String studentFrstName) {
        this.studentFrstName = studentFrstName;
    }

    public String getStudentLstName() {
        return studentLstName;
    }

    public void setStudentLstName(String studentLstName) {
        this.studentLstName = studentLstName;
    }

    public String getAltId() {
        return altId;
    }

    public void setAltId(String altId) {
        this.altId = altId;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }



    public int getAttendanceCatCode() {
        return attendanceCatCode;
    }

    public void setAttendanceCatCode(int attendanceCatCode) {
        this.attendanceCatCode = attendanceCatCode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    public int getAttendanceCode() {
        return attendanceCode;
    }

    public void setAttendanceCode(int attendanceCode) {
        this.attendanceCode = attendanceCode;
    }

    public String getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }
}
