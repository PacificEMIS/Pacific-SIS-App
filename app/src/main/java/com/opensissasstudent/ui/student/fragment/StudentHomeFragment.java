package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.DashboardAssignmentAdapter;
import com.opensissasstudent.adapter.DashboardAttendanceAdapter;
import com.opensissasstudent.adapter.RequestedClassAdapter;
import com.opensissasstudent.adapter.TeacherDashboardClassAdapter;
import com.opensissasstudent.adapter.TeacherDashboardEventAdapter;
import com.opensissasstudent.adapter.TeacherNotificationAdapter;
import com.opensissasstudent.adapter.TeacherNoticeAdapter;
import com.opensissasstudent.model.DashboardAssignmentModel;
import com.opensissasstudent.model.DashboardAttendanceModel;
import com.opensissasstudent.model.RequestedClassModel;
import com.opensissasstudent.model.TeacherClassesModel;
import com.opensissasstudent.model.TeacherDashboardEventModel;
import com.opensissasstudent.model.TeacherNoticeModel;
import com.opensissasstudent.model.TeacherNotificationModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.AppData;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class StudentHomeFragment extends Fragment implements View.OnClickListener {
    View view;
    RecyclerView rvClasses,rvNotification,rvNotice,rvCalendar,rvAssignments,rvRequestedClasses,rvAttendance;
    ArrayList<TeacherClassesModel>monclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>tueclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>wedclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>thuclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>friclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>satclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>sunclassList=new ArrayList<>();
    ArrayList<TeacherNotificationModel>notificationList=new ArrayList<>();
    ArrayList<DashboardAssignmentModel>assignmentlist=new ArrayList<>();
    ArrayList<TeacherNoticeModel>noticelist=new ArrayList<>();
    LinearLayout lnNotification,lnNotice,lnMissingAttendance;
    String monthFrstDate;
    String twomonthsDate;
    Pref pref;
    String period,room,time;
    int periodId,blockID;
    JSONObject jsonObject=new JSONObject();
    String currentDate,curDate;
    TeacherDashboardClassAdapter classAdapter;
    LinearLayout lnNoData,lnNoticeShowAll,lnEvent;
    TextView tvMissingAttendance;

    ArrayList<TeacherDashboardEventModel>eventList=new ArrayList<>();
    int missingCount;
    LinearLayout llAssignment,llRequestedClass;
    ArrayList<RequestedClassModel>requestedClassList=new ArrayList<>();
    TextView tvPeriodAttendance,tvPresent,tvAbsent,tvHalf;
    ArrayList<DashboardAttendanceModel>attendancesList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_student_home, container, false);
        initview();
        return view;
    }

    private void initview(){
        pref=new Pref(getContext());
        lnNotice=(LinearLayout)view.findViewById(R.id.lnNotice);
        lnNotification=(LinearLayout)view.findViewById(R.id.lnNotification);
        lnMissingAttendance=(LinearLayout)view.findViewById(R.id.lnMissingAttendance);
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        lnNoticeShowAll=(LinearLayout)view.findViewById(R.id.lnNoticeShowAll);
        lnEvent=(LinearLayout)view.findViewById(R.id.lnEvent);
        llRequestedClass=(LinearLayout)view.findViewById(R.id.llRequestedClass);

        tvMissingAttendance=(TextView)view.findViewById(R.id.tvMissingAttendance);

        rvClasses=(RecyclerView)view.findViewById(R.id.rvClasses);
        rvClasses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        rvRequestedClasses=(RecyclerView)view.findViewById(R.id.rvRequestedClasses);
        rvRequestedClasses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        rvAttendance=(RecyclerView)view.findViewById(R.id.rvAttendance);
        rvAttendance.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        rvNotification=(RecyclerView)view.findViewById(R.id.rvNotification);
        rvNotification.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));



        rvAssignments=(RecyclerView)view.findViewById(R.id.rvAssignments);
        rvAssignments.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        setNoticationAdapter();

        rvCalendar=(RecyclerView)view.findViewById(R.id.rvCalendar);
        rvCalendar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        rvNotice=(RecyclerView)view.findViewById(R.id.rvNotice);
        rvNotice.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        Date currentC = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("EEEE", Locale.getDefault());
         currentDate = df.format(currentC);
        Log.d("currentDate",currentDate);

        SimpleDateFormat cf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        curDate=cf.format(currentC);

        llAssignment=(LinearLayout) view.findViewById(R.id.llAssignment);

        try {
            jsonObject.put("studentId",pref.getUserID());
            jsonObject.put("membershipId",pref.getMemberShipID());
            jsonObject.put("allCourse",true);
            jsonObject.put("_tenantName", pref.getTenatName());
            jsonObject.put("_userName",pref.getName());
            jsonObject.put("_token",pref.getToken());
            jsonObject.put("_academicYear",pref.getAcademicYear());
            jsonObject.put("tenantId",pref.getTenatID());
            jsonObject.put("schoolId",pref.getSchoolID());
            jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
            jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
            getDashboardItem(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvPeriodAttendance=(TextView)view.findViewById(R.id.tvPeriodAttendance);
        tvPeriodAttendance.setText(pref.getPeriod()+" Attendance");

        tvPresent=(TextView)view.findViewById(R.id.tvPresent);
        tvAbsent=(TextView)view.findViewById(R.id.tvAbsent);
        tvHalf=(TextView)view.findViewById(R.id.tvHalf);

        lnMissingAttendance.setOnClickListener(this);

    }


    private void setNoticationAdapter(){
        TeacherNotificationAdapter notificationAdapter=new TeacherNotificationAdapter(notificationList,getContext());
        rvNotification.setAdapter(notificationAdapter);
    }



    public void getDashboardItem(JSONObject jsonObject) {
        rvClasses.setVisibility(View.VISIBLE);
        lnNoData.setVisibility(View.GONE);
        String url=pref.getAPI()+"StudentPortal/getStudentDashboard";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            rvClasses.setVisibility(View.GONE);
                            lnNoData.setVisibility(View.VISIBLE);


                        }else {
                            JSONArray courseSectionViewList=response.optJSONArray("courseSectionViewList");
                            if (courseSectionViewList.length()>0){
                                for (int i=0;i<courseSectionViewList.length();i++){
                                    JSONObject coursesectionOBJ=courseSectionViewList.optJSONObject(i);
                                    int courseSectionId=coursesectionOBJ.optInt("courseSectionId");
                                    int calendarId=coursesectionOBJ.optInt("calendarId");
                                    AppData.calendarID=calendarId;
                                    int courseId=coursesectionOBJ.optInt("courseId");
                                    String durationStartDate=coursesectionOBJ.optString("durationStartDate").replace("T00:00:00","");
                                    String durationEndDate=coursesectionOBJ.optString("durationEndDate").replace("T00:00:00","");
                                    String courseSectionName=coursesectionOBJ.optString("courseSectionName");
                                    String courseGradeLevel=coursesectionOBJ.optString("courseGradeLevel");
                                    boolean attendanceTaken=coursesectionOBJ.optBoolean("attendanceTaken");
                                    String meetingDays=coursesectionOBJ.optString("meetingDays");
                                    String courseTitle=coursesectionOBJ.optString("courseTitle");
                                    boolean isVirtualCourseSection=coursesectionOBJ.optBoolean("isVirtualCourseSection");
                                    int attendanceCategoryId=coursesectionOBJ.optInt("attendanceCategoryId");
                                    String gradeScaleType=coursesectionOBJ.optString("gradeScaleType");
                                    JSONArray courseVariableSchedule=coursesectionOBJ.optJSONArray("courseVariableSchedule");
                                    if (courseVariableSchedule.length()>0){

                                        for (int j=0;j<courseVariableSchedule.length();j++){
                                            JSONObject courseVariableScheduleOBJ=courseVariableSchedule.optJSONObject(j);
                                            JSONObject blockPeriod=courseVariableScheduleOBJ.optJSONObject("blockPeriod");
                                            if (blockPeriod!=null){
                                                time=blockPeriod.optString("periodStartTime");
                                                period=blockPeriod.optString("periodTitle");
                                                periodId=blockPeriod.optInt("periodId");
                                                blockID=blockPeriod.optInt("blockId");
                                            }

                                            JSONObject rooms=courseVariableScheduleOBJ.optJSONObject("rooms");
                                            if (rooms!=null){
                                                room=rooms.optString("title");
                                            }


                                        }


                                    }else {
                                        JSONObject courseFixedSchedule=coursesectionOBJ.optJSONObject("courseFixedSchedule");
                                        if (courseFixedSchedule!=null){
                                            JSONObject blockPeriod=courseFixedSchedule.optJSONObject("blockPeriod");
                                            if (blockPeriod!=null){
                                                time=blockPeriod.optString("periodStartTime");
                                                periodId=blockPeriod.optInt("periodId");
                                                period=blockPeriod.optString("periodTitle");
                                                blockID=blockPeriod.optInt("blockId");
                                            }

                                            JSONObject rooms=courseFixedSchedule.optJSONObject("rooms");
                                            if (rooms!=null){
                                                room=rooms.optString("title");
                                            }
                                            room=rooms.optString("title");
                                        }

                                    }

                                    if (checkDate(durationStartDate,curDate,durationEndDate)){
                                        TeacherClassesModel classesModel=new TeacherClassesModel();
                                        classesModel.setClassName(courseSectionName);
                                        classesModel.setSubject(courseTitle);
                                        classesModel.setGrade(courseGradeLevel);
                                        classesModel.setAttendanceTaken(attendanceTaken);
                                        classesModel.setTime(time);
                                        classesModel.setPeriod(period);

                                        if (isVirtualCourseSection){
                                            classesModel.setRoomNo("Virtual");
                                        }else {
                                            classesModel.setRoomNo(room);
                                        }

                                        classesModel.setPeriodID(periodId);
                                        classesModel.setBlockID(blockID);
                                        classesModel.setAttendanceCategoryId(attendanceCategoryId);
                                        classesModel.setCourseSectionId(courseSectionId);
                                        classesModel.setGradeScaleType(gradeScaleType);
                                        classesModel.setCourseId(courseId);
                                        if (meetingDays.contains("Monday")){
                                            classesModel.setMonday(true);
                                            monclassList.add(classesModel);

                                            Log.d("riku","1");
                                        } if (meetingDays.contains("Tuesday")){
                                            classesModel.setTuesDay(true);
                                            tueclassList.add(classesModel);
                                            Log.d("riku","2");
                                        } if (meetingDays.contains("Wednesday")){
                                            classesModel.setWednesday(true);
                                            wedclassList.add(classesModel);
                                            Log.d("riku","3");
                                        } if (meetingDays.contains("Thursday")){
                                            classesModel.setThursDay(true);
                                            thuclassList.add(classesModel);
                                            Log.d("riku","4");
                                        } if (meetingDays.contains("Friday")){
                                            classesModel.setFriday(true);
                                            friclassList.add(classesModel);

                                            Log.d("riku","5");
                                        } if (meetingDays.contains("Saturday")){
                                            classesModel.setSaturday(true);
                                            satclassList.add(classesModel);
                                            Log.d("riku","6");
                                        } if (meetingDays.contains("Sunday")){
                                            classesModel.setSunday(true);
                                            sunclassList.add(classesModel);
                                            Log.d("riku","7");
                                        }
                                    }





                                    if (currentDate.equalsIgnoreCase("monday")){
                                        if (monclassList.size()>0) {
                                            rvClasses.setVisibility(View.VISIBLE);
                                            lnNoData.setVisibility(View.GONE);
                                            classAdapter = new TeacherDashboardClassAdapter(monclassList, getContext());
                                        }else {
                                            rvClasses.setVisibility(View.GONE);
                                            lnNoData.setVisibility(View.VISIBLE);
                                        }

                                    }else if (currentDate.equalsIgnoreCase("tuesday")){
                                        if (tueclassList.size()>0) {
                                            rvClasses.setVisibility(View.VISIBLE);
                                            lnNoData.setVisibility(View.GONE);
                                            classAdapter = new TeacherDashboardClassAdapter(tueclassList, getContext());
                                        }else {
                                            rvClasses.setVisibility(View.GONE);
                                            lnNoData.setVisibility(View.VISIBLE);
                                        }

                                    }else if (currentDate.equalsIgnoreCase("wednesday")){
                                        if (wedclassList.size()>0) {
                                            rvClasses.setVisibility(View.VISIBLE);
                                            lnNoData.setVisibility(View.GONE);
                                            classAdapter = new TeacherDashboardClassAdapter(wedclassList, getContext());
                                        }else {
                                            rvClasses.setVisibility(View.GONE);
                                            lnNoData.setVisibility(View.VISIBLE);
                                        }

                                    }else if (currentDate.equalsIgnoreCase("thursday")){
                                        if (thuclassList.size()>0) {
                                            rvClasses.setVisibility(View.VISIBLE);
                                            lnNoData.setVisibility(View.GONE);
                                            classAdapter = new TeacherDashboardClassAdapter(thuclassList, getContext());
                                        }else {
                                            rvClasses.setVisibility(View.GONE);
                                            lnNoData.setVisibility(View.VISIBLE);
                                        }

                                    }else if (currentDate.equalsIgnoreCase("friday")){
                                        if (friclassList.size()>0) {
                                            rvClasses.setVisibility(View.VISIBLE);
                                            lnNoData.setVisibility(View.GONE);
                                            classAdapter = new TeacherDashboardClassAdapter(friclassList, getContext());
                                        }else {
                                            rvClasses.setVisibility(View.GONE);
                                            lnNoData.setVisibility(View.VISIBLE);
                                        }

                                    }else if (currentDate.equalsIgnoreCase("saturday")){
                                        if (satclassList.size()>0) {
                                            rvClasses.setVisibility(View.VISIBLE);
                                            lnNoData.setVisibility(View.GONE);
                                            classAdapter = new TeacherDashboardClassAdapter(satclassList, getContext());
                                        }else {
                                            rvClasses.setVisibility(View.GONE);
                                            lnNoData.setVisibility(View.VISIBLE);
                                        }

                                    }else if (currentDate.equalsIgnoreCase("sunday")){
                                        if (sunclassList.size()>0) {
                                            rvClasses.setVisibility(View.VISIBLE);
                                            lnNoData.setVisibility(View.GONE);
                                            classAdapter = new TeacherDashboardClassAdapter(sunclassList, getContext());
                                        }else {
                                            rvClasses.setVisibility(View.GONE);
                                            lnNoData.setVisibility(View.VISIBLE);
                                        }

                                    }

                                    rvClasses.setAdapter(classAdapter);



                                }
                                JSONArray assignmentList=response.optJSONArray("assignmentList");
                                if (assignmentList.length()>0){
                                    llAssignment.setVisibility(View.VISIBLE);
                                    for (int z=0;z<assignmentList.length();z++){
                                        JSONObject obj=assignmentList.optJSONObject(z);
                                        String assignmentTitle=obj.optString("assignmentTitle");
                                        String courseSectionTitle=obj.optString("courseSectionTitle");
                                        String dueDate= Util.changeAnyDateFormat(obj.optString("dueDate").replaceAll("T00:00:00",""),"yyyy-MM-dd","MMM dd, yyyy");
                                        String assignmentTypeTitle=obj.optString("assignmentTypeTitle");
                                        DashboardAssignmentModel assignmentModel=new DashboardAssignmentModel();
                                        assignmentModel.setAssignmentTitle(assignmentTitle);
                                        assignmentModel.setAssignmentSectionTitle(courseSectionTitle);
                                        assignmentModel.setAssignmentTypeTitle(assignmentTypeTitle);
                                        assignmentModel.setDueDate(dueDate);
                                        assignmentlist.add(assignmentModel);

                                    }

                                    DashboardAssignmentAdapter assignmentAdapter=new DashboardAssignmentAdapter(assignmentlist);
                                    rvAssignments.setAdapter(assignmentAdapter);
                                }

                                JSONArray noticeList=response.optJSONArray("noticeList");
                                if (noticeList.length()>0){
                                    for (int z=0;z<noticeList.length();z++){
                                        JSONObject obj=noticeList.optJSONObject(z);
                                        String title=obj.optString("title");
                                        String body=obj.optString("body");
                                        TeacherNoticeModel noticeModel=new TeacherNoticeModel();
                                        noticeModel.setDetails(body);
                                        noticeModel.setNotice(title);
                                        noticelist.add(noticeModel);

                                    }
                                    lnNotice.setVisibility(View.VISIBLE);
                                    TeacherNoticeAdapter notificeAdapter=new TeacherNoticeAdapter(noticelist,getContext());
                                    rvNotice.setAdapter(notificeAdapter);
                                    if (noticeList.length()>1){
                                        lnNoticeShowAll.setVisibility(View.VISIBLE);
                                    }else {
                                        lnNoticeShowAll.setVisibility(View.GONE);
                                    }
                                }else {
                                    lnNotice.setVisibility(View.GONE);
                                }

                                int missingAttendanceCount=response.optInt("missingAttendanceCount");
                                missingCount=missingAttendanceCount;
                                tvMissingAttendance.setText("You have "+missingAttendanceCount+" missing attendances");
                                if (missingAttendanceCount!=0){
                                    lnMissingAttendance.setVisibility(View.VISIBLE);
                                }else {
                                    lnMissingAttendance.setVisibility(View.GONE);
                                }


                            }

                            JSONArray studentCourseRegistrationViewList=response.optJSONArray("studentCourseRegistrationViewList");
                            if (studentCourseRegistrationViewList.length()>0){
                                llRequestedClass.setVisibility(View.VISIBLE);
                                for (int i=0;i<studentCourseRegistrationViewList.length();i++){
                                    JSONObject requestedClassObj=studentCourseRegistrationViewList.optJSONObject(i);
                                    String subject=requestedClassObj.optString("subject");
                                    String approvalStatus=requestedClassObj.optString("approvalStatus");
                                    RequestedClassModel requestedModel=new RequestedClassModel();
                                    requestedModel.setSubject(subject);
                                    requestedModel.setApprovalStatus(approvalStatus);
                                    requestedClassList.add(requestedModel);
                                }

                                RequestedClassAdapter requestedClassAdapter=new RequestedClassAdapter(requestedClassList);
                                rvRequestedClasses.setAdapter(requestedClassAdapter);
                            }


                            JSONArray attendanceList=response.optJSONArray("attendanceList");
                            JSONObject attendanceListobj=attendanceList.optJSONObject(0);
                            int presentCount=attendanceListobj.optInt("presentCount");
                            tvPresent.setText("Present ("+presentCount+")");
                            int absentCount=attendanceListobj.optInt("absentCount");
                            tvAbsent.setText("Absent ("+absentCount+")");
                            int halfDayCount=attendanceListobj.optInt("halfDayCount");
                            tvHalf.setText("Half Day ("+halfDayCount+")");
                            JSONArray studentDailyAttendances=attendanceListobj.optJSONArray("studentDailyAttendances");
                            for (int i=0;i<studentDailyAttendances.length();i++){
                                JSONObject dailyAttendanceObj=studentDailyAttendances.optJSONObject(i);
                                String attendanceCode=Util.getFreshValue(dailyAttendanceObj.optString("attendanceCode"),"!");
                                char first = attendanceCode.charAt(0);
                                DashboardAttendanceModel attendanceModel=new DashboardAttendanceModel();
                                attendanceModel.setStatus(Character.toString(first));
                                attendancesList.add(attendanceModel);

                            }

                            DashboardAttendanceAdapter attendanceAdapter=new DashboardAttendanceAdapter(attendancesList,getContext());
                            rvAttendance.setAdapter(attendanceAdapter);




                        }

                        JSONObject jsonObject1=new JSONObject();
                        JSONArray jsonArray=new JSONArray();
                        jsonObject1.put("noticeList",jsonArray);
                        jsonObject1.put("membershipId",pref.getMemberShipID());
                        jsonObject1.put("userId",pref.getUserID());
                        jsonObject1.put("_tenantName",pref.getTenatName());
                        jsonObject1.put("_userName",pref.getName());
                        jsonObject1.put("_token",pref.getToken());
                        jsonObject1.put("_academicYear",pref.getAcademicYear());
                        jsonObject1.put("tenantId",pref.getTenatID());
                        jsonObject1.put("schoolId",pref.getSchoolID());
                        jsonObject1.put("academicYear",pref.getAcademicYear());
                        getEventItem(jsonObject1);






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getEventItem(JSONObject jsonObject) {
        lnEvent.setVisibility(View.GONE);
        String url=pref.getAPI()+"Common/getDashboardViewForCalendarView";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("eventitemresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            if (_message.contains("Token")){
                                Intent intent=new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            }

                        }else {
                            JSONArray calendarEventList=response.optJSONArray("calendarEventList");
                            if (calendarEventList.length()>0) {
                                for (int i = 0; i < calendarEventList.length(); i++) {
                                    JSONObject eventOBJ=calendarEventList.optJSONObject(i);
                                    String title=eventOBJ.optString("title");
                                    String startDate=eventOBJ.optString("startDate").replace("T00:00:00","");
                                    String endDate=eventOBJ.optString("endDate").replace("T00:00:00","");
                                    String eventColor=eventOBJ.optString("eventColor");
                                    TeacherDashboardEventModel eventModel=new TeacherDashboardEventModel();
                                    eventModel.setEventName(title);
                                    eventModel.setEventDate(startDate);
                                    eventModel.setEventColor(eventColor);
                                    eventModel.setEndDate(endDate);
                                    eventList.add(eventModel);


                                }
                                lnEvent.setVisibility(View.VISIBLE);
                                TeacherDashboardEventAdapter eventAdapter=new TeacherDashboardEventAdapter(eventList,getContext());
                                rvCalendar.setAdapter(eventAdapter);
                            }else {
                                lnEvent.setVisibility(View.GONE);
                            }

                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private boolean checkDate(String start,String check,String end){
        boolean dateflag;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        Date strtd = null;
        Date cureentd=null;
        Date endd=null;
        try {
            strtd = sdf.parse(start);
            cureentd = sdf.parse(check);
            endd = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (cureentd.compareTo(strtd) >= 0) {
            if (cureentd.compareTo(endd) <= 0) {
                dateflag=true;
            } else {
                dateflag=false;
            }
        } else {
            dateflag=false;
        }

        return dateflag;
    }

    @Override
    public void onClick(View v) {
        if (v==lnMissingAttendance){

        }
    }
}