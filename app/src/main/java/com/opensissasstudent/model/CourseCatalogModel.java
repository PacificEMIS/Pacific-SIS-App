package com.opensissasstudent.model;

public class CourseCatalogModel {
    String courseTitle,courseShortName,courseGradeLevel,courseSubject,creditHours,courseSectionArray;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseShortName() {
        return courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    public String getCourseGradeLevel() {
        return courseGradeLevel;
    }

    public void setCourseGradeLevel(String courseGradeLevel) {
        this.courseGradeLevel = courseGradeLevel;
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(String courseSubject) {
        this.courseSubject = courseSubject;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getCourseSectionArray() {
        return courseSectionArray;
    }

    public void setCourseSectionArray(String courseSectionArray) {
        this.courseSectionArray = courseSectionArray;
    }
}
