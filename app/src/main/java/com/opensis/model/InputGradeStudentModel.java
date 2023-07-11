package com.opensis.model;

public class InputGradeStudentModel {
    String studentName,studentAltID,StudentPhoto,grade,obtainedGrade,teacherComment;
    int studenID,pos1,gradeId,gradeScaleId,percent;



    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentAltID() {
        return studentAltID;
    }

    public void setStudentAltID(String studentAltID) {
        this.studentAltID = studentAltID;
    }

    public String getStudentPhoto() {
        return StudentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        StudentPhoto = studentPhoto;
    }

    public int getStudenID() {
        return studenID;
    }

    public void setStudenID(int studenID) {
        this.studenID = studenID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getPos1() {
        return pos1;
    }

    public void setPos1(int pos1) {
        this.pos1 = pos1;
    }

    public String getObtainedGrade() {
        return obtainedGrade;
    }

    public void setObtainedGrade(String obtainedGrade) {
        this.obtainedGrade = obtainedGrade;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getGradeScaleId() {
        return gradeScaleId;
    }

    public void setGradeScaleId(int gradeScaleId) {
        this.gradeScaleId = gradeScaleId;
    }
}
