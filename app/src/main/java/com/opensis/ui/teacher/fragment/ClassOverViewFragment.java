package com.opensis.ui.teacher.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opensis.R;
import com.opensis.others.utility.Util;


public class ClassOverViewFragment extends Fragment {
    String courseTitle,courseName,courseSubject,calendartitle,creditHours,seats,attendanceCategory,courseWeighted,useStandards,affectsClassRank,affectsHonorRoll,onlineClassRoom;
    View view;
    String standardGradeScaleName,availableSeat,onlineClassroomUrl,onlineClassroomPassword;
    String gradescale,durationStartDate,durationEndDate,scheduleType,meetingDays,attendanceTaken,roomNo,period,quarters;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_class_over_view, container, false);
        initView();
        return view;
    }

    private void initView(){
        courseTitle=getArguments().getString("courseTitle");
        courseName=getArguments().getString("courseName");
        courseSubject=getArguments().getString("courseSubject");
        calendartitle=getArguments().getString("calendartitle");
        creditHours=getArguments().getString("creditHours");

        seats=getArguments().getString("seats");
        attendanceCategory=getArguments().getString("attendanceCategory");
        courseWeighted=getArguments().getString("courseWeighted");
        useStandards=getArguments().getString("useStandards");
        affectsClassRank=getArguments().getString("affectsClassRank");
        affectsHonorRoll=getArguments().getString("affectsHonorRoll");
        onlineClassRoom=getArguments().getString("onlineClassRoom");

        standardGradeScaleName=getArguments().getString("standardGradeScaleName");
        availableSeat=getArguments().getString("availableSeat");
        onlineClassroomUrl=getArguments().getString("onlineClassroomUrl");
        onlineClassroomPassword=getArguments().getString("onlineClassroomPassword");

        gradescale=getArguments().getString("gradescale");
        durationStartDate= Util.changeAnyDateFormat(getArguments().getString("durationStartDate"),"yyyy-MM-dd","MMM dd,yyyy");
        durationEndDate=Util.changeAnyDateFormat(getArguments().getString("durationEndDate"),"yyyy-MM-dd","MMM dd,yyyy");
        scheduleType=getArguments().getString("scheduleType");
        meetingDays=getArguments().getString("meetingDays");
        attendanceTaken=getArguments().getString("attendanceTaken");
        roomNo=getArguments().getString("roomNo");
        period=getArguments().getString("period");
        quarters=getArguments().getString("quarters");

        TextView tvCourse=(TextView)view.findViewById(R.id.tvCourse);
        tvCourse.setText(courseTitle);

        TextView tvCourseSectionName=(TextView)view.findViewById(R.id.tvCourseSectionName);
        tvCourseSectionName.setText(courseName);

        TextView tvSubject=(TextView)view.findViewById(R.id.tvSubject);
        tvSubject.setText(courseSubject);

        TextView tvCalendar=(TextView)view.findViewById(R.id.tvCalendar);
        tvCalendar.setText(calendartitle);

        TextView tvCredtHours=(TextView)view.findViewById(R.id.tvCredtHours);
        tvCredtHours.setText(creditHours);

        TextView tvSeats=(TextView)view.findViewById(R.id.tvSeats);
        tvSeats.setText(seats);

        TextView tvAttenDanceCategory=(TextView)view.findViewById(R.id.tvAttenDanceCategory);
        tvAttenDanceCategory.setText(attendanceCategory);

        TextView tvAvailableSeat=(TextView)view.findViewById(R.id.tvAvailableSeat);
        tvAvailableSeat.setText(availableSeat+" seats Available");

        TextView tvMainStandardGradeScale=(TextView)view.findViewById(R.id.tvMainStandardGradeScale);
        tvMainStandardGradeScale.setText(standardGradeScaleName);

        TextView tvOnlineUrl=(TextView)view.findViewById(R.id.tvOnlineUrl);
        tvOnlineUrl.setText(onlineClassroomUrl);

        TextView tvOnlinePassword=(TextView)view.findViewById(R.id.tvOnlinePassword);
        tvOnlinePassword.setText(onlineClassroomPassword);

        TextView tvQuater=(TextView)view.findViewById(R.id.tvQuater);
        tvQuater.setText(quarters);

        TextView tvQuaterPeriod=(TextView)view.findViewById(R.id.tvQuaterPeriod);
        tvQuaterPeriod.setText(durationStartDate+" to "+durationEndDate);

        TextView tvRoom=(TextView)view.findViewById(R.id.tvRoom);
        tvRoom.setText(roomNo);

        TextView tvPeriod=(TextView)view.findViewById(R.id.tvPeriod);
        tvPeriod.setText(period);

        TextView tvScheduleType=(TextView)view.findViewById(R.id.tvScheduleType);
        tvScheduleType.setText(scheduleType);

        LinearLayout lnSchedule=(LinearLayout)view.findViewById(R.id.lnSchedule);
        LinearLayout lnWeekDays=(LinearLayout)view.findViewById(R.id.lnWeekDays);
        LinearLayout lnSun=(LinearLayout)view.findViewById(R.id.lnSun);
        LinearLayout lnMon=(LinearLayout)view.findViewById(R.id.lnMon);
        LinearLayout lnTus=(LinearLayout)view.findViewById(R.id.lnTus);
        LinearLayout lnWed=(LinearLayout)view.findViewById(R.id.lnWed);
        LinearLayout lnThu=(LinearLayout)view.findViewById(R.id.lnThu);
        LinearLayout lnFri=(LinearLayout)view.findViewById(R.id.lnFri);
        LinearLayout lnSat=(LinearLayout)view.findViewById(R.id.lnSat);

        TextView tvSun=(TextView)view.findViewById(R.id.tvSun);
        TextView tvMon=(TextView)view.findViewById(R.id.tvMon);
        TextView tvTus=(TextView)view.findViewById(R.id.tvTus);
        TextView tvWed=(TextView)view.findViewById(R.id.tvWed);
        TextView tvThu=(TextView)view.findViewById(R.id.tvThu);
        TextView tvFri=(TextView)view.findViewById(R.id.tvFri);
        TextView tvSat=(TextView)view.findViewById(R.id.tvSat);

        if (meetingDays.equals("-")){
            lnSchedule.setVisibility(View.VISIBLE);
            lnWeekDays.setVisibility(View.GONE);
        }else {
            lnSchedule.setVisibility(View.GONE);
            lnWeekDays.setVisibility(View.VISIBLE);

            if (meetingDays.contains("Monday")){
                lnMon.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
                tvMon.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.white));
            }
            if (meetingDays.contains("Tuesday")){
                lnTus.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
                tvTus.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.white));
            }

            if (meetingDays.contains("Wednesday")){
                lnWed.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
                tvWed.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.white));
            }

            if (meetingDays.contains("Thursday")){
                lnThu.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
                tvThu.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.white));
            }

            if (meetingDays.contains("Friday")){
                lnFri.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
                tvFri.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.white));
            }

            if (meetingDays.contains("Saturday")){
                lnSat.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
                tvSat.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.white));
            }

            if (meetingDays.contains("Sunday")){
                lnSun.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.text_color));
                tvSun.setTextColor(ContextCompat.getColorStateList(getContext(),R.color.white));
            }

        }
        LinearLayout lnAttendanceEnable=(LinearLayout)view.findViewById(R.id.lnAttendanceEnable);
        if (attendanceTaken.equals("1")){
            lnAttendanceEnable.setVisibility(View.VISIBLE);
        }else {
            lnAttendanceEnable.setVisibility(View.GONE);
        }


        ImageView imgCourseWeighted=(ImageView)view.findViewById(R.id.imgCourseWeighted);
        TextView tvWeightedCourse=(TextView)view.findViewById(R.id.tvWeightedCourse);
        if (courseWeighted.equals("1")){
            imgCourseWeighted.setImageResource(R.drawable.check_black_48dp);
            imgCourseWeighted.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            tvWeightedCourse.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            imgCourseWeighted.setImageResource(R.drawable.close_black_48dp);
            imgCourseWeighted.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            tvWeightedCourse.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        ImageView imgUseStandard=(ImageView)view.findViewById(R.id.imgUseStandard);
        TextView tvUseStandard=(TextView)view.findViewById(R.id.tvUseStandard);
        if (useStandards.equals("1")){
            imgUseStandard.setImageResource(R.drawable.check_black_48dp);
            imgUseStandard.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            tvUseStandard.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            imgUseStandard.setImageResource(R.drawable.close_black_48dp);
            imgUseStandard.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            tvUseStandard.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        ImageView imgAffectClassRank=(ImageView)view.findViewById(R.id.imgAffectClassRank);
        TextView tvAffectClassRank=(TextView)view.findViewById(R.id.tvAffectClassRank);
        if (affectsClassRank.equals("1")){
            imgAffectClassRank.setImageResource(R.drawable.check_black_48dp);
            imgAffectClassRank.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            tvAffectClassRank.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            imgAffectClassRank.setImageResource(R.drawable.close_black_48dp);
            imgAffectClassRank.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            tvAffectClassRank.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        ImageView imgAffectHonorRoll=(ImageView)view.findViewById(R.id.imgAffectHonorRoll);
        TextView tvAffectHonorRoll=(TextView)view.findViewById(R.id.tvAffectHonorRoll);
        if (affectsHonorRoll.equals("1")){
            imgAffectHonorRoll.setImageResource(R.drawable.check_black_48dp);
            imgAffectHonorRoll.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            tvAffectHonorRoll.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            imgAffectHonorRoll.setImageResource(R.drawable.close_black_48dp);
            imgAffectHonorRoll.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            tvAffectHonorRoll.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }

        ImageView imgOnlineClass=(ImageView)view.findViewById(R.id.imgOnlineClass);
        TextView tvOnlineClass=(TextView)view.findViewById(R.id.tvOnlineClass);
        if (onlineClassRoom.equals("1")){
            imgOnlineClass.setImageResource(R.drawable.check_black_48dp);
            imgOnlineClass.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.black));
            tvOnlineClass.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.black));
        }else {
            imgOnlineClass.setImageResource(R.drawable.close_black_48dp);
            imgOnlineClass.setImageTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
            tvOnlineClass.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.grey));
        }



    }
}