package com.pacific.ui.teacher.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.pacific.R;
import com.pacific.adapter.MissiongAttendanceAdapter;
import com.pacific.model.MissingAttendanceModel;
import com.pacific.others.parser.PostJsonDataParser;
import com.pacific.others.utility.Pref;
import com.pacific.others.utility.Util;


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
    int pagesize;
    String startDate="",endDate="";
    LinearLayout llFromDate,llToDate;
    int dateflag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_attendance);
        initView();
    }

    private void initView() {
        llFromDate=(LinearLayout)findViewById(R.id.llFromDate);
        llToDate=(LinearLayout)findViewById(R.id.llToDate);
        pagesize=getIntent().getIntExtra("pagesize",0);
        pref = new Pref(MissingAttendanceActivity.this);
        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(MissingAttendanceActivity.this, LinearLayoutManager.VERTICAL, false));
        tvTo=(TextView) findViewById(R.id.tvTo);
        tvFrom=(TextView) findViewById(R.id.tvFrom);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = df.format(c);
        endDate=currentDate;
        tvTo.setText(Util.changeAnyDateFormat(currentDate,"yyyy-MM-dd","dd MMM, yyyy"));
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        llFromDate.setOnClickListener(this);
        llToDate.setOnClickListener(this);
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
                                Toast.makeText(MissingAttendanceActivity.this, _message, Toast.LENGTH_LONG).show();
                                rvItem.setVisibility(View.GONE);
                            }


                        } else {
                            rvItem.setVisibility(View.VISIBLE);
                            JSONArray courseSectionViewList = response.optJSONArray("courseSectionViewList");
                            if (courseSectionViewList.length() > 0) {
                                for (int i = 0; i < courseSectionViewList.length(); i++) {
                                    JSONObject obj = courseSectionViewList.optJSONObject(i);
                                    String courseSectionName = obj.optString("courseSectionName");
                                    String courseTitle = obj.optString("courseTitle");
                                    String periodTitle = obj.optString("periodTitle");
                                    String courseGradeLevel = Util.getFreshValue(obj.optString("courseGradeLevel"),"");
                                    int courseSectionId=obj.optInt("courseSectionId");
                                    int courseId=obj.optInt("courseId");
                                    int periodId=obj.optInt("periodId");
                                    int blockId=obj.optInt("blockId");
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
                                    model.setBlockId(blockId);
                                    model.setAttendanceCategoryId(attendanceCategoryId);

                                    itemList.add(model);


                                }
                                JSONObject obj = courseSectionViewList.optJSONObject(0);
                                String attendanceDate =obj.optString("attendanceDate").replace("T00:00:00","");
                                startDate=attendanceDate;
                                if (dateflag==0){
                                    tvFrom.setText(Util.changeAnyDateFormat(attendanceDate,"yyyy-MM-dd","dd MMM, yyyy"));

                                }

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

                        } else {
                            String _token = response.optString("_token");
                            pref.saveToken(_token);
                            JSONObject obj=new JSONObject();
                            obj.put("_failure",false);
                            obj.put("_message","");
                            obj.put("staffId",pref.getUserID());
                            obj.put("pageNumber",1);
                            obj.put("_pageSize",pagesize);
                            obj.put("pageSize",pagesize);
                            obj.put("schoolId",pref.getSchoolID());
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("_token",pref.getToken());
                            obj.put("tenantId",pref.getTenatID());
                            obj.put("_academicYear",pref.getAcademicYear());
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
        if (v==imgBack){
            onBackPressed();
        }else if (v==llFromDate){
            showStartDateDialog();
        }else if (v==llToDate){
            showEndDateDialog();
        }

    }

    private void showStartDateDialog() {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                StringBuffer strBuf = new StringBuffer();
                strBuf.append("Select date is ");
                strBuf.append(year);
                strBuf.append("-");
                strBuf.append(month + 1);
                strBuf.append("-");
                strBuf.append(dayOfMonth);


            }
        };

        // Get current year, month and day.
        Calendar now = Calendar.getInstance();
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(MissingAttendanceActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);


                String date = y + "-" + month + "-" + d;
                dateflag=1;
                tvFrom.setText(Util.changeAnyDateFormat(date,"yyyy-MM-dd","dd MMM, yyyy"));
                startDate=date;
                if (!endDate.equalsIgnoreCase("")){
                    JSONObject obj=new JSONObject();
                    try {
                        obj.put("_failure",false);
                        obj.put("_message","");
                        obj.put("staffId",pref.getUserID());
                        obj.put("pageNumber",1);
                        obj.put("_pageSize",pagesize);
                        obj.put("pageSize",pagesize);
                        obj.put("schoolId",pref.getSchoolID());
                        obj.put("_tenantName",pref.getTenatName());
                        obj.put("_userName",pref.getName());
                        obj.put("_token",pref.getToken());
                        obj.put("tenantId",pref.getTenatID());
                        obj.put("_academicYear",pref.getAcademicYear());
                        obj.put("academicYear",pref.getAcademicYear());
                        obj.put("DobStartDate",startDate);
                        obj.put("DobEndDate",endDate);
                        getAttendanceList(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getAttendanceList(obj);
                }


                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clock);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker();

        // Popup the dialog.

        dialog.show();

    }

    private void showEndDateDialog() {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                StringBuffer strBuf = new StringBuffer();
                strBuf.append("Select date is ");
                strBuf.append(year);
                strBuf.append("-");
                strBuf.append(month + 1);
                strBuf.append("-");
                strBuf.append(dayOfMonth);


            }
        };

        // Get current year, month and day.
        Calendar now = Calendar.getInstance();
        final int year2 = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        // Create the new DatePickerDialog instance.
        /*DatePickerDialog datePickerDialog = new DatePickerDialog(SalesManageActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);*/
        final DatePickerDialog dialog = new DatePickerDialog(MissingAttendanceActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                String sdate = (m + 1) + "/" + d + "/" + y;
                int s = (m + 1) + d + y;

                int month = (m + 1);


                String date = y + "-" + month + "-" + d;
                tvTo.setText(Util.changeAnyDateFormat(date,"yyyy-MM-dd","dd MMM, yyyy"));
                endDate=date;
                if (!startDate.equalsIgnoreCase("")){
                    JSONObject obj=new JSONObject();
                    try {
                        obj.put("_failure",false);
                        obj.put("_message","");
                        obj.put("staffId",pref.getUserID());
                        obj.put("pageNumber",1);
                        obj.put("_pageSize",pagesize);
                        obj.put("pageSize",pagesize);
                        obj.put("schoolId",pref.getSchoolID());
                        obj.put("_tenantName",pref.getTenatName());
                        obj.put("_userName",pref.getName());
                        obj.put("_token",pref.getToken());
                        obj.put("tenantId",pref.getTenatID());
                        obj.put("_academicYear",pref.getAcademicYear());
                        obj.put("academicYear",pref.getAcademicYear());
                        obj.put("DobStartDate",startDate);
                        obj.put("DobEndDate",endDate);
                        getAttendanceList(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    getAttendanceList(obj);
                }
                //  pref.saveDOJ(sdate);


            }
        }, year2, month, day);


        // Set dialog icon and title.
        dialog.setIcon(R.drawable.clock);
        dialog.setTitle("Please select date.");
        dialog.getDatePicker();

        // Popup the dialog.

        dialog.show();

    }


    public void login(JSONObject jsonObject) {

        String url=pref.getAPI()+"User/ValidateLogin";

        new PostJsonDataParser(MissingAttendanceActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        if (_failure){


                            Toast.makeText(MissingAttendanceActivity.this,"Invalid Login Credentials",Toast.LENGTH_LONG).show();

                        }else {


                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            JSONObject obj=new JSONObject();
                            obj.put("_failure",false);
                            obj.put("_message","");
                            obj.put("staffId",pref.getUserID());
                            obj.put("pageNumber",1);
                            obj.put("_pageSize",pagesize);
                            obj.put("pageSize",pagesize);
                            obj.put("schoolId",pref.getSchoolID());
                            obj.put("_tenantName",pref.getTenatName());
                            obj.put("_userName",pref.getName());
                            obj.put("_token",pref.getToken());
                            obj.put("tenantId",pref.getTenatID());
                            obj.put("_academicYear",pref.getAcademicYear());
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
}