package com.opensis.ui.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ActionProvider;
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

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.FYSemAdapter;
import com.opensis.adapter.FinalGradeSemAdapter;
import com.opensis.adapter.FinalQtrGradeAdapter;
import com.opensis.model.FYModel;
import com.opensis.model.FinalGradeQtrModel;
import com.opensis.model.FinalGradeSemModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;
import com.opensis.ui.common.activity.LoginActivity;

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
                            bookConfigOBJ.put("scoreRounding", "assignmentSorting");
                            bookConfigOBJ.put("gradebookConfigurationProgressPeriods", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationQuarter", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationSemester", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationYear", blankArray);
                            bookConfigOBJ.put("general", "");
                            bookConfigOBJ.put("courseId", courseID);
                            bookConfigOBJ.put("courseSectionId", courseSectionID);
                            bookConfigOBJ.put("academicYear", pref.getAcademyYear());
                            bookConfigOBJ.put("schoolId", pref.getSchoolID());
                            bookConfigOBJ.put("tenantId", AppData.tenatID);
                            JSONObject configOBJ = new JSONObject();
                            configOBJ.put("gradebookConfiguration", bookConfigOBJ);
                            configOBJ.put("_tenantName", pref.getTenatName());
                            configOBJ.put("_userName", pref.getName());
                            configOBJ.put("_token", pref.getToken());
                            configOBJ.put("tenantId", AppData.tenatID);
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
                            jsonObject1.put("tenantId", AppData.tenatID);
                            jsonObject1.put("schoolId", pref.getSchoolID());
                            jsonObject1.put("academicYear", pref.getAcademyYear());
                            getGrade(jsonObject1);
                            editFlag=false;

                        } else {
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

                            JSONArray gradebookConfigurationQuarter = gradebookConfiguration.optJSONArray("gradebookConfigurationQuarter");
                            if (gradebookConfigurationQuarter.length() > 0) {
                                for (int i = 0; i < gradebookConfigurationQuarter.length(); i++) {

                                    JSONObject quaterOBJ = gradebookConfigurationQuarter.optJSONObject(i);
                                    String title = quaterOBJ.optString("title");
                                    int markingPeriodId = quaterOBJ.optInt("qtrMarkingPeriodId");
                                    int id = quaterOBJ.optInt("id");
                                    String gradingPercentage = String.valueOf(quaterOBJ.optInt("gradingPercentage"));
                                    String examPercentage = String.valueOf(quaterOBJ.optInt("examPercentage"));

                                    FinalGradeQtrModel qtrModel = new FinalGradeQtrModel();
                                    qtrModel.setGradeMarks(examPercentage);
                                    qtrModel.setQtrUpdateID(id);
                                    qtrModel.setGradeTotalMarks(gradingPercentage);
                                    qtrModel.setGradeName("Quarter "+markingPeriodId);
                                    qtrModel.setMarkingPeriodId(markingPeriodId);

                                    qtrList.add(qtrModel);
                                }

                                FinalQtrGradeAdapter qtrGradeAdapter = new FinalQtrGradeAdapter(qtrList, getContext());
                                rvFinalQtrGrade.setAdapter(qtrGradeAdapter);

                            }


                            JSONArray semesters = gradebookConfiguration.optJSONArray("gradebookConfigurationSemester");
                            if (semesters.length() > 0) {

                                JSONObject semOBJ = semesters.optJSONObject(0);
                                semmarkingPeriodId = semOBJ.optInt("smstrMarkingPeriodId");
                                semTitle = "Semester "+semmarkingPeriodId;
                                tvSemExam.setText(semTitle + " Exam");
                                int id=semOBJ.optInt("id");
                                semUpdateID=id;
                                int qtrmarkingPeriodId = semOBJ.optInt("qtrMarkingPeriodId");
                                String gradingPercentage= String.valueOf(semOBJ.optInt("gradingPercentage"));
                                JSONObject semOBJJ = semesters.optJSONObject(semesters.length()-1);
                                String examPercentage= String.valueOf(semOBJJ.optInt("examPercentage"));
                                etSemExam.setText(examPercentage);
                                FinalGradeSemModel semModel = new FinalGradeSemModel();
                                semModel.setSemName("Semester "+semmarkingPeriodId);
                                semModel.setQtrID(qtrmarkingPeriodId);
                                semModel.setSemID(semmarkingPeriodId);
                                semModel.setQtrName("Quarter "+qtrmarkingPeriodId);
                                semModel.setSemMarks("0");
                                semModel.setQtrMarks(gradingPercentage);
                                semModel.setSemoUpdateID(id);
                                semList.add(semModel);


                                FinalGradeSemAdapter semAdapter = new FinalGradeSemAdapter(semList, getContext());
                                rvFinalSemGrade.setAdapter(semAdapter);



                            } else {
                                lnSem.setVisibility(View.GONE);
                            }


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
                                    qtrModel.setGradeMarks("0");
                                    qtrModel.setGradeTotalMarks("0");
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

                                JSONObject semOBJ = semesters.optJSONObject(0);
                                String title = semOBJ.optString("title");
                                boolean doesExam=semOBJ.optBoolean("doesExam");
                                semExam=doesExam;
                                tvSemExam.setText(title + " Exam");
                                semTitle = title;
                                semmarkingPeriodId = semOBJ.optInt("markingPeriodId");
                                JSONArray semquarters = semOBJ.optJSONArray("quarters");

                                JSONObject semone = semquarters.optJSONObject(semquarters.length() - 1);
                                String qone = semone.optString("title");
                                int qtrmarkingPeriodId = semone.optInt("markingPeriodId");
                                FinalGradeSemModel semModel = new FinalGradeSemModel();
                                semModel.setSemName(title);
                                semModel.setQtrID(qtrmarkingPeriodId);
                                semModel.setSemID(semmarkingPeriodId);
                                semModel.setQtrName(qone);
                                semModel.setSemMarks("0");
                                semModel.setQtrMarks("0");
                                semList.add(semModel);


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

                                JSONObject fysemOBJ = fysemesters.optJSONObject(0);
                                String semtitle = fysemOBJ.optString("title");
                                int markingPeriodId = fysemOBJ.optInt("markingPeriodId");
                                FYModel fyModel = new FYModel();
                                fyModel.setSemID(markingPeriodId);
                                fyModel.setYearID(fyid);
                                fyModel.setSemName(semtitle);
                                fyModel.setSemMarks("0");
                                fySemList.add(fyModel);

                                if (doesExam){
                                    lnFullYearExam.setVisibility(View.VISIBLE);
                                }else {
                                    lnFullYearExam.setVisibility(View.GONE);
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


        for (int i = 0; i < qtrList.size(); i++) {
            View view = rvFinalQtrGrade.getChildAt(i);
            EditText etGradeTotal = (EditText) view.findViewById(R.id.etGradeTotal);
            String getMarks = etGradeTotal.getText().toString();
            EditText etGradeExam = (EditText) view.findViewById(R.id.etGradeExam);
            qtrList.get(i).setGradeMarks(getMarks);
            qtrList.get(i).setGradeTotalMarks(etGradeExam.getText().toString());

        }

        for (int j = 0; j < qtrList.size(); j++) {

            JSONObject qtrOBJ = new JSONObject();
            try {

                qtrOBJ.put("tenantId", AppData.tenatID);
                qtrOBJ.put("schoolId", pref.getSchoolID());
                qtrOBJ.put("courseId", courseID);
                qtrOBJ.put("courseSectionId", courseSectionID);
                qtrOBJ.put("academicYear", pref.getAcademyYear());
                qtrOBJ.put("qtrMarkingPeriodId", qtrList.get(j).getMarkingPeriodId());
                qtrOBJ.put("gradingPercentage", qtrList.get(j).getGradeMarks());
                qtrOBJ.put("examPercentage", qtrList.get(j).getGradeTotalMarks());
                qtrOBJ.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
                qtrOBJ.put("createdOn", "2022-12-20T13:24:46");
                qtrOBJ.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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

            semOBJ.put("tenantId", AppData.tenatID);
            semOBJ.put("schoolId", pref.getSchoolID());
            semOBJ.put("courseId", courseID);
            semOBJ.put("courseSectionId", courseSectionID);
            semOBJ.put("academicYear", pref.getAcademyYear());
            semOBJ.put("smstrMarkingPeriodId", semmarkingPeriodId);
            semOBJ.put("qtrMarkingPeriodId", null);
            semOBJ.put("gradingPercentage", 0);
            semOBJ.put("examPercentage", etSemExam.getText().toString());
            semOBJ.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            semOBJ.put("createdOn", "2022-12-20T13:24:46");
            semOBJ.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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
                semqtrOBJ.put("tenantId", AppData.tenatID);
                semqtrOBJ.put("schoolId", pref.getSchoolID());
                semqtrOBJ.put("courseId", courseID);
                semqtrOBJ.put("courseSectionId", courseSectionID);
                semqtrOBJ.put("academicYear", pref.getAcademyYear());
                semqtrOBJ.put("smstrMarkingPeriodId", semmarkingPeriodId);
                semqtrOBJ.put("qtrMarkingPeriodId", semList.get(j).getQtrID());
                semqtrOBJ.put("gradingPercentage", semList.get(j).getQtrMarks());
                semqtrOBJ.put("examPercentage", 0);
                semqtrOBJ.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
                semqtrOBJ.put("createdOn", "2022-12-20T13:24:46");
                semqtrOBJ.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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
            fullYrobj.put("tenantId", AppData.tenatID);
            fullYrobj.put("schoolId", pref.getSchoolID());
            fullYrobj.put("courseId", courseID);
            fullYrobj.put("courseSectionId", courseSectionID);
            fullYrobj.put("academicYear", pref.getAcademyYear());
            fullYrobj.put("yrMarkingPeriodId", fyid);
            fullYrobj.put("smstrMarkingPeriodId", null);
            fullYrobj.put("gradingPercentage", 0);
            fullYrobj.put("examPercentage", etFYExam.getText().toString());
            fullYrobj.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            fullYrobj.put("createdOn", "2022-12-20T13:24:46");
            fullYrobj.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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
                yrsemOBJ.put("tenantId", AppData.tenatID);
                yrsemOBJ.put("schoolId", pref.getSchoolID());
                yrsemOBJ.put("courseId", courseID);
                yrsemOBJ.put("courseSectionId", courseSectionID);
                yrsemOBJ.put("academicYear", pref.getAcademyYear());
                yrsemOBJ.put("yrMarkingPeriodId", fyid);
                yrsemOBJ.put("smstrMarkingPeriodId", fySemList.get(j).getSemID());
                yrsemOBJ.put("gradingPercentage", fySemList.get(j).getSemMarks());
                yrsemOBJ.put("examPercentage", 0);
                fullYrobj.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
                fullYrobj.put("createdOn", "2022-12-20T13:24:46");
                fullYrobj.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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
            graderBookConfig.put("tenantId", AppData.tenatID);
            graderBookConfig.put("schoolId", pref.getSchoolID());
            graderBookConfig.put("courseId", courseID);
            graderBookConfig.put("courseSectionId", courseSectionID);
            graderBookConfig.put("general", general);
            graderBookConfig.put("scoreRounding", score);
            graderBookConfig.put("assignmentSorting", assesment);
            graderBookConfig.put("maxAnomalousGrade", etPercent.getText().toString());
            graderBookConfig.put("upgradedAssignmentGradeDays", etDays.getText().toString());
            graderBookConfig.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            graderBookConfig.put("createdOn", "2022-12-20T13:24:46");
            graderBookConfig.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            graderBookConfig.put("updatedOn", "2022-12-20T13:24:46");
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
            postGradeBook(obj);

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


        for (int i = 0; i < qtrList.size(); i++) {
            View view = rvFinalQtrGrade.getChildAt(i);
            EditText etGradeTotal = (EditText) view.findViewById(R.id.etGradeTotal);
            String getMarks = etGradeTotal.getText().toString();
            EditText etGradeExam = (EditText) view.findViewById(R.id.etGradeExam);
            qtrList.get(i).setGradeMarks(getMarks);
            qtrList.get(i).setGradeTotalMarks(etGradeExam.getText().toString());

        }

        for (int j = 0; j < qtrList.size(); j++) {

            JSONObject qtrOBJ = new JSONObject();
            try {
                qtrOBJ.put("id", qtrList.get(j).getQtrUpdateID());
                qtrOBJ.put("gradebookConfigurationId",gradebookConfigurationId);
                qtrOBJ.put("tenantId", AppData.tenatID);
                qtrOBJ.put("schoolId", pref.getSchoolID());
                qtrOBJ.put("courseId", courseID);
                qtrOBJ.put("courseSectionId", courseSectionID);
                qtrOBJ.put("academicYear", pref.getAcademyYear());
                qtrOBJ.put("qtrMarkingPeriodId", qtrList.get(j).getMarkingPeriodId());
                qtrOBJ.put("gradingPercentage", qtrList.get(j).getGradeMarks());
                qtrOBJ.put("examPercentage", qtrList.get(j).getGradeTotalMarks());
                qtrOBJ.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
                qtrOBJ.put("createdOn", "2022-12-20T13:24:46");
                qtrOBJ.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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
            semOBJ.put("id", semUpdateID);
            semOBJ.put("gradebookConfigurationId",gradebookConfigurationId);
            semOBJ.put("tenantId", AppData.tenatID);
            semOBJ.put("schoolId", pref.getSchoolID());
            semOBJ.put("courseId", courseID);
            semOBJ.put("courseSectionId", courseSectionID);
            semOBJ.put("academicYear", pref.getAcademyYear());
            semOBJ.put("smstrMarkingPeriodId", semmarkingPeriodId);
            semOBJ.put("qtrMarkingPeriodId", null);
            semOBJ.put("gradingPercentage", 0);
            semOBJ.put("examPercentage", etSemExam.getText().toString());
            semOBJ.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            semOBJ.put("createdOn", "2022-12-20T13:24:46");
            semOBJ.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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
                semqtrOBJ.put("id", semList.get(j).getSemoUpdateID());
                semqtrOBJ.put("gradebookConfigurationId",gradebookConfigurationId);
                semqtrOBJ.put("tenantId", AppData.tenatID);
                semqtrOBJ.put("schoolId", pref.getSchoolID());
                semqtrOBJ.put("courseId", courseID);
                semqtrOBJ.put("courseSectionId", courseSectionID);
                semqtrOBJ.put("academicYear", pref.getAcademyYear());
                semqtrOBJ.put("smstrMarkingPeriodId", semmarkingPeriodId);
                semqtrOBJ.put("qtrMarkingPeriodId", semList.get(j).getQtrID());
                semqtrOBJ.put("gradingPercentage", semList.get(j).getQtrMarks());
                semqtrOBJ.put("examPercentage", 0);
                semqtrOBJ.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
                semqtrOBJ.put("createdOn", "2022-12-20T13:24:46");
                semqtrOBJ.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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
            fullYrobj.put("tenantId", AppData.tenatID);
            fullYrobj.put("schoolId", pref.getSchoolID());
            fullYrobj.put("courseId", courseID);
            fullYrobj.put("courseSectionId", courseSectionID);
            fullYrobj.put("academicYear", pref.getAcademyYear());
            fullYrobj.put("yrMarkingPeriodId", fyid);
            fullYrobj.put("smstrMarkingPeriodId", null);
            fullYrobj.put("gradingPercentage", 0);
            fullYrobj.put("examPercentage", etFYExam.getText().toString());
            fullYrobj.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            fullYrobj.put("createdOn", "2022-12-20T13:24:46");
            fullYrobj.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            fullYrobj.put("updatedOn", "2022-12-20T13:24:46");
            fullYrobj.put("title", fYTitle);
           /* fullYrobj.put("doesExam",true);
            fullYrobj.put("doesGrades",true);
            fullYrobj.put("isSemesterExists",true);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < fySemList.size(); i++) {
            View view = rvFullYR.getChildAt(i);
            EditText etGrade = (EditText) view.findViewById(R.id.etGrade);
            String qtrmarks = etGrade.getText().toString();
            ;
            fySemList.get(i).setSemMarks(qtrmarks);


        }

        for (int j = 0; j < fySemList.size(); j++) {
            JSONObject yrsemOBJ = new JSONObject();
            try {
                yrsemOBJ.put("tenantId", AppData.tenatID);
                yrsemOBJ.put("schoolId", pref.getSchoolID());
                yrsemOBJ.put("courseId", courseID);
                yrsemOBJ.put("courseSectionId", courseSectionID);
                yrsemOBJ.put("academicYear", pref.getAcademyYear());
                yrsemOBJ.put("yrMarkingPeriodId", fyid);
                yrsemOBJ.put("smstrMarkingPeriodId", fySemList.get(j).getSemID());
                yrsemOBJ.put("gradingPercentage", fySemList.get(j).getSemMarks());
                yrsemOBJ.put("examPercentage", 0);
                fullYrobj.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
                fullYrobj.put("createdOn", "2022-12-20T13:24:46");
                fullYrobj.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
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
            graderBookConfig.put("gradebookConfigurationId", gradebookConfigurationId);
            graderBookConfig.put("tenantId", AppData.tenatID);
            graderBookConfig.put("schoolId", pref.getSchoolID());
            graderBookConfig.put("courseId", courseID);
            graderBookConfig.put("courseSectionId", courseSectionID);
            graderBookConfig.put("general", general);
            graderBookConfig.put("scoreRounding", score);
            graderBookConfig.put("assignmentSorting", assesment);
            graderBookConfig.put("maxAnomalousGrade", etPercent.getText().toString());
            graderBookConfig.put("upgradedAssignmentGradeDays", etDays.getText().toString());
            graderBookConfig.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            graderBookConfig.put("createdOn", "2022-12-20T13:24:46");
            graderBookConfig.put("updatedBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            graderBookConfig.put("updatedOn", "2022-12-20T13:24:46");
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
            postGradeBook(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == tvSubmit) {
            if (editFlag){
                editGradeBookConfig();
            }else {
                submitGradeBookConfig();
            }
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
                            JSONObject bookConfigOBJ = new JSONObject();
                            JSONArray blankArray = new JSONArray();
                            bookConfigOBJ.put("scoreRounding", "up");
                            bookConfigOBJ.put("scoreRounding", "assignmentSorting");
                            bookConfigOBJ.put("gradebookConfigurationProgressPeriods", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationQuarter", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationSemester", blankArray);
                            bookConfigOBJ.put("gradebookConfigurationYear", blankArray);
                            bookConfigOBJ.put("general", "");
                            bookConfigOBJ.put("courseId", courseID);
                            bookConfigOBJ.put("courseSectionId", courseSectionID);
                            bookConfigOBJ.put("academicYear", pref.getAcademyYear());
                            bookConfigOBJ.put("schoolId", pref.getSchoolID());
                            bookConfigOBJ.put("tenantId", AppData.tenatID);
                            JSONObject configOBJ = new JSONObject();
                            configOBJ.put("gradebookConfiguration", bookConfigOBJ);
                            configOBJ.put("_tenantName", pref.getTenatName());
                            configOBJ.put("_userName", pref.getName());
                            configOBJ.put("_token", pref.getToken());
                            configOBJ.put("tenantId", AppData.tenatID);
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

}