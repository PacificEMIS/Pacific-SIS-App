package com.pacificstudentteacher.ui.student.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.databinding.FragmentSchoolEnreollmentBinding;
import com.pacificstudentteacher.others.utility.Util;


public class SchoolEnreollmentFragment extends Fragment {
    FragmentSchoolEnreollmentBinding binding;
    View view;

    String sectionId,estimatedGradDate,eligibility504,economicDisadvantage,freeLunchEligibility,specialEducationIndicator,lepIndicator,gradeLevelTitle,enrollmentDate,enrollmentCode,rollingOption,schoolName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_school_enreollment,container,false);
        view=binding.getRoot();
        initView();
        return view;
    }

    private void initView(){
        sectionId=getArguments().getString("sectionId");
        binding.tvSection.setText(sectionId);

        estimatedGradDate=getArguments().getString("estimatedGradDate");
        if (!estimatedGradDate.equals("")||estimatedGradDate!=null){
            binding.tvGraduationDate.setText(Util.changeAnyDateFormat(estimatedGradDate.replaceAll("T00:00:00",""),"yyyy-MM-dd","dd MMM,yyyy"));

        }

        eligibility504=getArguments().getString("eligibility504");
        if (eligibility504.equalsIgnoreCase("True")){
            binding.imgEligibility.setImageResource(R.drawable.check_black_48dp);
            binding.imgEligibility.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvEligibility.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            binding.imgEligibility.setImageResource(R.drawable.close_black_48dp);
            binding.imgEligibility.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvEligibility.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));

        }


        economicDisadvantage=getArguments().getString("economicDisadvantage");
        if (economicDisadvantage.equalsIgnoreCase("True")){
            binding.imgEconomicDisavdantage.setImageResource(R.drawable.check_black_48dp);
            binding.imgEconomicDisavdantage.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvEconomicDisavdantage.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            binding.imgEconomicDisavdantage.setImageResource(R.drawable.close_black_48dp);
            binding.imgEconomicDisavdantage.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvEconomicDisavdantage.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        freeLunchEligibility=getArguments().getString("freeLunchEligibility");
        if (freeLunchEligibility.equalsIgnoreCase("True")){
            binding.imgFreeLunch.setImageResource(R.drawable.check_black_48dp);
            binding.imgFreeLunch.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvFreeLunch.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            binding.imgFreeLunch.setImageResource(R.drawable.close_black_48dp);
            binding.imgFreeLunch.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvFreeLunch.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        specialEducationIndicator=getArguments().getString("specialEducationIndicator");
        if (specialEducationIndicator.equalsIgnoreCase("True")){
            binding.imgEducationIndicator.setImageResource(R.drawable.check_black_48dp);
            binding.imgEducationIndicator.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvEducationIndicator.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            binding.imgEducationIndicator.setImageResource(R.drawable.close_black_48dp);
            binding.imgEducationIndicator.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvEducationIndicator.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        lepIndicator=getArguments().getString("lepIndicator");
        if (lepIndicator.equalsIgnoreCase("True")){
            binding.imgLEPIndicator.setImageResource(R.drawable.check_black_48dp);
            binding.imgLEPIndicator.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            binding.tvLEPIndicator.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            binding.imgLEPIndicator.setImageResource(R.drawable.close_black_48dp);
            binding.imgLEPIndicator.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            binding.tvLEPIndicator.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        gradeLevelTitle=getArguments().getString("gradeLevelTitle");
        binding.tvGradeLevel.setText(gradeLevelTitle);

        enrollmentDate=getArguments().getString("enrollmentDate");
        if (!enrollmentDate.equals("") ||enrollmentDate!=null){
            binding.tvDate.setText(Util.changeAnyDateFormat(enrollmentDate.replaceAll("T00:00:00",""),"yyyy-MM-dd","dd MMM,yyyy"));

        }

        enrollmentCode=getArguments().getString("enrollmentCode");
        binding.tvEnrollmentCode.setText(enrollmentCode);

        rollingOption=getArguments().getString("rollingOption");
        binding.tvRolling.setText(rollingOption);


        schoolName=getArguments().getString("schoolName");
        binding.tvEnrollMentScholl.setText(schoolName);




    }
}