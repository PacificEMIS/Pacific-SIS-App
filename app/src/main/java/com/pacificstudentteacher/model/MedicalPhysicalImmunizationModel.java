package com.pacificstudentteacher.model;

public class MedicalPhysicalImmunizationModel {
    String immunizationType,immunizationDate,comment;

    public String getImmunizationType() {
        return immunizationType;
    }

    public void setImmunizationType(String immunizationType) {
        this.immunizationType = immunizationType;
    }

    public String getImmunizationDate() {
        return immunizationDate;
    }

    public void setImmunizationDate(String immunizationDate) {
        this.immunizationDate = immunizationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
