package com.opensis.others.utility;

public class Api {
    //app-test->test link
    //app->live
    public static String sHost = "";
    public static String subHost="";
    public static String mainURL;
    public static String loginApi = mainURL + "User/ValidateLogin";
    public static String dashboardviewforStaffApi = mainURL + "Common/getDashboardViewForStaff";
    public static String dashboardEventApi = mainURL + "Common/getDashboardViewForCalendarView";
    public static String allschoolAddressListApi = mainURL + "School/getAllSchoolList";
    public static String viewSchoolApi = mainURL + "School/viewSchool";
    public static String viewStudentListApi = mainURL + "StudentSchedule/getStudentListByCourseSection";
    public static String viewClassDetailsApi = mainURL + "CourseManager/getAllCourseSection";
    public static String classStudentApi = mainURL + "StudentSchedule/getStudentListByCourseSection";
    public static String assignmentApi = mainURL + "StaffPortalAssignment/getAllAssignmentType";
    public static String addassignmentTypeApi = mainURL + "StaffPortalAssignment/addAssignmentType";
    public static String markingPeriodApi = mainURL + "MarkingPeriod/getMarkingPeriodTitleList";
    public static String addAssignmentDetailsAPI = mainURL + "StaffPortalAssignment/addAssignment";
    public static String deleteAssignmentTypeAPI = mainURL + "StaffPortalAssignment/deleteAssignmentType";
    public static String updateAssignmentTypeAPI = mainURL + "StaffPortalAssignment/updateAssignmentType";
    public static String deleteAssignmentAPi = mainURL + "StaffPortalAssignment/deleteAssignment";
    public static String refreshTokenAPi = mainURL + "User/RefreshToken";
    public static String gradeBookgradeAPi = mainURL + "StaffPortalGradebook/getGradebookGrade";
    public static String addgradeAPi = mainURL + "StaffPortalGradebook/addGradebookGrade";
    public static String courseWiseStudentAttendanceAPi = mainURL + "StudentAttendance/getAllStudentAttendanceList";
    public static String courseWiseStudentListAPi = mainURL + "StudentSchedule/getStudentListByCourseSection";
    public static String attendanceCodeAPi = mainURL + "AttendanceCode/getAllAttendanceCode";
    public static String attendanceaddAPi = mainURL + "StudentAttendance/addUpdateStudentAttendance";
    public static String finalGradeAPi = mainURL + "StaffPortalGradebook/populateFinalGrading";
    public static String addupdateGradeBookConfig = mainURL + "StaffPortalGradebook/addUpdateGradebookConfiguration";
    public static String viewGradeBookConfig = mainURL + "StaffPortalGradebook/viewGradebookConfiguration";
    public static String getAllSchool = mainURL + "School/getAllSchools";
    public static String getAcademyYear = mainURL + "MarkingPeriod/getAcademicYearList";
    public static String markingPeriod = mainURL + "MarkingPeriod/getMarkingPeriod";
    public static String calendarEventAPI = mainURL + "CalendarEvent/getAllCalendarEvent";
    public static String scheduleAPI = mainURL + "Staff/getScheduledCourseSectionsForStaff";
    public static String viewDropDownAPI = mainURL + "Common/getAllDropdownValues";
    public static String viewCountryApi = mainURL + "Common/getAllCountries";
    public static String viewLanguageApi = mainURL + "Common/getAllLanguage";

}
