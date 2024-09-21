package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
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
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.MedicalCriticalAlertAdapter;
import com.opensissasstudent.adapter.MedicalNoteAdapter;
import com.opensissasstudent.adapter.MyScheduleListAdapter;
import com.opensissasstudent.adapter.NurseVisitAdapter;
import com.opensissasstudent.adapter.PhyiscalRecordAdapter;
import com.opensissasstudent.databinding.FragmentMyInfoGeneralBinding;
import com.opensissasstudent.databinding.FragmentMyMedicalInfoBinding;
import com.opensissasstudent.model.MedicalAlertModel;
import com.opensissasstudent.model.MedicalNoteModel;
import com.opensissasstudent.model.MedicalNourseVisitModel;
import com.opensissasstudent.model.MedicalPhysicalImmunizationModel;
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


public class MyMedicalInfoFragment extends Fragment implements View.OnClickListener {

    FragmentMyMedicalInfoBinding binding;
    private View view;
    Pref pref;
    ArrayList<MedicalAlertModel> medicalAlertList = new ArrayList<>();
    ArrayList<MedicalPhysicalImmunizationModel> physicalrecordList = new ArrayList<>();
    ArrayList<MedicalNoteModel> medicalNoteList = new ArrayList<>();
    ArrayList<MedicalNourseVisitModel> nurseVisitList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_medical_info, container, false);
        view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        pref = new Pref(getContext());
        binding.rvAlert.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvMedicalNotes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvNurseRecord.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvPhysicalRecord.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


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

        binding.tvActivities.setOnClickListener(this);
        binding.tvProviderInformation.setOnClickListener(this);
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
                            startActivity(intent);

                        } else {
                            String _token = response.optString("_token");
                            pref.saveToken(_token);

                            JSONObject jsonObject1 = new JSONObject();

                            try {
                                jsonObject1.put("studentId", pref.getUserID());
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                getMedicalInformation(jsonObject1);
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

    public void getMedicalInformation(JSONObject jsonObject) {
        String url = pref.getAPI() + "Student/getAllStudentMedicalInfo";


        new PostJsonDataParser(getContext(), Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse", response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {

                            Toast.makeText(getContext(), _message, Toast.LENGTH_LONG).show();

                        } else {
                            medicalAlertList.clear();
                            physicalrecordList.clear();
                            medicalNoteList.clear();
                            nurseVisitList.clear();

                            JSONArray studentMedicalAlertList = response.optJSONArray("studentMedicalAlertList");


                            for (int i = 0; i < studentMedicalAlertList.length(); i++) {

                                JSONObject object = studentMedicalAlertList.optJSONObject(i);
                                String alertType = object.optString("alertType");
                                String alertDescription = Util.getFreshValue(object.optString("alertDescription"));
                                MedicalAlertModel medicalModel = new MedicalAlertModel();
                                medicalModel.setAlertType(alertType);
                                medicalModel.setAlertDescription(alertDescription);
                                medicalAlertList.add(medicalModel);


                            }
                            MedicalCriticalAlertAdapter medicalAdapter = new MedicalCriticalAlertAdapter(medicalAlertList);
                            binding.rvAlert.setAdapter(medicalAdapter);


                            JSONArray studentMedicalImmunizationList = response.optJSONArray("studentMedicalImmunizationList");


                            for (int i = 0; i < studentMedicalImmunizationList.length(); i++) {

                                JSONObject object = studentMedicalImmunizationList.optJSONObject(i);
                                String immunizationType = object.optString("immunizationType");
                                String comment = Util.getFreshValue(object.optString("comment"), "");
                                String immunizationDate = object.optString("immunizationDate").replaceAll("T00:00:00", "");
                                MedicalPhysicalImmunizationModel medicalModel = new MedicalPhysicalImmunizationModel();
                                medicalModel.setComment(comment);
                                medicalModel.setImmunizationDate(immunizationDate);
                                medicalModel.setImmunizationType(immunizationType);
                                physicalrecordList.add(medicalModel);


                            }
                            PhyiscalRecordAdapter phyiscalRecordAdapter = new PhyiscalRecordAdapter(physicalrecordList);
                            binding.rvPhysicalRecord.setAdapter(phyiscalRecordAdapter);


                            JSONArray studentMedicalNoteList = response.optJSONArray("studentMedicalNoteList");


                            for (int i = 0; i < studentMedicalNoteList.length(); i++) {

                                JSONObject object = studentMedicalNoteList.optJSONObject(i);
                                String medicalNote = Util.getFreshValue(object.optString("medicalNote"), "");
                                String noteDate = object.optString("noteDate").replaceAll("T00:00:00", "");
                                MedicalNoteModel noteModel = new MedicalNoteModel();
                                noteModel.setMedicalNote(medicalNote);
                                noteModel.setNoteDate(noteDate);
                                medicalNoteList.add(noteModel);


                            }

                            MedicalNoteAdapter noteAdapter = new MedicalNoteAdapter(medicalNoteList);
                            binding.rvMedicalNotes.setAdapter(noteAdapter);


                            JSONArray studentMedicalNurseVisitList = response.optJSONArray("studentMedicalNurseVisitList");


                            for (int i = 0; i < studentMedicalNurseVisitList.length(); i++) {

                                JSONObject object = studentMedicalNurseVisitList.optJSONObject(i);
                                String reason = Util.getFreshValue(object.optString("reason"), "-");
                                String result = Util.getFreshValue(object.optString("result"), "-");
                                String comment = Util.getFreshValue(object.optString("comment"), "-");
                                String nurseVisitDate = object.optString("nurseVisitDate").replaceAll("T00:00:00", "");
                                String timeIn = Util.getFreshValue(object.optString("timeIn"));
                                String timeOut = Util.getFreshValue(object.optString("timeOut"));
                                MedicalNourseVisitModel nurVisitModel = new MedicalNourseVisitModel();
                                nurVisitModel.setNurseVisitDate(nurseVisitDate);
                                nurVisitModel.setReason(reason);
                                nurVisitModel.setResult(result);
                                nurVisitModel.setComment(comment);
                                nurVisitModel.setTimeIn(timeIn.replaceAll("T", ""));
                                nurVisitModel.setTimeOut(timeOut.replaceAll("T", ""));
                                nurseVisitList.add(nurVisitModel);


                            }



                            NurseVisitAdapter nurseVisitAdapter = new NurseVisitAdapter(nurseVisitList);
                            binding.rvNurseRecord.setAdapter(nurseVisitAdapter);



                            if (studentMedicalNoteList.length()>0){
                                binding.llMedicalNotes.setVisibility(View.VISIBLE);
                            }

                            if (studentMedicalImmunizationList.length()>0){
                                binding.llPhysical.setVisibility(View.VISIBLE);
                            }

                            if (studentMedicalAlertList.length()>0){
                                binding.llAlertInformation.setVisibility(View.VISIBLE);
                            }

                            if (studentMedicalNurseVisitList.length()>0){
                                binding.llNurseVisit.setVisibility(View.VISIBLE);
                            }




                            JSONArray studentMedicalProviderList = response.optJSONArray("studentMedicalProviderList");


                            JSONObject object = studentMedicalProviderList.optJSONObject(0);

                            String primaryCarePhysician = Util.getFreshValue(object.optString("primaryCarePhysician"), "-");
                            binding.tvPrimaryPhysician.setText(primaryCarePhysician);

                            String primaryCarePhysicianPhone = Util.getFreshValue(object.optString("primaryCarePhysicianPhone"), "-");
                            binding.tvPrimaryPhysicianPhone.setText(primaryCarePhysicianPhone);

                            String preferredMedicalFacility = Util.getFreshValue(object.optString("preferredMedicalFacility"), "-");
                            binding.tvMedicalFacility.setText(preferredMedicalFacility);

                            String preferredMedicalFacilityPhone = Util.getFreshValue(object.optString("preferredMedicalFacilityPhone"), "-");
                            binding.tvMedicalFacilityPhn.setText(preferredMedicalFacilityPhone);

                            String insuranceCompany = Util.getFreshValue(object.optString("insuranceCompany"), "-");
                            binding.tvInsuranceCompanyPhn.setText(insuranceCompany);

                            String insuranceCompanyPhone = Util.getFreshValue(object.optString("insuranceCompanyPhone"), "-");
                            binding.tvInsuranceCompanyPhn.setText(insuranceCompanyPhone);

                            String policyNumber = Util.getFreshValue(object.optString("policyNumber"), "-");
                            binding.tvPolicyNumber.setText(policyNumber);

                            String policyHolderName = Util.getFreshValue(object.optString("policyHolderName"), "-");
                            binding.tvPolicyHolderName.setText(policyHolderName);

                            String dentistName = Util.getFreshValue(object.optString("dentistName"), "-");
                            binding.tvDentist.setText(dentistName);

                            String dentistPhone = Util.getFreshValue(object.optString("dentistPhone"), "-");
                            binding.tvDentistPhn.setText(dentistPhone);

                            String visionName = Util.getFreshValue(object.optString("visionName"), "-");
                            binding.tvVision.setText(visionName);

                            String visionProviderPhone = Util.getFreshValue(object.optString("visionProviderPhone"), "-");
                            binding.tvVisionPhn.setText(visionProviderPhone);


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == binding.tvActivities) {
            binding.tvActivities.setTextColor(Color.parseColor("#FFFFFF"));
            binding.tvProviderInformation.setTextColor(Color.parseColor("#094990"));
            binding.llActivities.setVisibility(View.VISIBLE);
            binding.llProvideInformation.setVisibility(View.GONE);
        } else if (v == binding.tvProviderInformation) {
            binding.tvActivities.setTextColor(Color.parseColor("#094990"));
            binding.tvProviderInformation.setTextColor(Color.parseColor("#FFFFFF"));
            binding.llActivities.setVisibility(View.GONE);
            binding.llProvideInformation.setVisibility(View.VISIBLE);
        }

    }
}