package com.pacific.model;

public class GradeBookGradeModel {
    String courseSectionName,letter,percent,staff,lowestGrade,highestGrade;
    int courseSectionId,ungraded;

    public String getCourseSectionName() {
        return courseSectionName;
    }

    public void setCourseSectionName(String courseSectionName) {
        this.courseSectionName = courseSectionName;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(int courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getLowestGrade() {
        return lowestGrade;
    }

    public void setLowestGrade(String lowestGrade) {
        this.lowestGrade = lowestGrade;
    }

    public String getHighestGrade() {
        return highestGrade;
    }

    public void setHighestGrade(String highestGrade) {
        this.highestGrade = highestGrade;
    }

    public int getUngraded() {
        return ungraded;
    }

    public void setUngraded(int ungraded) {
        this.ungraded = ungraded;
    }
}
