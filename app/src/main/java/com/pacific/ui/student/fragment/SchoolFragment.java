package com.pacific.ui.student.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.pacific.R;
import com.pacific.adapter.SchoolAdapter;
import com.pacific.model.TeacheSchoolModel;
import com.pacific.others.parser.PostJsonDataParser;
import com.pacific.others.utility.Pref;
import com.pacific.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SchoolFragment extends Fragment {
    View view;
    RecyclerView rvSchool;
    ArrayList<TeacheSchoolModel> itmList=new ArrayList<>();
    Pref pref;
    JSONObject jsonObject=new JSONObject();
    LinearLayout lnNoData;
    String address;
    String schoolLogo="";
    TextView tvCourseName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_school, container, false);
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        tvCourseName=(TextView)view.findViewById(R.id.tvCourseName);
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        rvSchool=(RecyclerView)view.findViewById(R.id.rvSchool);
        rvSchool.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        JSONObject obj=new JSONObject();
        JSONObject accessobj=new JSONObject();
        try {
            obj.put("tenantId",pref.getTenatID());
            obj.put("userId",0);
            obj.put("userAccessLog",accessobj);
            obj.put("_tenantName",pref.getTenatName());
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
        SchoolAdapter schoolAdapter=new SchoolAdapter(itmList,getContext());
        rvSchool.setAdapter(schoolAdapter);
    }


    public void getSchoolList(JSONObject jsonObject) {
        lnNoData.setVisibility(View.GONE);
        rvSchool.setVisibility(View.VISIBLE);
        String url=pref.getAPI()+"School/getAllSchoolList";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse",response.toString());
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

                                lnNoData.setVisibility(View.VISIBLE);
                                rvSchool.setVisibility(View.GONE);
                            }

                        }else {
                            JSONArray schoolMaster = response.optJSONArray("schoolMaster");
                            if (schoolMaster.length() > 0) {
                                for (int i=0;i<schoolMaster.length();i++){
                                    JSONObject schoolMasterOBJ=schoolMaster.optJSONObject(i);
                                    String schoolName=schoolMasterOBJ.optString("schoolName");
                                    String streetAddress1= Util.getFreshValue(schoolMasterOBJ.optString("streetAddress1"),"");
                                    String streetAddress2= Util.getFreshValue(schoolMasterOBJ.optString("streetAddress2"),"");
                                    String city= Util.getFreshValue(schoolMasterOBJ.optString("city"),"");
                                    String state= Util.getFreshValue(schoolMasterOBJ.optString("state"),"");
                                    String county= Util.getFreshValue(schoolMasterOBJ.optString("county"),"");
                                    String zip= Util.getFreshValue(schoolMasterOBJ.optString("zip"),"");
                                    String country= Util.getFreshValue(schoolMasterOBJ.optString("country"),"");
                                    JSONArray schoolDetail=schoolMasterOBJ.optJSONArray("schoolDetail");
                                    if (schoolDetail.length()>0){
                                        JSONObject masterOBJ=schoolDetail.optJSONObject(0);
                                        schoolLogo=masterOBJ.optString("schoolLogo");
                                    }else {
                                        schoolLogo="";
                                    }
                                    address=streetAddress1+" "+streetAddress2+" "+city+" "+state+" "+county+" "+zip+" "+country;
                                    int schoolId=schoolMasterOBJ.optInt("schoolId");
                                    TeacheSchoolModel schoolModel=new TeacheSchoolModel();
                                    schoolModel.setSchoolAddress(address);
                                    schoolModel.setSchoolID(schoolId);
                                    schoolModel.setSchoolName(schoolName);
                                    schoolModel.setSchoolLog(schoolLogo);
                                    itmList.add(schoolModel);



                                }


                                if (itmList.size()>1){
                                    tvCourseName.setText("Schools");
                                }else {
                                    tvCourseName.setText("School");
                                }


                                lnNoData.setVisibility(View.GONE);
                                rvSchool.setVisibility(View.VISIBLE);
                                    setAdapter();



                            }else {
                                lnNoData.setVisibility(View.VISIBLE);
                                rvSchool.setVisibility(View.GONE);
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
                            try {
                                jsonObject.put("pageNumber",0);
                                jsonObject.put("pageSize",0);
                                jsonObject.put("sortingModel",null);
                                jsonObject.put("filterParams",null);
                                jsonObject.put("includeInactive",false);
                                jsonObject.put("emailAddress",pref.getEmail());
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("tenantId", pref.getTenatID());
                                jsonObject.put("_userName", pref.getName());
                                jsonObject.put("_token", pref.getToken());
                                jsonObject.put("_academicYear", pref.getAcademicYear());
                                getSchoolList(jsonObject);
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
                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("pageNumber",0);
                                jsonObject.put("pageSize",0);
                                jsonObject.put("sortingModel",null);
                                jsonObject.put("filterParams",null);
                                jsonObject.put("includeInactive",false);
                                jsonObject.put("emailAddress",pref.getEmail());
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("tenantId", pref.getTenatID());
                                jsonObject.put("_userName", pref.getName());
                                jsonObject.put("_token", pref.getToken());
                                jsonObject.put("_academicYear", pref.getAcademicYear());
                                getSchoolList(jsonObject);
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