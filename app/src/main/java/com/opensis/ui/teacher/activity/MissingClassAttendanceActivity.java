package com.opensis.ui.teacher.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.AttendanceCodeAdapter;
import com.opensis.adapter.AttendanceStudentAdapter;
import com.opensis.adapter.MissingAttendanceCodeAdapter;
import com.opensis.adapter.MissingAttendanceStudentAdapter;
import com.opensis.model.AttendanceCodeModel;
import com.opensis.model.AttendanceStudentModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;
import com.opensis.ui.teacher.fragment.ClassAttendanceFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MissingClassAttendanceActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout lnAttendance;
    RecyclerView rvItem;
    Pref pref;
    TextView tvAttendanceDate;
    int courseSectionID, courseID, periodID;
    ArrayList<AttendanceStudentModel> itemList = new ArrayList<>();
    ArrayList<AttendanceCodeModel> codeList = new ArrayList<>();
    String attendanceShortName;
    int attendanceCode, attendanceCatCode;
    AlertDialog alerDialog1, commentPopUp;
    MissingAttendanceStudentAdapter studentAdapter;
    TextView tvSubmit;
    JSONArray jsonArray = new JSONArray();
    String defaultstudentPic = "data:image/png;base64,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";
    Set<String> stationCodes = new HashSet<String>();
    JSONArray tempArray = new JSONArray();
    int studentCount;
    JSONArray scheduleStudentForView;
    AttendanceStudentModel studentModel;
    ArrayList<String> studentPhotoList = new ArrayList<>();
    ArrayList<String> studentGradeList = new ArrayList<>();
    int academicyear, attendanceCatID;
    String date,attendate,coursesection;
    TextView tvCourseName;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_class_attendance);
        initView();
    }

    private void initView() {

        pref = new Pref(MissingClassAttendanceActivity.this);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        attendate=getIntent().getStringExtra("date");

        coursesection=getIntent().getStringExtra("coursesection");
        date=Util.changeAnyDateFormat(attendate,"yyyy-MM-dd","dd MMM,yyyy");
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        courseSectionID = getIntent().getIntExtra("courseSectionID", 0);
        attendanceCatID = getIntent().getIntExtra("attendanceCatID", 0);
        courseID = getIntent().getIntExtra("courseID", 0);
        periodID = getIntent().getIntExtra("periodID", 0);
        tvAttendanceDate = (TextView) findViewById(R.id.tvAttendanceDate);
        tvAttendanceDate.setText(date);

        lnAttendance = (LinearLayout) findViewById(R.id.lnAttendance);
        rvItem = (RecyclerView) findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        academicyear = pref.getAcademicYear();
        tvCourseName=(TextView)findViewById(R.id.tvCourseName);
        tvCourseName.setText(coursesection);


        tvSubmit.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        JSONObject obj = new JSONObject();
        JSONObject accessobj = new JSONObject();
        try {
            obj.put("tenantId", AppData.tenatID);
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

    public void getRefreshToken(JSONObject jsonObject) {
        String url = pref.getAPI() + "User/RefreshToken";


        new PostJsonDataParser(MissingClassAttendanceActivity.this, Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse", response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {
                            Intent intent = new Intent(MissingClassAttendanceActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } else {
                            String _token = response.optString("_token");
                            pref.saveToken(_token);
                            JSONObject obj = new JSONObject();
                            JSONArray codeArray = new JSONArray();
                            obj.put("attendanceCodeList", codeArray);
                            obj.put("attendanceCategoryId", attendanceCatID);
                            obj.put("_tenantName", pref.getTenatName());
                            obj.put("_userName", pref.getName());
                            obj.put("_token", pref.getToken());
                            obj.put("tenantId", AppData.tenatID);
                            obj.put("schoolId", pref.getSchoolID());
                            obj.put("academicYear", pref.getAcademicYear());
                            getAttendaceCode(obj);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void getAttendaceCode(JSONObject jsonObject) {
        String url = pref.getAPI() + "AttendanceCode/getAllAttendanceCode";

        new PostJsonDataParser(MissingClassAttendanceActivity.this, Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse", response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        codeList.clear();

                        JSONArray attendanceCodeList = response.optJSONArray("attendanceCodeList");
                        for (int i = 0; i < attendanceCodeList.length(); i++) {
                            JSONObject codeOBJ = attendanceCodeList.optJSONObject(i);
                            String title = codeOBJ.optString("title");
                            String shortName = codeOBJ.optString("shortName");
                            boolean defaultCode = codeOBJ.optBoolean("defaultCode");
                            int attendanceCategoryId = codeOBJ.optInt("attendanceCategoryId");
                            int attendanceCode1 = codeOBJ.optInt("attendanceCode1");
                            AttendanceCodeModel codeModel = new AttendanceCodeModel();
                            codeModel.setAttendanceName(title);
                            codeModel.setAttendanceShortName(shortName);
                            codeModel.setAttendanceCatCode(attendanceCategoryId);
                            codeModel.setAttendanceCode(attendanceCode1);
                            if (defaultCode) {
                                attendanceShortName = shortName;
                                attendanceCode = attendanceCode1;
                                attendanceCatCode = attendanceCategoryId;
                            }
                            codeList.add(codeModel);
                        }


                        JSONArray filterArray = new JSONArray();
                        JSONArray sectionArray = new JSONArray();
                        sectionArray.put(courseSectionID);
                        JSONObject obj = new JSONObject();
                        obj.put("pageNumber", 0);
                        obj.put("_pageSize", 0);
                        obj.put("sortingModel", null);
                        obj.put("filterParams", filterArray);
                        obj.put("courseSectionIds", sectionArray);
                        obj.put("pageSize", 0);
                        obj.put("profilePhoto", true);
                        obj.put("_tenantName", pref.getTenatName());
                        obj.put("_userName", pref.getName());
                        obj.put("_token", pref.getToken());
                        obj.put("tenantId", AppData.tenatID);
                        obj.put("schoolId", pref.getSchoolID());
                        getStudentList(obj);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }




    public void getStudentList(JSONObject jsonObject) {
        String url = pref.getAPI() + "StudentSchedule/getStudentListByCourseSection";

        new PostJsonDataParser(MissingClassAttendanceActivity.this, Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse", response.toString());
                    try {
                        itemList.clear();
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {


                        } else {
                            JSONArray scheduleStudentForView = response.optJSONArray("scheduleStudentForView");
                            if (scheduleStudentForView.length() > 0) {
                                for (int i = 0; i < scheduleStudentForView.length(); i++) {
                                    JSONObject studentOBJ = scheduleStudentForView.optJSONObject(i);
                                    String firstGivenName = studentOBJ.optString("firstGivenName");
                                    String lastFamilyName = studentOBJ.optString("lastFamilyName");
                                    String middleName = Util.getFreshValue(studentOBJ.optString("middleName"), "");
                                    String alternateId = Util.getFreshValue(studentOBJ.optString("alternateId"), "-");
                                    String studentInternalId = studentOBJ.optString("studentInternalId");
                                    String gradeLevel = Util.getFreshValue(studentOBJ.optString("gradeLevel"), "");
                                    String section = Util.getFreshValue(studentOBJ.optString("section"), "");
                                    String studentPhoto = Util.getFreshValue(studentOBJ.optString("studentPhoto"), "");
                                    int studentId = studentOBJ.optInt("studentId");
                                    AttendanceStudentModel studentModel = new AttendanceStudentModel();
                                    studentModel.setStudentFrstName(firstGivenName);
                                    studentModel.setStudentLstName(lastFamilyName);
                                    studentModel.setStudentID(studentInternalId);
                                    studentModel.setAltId(alternateId);
                                    studentModel.setGrade(gradeLevel);
                                    studentModel.setID(studentId);
                                    studentModel.setSection(section);
                                    studentModel.setAttendanceCatCode(attendanceCatCode);
                                    studentModel.setAttendanceCode(attendanceCode);
                                    studentModel.setAttendanceName(attendanceShortName);
                                    studentModel.setStudentPhoto(studentPhoto);
                                    studentModel.setComments("");
                                    studentModel.setAttendanceName("P");
                                    itemList.add(studentModel);

                                }

                                studentAdapter = new MissingAttendanceStudentAdapter(itemList, MissingClassAttendanceActivity.this, 2);
                                rvItem.setAdapter(studentAdapter);


                            } else {

                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }



    public void codePopUp(int pos) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MissingClassAttendanceActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.attendance_code_popup, null);
        dialogBuilder.setView(dialogView);
        RecyclerView rvAttendanceCode = (RecyclerView) dialogView.findViewById(R.id.rvAttendanceCode);
        rvAttendanceCode.setLayoutManager(new LinearLayoutManager(MissingClassAttendanceActivity.this, LinearLayoutManager.VERTICAL, false));
        MissingAttendanceCodeAdapter codeAdapter = new MissingAttendanceCodeAdapter(codeList, MissingClassAttendanceActivity.this,  pos);
        rvAttendanceCode.setAdapter(codeAdapter);

        LinearLayout lnCLose = (LinearLayout) dialogView.findViewById(R.id.lnCLose);
        lnCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerDialog1.dismiss();
            }
        });


        alerDialog1 = dialogBuilder.create();
        alerDialog1.setCancelable(true);
        Window window = alerDialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alerDialog1.show();
    }

    public void getcode(int i, int j) {
        String attendanceName = codeList.get(i).getAttendanceShortName();
        int attencode = codeList.get(i).getAttendanceCode();
        int attenCatCode = codeList.get(i).getAttendanceCatCode();
        itemList.get(j).setAttendanceName(attendanceName);
        itemList.get(j).setAttendanceCode(attencode);
        itemList.get(j).setAttendanceCatCode(attenCatCode);
        studentAdapter.notifyDataSetChanged();
        alerDialog1.dismiss();

    }

    @Override
    public void onClick(View v) {
        if (v==tvSubmit){
            jsonArray=new JSONArray();
            submitAttendance();
        }else if (v==imgBack){
            onBackPressed();
        }

    }

    public void commentPopUp(int pos,String studentName,String comment) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MissingClassAttendanceActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.attendance_comment_popup, null);
        dialogBuilder.setView(dialogView);
        TextView tvStudentName=(TextView)dialogView.findViewById(R.id.tvStudentName);
        tvStudentName.setText("Add Comment to "+studentName);
        EditText etComment=(EditText)dialogView.findViewById(R.id.etComment);
        etComment.setText(comment);
        TextView tvSubmit=(TextView)dialogView.findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.get(pos).setComments(etComment.getText().toString());
                studentAdapter.notifyDataSetChanged();
                commentPopUp.dismiss();
            }
        });

        LinearLayout lnCLose=(LinearLayout)dialogView.findViewById(R.id.lnCLose);
        lnCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentPopUp.dismiss();
            }
        });





        commentPopUp = dialogBuilder.create();
        commentPopUp.setCancelable(true);
        Window window = commentPopUp.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        commentPopUp.show();
    }

    private void submitAttendance(){

        for (int i=0;i<itemList.size();i++){
            JSONObject fianlobj=new JSONObject();
            JSONArray studentAttendanceComments=new JSONArray();
            JSONObject studentOBJ=new JSONObject();
            try {
                studentOBJ.put("comment",itemList.get(i).getComments());
                studentOBJ.put("membershipId",pref.getMemberShipID());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            studentAttendanceComments.put(studentOBJ);
            try {
                fianlobj.put("studentAttendanceComments",studentAttendanceComments);
                fianlobj.put("studentId",itemList.get(i).getID());
                fianlobj.put("attendanceCategoryId",itemList.get(i).getAttendanceCatCode());
                fianlobj.put("attendanceDate",attendate);
                if (pref.getTenatName().equals("misis")) {
                    fianlobj.put("blockId", 4);
                }else {
                    fianlobj.put("blockId", 1);
                }
                fianlobj.put("updatedBy","392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
                fianlobj.put("attendanceCode",itemList.get(i).getAttendanceCode());
                fianlobj.put("comments","");

                jsonArray.put(fianlobj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("studentAttendance",jsonArray);
            jsonObject.put("courseId",courseID);
            jsonObject.put("courseSectionId",courseSectionID);
            jsonObject.put("attendanceDate",attendate);
            jsonObject.put("periodId",periodID);
            jsonObject.put("updatedBy","392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            jsonObject.put("staffId",pref.getUserID());
            jsonObject.put("_tenantName",pref.getTenatName());
            jsonObject.put("_userName",pref.getName());
            jsonObject.put("_token",pref.getToken());
            jsonObject.put("tenantId",AppData.tenatID);
            jsonObject.put("schoolId",pref.getSchoolID());
            Log.d("final",jsonObject.toString());
            postAttendance(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    private void postAttendance(JSONObject jsonObject){
        String url=pref.getAPI()+"StudentAttendance/addUpdateStudentAttendance";
        new PostJsonDataParser(MissingClassAttendanceActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("schoolresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){



                        }else {
                            Toast.makeText(MissingClassAttendanceActivity.this,_message,Toast.LENGTH_LONG).show();
                            finish();


                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}