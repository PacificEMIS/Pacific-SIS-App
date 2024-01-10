package com.opensissas.ui.teacher.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensissas.R;
import com.opensissas.adapter.ClassAssignmentAdapter;
import com.opensissas.model.ClassAssignmentModel;
import com.opensissas.others.parser.PostJsonDataParser;
import com.opensissas.others.utility.AppData;
import com.opensissas.others.utility.Pref;
import com.opensissas.others.utility.Util;
import com.opensissas.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssignmentsFragment extends Fragment implements View.OnClickListener {

    View view;
    RecyclerView rvItem;
    ArrayList<ClassAssignmentModel> itmList=new ArrayList<>();
    LinearLayout lnAssignment,lnCreateAssignment,lnUpdateAssignment;
    TextView tvAssignmentType,tvCancel,tvUpdateCancel;
    JSONObject jsonObject=new JSONObject();
    Pref pref;
    int courseSectionID;
    String monthFrstDate;
    String twomonthsDate,twoMonthsLastDate;
    EditText etTitle,etUpdateTitle;
    TextView tvSubmit,tvUpdateSubmit;
    LinearLayout lnCLose,lnUpdateCLose;
    String weightage=null;
    AlertDialog alerDialog1;
    int updateAssementTypeID,updateAssesmentCourseID;
    LinearLayout lnData,lnNoData,lnAddWeightPercentage,lnUpdateWeightPercentage,lnAccess;
    int courseID;
    EditText etAddWeightPercentage,etUpdateWeightPercentage;
    String gradescaletype;
    int periodID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_assignments, container, false);
        initView();
        return view;
    }

    private void initView(){
        pref=new Pref(getContext());
        etAddWeightPercentage=(EditText)view.findViewById(R.id.etAddWeightPercentage);
        etUpdateWeightPercentage=(EditText)view.findViewById(R.id.etUpdateWeightPercentage);
        courseID = Integer.parseInt(getArguments().getString("courseID"));
        periodID=getArguments().getInt("periodID");
        gradescaletype=getArguments().getString("gradescaletype");
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        lnData=(LinearLayout)view.findViewById(R.id.lnData);
        etTitle=(EditText)view.findViewById(R.id.etTitle);
        etUpdateTitle=(EditText)view.findViewById(R.id.etUpdateTitle);
        tvSubmit=(TextView)view.findViewById(R.id.tvSubmit);
        tvUpdateSubmit=(TextView)view.findViewById(R.id.tvUpdateSubmit);
        lnCLose=(LinearLayout)view.findViewById(R.id.lnCLose);
        lnUpdateCLose=(LinearLayout)view.findViewById(R.id.lnUpdateCLose);
        lnAddWeightPercentage=(LinearLayout)view.findViewById(R.id.lnAddWeightPercentage);
        lnUpdateWeightPercentage=(LinearLayout)view.findViewById(R.id.lnUpdateWeightPercentage);
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DATE, 1);
        monthFrstDate= Util.changeAnyDateFormat(c.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd")+"T00:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date calculateDate = null;
        try {
            calculateDate = sdf.parse(monthFrstDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(calculateDate);
        calendar.add(Calendar.MONTH,2);
        //Log.d("twomonthsDate",calendar.getTime().toString());
        String twomonthsdate=Util.changeAnyDateFormat(calendar.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd");
        twomonthsDate=twomonthsdate+"T00:00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(twomonthsdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cd = Calendar.getInstance();
        cd.setTime(convertedDate);
        cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));
        twoMonthsLastDate=Util.changeAnyDateFormat(cd.getTime().toString(),"EEE MMM dd HH:mm:ss zzzz yyyy","yyyy-MM-dd")+"T00:00:00";


        courseSectionID= Integer.parseInt(getArguments().getString("courseSectionID"));
        tvAssignmentType=(TextView)view.findViewById(R.id.tvAssignmentType);
        tvCancel=(TextView)view.findViewById(R.id.tvCancel);
        tvUpdateCancel=(TextView)view.findViewById(R.id.tvUpdateCancel);
        lnCreateAssignment=(LinearLayout)view.findViewById(R.id.lnCreateAssignment);
        lnUpdateAssignment=(LinearLayout)view.findViewById(R.id.lnUpdateAssignment);
        lnAssignment=(LinearLayout)view.findViewById(R.id.lnAssignment);
        rvItem=(RecyclerView)view.findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tvAssignmentType.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        lnCLose.setOnClickListener(this);
        lnUpdateCLose.setOnClickListener(this);
        tvUpdateCancel.setOnClickListener(this);
        tvUpdateSubmit.setOnClickListener(this);
        lnAccess=(LinearLayout) view.findViewById(R.id.lnAccess);


    }

    private void setAdapter(){
        ClassAssignmentAdapter assignmentAdapter=new ClassAssignmentAdapter(itmList,getContext(),AssignmentsFragment.this);
        rvItem.setAdapter(assignmentAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view==tvAssignmentType){

            lnCreateAssignment.setVisibility(View.VISIBLE);
        }else if (view==tvCancel){
            lnAssignment.setVisibility(View.VISIBLE);
            lnCreateAssignment.setVisibility(View.GONE);

        }else if (view==tvUpdateCancel){
            lnAssignment.setVisibility(View.VISIBLE);
            lnUpdateAssignment.setVisibility(View.GONE);

        }
        else if (view==lnCLose){
            lnAssignment.setVisibility(View.VISIBLE);
            lnCreateAssignment.setVisibility(View.GONE);

        }else if (view==lnUpdateCLose){
            lnAssignment.setVisibility(View.VISIBLE);
            lnUpdateAssignment.setVisibility(View.GONE);

        }
        else if (view==tvSubmit){
            if (etTitle.getText().toString().length()>0) {
                JSONObject jsonObject = new JSONObject();
                JSONObject assignmentTypeOBJ = new JSONObject();
                try {
                    assignmentTypeOBJ.put("title", etTitle.getText().toString());
                    assignmentTypeOBJ.put("weightage",etAddWeightPercentage.getText().toString());
                    assignmentTypeOBJ.put("courseSectionId", courseSectionID);
                    assignmentTypeOBJ.put("markingPeriodId", periodID);
                    assignmentTypeOBJ.put("schoolId", pref.getSchoolID());
                    assignmentTypeOBJ.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                    assignmentTypeOBJ.put("tenantId", pref.getTenatID());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.put("assignmentType", assignmentTypeOBJ);
                    jsonObject.put("_tenantName", pref.getTenatName());
                    jsonObject.put("_userName", pref.getName());
                    jsonObject.put("_token", pref.getToken());
                    jsonObject.put("tenantId", pref.getTenatID());
                    jsonObject.put("_academicYear", pref.getAcademicYear());
                    jsonObject.put("schoolId", pref.getSchoolID());
                    jsonObject.put("markingPeriodStartDate", pref.getPeriodStartYear());
                    jsonObject.put("markingPeriodEndDate", pref.getPeriodEndYear());
                    addAssignemnts(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(getContext(),"Please Enter Assignment Type",Toast.LENGTH_LONG).show();
            }

        }else if (view==tvUpdateSubmit){
            JSONObject obj=new JSONObject();
            JSONObject assementObj=new JSONObject();
            try {
                assementObj.put("title",etUpdateTitle.getText().toString());
                assementObj.put("weightage",etUpdateWeightPercentage.getText().toString());
                assementObj.put("assignmentTypeId",updateAssementTypeID);
                assementObj.put("courseSectionId",updateAssesmentCourseID);
                assementObj.put("markingPeriodId",periodID);
                assementObj.put("updatedBy","2199863c-a77b-4e86-bad3-a524424d969b");
                assementObj.put("schoolId",pref.getSchoolID());
                assementObj.put("tenantId",pref.getTenatID());
                obj.put("assignmentType",assementObj);
                obj.put("_tenantName",pref.getTenatName());
                obj.put("_userName",pref.getName());
                obj.put("_token",pref.getToken());
                obj.put("tenantId",pref.getTenatID());
                obj.put("schoolId",pref.getSchoolID());
                updateAssignemntsType(obj);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void getAssignemnts(JSONObject jsonObject) {
        lnData.setVisibility(View.VISIBLE);
        lnNoData.setVisibility(View.GONE);

        String url=pref.getAPI()+"StaffPortalAssignment/getAllAssignmentType";
        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("studentresponse",response.toString());
                    itmList.clear();
                    JSONObject bookConfigOBJ = new JSONObject();
                    JSONArray blankArray = new JSONArray();
                    try {
                        bookConfigOBJ.put("scoreRounding", "up");
                        bookConfigOBJ.put("scoreRounding", "newestFirst");
                        bookConfigOBJ.put("gradebookConfigurationProgressPeriods", blankArray);
                        bookConfigOBJ.put("gradebookConfigurationQuarter", blankArray);
                        bookConfigOBJ.put("gradebookConfigurationSemester", blankArray);
                        bookConfigOBJ.put("gradebookConfigurationYear", blankArray);
                        bookConfigOBJ.put("lmsGradeSync", 1);
                        bookConfigOBJ.put("courseId", courseID);
                        bookConfigOBJ.put("courseSectionId", courseSectionID);
                        bookConfigOBJ.put("academicYear", pref.getAcademicYear());
                        bookConfigOBJ.put("schoolId", pref.getSchoolID());
                        bookConfigOBJ.put("tenantId", pref.getTenatID());
                        JSONObject configOBJ = new JSONObject();
                        configOBJ.put("gradebookConfiguration", bookConfigOBJ);
                        configOBJ.put("_tenantName", pref.getTenatName());
                        configOBJ.put("_userName", pref.getName());
                        configOBJ.put("_token", pref.getToken());
                        configOBJ.put("tenantId", pref.getTenatID());
                        configOBJ.put("schoolId", pref.getSchoolID());
                        configOBJ.put("_academicYear", pref.getAcademicYear());
                        viewGradeBookConfig(configOBJ);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            lnData.setVisibility(View.GONE);
                            lnNoData.setVisibility(View.VISIBLE);


                        }else {
                            JSONArray assignmentTypeList = response.optJSONArray("assignmentTypeList");
                            if (assignmentTypeList.length() > 0) {
                                for (int i=0;i<assignmentTypeList.length();i++){
                                    JSONObject assignmetOBJ=assignmentTypeList.optJSONObject(i);
                                   String title=assignmetOBJ.optString("title");
                                   String weightage=assignmetOBJ.optString("weightage");
                                   int assignmentTypeId=assignmetOBJ.optInt("assignmentTypeId");
                                    int courseSectionId=assignmetOBJ.optInt("courseSectionId");
                                   JSONArray assignment=assignmetOBJ.optJSONArray("assignment");
                                   ClassAssignmentModel assignmentModel=new ClassAssignmentModel();
                                   assignmentModel.setAssignmentDetails(assignment.toString());
                                   assignmentModel.setAssignmentName(title);
                                   assignmentModel.setAssignmentTypeId(assignmentTypeId);
                                   assignmentModel.setCourseSectionId(courseSectionId);
                                   assignmentModel.setWeightage(weightage);
                                   itmList.add(assignmentModel);


                                }


                                lnData.setVisibility(View.VISIBLE);
                                lnNoData.setVisibility(View.GONE);
                                setAdapter();



                            }else {
                                lnData.setVisibility(View.GONE);
                                lnNoData.setVisibility(View.VISIBLE);
                            }
                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void addAssignemnts(JSONObject jsonObject) {

        String url=pref.getAPI()+"StaffPortalAssignment/addAssignmentType";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("addassignmnet",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();



                        }else {

                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            lnAssignment.setVisibility(View.VISIBLE);
                            lnCreateAssignment.setVisibility(View.GONE);

                            try {
                                jsonObject.put("courseSectionId",courseSectionID);
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId",pref.getTenatID());
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("academicYear",pref.getAcademicYear());
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                getAssignemnts(jsonObject);
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

    @Override
    public void onResume() {
        super.onResume();

        if (gradescaletype.equalsIgnoreCase("Ungraded")){
            lnAssignment.setVisibility(View.GONE);
            lnAccess.setVisibility(View.VISIBLE);

        }else {
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



    }

    public void editWorkoutPopup(int pos) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.edit_delete_popup, null);
        dialogBuilder.setView(dialogView);
        LinearLayout lnDelete=(LinearLayout)dialogView.findViewById(R.id.lnDelete);
        lnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj=new JSONObject();
                JSONObject assismentobj=new JSONObject();
                try {
                    assismentobj.put("assignmentTypeId",itmList.get(pos).getAssignmentTypeId());
                    assismentobj.put("courseSectionId",itmList.get(pos).getCourseSectionId());
                    assismentobj.put("schoolId",pref.getSchoolID());
                    assismentobj.put("tenantId",pref.getTenatID());
                    obj.put("assignmentType", assismentobj);
                    obj.put("_tenantName", pref.getTenatName());
                    obj.put("_userName", pref.getName());
                    obj.put("_token", pref.getToken());
                    obj.put("tenantId", pref.getTenatID());
                    obj.put("schoolId", pref.getSchoolID());
                    deleteAssignemntsType(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        LinearLayout lnEdit=(LinearLayout)dialogView.findViewById(R.id.lnEdit);
        lnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
                lnUpdateAssignment.setVisibility(View.VISIBLE);
                etUpdateTitle.setText(itmList.get(pos).getAssignmentName());
                etUpdateWeightPercentage.setText(itmList.get(pos).getWeightage());
                updateAssementTypeID=itmList.get(pos).getAssignmentTypeId();
                updateAssesmentCourseID=itmList.get(pos).getCourseSectionId();
            }
        });



        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }



    public void deleteAssignemntsType(JSONObject jsonObject) {

        String url=pref.getAPI()+"StaffPortalAssignment/deleteAssignmentType";

        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("addassignmnet",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            alerDialog1.dismiss();



                        }else {

                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            alerDialog1.dismiss();
                            try {
                                jsonObject.put("courseSectionId",courseSectionID);
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId",pref.getTenatID());
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("academicYear",pref.getAcademicYear());
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                getAssignemnts(jsonObject);
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

    public void updateAssignemntsType(JSONObject jsonObject) {
        String url=pref.getAPI()+"StaffPortalAssignment/updateAssignmentType";

        new PostJsonDataParser(getContext(), Request.Method.PUT, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("addassignmnet",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            alerDialog1.dismiss();



                        }else {
                            lnUpdateAssignment.setVisibility(View.GONE);
                            Toast.makeText(getContext(),_message,Toast.LENGTH_LONG).show();
                            alerDialog1.dismiss();
                            try {
                                jsonObject.put("courseSectionId",courseSectionID);
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId",pref.getTenatID());
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("academicYear",pref.getAcademicYear());
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                getAssignemnts(jsonObject);
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
                                jsonObject.put("courseSectionId",courseSectionID);
                                jsonObject.put("_tenantName", pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId",pref.getTenatID());
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("academicYear",pref.getAcademicYear());
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                getAssignemnts(jsonObject);
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


    public void viewGradeBookConfig(JSONObject jsonObject) {

        String url=pref.getAPI()+"StaffPortalGradebook/viewGradebookConfiguration";


        new PostJsonDataParser(getContext(), Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("gradeResponse", response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {
                            lnAddWeightPercentage.setVisibility(View.GONE);
                        } else {

                            JSONObject gradebookConfiguration=response.optJSONObject("gradebookConfiguration");
                            String general = gradebookConfiguration.optString("general");
                            if (general.contains("weightGrades")){
                                lnAddWeightPercentage.setVisibility(View.VISIBLE);
                                lnUpdateWeightPercentage.setVisibility(View.VISIBLE);
                            }else {
                                lnAddWeightPercentage.setVisibility(View.GONE);
                                lnUpdateWeightPercentage.setVisibility(View.GONE);
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