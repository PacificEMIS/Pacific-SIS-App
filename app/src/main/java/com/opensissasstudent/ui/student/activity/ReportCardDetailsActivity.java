package com.opensissasstudent.ui.student.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.MyScheduleListAdapter;
import com.opensissasstudent.adapter.ReportCardAdapter;
import com.opensissasstudent.databinding.ActivityReportCardDetailsBinding;
import com.opensissasstudent.model.MyScheduleListModel;
import com.opensissasstudent.model.ReportCardModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Api;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReportCardDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityReportCardDetailsBinding binding;
    Pref pref;
    int academicyear;
    String period;
    ArrayList<ReportCardModel>itemList=new ArrayList<>();
    ReportCardAdapter reportCardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_report_card_details);
        initView();
    }

    private void initView(){
        academicyear=getIntent().getIntExtra("academicyear",0);
        period=getIntent().getStringExtra("period");
        pref=new Pref(ReportCardDetailsActivity.this);

        binding.rvItem.setLayoutManager(new LinearLayoutManager(ReportCardDetailsActivity.this, LinearLayoutManager.VERTICAL, false));


        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("studentId",pref.getUserID());

            JSONArray cardArray=new JSONArray();
            cardArray.put(jsonObject);

            JSONObject reportOBJ=new JSONObject();
            reportOBJ.put("studentsReportCardViewModelList",cardArray);
            reportOBJ.put("teacherName",true);
            reportOBJ.put("teacherComments",true);
            reportOBJ.put("commentKeys",true);
            reportOBJ.put("signatureLine",true);
            reportOBJ.put("parcentage",true);
            reportOBJ.put("gpa",true);
            reportOBJ.put("yearToDateDailyAbsences",true);
            reportOBJ.put("dailyAbsencesThisMarkingPeriod",true);
            reportOBJ.put("otherAttendanceCodeYearToDate",true);
            reportOBJ.put("templateType","default");
            reportOBJ.put("academicYear",academicyear);
            reportOBJ.put("markingPeriods",period);
            reportOBJ.put("_tenantName",pref.getTenatName());
            reportOBJ.put("_userName",pref.getName());
            reportOBJ.put("_token",pref.getToken());
            reportOBJ.put("_academicYear",academicyear);
            reportOBJ.put("tenantId",pref.getTenatID());
            reportOBJ.put("schoolId",pref.getSchoolID());
            reportOBJ.put("createdBy","7ad0e8bf-2e0d-46c6-b354-95213985acf7");

            getReportCard(reportOBJ);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.imgBack.setOnClickListener(this);

    }

    public void getReportCard(JSONObject jsonObject) {
        String url=pref.getAPI()+"ReportCard/getReportCardForStudents";


        new PostJsonDataParser(ReportCardDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            if (_message.contains("token")||_message.contains("Token") ) {
                                JSONObject jobj = new JSONObject();
                                try {
                                    jobj.put("tenantId", pref.getTenatID());
                                    jobj.put("userId", 0);
                                    jobj.put("_tenantName", pref.getTenatName());
                                    jobj.put("password", pref.getPasswordBase64());
                                    jobj.put("email", pref.getLoginEmail());
                                    jobj.put("schoolId", null);
                                    jobj.put("_userName", "");
                                    jobj.put("_token", null);
                                    jobj.put("isMobileLogin", true);
                                    login(jobj);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                Toast.makeText(ReportCardDetailsActivity.this,_message,Toast.LENGTH_LONG).show();

                            }

                        }else {
                            itemList.clear();
                            JSONArray studentsReportCardViewModelList=response.optJSONArray("studentsReportCardViewModelList");
                            JSONObject reportCardOBJ=studentsReportCardViewModelList.optJSONObject(0);
                            String firstGivenName=reportCardOBJ.optString("firstGivenName");
                            String lastFamilyName=reportCardOBJ.optString("lastFamilyName");
                            binding.tvStudentName.setText(firstGivenName+" "+lastFamilyName);

                            String gradeTitle=reportCardOBJ.optString("gradeTitle");
                            binding.tvGrade.setText(gradeTitle);

                            String studentInternalId=reportCardOBJ.optString("studentInternalId");
                            binding.tvStudentID.setText("Student ID: "+studentInternalId);

                            String yearToDateAttendencePercent=reportCardOBJ.optString("yearToDateAttendencePercent");
                            binding.tvAttendance.setText("Attendance % -"+yearToDateAttendencePercent);

                            String yearToDateAbsencesInDays=reportCardOBJ.optString("yearToDateAbsencesInDays");
                            binding.tvAbsence.setText("Absences (in days) -"+yearToDateAbsencesInDays);

                            JSONArray markingPeriodDetailsForDefaultTemplates=reportCardOBJ.optJSONArray("markingPeriodDetailsForDefaultTemplates");
                            JSONObject reportcarddetailsobj=markingPeriodDetailsForDefaultTemplates.optJSONObject(0);

                            String markingPeriodTitle=reportcarddetailsobj.optString("markingPeriodTitle");
                            binding.tvSemister.setText(markingPeriodTitle);


                            JSONArray courseSectionGradeDetailsForDefaultTemplates=reportcarddetailsobj.optJSONArray("courseSectionGradeDetailsForDefaultTemplates");
                            for (int i=0;i<courseSectionGradeDetailsForDefaultTemplates.length();i++){
                                JSONObject jsonObject1=courseSectionGradeDetailsForDefaultTemplates.optJSONObject(i);
                                String courseSectionName=jsonObject1.optString("courseSectionName");
                                String staffName= Util.getFreshValue(jsonObject1.optString("staffName"),"");
                                double percentMarks= jsonObject1.optDouble("percentMarks");
                                double gpa= jsonObject1.optDouble("gpa");
                                String gradeObtained= Util.getFreshValue(jsonObject1.optString("gradeObtained"),"");
                                String comments= Util.getFreshValue(jsonObject1.optString("comments"),"");
                                String teacherComments= Util.getFreshValue(jsonObject1.optString("teacherComments"),"");
                                ReportCardModel reportCardModel=new ReportCardModel();
                                reportCardModel.setCourseSectionName(courseSectionName);
                                reportCardModel.setStaffName(staffName);
                                reportCardModel.setPercentMarks(percentMarks);
                                reportCardModel.setGpa(gpa);
                                reportCardModel.setGradeObtained(gradeObtained);
                                reportCardModel.setComments(comments);
                                reportCardModel.setTeacherComments(teacherComments);
                                itemList.add(reportCardModel);
                            }


                             reportCardAdapter=new ReportCardAdapter(itemList,ReportCardDetailsActivity.this,ReportCardDetailsActivity.this);
                            binding.rvItem.setAdapter(reportCardAdapter);

                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void updateStatus(int position,boolean status)
    {
        for (int i =0 ;i<itemList.size();i++)
        {
            if (i==position)
            {
                itemList.get(i).setExpanded(status);
            }
            else
            {
                itemList.get(i).setExpanded(false);
            }
        }
        reportCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v==binding.imgBack){
            onBackPressed();
        }

    }


    public void login(JSONObject jsonObject) {

        String url=pref.getAPI()+"User/ValidateLogin";

        new PostJsonDataParser(ReportCardDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        if (_failure){


                            Toast.makeText(ReportCardDetailsActivity.this,"Invalid Login Credentials",Toast.LENGTH_LONG).show();

                        }else {


                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("studentId",pref.getUserID());

                                JSONArray cardArray=new JSONArray();
                                cardArray.put(jsonObject);

                                JSONObject reportOBJ=new JSONObject();
                                reportOBJ.put("studentsReportCardViewModelList",cardArray);
                                reportOBJ.put("teacherName",true);
                                reportOBJ.put("teacherComments",true);
                                reportOBJ.put("commentKeys",true);
                                reportOBJ.put("signatureLine",true);
                                reportOBJ.put("parcentage",true);
                                reportOBJ.put("gpa",true);
                                reportOBJ.put("yearToDateDailyAbsences",true);
                                reportOBJ.put("dailyAbsencesThisMarkingPeriod",true);
                                reportOBJ.put("otherAttendanceCodeYearToDate",true);
                                reportOBJ.put("templateType","default");
                                reportOBJ.put("academicYear",academicyear);
                                reportOBJ.put("markingPeriods",period);
                                reportOBJ.put("_tenantName",pref.getTenatName());
                                reportOBJ.put("_userName",pref.getName());
                                reportOBJ.put("_token",pref.getToken());
                                reportOBJ.put("_academicYear",academicyear);
                                reportOBJ.put("tenantId",pref.getTenatID());
                                reportOBJ.put("schoolId",pref.getSchoolID());
                                reportOBJ.put("createdBy","7ad0e8bf-2e0d-46c6-b354-95213985acf7");

                                getReportCard(reportOBJ);


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