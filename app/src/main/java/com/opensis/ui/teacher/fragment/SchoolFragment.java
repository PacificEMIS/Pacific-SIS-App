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
import com.opensis.adapter.TeacherSchoolAdapter;
import com.opensis.model.ClassStudentModel;
import com.opensis.model.TeacheSchoolModel;
import com.opensis.model.TeacherClassesModel;
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


public class SchoolFragment extends Fragment {
    View view;
    RecyclerView rvSchool;
    ArrayList<TeacheSchoolModel> itmList=new ArrayList<>();
    Pref pref;
    JSONObject jsonObject=new JSONObject();
    LinearLayout lnNoData;
    String address;

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
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        rvSchool=(RecyclerView)view.findViewById(R.id.rvSchool);
        rvSchool.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        JSONObject obj=new JSONObject();
        JSONObject accessobj=new JSONObject();
        try {
            obj.put("tenantId",AppData.tenatID);
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
        TeacherSchoolAdapter schoolAdapter=new TeacherSchoolAdapter(itmList,getContext());
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
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();

                            lnNoData.setVisibility(View.VISIBLE);
                            rvSchool.setVisibility(View.GONE);

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

                                    address=streetAddress1+" "+streetAddress2+" "+city+" "+state+" "+county+" "+zip+" "+country;
                                    int schoolId=schoolMasterOBJ.optInt("schoolId");
                                    TeacheSchoolModel schoolModel=new TeacheSchoolModel();
                                    schoolModel.setSchoolAddress(address);
                                    schoolModel.setSchoolID(schoolId);
                                    schoolModel.setSchoolName(schoolName);
                                    itmList.add(schoolModel);



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
                            Intent intent=new Intent(getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

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
                                jsonObject.put("tenantId", AppData.tenatID);
                                jsonObject.put("_userName", pref.getName());
                                jsonObject.put("_token", pref.getToken());
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