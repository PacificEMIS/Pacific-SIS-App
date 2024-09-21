package com.opensissasstudent.ui.student.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.opensissasstudent.R;
import com.opensissasstudent.adapter.CourseDetailsAdapter;
import com.opensissasstudent.databinding.ActivityCourseCatalogDetailsBinding;
import com.opensissasstudent.model.CourseVeriableScheduleModel;
import com.opensissasstudent.model.CurseDetailsModel;
import com.opensissasstudent.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CourseCatalogDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityCourseCatalogDetailsBinding binding;
    String courseTitle,courseShortName,credithours,subject,grade,coursesectionarray;
    JSONArray jsonArray;
    ArrayList<CurseDetailsModel>itemList=new ArrayList<>();
    ArrayList<CourseVeriableScheduleModel>variableScheduleList=new ArrayList<>();
    ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_course_catalog_details);
        initView();
    }

    private void initView(){
        imgBack=(ImageView)findViewById(R.id.imgBack);
        courseTitle=getIntent().getStringExtra("courseTitle");
        courseShortName=getIntent().getStringExtra("courseShortName");
        credithours=getIntent().getStringExtra("credithours");
        subject=getIntent().getStringExtra("subject");
        grade=getIntent().getStringExtra("grade");
        coursesectionarray=getIntent().getStringExtra("coursesectionarray");

        binding.tvCourseTitle.setText(courseTitle);
        binding.tvCourseShortName.setText(courseShortName);
        binding.tvCredtHours.setText(credithours);
        binding.tvSubject.setText(subject);
        binding.tvGradeLevel.setText(grade);

        binding.rvItem.setLayoutManager(new LinearLayoutManager(CourseCatalogDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        try {
            jsonArray=new JSONArray(coursesectionarray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imgBack.setOnClickListener(this);
        getcoursedetails();
    }

    private void getcoursedetails(){

        for (int i=0;i<jsonArray.length();i++){
            JSONObject obj=jsonArray.optJSONObject(i);
            String staffName= Util.getFreshValue(obj.optString("staffName"),"");
            JSONObject courseSection=obj.optJSONObject("courseSection");
            String courseSectionName=courseSection.optString("courseSectionName");
            String meetingDays=courseSection.optString("meetingDays");
            String scheduleType=courseSection.optString("scheduleType");
            String markingPeriod=obj.optString("markingPeriod");
            JSONObject courseFixedSchedule=obj.optJSONObject("courseFixedSchedule");
            if (courseFixedSchedule!=null){
                JSONObject blockPeriod=courseFixedSchedule.optJSONObject("blockPeriod");
                String periodTitle=blockPeriod.optString("periodTitle");
                String periodStartTime=blockPeriod.optString("periodStartTime");
                String periodEndTime=blockPeriod.optString("periodEndTime");
                JSONObject rooms=courseFixedSchedule.optJSONObject("rooms");
                String title=rooms.optString("title");
                CurseDetailsModel coursemodel=new CurseDetailsModel();
                coursemodel.setCourseSectionName(courseSectionName);
                coursemodel.setMeetingDays(meetingDays);
                coursemodel.setScheduleType(scheduleType);
                coursemodel.setMarkingPeriod(markingPeriod);
                coursemodel.setPeriodTitle(periodTitle);
                coursemodel.setTime(periodStartTime+":"+periodEndTime);
                coursemodel.setRoom(title);
                coursemodel.setStaffName(staffName);
                itemList.add(coursemodel);
            }else {
                JSONArray courseVariableSchedule=obj.optJSONArray("courseVariableSchedule");
                for (int j=0;j<courseVariableSchedule.length();j++){
                    JSONObject variableobj=courseVariableSchedule.optJSONObject(j);
                    String day=variableobj.optString("day");
                    JSONObject blockPeriod=variableobj.optJSONObject("blockPeriod");
                    String periodTitle=blockPeriod.optString("periodTitle");
                    String periodStartTime=blockPeriod.optString("periodStartTime");
                    String periodEndTime=blockPeriod.optString("periodEndTime");
                    JSONObject rooms=variableobj.optJSONObject("rooms");
                    String title=rooms.optString("title");
                    CourseVeriableScheduleModel variableModel=new CourseVeriableScheduleModel();
                    variableModel.setDay(day);
                    variableModel.setPeriod(periodTitle);
                    variableModel.setRoom(title);
                    variableModel.setTime(periodStartTime+":"+periodEndTime);
                    variableScheduleList.add(variableModel);



                }

                CurseDetailsModel coursemodel=new CurseDetailsModel();
                coursemodel.setCourseSectionName(courseSectionName);
                coursemodel.setScheduleType(scheduleType);
                coursemodel.setMarkingPeriod(markingPeriod);
                coursemodel.setStaffName(staffName);
                coursemodel.setMeetingDays("");
                coursemodel.setVariablescheduleList(variableScheduleList);
                itemList.add(coursemodel);
            }









        }

        CourseDetailsAdapter detailsAdapter=new CourseDetailsAdapter(itemList,CourseCatalogDetailsActivity.this);
        binding.rvItem.setAdapter(detailsAdapter);

    }

    @Override
    public void onClick(View v) {
        if (v==binding.imgBack){
            onBackPressed();
        }
    }
}