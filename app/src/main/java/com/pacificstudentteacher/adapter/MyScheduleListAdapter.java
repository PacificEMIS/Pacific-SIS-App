package com.pacificstudentteacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificstudentteacher.R;
import com.pacificstudentteacher.model.MyScheduleListModel;
import com.pacificstudentteacher.ui.student.activity.ScheduleDetailsActivity;

import java.util.ArrayList;


public class MyScheduleListAdapter extends RecyclerView.Adapter<MyScheduleListAdapter.MyViewHolder> {
    ArrayList<MyScheduleListModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_list_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
       myViewHolder.tvCourseTitle.setText(itemList.get(i).getCourseTitle());
        myViewHolder.tvCOurseSectionName.setText(itemList.get(i).getCourseSectionName());

        if (itemList.get(i).getDayOfWeek().contains("Monday")) {
            myViewHolder.lnMon.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
            myViewHolder.tvMon.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        }
        if (itemList.get(i).getDayOfWeek().contains("Tuesday")) {
            myViewHolder.lnTus.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
            myViewHolder.tvTus.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        }

        if (itemList.get(i).getDayOfWeek().contains("Wednesday")) {
            myViewHolder.lnWed.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
            myViewHolder.tvWed.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        }

        if (itemList.get(i).getDayOfWeek().contains("Thursday")) {
            myViewHolder.lnThu.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
            myViewHolder.tvThu.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        }

        if (itemList.get(i).getDayOfWeek().contains("Friday")) {
            myViewHolder.lnFri.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
            myViewHolder.tvFri.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        }

        if (itemList.get(i).getDayOfWeek().contains("Saturday")) {
            myViewHolder.lnSat.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
            myViewHolder. tvSat.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        }

        if (itemList.get(i).getDayOfWeek().contains("Sunday")) {
            myViewHolder. lnSun.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.text_color));
            myViewHolder. tvSun.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        }
        myViewHolder.setIsRecyclable(false);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ScheduleDetailsActivity.class);
                intent.putExtra("courseTitle",itemList.get(i).getCourseTitle());
                intent.putExtra("courseSection",itemList.get(i).getCourseSectionName());
                intent.putExtra("courseTeacher",itemList.get(i).getTeacher().replace("[","").replace("]",""));
                intent.putExtra("markingPeriod",itemList.get(i).getQtr());
                intent.putExtra("enrollDate",itemList.get(i).getEffectiveStartDate());
                intent.putExtra("dropdate",itemList.get(i).getEffectiveDropDate());
                intent.putExtra("scheduletype",itemList.get(i).getScheduleType());
                intent.putExtra("period",itemList.get(i).getPeriodTitle());
                intent.putExtra("room",itemList.get(i).getRoom());
                intent.putExtra("attendancetaken",itemList.get(i).isAttendanceTaken());
                intent.putExtra("meetingdays",itemList.get(i).getDayOfWeek());
                intent.putExtra("variableSchedule",itemList.get(i).getVariableScheduleList());
                intent.putExtra("time",itemList.get(i).getPeriodStartTime()+" - "+itemList.get(i).getPeriodEndTime());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView tvCourseTitle,tvCOurseSectionName,tvSun,tvMon,tvTus,tvWed,tvThu,tvFri,tvSat;
        LinearLayout lnSun,lnMon,lnTus,lnWed,lnThu,lnFri,lnSat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCourseTitle=(TextView)itemView.findViewById(R.id.tvCourseTitle);
            tvCOurseSectionName=(TextView)itemView.findViewById(R.id.tvCOurseSectionName);

            tvSun=(TextView)itemView.findViewById(R.id.tvSun);
            tvMon=(TextView)itemView.findViewById(R.id.tvMon);
            tvTus=(TextView)itemView.findViewById(R.id.tvTus);
            tvWed=(TextView)itemView.findViewById(R.id.tvWed);
            tvThu=(TextView)itemView.findViewById(R.id.tvThu);
            tvFri=(TextView)itemView.findViewById(R.id.tvFri);
            tvSat=(TextView)itemView.findViewById(R.id.tvSat);
            lnSun=(LinearLayout)itemView.findViewById(R.id.lnSun);
            lnMon=(LinearLayout)itemView.findViewById(R.id.lnMon);
            lnTus=(LinearLayout)itemView.findViewById(R.id.lnTus);
            lnWed=(LinearLayout)itemView.findViewById(R.id.lnWed);
            lnThu=(LinearLayout)itemView.findViewById(R.id.lnThu);
            lnFri=(LinearLayout)itemView.findViewById(R.id.lnFri);
            lnSat=(LinearLayout)itemView.findViewById(R.id.lnSat);


        }
    }

    public MyScheduleListAdapter(ArrayList<MyScheduleListModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
