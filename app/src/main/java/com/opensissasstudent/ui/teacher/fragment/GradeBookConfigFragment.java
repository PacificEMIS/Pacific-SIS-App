package com.opensissasstudent.ui.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.opensissasstudent.R;
import com.opensissasstudent.adapter.FYSemAdapter;
import com.opensissasstudent.adapter.FinalGradeSemAdapter;
import com.opensissasstudent.adapter.FinalQtrGradeAdapter;
import com.opensissasstudent.model.FYModel;
import com.opensissasstudent.model.FinalGradeQtrModel;
import com.opensissasstudent.model.FinalGradeSemModel;
import com.opensissasstudent.model.GradeBokConfigSemQtrModel;
import com.opensissasstudent.others.parser.PostJsonDataParser;
import com.opensissasstudent.others.utility.AppData;
import com.opensissasstudent.others.utility.Pref;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GradeBookConfigFragment extends Fragment implements View.OnClickListener {
    int courseSectionID;
    View view;
    RecyclerView rvFinalQtrGrade, rvFinalSemGrade;
    ArrayList<FinalGradeQtrModel> qtrList = new ArrayList<>();
    ArrayList<FinalGradeSemModel> semList = new ArrayList<>();
    Pref pref;
    CheckBox ckWeightGrades, ckAssignedDate, ckDueDate;
    RecyclerView rvFullYR;
    LinearLayout lnFullYear;
    TextView tvFullYR, tvFYExam;
    EditText etFYExam;
    ArrayList<FYModel> fySemList = new ArrayList<>();
    TextView tvClass;
    String courseTitle, courseName;
    RadioGroup rgScore;
    RadioButton rbscore;
    TextView tvSubmit;

    RadioGroup rgAssesment;
    RadioButton rvAssesment;
    String weightGrade = "", assignedDate = "", dueDate = "";

    int courseID, periodID;
    String assesment, score;
    JSONArray qtrArray = new JSONArray();
    JSONArray semArray = new JSONArray();
    JSONArray fullYearArray = new JSONArray();
    int semmarkingPeriodId,semUpdateID;
    String semTitle;
    LinearLayout lnSem;
    TextView tvSemExam;
    EditText etSemExam;
    String fYTitle;
    int fyid;
    EditText etPercent, etDays;
    int gradebookConfigurationId;
    boolean editFlag;
    LinearLayout lnAccess,lnGradeBook;
    String gradescaletype;
    LinearLayout lnFullYearExam;
    boolean qtrExam,semExam,fyExam;
    ArrayList<GradeBokConfigSemQtrModel>semqtrList=new ArrayList<>();
    ArrayList<GradeBokConfigSemQtrModel>semqtrrList=new ArrayList<>();
    ArrayList<Integer>qtrnumber=new ArrayList<>();
    ArrayList<String>semqtrnumber=new ArrayList<>();
    ArrayList<String>fynumber=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grade_book_config, container, false);
        initView();
        return view;
    }

    private void initView() {
        pref = new Pref(getContext());
        lnFullYearExam=(LinearLayout)view.findViewById(R.id.lnFullYearExam);
        etDays = (EditText) view.findViewById(R.id.etDays);
        etPercent = (EditText) view.findViewById(R.id.etPercent);
        lnSem = (LinearLayout) view.findViewById(R.id.lnSem);
        tvSemExam = (TextView) view.findViewById(R.id.tvSemExam);
        etSemExam = (EditText) view.findViewById(R.id.etSemExam);
        ckWeightGrades = (CheckBox) view.findViewById(R.id.ckWeightGrades);
        ckAssignedDate = (CheckBox) view.findViewById(R.id.ckAssignedDate);
        ckDueDate = (CheckBox) view.findViewById(R.id.ckDueDate);
        tvSubmit = (TextView) view.findViewById(R.id.tvSubmit);
        rgScore = (RadioGroup) view.findViewById(R.id.rgScore);
        rgAssesment = (RadioGroup) view.findViewById(R.id.rgAssesment);
        courseSectionID = Integer.parseInt(getArguments().getString("courseSectionID"));
        gradescaletype=getArguments().getString("gradescaletype");

        lnAccess=(LinearLayout)view.findViewById(R.id.lnAccess);
        lnGradeBook=(LinearLayout)view.findViewById(R.id.lnGradeBook);

        courseID = Integer.parseInt(getArguments().getString("courseID"));
        periodID = Integer.parseInt(getArguments().getString("periodID"));
        courseName = getArguments().getString("courseName");
        courseTitle = getArguments().getString("courseTitle");
        rvFinalQtrGrade = (RecyclerView) view.findViewById(R.id.rvFinalQtrGrade);
        rvFinalSemGrade = (RecyclerView) view.findViewById(R.id.rvFinalSemGrade);
        rvFullYR = (RecyclerView) view.findViewById(R.id.rvFullYR);
        rvFinalSemGrade.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvFinalQtrGrade.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvFullYR.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        lnFullYear = (LinearLayout) view.findViewById(R.id.lnFullYear);
        tvFullYR = (TextView) view.findViewById(R.id.tvFullYR);
        tvFYExam = (TextView) view.findViewById(R.id.tvFYExam);

        etFYExam = (EditText) view.findViewById(R.id.etFYExam);
        tvClass = (TextView) view.findViewById(R.id.tvClass);
        tvClass.setText(courseName + " - " + courseTitle);

        if (gradescaletype.equals("Ungraded")){
            lnGradeBook.setVisibility(View.GONE);
            lnAccess.setVisibility(View.VISIBLE);
        }else {
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
        tvSubmit.setOnClickListener(this);
    }


    public void getRefreshToken(JSONObject jsonObject) {
        String url=pref.getAPI()+"User/RefreshToken";


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
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } else {
                            String _token = response.optString("_token");
                            pref.saveToken(_token);
                            JSONObject bookConfigOBJ = new JSONObject();
                            JSONArray blankArray = new JSONArray();
                            bookConfigOBJ.put("scoreRounding", "up");
                            bookConfigOBJ.put("assignmentSorting", "newestFirst");
                            bookConfigOBJ.put("gradebookConfigurationProgressPeriods", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationQuarter", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationSemester", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationYear", blankArray);
                            bookConfigOBJ.put("general", "");
                            bookConfigOBJ.put("courseId", courseID);
                            bookConfigOBJ.put("lmsGradeSync", 1);
                            bookConfigOBJ.put("courseSectionId", courseSectionID);
                            bookConfigOBJ.put("academicYear", pref.getAcademicYear());
                            bookConfigOBJ.put("schoolId", pref.getSchoolID());
                            bookConfigOBJ.put("tenantId", pref.getTenatID());
                            JSONObject configOBJ = new JSONObject();
                            configOBJ.put("gradebookConfiguration", bookConfigOBJ);
                            configOBJ.put("_tenantName", pref.getTenatName());
                            configOBJ.put("_userName", pref.getName());
                            configOBJ.put("_token", pref.getToken());
                            configOBJ.put("_academicYear", pref.getAcademicYear());
                            configOBJ.put("tenantId", pref.getTenatID());
                            configOBJ.put("schoolId", pref.getSchoolID());
                            viewGradeBookConfig(configOBJ);


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
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("courseSectionId", courseSectionID);
                            jsonObject1.put("isConfiguration", true);
                            jsonObject1.put("_tenantName", pref.getTenatName());
                            jsonObject1.put("_userName", pref.getName());
                            jsonObject1.put("_token", pref.getToken());
                            jsonObject1.put("tenantId", pref.getTenatID());
                            jsonObject1.put("schoolId", pref.getSchoolID());
                            jsonObject1.put("_academicYear", pref.getAcademicYear());
                            jsonObject1.put("academicYear", pref.getAcademicYear());
                            getGrade(jsonObject1);
                            editFlag=false;

                        } else {

                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("courseSectionId", courseSectionID);
                            jsonObject1.put("isConfiguration", true);
                            jsonObject1.put("_tenantName", pref.getTenatName());
                            jsonObject1.put("_userName", pref.getName());
                            jsonObject1.put("_token", pref.getToken());
                            jsonObject1.put("tenantId", pref.getTenatID());
                            jsonObject1.put("schoolId", pref.getSchoolID());
                            jsonObject1.put("_academicYear", pref.getAcademicYear());
                            jsonObject1.put("academicYear", pref.getAcademicYear());

                            semList.clear();
                            qtrList.clear();
                            fySemList.clear();

                            editFlag=true;
                            JSONObject gradebookConfiguration = response.optJSONObject("gradebookConfiguration");
                            gradebookConfigurationId=response.optInt("gradebookConfigurationId");
                            String general = gradebookConfiguration.optString("general");
                            Log.d("general", general);
                            if (general.contains("weightGrades")) {
                                ckWeightGrades.setChecked(true);
                            }
                            if (general.contains("assignedDateDefaultsToToday")) {
                                ckAssignedDate.setChecked(true);
                            }
                            if (general.contains("dueDateDefaultsToToday")) {
                                ckDueDate.setChecked(true);
                            }
                            String scoreRounding = gradebookConfiguration.optString("scoreRounding");
                            if (scoreRounding.equalsIgnoreCase("up")) {
                                rgScore.check(R.id.rbScoreUp);

                            } else if (scoreRounding.equalsIgnoreCase("down")) {
                                rgScore.check(R.id.rbScoreDown);
                            } else if (scoreRounding.equalsIgnoreCase("normal")) {
                                rgScore.check(R.id.rbScoreNormal);
                            } else if (scoreRounding.equalsIgnoreCase("none")) {
                                rgScore.check(R.id.rbScoreNone);
                            }

                            String assignmentSorting = gradebookConfiguration.optString("assignmentSorting");
                            if (assignmentSorting.equalsIgnoreCase("newestFirst")) {
                                rgAssesment.check(R.id.rbAssesmentNew);

                            } else if (assignmentSorting.equalsIgnoreCase("dueDate")) {
                                rgAssesment.check(R.id.rbAssesmentDue);
                            } else if (assignmentSorting.equalsIgnoreCase("assignedDate")) {
                                rgAssesment.check(R.id.rbAssesmentAssigned);
                            } else if (assignmentSorting.equalsIgnoreCase("ungraded")) {
                                rgAssesment.check(R.id.rbUngraded);
                            }
                            String maxAnomalousGrade = gradebookConfiguration.optString("maxAnomalousGrade");
                            etPercent.setText(maxAnomalousGrade);

                            String upgradedAssignmentGradeDays = gradebookConfiguration.optString("upgradedAssignmentGradeDays");
                            etDays.setText(upgradedAssignmentGradeDays);

                            JSONArray gradebookConfigurationQuarter=gradebookConfiguration.optJSONArray("gradebookConfigurationQuarter");
                            if (gradebookConfigurationQuarter.length()>0){
                                for (int i=0;i<gradebookConfigurationQuarter.length();i++){
                                    JSONObject qtrobj=gradebookConfigurationQuarter.optJSONObject(i);
                                    int gradingPercentage=qtrobj.optInt("gradingPercentage");
                                    qtrnumber.add(gradingPercentage);

                                }

                            }

                            JSONArray gradebookConfigurationSemester=gradebookConfiguration.optJSONArray("gradebookConfigurationSemester");
                            if (gradebookConfigurationSemester.length()>0){
                                for (int i=0;i<gradebookConfigurationSemester.length();i++){
                                    JSONObject semobj=gradebookConfigurationSemester.optJSONObject(i);

                                    String gradingPercentage= Util.getFreshValue(String.valueOf(semobj.optInt("gradingPercentage")),"");
                                    if (!gradingPercentage.equals("0")){
                                        semqtrnumber.add(gradingPercentage);

                                    }
                                    Log.d("semqtrnumber", String.valueOf(semqtrnumber));

                                }
                            }

                            JSONArray gradebookConfigurationYear=gradebookConfiguration.optJSONArray("gradebookConfigurationYear");
                            if (gradebookConfigurationYear.length()>0){
                                for (int i=0;i<gradebookConfigurationYear.length();i++){
                                    JSONObject semobj=gradebookConfigurationYear.optJSONObject(i);

                                    String gradingPercentage= Util.getFreshValue(String.valueOf(semobj.optInt("gradingPercentage")),"");
                                    if (!gradingPercentage.equals("0")){
                                        fynumber.add(gradingPercentage);

                                    }


                                }
                            }




                            getGrade(jsonObject1);



                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void getGrade(JSONObject jsonObject) {

        String url=pref.getAPI()+"StaffPortalGradebook/populateFinalGrading";


        new PostJsonDataParser(getContext(), Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("gradeResponse", response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {


                        } else {
                            JSONArray quarters = response.optJSONArray("quarters");

                            if (quarters.length() > 0) {
                                for (int i = 0; i < quarters.length(); i++) {
                                    JSONObject quaterOBJ = quarters.optJSONObject(i);
                                    String title = quaterOBJ.optString("title");
                                    int markingPeriodId = quaterOBJ.optInt("markingPeriodId");
                                    boolean doesExam=quaterOBJ.optBoolean("doesExam");
                                    qtrExam=doesExam;
                                    boolean doesGrades=quaterOBJ.optBoolean("doesGrades");

                                    FinalGradeQtrModel qtrModel = new FinalGradeQtrModel();
                                    if (qtrnumber.size()>0){
                                        qtrModel.setGradeMarks(String.valueOf(qtrnumber.get(i)));
                                        qtrModel.setGradeTotalMarks(String.valueOf(qtrnumber.get(i)));
                                    }else {
                                        qtrModel.setGradeMarks(String.valueOf(0));
                                        qtrModel.setGradeTotalMarks(String.valueOf(0));
                                    }

                                    qtrModel.setDoesGrades(doesGrades);
                                    qtrModel.setDoesExam(doesExam);
                                    qtrModel.setGradeName(title);
                                    qtrModel.setMarkingPeriodId(markingPeriodId);
                                    qtrList.add(qtrModel);
                                }

                                FinalQtrGradeAdapter qtrGradeAdapter = new FinalQtrGradeAdapter(qtrList, getContext());
                                rvFinalQtrGrade.setAdapter(qtrGradeAdapter);

                            }


                            JSONArray semesters = response.optJSONArray("semesters");
                            if (semesters.length() > 0) {
                                semList=new ArrayList<>();

                                for (int j=0;j<semesters.length();j++){
                                    JSONObject semOBJ = semesters.optJSONObject(j);
                                    String semtitle = semOBJ.optString("title");
                                    int semID=semOBJ.optInt("markingPeriodId");
                                    JSONArray semquarters=semOBJ.optJSONArray("quarters");
                                    if (semquarters.length()>0){
                                        semqtrList=new ArrayList<>();
                                        for (int z=0;z<semquarters.length();z++){
                                            JSONObject semqtrobj=semquarters.optJSONObject(z);
                                            String title=semqtrobj.optString("title");
                                            int markingPeriodId=semqtrobj.optInt("markingPeriodId");
                                            int semesterId=semqtrobj.optInt("semesterId");
                                            GradeBokConfigSemQtrModel semQtrModel=new GradeBokConfigSemQtrModel();
                                            semQtrModel.setTitle(title);
                                            if (semqtrnumber.size()>0){
                                                semQtrModel.setQtrMarks(semqtrnumber.get(z));
                                            }else {
                                                semQtrModel.setQtrMarks("0");
                                            }

                                            semQtrModel.setMarkingPeriodId(markingPeriodId);
                                            semQtrModel.setSemID(semesterId);
                                            semqtrList.add(semQtrModel);
                                            semqtrrList.add(semQtrModel);
                                        }
                                        Log.d("semqtrList", String.valueOf(semqtrList.size()));
                                    }

                                    FinalGradeSemModel semModel = new FinalGradeSemModel();
                                    semModel.setSemName(semtitle);
                                    semModel.setSemID(semID);
                                    semModel.setQtrList(semqtrList);
                                    semModel.setSemMarks("0");
                                    semList.add(semModel);


                                }

                                JSONObject semOBJ = semesters.optJSONObject(0);

                                boolean doesExam=semOBJ.optBoolean("doesExam");
                                semExam=doesExam;


                                FinalGradeSemAdapter semAdapter = new FinalGradeSemAdapter(semList, getContext());
                                rvFinalSemGrade.setAdapter(semAdapter);
                                if (doesExam){
                                    lnSem.setVisibility(View.VISIBLE);
                                }else {
                                    lnSem.setVisibility(View.GONE);
                                }



                            } else {
                                lnSem.setVisibility(View.GONE);
                            }

                            JSONObject schoolYears = response.optJSONObject("schoolYears");
                            if (schoolYears != null) {
                                lnFullYear.setVisibility(View.VISIBLE);
                                String title = schoolYears.optString("title").trim();
                                fYTitle = title;
                                fyid = schoolYears.optInt("markingPeriodId");
                                boolean doesExam=schoolYears.optBoolean("doesExam");
                                fyExam=doesExam;
                                tvFullYR.setText(title);
                                tvFYExam.setText(title);

                                JSONArray fysemesters = schoolYears.optJSONArray("semesters");
                                for (int i=0;i<fysemesters.length();i++){
                                    JSONObject fysemOBJ = fysemesters.optJSONObject(i);
                                    String semtitle = fysemOBJ.optString("title");
                                    int markingPeriodId = fysemOBJ.optInt("markingPeriodId");
                                    FYModel fyModel = new FYModel();
                                    fyModel.setSemID(markingPeriodId);
                                    fyModel.setYearID(fyid);
                                    fyModel.setSemName(semtitle);
                                    if (fynumber.size()>0){
                                        fyModel.setSemMarks(fynumber.get(i));

                                    }else {
                                        fyModel.setSemMarks("0");
                                    }

                                    fySemList.add(fyModel);
                                }






                                FYSemAdapter fysemAdapter = new FYSemAdapter(fySemList, getContext());
                                rvFullYR.setAdapter(fysemAdapter);
                            } else {
                                lnFullYear.setVisibility(View.GONE);
                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void submitGradeBookConfig() {
        //score
        int scoreselectedId = rgScore.getCheckedRadioButtonId();
        rbscore = (RadioButton) view.findViewById(scoreselectedId);
        if (scoreselectedId == -1) {
            Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {

            score = rbscore.getText().toString().toLowerCase();
        }
        //assesment
        int assesmentselectedId = rgAssesment.getCheckedRadioButtonId();
        rvAssesment = (RadioButton) view.findViewById(assesmentselectedId);
        if (assesmentselectedId == -1) {


        } else {
            if (rvAssesment.getText().toString().replaceAll("\\s", "").equalsIgnoreCase("NewestFirst")) {
                assesment = "newestFirst";
            } else if (rvAssesment.getText().toString().replaceAll("\\s", "").equalsIgnoreCase("DueDate")) {
                assesment = "dueDate";
            } else if (rvAssesment.getText().toString().replaceAll("\\s", "").equalsIgnoreCase("AssignedDate")) {
                assesment = "assignedDate";
            } else if (rvAssesment.getText().toString().replaceAll("\\s", "").equalsIgnoreCase("ungraded")) {
                assesment = "ungraded";
            }
        }


        if (ckWeightGrades.isChecked()) {
            weightGrade = "weightGrades";
        } else {
            weightGrade = "";
        }

        if (ckAssignedDate.isChecked()) {
            assignedDate = "|assignedDateDefaultsToToday";
        } else {
            assignedDate = "";
        }

        if (ckDueDate.isChecked()) {
            dueDate = "|dueDateDefaultsToToday";
        } else {
            dueDate = "";
        }
        String general = Util.getFreshValue(weightGrade) + Util.getFreshValue(assignedDate) + Util.getFreshValue(dueDate);




        for (int j = 0; j < qtrList.size(); j++) {

            JSONObject qtrOBJ = new JSONObject();
            try {

                qtrOBJ.put("tenantId", pref.getTenatID());
                qtrOBJ.put("schoolId", pref.getSchoolID());
                qtrOBJ.put("courseId", courseID);
                qtrOBJ.put("courseSectionId", courseSectionID);
                qtrOBJ.put("academicYear", pref.getAcademicYear());
                qtrOBJ.put("qtrMarkingPeriodId", qtrList.get(j).getMarkingPeriodId());
                qtrOBJ.put("gradingPercentage", qtrList.get(j).getGradeMarks());
                qtrOBJ.put("examPercentage", qtrList.get(j).getGradeTotalMarks());
                qtrOBJ.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                qtrOBJ.put("createdOn", "2022-12-20T13:24:46");
                qtrOBJ.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                qtrOBJ.put("updatedOn", "2022-12-20T13:24:46");
                qtrOBJ.put("title", qtrList.get(j).getGradeName());
            /*    qtrOBJ.put("doesExam", true);
                qtrOBJ.put("doesGrades", true);
                qtrOBJ.put("isProgressPeriodExists", false);
*/


            } catch (JSONException e) {
                e.printStackTrace();
            }


            qtrArray.put(qtrOBJ);

        }


        JSONObject semOBJ = new JSONObject();
        try {

            semOBJ.put("tenantId", pref.getTenatID());
            semOBJ.put("schoolId", pref.getSchoolID());
            semOBJ.put("courseId", courseID);
            semOBJ.put("courseSectionId", courseSectionID);
            semOBJ.put("academicYear", pref.getAcademicYear());
            semOBJ.put("smstrMarkingPeriodId", semmarkingPeriodId);
            semOBJ.put("qtrMarkingPeriodId", null);
            semOBJ.put("gradingPercentage", 0);
            semOBJ.put("examPercentage", etSemExam.getText().toString());
            semOBJ.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            semOBJ.put("createdOn", "2022-12-20T13:24:46");
            semOBJ.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            semOBJ.put("updatedOn", "2022-12-20T13:24:46");
            semOBJ.put("title", semTitle);
          /*  semOBJ.put("doesExam",true);
            semOBJ.put("doesGrades",true);
            semOBJ.put("isQuarterExists",true);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < semList.size(); i++) {
            View view = rvFinalSemGrade.getChildAt(i);
            EditText etGradeTotal = (EditText) view.findViewById(R.id.etGradeQ1);
            String qtrmarks = etGradeTotal.getText().toString();
            ;
            semList.get(i).setQtrMarks(qtrmarks);


        }

        for (int j = 0; j < semList.size(); j++) {
            JSONObject semqtrOBJ = new JSONObject();
            try {
                semqtrOBJ.put("tenantId", pref.getTenatID());
                semqtrOBJ.put("schoolId", pref.getSchoolID());
                semqtrOBJ.put("courseId", courseID);
                semqtrOBJ.put("courseSectionId", courseSectionID);
                semqtrOBJ.put("academicYear", pref.getAcademicYear());
                semqtrOBJ.put("smstrMarkingPeriodId", semmarkingPeriodId);
                semqtrOBJ.put("qtrMarkingPeriodId", semList.get(j).getQtrID());
                semqtrOBJ.put("gradingPercentage", semList.get(j).getQtrMarks());
                semqtrOBJ.put("examPercentage", 0);
                semqtrOBJ.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                semqtrOBJ.put("createdOn", "2022-12-20T13:24:46");
                semqtrOBJ.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                semqtrOBJ.put("updatedOn", "2022-12-20T13:24:46");
                semqtrOBJ.put("title", semList.get(j).getQtrName());
               /* semqtrOBJ.put("doesExam",true);
                semqtrOBJ.put("doesGrades",true);
                semqtrOBJ.put("isQuarterExists",true);*/
                semArray.put(semqtrOBJ);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (semList.size() > 0) {
            semArray.put(semOBJ);
        } else {
            semArray = new JSONArray();
        }

        JSONObject fullYrobj = new JSONObject();
        try {
            fullYrobj.put("tenantId", pref.getTenatID());
            fullYrobj.put("schoolId", pref.getSchoolID());
            fullYrobj.put("courseId", courseID);
            fullYrobj.put("courseSectionId", courseSectionID);
            fullYrobj.put("academicYear", pref.getAcademicYear());
            fullYrobj.put("yrMarkingPeriodId", fyid);
            fullYrobj.put("smstrMarkingPeriodId", null);
            fullYrobj.put("gradingPercentage", 0);
            fullYrobj.put("examPercentage", etFYExam.getText().toString());
            fullYrobj.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            fullYrobj.put("createdOn", "2022-12-20T13:24:46");
            fullYrobj.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            fullYrobj.put("updatedOn", "2022-12-20T13:24:46");
            fullYrobj.put("title", fYTitle);
           /* fullYrobj.put("doesExam",true);
            fullYrobj.put("doesGrades",true);
            fullYrobj.put("isSemesterExists",true);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (fyExam){
            for (int i = 0; i < fySemList.size(); i++) {
                View view = rvFullYR.getChildAt(i);
                EditText etGrade = (EditText) view.findViewById(R.id.etGrade);
                String qtrmarks = etGrade.getText().toString();
                ;
                fySemList.get(i).setSemMarks(qtrmarks);


            }
        }


        for (int j = 0; j < fySemList.size(); j++) {
            JSONObject yrsemOBJ = new JSONObject();
            try {
                yrsemOBJ.put("tenantId",pref.getTenatID());
                yrsemOBJ.put("schoolId", pref.getSchoolID());
                yrsemOBJ.put("courseId", courseID);
                yrsemOBJ.put("courseSectionId", courseSectionID);
                yrsemOBJ.put("academicYear", pref.getAcademicYear());
                yrsemOBJ.put("yrMarkingPeriodId", fyid);
                yrsemOBJ.put("smstrMarkingPeriodId", fySemList.get(j).getSemID());
                yrsemOBJ.put("gradingPercentage", fySemList.get(j).getSemMarks());
                yrsemOBJ.put("examPercentage", 0);
                fullYrobj.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                fullYrobj.put("createdOn", "2022-12-20T13:24:46");
                fullYrobj.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                fullYrobj.put("updatedOn", "2022-12-20T13:24:46");
                yrsemOBJ.put("title", fySemList.get(j).getSemName());
                /*yrsemOBJ.put("doesExam",true);
                yrsemOBJ.put("doesGrades",true);
                yrsemOBJ.put("isQuarterExists",true);*/
                fullYearArray.put(yrsemOBJ);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (fySemList.size() > 0) {
            fullYearArray.put(fullYrobj);
        } else {
            fullYearArray = new JSONArray();
        }

        JSONObject graderBookConfig = new JSONObject();
        JSONArray gradebookConfigurationProgressPeriods = new JSONArray();
        try {
            graderBookConfig.put("tenantId", pref.getTenatID());
            graderBookConfig.put("schoolId", pref.getSchoolID());
            graderBookConfig.put("courseId", courseID);
            graderBookConfig.put("courseSectionId", courseSectionID);
            graderBookConfig.put("general", general);
            graderBookConfig.put("scoreRounding", score);
            graderBookConfig.put("assignmentSorting", assesment);
            graderBookConfig.put("maxAnomalousGrade", etPercent.getText().toString());
            graderBookConfig.put("upgradedAssignmentGradeDays", etDays.getText().toString());
            graderBookConfig.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            graderBookConfig.put("createdOn", "2022-12-20T13:24:46");
            graderBookConfig.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            graderBookConfig.put("updatedOn", "2022-12-20T13:24:46");
            graderBookConfig.put("configUpdateFlag", false);
            graderBookConfig.put("lmsGradeSync", 1);
            graderBookConfig.put("gradebookConfigurationGradescale", gradebookConfigurationProgressPeriods);
            graderBookConfig.put("gradebookConfigurationProgressPeriods", gradebookConfigurationProgressPeriods);
            graderBookConfig.put("gradebookConfigurationQuarter", qtrArray);
            graderBookConfig.put("gradebookConfigurationSemester", semArray);
            graderBookConfig.put("gradebookConfigurationYear", fullYearArray);


            JSONObject obj = new JSONObject();
            obj.put("gradebookConfiguration", graderBookConfig);
            obj.put("_tenantName", pref.getTenatName());
            obj.put("_userName", pref.getName());
            obj.put("_token", pref.getToken());
            obj.put("tenantId", AppData.tenatID);
            obj.put("schoolId", pref.getSchoolID());
            Log.d("finalOBJ", obj.toString());
          //  postGradeBook(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void editGradeBookConfig() {
        //score
        int scoreselectedId = rgScore.getCheckedRadioButtonId();
        rbscore = (RadioButton) view.findViewById(scoreselectedId);
        if (scoreselectedId == -1) {
            Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {

            score = rbscore.getText().toString().toLowerCase();
        }
        //assesment
        int assesmentselectedId = rgAssesment.getCheckedRadioButtonId();
        rvAssesment = (RadioButton) view.findViewById(assesmentselectedId);
        if (assesmentselectedId == -1) {


        } else {
            if (rvAssesment.getText().toString().replaceAll("\\s", "").equalsIgnoreCase("NewestFirst")) {
                assesment = "newestFirst";
            } else if (rvAssesment.getText().toString().replaceAll("\\s", "").equalsIgnoreCase("DueDate")) {
                assesment = "dueDate";
            } else if (rvAssesment.getText().toString().replaceAll("\\s", "").equalsIgnoreCase("AssignedDate")) {
                assesment = "assignedDate";
            } else if (rvAssesment.getText().toString().replaceAll("\\s", "").equalsIgnoreCase("ungraded")) {
                assesment = "ungraded";
            }
        }


        if (ckWeightGrades.isChecked()) {
            weightGrade = "weightGrades";
        } else {
            weightGrade = "";
        }

        if (ckAssignedDate.isChecked()) {
            assignedDate = "|assignedDateDefaultsToToday";
        } else {
            assignedDate = "";
        }

        if (ckDueDate.isChecked()) {
            dueDate = "|dueDateDefaultsToToday";
        } else {
            dueDate = "";
        }
        String general = Util.getFreshValue(weightGrade) + Util.getFreshValue(assignedDate) + Util.getFreshValue(dueDate);



        for (int j = 0; j < qtrList.size(); j++) {

            JSONObject qtrOBJ = new JSONObject();
            try {
                qtrOBJ.put("id", qtrList.get(j).getQtrUpdateID());
                qtrOBJ.put("gradebookConfigurationId",gradebookConfigurationId);
                qtrOBJ.put("tenantId", AppData.tenatID);
                qtrOBJ.put("schoolId", pref.getSchoolID());
                qtrOBJ.put("courseId", courseID);
                qtrOBJ.put("courseSectionId", courseSectionID);
                qtrOBJ.put("academicYear", pref.getAcademicYear());
                qtrOBJ.put("qtrMarkingPeriodId", qtrList.get(j).getMarkingPeriodId());
                qtrOBJ.put("gradingPercentage", qtrList.get(j).getGradeMarks());
                qtrOBJ.put("examPercentage", qtrList.get(j).getGradeTotalMarks());
                qtrOBJ.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                qtrOBJ.put("createdOn", "2022-12-20T13:24:46");
                qtrOBJ.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                qtrOBJ.put("updatedOn", "2022-12-20T13:24:46");
                qtrOBJ.put("title", qtrList.get(j).getGradeName());
            /*    qtrOBJ.put("doesExam", true);
                qtrOBJ.put("doesGrades", true);
                qtrOBJ.put("isProgressPeriodExists", false);
*/


            } catch (JSONException e) {
                e.printStackTrace();
            }


            qtrArray.put(qtrOBJ);

        }






        for (int j = 0; j < semList.size(); j++) {
            JSONObject semOBJ = new JSONObject();
            try {
                semOBJ.put("id", semList.get(j).getSemoUpdateID());
                semOBJ.put("gradebookConfigurationId",gradebookConfigurationId);
                semOBJ.put("tenantId", AppData.tenatID);
                semOBJ.put("schoolId", pref.getSchoolID());
                semOBJ.put("courseId", courseID);
                semOBJ.put("courseSectionId", courseSectionID);
                semOBJ.put("academicYear", pref.getAcademicYear());
                semOBJ.put("smstrMarkingPeriodId", semList.get(j).getSemID());
                semOBJ.put("qtrMarkingPeriodId", null);
                semOBJ.put("gradingPercentage", semList.get(j).getQtrMarks());
                semOBJ.put("examPercentage", 0);
                semOBJ.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                semOBJ.put("createdOn", "2022-12-20T13:24:46");
                semOBJ.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                semOBJ.put("updatedOn", "2022-12-20T13:24:46");
                semOBJ.put("title", semList.get(j).getSemName());
               /* semqtrOBJ.put("doesExam",true);
                semqtrOBJ.put("doesGrades",true);
                semqtrOBJ.put("isQuarterExists",true);*/
                semArray.put(semOBJ);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        for (int p=0;p<semqtrrList.size();p++){
            JSONObject semqtrOBJ = new JSONObject();

            try {
                semqtrOBJ.put("id", 0);
                semqtrOBJ.put("gradebookConfigurationId",gradebookConfigurationId);
                semqtrOBJ.put("tenantId", AppData.tenatID);
                semqtrOBJ.put("schoolId", pref.getSchoolID());
                semqtrOBJ.put("courseId", courseID);
                semqtrOBJ.put("courseSectionId", courseSectionID);
                semqtrOBJ.put("academicYear", pref.getAcademicYear());
                semqtrOBJ.put("smstrMarkingPeriodId", semqtrrList.get(p).getSemID());
                semqtrOBJ.put("qtrMarkingPeriodId", semqtrrList.get(p).getMarkingPeriodId());
                semqtrOBJ.put("gradingPercentage", semqtrrList.get(p).getQtrMarks());
                semqtrOBJ.put("examPercentage", 0);
                semqtrOBJ.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                semqtrOBJ.put("createdOn", "2022-12-20T13:24:46");
                semqtrOBJ.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                semqtrOBJ.put("updatedOn", "2022-12-20T13:24:46");
                semqtrOBJ.put("title", semqtrrList.get(p).getTitle());
                semqtrOBJ.put("doesExam",true);
                semqtrOBJ.put("doesGrades",true);
                semqtrOBJ.put("isQuarterExists",true);
                semArray.put(semqtrOBJ);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        JSONObject fullYrobj = new JSONObject();
        try {
            fullYrobj.put("tenantId", AppData.tenatID);
            fullYrobj.put("schoolId", pref.getSchoolID());
            fullYrobj.put("courseId", courseID);
            fullYrobj.put("courseSectionId", courseSectionID);
            fullYrobj.put("academicYear", pref.getAcademicYear());
            fullYrobj.put("yrMarkingPeriodId", fyid);
            fullYrobj.put("smstrMarkingPeriodId", null);
            fullYrobj.put("gradingPercentage", 0);
            fullYrobj.put("examPercentage", 0);
            fullYrobj.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            fullYrobj.put("createdOn", "2022-12-20T13:24:46");
            fullYrobj.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            fullYrobj.put("updatedOn", "2022-12-20T13:24:46");
            fullYrobj.put("title", fYTitle);
           /* fullYrobj.put("doesExam",true);
            fullYrobj.put("doesGrades",true);
            fullYrobj.put("isSemesterExists",true);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }



        for (int j = 0; j < fySemList.size(); j++) {
            JSONObject yrsemOBJ = new JSONObject();
            try {
                yrsemOBJ.put("tenantId", AppData.tenatID);
                yrsemOBJ.put("schoolId", pref.getSchoolID());
                yrsemOBJ.put("courseId", courseID);
                yrsemOBJ.put("courseSectionId", courseSectionID);
                yrsemOBJ.put("academicYear", pref.getAcademicYear());
                yrsemOBJ.put("yrMarkingPeriodId", fyid);
                yrsemOBJ.put("smstrMarkingPeriodId", fySemList.get(j).getSemID());
                yrsemOBJ.put("gradingPercentage", fySemList.get(j).getSemMarks());
                yrsemOBJ.put("examPercentage", 0);
                fullYrobj.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                fullYrobj.put("createdOn", "2022-12-20T13:24:46");
                fullYrobj.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
                fullYrobj.put("updatedOn", "2022-12-20T13:24:46");
                yrsemOBJ.put("title", fySemList.get(j).getSemName());
                /*yrsemOBJ.put("doesExam",true);
                yrsemOBJ.put("doesGrades",true);
                yrsemOBJ.put("isQuarterExists",true);*/
                fullYearArray.put(yrsemOBJ);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (fySemList.size() > 0) {
            fullYearArray.put(fullYrobj);
        } else {
            fullYearArray = new JSONArray();
        }

        JSONObject graderBookConfig = new JSONObject();
        JSONArray gradebookConfigurationProgressPeriods = new JSONArray();
        try {

            graderBookConfig.put("tenantId", pref.getTenatID());
            graderBookConfig.put("schoolId", pref.getSchoolID());
            graderBookConfig.put("courseId", courseID);
            graderBookConfig.put("courseSectionId", courseSectionID);
            graderBookConfig.put("gradebookConfigurationId", gradebookConfigurationId);
            graderBookConfig.put("general", general);
            graderBookConfig.put("scoreRounding", score);
            graderBookConfig.put("assignmentSorting", assesment);
            graderBookConfig.put("maxAnomalousGrade", etPercent.getText().toString());
            graderBookConfig.put("upgradedAssignmentGradeDays", etDays.getText().toString());
            graderBookConfig.put("createdBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            graderBookConfig.put("createdOn", "2022-12-20T13:24:46");
            graderBookConfig.put("updatedBy", "2199863c-a77b-4e86-bad3-a524424d969b");
            graderBookConfig.put("updatedOn", "2022-12-20T13:24:46");
            graderBookConfig.put("lmsGradeSync", 1);
            graderBookConfig.put("gradebookConfigurationGradescale", gradebookConfigurationProgressPeriods);
            graderBookConfig.put("gradebookConfigurationProgressPeriods", gradebookConfigurationProgressPeriods);
            graderBookConfig.put("gradebookConfigurationQuarter", qtrArray);
            graderBookConfig.put("gradebookConfigurationSemester", semArray);
            graderBookConfig.put("gradebookConfigurationYear", fullYearArray);


            JSONObject obj = new JSONObject();
            obj.put("gradebookConfiguration", graderBookConfig);
            obj.put("_tenantName", pref.getTenatName());
            obj.put("_userName", pref.getName());
            obj.put("_token", pref.getToken());
            obj.put("_tokenExpiry", "0001-01-01T00:00:00+00:00");
            obj.put("_failure", false);
            obj.put("_academicYear", pref.getAcademicYear());
            obj.put("tenantId", AppData.tenatID);
            obj.put("schoolId", pref.getSchoolID());

            Log.d("finalOBJ", obj.toString());
            postGradeBook(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == tvSubmit) {

                editGradeBookConfig();

        }
    }

    public void postGradeBook(JSONObject jsonObject) {
        String url=pref.getAPI()+"StaffPortalGradebook/addUpdateGradebookConfiguration";


        new PostJsonDataParser(getContext(), Request.Method.POST, url, jsonObject, true, true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("gradeResponse", response.toString());

                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message = response.optString("_message");
                        if (_failure) {

                            Toast.makeText(getContext(), _message, Toast.LENGTH_SHORT).show();
                        } else {
                            semArray=new JSONArray();
                            qtrArray=new JSONArray();
                            fullYearArray=new JSONArray();
                            Toast.makeText(getContext(), _message, Toast.LENGTH_SHORT).show();
                           /* JSONObject bookConfigOBJ = new JSONObject();
                            JSONArray blankArray = new JSONArray();
                            bookConfigOBJ.put("scoreRounding", "up");
                            bookConfigOBJ.put("assignmentSorting", "newestFirst");
                            bookConfigOBJ.put("gradebookConfigurationProgressPeriods", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationQuarter", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationSemester", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationYear", blankArray);
                            bookConfigOBJ.put("general", "");
                            bookConfigOBJ.put("courseId", courseID);
                            bookConfigOBJ.put("lmsGradeSync", 1);
                            bookConfigOBJ.put("courseSectionId", courseSectionID);
                            bookConfigOBJ.put("academicYear", pref.getAcademicYear());
                            bookConfigOBJ.put("schoolId", pref.getSchoolID());
                            bookConfigOBJ.put("tenantId", AppData.tenatID);
                            JSONObject configOBJ = new JSONObject();
                            configOBJ.put("gradebookConfiguration", bookConfigOBJ);
                            configOBJ.put("_tenantName", pref.getTenatName());
                            configOBJ.put("_userName", pref.getName());
                            configOBJ.put("_token", pref.getToken());
                            configOBJ.put("_academicYear", pref.getAcademicYear());
                            configOBJ.put("tenantId", AppData.tenatID);
                            configOBJ.put("schoolId", pref.getSchoolID());
                            viewGradeBookConfig(configOBJ);*/


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

}