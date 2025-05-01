package com.pacific.ui.student.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.pacific.R;
import com.pacific.adapter.MyGradeBookAssignmentAdapter;
import com.pacific.databinding.ActivityMyGardeBookGradeDetailsBinding;
import com.pacific.model.GradeBookAssignmentDetailsModel;
import com.pacific.others.parser.PostJsonDataParser;
import com.pacific.others.utility.Pref;
import com.pacific.others.utility.Util;
import com.pacific.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyGardeBookGradeDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    String courseName,percentage,letter,staff,garderange;
    int courseid,ungarde;
    ActivityMyGardeBookGradeDetailsBinding binding;
    Pref pref;
    ArrayList<GradeBookAssignmentDetailsModel>itemList=new ArrayList<>();
    MyGradeBookAssignmentAdapter gradeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_garde_book_grade_details);
        initView();
    }

    private void initView(){
        courseName=getIntent().getStringExtra("courseName");
        percentage=getIntent().getStringExtra("percentage");
        letter=getIntent().getStringExtra("letter");
        staff=getIntent().getStringExtra("staff");
        garderange=getIntent().getStringExtra("garderange");

        courseid=getIntent().getIntExtra("courseid",0);
        ungarde=getIntent().getIntExtra("ungarde",0);

        binding.tvCourseName.setText(courseName);
        binding.tvPercent.setText(percentage);
        binding.tvLetter.setText(letter);
        binding.tvTeacher.setText(staff);
        binding.tvGradeRange.setText(garderange);
        binding.tvUngraded.setText(""+ungarde);


        pref=new Pref(MyGardeBookGradeDetailsActivity.this);

        binding.rvItem.setLayoutManager(new LinearLayoutManager(MyGardeBookGradeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));

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

        binding.imgBack.setOnClickListener(this);
    }

    public void getRefreshToken(JSONObject jsonObject) {
        String url=pref.getAPI()+"User/RefreshToken";


        new PostJsonDataParser(MyGardeBookGradeDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Intent intent=new Intent(MyGardeBookGradeDetailsActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);

                            JSONObject jsonObject1=new JSONObject();

                            try {
                                jsonObject1.put("pageNumber", 1);
                                jsonObject1.put("pageSize", 40);
                                jsonObject1.put("studentId", pref.getUserID());
                                jsonObject1.put("courseSectionId", courseid);
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                jsonObject1.put("markingPeriodStartDate", pref.getPeriodStartYear());
                                jsonObject1.put("markingPeriodEndDate", pref.getPeriodEndYear());
                                jsonObject1.put("academicYear", pref.getAcademicYear());
                                getGradeBookGrade(jsonObject1);
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

    public void getGradeBookGrade(JSONObject jsonObject) {
        String url=pref.getAPI()+"StudentPortal/getStudentGradebookGradesByCourseSection";


        new PostJsonDataParser(MyGardeBookGradeDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(MyGardeBookGradeDetailsActivity.this,_message,Toast.LENGTH_LONG).show();

                        }else {
                            itemList.clear();

                            JSONArray studentGradebookDetails=response.optJSONArray("studentGradebookDetails");

                            for (int i=0;i<studentGradebookDetails.length();i++){
                                JSONObject object=studentGradebookDetails.optJSONObject(i);
                                String assignmentTitle=object.optString("assignmentTitle");
                                String assignmentType= Util.getFreshValue(object.optString("assignmentType"),"N/A");
                                String assignmentDescription=Util.getFreshValue(object.optString("assignmentDescription"),"");
                                String points=Util.getFreshValue(object.optString("points"),"");
                                String possible=Util.getFreshValue(object.optString("possible"),"");
                                String percent=Util.getFreshValue(object.optString("percent"),"");
                                String letter=Util.getFreshValue(object.optString("letter"),"");
                                String assignmentDate=Util.getFreshValue(object.optString("assignmentDate"),"").replaceAll("T00:00:00","");
                                String dueDate=Util.getFreshValue(object.optString("dueDate"),"").replaceAll("T00:00:00","");
                                String lowestGrade=Util.getFreshValue(object.optString("lowestGrade"),"");
                                String highestGrade=Util.getFreshValue(object.optString("highestGrade"),"");

                                GradeBookAssignmentDetailsModel gradeModel=new GradeBookAssignmentDetailsModel();
                                gradeModel.setLetter(letter);
                                gradeModel.setGradeRange(highestGrade+"-"+lowestGrade);
                                gradeModel.setAssignedDate(Util.changeAnyDateFormat(assignmentDate,"yyyy-MM-dd","dd MMM, yyyy"));
                                gradeModel.setDueDate(Util.changeAnyDateFormat(dueDate,"yyyy-MM-dd","dd MMM, yyyy"));
                                gradeModel.setAssignemtnName(assignmentTitle);
                                gradeModel.setAssignmentType(assignmentType);
                                gradeModel.setDescription(assignmentDescription);
                                gradeModel.setPoints(points);
                                gradeModel.setPossible(possible);
                                gradeModel.setPercentage(percent);
                                itemList.add(gradeModel);




                            }
                             gradeAdapter=new MyGradeBookAssignmentAdapter(itemList,MyGardeBookGradeDetailsActivity.this,MyGardeBookGradeDetailsActivity.this);
                            binding.rvItem.setAdapter(gradeAdapter);

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
        gradeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v==binding.imgBack){
            onBackPressed();
        }
    }
}