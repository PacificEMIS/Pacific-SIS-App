package com.opensissasstudent.ui.student.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensissasstudent.R;
import com.opensissasstudent.databinding.FragmentMyInfoGeneralBinding;


public class MyInfoGeneralFragment extends Fragment {
    FragmentMyInfoGeneralBinding binding;
    private View view;
    String studentId,alternateId,districtId,stateId,admissionNumber,rollNumber,firstGivenName,middleName,lastFamilyName,preferredName,previousName,socialSecurityNumber,otherGovtIssuedNumber,studentPhoto,dob,studentPortalId,gender,race,ethnicity;
    String maritalStatus,countryOfBirth,nationality,firstLanguageId,secondLanguageId,thirdLanguageId,loginEmail;
    String portalAccess;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_info_general,container,false);
        view=binding.getRoot();
        initView();
        return view;
    }


    private void initView(){
        firstGivenName=getArguments().getString("firstGivenName");
        middleName=getArguments().getString("middleName");
        lastFamilyName=getArguments().getString("lastFamilyName");

        binding.tvStudentName.setText(firstGivenName+" "+middleName+" "+lastFamilyName);


        studentId=getArguments().getString("studentId");
        binding.tvStudentID.setText(studentId);

        alternateId=getArguments().getString("alternateId");
        binding.tvAltID.setText(alternateId);

        districtId=getArguments().getString("districtId");
        binding.tvDistricID.setText(districtId);

        stateId=getArguments().getString("stateId");
        binding.tvStateID.setText(stateId);

        admissionNumber=getArguments().getString("admissionNumber");
        binding.tvAdmissionNumber.setText(admissionNumber);

        rollNumber=getArguments().getString("rollNumber");
        binding.tvRollNumber.setText(rollNumber);

        preferredName=getArguments().getString("preferredName");
        binding.tvCommonName.setText(preferredName);

        previousName=getArguments().getString("previousName");
        binding.tvMiddleName.setText(previousName);

        socialSecurityNumber=getArguments().getString("socialSecurityNumber");
        binding.tvSecurityNumber.setText(socialSecurityNumber);

        otherGovtIssuedNumber=getArguments().getString("otherGovtIssuedNumber");
        binding.tvGovtNumer.setText(otherGovtIssuedNumber);

        studentPhoto=getArguments().getString("studentPhoto");
        if (!studentPhoto.equals("-")) {
            byte[] decodedString = Base64.decode(studentPhoto, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            binding.imgStudent.setImageBitmap(decodedByte);
        }

        dob=getArguments().getString("dob");
        binding.tvDOB.setText(dob);


        gender=getArguments().getString("gender");
        binding.tvGender.setText(gender);


        race=getArguments().getString("race");
        binding.tvRace.setText(race);


        ethnicity=getArguments().getString("ethnicity");
        binding.tvEthnicity.setText(ethnicity);


        maritalStatus=getArguments().getString("maritalStatus");
        binding.tvMartialStatus.setText(maritalStatus);


        countryOfBirth=getArguments().getString("countryOfBirth");
        binding.tvCountry.setText(countryOfBirth);


        nationality=getArguments().getString("nationality");
        binding.tvNationality.setText(nationality);


        firstLanguageId=getArguments().getString("firstLanguageId");
        binding.tvFirstLanguage.setText(firstLanguageId);


        secondLanguageId=getArguments().getString("secondLanguageId");
        binding.tvScndLanguage.setText(secondLanguageId);


        thirdLanguageId=getArguments().getString("thirdLanguageId");
        binding.tvThrdLanguage.setText(thirdLanguageId);


        loginEmail=getArguments().getString("loginEmail");
        binding.tvEmail.setText(loginEmail);

        portalAccess=getArguments().getString("portalAccess");
        binding.tvPortalAccess.setText(portalAccess);
    }


}