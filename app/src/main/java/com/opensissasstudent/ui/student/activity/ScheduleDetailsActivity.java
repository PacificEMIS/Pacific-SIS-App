package com.opensissasstudent.ui.student.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.opensissasstudent.R;
import com.opensissasstudent.adapter.VariableScheduleAdapter;
import com.opensissasstudent.databinding.ActivityScheduleDetailsBinding;
import com.opensissasstudent.model.MyScheduleListModel;
import com.opensissasstudent.others.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleDetailsActivity extends AppCompatActivity {
    String courseTitle,courseSection,courseTeacher,markingPeriod,enrollDate,dropdate,scheduletype,period,room,meetingdays,variableSchedule,time;
    boolean attendancetaken;
    ActivityScheduleDetailsBinding binding;
    JSONArray variableArray;
    ArrayList<MyScheduleListModel>variablelist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_schedule_details);
        initView();
    }

    private void initView(){

        courseTitle=getIntent().getStringExtra("courseTitle");
        courseSection=getIntent().getStringExtra("courseSection");
        courseTeacher=getIntent().getStringExtra("courseTeacher");
        markingPeriod=getIntent().getStringExtra("markingPeriod");
        enrollDate=getIntent().getStringExtra("enrollDate");
        dropdate=getIntent().getStringExtra("dropdate");
        scheduletype=getIntent().getStringExtra("scheduletype");
        period=getIntent().getStringExtra("period");
        room=getIntent().getStringExtra("room");
        meetingdays=getIntent().getStringExtra("meetingdays");
        attendancetaken=getIntent().getBooleanExtra("attendancetaken",false);
        time=getIntent().getStringExtra("time");
        binding.tvCourseTitle.setText(courseTitle);
        binding.tvCourseSection.setText(courseSection);
        binding.tvTeacher.setText(courseTeacher);
        binding.tvEnrollDate.setText(Util.changeAnyDateFormat(enrollDate,"yyyy-MM-dd","dd MMM, yyyy"));
        binding.tvDropDate.setText(Util.changeAnyDateFormat(dropdate,"yyyy-MM-dd","dd MMM, yyyy"));
        binding.tvScheduleType.setText("Schedule Type: "+scheduletype);
        binding.tvPeriod.setText(period);
        binding.tvRoom.setText(room);
        binding.tvTime.setText(time);
        binding.tvMarkingPeriod.setText(markingPeriod);
        if (attendancetaken){
            binding.tvTakeAttendance.setText("Yes");
        }else {

        }


        if (meetingdays.contains("Monday")) {
            binding.lnMon.setBackgroundTintList(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.text_color));
            binding.tvMon.setTextColor(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.white));
        }
        if (meetingdays.contains("Tuesday")) {
            binding.lnTus.setBackgroundTintList(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.text_color));
            binding.tvTus.setTextColor(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.white));
        }

        if (meetingdays.contains("Wednesday")) {
            binding.lnWed.setBackgroundTintList(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.text_color));
            binding.tvWed.setTextColor(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.white));
        }

        if (meetingdays.contains("Thursday")) {
            binding.lnThu.setBackgroundTintList(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.text_color));
            binding.tvThu.setTextColor(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.white));
        }

        if (meetingdays.contains("Friday")) {
            binding.lnFri.setBackgroundTintList(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.text_color));
            binding.tvFri.setTextColor(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.white));
        }

        if (meetingdays.contains("Saturday")) {
            binding.lnSat.setBackgroundTintList(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.text_color));
            binding. tvSat.setTextColor(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.white));
        }

        if (meetingdays.contains("Sunday")) {
            binding. lnSun.setBackgroundTintList(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.text_color));
            binding. tvSun.setTextColor(ContextCompat.getColorStateList(ScheduleDetailsActivity.this, R.color.white));
        }

        binding.rvItem.setLayoutManager(new LinearLayoutManager(ScheduleDetailsActivity.this, LinearLayoutManager.VERTICAL, false));


        if (scheduletype.equalsIgnoreCase("Variable Schedule")){
            variableSchedule=getIntent().getStringExtra("variableSchedule");
            binding.lFixedSchedule.setVisibility(View.GONE);
            binding.lVariableSchedule.setVisibility(View.VISIBLE);
            try {
                variableArray=new JSONArray(variableSchedule);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            varaibleschedule();
        }else {
            binding.lFixedSchedule.setVisibility(View.VISIBLE);
            binding.lVariableSchedule.setVisibility(View.GONE);
        }

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void varaibleschedule(){
        for (int i=0;i<variableArray.length();i++){
            JSONObject object=variableArray.optJSONObject(i);
            String day=object.optString("day");
            boolean takeAttendance =object.optBoolean("takeAttendance");
            JSONObject blockPeriod=object.optJSONObject("blockPeriod");
            String periodShortName=blockPeriod.optString("periodShortName");
            String periodStartTime=blockPeriod.optString("periodStartTime");
            String periodEndTime=blockPeriod.optString("periodEndTime");
            JSONObject rooms=object.optJSONObject("rooms");
            String title =rooms.optString("title");

            MyScheduleListModel scheduleListModel=new MyScheduleListModel();
            scheduleListModel.setDay(day);
            scheduleListModel.setAttendanceTaken(takeAttendance);
            scheduleListModel.setPeriodStartTime(periodStartTime);
            scheduleListModel.setPeriodEndTime(periodEndTime);
            scheduleListModel.setRoom(title);
            scheduleListModel.setPeriodTitle(periodShortName);
            variablelist.add(scheduleListModel);


        }

        VariableScheduleAdapter scheduleAdapter=new VariableScheduleAdapter(variablelist,ScheduleDetailsActivity.this);
        binding.rvItem.setAdapter(scheduleAdapter);
    }
}