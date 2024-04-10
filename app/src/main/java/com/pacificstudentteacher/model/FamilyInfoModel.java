package com.pacificstudentteacher.model;

public class FamilyInfoModel {
    String name,contactType,relationship,parentPhoto;
    boolean isCustodian;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public boolean isCustodian() {
        return isCustodian;
    }

    public void setCustodian(boolean custodian) {
        isCustodian = custodian;
    }

    public String getParentPhoto() {
        return parentPhoto;
    }

    public void setParentPhoto(String parentPhoto) {
        this.parentPhoto = parentPhoto;
    }
}
