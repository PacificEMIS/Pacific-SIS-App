package com.opensissasstudent.ui.student.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.AbsentAdapter;
import com.opensissasstudent.databinding.ActivityAbsentSummaryDetailsBinding;
import com.opensissasstudent.model.AbsentModel;
import com.opensissasstudent.model.AbsentSummaryChildModel;
import com.opensissasstudent.model.SpinnerModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AbsentSummaryDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    String periodid,fromdate,todate;
    ActivityAbsentSummaryDetailsBinding binding;
    Pref pref;
    ArrayList<AbsentModel>itemList=new ArrayList<>();
    ArrayList<AbsentSummaryChildModel>periodList=new ArrayList<>();
    AbsentAdapter absentAdapter;
    int date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_absent_summary_details);
        initView();
    }

    private void initView(){
        pref=new Pref(AbsentSummaryDetailsActivity.this);
        periodid=getIntent().getStringExtra("periodid");
        fromdate=getIntent().getStringExtra("fromdate");
        todate=getIntent().getStringExtra("todate");
        binding.rvItem.setLayoutManager(new LinearLayoutManager(AbsentSummaryDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNumber",1);
            jsonObject.put("pageSize",40);
            jsonObject.put("periodId",periodid);
            jsonObject.put("markingPeriodStartDate",fromdate);
            jsonObject.put("markingPeriodEndDate",todate);
            jsonObject.put("academicYear",pref.getAcademicYear());
            jsonObject.put("studentId",pref.getUserID());
            jsonObject.put("_tenantName",pref.getTenatName());
            jsonObject.put("_userName",pref.getName());
            jsonObject.put("_token",pref.getToken());
            jsonObject.put("_academicYear",pref.getAcademicYear());
            jsonObject.put("tenantId",pref.getTenatID());
            jsonObject.put("schoolId",pref.getSchoolID());
            getAbsentSummary(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.imgBack.setOnClickListener(this);

    }

    public void getAbsentSummary(JSONObject jsonObject) {
        String url=pref.getAPI()+"Report/getAbsenceListByStudent";


        new PostJsonDataParser(AbsentSummaryDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(AbsentSummaryDetailsActivity.this,_message,Toast.LENGTH_LONG).show();

                        }else {




                            JSONArray studendList=response.optJSONArray("studendList");
                            for (int i=0;i<studendList.length();i++){
                                periodList=new ArrayList<>();
                                JSONObject absentOBJ=studendList.optJSONObject(i);
                                JSONArray attendanceInPeriodDetailsViewModels=absentOBJ.optJSONArray("attendanceInPeriodDetailsViewModels");
                                for (int j=0;j<attendanceInPeriodDetailsViewModels.length();j++){
                                    JSONObject periodOBJ=attendanceInPeriodDetailsViewModels.optJSONObject(j);
                                    String periodTitle=periodOBJ.optString("periodTitle");
                                    String attendanceShortName=periodOBJ.optString("attendanceShortName");
                                    AbsentSummaryChildModel childModel=new AbsentSummaryChildModel();
                                    childModel.setPeriodTitle(periodTitle);
                                    childModel.setAttendanceShortName(attendanceShortName);
                                    periodList.add(childModel);
                                }

                                String attendance=absentOBJ.optString("attendance");
                                String adminComment= Util.getFreshValue(absentOBJ.optString("adminComment"),"");
                                String teacherComment= Util.getFreshValue(absentOBJ.optString("teacherComment"),"");
                                char first = attendance.charAt(0);
                                String shortCode=Character.toString(first);
                                String absenceDate=absentOBJ.optString("absenceDate").replaceAll("T00:00:00","");
                                AbsentModel absentModel=new AbsentModel();
                                absentModel.setAbsenceDate(absenceDate);
                                absentModel.setAttendance(attendance);
                                absentModel.setAttendanceshortCode(shortCode);
                                absentModel.setItemList(periodList);
                                absentModel.setTeacherComment(teacherComment);
                                absentModel.setAdminComment(adminComment);
                                itemList.add(absentModel);




                            }

                             absentAdapter=new AbsentAdapter(itemList,AbsentSummaryDetailsActivity.this,AbsentSummaryDetailsActivity.this);
                            binding.rvItem.setAdapter(absentAdapter);




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
        absentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v==binding.imgBack){
            onBackPressed();
        }

    }
}