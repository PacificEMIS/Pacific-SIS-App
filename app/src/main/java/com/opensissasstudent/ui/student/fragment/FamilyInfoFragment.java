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
import com.opensissasstudent.adapter.FamilyInfoAdapter;
import com.opensissasstudent.adapter.SiblingInfoAdapter;
import com.opensissasstudent.databinding.FragmentFamilyInfoBinding;
import com.opensissasstudent.databinding.FragmentMyInfoGeneralBinding;
import com.opensissasstudent.model.FamilyInfoModel;
import com.opensissasstudent.model.SiblingModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.common.activity.LoginActivity;
import com.opensissasstudent.ui.student.activity.MissingAttendanceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FamilyInfoFragment extends Fragment {

    FragmentFamilyInfoBinding binding;
    private View view;
    Pref pref;
    ArrayList<FamilyInfoModel>familyList=new ArrayList<>();
    ArrayList<SiblingModel>sibilingList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_family_info,container,false);
        view=binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        binding.rvContactFamily.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvSibling.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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

                            Intent intent=new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);

                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);

                            JSONObject jsonObject1=new JSONObject();

                            try {
                                jsonObject1.put("studentId", pref.getUserID());
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                getFamilyInfo(jsonObject1);
                                getSiblingInfo(jsonObject1);
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

    public void getFamilyInfo(JSONObject jsonObject) {
        String url=pref.getAPI()+"ParentInfo/viewParentListForStudent";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(getContext(),"No family(s) info found",Toast.LENGTH_LONG).show();
                            binding.lnFamily.setVisibility(View.GONE);
                        }else {
                            JSONArray parentInfoListForView=response.optJSONArray("parentInfoListForView");
                            if (parentInfoListForView.length()>0) {
                                for (int i = 0; i < parentInfoListForView.length(); i++) {
                                    JSONObject parentOBJ = parentInfoListForView.optJSONObject(i);
                                    String firstname = parentOBJ.optString("firstname");
                                    String lastname = parentOBJ.optString("lastname");
                                    String contactType = parentOBJ.optString("contactType");
                                    String relationship = parentOBJ.optString("relationship");
                                    boolean isCustodian = parentOBJ.optBoolean("isCustodian");
                                    JSONObject parentAddress = parentOBJ.optJSONObject("parentAddress");
                                    JSONObject parentInfo = parentAddress.optJSONObject("parentInfo");
                                    String parentPhoto = Util.getFreshValue(parentInfo.optString("parentPhoto"), "");
                                    FamilyInfoModel familyInfoModel = new FamilyInfoModel();
                                    familyInfoModel.setName(firstname + " " + lastname);
                                    familyInfoModel.setContactType(contactType);
                                    familyInfoModel.setRelationship(relationship);
                                    familyInfoModel.setParentPhoto(parentPhoto);
                                    familyInfoModel.setCustodian(isCustodian);
                                    familyList.add(familyInfoModel);

                                }
                                FamilyInfoAdapter familyInfoAdapter = new FamilyInfoAdapter(familyList, getContext());
                                binding.rvContactFamily.setAdapter(familyInfoAdapter);
                            }else {
                                binding.lnFamily.setVisibility(View.GONE);
                            }


                        }







                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getSiblingInfo(JSONObject jsonObject) {
        String url=pref.getAPI()+"Student/viewSibling";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(getContext(),"No sibling(s) info found",Toast.LENGTH_LONG).show();
                            binding.lnSibling.setVisibility(View.GONE);

                        }else {
                            JSONArray studentMaster=response.optJSONArray("studentMaster");
                            if (studentMaster.length()>0) {
                                for (int i = 0; i < studentMaster.length(); i++) {
                                    JSONObject sibilingOBJ = studentMaster.optJSONObject(i);
                                    String firstGivenName=sibilingOBJ.optString("firstGivenName");
                                    String lastFamilyName=sibilingOBJ.optString("lastFamilyName");
                                    String studentPhoto=Util.getFreshValue(sibilingOBJ.optString("studentPhoto"),"");
                                    JSONObject schoolMaster=sibilingOBJ.optJSONObject("schoolMaster");
                                    String schoolName=schoolMaster.optString("schoolName");
                                    JSONArray studentEnrollment=sibilingOBJ.optJSONArray("studentEnrollment");
                                    JSONObject enrollmentOBJ=studentEnrollment.optJSONObject(0);
                                    String gradeLevelTitle=enrollmentOBJ.optString("gradeLevelTitle");
                                    SiblingModel siblingModel=new SiblingModel();
                                    siblingModel.setName(firstGivenName+" "+lastFamilyName);
                                    siblingModel.setStudentPhoto(studentPhoto);
                                    siblingModel.setSchoolName(schoolName);
                                    siblingModel.setGarde(gradeLevelTitle);
                                    sibilingList.add(siblingModel);



                                }

                                SiblingInfoAdapter siblingInfoAdapter=new SiblingInfoAdapter(sibilingList,getContext());
                                binding.rvSibling.setAdapter(siblingInfoAdapter);

                            }else {
                                binding.lnSibling.setVisibility(View.GONE);
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