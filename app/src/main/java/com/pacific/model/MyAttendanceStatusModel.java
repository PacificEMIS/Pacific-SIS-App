package com.pacific.model;

import java.util.ArrayList;

public class MyAttendanceStatusModel {
    String date;
    private ArrayList<AttendanceStatusDetailsModel> statusDetailsModelArrayList;


    public MyAttendanceStatusModel(String date, ArrayList<AttendanceStatusDetailsModel> statusDetailsModelArrayList) {
        this.date = date;
        this.statusDetailsModelArrayList = statusDetailsModelArrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<AttendanceStatusDetailsModel> getStatusDetailsModelArrayList() {
        return statusDetailsModelArrayList;
    }

    public void setStatusDetailsModelArrayList(ArrayList<AttendanceStatusDetailsModel> statusDetailsModelArrayList) {
        this.statusDetailsModelArrayList = statusDetailsModelArrayList;
    }
}
