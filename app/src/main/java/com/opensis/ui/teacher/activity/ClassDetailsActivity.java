package com.opensis.ui.teacher.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.model.ClassAssignmentModel;
import com.opensis.model.StudentsModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;
import com.opensis.ui.teacher.fragment.AssignmentsFragment;
import com.opensis.ui.teacher.fragment.ClassAttendanceFragment;
import com.opensis.ui.teacher.fragment.ClassOverViewFragment;
import com.opensis.ui.teacher.fragment.ClassStudentFragment;
import com.opensis.ui.teacher.fragment.GradeBookConfigFragment;
import com.opensis.ui.teacher.fragment.TeacheHomeFragment;
import com.opensis.ui.teacher.fragment.TeacherGradeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ClassDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout lnFragment;
    private FragmentTransaction transaction;
    TextView tvOverView,tvStudents,tvAssignments,tvGradeConfig,tvGrade,tvAttendance;
    int courseID,attendanceCatID;
    String courseName,courseTitle,courseSubject,calendartitle,seats,attendanceCategory,courseWeighted,useStandards,affectsClassRank,affectsHonorRoll,onlineClassRoom;
    double creditHours;
    Pref pref;
    String monthFrstDate;
    String twomonthsDate;
    String standardGradeScaleName,availableSeat,onlineClassroomUrl,onlineClassroomPassword;
    String gradescale,durationStartDate,durationEndDate,scheduleType,meetingDays,attendanceTaken,roomNo,period,quarters;
    int courseSectionID;
    TextView tvCourseName;
    int periodID;
    ImageView imgBack;
    boolean attendancetaken;
    String gradescaletype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        initView();
    }

    private void initView(){
        pref=new Pref(ClassDetailsActivity.this);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvCourseName=(TextView)findViewById(R.id.tvCourseName);
        periodID=getIntent().getIntExtra("periodID",0);
        attendancetaken= getIntent().getBooleanExtra("attendanceTaken",true);
        courseSectionID=getIntent().getIntExtra("courseSectionID",0);
        courseName=getIntent().getStringExtra("courseName");
        gradescaletype=getIntent().getStringExtra("gradescaletype");
        tvCourseName.setText(courseName);
        period=getIntent().getStringExtra("period");
        roomNo=getIntent().getStringExtra("roomNo");
        courseID=getIntent().getIntExtra("courseID",0);
        attendanceCatID=getIntent().getIntExtra("attendanceCatID",1);
        lnFragment=(LinearLayout)findViewById(R.id.lnFragment);
        tvOverView=(TextView)findViewById(R.id.tvOverView);
        tvStudents=(TextView)findViewById(R.id.tvStudents);
        tvAssignments=(TextView)findViewById(R.id.tvAssignments);
        tvGradeConfig=(TextView)findViewById(R.id.tvGradeConfig);
        tvAttendance=(TextView)findViewById(R.id.tvAttendance);
        tvGrade=(TextView)findViewById(R.id.tvGrade);
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
        twomonthsDate=Util.changeAnyDateFormat(calendar.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd")+"T00:00:00";



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

        tvOverView.setOnClickListener(this);
        tvStudents.setOnClickListener(this);
        tvAssignments.setOnClickListener(this);
        tvGradeConfig.setOnClickListener(this);
        tvGrade.setOnClickListener(this);
        tvAttendance.setOnClickListener(this);

    }

    private void viewClassOverViewFragment(){
        Log.d("riku","001");

        ClassOverViewFragment fragment = new ClassOverViewFragment();
        Bundle bundle = new Bundle();
         bundle.putString("courseTitle", courseTitle);
        bundle.putString("courseName", courseName);
        bundle.putString("courseSubject", courseSubject);
        bundle.putString("calendartitle", "Default Calendar");
        bundle.putString("creditHours", String.valueOf(creditHours));
        bundle.putString("seats", seats);
        bundle.putString("attendanceCategory", attendanceCategory);
        bundle.putString("courseWeighted", courseWeighted);
        bundle.putString("useStandards", useStandards);
        bundle.putString("affectsClassRank", affectsClassRank);
        bundle.putString("affectsHonorRoll", affectsHonorRoll);
        bundle.putString("onlineClassRoom", onlineClassRoom);
        bundle.putString("standardGradeScaleName", standardGradeScaleName);
        bundle.putString("availableSeat", availableSeat);
        bundle.putString("onlineClassroomUrl", onlineClassroomUrl);
        bundle.putString("onlineClassroomPassword", onlineClassroomPassword);
        bundle.putString("gradescale", gradescale);
        bundle.putString("durationStartDate", durationStartDate);
        bundle.putString("durationEndDate", durationEndDate);
        bundle.putString("scheduleType", scheduleType);
        bundle.putString("meetingDays", meetingDays);
        bundle.putString("attendanceTaken", attendanceTaken);
        bundle.putString("roomNo", roomNo);
        bundle.putString("period", period);
        bundle.putString("quarters", quarters);
        fragment.setArguments(bundle);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnFragment, fragment, "Overview");
        transaction.commit();

        tvOverView.setTextColor(Color.parseColor("#094990"));
        tvStudents.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAssignments.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGradeConfig.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGrade.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAttendance.setTextColor(Color.parseColor("#FFFFFFFF"));


        tvOverView.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        tvStudents.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAssignments.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGradeConfig.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGrade.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAttendance.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));





    }

    private void viewClassStudentsFragment(){

        ClassStudentFragment fragment = new ClassStudentFragment();
        Bundle bundle = new Bundle();
         bundle.putString("courseID", String.valueOf(courseSectionID));
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnFragment, fragment, "student");
        transaction.commit();

        tvOverView.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAssignments.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGradeConfig.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvStudents.setTextColor(Color.parseColor("#094990"));
        tvGrade.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAttendance.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvOverView.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvStudents.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        tvAssignments.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGradeConfig.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGrade.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAttendance.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));





    }

    private void viewClassAssignmentFragment(){

        AssignmentsFragment fragment = new AssignmentsFragment();
        Bundle bundle = new Bundle();
         bundle.putString("courseSectionID", String.valueOf(courseSectionID));
        bundle.putString("courseID", String.valueOf(courseID));
        bundle.putString("gradescaletype",gradescaletype);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnFragment, fragment, "assignment");
        transaction.commit();

        tvOverView.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvStudents.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAssignments.setTextColor(Color.parseColor("#094990"));
        tvGradeConfig.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGrade.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAttendance.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvOverView.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvStudents.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGradeConfig.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAssignments.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        tvGrade.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAttendance.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));





    }

    private void viewClassAttendanceAssignmentFragment(){

        ClassAttendanceFragment fragment = new ClassAttendanceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("courseSectionID", String.valueOf(courseSectionID));
        bundle.putString("courseID", String.valueOf(courseID));
        bundle.putString("periodID", String.valueOf(periodID));
        bundle.putString("meetingDays", meetingDays);
        bundle.putString("attendanceTaken",attendanceTaken);
        bundle.putInt("attendanceCatID",attendanceCatID);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnFragment, fragment, "attendance");
        transaction.commit();

        tvOverView.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvStudents.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAssignments.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGradeConfig.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGrade.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAttendance.setTextColor(Color.parseColor("#094990"));

        tvOverView.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvStudents.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGradeConfig.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAssignments.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGrade.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAttendance.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));





    }

    private void viewGradeBookConfigFragment(){

        GradeBookConfigFragment fragment = new GradeBookConfigFragment();
        Bundle bundle = new Bundle();
        bundle.putString("courseSectionID", String.valueOf(courseSectionID));
        bundle.putString("courseID", String.valueOf(courseID));
        bundle.putString("periodID", String.valueOf(periodID));
        bundle.putString("courseTitle", courseTitle);
        bundle.putString("courseName", courseName);
        bundle.putString("gradescaletype",gradescaletype);
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnFragment, fragment, "config");
        transaction.commit();

        tvOverView.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvStudents.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAssignments.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGradeConfig.setTextColor(Color.parseColor("#094990"));
        tvGrade.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAttendance.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvOverView.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvStudents.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGradeConfig.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        tvAssignments.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGrade.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAttendance.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));





    }
    private void viewGradeFragment(){

        TeacherGradeFragment fragment = new TeacherGradeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("courseSectionID", String.valueOf(courseSectionID));
        bundle.putString("gradescaletype",gradescaletype);
        // bundle.putString("currentDate", currentDate);
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnFragment, fragment, "config");
        transaction.commit();

        tvOverView.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvStudents.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAssignments.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGradeConfig.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvGrade.setTextColor(Color.parseColor("#094990"));
        tvAttendance.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvOverView.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvStudents.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGradeConfig.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvAssignments.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvGrade.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        tvAttendance.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));





    }

    @Override
    public void onClick(View view) {
        if (view==tvOverView){
              viewClassOverViewFragment();
        }else if (view==tvStudents){
            viewClassStudentsFragment();
        }else if (view==tvAssignments){
            viewClassAssignmentFragment();
        }else if (view==tvGradeConfig){
            viewGradeBookConfigFragment();
        }else if (view==tvGrade){
            viewGradeFragment();
        }else if (view==tvAttendance){
            viewClassAttendanceAssignmentFragment();
        }else if (view==imgBack){
            onBackPressed();
        }

    }

    public void getClassInformation(JSONObject jsonObject) {
        String url=pref.getAPI()+"CourseManager/getAllCourseSection";

        new PostJsonDataParser(ClassDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(ClassDetailsActivity.this,_message,Toast.LENGTH_LONG).show();



                        }else {
                            JSONArray getCourseSectionForView = response.optJSONArray("getCourseSectionForView");
                            if (getCourseSectionForView.length() > 0) {
                                for (int i=0;i<getCourseSectionForView.length();i++){
                                    JSONObject OBJ=getCourseSectionForView.optJSONObject(i);

                                    JSONObject courseSectionOBJ=OBJ.optJSONObject("courseSection");

                                    String courseSectionName=courseSectionOBJ.optString("courseSectionName");
                                    if (courseSectionName.equalsIgnoreCase(courseName)){

                                        standardGradeScaleName=Util.getFreshValue(OBJ.optString("standardGradeScaleName"),"-");
                                        availableSeat= String.valueOf(OBJ.optInt("availableSeat"));


                                        creditHours=courseSectionOBJ.optDouble("creditHours");
                                        seats= String.valueOf(courseSectionOBJ.optInt("seats"));
                                        onlineClassroomUrl=Util.getFreshValue(courseSectionOBJ.optString("onlineClassroomUrl"),"-");
                                        onlineClassroomPassword=Util.getFreshValue(courseSectionOBJ.optString("onlineClassroomPassword"),"-");
                                        gradescale=Util.getFreshValue(courseSectionOBJ.optString("gradescale"),"-");
                                        durationStartDate=courseSectionOBJ.optString("durationStartDate").replace("T00:00:00","");
                                        durationEndDate=courseSectionOBJ.optString("durationEndDate").replace("T00:00:00","");
                                        scheduleType=courseSectionOBJ.optString("scheduleType");
                                        meetingDays=Util.getFreshValue(courseSectionOBJ.optString("meetingDays"),"-");

                                        JSONObject semestersOBJ=courseSectionOBJ.optJSONObject("semesters");
                                        if (semestersOBJ!=null) {
                                            quarters = Util.getFreshValue(semestersOBJ.optString("title"), "-");
                                        }
                                        boolean battendanceTaken=courseSectionOBJ.optBoolean("attendanceTaken");
                                        if (battendanceTaken) {
                                            attendanceTaken = "1";
                                        }else {
                                            attendanceTaken="0";
                                        }

                                        boolean isWeightedCourse=courseSectionOBJ.optBoolean("isWeightedCourse");
                                        if (isWeightedCourse){
                                            courseWeighted="1";
                                        }else {
                                            courseWeighted="0";
                                        }
                                        boolean baffectsClassRank=courseSectionOBJ.optBoolean("affectsClassRank");
                                        if (baffectsClassRank){
                                            affectsClassRank="1";
                                        }else {
                                            affectsClassRank="0";
                                        }
                                        boolean baffectsHonorRoll=courseSectionOBJ.optBoolean("affectsHonorRoll");
                                        if (baffectsHonorRoll){
                                            affectsHonorRoll="1";
                                        }else {
                                            affectsHonorRoll="0";
                                        }
                                        boolean bonlineClassRoom=courseSectionOBJ.optBoolean("onlineClassRoom");
                                        if (bonlineClassRoom){
                                            onlineClassRoom="1";
                                        }else {
                                            onlineClassRoom="0";
                                        }
                                        boolean buseStandards=courseSectionOBJ.optBoolean("useStandards");
                                        if (buseStandards){
                                            useStandards="1";
                                        }else {
                                            useStandards="0";
                                        }

                                        JSONObject attendanceCatOBJ=courseSectionOBJ.optJSONObject("attendanceCodeCategories");
                                        if (attendanceCatOBJ!=null) {
                                            attendanceCategory = attendanceCatOBJ.optString("title");
                                        }



                                        JSONObject courseOBJ=courseSectionOBJ.optJSONObject("course");
                                        courseTitle=courseOBJ.optString("courseTitle");
                                        courseSubject=courseOBJ.optString("courseSubject");

                                      /*  JSONObject schoolCalendarsOBJ=courseSectionOBJ.optJSONObject("schoolCalendars");
                                         //calendartitle="Default Calendar";*/


                                    }else {
                                        //Toast.makeText(ClassDetailsActivity.this,"Not Found",Toast.LENGTH_LONG).show();
                                    }



                                }
                                viewClassOverViewFragment();







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


        new PostJsonDataParser(ClassDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Intent intent=new Intent(ClassDetailsActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("courseId",courseID);
                                jsonObject.put("academicYear",pref.getAcademyYear());
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId",AppData.tenatID);
                                jsonObject.put("schoolId",pref.getSchoolID());
                                getClassInformation(jsonObject);
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