package com.opensis.ui.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.ClassStudentAdapter;
import com.opensis.adapter.TeacherNotificationAdapter;
import com.opensis.model.ClassStudentModel;
import com.opensis.model.StudentsModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassStudentFragment extends Fragment {

    View view;
    RecyclerView rvStudent;
    ArrayList<ClassStudentModel>itmList=new ArrayList<>();
    int courseID;
    JSONObject jsonObject=new JSONObject();
    Pref pref;
    LinearLayout lnNoData;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_class_student, container, false);
        initView();
        return view;
    }

    private void initView(){
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        pref=new Pref(getContext());
        courseID= Integer.parseInt(getArguments().getString("courseID"));

        rvStudent=(RecyclerView)view.findViewById(R.id.rvStudent);
        rvStudent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


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

    private void setAdapter(){
        ClassStudentAdapter studentAdapter=new ClassStudentAdapter(itmList,getContext());
        rvStudent.setAdapter(studentAdapter);
    }

    public void getStudentList(JSONObject jsonObject) {
        rvStudent.setVisibility(View.VISIBLE);
        lnNoData.setVisibility(View.GONE);

        String url=pref.getAPI()+"StudentSchedule/getStudentListByCourseSection";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            rvStudent.setVisibility(View.GONE);
                            lnNoData.setVisibility(View.VISIBLE);


                        }else {
                            JSONArray scheduleStudentForView = response.optJSONArray("scheduleStudentForView");
                            if (scheduleStudentForView.length() > 0) {
                                for (int i=0;i<scheduleStudentForView.length();i++){
                                    JSONObject studentMasterOBJ=scheduleStudentForView.optJSONObject(i);
                                    String firstGivenName=studentMasterOBJ.optString("firstGivenName");
                                    String lastFamilyName=studentMasterOBJ.optString("lastFamilyName");
                                    String middleName= Util.getFreshValue(studentMasterOBJ.optString("middleName"));
                                    String name=firstGivenName+" "+middleName+" "+lastFamilyName;
                                    String studentInternalId=studentMasterOBJ.optString("studentInternalId");
                                    String studentPhoto=Util.getFreshValue(studentMasterOBJ.optString("studentPhoto"),"");
                                    String personalEmail=Util.getFreshValue(studentMasterOBJ.optString("personalEmail"),"");
                                    String mobilePhone=Util.getFreshValue(studentMasterOBJ.optString("mobilePhone"),"");
                                    String alternateId=Util.getFreshValue(studentMasterOBJ.optString("alternateId"),"");
                                    String section=Util.getFreshValue(studentMasterOBJ.optString("section"),"");
                                    String gradeLevel=Util.getFreshValue(studentMasterOBJ.optString("gradeLevel"),"");
                                    ClassStudentModel model=new ClassStudentModel();
                                    model.setStudentName(name);
                                    model.setStudentPhoto(studentPhoto);
                                    model.setStudentID(studentInternalId);
                                    model.setStudentAltID(alternateId);
                                    model.setSection(section);
                                    model.setGrade(gradeLevel);
                                    model.setMob(mobilePhone);
                                    model.setEmail(personalEmail);
                                    itmList.add(model);



                                }
                                rvStudent.setVisibility(View.VISIBLE);
                                lnNoData.setVisibility(View.GONE);



                                setAdapter();



                            }else {
                                rvStudent.setVisibility(View.GONE);
                                lnNoData.setVisibility(View.VISIBLE);
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


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Intent intent=new Intent(getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            JSONArray paramsArray=new JSONArray();
                            JSONArray courseSectionArray=new JSONArray();
                            courseSectionArray.put(courseID);
                            try {
                                jsonObject.put("pageNumber",0);
                                jsonObject.put("_pageSize",0);
                                jsonObject.put("sortingModel",null);
                                jsonObject.put("filterParams",paramsArray);
                                jsonObject.put("courseSectionIds",courseSectionArray);
                                jsonObject.put("includeInactive",false);
                                jsonObject.put("profilePhoto",true);
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName", pref.getName());
                                jsonObject.put("_token", pref.getToken());
                                jsonObject.put("tenantId", AppData.tenatID);
                                jsonObject.put("schoolId", pref.getSchoolID());
                                getStudentList(jsonObject);
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