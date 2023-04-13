package com.opensis.ui.teacher.fragment;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.TeacherDashboardClassAdapter;
import com.opensis.adapter.TeacherDashboardEventAdapter;
import com.opensis.adapter.TeacherNotificationAdapter;
import com.opensis.adapter.TeacherNoticeAdapter;
import com.opensis.model.TeacherClassesModel;
import com.opensis.model.TeacherDashboardEventModel;
import com.opensis.model.TeacherNoticeModel;
import com.opensis.model.TeacherNotificationModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TeacheHomeFragment extends Fragment {
    View view;
    RecyclerView rvClasses,rvNotification,rvNotice,rvCalendar;
    ArrayList<TeacherClassesModel>monclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>tueclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>wedclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>thuclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>friclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>satclassList=new ArrayList<>();
    ArrayList<TeacherClassesModel>sunclassList=new ArrayList<>();
    ArrayList<TeacherNotificationModel>notificationList=new ArrayList<>();
    ArrayList<TeacherNoticeModel>noticelist=new ArrayList<>();
    LinearLayout lnNotification,lnNotice,lnMissingAttendance;
    String monthFrstDate;
    String twomonthsDate;
    Pref pref;
    String period,room,time;
    int periodId;
    JSONObject jsonObject=new JSONObject();
    String currentDate,curDate;
    TeacherDashboardClassAdapter classAdapter;
    LinearLayout lnNoData,lnNoticeShowAll,lnEvent;
    TextView tvMissingAttendance;

    ArrayList<TeacherDashboardEventModel>eventList=new ArrayList<>();
    int currentMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_teache_home, container, false);
        initview();
        return view;
    }

    private void initview(){
        pref=new Pref(getContext());
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        currentMonth= Integer.parseInt(dateFormat.format(date))+1;
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DATE, 1);
        monthFrstDate=Util.changeAnyDateFormat(c.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd")+"T00:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date calculateDate = null;
        try {
            calculateDate = sdf.parse(monthFrstDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(calculateDate);
        calendar.add(Calendar.MONTH,2);
        //Log.d("twomonthsDate",calendar.getTime().toString());
        twomonthsDate=Util.changeAnyDateFormat(calendar.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd")+"T00:00:00";
        Log.d("twomonthsDate",twomonthsDate);
        lnNotice=(LinearLayout)view.findViewById(R.id.lnNotice);
        lnNotification=(LinearLayout)view.findViewById(R.id.lnNotification);
        lnMissingAttendance=(LinearLayout)view.findViewById(R.id.lnMissingAttendance);
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        lnNoticeShowAll=(LinearLayout)view.findViewById(R.id.lnNoticeShowAll);
        lnEvent=(LinearLayout)view.findViewById(R.id.lnEvent);

        tvMissingAttendance=(TextView)view.findViewById(R.id.tvMissingAttendance);

        rvClasses=(RecyclerView)view.findViewById(R.id.rvClasses);
        rvClasses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        rvNotification=(RecyclerView)view.findViewById(R.id.rvNotification);
        rvNotification.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

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

        try {
            jsonObject.put("staffId",pref.getUserID());
            jsonObject.put("membershipId",pref.getMemberShipID());
            jsonObject.put("allCourse",true);
            jsonObject.put("_tenantName", pref.getTenatName());
            jsonObject.put("_userName",pref.getName());
            jsonObject.put("_token",pref.getToken());
            jsonObject.put("tenantId",AppData.tenatID);
            jsonObject.put("schoolId",pref.getSchoolID());
            jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
            jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
            getDashboardItem(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void setNoticationAdapter(){
        TeacherNotificationAdapter notificationAdapter=new TeacherNotificationAdapter(notificationList,getContext());
        rvNotification.setAdapter(notificationAdapter);
    }



    public void getDashboardItem(JSONObject jsonObject) {
        rvClasses.setVisibility(View.VISIBLE);
        lnNoData.setVisibility(View.GONE);
        String url=pref.getAPI()+"Common/getDashboardViewForStaff";


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
                                    int courseId=coursesectionOBJ.optInt("courseId");
                                    String durationStartDate=coursesectionOBJ.optString("durationStartDate").replace("T00:00:00","");
                                    String durationEndDate=coursesectionOBJ.optString("durationEndDate").replace("T00:00:00","");
                                    String courseSectionName=coursesectionOBJ.optString("courseSectionName");
                                    String courseGradeLevel=coursesectionOBJ.optString("courseGradeLevel");
                                    boolean attendanceTaken=coursesectionOBJ.optBoolean("attendanceTaken");
                                    String meetingDays=coursesectionOBJ.optString("meetingDays");
                                    String courseTitle=coursesectionOBJ.optString("courseTitle");
                                    int attendanceCategoryId=coursesectionOBJ.optInt("attendanceCategoryId");
                                    String gradeScaleType=coursesectionOBJ.optString("gradeScaleType");
                                    JSONArray courseVariableSchedule=coursesectionOBJ.optJSONArray("courseVariableSchedule");
                                    if (courseVariableSchedule.length()>0){
                                        for (int j=0;j<courseVariableSchedule.length();j++){
                                            JSONObject courseVariableScheduleOBJ=courseVariableSchedule.optJSONObject(i);
                                            JSONObject blockPeriod=courseVariableScheduleOBJ.optJSONObject("blockPeriod");
                                            time=blockPeriod.optString("periodStartTime");
                                            period=blockPeriod.optString("periodTitle");
                                            periodId=blockPeriod.optInt("periodId");
                                            JSONObject rooms=courseVariableScheduleOBJ.optJSONObject("rooms");
                                            room=rooms.optString("title");

                                        }

                                    }else {
                                        JSONObject courseFixedSchedule=coursesectionOBJ.optJSONObject("courseFixedSchedule");
                                        JSONObject blockPeriod=courseFixedSchedule.optJSONObject("blockPeriod");
                                        time=blockPeriod.optString("periodStartTime");
                                        periodId=blockPeriod.optInt("periodId");
                                        period=blockPeriod.optString("periodTitle");
                                        JSONObject rooms=courseFixedSchedule.optJSONObject("rooms");
                                        room=rooms.optString("title");
                                    }

                                    if (checkDate(durationStartDate,curDate,durationEndDate)){
                                        TeacherClassesModel classesModel=new TeacherClassesModel();
                                        classesModel.setClassName(courseSectionName);
                                        classesModel.setSubject(courseTitle);
                                        classesModel.setGrade(courseGradeLevel);
                                        classesModel.setAttendanceTaken(attendanceTaken);
                                        classesModel.setTime(time);
                                        classesModel.setPeriod(period);
                                        classesModel.setRoomNo(room);
                                        classesModel.setPeriodID(periodId);
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
                                tvMissingAttendance.setText("You have "+missingAttendanceCount+" missing attendances");
                                if (missingAttendanceCount!=0){
                                    lnMissingAttendance.setVisibility(View.VISIBLE);
                                }else {
                                    lnMissingAttendance.setVisibility(View.GONE);
                                }


                            }

                        }

                        JSONObject jsonObject1=new JSONObject();
                        JSONArray jsonArray=new JSONArray();
                        jsonObject1.put("noticeList",jsonArray);
                        jsonObject1.put("membershipId",pref.getMemberShipID());
                        jsonObject1.put("_tenantName",pref.getTenatName());
                        jsonObject1.put("_userName",pref.getName());
                        jsonObject1.put("_token",pref.getToken());
                        jsonObject1.put("tenantId",AppData.tenatID);
                        jsonObject1.put("schoolId",pref.getSchoolID());
                        jsonObject1.put("academicYear",pref.getAcademyYear());
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
                                    String eventColor=eventOBJ.optString("eventColor");
                                    TeacherDashboardEventModel eventModel=new TeacherDashboardEventModel();
                                    eventModel.setEventName(title);
                                    eventModel.setEventDate(startDate);
                                    eventModel.setEventColor(eventColor);
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
}