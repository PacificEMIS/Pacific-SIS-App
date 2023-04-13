package com.opensis.ui.teacher.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.model.MArkinPeriodSpinnerModel;
import com.opensis.model.SpinnerModel;
import com.opensis.model.TeacherClassesModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;
import com.opensis.ui.teacher.fragment.ClassFragment;
import com.opensis.ui.teacher.fragment.MarkingPeriodsFragment;
import com.opensis.ui.teacher.fragment.ScheduleFragment;
import com.opensis.ui.teacher.fragment.SchoolFragment;
import com.opensis.ui.teacher.fragment.StudentsFragment;
import com.opensis.ui.teacher.fragment.TeacheHomeFragment;
import com.opensis.ui.teacher.fragment.TeacherCalenderFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TeacherDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout lnHome;
    private FragmentTransaction transaction;
    LinearLayout llHome,llMessage,llCourse,llSchedule,llReport;
    ImageView imgHomeNormal,imgMessageNormal,imgCourseNormal,imgScheduleNormal,imgReportNormal;
    LinearLayout lnHomeSelected,lnMessageSelected,lnCourseSelected,lnScheduleSelected,lnReportSelected;
    DrawerLayout dlMain;
    boolean mslideState;
    ImageView imgMenu;
    LinearLayout llsSchool,llsClass,llsStudent,llsDashboard,llsLogout,llsCalendar,llsSchedule;
    LinearLayout lnHeader,llsMarkingPeriod;
    TextView tvTeacherName,tvMemberShip;
    Pref pref;
    int currentMonth;
    ImageView imgTune;
    Spinner spSchool,spSchoolYear,spMarkingPeriod;
    ArrayList<SpinnerModel>mSchoolList=new ArrayList<>();
    ArrayList<String>schoolList=new ArrayList<>();
    LinearLayout lnSchoolTune,lnCLose;

    ArrayList<SpinnerModel>macademyYearList=new ArrayList<>();
    ArrayList<String>academyYearList=new ArrayList<>();
    ArrayList<String>strtDateList=new ArrayList<>();


    ArrayList<MArkinPeriodSpinnerModel>mmarkingperiodlist=new ArrayList<>();
    ArrayList<String>markingperiodList=new ArrayList<>();

    TextView tvSubmit;
    ImageView imgUser;
    String token,dateSchoolOpened;

    int monthGap;
    Date date,date1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        initView();
        ONCLICK();
    }

    private void initView(){
        pref=new Pref(TeacherDashboardActivity.this);
        token=getIntent().getStringExtra("token");
        ImageView imgTenantLogo=(ImageView)findViewById(R.id.imgTenantLogo);
        imgUser=(ImageView)findViewById(R.id.imgUser);
        if (!pref.getUserPhoto().equals("")) {
            byte[] decodedString = Base64.decode(pref.getUserPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgUser.setImageBitmap(decodedByte);
        }else {

        }
        byte[] decodedString = Base64.decode(pref.getLogo(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgTenantLogo.setImageBitmap(decodedByte);

        tvSubmit=(TextView)findViewById(R.id.tvSubmit);
        imgTune=(ImageView)findViewById(R.id.imgTune);
        llsSchedule=(LinearLayout)findViewById(R.id.llsSchedule);
        lnSchoolTune=(LinearLayout)findViewById(R.id.lnSchoolTune);
        llsCalendar=(LinearLayout)findViewById(R.id.llsCalendar);
        llsDashboard=(LinearLayout)findViewById(R.id.llsDashboard);
        llsLogout=(LinearLayout)findViewById(R.id.llsLogout);
        lnCLose=(LinearLayout)findViewById(R.id.lnCLose);
        spMarkingPeriod=(Spinner)findViewById(R.id.spMarkingPeriod);
        spSchoolYear=(Spinner)findViewById(R.id.spSchoolYear);
        spSchool=(Spinner)findViewById(R.id.spSchool);
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        currentMonth= Integer.parseInt(dateFormat.format(date));
        tvMemberShip=(TextView)findViewById(R.id.tvMemberShip);
        tvTeacherName=(TextView)findViewById(R.id.tvTeacherName);
        tvTeacherName.setText(pref.getMemberName());
        tvMemberShip.setText(pref.getMemberShip());
        lnHome=(LinearLayout)findViewById(R.id.lnHome);
        lnHeader=(LinearLayout)findViewById(R.id.lnHeader);
        dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        imgMenu=(ImageView)findViewById(R.id.imgMenu);
        llsCalendar.setOnClickListener(this);

        dlMain.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                mslideState = true;

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                mslideState = false;

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        llHome=(LinearLayout)findViewById(R.id.llHome);
        llMessage=(LinearLayout)findViewById(R.id.llMessage);
        llCourse=(LinearLayout)findViewById(R.id.llCourse);
        llSchedule=(LinearLayout)findViewById(R.id.llSchedule);
        llReport=(LinearLayout)findViewById(R.id.llReport);

        lnHomeSelected=(LinearLayout)findViewById(R.id.lnHomeSelected);
        lnMessageSelected=(LinearLayout)findViewById(R.id.lnMessageSelected);
        lnCourseSelected=(LinearLayout)findViewById(R.id.lnCourseSelected);
        lnScheduleSelected=(LinearLayout)findViewById(R.id.lnScheduleSelected);
        lnReportSelected=(LinearLayout)findViewById(R.id.lnReportSelected);

        imgHomeNormal=(ImageView)findViewById(R.id.imgHomeNormal);
        imgMessageNormal=(ImageView)findViewById(R.id.imgMessageNormal);
        imgCourseNormal=(ImageView)findViewById(R.id.imgCourseNormal);
        imgScheduleNormal=(ImageView)findViewById(R.id.imgScheduleNormal);
        imgReportNormal=(ImageView)findViewById(R.id.imgReportNormal);
        llsSchool=(LinearLayout)findViewById(R.id.llsSchool);
        llsClass=(LinearLayout)findViewById(R.id.llsClass);
        llsStudent=(LinearLayout)findViewById(R.id.llsStudent);
        llsMarkingPeriod=(LinearLayout)findViewById(R.id.llsMarkingPeriod);

        imgMenu.setOnClickListener(this);
        llsSchool.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llCourse.setOnClickListener(this);
        llsClass.setOnClickListener(this);
        llsStudent.setOnClickListener(this);
        llsMarkingPeriod.setOnClickListener(this);
        imgTune.setOnClickListener(this);
        lnCLose.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        llsDashboard.setOnClickListener(this);
        llsLogout.setOnClickListener(this);
        llsSchedule.setOnClickListener(this);
        llSchedule.setOnClickListener(this);


        JSONObject schoolOBJ=new JSONObject();
        try {
            schoolOBJ.put("emailAddress",pref.getEmail());
            schoolOBJ.put("_tenantName",pref.getTenatName());
            schoolOBJ.put("_userName",pref.getName());
            schoolOBJ.put("_token",pref.getToken());
            schoolOBJ.put("tenantId",AppData.tenatID);
            schoolOBJ.put("schoolId",pref.getSchoolID());
            getSchool(schoolOBJ);
        } catch (JSONException e) {
            e.printStackTrace();
        }





        /* if (pref.getSplashFlag().equals("1")){*/


         /*}else {
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
         }*/


        



    }

    private void viewHomeFragment(){

        TeacheHomeFragment fragment = new TeacheHomeFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnHome, fragment, "dashboard");
        transaction.commit();

        lnHomeSelected.setVisibility(View.VISIBLE);
        lnMessageSelected.setVisibility(View.GONE);
        lnCourseSelected.setVisibility(View.GONE);
        lnScheduleSelected.setVisibility(View.GONE);
        lnReportSelected.setVisibility(View.GONE);

        imgHomeNormal.setVisibility(View.GONE);
        imgMessageNormal.setVisibility(View.VISIBLE);
        imgCourseNormal.setVisibility(View.VISIBLE);
        imgScheduleNormal.setVisibility(View.VISIBLE);
        imgReportNormal.setVisibility(View.VISIBLE);

        lnHeader.setVisibility(View.VISIBLE);






    }

    private void viewSchoolFragment(){

        SchoolFragment fragment = new SchoolFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnHome, fragment, "school");
        transaction.commit();

        lnHomeSelected.setVisibility(View.GONE);
        lnMessageSelected.setVisibility(View.GONE);
        lnCourseSelected.setVisibility(View.GONE);
        lnScheduleSelected.setVisibility(View.GONE);
        lnReportSelected.setVisibility(View.GONE);

        imgHomeNormal.setVisibility(View.VISIBLE);
        imgMessageNormal.setVisibility(View.VISIBLE);
        imgCourseNormal.setVisibility(View.VISIBLE);
        imgScheduleNormal.setVisibility(View.VISIBLE);
        imgReportNormal.setVisibility(View.VISIBLE);

        lnHeader.setVisibility(View.GONE);

    }

    private void viewClassesFragment(){

        ClassFragment fragment = new ClassFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnHome, fragment, "classes");
        transaction.commit();

        lnHomeSelected.setVisibility(View.GONE);
        lnMessageSelected.setVisibility(View.GONE);
        lnCourseSelected.setVisibility(View.VISIBLE);
        lnScheduleSelected.setVisibility(View.GONE);
        lnReportSelected.setVisibility(View.GONE);

        imgHomeNormal.setVisibility(View.VISIBLE);
        imgMessageNormal.setVisibility(View.VISIBLE);
        imgCourseNormal.setVisibility(View.GONE);
        imgScheduleNormal.setVisibility(View.VISIBLE);
        imgReportNormal.setVisibility(View.VISIBLE);

        lnHeader.setVisibility(View.GONE);

    }

    private void viewStudentFragment(){

        StudentsFragment fragment = new StudentsFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnHome, fragment, "students");
        transaction.commit();

        lnHomeSelected.setVisibility(View.GONE);
        lnMessageSelected.setVisibility(View.GONE);
        lnCourseSelected.setVisibility(View.GONE);
        lnScheduleSelected.setVisibility(View.GONE);
        lnReportSelected.setVisibility(View.GONE);

        imgHomeNormal.setVisibility(View.VISIBLE);
        imgMessageNormal.setVisibility(View.VISIBLE);
        imgCourseNormal.setVisibility(View.VISIBLE);
        imgScheduleNormal.setVisibility(View.VISIBLE);
        imgReportNormal.setVisibility(View.VISIBLE);

        lnHeader.setVisibility(View.GONE);

    }

    private void viewCalendarFragment(){

        TeacherCalenderFragment fragment = new TeacherCalenderFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnHome, fragment, "calendar");
        transaction.commit();

        lnHomeSelected.setVisibility(View.GONE);
        lnMessageSelected.setVisibility(View.GONE);
        lnCourseSelected.setVisibility(View.GONE);
        lnScheduleSelected.setVisibility(View.GONE);
        lnReportSelected.setVisibility(View.GONE);

        imgHomeNormal.setVisibility(View.VISIBLE);
        imgMessageNormal.setVisibility(View.VISIBLE);
        imgCourseNormal.setVisibility(View.VISIBLE);
        imgScheduleNormal.setVisibility(View.VISIBLE);
        imgReportNormal.setVisibility(View.VISIBLE);

        lnHeader.setVisibility(View.GONE);

    }

    private void viewScheduleFragment(){

        ScheduleFragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnHome, fragment, "calendar");
        transaction.commit();

        lnHomeSelected.setVisibility(View.GONE);
        lnMessageSelected.setVisibility(View.GONE);
        lnCourseSelected.setVisibility(View.GONE);
        lnScheduleSelected.setVisibility(View.VISIBLE);
        lnReportSelected.setVisibility(View.GONE);

        imgHomeNormal.setVisibility(View.VISIBLE);
        imgMessageNormal.setVisibility(View.VISIBLE);
        imgCourseNormal.setVisibility(View.VISIBLE);
        imgScheduleNormal.setVisibility(View.GONE);
        imgReportNormal.setVisibility(View.VISIBLE);

        lnHeader.setVisibility(View.GONE);

    }

    private void viewMarkingPeriodsFragment(){

        MarkingPeriodsFragment fragment = new MarkingPeriodsFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnHome, fragment, "markingperiods");
        transaction.commit();

        lnHomeSelected.setVisibility(View.GONE);
        lnMessageSelected.setVisibility(View.GONE);
        lnCourseSelected.setVisibility(View.GONE);
        lnScheduleSelected.setVisibility(View.GONE);
        lnReportSelected.setVisibility(View.GONE);

        imgHomeNormal.setVisibility(View.VISIBLE);
        imgMessageNormal.setVisibility(View.VISIBLE);
        imgCourseNormal.setVisibility(View.VISIBLE);
        imgScheduleNormal.setVisibility(View.VISIBLE);
        imgReportNormal.setVisibility(View.VISIBLE);

        lnHeader.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View view) {
        if (view==imgMenu){
            dlMain.openDrawer(Gravity.LEFT);
        }else if (view==llsSchool){
            viewSchoolFragment();
            dlMain.closeDrawer(Gravity.LEFT);
        }else if (view==llHome){
            viewHomeFragment();
        }else if (view==llCourse){
            viewClassesFragment();
        }else if (view==llsClass){
            dlMain.closeDrawer(Gravity.LEFT);
            viewClassesFragment();
        }else if (view==llsStudent){
            dlMain.closeDrawer(Gravity.LEFT);
            viewStudentFragment();
        }else if (view==llsMarkingPeriod){
            dlMain.closeDrawer(Gravity.LEFT);
            viewMarkingPeriodsFragment();
        }else if (view==imgTune){
            lnSchoolTune.setVisibility(View.VISIBLE);
        }else if (view==lnCLose){
            lnSchoolTune.setVisibility(View.GONE);
        }else if (view==tvSubmit){
            lnSchoolTune.setVisibility(View.GONE);
            viewHomeFragment();
        }else if (view==llsDashboard){
            dlMain.closeDrawer(Gravity.LEFT);
            viewHomeFragment();
        }else if (view==llsLogout){
            dlMain.closeDrawer(Gravity.LEFT);
            Intent intent=new Intent(TeacherDashboardActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            pref.saveName("");
        }else if (view==llsCalendar){
            dlMain.closeDrawer(Gravity.LEFT);
            viewCalendarFragment();
        }else if (view==llsSchedule){
            dlMain.closeDrawer(Gravity.LEFT);
            viewScheduleFragment();
        }else if (view==llSchedule){
            viewScheduleFragment();
        }
    }

    public void getPeriodList(JSONObject jsonObject) {

        String url=pref.getAPI()+"MarkingPeriod/getMarkingPeriodTitleList";


        new PostJsonDataParser(TeacherDashboardActivity.this, Request.Method.POST,url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){



                        }else {
                            int academicYear= response.optInt("academicYear");

                            JSONArray period = response.optJSONArray("period");
                            if (period.length() > 0) {
                                for (int i=0;i<period.length();i++){
                                    JSONObject obj=period.optJSONObject(i);
                                    int markingPeriodId=obj.optInt("markingPeriodId");
                                    String periodTitle=obj.optString("periodTitle");
                                    String startDate=obj.optString("startDate");
                                    String endDate=obj.optString("endDate");
                                    markingperiodList.add(periodTitle);

                                    MArkinPeriodSpinnerModel spModel=new MArkinPeriodSpinnerModel(periodTitle,startDate,endDate,markingPeriodId);
                                    mmarkingperiodlist.add(spModel);
                                }

                                ArrayAdapter ad
                                        = new ArrayAdapter(
                                        TeacherDashboardActivity.this,
                                        android.R.layout.simple_spinner_item,
                                        markingperiodList);

                                // set simple layout resource file
                                // for each item of spinner
                                ad.setDropDownViewResource(
                                        android.R.layout
                                                .simple_spinner_dropdown_item);

                                // Set the ArrayAdapter (ad) data on the
                                // Spinner which binds data to spinner
                                spMarkingPeriod.setAdapter(ad);

                                if (monthGap>10) {


                                    if (currentMonth == 1 || currentMonth == 11 || currentMonth == 12) {
                                        JSONObject OBJ = period.optJSONObject(1);
                                        spMarkingPeriod.setSelection(1);
                                        int markingPeriodId = OBJ.optInt("markingPeriodId");
                                        pref.savePeriodID(markingPeriodId);
                                        String periodTitle = OBJ.optString("periodTitle");
                                        String startDate = OBJ.optString("startDate");
                                        String endDate = OBJ.optString("endDate");
                                        pref.savePeriodStartYear(startDate);
                                        pref.savePeriodEndYear(endDate);
                                    } else if (currentMonth == 8 || currentMonth == 9 || currentMonth == 10) {
                                        JSONObject OBJ = period.optJSONObject(0);
                                        spMarkingPeriod.setSelection(0);
                                        int markingPeriodId = OBJ.optInt("markingPeriodId");
                                        pref.savePeriodID(markingPeriodId);
                                        String periodTitle = OBJ.optString("periodTitle");
                                        String startDate = OBJ.optString("startDate");
                                        String endDate = OBJ.optString("endDate");
                                        pref.savePeriodStartYear(startDate);
                                        pref.savePeriodEndYear(endDate);
                                    } else if (currentMonth == 2 || currentMonth == 3 || currentMonth == 4) {
                                        JSONObject OBJ = period.optJSONObject(2);
                                        spMarkingPeriod.setSelection(2);
                                        int markingPeriodId = OBJ.optInt("markingPeriodId");
                                        pref.savePeriodID(markingPeriodId);
                                        String periodTitle = OBJ.optString("periodTitle");
                                        String startDate = OBJ.optString("startDate");
                                        String endDate = OBJ.optString("endDate");
                                        pref.savePeriodStartYear(startDate);
                                        pref.savePeriodEndYear(endDate);
                                    } else {
                                        JSONObject OBJ = period.optJSONObject(3);
                                        spMarkingPeriod.setSelection(3);
                                        int markingPeriodId = OBJ.optInt("markingPeriodId");
                                        pref.savePeriodID(markingPeriodId);
                                        String periodTitle = OBJ.optString("periodTitle");
                                        String startDate = OBJ.optString("startDate");
                                        String endDate = OBJ.optString("endDate");
                                        pref.savePeriodStartYear(startDate);
                                        pref.savePeriodEndYear(endDate);


                                    }
                                }else {
                                    if (currentMonth == 10 || currentMonth == 11 ) {
                                        JSONObject OBJ = period.optJSONObject(1);
                                        spMarkingPeriod.setSelection(1);
                                        int markingPeriodId = OBJ.optInt("markingPeriodId");
                                        pref.savePeriodID(markingPeriodId);
                                        String periodTitle = OBJ.optString("periodTitle");
                                        String startDate = OBJ.optString("startDate");
                                        String endDate = OBJ.optString("endDate");
                                        pref.savePeriodStartYear(startDate);
                                        pref.savePeriodEndYear(endDate);
                                    } else if (currentMonth == 8 || currentMonth == 9 ) {
                                        JSONObject OBJ = period.optJSONObject(0);
                                        spMarkingPeriod.setSelection(0);
                                        int markingPeriodId = OBJ.optInt("markingPeriodId");
                                        pref.savePeriodID(markingPeriodId);
                                        String periodTitle = OBJ.optString("periodTitle");
                                        String startDate = OBJ.optString("startDate");
                                        String endDate = OBJ.optString("endDate");
                                        pref.savePeriodStartYear(startDate);
                                        pref.savePeriodEndYear(endDate);
                                    } else if (currentMonth == 1 || currentMonth == 2 ) {
                                        JSONObject OBJ = period.optJSONObject(2);
                                        spMarkingPeriod.setSelection(2);
                                        int markingPeriodId = OBJ.optInt("markingPeriodId");
                                        pref.savePeriodID(markingPeriodId);
                                        String periodTitle = OBJ.optString("periodTitle");
                                        String startDate = OBJ.optString("startDate");
                                        String endDate = OBJ.optString("endDate");
                                        pref.savePeriodStartYear(startDate);
                                        pref.savePeriodEndYear(endDate);
                                    } else {
                                        JSONObject OBJ = period.optJSONObject(3);
                                        spMarkingPeriod.setSelection(3);
                                        int markingPeriodId = OBJ.optInt("markingPeriodId");
                                        pref.savePeriodID(markingPeriodId);
                                        String periodTitle = OBJ.optString("periodTitle");
                                        String startDate = OBJ.optString("startDate");
                                        String endDate = OBJ.optString("endDate");
                                        pref.savePeriodStartYear(startDate);
                                        pref.savePeriodEndYear(endDate);

                                    }
                                    }
                                viewHomeFragment();

                            }else {

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


        new PostJsonDataParser(TeacherDashboardActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Intent intent=new Intent(TeacherDashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }else {
                           String _token=response.optString("_token");
                           pref.saveToken(_token);
                            JSONObject schoolOBJ=new JSONObject();
                            schoolOBJ.put("_tenantName",pref.getTenatName());
                            schoolOBJ.put("_userName",pref.getName());
                            schoolOBJ.put("_token",_token);
                            schoolOBJ.put("tenantId",AppData.tenatID);
                            schoolOBJ.put("schoolId",pref.getSchoolID());
                            getAcademyYearList(schoolOBJ);
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getSchool(JSONObject jsonObject) {
        String url=pref.getAPI()+"School/getAllSchools";


        new PostJsonDataParser(TeacherDashboardActivity.this, Request.Method.POST, url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Intent intent=new Intent(TeacherDashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }else {
                           JSONArray getSchoolForView=response.optJSONArray("getSchoolForView");
                           if (getSchoolForView.length()>0){
                               for (int i=0;i<getSchoolForView.length();i++){
                                   JSONObject schoolOBJ=getSchoolForView.optJSONObject(i);
                                   int schoolId=schoolOBJ.optInt("schoolId");
                                   String schoolName=schoolOBJ.optString("schoolName");
                                   schoolList.add(schoolName);
                                   SpinnerModel spModel=new SpinnerModel(schoolName,String.valueOf(schoolId));
                                   mSchoolList.add(spModel);
                               }

                               JSONObject schoolOBJ=getSchoolForView.optJSONObject(0);
                                dateSchoolOpened=schoolOBJ.optString("dateSchoolOpened");

                               ArrayAdapter ad
                                       = new ArrayAdapter(
                                       TeacherDashboardActivity.this,
                                       android.R.layout.simple_spinner_item,
                                       schoolList);

                               // set simple layout resource file
                               // for each item of spinner
                               ad.setDropDownViewResource(
                                       android.R.layout
                                               .simple_spinner_dropdown_item);

                               // Set the ArrayAdapter (ad) data on the
                               // Spinner which binds data to spinner
                               spSchool.setAdapter(ad);
                           }

                            JSONObject jsonObject=new JSONObject();
                            JSONObject schoolOBJ=new JSONObject();
                            try {
                                schoolOBJ.put("_tenantName",pref.getTenatName());
                                schoolOBJ.put("_userName",pref.getName());
                                schoolOBJ.put("_token",token);
                                schoolOBJ.put("tenantId",AppData.tenatID);
                                schoolOBJ.put("schoolId",pref.getSchoolID());
                                getAcademyYearList(schoolOBJ);
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

    public void getAcademyYearList(JSONObject jsonObject) {
        String url=pref.getAPI()+"MarkingPeriod/getAcademicYearList";


        new PostJsonDataParser(TeacherDashboardActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            if (_message.contains("Valid")){
                                JSONObject obj=new JSONObject();
                                JSONObject accessobj=new JSONObject();
                                try {
                                    obj.put("tenantId",AppData.tenatID);
                                    obj.put("userId",0);
                                    obj.put("userAccessLog",accessobj);
                                    obj.put("_tenantName", pref.getTenatName());
                                    obj.put("_userName", pref.getName());
                                    obj.put("_token", token);
                                    obj.put("schoolId", pref.getSchoolID());
                                    obj.put("email", pref.getEmail());
                                    getRefreshToken(obj);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                               Intent intent=new Intent(TeacherDashboardActivity.this,LoginActivity.class);
                               startActivity(intent);
                               finish();
                            }


                        }else {
                            JSONArray academicYears=response.optJSONArray("academicYears");
                            if (academicYears.length()>0){
                                for (int i=0;i<academicYears.length();i++){
                                    JSONObject academicYearsOBJ=academicYears.optJSONObject(i);
                                    String academyYear= String.valueOf(academicYearsOBJ.optDouble("academyYear"));
                                    String year=academicYearsOBJ.optString("year");
                                    academyYearList.add(year);
                                    String startDate=academicYearsOBJ.optString("startDate");
                                    strtDateList.add(startDate);
                                    SpinnerModel spModel=new SpinnerModel(year,academyYear);
                                    macademyYearList.add(spModel);
                                }

                                int pos=strtDateList.indexOf(dateSchoolOpened);

                                JSONObject academicYearsOBJ=academicYears.optJSONObject(pos);
                                int academyYear= academicYearsOBJ.optInt("academyYear");
                                pref.saveAcademicYear(academyYear);
                                String startDate=academicYearsOBJ.optString("startDate").replaceAll("T00:00:00","");
                                String endDate=academicYearsOBJ.optString("endDate").replaceAll("T00:00:00","");
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                     date = format.parse(startDate);
                                    date1 = format.parse(endDate);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                monthGap=monthsBetweenDates(date,date1);
                                Log.d("monthGap", String.valueOf(monthGap));
                                String academicYear= String.valueOf(academicYearsOBJ.optDouble("academyYear"));
                                pref.saveAcademyYear(academicYear);
                                ArrayAdapter ad
                                        = new ArrayAdapter(
                                        TeacherDashboardActivity.this,
                                        android.R.layout.simple_spinner_item,
                                        academyYearList);

                                // set simple layout resource file
                                // for each item of spinner
                                ad.setDropDownViewResource(
                                        android.R.layout
                                                .simple_spinner_dropdown_item);

                                // Set the ArrayAdapter (ad) data on the
                                // Spinner which binds data to spinner
                                spSchoolYear.setAdapter(ad);
                                spSchoolYear.setSelection(pos);

                                try {
                                    jsonObject.put("academicYear",academyYear);
                                    jsonObject.put("_tenantName",pref.getTenatName());
                                    jsonObject.put("_userName",pref.getName());
                                    jsonObject.put("_token",pref.getToken());
                                    jsonObject.put("tenantId",AppData.tenatID);
                                    jsonObject.put("schoolId",pref.getSchoolID());
                                    getPeriodList(jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void ONCLICK(){
        spSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int schoolID= Integer.parseInt(mSchoolList.get(i).getItemID());
                pref.saveSchoolID(schoolID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spSchoolYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String schoolYR= macademyYearList.get(i).getItemID();
                pref.saveAcademyYear(schoolYR);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMarkingPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String startDate= mmarkingperiodlist.get(i).getStartDate();
                String enddate= mmarkingperiodlist.get(i).getEndDate();
                int periodID=mmarkingperiodlist.get(i).getPeriodID();
                pref.savePeriodStartYear(startDate);
                pref.savePeriodEndYear(enddate);
                pref.savePeriodID(periodID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public int monthsBetweenDates(Date startDate, Date endDate){

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int monthsBetween = 0;
        int dateDiff = end.get(Calendar.DAY_OF_MONTH)-start.get(Calendar.DAY_OF_MONTH);

        if(dateDiff<0) {
            int borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);
            dateDiff = (end.get(Calendar.DAY_OF_MONTH)+borrrow)-start.get(Calendar.DAY_OF_MONTH);
            monthsBetween--;

            if(dateDiff>0) {
                monthsBetween++;
            }
        }
        else {
            monthsBetween++;
        }
        monthsBetween += end.get(Calendar.MONTH)-start.get(Calendar.MONTH);
        monthsBetween  += (end.get(Calendar.YEAR)-start.get(Calendar.YEAR))*12;
        return monthsBetween;
    }
}