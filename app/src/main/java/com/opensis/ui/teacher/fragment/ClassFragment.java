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
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.TeacherClassAdapter;
import com.opensis.adapter.TeacherDashboardClassAdapter;
import com.opensis.adapter.TeacherNoticeAdapter;
import com.opensis.adapter.TeacherSchoolAdapter;
import com.opensis.model.ClassStudentModel;
import com.opensis.model.TeacherClassesModel;
import com.opensis.model.TeacherNoticeModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;
import com.opensis.ui.teacher.activity.TeacherDashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DATE, 1);
        monthFrstDate= Util.changeAnyDateFormat(c.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd")+"T00:00:00";

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
        String twomonthsdate=Util.changeAnyDateFormat(calendar.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd");
        twomonthsDate=twomonthsdate+"T00:00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(twomonthsdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cd = Calendar.getInstance();
        cd.setTime(convertedDate);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));
        twoMonthsLastDate=Util.changeAnyDateFormat(cd.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd")+"T00:00:00";


        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        rvClasses=(RecyclerView)view.findViewById(R.id.rvClasses);
        rvClasses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        JSONObject obj=new JSONObject();
        JSONObject accessobj=new JSONObject();
        try {
            obj.put("tenantId",AppData.tenatID);
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

                            rvClasses.setVisibility(View.GONE);
                            lnNoData.setVisibility(View.VISIBLE);

                        }else {
                            JSONArray courseSectionViewList = response.optJSONArray("courseSectionViewList");
                            if (courseSectionViewList.length() > 0) {
                                for (int i = 0; i < courseSectionViewList.length(); i++) {
                                    JSONObject coursesectionOBJ = courseSectionViewList.optJSONObject(i);
                                    String courseSectionName = coursesectionOBJ.optString("courseSectionName");
                                    String courseGradeLevel = coursesectionOBJ.optString("courseGradeLevel");
                                    boolean attendanceTaken = coursesectionOBJ.optBoolean("attendanceTaken");
                                    String meetingDays = coursesectionOBJ.optString("meetingDays");
                                    String courseTitle = coursesectionOBJ.optString("courseTitle");
                                    String scheduleType=coursesectionOBJ.optString("scheduleType");
                                    JSONArray courseVariableSchedule = coursesectionOBJ.optJSONArray("courseVariableSchedule");
                                    if (courseVariableSchedule.length() > 0) {
                                        for (int j = 0; j < courseVariableSchedule.length(); j++) {
                                            JSONObject courseVariableScheduleOBJ = courseVariableSchedule.optJSONObject(i);
                                            JSONObject blockPeriod = courseVariableScheduleOBJ.optJSONObject("blockPeriod");
                                            time = blockPeriod.optString("periodStartTime");
                                            period = blockPeriod.optString("periodTitle");
                                            JSONObject rooms = courseVariableScheduleOBJ.optJSONObject("rooms");
                                            room = rooms.optString("title");

                                        }


                                    } else {
                                        JSONObject courseFixedSchedule = coursesectionOBJ.optJSONObject("courseFixedSchedule");
                                        JSONObject blockPeriod = courseFixedSchedule.optJSONObject("blockPeriod");
                                        time = blockPeriod.optString("periodStartTime");
                                        period = blockPeriod.optString("periodTitle");
                                        JSONObject rooms = courseFixedSchedule.optJSONObject("rooms");
                                        room = rooms.optString("title");
                                    }


                                    TeacherClassesModel classesModel = new TeacherClassesModel();
                                    classesModel.setClassName(courseSectionName);
                                    classesModel.setSubject(courseTitle);
                                    classesModel.setGrade(courseGradeLevel);
                                    classesModel.setAttendanceTaken(attendanceTaken);
                                    classesModel.setTime(time);
                                    classesModel.setPeriod(period);
                                    classesModel.setRoomNo(room);
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
                            Intent intent=new Intent(getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

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
                                jsonObject.put("tenantId",AppData.tenatID);
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
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