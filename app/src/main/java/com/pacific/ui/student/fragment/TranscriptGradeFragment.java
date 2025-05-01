package com.pacific.ui.student.fragment;

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
import com.pacific.R;
import com.pacific.adapter.TranscriptGradeAdapter;
import com.pacific.databinding.FragmentTranscriptGradeBinding;
import com.pacific.model.TranscriptGradeModel;
import com.pacific.others.parser.PostJsonDataParser;
import com.pacific.others.utility.Pref;
import com.pacific.ui.student.activity.TranscriptActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TranscriptGradeFragment extends Fragment implements View.OnClickListener {
    FragmentTranscriptGradeBinding binding;
    private View view;
    Pref pref;
    ArrayList<TranscriptGradeModel>itemList=new ArrayList<>();
    ArrayList<Integer>gradeIDList=new ArrayList<>();
    TranscriptGradeAdapter gradeAdapter;
    String gradID="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transcript_grade, container, false);
        view = binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        binding.rvListItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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

        binding.tvShow.setOnClickListener(this);
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
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                getGrade(jsonObject1);
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

    public void getGrade(JSONObject jsonObject) {
        String url=pref.getAPI()+"Gradelevel/getAllGradeLevels";


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
                            itemList.clear();

                            JSONArray tableGradelevelList=response.optJSONArray("tableGradelevelList");

                            for (int i=0;i<tableGradelevelList.length();i++){

                                JSONObject object=tableGradelevelList.optJSONObject(i);
                                String title=object.optString("title");
                                int gradeId=object.optInt("gradeId");
                                TranscriptGradeModel gradeModel=new TranscriptGradeModel();
                                gradeModel.setGradeName(title);
                                gradeModel.setGradeID(gradeId);
                                itemList.add(gradeModel);


                            }
                             gradeAdapter=new TranscriptGradeAdapter(itemList,getContext(),TranscriptGradeFragment.this);
                            binding.rvListItem.setAdapter(gradeAdapter);

                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void updateAttendanceStatus(int position, boolean status) {
        itemList.get(position).setSelected(status);
        if (itemList.get(position).isSelected() == true) {
            gradeIDList.add(itemList.get(position).getGradeID());


        } else {
            gradeIDList.remove(position);
        }

        gradID = gradeIDList.toString().replace("[", "").replace("]", "").replaceAll("\\s+", "");

        gradeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v==binding.tvShow){
            if (gradeIDList.size()>0){
                Intent intent=new Intent(getContext(), TranscriptActivity.class);
                intent.putExtra("gradeID",gradID);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(),"Please Select at least one grade",Toast.LENGTH_LONG).show();
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
                            JSONObject jsonObject1=new JSONObject();

                            try {
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                getGrade(jsonObject1);
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