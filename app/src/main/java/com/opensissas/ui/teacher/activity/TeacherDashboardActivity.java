package com.opensissas.ui.teacher.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensissas.R;
import com.opensissas.model.MArkinPeriodSpinnerModel;
import com.opensissas.model.SpinnerMixedModel;
import com.opensissas.model.SpinnerModel;
import com.opensissas.others.parser.PostJsonDataParser;
import com.opensissas.others.utility.AppData;
import com.opensissas.others.utility.Pref;
import com.opensissas.ui.common.activity.LoginActivity;
import com.opensissas.ui.teacher.fragment.ClassFragment;
import com.opensissas.ui.teacher.fragment.MarkingPeriodsFragment;
import com.opensissas.ui.teacher.fragment.ScheduleFragment;
import com.opensissas.ui.teacher.fragment.SchoolFragment;
import com.opensissas.ui.teacher.fragment.StudentsFragment;
import com.opensissas.ui.teacher.fragment.TeacheHomeFragment;
import com.opensissas.ui.teacher.fragment.TeacherCalenderFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    ArrayList<String>schoolOpenedList=new ArrayList<>();
    LinearLayout lnSchoolTune,lnCLose;

    ArrayList<SpinnerMixedModel>macademyYearList=new ArrayList<>();
    ArrayList<String>academyYearList=new ArrayList<>();
    ArrayList<String>strtDateList=new ArrayList<>();


    ArrayList<MArkinPeriodSpinnerModel>mmarkingperiodlist=new ArrayList<>();
    ArrayList<String>markingperiodList=new ArrayList<>();

    TextView tvSubmit;
    ImageView imgUser;
    String token,dateSchoolOpened;

    int monthGap;
    Date date,date1;
    ImageView fabCamera;
    AlertDialog alerDialog1;

    private final int PERMISSION_CODE_CAM = 100;
    private final int PERMISSION_CODE = 200;
    private Uri filepath = null;
    private static final int CAMERA_REQUEST = 1;
    private static final int REQUEST_GALLERY_CODE = 200;
    private Uri imageUri, uri;
    File file;
    String encodedImage;

    Calendar  c;
    SimpleDateFormat df;
    String currentDate;
    ArrayList<Integer>periodIDlIst=new ArrayList<>();
    ArrayList<String>startDateList=new ArrayList<>();
    ArrayList<String>endDateList=new ArrayList<>();
    ArrayList<String>titleList=new ArrayList<>();
    String strtDate;
    String periodTitle,school;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        initView();
        ONCLICK();
    }

    private void initView(){
        school=getIntent().getStringExtra("school");
        pref=new Pref(TeacherDashboardActivity.this);
        fabCamera=(ImageView)findViewById(R.id.fabCamera);
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
        fabCamera.setOnClickListener(this);

        c= Calendar.getInstance();
        System.out.println("Current time => " + c);

        df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(c.getTime());


        JSONObject schoolOBJ=new JSONObject();
        try {
            schoolOBJ.put("emailAddress",pref.getEmail());
            schoolOBJ.put("_tenantName",pref.getTenatName());
            schoolOBJ.put("_userName",pref.getName());
            schoolOBJ.put("_token",pref.getToken());
            schoolOBJ.put("tenantId",pref.getTenatID());
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
        }else if (view==fabCamera){
            camerapopUp();
        }
    }

    public void getPeriodList(JSONObject jsonObject) {

        String url=pref.getAPI()+"MarkingPeriod/getMarkingPeriodTitleList";


        new PostJsonDataParser(TeacherDashboardActivity.this, Request.Method.POST,url,jsonObject, false,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());
                    markingperiodList.clear();
                    mmarkingperiodlist.clear();
                    periodIDlIst.clear();
                    startDateList.clear();
                    endDateList.clear();
                    titleList.clear();
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
                                     String periodTitlee=obj.optString("periodTitle");
                                    String startDate=obj.optString("startDate");
                                    String endDate=obj.optString("endDate");
                                    markingperiodList.add(periodTitlee);
                                    periodIDlIst.add(markingPeriodId);
                                    startDateList.add(startDate);
                                    endDateList.add(endDate);
                                    titleList.add(periodTitlee);
                                    MArkinPeriodSpinnerModel spModel=new MArkinPeriodSpinnerModel(periodTitlee,startDate,endDate,markingPeriodId);
                                    mmarkingperiodlist.add(spModel);
                                    if (checkDate(startDate.replace("T00:00:00",""),currentDate,endDate.replace("T00:00:00",""))){
                                        periodTitle=obj.optString("periodTitle");
                                    }
                                }


                                int pos=markingperiodList.indexOf(periodTitle);



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
                                    spMarkingPeriod.setSelection(pos);


                                    pref.savePeriodID(periodIDlIst.get(pos));
                                    pref.savePeriodStartYear(startDateList.get(pos));
                                    pref.savePeriodEndYear(endDateList.get(pos));

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
                            schoolOBJ.put("tenantId",pref.getTenatID());
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
                                   dateSchoolOpened=schoolOBJ.optString("dateSchoolOpened");
                                   String dateSchoolClosed=schoolOBJ.optString("dateSchoolClosed");
                                   schoolList.add(schoolName);
                                   SpinnerModel spModel=new SpinnerModel(schoolName,String.valueOf(schoolId));
                                   mSchoolList.add(spModel);


                               }

                               int pos=schoolList.indexOf(school);





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
                               spSchool.setSelection(pos);
                           }

                            JSONObject jsonObject=new JSONObject();
                            JSONObject schoolOBJ=new JSONObject();
                            try {
                                schoolOBJ.put("_tenantName",pref.getTenatName());
                                schoolOBJ.put("_userName",pref.getName());
                                schoolOBJ.put("_token",token);
                                schoolOBJ.put("tenantId",pref.getTenatID());
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
                    academyYearList.clear();
                    macademyYearList.clear();

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            if (_message.contains("Valid")){
                                JSONObject obj=new JSONObject();
                                JSONObject accessobj=new JSONObject();
                                try {
                                    obj.put("tenantId",pref.getTenatID());
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
                                    int academyYear= academicYearsOBJ.optInt("academyYear");
                                    String year=academicYearsOBJ.optString("year");
                                    academyYearList.add(year);
                                    String startDate=academicYearsOBJ.optString("startDate");
                                    strtDateList.add(startDate);
                                    String endDate=academicYearsOBJ.optString("endDate");
                                    if (checkDate(startDate.replace("T00:00:00",""),currentDate,endDate.replace("T00:00:00",""))){
                                        strtDate=academicYearsOBJ.optString("startDate");


                                    }

                                    SpinnerMixedModel spModel=new SpinnerMixedModel(year,academyYear);
                                    macademyYearList.add(spModel);
                                }

                                int pos=strtDateList.indexOf(strtDate);

                                JSONObject academicYearsOBJ=academicYears.optJSONObject(pos);
                                int academyYear= academicYearsOBJ.optInt("academyYear");
                                pref.saveAcademicYear(academyYear);

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
                                    jsonObject.put("tenantId",pref.getTenatID());
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


                JSONObject schoolOBJ=new JSONObject();
                try {
                    schoolOBJ.put("_tenantName",pref.getTenatName());
                    schoolOBJ.put("_userName",pref.getName());
                    schoolOBJ.put("_token",pref.getToken());
                    schoolOBJ.put("tenantId",pref.getTenatID());
                    schoolOBJ.put("schoolId",pref.getSchoolID());
                    getAcademyYearList(schoolOBJ);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject=new JSONObject();

                try {
                    jsonObject.put("academicYear",pref.getAcademicYear());
                    jsonObject.put("_tenantName",pref.getTenatName());
                    jsonObject.put("_userName",pref.getName());
                    jsonObject.put("_token",pref.getToken());
                    jsonObject.put("tenantId",pref.getTenatID());
                    jsonObject.put("schoolId",pref.getSchoolID());
                    getPeriodList(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spSchoolYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int schoolYR= macademyYearList.get(i).getItemID();
                pref.saveAcademicYear(schoolYR);
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("academicYear",pref.getAcademicYear());
                    jsonObject.put("_tenantName",pref.getTenatName());
                    jsonObject.put("_userName",pref.getName());
                    jsonObject.put("_token",pref.getToken());
                    jsonObject.put("tenantId",pref.getTenatID());
                    jsonObject.put("schoolId",pref.getSchoolID());
                    getPeriodList(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


    private void camerapopUp() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TeacherDashboardActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.picture_popup, null);
        dialogBuilder.setView(dialogView);
        LinearLayout lnTakeCamera = (LinearLayout) dialogView.findViewById(R.id.lnTakeCamera);
        LinearLayout lnGallery = (LinearLayout) dialogView.findViewById(R.id.lnGallery);
        lnTakeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTakePermissions();
            }
        });
        lnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissions();
            }
        });


        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }


    private void checkTakePermissions() {
        int cameraPermission = ActivityCompat.checkSelfPermission(TeacherDashboardActivity.this, Manifest.permission.CAMERA);
        int storagePermission = ActivityCompat.checkSelfPermission(TeacherDashboardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (cameraPermission != PackageManager.PERMISSION_GRANTED || storagePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TeacherDashboardActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_CAM);
            } else {
                cameraIntent();
            }
        } else {
            cameraIntent();

        }
    }

    private void checkPermissions() {
        int cameraPermission = ActivityCompat.checkSelfPermission(TeacherDashboardActivity.this, Manifest.permission.CAMERA);
        int storagePermission = ActivityCompat.checkSelfPermission(TeacherDashboardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (cameraPermission != PackageManager.PERMISSION_GRANTED ||
                    storagePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TeacherDashboardActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
            } else {

                galleryIntent();
            }
        } else {
            galleryIntent();

        }
    }

    private void cameraIntent() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    private String getRealPathFromURIPath(Uri contentURI) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentURI, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Bitmap cropToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width) ? height - (height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0) ? 0 : cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0) ? 0 : cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);
        return cropImg;
    }

    private void galleryIntent() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0) {
                //from gallery pick permission
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {

                    //  startActivityForResult(getTakeImageChooserIntent(), 200);
                }
            }
        } else if (requestCode == PERMISSION_CODE_CAM && grantResults.length > 0) {
            //from camera take permission
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                cameraIntent();
            } else {

            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:

                if (resultCode == Activity.RESULT_OK) {
                    try {
                        try {

                            //messageAlert();
                            String imageurl = /*"file://" +*/ getRealPathFromURIPath(imageUri);
                            file = new File(imageurl);
                            alerDialog1.dismiss();

                            BitmapFactory.Options o = new BitmapFactory.Options();
                            o.inSampleSize = 2;
                            Bitmap bm = cropToSquare(BitmapFactory.decodeFile(imageurl, o));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("staffId",pref.getUserID());
                            jsonObject.put("staffPhoto",encodedImage);
                            jsonObject.put("staffThumbnailPhoto",encodedImage);
                            jsonObject.put("tenantId",pref.getTenatID());
                            jsonObject.put("updatedBy","3d37d665-24dc-43e4-8d68-407b6d473d19");
                            JSONObject dataOBJ=new JSONObject();
                            dataOBJ.put("staffMaster",jsonObject);
                            dataOBJ.put("_tenantName",pref.getTenatName());
                            dataOBJ.put("_userName",pref.getName());
                            dataOBJ.put("_token",pref.getToken());
                            dataOBJ.put("tenantId",pref.getTenatID());
                            dataOBJ.put("schoolId",pref.getSchoolID());
                            addTeacherProfile(dataOBJ);



                            // al2.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }

                }
                break;
            case REQUEST_GALLERY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    InputStream imageStream = null;
                    try {
                        try {
                            uri = data.getData();
                            String filePath = getRealPathFromURIPath(uri);
                            file = new File(filePath);
                            alerDialog1.dismiss();
                            imageStream = getContentResolver().openInputStream(uri);
                            Bitmap bm = cropToSquare(BitmapFactory.decodeStream(imageStream));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("staffId",pref.getUserID());
                            jsonObject.put("staffPhoto",encodedImage);
                            jsonObject.put("staffThumbnailPhoto",encodedImage);
                            jsonObject.put("tenantId",pref.getTenatID());
                            jsonObject.put("updatedBy","3d37d665-24dc-43e4-8d68-407b6d473d19");
                            JSONObject dataOBJ=new JSONObject();
                            dataOBJ.put("staffMaster",jsonObject);
                            dataOBJ.put("_tenantName",pref.getTenatName());
                            dataOBJ.put("_userName",pref.getName());
                            dataOBJ.put("_token",pref.getToken());
                            dataOBJ.put("tenantId",pref.getTenatID());
                            dataOBJ.put("schoolId",pref.getSchoolID());
                            addTeacherProfile(dataOBJ);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }

                }
                break;


        }
    }


    public void addTeacherProfile(JSONObject jsonObject) {

        String url=pref.getAPI()+"Staff/addUpdateStaffPhoto";


        new PostJsonDataParser(TeacherDashboardActivity.this, Request.Method.PUT,url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){



                        }else {

                            Toast.makeText(TeacherDashboardActivity.this,_message,Toast.LENGTH_LONG).show();
                            pref.saveUserPhoto(encodedImage);
                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imgUser.setImageBitmap(decodedByte);
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