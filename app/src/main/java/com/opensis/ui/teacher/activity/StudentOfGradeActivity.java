package com.opensis.ui.teacher.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.opensis.R;
import com.opensis.adapter.StudentofGradeAdapter;
import com.opensis.model.AssignmentDetailsModel;
import com.opensis.model.StudentofGradeModel;
import com.opensis.others.parser.PostJsonDataParser;
import com.opensis.others.utility.Api;
import com.opensis.others.utility.AppData;
import com.opensis.others.utility.Pref;
import com.opensis.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentOfGradeActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvItem;
    ArrayList<StudentofGradeModel> itemList=new ArrayList<>();
    String studentList;
    int assignmentID,assignmentTypeID;
    JSONArray studentArray=new JSONArray();
    String gradeName;
    int pos;
    String gradeList,assignedDate,dueDate,gradeTitle;
    ImageView imgEdit;
    ArrayList<String>itemListNumber=new ArrayList<>();
    ArrayList<String>itemListComments=new ArrayList<>();
    EditText etFocus;
    TextView tvSaveGrade;
    JSONArray jArray=new JSONArray();
    JSONArray assismentViewModelArray;
    Pref pref;
    int courseSectionID;
    TextView tvGradeName;
    ImageView imgBack;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_of_grade);
        initView();

    }

    private void initView(){
        pref=new Pref(StudentOfGradeActivity.this);
        imgBack=(ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        etFocus=(EditText)findViewById(R.id.etFocus);
        tvSaveGrade=(TextView)findViewById(R.id.tvSaveGrade);
        courseSectionID=getIntent().getIntExtra("courseSectionID",0);
        assignmentID=getIntent().getIntExtra("assignmentID",0);
        assignmentTypeID=getIntent().getIntExtra("assignmentTypeID",0);
        pos=getIntent().getIntExtra("pos",0);
        gradeName=getIntent().getStringExtra("gradeName");
        studentList=getIntent().getStringExtra("studentList");
        gradeList=getIntent().getStringExtra("gradeList");
        assignedDate=getIntent().getStringExtra("assignedDate")+"T00:00:00";
        dueDate=getIntent().getStringExtra("dueDate")+"T00:00:00";
        gradeTitle=getIntent().getStringExtra("gradeName");
        imgEdit=(ImageView)findViewById(R.id.imgEdit);
        tvGradeName=(TextView)findViewById(R.id.tvGradeName);
        tvGradeName.setText(gradeTitle);
        imgEdit.setOnClickListener(this);
        try {
            assismentViewModelArray=new JSONArray(gradeList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            studentArray=new JSONArray(studentList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rvItem=(RecyclerView)findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(StudentOfGradeActivity.this, LinearLayoutManager.VERTICAL, false));
        getStudentsList();
        tvSaveGrade.setOnClickListener(this);
    }

    private void getStudentsList(){
        itemList.clear();

        if (studentArray.length()>0){
            for (int i=0;i<studentArray.length();i++){
                JSONObject obj=studentArray.optJSONObject(i);
                int studentId=obj.optInt("studentId");
                String studentInternalId=obj.optString("studentInternalId");
                String firstGivenName=obj.optString("firstGivenName");
                String lastFamilyName=obj.optString("lastFamilyName");
                String name=firstGivenName+" "+lastFamilyName;
                String points= String.valueOf(obj.optInt("points"));
                String allowedMarks=obj.optString("allowedMarks");
                String comment=obj.optString("comment");
                StudentofGradeModel gradeModel=new StudentofGradeModel();
                gradeModel.setComment(Util.getFreshValue(comment,""));
                gradeModel.setStudentName(name);
                gradeModel.setMarks(Util.getFreshValue(allowedMarks,"0"));
                gradeModel.setStudentID(studentInternalId);
                gradeModel.setTotalMarks(Util.getFreshValue(points,"0"));
                gradeModel.setId(studentId);
                gradeModel.setStudentFrstName(firstGivenName);
                gradeModel.setStudentLstName(lastFamilyName);
                itemList.add(gradeModel);

            }
            StudentofGradeAdapter studentofGradeAdapter=new StudentofGradeAdapter(itemList,StudentOfGradeActivity.this,0);
            rvItem.setAdapter(studentofGradeAdapter);
        }
    }
    private void getStudentsListForEdit(){
        itemList.clear();

        if (studentArray.length()>0){
            for (int i=0;i<studentArray.length();i++){
                JSONObject obj=studentArray.optJSONObject(i);
                int studentId=obj.optInt("studentId");
                String studentInternalId=obj.optString("studentInternalId");
                String firstGivenName=obj.optString("firstGivenName");
                String lastFamilyName=obj.optString("lastFamilyName");
                String name=firstGivenName+" "+lastFamilyName;
                String points= String.valueOf(obj.optInt("points"));
                String allowedMarks=obj.optString("allowedMarks");
                String comment=obj.optString("comment");
                StudentofGradeModel gradeModel=new StudentofGradeModel();
                gradeModel.setComment(Util.getFreshValue(comment,""));
                gradeModel.setStudentName(name);
                gradeModel.setMarks(Util.getFreshValue(allowedMarks,"0"));
                gradeModel.setStudentID(studentInternalId);
                gradeModel.setTotalMarks(Util.getFreshValue(points,"0"));
                gradeModel.setId(studentId);
                gradeModel.setStudentFrstName(firstGivenName);
                gradeModel.setStudentLstName(lastFamilyName);
                itemList.add(gradeModel);

            }
            StudentofGradeAdapter studentofGradeAdapter=new StudentofGradeAdapter(itemList,StudentOfGradeActivity.this,1);
            rvItem.setAdapter(studentofGradeAdapter);
            rvItem.setItemViewCacheSize(itemList.size());
        }
    }


    @Override
    public void onClick(View view) {
        if (view==imgEdit){
            getStudentsListForEdit();
           // etFocus.requestFocus();
            tvSaveGrade.setVisibility(View.VISIBLE);
        }else if (view==tvSaveGrade){
            postGrade();

        }else if (view==imgBack){
            onBackPressed();
        }
    }


    public void updateItemStatusForNumber(int position, boolean isSelected) {

        if (isSelected) {
            itemListNumber.add(itemList.get(position).getMarks());
        }

    }

    public void updateItemStatusForCommets(int position, boolean isSelected) {


        itemListComments.add(itemList.get(position).getComment());

    }





    private void postGrade(){
        itemListNumber.clear();
        itemListComments.clear();
        for (int i = 0; i < itemList.size(); i++) {
            View view = rvItem.getChildAt(i);
            EditText etNumber = (EditText) view.findViewById(R.id.etNumber);
            String number = etNumber.getText().toString();
            itemList.get(i).setMarks(number);

            EditText etComment = (EditText) view.findViewById(R.id.etComment);
            String comment = etComment.getText().toString();

            itemList.get(i).setComment(comment);


        }


        for (int j=0;j<itemList.size();j++){
            int a= Integer.parseInt(itemList.get(j).getMarks());
            int b= Integer.parseInt(itemList.get(j).getTotalMarks());
            double c=a*100;
            double d=c/b;
            String percentage= String.valueOf(d);
            JSONObject studentViewobject=new JSONObject();
            try {
                studentViewobject.put("studentId",itemList.get(j).getId());
                studentViewobject.put("studentInternalId",itemList.get(j).getStudentID());
                studentViewobject.put("firstGivenName",itemList.get(j).getStudentFrstName());
                studentViewobject.put("middleName",null);
                studentViewobject.put("lastFamilyName",itemList.get(j).getStudentLstName());
                studentViewobject.put("studentPhoto",null);
                studentViewobject.put("runningAvg",percentage);
                studentViewobject.put("runningAvgGrade",null);
                studentViewobject.put("points",itemList.get(j).getTotalMarks());
                studentViewobject.put("allowedMarks",itemList.get(j).getMarks());
                studentViewobject.put("percentage",percentage);
                studentViewobject.put("letterGrade","");
                studentViewobject.put("comment",itemList.get(j).getComment());

            } catch (JSONException e) {
                e.printStackTrace();
            }


            jArray.put(studentViewobject);

        }
        Log.d("jArray",jArray.toString());
        JSONObject obj=new JSONObject();
        try {
            obj.put("assignmentTypeId",assignmentTypeID);
            obj.put("title",gradeTitle);
            obj.put("weightage",null);
            obj.put("assignmentId",assignmentID);
            obj.put("assignmentTitle",gradeName);
            obj.put("assignmentDate",assignedDate);
            obj.put("dueDate",dueDate);
            obj.put("studentsListViewModels",jArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            assismentViewModelArray.put(pos,obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obbj=new JSONObject();
        try {
            obbj.put("assignmentsListViewModels",assismentViewModelArray);
            obbj.put("tenantId", AppData.tenatID);
            obbj.put("schoolId", pref.getSchoolID());
            obbj.put("courseSectionId", courseSectionID);
            obbj.put("createdBy", "392a89a6-ff74-47c2-ab69-04ae19bcaeb5");
            obbj.put("markingPeriodId", "1_1");
            obbj.put("_userName", pref.getName());
            obbj.put("_tenantName",pref.getTenatName());
            obbj.put("_token",pref.getToken());
            obbj.put("_tokenExpiry","0001-01-01T00:00:00+00:00");
            obbj.put("_failure",false);
            addGrade(obbj);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void addGrade(JSONObject jsonObject) {
        String url=pref.getAPI()+"StaffPortalGradebook/addGradebookGrade";

        new PostJsonDataParser(StudentOfGradeActivity.this, Request.Method.POST, url,jsonObject, true,true, new PostJsonDataParser.OnGetResponseListner() {
            @Override
            public void onGetResponse(JSONObject response) {
                if (response != null) {
                    Log.d("addassignmnet",response.toString());
                    try {
                        boolean _failure = response.optBoolean("_failure");
                        String _message=response.optString("_message");
                        if (_failure){
                            Toast.makeText(StudentOfGradeActivity.this,_message,Toast.LENGTH_LONG).show();



                        }else {
                            Toast.makeText(StudentOfGradeActivity.this,_message,Toast.LENGTH_LONG).show();

                            onBackPressed();

                        }






                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

}