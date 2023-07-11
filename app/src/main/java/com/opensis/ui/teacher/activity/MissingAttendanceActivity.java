package com.opensis.ui.teacher.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.ClassStudentAdapter;
import com.opensis.adapter.MissiongAttendanceAdapter;
import com.opensis.model.ClassStudentModel;
import com.opensis.model.MissingAttendanceModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MissingAttendanceActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvItem;
    ArrayList<MissingAttendanceModel> itemList = new ArrayList<>();
    Pref pref;
    TextView tvFrom,tvTo;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_attendance);
        initView();
    }

    private void initView() {
        pref = new Pref(MissingAttendanceActivity.this);
        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(MissingAttendanceActivity.this, LinearLayoutManager.VERTICAL, false));
        tvTo=(TextView) findViewById(R.id.tvTo);
        tvFrom=(TextView) findViewById(R.id.tvFrom);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = df.format(c);
        tvTo.setText(Util.changeAnyDateFormat(currentDate,"yyyy-MM-dd","dd MMM,yyyy"));
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
    }


    private void setAdapter() {
        MissiongAttendanceAdapter missingAdapter = new MissiongAttendanceAdapter(itemList, MissingAttendanceActivity.this);
        rvItem.setAdapter(missingAdapter);
    }

    public void getAttendanceList(JSONObject jsonObject) {

        String url = pref.getAPI() + "StaffPortal/getAllMissingAttendanceListForStaff";

        new PostJsonDataParser(MissingAttendanceActivity.this, Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse", response.toString());
                    itemList.clear();
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {
                            Toast.makeText(MissingAttendanceActivity.this, _message, Toast.LENGTH_LONG).show();


                        } else {
                            JSONArray courseSectionViewList = response.optJSONArray("courseSectionViewList");
                            if (courseSectionViewList.length() > 0) {
                                for (int i = 0; i < courseSectionViewList.length(); i++) {
                                    JSONObject obj = courseSectionViewList.optJSONObject(i);
                                    String courseSectionName = obj.optString("courseSectionName");
                                    String courseTitle = obj.optString("courseTitle");
                                    String periodTitle = obj.optString("periodTitle");
                                    String courseGradeLevel = obj.optString("courseGradeLevel");
                                    int courseSectionId=obj.optInt("courseSectionId");
                                    int courseId=obj.optInt("courseId");
                                    int periodId=obj.optInt("periodId");
                                    int attendanceCategoryId=obj.optInt("attendanceCategoryId");
                                    String attendanceDate = obj.optString("attendanceDate").replace("T00:00:00","");
                                    MissingAttendanceModel model = new MissingAttendanceModel();
                                    model.setCourseSectionName(courseSectionName);
                                    model.setCourseSection(courseTitle);
                                    model.setPeriod(periodTitle);
                                    model.setGrade(courseGradeLevel);
                                    model.setDate(attendanceDate);
                                    model.setCourseID(courseId);
                                    model.setCourseSectionID(courseSectionId);
                                    model.setPeriodId(periodId);
                                    model.setAttendanceCategoryId(attendanceCategoryId);

                                    itemList.add(model);


                                }
                                JSONObject obj = courseSectionViewList.optJSONObject(0);
                                String attendanceDate = Util.changeAnyDateFormat(obj.optString("attendanceDate").replace("T00:00:00",""),"yyyy-MM-dd","dd MMM,yyyy");
                                tvFrom.setText(attendanceDate);

                                setAdapter();


                            } else {

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
        String url = pref.getAPI() + "User/RefreshToken";


        new PostJsonDataParser(MissingAttendanceActivity.this, Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse", response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {
                            Intent intent = new Intent(MissingAttendanceActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } else {
                            String _token = response.optString("_token");
                            pref.saveToken(_token);
                            JSONObject obj=new JSONObject();
                            obj.put("_failure",false);
                            obj.put("_message","");
                            obj.put("staffId",pref.getUserID());
                            obj.put("schoolId",pref.getSchoolID());
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("_token",pref.getToken());
                            obj.put("tenantId",AppData.tenatID);
                            obj.put("academicYear",pref.getAcademicYear());
                            getAttendanceList(obj);


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public void onClick(View v) {
        if (v==imgBack){
            onBackPressed();
        }

    }
}