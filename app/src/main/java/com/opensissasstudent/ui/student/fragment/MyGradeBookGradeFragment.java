package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
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
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.MyGradeBookGradeAdapter;
import com.opensissasstudent.adapter.MyScheduleListAdapter;
import com.opensissasstudent.databinding.FragmentMyGradeBookGradeBinding;
import com.opensissasstudent.databinding.FragmentMyScheduleBinding;
import com.opensissasstudent.model.GradeBookGradeModel;
import com.opensissasstudent.model.MyScheduleListModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MyGradeBookGradeFragment extends Fragment {

    FragmentMyGradeBookGradeBinding binding;
    private View view;
    Pref pref;
    ArrayList<GradeBookGradeModel>itemList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_grade_book_grade,container,false);
        view=binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());

        binding.rvItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

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
                            String _token=response.optString("_token");
                            pref.saveToken(_token);

                            JSONObject jsonObject1=new JSONObject();

                            try {
                                jsonObject1.put("pageNumber", 1);
                                jsonObject1.put("pageSize", 40);
                                jsonObject1.put("studentId", pref.getUserID());
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
        String url=pref.getAPI()+"StudentPortal/getStudentGradebookGrades";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

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
                                Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            }

                        }else {
                            itemList.clear();

                            JSONArray studentGradebookGradeDetails=response.optJSONArray("studentGradebookGradeDetails");

                            for (int i=0;i<studentGradebookGradeDetails.length();i++){
                                JSONObject object=studentGradebookGradeDetails.optJSONObject(i);
                                String courseSectionName=object.optString("courseSectionName");
                                String letter= Util.getFreshValue(object.optString("letter"),"N/A");
                                String percent=Util.getFreshValue(object.optString("percent"),"");
                                String staffFirstName=Util.getFreshValue(object.optString("staffFirstName"),"");
                                String staffLastName=Util.getFreshValue(object.optString("staffLastName"),"");
                                int courseSectionId=object.optInt("courseSectionId");
                                int ungraded=object.optInt("ungraded");
                                String highestGrade=Util.getFreshValue(object.optString("highestGrade"),"");
                                String lowestGrade=Util.getFreshValue(object.optString("lowestGrade"),"");
                                GradeBookGradeModel gradeModel=new GradeBookGradeModel();
                                gradeModel.setLetter(letter);
                                gradeModel.setCourseSectionName(courseSectionName);
                                gradeModel.setPercent(percent);
                                gradeModel.setStaff(staffFirstName+" "+staffLastName);
                                gradeModel.setCourseSectionId(courseSectionId);
                                gradeModel.setUngraded(ungraded);
                                gradeModel.setHighestGrade(highestGrade);
                                gradeModel.setLowestGrade(lowestGrade);
                                itemList.add(gradeModel);




                            }
                            MyGradeBookGradeAdapter gradeAdapter=new MyGradeBookGradeAdapter(itemList,getContext());
                            binding.rvItem.setAdapter(gradeAdapter);

                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


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
                            JSONObject jsonObject1=new JSONObject();

                            try {
                                jsonObject1.put("pageNumber", 1);
                                jsonObject1.put("pageSize", 40);
                                jsonObject1.put("studentId", pref.getUserID());
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
}