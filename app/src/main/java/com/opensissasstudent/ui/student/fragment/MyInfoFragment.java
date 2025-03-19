package com.opensissasstudent.ui.student.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.common.activity.LoginActivity;
import com.opensissasstudent.ui.student.activity.SchoolDetailsActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class MyInfoFragment extends Fragment implements View.OnClickListener {
    TextView tvGeneralInfo, tvSchoolEnrollMent, tvAddressContact, tvFamily, tvMedical;
    View view;
    private FragmentTransaction transaction;
    Pref pref;
    String studentId, alternateId, districtId, stateId, admissionNumber, rollNumber, firstGivenName, middleName, lastFamilyName, preferredName, previousName, socialSecurityNumber, otherGovtIssuedNumber, studentPhoto, dob, studentPortalId, gender, race, ethnicity;
    String maritalStatus, countryOfBirth, nationality, firstLanguageId, secondLanguageId, thirdLanguageId, loginEmail, country;
    String portalAccess, sectionId, estimatedGradDate, eligibility504, economicDisadvantage, freeLunchEligibility, specialEducationIndicator, lepIndicator, gradeLevelTitle, enrollmentDate, enrollmentCode, rollingOption, schoolName;
    String homePhone, mobilePhone, personalEmail, schoolEmail, twitter, facebook, instagram, youtube, linkedin, homeAddressLineOne, homeAddressLineTwo, homeAddressCity, homeAddressState, homeAddressCountry, homeAddressZip, busNo, mailingAddressLineOne, mailingAddressLineTwo, mailingAddressCity, mailingAddressState, mailingAddressCountry, mailingAddressZip, schoolBusPickUp, schoolBusDropOff;
    ArrayList<String> countryid = new ArrayList<>();
    ArrayList<String> languageid = new ArrayList<>();
    String currentDate, dateofbirth;
    int age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_info, container, false);
        initView();
        return view;
    }

    private void initView() {
        pref = new Pref(getContext());
        tvGeneralInfo = (TextView) view.findViewById(R.id.tvGeneralInfo);
        tvSchoolEnrollMent = (TextView) view.findViewById(R.id.tvSchoolEnrollMent);
        tvAddressContact = (TextView) view.findViewById(R.id.tvAddressContact);
        tvFamily = (TextView) view.findViewById(R.id.tvFamily);
        tvMedical = (TextView) view.findViewById(R.id.tvMedical);

        tvGeneralInfo.setOnClickListener(this);
        tvSchoolEnrollMent.setOnClickListener(this);
        tvAddressContact.setOnClickListener(this);
        tvFamily.setOnClickListener(this);
        tvMedical.setOnClickListener(this);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = df.format(c);

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


    }

    @Override
    public void onClick(View v) {
        if (v == tvGeneralInfo) {
            viewGeneralInfoFragment();
        } else if (v == tvSchoolEnrollMent) {
            viewSchoolEnrollMentFragment();
        } else if (v == tvAddressContact) {
            viewAddressInfoFragment();
        } else if (v == tvFamily) {
            viewFamilyInfoFragment();
        } else if (v == tvMedical) {
            viewMedicalInfoFragment();
        }


    }

    private void viewGeneralInfoFragment() {

        MyInfoGeneralFragment fragment = new MyInfoGeneralFragment();
        Bundle bundle = new Bundle();
        bundle.putString("studentId", studentId);
        bundle.putString("alternateId", alternateId);
        bundle.putString("districtId", districtId);
        bundle.putString("stateId", stateId);
        bundle.putString("admissionNumber", admissionNumber);
        bundle.putString("rollNumber", rollNumber);
        bundle.putString("firstGivenName", firstGivenName);
        bundle.putString("middleName", middleName);
        bundle.putString("lastFamilyName", lastFamilyName);
        bundle.putString("preferredName", preferredName);
        bundle.putString("previousName", previousName);
        bundle.putString("socialSecurityNumber", socialSecurityNumber);
        bundle.putString("otherGovtIssuedNumber", otherGovtIssuedNumber);
        bundle.putString("studentPhoto", studentPhoto);
        bundle.putString("dob", dateofbirth);
        bundle.putString("gender", gender);
        bundle.putString("race", race);
        bundle.putString("ethnicity", ethnicity);
        bundle.putString("maritalStatus", maritalStatus);
        bundle.putString("countryOfBirth", country);
        bundle.putString("nationality", nationality);
        bundle.putString("firstLanguageId", firstLanguageId);
        bundle.putString("secondLanguageId", secondLanguageId);
        bundle.putString("thirdLanguageId", thirdLanguageId);
        bundle.putString("loginEmail", loginEmail);
        bundle.putString("portalAccess", portalAccess);
        fragment.setArguments(bundle);
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lnSchoolFragment, fragment, "generalinfo");
        transaction.commit();

        tvGeneralInfo.setTextColor(Color.parseColor("#094990"));
        tvSchoolEnrollMent.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAddressContact.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvFamily.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvMedical.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvGeneralInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
        tvSchoolEnrollMent.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvAddressContact.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvFamily.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvMedical.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));


    }

    private void viewSchoolEnrollMentFragment() {

        SchoolEnreollmentFragment fragment = new SchoolEnreollmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sectionId", sectionId);
        bundle.putString("estimatedGradDate", estimatedGradDate);
        bundle.putString("eligibility504", eligibility504);
        bundle.putString("economicDisadvantage", economicDisadvantage);
        bundle.putString("freeLunchEligibility", freeLunchEligibility);
        bundle.putString("specialEducationIndicator", specialEducationIndicator);
        bundle.putString("lepIndicator", lepIndicator);
        bundle.putString("gradeLevelTitle", gradeLevelTitle);
        bundle.putString("enrollmentDate", enrollmentDate);
        bundle.putString("enrollmentCode", enrollmentCode);
        bundle.putString("rollingOption", rollingOption);
        bundle.putString("schoolName", schoolName);
        fragment.setArguments(bundle);
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lnSchoolFragment, fragment, "schollenrollment");
        transaction.commit();

        tvGeneralInfo.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvSchoolEnrollMent.setTextColor(Color.parseColor("#094990"));
        tvAddressContact.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvFamily.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvMedical.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvGeneralInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvSchoolEnrollMent.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
        tvAddressContact.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvFamily.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvMedical.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));


    }


    private void viewAddressInfoFragment() {

        MyInfoAddressFragment fragment = new MyInfoAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("homePhone", homePhone);
        bundle.putString("mobilePhone", mobilePhone);
        bundle.putString("personalEmail", personalEmail);
        bundle.putString("schoolEmail", schoolEmail);
        bundle.putString("twitter", twitter);
        bundle.putString("facebook", facebook);
        bundle.putString("instagram", instagram);
        bundle.putString("youtube", youtube);
        bundle.putString("linkedin", linkedin);
        bundle.putString("homeAddressLineOne", homeAddressLineOne);
        bundle.putString("homeAddressLineTwo", homeAddressLineTwo);
        bundle.putString("homeAddressCity", homeAddressCity);
        bundle.putString("homeAddressState", homeAddressState);
        bundle.putString("homeAddressCountry", homeAddressCountry);
        bundle.putString("homeAddressZip", homeAddressZip);
        bundle.putString("busNo", busNo);
        bundle.putString("mailingAddressLineOne", mailingAddressLineOne);
        bundle.putString("mailingAddressLineTwo", mailingAddressLineTwo);
        bundle.putString("mailingAddressCity", mailingAddressCity);
        bundle.putString("mailingAddressState", mailingAddressState);
        bundle.putString("mailingAddressCountry", mailingAddressCountry);
        bundle.putString("mailingAddressZip", mailingAddressZip);
        bundle.putString("schoolBusPickUp", schoolBusPickUp);
        bundle.putString("schoolBusDropOff", schoolBusDropOff);
        fragment.setArguments(bundle);
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lnSchoolFragment, fragment, "myinfoaddress");
        transaction.commit();

        tvGeneralInfo.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvSchoolEnrollMent.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAddressContact.setTextColor(Color.parseColor("#094990"));
        tvFamily.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvMedical.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvGeneralInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvSchoolEnrollMent.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvAddressContact.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
        tvFamily.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvMedical.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));


    }


    private void viewFamilyInfoFragment() {

        FamilyInfoFragment fragment = new FamilyInfoFragment();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lnSchoolFragment, fragment, "familyinfo");
        transaction.commit();

        tvGeneralInfo.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvSchoolEnrollMent.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAddressContact.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvFamily.setTextColor(Color.parseColor("#094990"));
        tvMedical.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvGeneralInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvSchoolEnrollMent.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvAddressContact.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvFamily.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));
        tvMedical.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));


    }


    private void viewMedicalInfoFragment() {

        MyMedicalInfoFragment fragment = new MyMedicalInfoFragment();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.lnSchoolFragment, fragment, "familyinfo");
        transaction.commit();

        tvGeneralInfo.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvSchoolEnrollMent.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvAddressContact.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvFamily.setTextColor(Color.parseColor("#FFFFFFFF"));
        tvMedical.setTextColor(Color.parseColor("#094990"));

        tvGeneralInfo.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvSchoolEnrollMent.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvAddressContact.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvFamily.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
        tvMedical.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.white));


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

                        } else {
                            String _token = response.optString("_token");
                            pref.saveToken(_token);
                            JSONObject sudentMasterOBJ = new JSONObject();
                            try {
                                sudentMasterOBJ.put("academicYear", pref.getAcademicYear());
                                sudentMasterOBJ.put("studentId", pref.getUserID());
                                sudentMasterOBJ.put("tenantId", pref.getTenatID());
                                sudentMasterOBJ.put("schoolId", pref.getSchoolID());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject jsonObject1 = new JSONObject();

                            try {
                                jsonObject1.put("studentMaster", sudentMasterOBJ);
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                getMyInfo(jsonObject1);
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

    public void getMyInfo(JSONObject jsonObject) {
        String url = pref.getAPI() + "Student/viewStudent";


        new PostJsonDataParser(getContext(), Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse", response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {
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

                        } else {
                            JSONObject studentMaster = response.optJSONObject("studentMaster");
                            studentId = Util.getFreshValue(studentMaster.optString("studentId", "-"));
                            alternateId = Util.getFreshValue(studentMaster.optString("alternateId", "-"));
                            districtId = Util.getFreshValue(studentMaster.optString("districtId", "-"));
                            stateId = Util.getFreshValue(studentMaster.optString("stateId", "-"));
                            admissionNumber = Util.getFreshValue(studentMaster.optString("admissionNumber", "-"));
                            rollNumber = Util.getFreshValue(studentMaster.optString("rollNumber", "-"));
                            firstGivenName = Util.getFreshValue(studentMaster.optString("firstGivenName", "-"));
                            middleName = Util.getFreshValue(studentMaster.optString("middleName", ""));
                            lastFamilyName = Util.getFreshValue(studentMaster.optString("lastFamilyName", "-"));
                            preferredName = Util.getFreshValue(studentMaster.optString("preferredName", "-"));
                            previousName = Util.getFreshValue(studentMaster.optString("previousName", "-"));
                            socialSecurityNumber = Util.getFreshValue(studentMaster.optString("socialSecurityNumber", "-"));
                            otherGovtIssuedNumber = Util.getFreshValue(studentMaster.optString("otherGovtIssuedNumber", "-"));
                            studentPhoto = Util.getFreshValue(studentMaster.optString("studentPhoto", "-"));
                            dob = Util.getFreshValue(studentMaster.optString("dob", "-").replaceAll("T00:00:00", ""));
                            if (!dob.equals("-")){
                                String month=Util.changeAnyDateFormat(dob,"yyyy-MM-dd","MM");
                                String day=Util.changeAnyDateFormat(dob,"yyyy-MM-dd","dd");
                                String year=Util.changeAnyDateFormat(dob,"yyyy-MM-dd","yyyy");
                                int m= Integer.parseInt(month);
                                int y= Integer.parseInt(year);
                                int d= Integer.parseInt(day);
                                age=getAge(y,m,d);
                                Log.d("age",String.valueOf(age));

                            }

                            dateofbirth = Util.changeAnyDateFormat(dob, "yyyy-MM-dd", "dd MMM, yyyy")+" Age: "+age+" Years" ;


                            gender = Util.getFreshValue(studentMaster.optString("gender", "-"));
                            race = Util.getFreshValue(studentMaster.optString("race", "-"));
                            ethnicity = Util.getFreshValue(studentMaster.optString("ethnicity", "-"));
                            maritalStatus = Util.getFreshValue(studentMaster.optString("maritalStatus", "-"));
                            countryOfBirth = Util.getFreshValue(studentMaster.optString("countryOfBirth", "-"));
                            nationality = Util.getFreshValue(studentMaster.optString("nationality", "-"));
                            firstLanguageId = Util.getFreshValue(studentMaster.optString("firstLanguageId", "-"));
                            secondLanguageId = Util.getFreshValue(studentMaster.optString("secondLanguageId", "-"));
                            thirdLanguageId = Util.getFreshValue(studentMaster.optString("thirdLanguageId", "-"));
                            loginEmail = Util.getFreshValue(response.optString("loginEmail", "-"));
                            boolean bportalAccess = response.optBoolean("portalAccess");
                            if (bportalAccess) {
                                portalAccess = "Yes";
                            } else {
                                portalAccess = "No";
                            }
                            boolean beligibility504 = studentMaster.optBoolean("eligibility504");
                            if (beligibility504) {
                                eligibility504 = "Yes";
                            } else {
                                eligibility504 = "No";
                            }
                            boolean beconomicDisadvantage = studentMaster.optBoolean("economicDisadvantage");
                            if (beconomicDisadvantage) {
                                economicDisadvantage = "Yes";
                            } else {
                                economicDisadvantage = "No";
                            }
                            boolean bfreeLunchEligibility = studentMaster.optBoolean("freeLunchEligibility");
                            if (bfreeLunchEligibility) {
                                freeLunchEligibility = "Yes";
                            } else {
                                freeLunchEligibility = "No";
                            }
                            boolean bspecialEducationIndicator = studentMaster.optBoolean("specialEducationIndicator");
                            if (bspecialEducationIndicator) {
                                specialEducationIndicator = "Yes";
                            } else {
                                specialEducationIndicator = "No";
                            }
                            boolean blepIndicator = studentMaster.optBoolean("lepIndicator");
                            if (blepIndicator) {
                                lepIndicator = "Yes";
                            } else {
                                lepIndicator = "No";
                            }

                            sectionId = Util.getFreshValue(studentMaster.optString("sectionId", "-"));
                            estimatedGradDate = Util.getFreshValue(studentMaster.optString("estimatedGradDate", "-"));
                            JSONArray studentEnrollment = studentMaster.optJSONArray("studentEnrollment");
                            JSONObject enrollmentobj = studentEnrollment.optJSONObject(0);
                            gradeLevelTitle = Util.getFreshValue(enrollmentobj.optString("gradeLevelTitle"), "-");
                            enrollmentDate = Util.getFreshValue(enrollmentobj.optString("enrollmentDate"), "-");
                            enrollmentCode = Util.getFreshValue(enrollmentobj.optString("enrollmentCode"), "-");
                            rollingOption = Util.getFreshValue(enrollmentobj.optString("rollingOption"), "-");
                            schoolName = Util.getFreshValue(enrollmentobj.optString("schoolName"), "-");


                            homePhone = Util.getFreshValue(studentMaster.optString("homePhone", "-"));
                            mobilePhone = Util.getFreshValue(studentMaster.optString("mobilePhone", "-"));
                            personalEmail = Util.getFreshValue(studentMaster.optString("personalEmail", "-"));
                            schoolEmail = Util.getFreshValue(studentMaster.optString("schoolEmail", "-"));
                            twitter = Util.getFreshValue(studentMaster.optString("twitter", ""));
                            facebook = Util.getFreshValue(studentMaster.optString("facebook", ""));
                            instagram = Util.getFreshValue(studentMaster.optString("instagram", ""));
                            youtube = Util.getFreshValue(studentMaster.optString("youtube", ""));
                            linkedin = Util.getFreshValue(studentMaster.optString("linkedin", ""));
                            homeAddressLineOne = Util.getFreshValue(studentMaster.optString("homeAddressLineOne", "-"));
                            homeAddressLineTwo = Util.getFreshValue(studentMaster.optString("homeAddressLineTwo", "-"));
                            homeAddressCity = Util.getFreshValue(studentMaster.optString("homeAddressCity", "-"));
                            homeAddressState = Util.getFreshValue(studentMaster.optString("homeAddressState", "-"));
                            homeAddressCountry = Util.getFreshValue(studentMaster.optString("homeAddressCountry", "-"));
                            homeAddressZip = Util.getFreshValue(studentMaster.optString("homeAddressZip", "-"));
                            busNo = Util.getFreshValue(studentMaster.optString("busNo", "-"));
                            mailingAddressLineOne = Util.getFreshValue(studentMaster.optString("mailingAddressLineOne", "-"));
                            mailingAddressLineTwo = Util.getFreshValue(studentMaster.optString("mailingAddressLineTwo", "-"));
                            mailingAddressCity = Util.getFreshValue(studentMaster.optString("mailingAddressCity", "-"));
                            mailingAddressState = Util.getFreshValue(studentMaster.optString("mailingAddressState", "-"));
                            mailingAddressCountry = Util.getFreshValue(studentMaster.optString("mailingAddressCountry", "-"));
                            mailingAddressZip = Util.getFreshValue(studentMaster.optString("mailingAddressZip", "-"));
                            boolean bschoolBusPickUp = studentMaster.optBoolean("mailingAddressZip");
                            if (bschoolBusPickUp) {
                                schoolBusPickUp = "Yes";
                            } else {
                                schoolBusPickUp = "No";
                            }
                            boolean bschoolBusDropOff = studentMaster.optBoolean("schoolBusDropOff");
                            if (bschoolBusDropOff) {
                                schoolBusDropOff = "Yes";
                            } else {
                                schoolBusDropOff = "No";
                            }


                        }

                        JSONObject countryobj = new JSONObject();
                        countryobj.put("isCountryAvailable", true);
                        countryobj.put("_tenantName", pref.getTenatName());
                        countryobj.put("_userName", pref.getName());
                        countryobj.put("_token", pref.getToken());
                        countryobj.put("_academicYear", pref.getAcademicYear());
                        countryobj.put("tenantId", pref.getTenatID());
                        countryobj.put("schoolId", pref.getSchoolID());
                        getCountryDetails(jsonObject);


                    } catch (Exception e) {
                        e.printStackTrace();
                        JSONObject countryobj = new JSONObject();
                        try {
                            countryobj.put("isCountryAvailable", true);
                            countryobj.put("_tenantName", pref.getTenatName());
                            countryobj.put("_userName", pref.getName());
                            countryobj.put("_token", pref.getToken());
                            countryobj.put("_academicYear", pref.getAcademicYear());
                            countryobj.put("tenantId", pref.getTenatID());
                            countryobj.put("schoolId", pref.getSchoolID());
                            getCountryDetails(jsonObject);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }


                    }
                }
            }
        });


    }


    public void getCountryDetails(JSONObject jsonObject) {
        String url = pref.getAPI() + "Common/getAllCountries";


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
                            JSONArray tableCountry = response.optJSONArray("tableCountry");
                            for (int i = 0; i < tableCountry.length(); i++) {
                                JSONObject countryOBJ = tableCountry.optJSONObject(i);
                                String id = countryOBJ.optString("id");
                                countryid.add(id);
                            }
                            int pos = countryid.indexOf(countryOfBirth);
                            int nationalitypos = countryid.indexOf(nationality);
                            JSONObject obj = tableCountry.optJSONObject(pos);
                            country = obj.optString("name");

                            JSONObject nationalityobj = tableCountry.optJSONObject(nationalitypos);
                            nationality = nationalityobj.optString("name");


                        }

                        JSONArray languageArray = new JSONArray();
                        JSONObject languageOBJ = new JSONObject();
                        languageOBJ.put("tableLanguage", languageArray);
                        languageOBJ.put("isLanguageAvailable", true);
                        languageOBJ.put("_tenantName", pref.getTenatName());
                        languageOBJ.put("_userName", pref.getName());
                        languageOBJ.put("_token", pref.getToken());
                        languageOBJ.put("_academicYear", pref.getAcademicYear());
                        languageOBJ.put("tenantId", pref.getTenatID());
                        languageOBJ.put("schoolId", pref.getSchoolID());
                        getLanguageDetails(languageOBJ);


                    } catch (Exception e) {
                        e.printStackTrace();
                        JSONArray languageArray = new JSONArray();
                        JSONObject languageOBJ = new JSONObject();
                        try {
                            languageOBJ.put("tableLanguage", languageArray);
                            languageOBJ.put("isLanguageAvailable", true);
                            languageOBJ.put("_tenantName", pref.getTenatName());
                            languageOBJ.put("_userName", pref.getName());
                            languageOBJ.put("_token", pref.getToken());
                            languageOBJ.put("_academicYear", pref.getAcademicYear());
                            languageOBJ.put("tenantId", pref.getTenatID());
                            languageOBJ.put("schoolId", pref.getSchoolID());
                            getLanguageDetails(languageOBJ);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }


                    }
                }
            }
        });


    }

    public void getLanguageDetails(JSONObject jsonObject) {
        String url = pref.getAPI() + "Common/getAllLanguage";


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
                            JSONArray tableLanguage = response.optJSONArray("tableLanguage");
                            for (int i = 0; i < tableLanguage.length(); i++) {
                                JSONObject OBJ = tableLanguage.optJSONObject(i);
                                String id = OBJ.optString("langId");
                                languageid.add(id);
                            }
                            int firstlanguagepos = languageid.indexOf(firstLanguageId);
                            JSONObject firstlanguageobj = tableLanguage.optJSONObject(firstlanguagepos);
                            firstLanguageId = firstlanguageobj.optString("locale");


                            int scndlanguagepos = languageid.indexOf(secondLanguageId);
                            JSONObject scndlanguageobj = tableLanguage.optJSONObject(scndlanguagepos);
                            secondLanguageId = scndlanguageobj.optString("locale");


                            int thirdLanguagepos = languageid.indexOf(thirdLanguageId);
                            JSONObject thirdLanguageobj = tableLanguage.optJSONObject(thirdLanguagepos);
                            thirdLanguageId = thirdLanguageobj.optString("locale");


                        }

                        viewGeneralInfoFragment();


                    } catch (Exception e) {
                        e.printStackTrace();
                        viewGeneralInfoFragment();
                    }
                }
            }
        });


    }


    public int getAge (int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;

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
                            JSONObject sudentMasterOBJ = new JSONObject();
                            try {
                                sudentMasterOBJ.put("academicYear", pref.getAcademicYear());
                                sudentMasterOBJ.put("studentId", pref.getUserID());
                                sudentMasterOBJ.put("tenantId", pref.getTenatID());
                                sudentMasterOBJ.put("schoolId", pref.getSchoolID());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject jsonObject1 = new JSONObject();

                            try {
                                jsonObject1.put("studentMaster", sudentMasterOBJ);
                                jsonObject1.put("_tenantName", pref.getTenatName());
                                jsonObject1.put("_userName", pref.getName());
                                jsonObject1.put("_token", pref.getToken());
                                jsonObject1.put("tenantId", pref.getTenatID());
                                jsonObject1.put("_academicYear", pref.getAcademicYear());
                                jsonObject1.put("schoolId", pref.getSchoolID());
                                getMyInfo(jsonObject1);
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