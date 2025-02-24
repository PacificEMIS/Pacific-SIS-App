package com.opensissasstudent.ui.student.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.MyScheduleListAdapter;
import com.opensissasstudent.adapter.TranscriptAdapter;
import com.opensissasstudent.databinding.ActivityTranscriptBinding;
import com.opensissasstudent.model.MyScheduleListModel;
import com.opensissasstudent.model.TranscriptModel;
import com.opensissasstudent.model.TranscriptReportCardModel;
import com.opensissasstudent.model.Transcriptmarkingperiodmodel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TranscriptActivity extends AppCompatActivity implements View.OnClickListener {
    Pref pref;
    ArrayList<TranscriptModel>transcriptList=new ArrayList<>();
    ArrayList<Transcriptmarkingperiodmodel>transcriptperiodList=new ArrayList<>();
    ArrayList<TranscriptReportCardModel>transcriptreporcardList=new ArrayList<>();
    ActivityTranscriptBinding binding;
    String gradeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_transcript);
        initView();
    }

    private void initView(){
        gradeID=getIntent().getStringExtra("gradeID");
        pref=new Pref(TranscriptActivity.this);

        binding.rvItem.setLayoutManager(new LinearLayoutManager(TranscriptActivity.this, LinearLayoutManager.VERTICAL, false));


        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        JSONObject transcriptOBJ=new JSONObject();
        try {
            transcriptOBJ.put("studentId",pref.getUserID());
            jsonArray.put(transcriptOBJ);
            jsonObject.put("schoolLogo",true);
            jsonObject.put("studentPhoto",false);
            jsonObject.put("gradeLagend",true);
            jsonObject.put("signatureLine",false);
            jsonObject.put("historicalGradeLavels",true);
            jsonObject.put("gradeLavels",gradeID);
            jsonObject.put("studentsDetailsForTranscripts",jsonArray);
            jsonObject.put("_tenantName",pref.getTenatName());
            jsonObject.put("_userName",pref.getName());
            jsonObject.put("_token",pref.getToken());
            jsonObject.put("_academicYear",pref.getAcademicYear());
            jsonObject.put("tenantId",pref.getTenatID());
            jsonObject.put("schoolId",pref.getSchoolID());
            jsonObject.put("academicYear",pref.getAcademicYear());
            getTranscript(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        binding.imgBack.setOnClickListener(this);
    }


    public void getTranscript(JSONObject jsonObject) {
        String url=pref.getAPI()+"Student/getTranscriptForStudents";


        new PostJsonDataParser(TranscriptActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("scholldetailsresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            Toast.makeText(TranscriptActivity.this,_message,Toast.LENGTH_LONG).show();

                        }else {


                            JSONArray studentsDetailsForTranscripts=response.optJSONArray("studentsDetailsForTranscripts");

                                JSONObject transcriptOBJ=studentsDetailsForTranscripts.optJSONObject(0);
                                String schoolName=transcriptOBJ.optString("schoolName");
                                String schoolPicture=transcriptOBJ.optString("schoolPicture");
                                String streetAddress1=transcriptOBJ.optString("streetAddress1");
                                String streetAddress2=transcriptOBJ.optString("streetAddress2");
                                String city=transcriptOBJ.optString("city");
                                String state=transcriptOBJ.optString("state");
                                String zip=transcriptOBJ.optString("zip");
                                binding.tvSchlName.setText(schoolName);
                                binding.tvSchlAddress.setText(streetAddress1+","+streetAddress2+"\n"+city+","+state+","+zip);

                                String firstGivenName=transcriptOBJ.optString("firstGivenName");
                            String lastFamilyName=transcriptOBJ.optString("lastFamilyName");
                            binding.tvStudentName.setText(firstGivenName+" "+lastFamilyName);
                            String homeAddressLineOne=Util.getFreshValue(transcriptOBJ.optString("homeAddressLineOne"),"");
                            String homeAddressZip=Util.getFreshValue(transcriptOBJ.optString("homeAddressZip"),"");
                            String homeAddressState=Util.getFreshValue(transcriptOBJ.optString("homeAddressState"),"");

                            binding.tvStudentAddress.setText(homeAddressLineOne+"\n"+homeAddressState+","+homeAddressZip);

                            String dob=Util.getFreshValue(transcriptOBJ.optString("dob").replaceAll("T00:00:00",""),"");
                            binding.tvStudentDOB.setText("DOB: "+Util.changeAnyDateFormat(dob,"yyyy-MM-dd","dd MMM, yyyy"));
                            String studentInternalId=Util.getFreshValue(transcriptOBJ.optString("studentInternalId"),"");
                            binding.tvStudentID.setText("ID: "+studentInternalId);

                            double cumulativeGPA=transcriptOBJ.optDouble("cumulativeGPA");
                            binding.tvCumulativeGPA.setText(""+cumulativeGPA);

                            double totalCreditAttempeted=transcriptOBJ.optDouble("totalCreditAttempeted");
                            binding.tvCreditAttemted.setText(""+totalCreditAttempeted);

                            double totalCreditEarned=transcriptOBJ.optDouble("totalCreditEarned");
                            binding.tvCreditEarned.setText(""+totalCreditEarned);


                            if (!schoolPicture.equals("")) {
                                byte[] decodedString = Base64.decode(schoolPicture, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                binding.imgSchlImage.setImageBitmap(decodedByte);
                            }
                                JSONArray gradeLevelDetailsForTranscripts=transcriptOBJ.optJSONArray("gradeLevelDetailsForTranscripts");
                                for (int i=0;i<gradeLevelDetailsForTranscripts.length();i++){
                                    transcriptperiodList=new ArrayList<>();


                                    JSONObject gradeOBJ=gradeLevelDetailsForTranscripts.optJSONObject(i);
                                    JSONArray markingPeriodDetailsForTranscripts=gradeOBJ.optJSONArray("markingPeriodDetailsForTranscripts");
                                    for (int j=0;j<markingPeriodDetailsForTranscripts.length();j++){
                                        JSONObject periodOBJ=markingPeriodDetailsForTranscripts.optJSONObject(j);
                                        JSONArray reportCardDetailsForTranscripts=periodOBJ.optJSONArray("reportCardDetailsForTranscripts");
                                        String markingPeriodTitle=periodOBJ.optString("markingPeriodTitle");
                                        int creditEarned=periodOBJ.optInt("creditEarned");
                                        int creditAttemped=periodOBJ.optInt("creditAttemped");
                                        Transcriptmarkingperiodmodel periodModel=new Transcriptmarkingperiodmodel();
                                        periodModel.setMarkingPeriodTitle(markingPeriodTitle);
                                        periodModel.setReprtCardArray(reportCardDetailsForTranscripts.toString());
                                        periodModel.setCreditAttemted(creditAttemped);
                                        periodModel.setCreditEarned(creditEarned);
                                        transcriptperiodList.add(periodModel);
                                    }

                                    TranscriptModel transcriptModel=new TranscriptModel();
                                    String gradeLevelTitle=gradeOBJ.optString("gradeLevelTitle");
                                    String gradeschoolName=gradeOBJ.optString("schoolName");
                                    String schoolYear=gradeOBJ.optString("schoolYear");


                                    transcriptModel.setGradeLevelTitle(gradeLevelTitle);
                                    transcriptModel.setSchoolName(gradeschoolName);
                                    transcriptModel.setSchoolYear(schoolYear);

                                    transcriptModel.setPeriodList(transcriptperiodList);
                                    transcriptList.add(transcriptModel);

                                }










                            TranscriptAdapter transcriptAdapter=new TranscriptAdapter(transcriptList,TranscriptActivity.this);
                            binding.rvItem.setAdapter(transcriptAdapter);

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
        if (v==binding.imgBack){
            onBackPressed();
        }

    }
}