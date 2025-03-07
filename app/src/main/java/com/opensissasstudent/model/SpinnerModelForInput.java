package com.opensissasstudent.model;

public class SpinnerModelForInput {
    String itemName;
    int breakoff;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }



    public int getBreakoff() {
        return breakoff;
    }

    public void setBreakoff(int breakoff) {
        this.breakoff = breakoff;
    }

    public SpinnerModelForInput(String itemName, int breakoff) {
        this.itemName = itemName;
        this.breakoff = breakoff;
    }
}
