package com.opensissasstudent.ui.teacher.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.TeacherClassAdapter;
import com.opensissasstudent.model.TeacherClassesModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.AppData;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.ui.common.activity.LoginActivity;
import com.opensissasstudent.ui.teacher.activity.TeacherDashboardActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ClassFragment extends Fragment {

    View view;
    RecyclerView rvClasses;
    ArrayList<TeacherClassesModel> itmList=new ArrayList<>();
    LinearLayout lnNoData;
    Pref pref;
    String period,room,time;
    JSONObject jsonObject=new JSONObject();
    String monthFrstDate,twoMonthsLastDate;
    String twomonthsDate;
    int periodId,blockID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_class, container, false);
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());

        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        rvClasses=(RecyclerView)view.findViewById(R.id.rvClasses);
        rvClasses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        JSONObject obj=new JSONObject();
        JSONObject accessobj=new JSONObject();
        try {
            obj.put("tenantId",pref.getTenatID());
            obj.put("userId",0);
            obj.put("userAccessLog",accessobj);
            obj.put("_tenantName", pref.getTenatName());
            obj.put("_userName", pref.getName());
            obj.put("_token", pref.getToken());
            obj.put("schoolId", pref.getSchoolID());
            obj.put("email", pref.getEmail());
            getRefreshToken(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setAdapter(){
        TeacherClassAdapter classAdapter=new TeacherClassAdapter(itmList,getContext());
        rvClasses.setAdapter(classAdapter);
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

                            if (_message.contains("token")||_message.contains("Token") ){
                                JSONObject jobj=new JSONObject();
                                try {
                                    jobj.put("tenantId",pref.getTenatID());
                                    jobj.put("userId",0);
                                    jobj.put("_tenantName",pref.getTenatName());
                                    jobj.put("password",pref.getPasswordBase64());
                                    jobj.put("email",pref.getLoginEmail());
                                    jobj.put("schoolId",null);
                                    jobj.put("_userName","");
                                    jobj.put("_token",null);
                                    jobj.put("isMobileLogin",true);
                                    login(jobj);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                rvClasses.setVisibility(View.GONE);
                                lnNoData.setVisibility(View.VISIBLE);
                            }



                        }else {
                            JSONArray courseSectionViewList = response.optJSONArray("courseSectionViewList");
                            if (courseSectionViewList.length() > 0) {
                                for (int i = 0; i < courseSectionViewList.length(); i++) {
                                    JSONObject coursesectionOBJ = courseSectionViewList.optJSONObject(i);
                                    int courseId=coursesectionOBJ.optInt("courseId");
                                    String gradeScaleType=coursesectionOBJ.optString("gradeScaleType");
                                    int courseSectionId=coursesectionOBJ.optInt("courseSectionId");
                                    int calendarId=coursesectionOBJ.optInt("calendarId");
                                    AppData.calendarID=calendarId;
                                    int attendanceCategoryId=coursesectionOBJ.optInt("attendanceCategoryId");
                                    String courseSectionName = coursesectionOBJ.optString("courseSectionName");
                                    boolean isVirtualCourseSection=coursesectionOBJ.optBoolean("isVirtualCourseSection");
                                    String courseGradeLevel = coursesectionOBJ.optString("courseGradeLevel");
                                    boolean attendanceTaken = coursesectionOBJ.optBoolean("attendanceTaken");
                                    String meetingDays = coursesectionOBJ.optString("meetingDays");
                                    String courseTitle = coursesectionOBJ.optString("courseTitle");
                                    String scheduleType=coursesectionOBJ.optString("scheduleType");
                                    JSONArray courseVariableSchedule = coursesectionOBJ.optJSONArray("courseVariableSchedule");
                                    if (courseVariableSchedule.length() > 0) {
                                        for (int j = 0; j < courseVariableSchedule.length(); j++) {
                                            JSONObject courseVariableScheduleOBJ = courseVariableSchedule.optJSONObject(j);
                                            JSONObject blockPeriod = courseVariableScheduleOBJ.optJSONObject("blockPeriod");
                                            time = blockPeriod.optString("periodStartTime");
                                            period = blockPeriod.optString("periodTitle");
                                            JSONObject rooms = courseVariableScheduleOBJ.optJSONObject("rooms");
                                            room = rooms.optString("title");
                                            periodId=blockPeriod.optInt("periodId");
                                            blockID=blockPeriod.optInt("blockId");

                                        }


                                    } else {
                                        JSONObject courseFixedSchedule = coursesectionOBJ.optJSONObject("courseFixedSchedule");
                                        if (courseFixedSchedule!=null) {
                                            JSONObject blockPeriod = courseFixedSchedule.optJSONObject("blockPeriod");
                                            time = blockPeriod.optString("periodStartTime");
                                            period = blockPeriod.optString("periodTitle");
                                            JSONObject rooms = courseFixedSchedule.optJSONObject("rooms");
                                            room = rooms.optString("title");
                                            periodId=blockPeriod.optInt("periodId");
                                            blockID=blockPeriod.optInt("blockId");
                                        }
                                    }


                                    TeacherClassesModel classesModel = new TeacherClassesModel();
                                    classesModel.setClassName(courseSectionName);
                                    classesModel.setCourseId(courseId);
                                    classesModel.setSubject(courseTitle);
                                    classesModel.setGrade(courseGradeLevel);
                                    classesModel.setAttendanceTaken(attendanceTaken);
                                    classesModel.setAttendanceCategoryId(attendanceCategoryId);
                                    classesModel.setCourseSectionId(courseSectionId);
                                    classesModel.setPeriodID(periodId);
                                    classesModel.setBlockID(blockID);
                                    classesModel.setGradeScaleType(gradeScaleType);
                                    if ( isVirtualCourseSection){
                                        classesModel.setRoomNo("Virtual");
                                        classesModel.setTime("");
                                        classesModel.setPeriod("");
                                    }else {
                                        classesModel.setRoomNo(room);
                                        classesModel.setTime(time);
                                        classesModel.setPeriod(period);

                                    }

                                    classesModel.setScheduleType(scheduleType);
                                    if (courseVariableSchedule.length()>0){
                                       classesModel.setFixedSchedule(false);
                                    }else {
                                        classesModel.setFixedSchedule(true);
                                    }
                                    if (meetingDays.contains("Monday")) {
                                        classesModel.setMonday(true);
                                        Log.d("riku", "1");
                                    }
                                    if (meetingDays.contains("Tuesday")) {
                                        classesModel.setTuesDay(true);
                                        Log.d("riku", "2");
                                    }
                                    if (meetingDays.contains("Wednesday")) {
                                        classesModel.setWednesday(true);
                                        Log.d("riku", "3");
                                    }
                                    if (meetingDays.contains("Thursday")) {
                                        classesModel.setThursDay(true);
                                        Log.d("riku", "4");
                                    }
                                    if (meetingDays.contains("Friday")) {
                                        classesModel.setFriday(true);
                                        Log.d("riku", "5");
                                    }
                                    if (meetingDays.contains("Saturday")) {
                                        classesModel.setSaturday(true);
                                        Log.d("riku", "6");
                                    }
                                    if (meetingDays.contains("Sunday")) {
                                        classesModel.setSunday(true);
                                        Log.d("riku", "7");
                                    }

                                    itmList.add(classesModel);


                                    setAdapter();


                                }
                            }else {
                                rvClasses.setVisibility(View.GONE);
                                lnNoData.setVisibility(View.VISIBLE);
                            }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });




    }


    public void getRefreshToken(JSONObject jsonObject) {
        String url=pref.getAPI()+"User/RefreshToken";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            JSONObject jobj=new JSONObject();
                            try {
                                jobj.put("tenantId",pref.getTenatID());
                                jobj.put("userId",0);
                                jobj.put("_tenantName",pref.getTenatName());
                                jobj.put("password",pref.getPasswordBase64());
                                jobj.put("email",pref.getLoginEmail());
                                jobj.put("schoolId",null);
                                jobj.put("_userName","");
                                jobj.put("_token",null);
                                jobj.put("isMobileLogin",true);
                                login(jobj);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            try {
                                jsonObject.put("staffId",pref.getUserID());
                                jsonObject.put("membershipId",pref.getMemberShipID());
                                jsonObject.put("allCourse",true);
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId",pref.getTenatID());
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                jsonObject.put("_academicYear",pref.getAcademicYear());
                                getDashboardItem(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }



    public void login(JSONObject jsonObject) {

        String url=pref.getAPI()+"User/ValidateLogin";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        if (_failure){


                            Toast.makeText(getContext(),"Invalid Login Credentials",Toast.LENGTH_LONG).show();

                        }else {


                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            try {
                                jsonObject.put("staffId",pref.getUserID());
                                jsonObject.put("membershipId",pref.getMemberShipID());
                                jsonObject.put("allCourse",true);
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId",pref.getTenatID());
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                jsonObject.put("_academicYear",pref.getAcademicYear());
                                getDashboardItem(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

}