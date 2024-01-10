package com.opensissas.ui.teacher.fragment;

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
import com.opensissas.R;
import com.opensissas.adapter.TeacherScheduleAdapter;
import com.opensissas.model.ScheduleModel;
import com.opensissas.others.parser.PostJsonDataParser;
import com.opensissas.others.utility.AppData;
import com.opensissas.others.utility.Pref;
import com.opensissas.others.utility.Util;
import com.opensissas.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class ScheduleFragment extends Fragment implements View.OnClickListener {
    View view;
    RecyclerView rvSchedule;
    TextView tvDate;
    String currentDate,changeDate;
    LinearLayout lnForward,lnBackWard;
    Calendar  c;
    SimpleDateFormat df;
    TextView tvDay;
    boolean dateFlag;
    Pref pref;
    String period,room,strttime,endtime;
    ArrayList<ScheduleModel>scheduleList=new ArrayList<>();
    LinearLayout lnNoData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view= inflater.inflate(R.layout.fragment_schedule, container, false);
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        rvSchedule=(RecyclerView)view.findViewById(R.id.rvSchedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tvDate=(TextView) view.findViewById(R.id.tvDate);
        tvDay=(TextView)view.findViewById(R.id.tvDay);
         c= Calendar.getInstance();
        System.out.println("Current time => " + c);

         df = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = df.format(c.getTime());
        tvDate.setText(Util.changeAnyDateFormat(currentDate,"dd-MM-yyyy","MMM dd,yyyy"));
        lnForward=(LinearLayout) view.findViewById(R.id.lnForward);
        lnBackWard=(LinearLayout)view.findViewById(R.id.lnBackWard);
        lnBackWard.setOnClickListener(this);
        lnForward.setOnClickListener(this);



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


    @Override
    public void onClick(View v) {
        if (v==lnForward){
            c.add(Calendar.DATE, 1);
            changeDate = df.format(c.getTime());
            tvDate.setText(Util.changeAnyDateFormat(changeDate,"dd-MM-yyyy","MMM dd,yyyy"));
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("staffId",pref.getUserID());
                jsonObject.put("_tenantName",pref.getTenatName());
                jsonObject.put("_userName",pref.getName());
                jsonObject.put("_token",pref.getToken());
                jsonObject.put("tenantId", pref.getTenatID());
                jsonObject.put("schoolId", pref.getSchoolID());
                jsonObject.put("academicYear", pref.getAcademicYear());
                String date=Util.changeAnyDateFormat(changeDate,"dd-MM-yyyy","yyyy-MM-dd");
                getSchedule(jsonObject,date);
                //getSchoolList(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (changeDate.equals(currentDate)){
                tvDay.setText("Today");

            }else {
                tvDay.setText(Util.changeAnyDateFormat(changeDate,"dd-MM-yyyy","EEEE"));

            }

        }else if (v==lnBackWard){
            c.add(Calendar.DATE, -1);
            changeDate = df.format(c.getTime());
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("staffId",pref.getUserID());
                jsonObject.put("_tenantName",pref.getTenatName());
                jsonObject.put("_userName",pref.getName());
                jsonObject.put("_token",pref.getToken());
                jsonObject.put("tenantId", pref.getTenatID());
                jsonObject.put("schoolId", pref.getSchoolID());
                jsonObject.put("academicYear", pref.getAcademicYear());
                String date=Util.changeAnyDateFormat(changeDate,"dd-MM-yyyy","yyyy-MM-dd");
                getSchedule(jsonObject,date);
                //getSchoolList(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tvDate.setText(Util.changeAnyDateFormat(changeDate,"dd-MM-yyyy","MMM dd,yyyy"));
            if (changeDate.equals(currentDate)){
                tvDay.setText("Today");

            }else {
                tvDay.setText(Util.changeAnyDateFormat(changeDate,"dd-MM-yyyy","EEEE"));

            }
        }
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
                                jsonObject.put("_tenantName",pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId", pref.getTenatID());
                                jsonObject.put("schoolId", pref.getSchoolID());
                                jsonObject.put("academicYear", pref.getAcademicYear());
                                jsonObject.put("_academicYear", pref.getAcademicYear());
                                String date=Util.changeAnyDateFormat(currentDate,"dd-MM-yyyy","yyyy-MM-dd");
                                getSchedule(jsonObject,date);
                                //getSchoolList(jsonObject);
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

    public void getSchedule(JSONObject jsonObject,String currenDate) {
        rvSchedule.setVisibility(View.VISIBLE);
        lnNoData.setVisibility(View.GONE);
        String currentDay=Util.changeAnyDateFormat(currenDate,"yyyy-MM-dd","EEEE");

        String url=pref.getAPI()+"Staff/getScheduledCourseSectionsForStaff";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();

                        }else {
                            scheduleList.clear();;
                            JSONArray courseSectionViewList=response.optJSONArray("courseSectionViewList");
                            if (courseSectionViewList.length()>0){
                                for (int i=0;i<courseSectionViewList.length();i++){
                                    JSONObject coursesectionOBJ=courseSectionViewList.optJSONObject(i);
                                    String courseSectionName=coursesectionOBJ.optString("courseSectionName");
                                    String courseGradeLevel=coursesectionOBJ.optString("courseGradeLevel");
                                    String meetingDays=coursesectionOBJ.optString("meetingDays");
                                    String durationStartDate=coursesectionOBJ.optString("durationStartDate").replace("T00:00:00","");
                                    String durationEndDate=coursesectionOBJ.optString("durationEndDate").replace("T00:00:00","");
                                    JSONArray courseVariableSchedule=coursesectionOBJ.optJSONArray("courseVariableSchedule");
                                    if (courseVariableSchedule.length()>0){
                                        for (int j=0;j<courseVariableSchedule.length();j++){
                                            JSONObject courseVariableScheduleOBJ=courseVariableSchedule.optJSONObject(i);
                                            JSONObject blockPeriod=courseVariableScheduleOBJ.optJSONObject("blockPeriod");
                                            strttime=blockPeriod.optString("periodStartTime");
                                            endtime=blockPeriod.optString("periodEndTime");
                                            period=blockPeriod.optString("periodTitle");

                                        }

                                    }else {
                                        JSONObject courseFixedSchedule=coursesectionOBJ.optJSONObject("courseFixedSchedule");
                                        if (courseFixedSchedule!=null) {
                                            JSONObject blockPeriod = courseFixedSchedule.optJSONObject("blockPeriod");
                                            strttime = blockPeriod.optString("periodStartTime");
                                            endtime = blockPeriod.optString("periodEndTime");
                                            period = blockPeriod.optString("periodTitle");
                                        }

                                    }


                                    ScheduleModel scheduleModel=new ScheduleModel();
                                    scheduleModel.setSubject(courseSectionName);
                                    scheduleModel.setGrade(courseGradeLevel);
                                    scheduleModel.setTiming(strttime+" - "+endtime);
                                    scheduleModel.setPeriod(period);
                                    if (checkDate(durationStartDate,currenDate,durationEndDate)){
                                        if (meetingDays.contains(currentDay)){
                                            scheduleList.add(scheduleModel);
                                        }

                                    }



                                }

                                Collections.sort(scheduleList, new Comparator<ScheduleModel>() {
                                    @Override
                                    public int compare(ScheduleModel lhs, ScheduleModel rhs) {
                                        return lhs.getPeriod().compareTo(rhs.getPeriod());
                                    }
                                });

                                TeacherScheduleAdapter scheduleAdapter=new TeacherScheduleAdapter(scheduleList,getContext());
                                rvSchedule.setAdapter(scheduleAdapter);
                                if (scheduleList.size()>0){
                                    rvSchedule.setVisibility(View.VISIBLE);
                                    lnNoData.setVisibility(View.GONE);
                                }else {
                                    rvSchedule.setVisibility(View.GONE);
                                    lnNoData.setVisibility(View.VISIBLE);
                                }




                            }else {
                                rvSchedule.setVisibility(View.GONE);
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

}