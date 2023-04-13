package com.opensis.model;

public class SpinnerModel {
    String itemName,itemID;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public SpinnerModel(String itemName, String itemID) {
        this.itemName = itemName;
        this.itemID = itemID;
    }
}
