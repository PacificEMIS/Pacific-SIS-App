package com.opensissas.model;

public class SpinnerMixedModel {
    String itemName;
    int itemID;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public SpinnerMixedModel(String itemName, int itemID) {
        this.itemName = itemName;
        this.itemID = itemID;
    }
}
