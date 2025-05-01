package com.pacific.ui.student.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.pacific.R;
import com.pacific.adapter.ProgressReportAdapter;
import com.pacific.adapter.TotalProgressReportAdapter;
import com.pacific.databinding.FragmentProgressReportBinding;
import com.pacific.model.ProgressReportChildModel;
import com.pacific.model.ProgressReportModel;
import com.pacific.others.parser.PostJsonDataParser;
import com.pacific.others.utility.Pref;
import com.pacific.others.utility.Util;
import com.pacific.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProgressReportFragment extends Fragment implements View.OnClickListener {

    FragmentProgressReportBinding binding;
    private View view;
    Pref pref;
    ArrayList<ProgressReportChildModel> childList = new ArrayList<>();
    ArrayList<ProgressReportModel> itemList = new ArrayList<>();
    ProgressReportAdapter reportAdapter;
    ArrayList<Integer>pointss=new ArrayList<>();
    ArrayList<Integer>totalassignpointss=new ArrayList<>();
    ArrayList<Integer>totalgrade=new ArrayList<>();
    int totalpoints,totalassignpoints;
    int totalGrade;
    ArrayList<Integer>breakoffList=new ArrayList<Integer>();
    ArrayList<String>gradeTitle=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress_report, container, false);
        view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        pref = new Pref(getContext());

        binding.rvAssignment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        binding.rvTotalItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        JSONObject obj = new JSONObject();
        JSONObject accessobj = new JSONObject();
        try {
            obj.put("tenantId", pref.getTenatID());
            obj.put("userId", 0);
            obj.put("userAccessLog", accessobj);
            obj.put("_tenantName", pref.getTenatName());
            obj.put("_userName", pref.getName());
            obj.put("_token", pref.getToken());
            obj.put("schoolId", pref.getSchoolID());
            obj.put("email", pref.getEmail());
            getRefreshToken(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.tvAssignment.setOnClickListener(this);
        binding.tvTotal.setOnClickListener(this);


    }


    public void getRefreshToken(JSONObject jsonObject) {
        String url = pref.getAPI() + "User/RefreshToken";


        new PostJsonDataParser(getContext(), Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse", response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } else {
                            String _token = response.optString("_token");
                            pref.saveToken(_token);
                            JSONArray guid = new JSONArray();
                            guid.put(pref.getUserGUIID());
                            try {
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("totalsOnly", false);
                                jsonObject1.put("studentGuids",guid);
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                jsonObject1.put("academicYear", pref.getAcademicYear());
                                jsonObject1.put("markingPeriodStartDate", pref.getPeriodStartYear());
                                jsonObject1.put("markingPeriodEndDate", pref.getPeriodEndYear());
                                jsonObject1.put("markingPeriodTitle", pref.getPeriod());

                                getSchedule(jsonObject1);
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

    public void getSchedule(JSONObject jsonObject) {

        String url = pref.getAPI() + "Report/GetStudentProgressReport";
        new PostJsonDataParser(getContext(), Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse", response.toString());
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
                                Toast.makeText(getContext(), _message, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            itemList.clear();
                            childList.clear();
                            JSONArray schoolMasterListData = response.optJSONArray("schoolMasterListData");
                            if (schoolMasterListData.length() > 0) {

                                JSONObject schoolMasterListOBJ = schoolMasterListData.optJSONObject(0);
                                JSONArray studentMasterListData=schoolMasterListOBJ.optJSONArray("studentMasterListData");
                                if (studentMasterListData.length()>0){
                                    JSONObject studentMasterListOBJ = studentMasterListData.optJSONObject(0);
                                    String studentId=Util.getFreshValue(studentMasterListOBJ.optString("studentId"),"");
                                    binding.tvStudentID.setText(studentId);
                                    String firstGivenName=Util.getFreshValue(studentMasterListOBJ.optString("firstGivenName"),"");
                                    String lastFamilyName=Util.getFreshValue(studentMasterListOBJ.optString("lastFamilyName"),"");
                                    String middleName=Util.getFreshValue(studentMasterListOBJ.optString("middleName"),"");
                                    binding.tvStudentName.setText(firstGivenName+" "+middleName+" "+lastFamilyName);
                                    String gradeLevelTitle=Util.getFreshValue(studentMasterListOBJ.optString("gradeLevelTitle"),"");
                                    binding.tvGrade.setText(gradeLevelTitle);
                                    JSONArray courseSectionListData=studentMasterListOBJ.optJSONArray("courseSectionListData");
                                    for (int i=0;i<courseSectionListData.length();i++){
                                        childList=new ArrayList<>();
                                        pointss=new ArrayList<>();
                                        totalassignpointss=new ArrayList<>();
                                        totalgrade=new ArrayList<>();
                                        gradeTitle=new ArrayList<>();
                                        breakoffList=new ArrayList<>();
                                        JSONObject courseSectionOBJ=courseSectionListData.optJSONObject(i);
                                        JSONArray gradeBookGradeListData=courseSectionOBJ.optJSONArray("gradeBookGradeListData");
                                        String runningAvgGrade=Util.getFreshValue(courseSectionOBJ.optString("runningAvgGrade",""));

                                        if (gradeBookGradeListData.length()>0) {
                                            for (int j = 0; j < gradeBookGradeListData.length(); j++) {
                                                JSONObject gradeBookGradeListOBJ = gradeBookGradeListData.optJSONObject(j);
                                                String assignmentTypeTitle = Util.getFreshValue(gradeBookGradeListOBJ.optString("assignmentTypeTitle"), "");
                                                int weight = gradeBookGradeListOBJ.optInt("weight");
                                                String assignmentTitle = Util.getFreshValue(gradeBookGradeListOBJ.optString("assignmentTitle"), "");
                                                String assignmentDate = Util.getFreshValue(gradeBookGradeListOBJ.optString("assignmentDate").replaceAll("T00:00:00", ""));
                                                String dueDate = Util.getFreshValue(gradeBookGradeListOBJ.optString("dueDate").replaceAll("T00:00:00", ""));
                                                int points = gradeBookGradeListOBJ.optInt("points");
                                                int assignmentPoint = gradeBookGradeListOBJ.optInt("assignmentPoint");
                                                String grade = Util.getFreshValue(gradeBookGradeListOBJ.optString("grade"), "");
                                                String comment = Util.getFreshValue(gradeBookGradeListOBJ.optString("comment"), "");
                                                String wieghtedGrade = Util.getFreshValue(gradeBookGradeListOBJ.optString("wieghtedGrade"), "");
                                                if (!grade.equals("")){
                                                    ProgressReportChildModel childModel = new ProgressReportChildModel();
                                                    childModel.setAssignmentTypeTitle(assignmentTypeTitle);
                                                    childModel.setWeight(weight);
                                                    childModel.setAssignmentTitle(assignmentTitle);
                                                    childModel.setAssignmentDate(assignmentDate);
                                                    childModel.setAssignmentDate(dueDate);
                                                    childModel.setPoints(points);
                                                    childModel.setAssignmentPoint(assignmentPoint);
                                                    childModel.setGrade(grade);
                                                    childModel.setComment(comment);
                                                    childModel.setWieghtedGrade(wieghtedGrade);
                                                    childList.add(childModel);
                                                    pointss.add(points);
                                                    totalassignpointss.add(assignmentPoint);

                                                }else {

                                                    pointss.add(0);
                                                    totalassignpointss.add(assignmentPoint);
                                                }


                                            }
                                        }else {
                                            ProgressReportChildModel childModel = new ProgressReportChildModel();
                                            childModel.setAssignmentTypeTitle("");
                                            childModel.setAssignmentTitle("");
                                            childModel.setAssignmentDate("");
                                            childModel.setAssignmentDate("");
                                            childModel.setGrade("");
                                            childModel.setComment("");
                                            childModel.setWieghtedGrade("");
                                            childList.add(childModel);
                                        }





                                        if (pointss.size()>0){
                                            totalpoints=0;
                                            for(int j = 0; j < pointss.size(); j++){
                                                totalpoints += pointss.get(j);
                                            }
                                        }

                                        if (totalassignpointss.size()>0){
                                            totalassignpoints=0;
                                            for(int j = 0; j < totalassignpointss.size(); j++){
                                                totalassignpoints += totalassignpointss.get(j);
                                            }
                                        }








                                        String courseSectionName=Util.getFreshValue(courseSectionOBJ.optString("courseSectionName",""));
                                        String total=Util.getFreshValue(courseSectionOBJ.optString("total",""));
                                        String totalWeightedGrade=Util.getFreshValue(courseSectionOBJ.optString("totalWeightedGrade",""));
                                        String courseName=Util.getFreshValue(courseSectionOBJ.optString("courseName"));
                                        String teacherName=Util.getFreshValue(courseSectionOBJ.optString("teacherFirstName",""))+" "+Util.getFreshValue(courseSectionOBJ.optString("teacherLastName",""));
                                        ProgressReportModel reportModel=new ProgressReportModel();
                                        reportModel.setCourse(courseName);
                                        reportModel.setTeacher(teacherName);
                                        reportModel.setCourseSection(courseSectionName);
                                        reportModel.setRunningAvg(runningAvgGrade);
                                        reportModel.setTotalpoints(totalpoints);
                                        reportModel.setItemList(childList);
                                        reportModel.setTotalassignpoints(totalassignpoints);
                                        reportModel.setSize(gradeBookGradeListData.length());
                                        reportModel.setTotlWeightgrade(totalWeightedGrade);
                                        reportModel.setTotalGrade(total);





                                        itemList.add(reportModel);


                                    }

                                    reportAdapter=new ProgressReportAdapter(itemList,getContext(),ProgressReportFragment.this);
                                    binding.rvAssignment.setAdapter(reportAdapter);

                                    TotalProgressReportAdapter toalAdapter=new TotalProgressReportAdapter(itemList,getContext());
                                    binding.rvTotalItem.setAdapter(toalAdapter);

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
        reportAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v==binding.tvAssignment){
            binding.tvTotal.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#094990")));
            binding.tvTotal.setTextColor(Color.parseColor("#FFFFFF"));

            binding.tvAssignment.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            binding.tvAssignment.setTextColor(Color.parseColor("#094990"));

            binding.llAssignment.setVisibility(View.VISIBLE);
            binding.llTotal.setVisibility(View.GONE);

            JSONArray guid = new JSONArray();
            guid.put(pref.getUserGUIID());
            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("totalsOnly", false);
                jsonObject1.put("studentGuids",guid);
                jsonObject1.put("_tenantName", pref.getTenatName());
                jsonObject1.put("_userName", pref.getName());
                jsonObject1.put("_token", pref.getToken());
                jsonObject1.put("_academicYear", pref.getAcademicYear());
                jsonObject1.put("tenantId", pref.getTenatID());
                jsonObject1.put("schoolId", pref.getSchoolID());
                jsonObject1.put("academicYear", pref.getAcademicYear());
                jsonObject1.put("markingPeriodStartDate", pref.getPeriodStartYear());
                jsonObject1.put("markingPeriodEndDate", pref.getPeriodEndYear());
                jsonObject1.put("markingPeriodTitle", pref.getPeriod());

                getSchedule(jsonObject1);
                //getSchoolList(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else if (v==binding.tvTotal){
            binding.tvTotal.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            binding.tvTotal.setTextColor(Color.parseColor("#094990"));

            binding.tvAssignment.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#094990")));
            binding.tvAssignment.setTextColor(Color.parseColor("#FFFFFF"));

            binding.llAssignment.setVisibility(View.GONE);
            binding.llTotal.setVisibility(View.VISIBLE);

            JSONArray guid = new JSONArray();
            guid.put(pref.getUserGUIID());
            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("totalsOnly", true);
                jsonObject1.put("studentGuids",guid);
                jsonObject1.put("_tenantName", pref.getTenatName());
                jsonObject1.put("_userName", pref.getName());
                jsonObject1.put("_token", pref.getToken());
                jsonObject1.put("_academicYear", pref.getAcademicYear());
                jsonObject1.put("tenantId", pref.getTenatID());
                jsonObject1.put("schoolId", pref.getSchoolID());
                jsonObject1.put("academicYear", pref.getAcademicYear());
                jsonObject1.put("markingPeriodStartDate", pref.getPeriodStartYear());
                jsonObject1.put("markingPeriodEndDate", pref.getPeriodEndYear());
                jsonObject1.put("markingPeriodTitle", pref.getPeriod());

                getSchedule(jsonObject1);
                //getSchoolList(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    public void login(JSONObject jsonObject) {

        String url=pref.getAPI()+"User/ValidateLogin";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("loginresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        if (_failure){


                            Toast.makeText(getContext(),"Invalid Login Credentials",Toast.LENGTH_LONG).show();

                        }else {


                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            JSONArray guid = new JSONArray();
                            guid.put(pref.getUserGUIID());
                            try {
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("totalsOnly", false);
                                jsonObject1.put("studentGuids",guid);
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                jsonObject1.put("academicYear", pref.getAcademicYear());
                                jsonObject1.put("markingPeriodStartDate", pref.getPeriodStartYear());
                                jsonObject1.put("markingPeriodEndDate", pref.getPeriodEndYear());
                                jsonObject1.put("markingPeriodTitle", pref.getPeriod());

                                getSchedule(jsonObject1);
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
}