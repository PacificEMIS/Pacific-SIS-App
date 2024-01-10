package com.opensissas.model;

public class AttendanceCodeModel {
    int attendanceCode,attendanceCatCode;
    String attendanceName,attendanceShortName;

    public int getAttendanceCode() {
        return attendanceCode;
    }

    public void setAttendanceCode(int attendanceCode) {
        this.attendanceCode = attendanceCode;
    }

    public int getAttendanceCatCode() {
        return attendanceCatCode;
    }

    public void setAttendanceCatCode(int attendanceCatCode) {
        this.attendanceCatCode = attendanceCatCode;
    }

    public String getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    public String getAttendanceShortName() {
        return attendanceShortName;
    }

    public void setAttendanceShortName(String attendanceShortName) {
        this.attendanceShortName = attendanceShortName;
    }
}
