package com.opensis.ui.teacher.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.model.TeacherClassesModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;
import com.opensis.ui.teacher.fragment.ClassOverViewFragment;
import com.opensis.ui.teacher.fragment.SchoolGeneralInfoFragment;
import com.opensis.ui.teacher.fragment.SchoolWashInfoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SchoolDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvGeneralInfo,tvWashInfo;
    LinearLayout lnSchoolFragment;
    ImageView imgBack;
    private FragmentTransaction transaction;
    int schoolID;
    String schoolName,alternateName,schoolId,schoolAltId,schoolStateId,schoolDistrictId,schoolLevel,schoolClassification,affiliation,associations,lowestGradeLevel,highestGradeLevel,dateSchoolOpened,locale,gender;
    String internet,electricity,status,streetAddress1,streetAddress2,country,state,city,county,division,district,zip;
    String longitude,latitude;
    String nameOfPrincipal,nameOfAssistantPrincipal,telephone,fax,website,email,twitter,facebook,instagram,youtube,linkedIn;
    String runningWater,mainSourceOfDrinkingWater,currentlyAvailable,handwashingAvailable,soapAndWaterAvailable,hygeneEducation;
    String femaleToiletType,femaleToiletAccessibility;
    int totalFemaleToilets,totalFemaleToiletsUsable;

    String maleToiletType,maleToiletAccessibility;
    int totalMaleToilets,totalMaleToiletsUsable;

    String comonToiletType,commonToiletAccessibility;
    int totalCommonToilets,totalCommonToiletsUsable;


    JSONObject jsonObject=new JSONObject();
    Pref pref;
    TextView tvSchoolName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        initView();
    }

    private void initView(){
        pref=new Pref(SchoolDetailsActivity.this);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvSchoolName=(TextView)findViewById(R.id.tvSchoolName);
        schoolID=getIntent().getIntExtra("schoolID",0);


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


        tvGeneralInfo=(TextView)findViewById(R.id.tvGeneralInfo);
        tvWashInfo=(TextView)findViewById(R.id.tvWashInfo);

        lnSchoolFragment=(LinearLayout)findViewById(R.id.lnSchoolFragment);


        tvGeneralInfo.setOnClickListener(this);
        tvWashInfo.setOnClickListener(this);

    }

    private void viewGeneralInfoFragment(){

        SchoolGeneralInfoFragment fragment = new SchoolGeneralInfoFragment();
        Bundle bundle = new Bundle();
         bundle.putString("schoolName", schoolName);
        bundle.putString("schoolAltName", alternateName);
        bundle.putString("schoolID", schoolId);
        bundle.putString("schoolAltID", schoolAltId);
        bundle.putString("stateID", schoolStateId);
        bundle.putString("schoolDistrictId", schoolDistrictId);
        bundle.putString("schoolLevel", schoolLevel);
        bundle.putString("schoolClassification", schoolClassification);
        bundle.putString("affiliation", affiliation);
        bundle.putString("associations", associations);
        bundle.putString("lowestGradeLevel", lowestGradeLevel);
        bundle.putString("highestGradeLevel", highestGradeLevel);
        bundle.putString("dateSchoolOpened", dateSchoolOpened);
        bundle.putString("locale", locale);
        bundle.putString("gender", gender);
        bundle.putString("internet", internet);
        bundle.putString("electricity", electricity);
        bundle.putString("status", status);
        bundle.putString("streetAddress1", streetAddress1);
        bundle.putString("streetAddress2", streetAddress2);
        bundle.putString("country", country);
        bundle.putString("state", state);
        bundle.putString("city", city);
        bundle.putString("county", county);
        bundle.putString("division", division);
        bundle.putString("district", district);
        bundle.putString("zip", zip);
        bundle.putString("longitude", longitude);
        bundle.putString("latitude", latitude);
        bundle.putString("linkedIn", linkedIn);
        bundle.putString("youtube", youtube);
        bundle.putString("instagram", instagram);
        bundle.putString("facebook", facebook);
        bundle.putString("twitter", twitter);
        bundle.putString("email", email);
        bundle.putString("website", website);
        bundle.putString("fax", fax);
        bundle.putString("telephone", telephone);
        bundle.putString("nameOfAssistantPrincipal", nameOfAssistantPrincipal);
        bundle.putString("nameOfPrincipal", nameOfPrincipal);



        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnSchoolFragment, fragment, "Overview");
        transaction.commit();

        tvGeneralInfo.setTextColor(Color.parseColor("#094990"));
        tvWashInfo.setTextColor(Color.parseColor("#FFFFFFFF"));

        tvGeneralInfo.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        tvWashInfo.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));





    }

    private void viewWashInfoFragment(){

        SchoolWashInfoFragment fragment = new SchoolWashInfoFragment();
        Bundle bundle = new Bundle();
         bundle.putString("runningWater", runningWater);
        bundle.putString("mainSourceOfDrinkingWater", mainSourceOfDrinkingWater);
        bundle.putString("currentlyAvailable", currentlyAvailable);
        bundle.putString("handwashingAvailable", handwashingAvailable);
        bundle.putString("soapAndWaterAvailable", soapAndWaterAvailable);
        bundle.putString("hygeneEducation", hygeneEducation);
        bundle.putString("femaleToiletType", femaleToiletType);
        bundle.putString("femaleToiletAccessibility", femaleToiletAccessibility);
        bundle.putString("totalFemaleToilets", String.valueOf(totalFemaleToilets));
        bundle.putString("totalFemaleToiletsUsable", String.valueOf(totalFemaleToiletsUsable));
        bundle.putString("totalMaleToiletsUsable", String.valueOf(totalMaleToiletsUsable));
        bundle.putString("totalMaleToilets", String.valueOf(totalMaleToilets));
        bundle.putString("maleToiletType", maleToiletType);
        bundle.putString("maleToiletAccessibility", maleToiletAccessibility);
        bundle.putString("comonToiletType", comonToiletType);
        bundle.putString("commonToiletAccessibility", commonToiletAccessibility);
        bundle.putString("totalCommonToilets", String.valueOf(totalCommonToilets));
        bundle.putString("totalCommonToiletsUsable", String.valueOf(totalCommonToiletsUsable));
        fragment.setArguments(bundle);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lnSchoolFragment, fragment, "wash");
        transaction.commit();

        tvGeneralInfo.setTextColor(Color.parseColor("#FFFFFF"));
        tvWashInfo.setTextColor(Color.parseColor("#094990"));

        tvGeneralInfo.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        tvWashInfo.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));





    }

    @Override
    public void onClick(View view) {
        if (view==tvGeneralInfo){
            viewGeneralInfoFragment();
        }else if (view==tvWashInfo){
            viewWashInfoFragment();
        }else if (view==imgBack){
            onBackPressed();
        }

    }

    public void getSchoolDetails(JSONObject jsonObject) {
        String url=pref.getAPI()+"School/viewSchool";


        new PostJsonDataParser(SchoolDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(SchoolDetailsActivity.this,_message,Toast.LENGTH_LONG).show();

                        }else {
                            JSONObject schoolMaster=response.optJSONObject("schoolMaster");
                            schoolName=schoolMaster.optString("schoolName");
                            tvSchoolName.setText(schoolName);
                            alternateName= Util.getFreshValue(schoolMaster.optString("alternateName"),"-");
                            schoolId=Util.getFreshValue(schoolMaster.optString("schoolInternalId"),"-");
                            schoolAltId= Util.getFreshValue(String.valueOf(schoolMaster.optInt("schoolInternalId")),"-");
                            schoolStateId= Util.getFreshValue(String.valueOf(schoolMaster.optInt("schoolStateId")),"-");
                            schoolDistrictId= Util.getFreshValue(String.valueOf(schoolMaster.optInt("schoolDistrictId")),"-");
                            schoolLevel=Util.getFreshValue(schoolMaster.optString("schoolLevel"),"-");
                            schoolClassification=Util.getFreshValue(schoolMaster.optString("schoolClassification"),"-");
                            streetAddress1=Util.getFreshValue(schoolMaster.optString("streetAddress1"),"-");
                            streetAddress2=Util.getFreshValue(schoolMaster.optString("streetAddress2"),"-");
                            country=Util.getFreshValue(schoolMaster.optString("country"),"-");
                            state=Util.getFreshValue(schoolMaster.optString("state"),"-");
                            city=Util.getFreshValue(schoolMaster.optString("city"),"-");
                            county=Util.getFreshValue(schoolMaster.optString("county"),"-");
                            division=Util.getFreshValue(schoolMaster.optString("division"),"-");
                            district=Util.getFreshValue(schoolMaster.optString("district"),"-");
                            zip=Util.getFreshValue(schoolMaster.optString("zip"),"-");
                            longitude= Util.getFreshValue(String.valueOf(schoolMaster.optDouble("longitude")),"");
                            latitude=Util.getFreshValue(String.valueOf(schoolMaster.optDouble("latitude")),"");


                            JSONArray schoolDetail=schoolMaster.optJSONArray("schoolDetail");
                            JSONObject schoolDetailOBJ=schoolDetail.optJSONObject(0);
                            affiliation=Util.getFreshValue(schoolDetailOBJ.optString("affiliation"),"-");
                            associations=Util.getFreshValue(schoolDetailOBJ.optString("associations"),"-");
                            lowestGradeLevel=Util.getFreshValue(schoolDetailOBJ.optString("lowestGradeLevel"),"-");
                            highestGradeLevel=Util.getFreshValue(schoolDetailOBJ.optString("highestGradeLevel"),"-");
                             dateSchoolOpened=schoolDetailOBJ.optString("dateSchoolOpened").replace("T00:00:00","");
                            locale=Util.getFreshValue(schoolDetailOBJ.optString("locale"),"-");
                            gender=Util.getFreshValue(schoolDetailOBJ.optString("gender"),"-");
                            boolean binternet=schoolDetailOBJ.optBoolean("internet");
                            if (binternet){
                                internet="Yes";
                            }else {
                                internet="-";
                            }
                            boolean belectricity=schoolDetailOBJ.optBoolean("electricity");
                            if (belectricity){
                                electricity="Yes";
                            }else {
                                electricity="-";
                            }
                            boolean bstatus=schoolDetailOBJ.optBoolean("status");
                            if (bstatus){
                                status="Active";
                            }else {
                                status="-";
                            }

                            nameOfPrincipal=Util.getFreshValue(schoolDetailOBJ.optString("nameOfPrincipal"),"-");
                            nameOfAssistantPrincipal=Util.getFreshValue(schoolDetailOBJ.optString("nameOfAssistantPrincipal"),"-");
                            telephone=Util.getFreshValue(schoolDetailOBJ.optString("telephone"),"-");
                            fax=Util.getFreshValue(schoolDetailOBJ.optString("fax"),"-");
                            website=Util.getFreshValue(schoolDetailOBJ.optString("website"),"-");
                            email=Util.getFreshValue(schoolDetailOBJ.optString("email"),"-");
                            twitter=Util.getFreshValue(schoolDetailOBJ.optString("twitter"),"-");
                            facebook=Util.getFreshValue(schoolDetailOBJ.optString("facebook"),"-");
                            instagram=Util.getFreshValue(schoolDetailOBJ.optString("instagram"),"-");
                            youtube=Util.getFreshValue(schoolDetailOBJ.optString("youtube"),"-");
                            linkedIn=Util.getFreshValue(schoolDetailOBJ.optString("linkedIn"),"-");

                            boolean brunningWater=schoolDetailOBJ.optBoolean("runningWater");
                            if (brunningWater){
                                runningWater="Yes";
                            }else {
                                runningWater="No";
                            }
                            mainSourceOfDrinkingWater=Util.getFreshValue(schoolDetailOBJ.optString("mainSourceOfDrinkingWater"),"-");

                            boolean bcurrentlyAvailable=schoolDetailOBJ.optBoolean("currentlyAvailable");
                             if (bcurrentlyAvailable){
                                 currentlyAvailable="Yes";
                             }else {
                                 currentlyAvailable="No";
                             }

                            boolean bhandwashingAvailable=schoolDetailOBJ.optBoolean("handwashingAvailable");
                            if (bhandwashingAvailable){
                                handwashingAvailable="Yes";
                            }else {
                                handwashingAvailable="No";
                            }

                            boolean bsoapAndWaterAvailable=schoolDetailOBJ.optBoolean("soapAndWaterAvailable");
                            if (bsoapAndWaterAvailable){
                                soapAndWaterAvailable="Yes";
                            }else {
                                soapAndWaterAvailable="No";
                            }
                            hygeneEducation=Util.getFreshValue(schoolDetailOBJ.optString("hygeneEducation"));
                            femaleToiletType=Util.getFreshValue(schoolDetailOBJ.optString("femaleToiletType"),"-");
                            femaleToiletAccessibility=Util.getFreshValue(schoolDetailOBJ.optString("femaleToiletAccessibility"),"-");
                            totalFemaleToilets=schoolDetailOBJ.optInt("totalFemaleToilets");
                            totalFemaleToiletsUsable=schoolDetailOBJ.optInt("totalFemaleToiletsUsable");
                            maleToiletType=Util.getFreshValue(schoolDetailOBJ.optString("maleToiletType"),"-");
                            maleToiletAccessibility=Util.getFreshValue(schoolDetailOBJ.optString("maleToiletAccessibility"),"-");
                            totalMaleToilets=schoolDetailOBJ.optInt("totalMaleToilets");
                            totalMaleToiletsUsable=schoolDetailOBJ.optInt("totalMaleToiletsUsable");
                            comonToiletType=Util.getFreshValue(schoolDetailOBJ.optString("comonToiletType"),"-");
                            commonToiletAccessibility=Util.getFreshValue(schoolDetailOBJ.optString("commonToiletAccessibility"),"-");
                            totalCommonToilets=schoolDetailOBJ.optInt("totalCommonToilets");
                            totalCommonToiletsUsable=schoolDetailOBJ.optInt("totalCommonToiletsUsable");


                            viewGeneralInfoFragment();

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


        new PostJsonDataParser(SchoolDetailsActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Intent intent=new Intent(SchoolDetailsActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            String _token=response.optString("_token");
                            pref.saveToken(_token);
                            JSONArray schoolArray=new JSONArray();
                            JSONObject schoolOBJ=new JSONObject();
                            try {
                                schoolOBJ.put("id",0);
                                schoolOBJ.put("status",true);
                                schoolOBJ.put("tenantId",AppData.tenatID);
                                schoolOBJ.put("schoolId",schoolID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            schoolArray.put(schoolOBJ);
                            JSONObject schoolMasetOBJ=new JSONObject();
                            try {
                                schoolMasetOBJ.put("schoolDetail",schoolArray);
                                schoolMasetOBJ.put("latitude",null);
                                schoolMasetOBJ.put("longitude",null);
                                schoolMasetOBJ.put("schoolId",schoolID);
                                schoolMasetOBJ.put("tenantId",AppData.tenatID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                jsonObject.put("schoolMaster",schoolMasetOBJ);
                                jsonObject.put("selectedCategoryId",0);
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName", pref.getName());
                                jsonObject.put("_token", pref.getToken());
                                jsonObject.put("tenantId", AppData.tenatID);

                                getSchoolDetails(jsonObject);
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