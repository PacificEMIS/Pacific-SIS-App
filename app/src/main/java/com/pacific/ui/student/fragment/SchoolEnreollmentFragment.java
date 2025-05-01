package com.pacific.ui.student.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.pacific.R;
import com.pacific.adapter.EnrolmmentHistoryAdapter;
import com.pacific.databinding.FragmentSchoolEnreollmentBinding;
import com.pacific.model.EnrpllmentHistoryModel;
import com.pacific.others.parser.PostJsonDataParser;
import com.pacific.others.utility.Pref;
import com.pacific.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SchoolEnreollmentFragment extends Fragment {
    FragmentSchoolEnreollmentBinding binding;
    View view;
    Pref pref;

    String sectionId, estimatedGradDate, eligibility504, economicDisadvantage, freeLunchEligibility, specialEducationIndicator, lepIndicator, gradeLevelTitle, enrollmentDate="", enrollmentCode, rollingOption, schoolName;
    ArrayList<EnrpllmentHistoryModel> itemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_school_enreollment, container, false);
        view = binding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        pref = new Pref(getContext());
        sectionId = getArguments().getString("sectionId");
        binding.tvSection.setText(sectionId);
        binding.rvEnrollmentHistory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        estimatedGradDate = getArguments().getString("estimatedGradDate");
        binding.tvGraduationDate.setText(Util.changeAnyDateFormat(estimatedGradDate.replaceAll("T00:00:00", ""), "yyyy-MM-dd", "dd MMM, yyyy"));

        eligibility504 = getArguments().getString("eligibility504");
        if (eligibility504.equalsIgnoreCase("Yes")) {
            binding.imgEligibility.setImageResource(R.drawable.check_black_48dp);
            binding.imgEligibility.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvEligibility.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        } else {
            binding.imgEligibility.setImageResource(R.drawable.close_black_48dp);
            binding.imgEligibility.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvEligibility.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));

        }


        economicDisadvantage = getArguments().getString("economicDisadvantage");
        if (economicDisadvantage.equalsIgnoreCase("Yes")) {
            binding.imgEconomicDisavdantage.setImageResource(R.drawable.check_black_48dp);
            binding.imgEconomicDisavdantage.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvEconomicDisavdantage.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        } else {
            binding.imgEconomicDisavdantage.setImageResource(R.drawable.close_black_48dp);
            binding.imgEconomicDisavdantage.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvEconomicDisavdantage.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        freeLunchEligibility = getArguments().getString("freeLunchEligibility");
        if (freeLunchEligibility.equalsIgnoreCase("Yes")) {
            binding.imgFreeLunch.setImageResource(R.drawable.check_black_48dp);
            binding.imgFreeLunch.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvFreeLunch.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        } else {
            binding.imgFreeLunch.setImageResource(R.drawable.close_black_48dp);
            binding.imgFreeLunch.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvFreeLunch.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        specialEducationIndicator = getArguments().getString("specialEducationIndicator");
        if (specialEducationIndicator.equalsIgnoreCase("Yes")) {
            binding.imgEducationIndicator.setImageResource(R.drawable.check_black_48dp);
            binding.imgEducationIndicator.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvEducationIndicator.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        } else {
            binding.imgEducationIndicator.setImageResource(R.drawable.close_black_48dp);
            binding.imgEducationIndicator.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvEducationIndicator.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        lepIndicator = getArguments().getString("lepIndicator");
        if (lepIndicator.equalsIgnoreCase("Yes")) {
            binding.imgLEPIndicator.setImageResource(R.drawable.check_black_48dp);
            binding.imgLEPIndicator.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvLEPIndicator.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        } else {
            binding.imgLEPIndicator.setImageResource(R.drawable.close_black_48dp);
            binding.imgLEPIndicator.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvLEPIndicator.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        gradeLevelTitle = getArguments().getString("gradeLevelTitle");
        binding.tvGradeLevel.setText(gradeLevelTitle);

        enrollmentDate = getArguments().getString("enrollmentDate");
        if (!enrollmentDate.equals("") || !enrollmentDate.equals("null")) {
            binding.tvDate.setText(Util.changeAnyDateFormat(enrollmentDate.replaceAll("T00:00:00", ""), "yyyy-MM-dd", "dd MMM, yyyy"));
        }
        enrollmentCode = getArguments().getString("enrollmentCode");
        binding.tvEnrollmentCode.setText(enrollmentCode);

        rollingOption = getArguments().getString("rollingOption");
        binding.tvRolling.setText(rollingOption);


        schoolName = getArguments().getString("schoolName");
        binding.tvEnrollMentScholl.setText(schoolName);
        JSONObject jsonObject = new JSONObject();
        JSONObject blankobj = new JSONObject();
        JSONArray studentEnrollmentListForView = new JSONArray();
        studentEnrollmentListForView.put(blankobj);
        JSONArray studentEnrollments = new JSONArray();
        studentEnrollments.put(blankobj);
        try {
            jsonObject.put("studentEnrollments", studentEnrollments);
            jsonObject.put("studentEnrollmentListForView", studentEnrollmentListForView);
            jsonObject.put("studentGuid", "33116a2a-6613-4c39-ac36-561ad0a929a5");
            jsonObject.put("studentId", pref.getUserID());
            jsonObject.put("tenantId", pref.getTenatID());
            jsonObject.put("schoolId", pref.getSchoolID());
            jsonObject.put("academicYear", String.valueOf(pref.getAcademicYear()));
            jsonObject.put("_tenantName", pref.getTenatName());
            jsonObject.put("_userName", pref.getName());
            jsonObject.put("_token", pref.getToken());
            jsonObject.put("_academicYear", pref.getAcademicYear());
            getEnrollmentHistory(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void getEnrollmentHistory(JSONObject jsonObject) {
        String url = pref.getAPI() + "Student/getAllStudentEnrollment";


        new PostJsonDataParser(getContext(), Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("enrollmenthistory", response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {

                            //  Toast.makeText(getContext(), _message, Toast.LENGTH_LONG).show();
                            binding.llEnrollmentHistory.setVisibility(View.GONE);

                        } else {

                            JSONArray studentEnrollmentListForView = response.optJSONArray("studentEnrollmentListForView");
                            if (studentEnrollmentListForView.length() > 0) {
                                for (int i = 0; i < studentEnrollmentListForView.length(); i++) {
                                    JSONObject job = studentEnrollmentListForView.optJSONObject(0);
                                    String schoolName = job.optString("schoolName");
                                    String gradeLevelTitle = Util.getFreshValue(job.optString("gradeLevelTitle"), "");
                                    String enrollmentDate = Util.getFreshValue(job.optString("enrollmentDate").replaceAll("T00:00:00", ""), "");
                                    String enrollmentCode = Util.getFreshValue(job.optString("enrollmentCode"), "");
                                    String exitDate = Util.getFreshValue(job.optString("exitDate").replaceAll("T00:00:00", ""), "");
                                    String exitCode = Util.getFreshValue(job.optString("exitCode"), "");
                                    String enrollmentType = Util.getFreshValue(job.optString("enrollmentType"), "");
                                    String startYear = Util.getFreshValue(job.optString("startYear"), "");
                                    String endYear = Util.getFreshValue(job.optString("endYear"), "");
                                    EnrpllmentHistoryModel historyModel = new EnrpllmentHistoryModel();
                                    historyModel.setSchoolName(schoolName);
                                    historyModel.setGradeLevelTitle(gradeLevelTitle);
                                    historyModel.setEnrollmentDate(enrollmentDate);
                                    historyModel.setEnrollmentCode(enrollmentCode);
                                    historyModel.setExitDate(exitDate);
                                    historyModel.setExitCode(exitCode);
                                    historyModel.setEnrollmentType(enrollmentType);
                                    historyModel.setStartYear(startYear);
                                    historyModel.setEndYear(endYear);
                                    itemList.add(historyModel);

                                }
                                binding.llEnrollmentHistory.setVisibility(View.VISIBLE);
                                EnrolmmentHistoryAdapter historyAdapter = new EnrolmmentHistoryAdapter(itemList, getContext());
                                binding.rvEnrollmentHistory.setAdapter(historyAdapter);
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