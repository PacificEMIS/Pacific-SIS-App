package com.opensis.ui.teacher.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.TeacherGradeAdapter;
import com.opensis.model.TeacherGradeModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.ui.common.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GradeBookGradesFragment extends Fragment {

    View view;
    RecyclerView rvItem;
    ArrayList<TeacherGradeModel> itmList=new ArrayList<>();
    Pref pref;
    int courseSectionID;
    LinearLayout lnNoData;
    int academicYear;
    String gradescaletype;
    LinearLayout lnAccess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_grade_book_grades, container, false);
        initView();
        return view;
    }


    private void initView(){
        courseSectionID= getArguments().getInt("courseSectionID");
        gradescaletype=getArguments().getString("gradescaletype");
        lnNoData=(LinearLayout)view.findViewById(R.id.lnNoData);
        lnAccess=(LinearLayout)view.findViewById(R.id.lnAccess);
        pref=new Pref(getContext());

        academicYear=pref.getAcademicYear();

        rvItem=(RecyclerView)view.findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    private void setAdapter(){
        TeacherGradeAdapter gradeAdapter=new TeacherGradeAdapter(itmList,getContext(),courseSectionID);
        rvItem.setAdapter(gradeAdapter);
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
                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("courseSectionId",courseSectionID);
                                jsonObject.put("SearchValue",null);
                                jsonObject.put("allCourse",true);
                                jsonObject.put("includeInactive", false);
                                jsonObject.put("_tenantName",pref.getTenatName());
                                jsonObject.put("_userName",pref.getName());
                                jsonObject.put("_token",pref.getToken());
                                jsonObject.put("tenantId", AppData.tenatID);
                                jsonObject.put("schoolId",pref.getSchoolID());
                                jsonObject.put("academicYear",academicYear);
                                jsonObject.put("markingPeriodStartDate",pref.getPeriodStartYear());
                                jsonObject.put("markingPeriodEndDate",pref.getPeriodEndYear());
                                getGraderBookItem(jsonObject);
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


    public void getGraderBookItem(JSONObject jsonObject) {
        rvItem.setVisibility(View.VISIBLE);
        lnNoData.setVisibility(View.GONE);
        itmList.clear();

        String url=pref.getAPI()+"StaffPortalGradebook/getGradebookGrade";


        new PostJsonDataParser(getContext(), Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("dashboardresponse",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){

                            rvItem.setVisibility(View.GONE);
                            lnNoData.setVisibility(View.VISIBLE);

                        }else {
                            JSONArray assignmentsListViewModels = response.optJSONArray("assignmentsListViewModels");
                            if (assignmentsListViewModels.length() > 0) {
                                for (int i = 0; i < assignmentsListViewModels.length(); i++) {
                                    JSONObject obj = assignmentsListViewModels.optJSONObject(i);
                                    String title=obj.optString("title");
                                    int assignmentId=obj.optInt("assignmentId");
                                    int assignmentTypeId=obj.optInt("assignmentTypeId");
                                    String assignmentTitle=obj.optString("assignmentTitle");
                                    String assignmentDate=obj.optString("assignmentDate").replace("T00:00:00","");
                                    String dueDate=obj.optString("dueDate").replace("T00:00:00","");

                                    JSONArray studentsListViewModels=obj.optJSONArray("studentsListViewModels");
                                    String studentList=studentsListViewModels.toString();
                                    TeacherGradeModel gradeModel=new TeacherGradeModel();
                                    gradeModel.setStudentList(studentList);
                                    gradeModel.setAssignedDate(assignmentDate);
                                    gradeModel.setDueDate(dueDate);
                                    gradeModel.setGradeName(assignmentTitle);
                                    gradeModel.setGradeType(title);
                                    gradeModel.setGraderID(assignmentId);
                                    gradeModel.setAssignmentTypeId(assignmentTypeId);
                                    gradeModel.setGradeList(assignmentsListViewModels.toString());
                                    itmList.add(gradeModel);


                                }
                                rvItem.setVisibility(View.VISIBLE);
                                lnNoData.setVisibility(View.GONE);


                                setAdapter();
                            }else {
                                rvItem.setVisibility(View.GONE);
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

    @Override
    public void onResume() {
        super.onResume();

        if (gradescaletype.equals("Ungraded")){
            rvItem.setVisibility(View.GONE);
            lnNoData.setVisibility(View.GONE);
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
    }
}