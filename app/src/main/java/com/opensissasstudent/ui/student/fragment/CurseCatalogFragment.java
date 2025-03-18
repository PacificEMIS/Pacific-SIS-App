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
import com.opensissasstudent.adapter.CourseCatalogAdapter;
import com.opensissasstudent.databinding.FragmentCurseCatalogBinding;
import com.opensissasstudent.databinding.FragmentMyInfoGeneralBinding;
import com.opensissasstudent.model.CourseCatalogModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CurseCatalogFragment extends Fragment {

    FragmentCurseCatalogBinding binding;
    private View view;
    Pref pref;
    ArrayList<CourseCatalogModel>itemList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_curse_catalog,container,false);
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
                                jsonObject1.put("markingPeriodId", "");
                                jsonObject1.put("courseSubject", "");
                                jsonObject1.put("courseId", "");
                                jsonObject1.put("gradeLevel", "");
                                jsonObject1.put("gradeLevel", "");
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                jsonObject1.put("academicYear", pref.getAcademicYear());
                                getcoursecatalog(jsonObject1);
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

    public void getcoursecatalog(JSONObject jsonObject) {
        String url=pref.getAPI()+"CourseManager/getCourseCatelog";


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
                                Toast.makeText(getContext(), _message, Toast.LENGTH_LONG).show();
                            }

                        }else {
                            JSONArray courseWithCourseSectionDetailsViewModels=response.optJSONArray("courseWithCourseSectionDetailsViewModels");
                            for (int i=0;i<courseWithCourseSectionDetailsViewModels.length();i++){
                                JSONObject object=courseWithCourseSectionDetailsViewModels.optJSONObject(i);
                                String courseTitle=object.optString("courseTitle");
                                String courseShortName=Util.getFreshValue(object.optString("courseShortName"),"");
                                String courseGradeLevel=Util.getFreshValue(object.optString("courseGradeLevel"),"");
                                String courseSubject=Util.getFreshValue(object.optString("courseSubject"),"");
                                String creditHours=Util.getFreshValue(object.optString("creditHours"),"");
                                JSONArray getCourseSectionForView=object.optJSONArray("getCourseSectionForView");
                                String CourseSectionForView=getCourseSectionForView.toString();

                                CourseCatalogModel catalogModel=new CourseCatalogModel();
                                catalogModel.setCourseTitle(courseTitle);
                                catalogModel.setCourseShortName(courseShortName);
                                catalogModel.setCourseGradeLevel(courseGradeLevel);
                                catalogModel.setCreditHours(creditHours);
                                catalogModel.setCourseSubject(courseSubject);
                                catalogModel.setCourseSectionArray(CourseSectionForView);
                                itemList.add(catalogModel);

                            }
                            CourseCatalogAdapter catalogAdapter=new CourseCatalogAdapter(itemList,getContext());
                            binding.rvItem.setAdapter(catalogAdapter);

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
                                jsonObject1.put("markingPeriodId", "");
                                jsonObject1.put("courseSubject", "");
                                jsonObject1.put("courseId", "");
                                jsonObject1.put("gradeLevel", "");
                                jsonObject1.put("gradeLevel", "");
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                jsonObject1.put("academicYear", pref.getAcademicYear());
                                getcoursecatalog(jsonObject1);
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