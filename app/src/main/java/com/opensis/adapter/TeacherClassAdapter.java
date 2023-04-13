package com.opensis.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensis.R;
import com.opensis.model.TeacherClassesModel;
import com.opensis.ui.teacher.activity.ClassDetailsActivity;

import java.util.ArrayList;


public class TeacherClassAdapter extends RecyclerView.Adapter<TeacherClassAdapter.MyViewHolder> {
    ArrayList<TeacherClassesModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.classes_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvClassName.setText(itemList.get(i).getClassName());
        myViewHolder.tvSubject.setText(itemList.get(i).getSubject());
        myViewHolder.tvClassTime.setText(itemList.get(i).getTime());
        myViewHolder.tvRoom.setText(itemList.get(i).getRoomNo());
        myViewHolder.tvPeriod.setText(itemList.get(i).getPeriod());
        myViewHolder.tvGrade.setText(itemList.get(i).getGrade());
        if (itemList.get(i).isAttendanceTaken()){
            myViewHolder.imgAttendance.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.imgAttendance.setVisibility(View.GONE);
        }

        if (itemList.get(i).isMonday()){
            myViewHolder.tvMon.setTextColor(Color.parseColor("#094990"));
        }else {
            myViewHolder.tvMon.setTextColor(Color.parseColor("#CCCACA"));
        }

        if (itemList.get(i).isTuesDay()){
            myViewHolder.tvTus.setTextColor(Color.parseColor("#094990"));
        }else {
            myViewHolder.tvTus.setTextColor(Color.parseColor("#CCCACA"));
        }

        if (itemList.get(i).isWednesday()){
            myViewHolder.tvWed.setTextColor(Color.parseColor("#094990"));
        }else {
            myViewHolder.tvWed.setTextColor(Color.parseColor("#CCCACA"));
        }

        if (itemList.get(i).isThursDay()){
            myViewHolder.tvThu.setTextColor(Color.parseColor("#094990"));
        }else {
            myViewHolder.tvThu.setTextColor(Color.parseColor("#CCCACA"));
        }

        if (itemList.get(i).isFriday()){
            myViewHolder.tvFri.setTextColor(Color.parseColor("#094990"));
        }else {
            myViewHolder.tvFri.setTextColor(Color.parseColor("#CCCACA"));
        }

        if (itemList.get(i).isSaturday()){
            myViewHolder.tvSat.setTextColor(Color.parseColor("#094990"));
        }else {
            myViewHolder.tvSat.setTextColor(Color.parseColor("#CCCACA"));
        }
        if (itemList.get(i).isSunday()){
            myViewHolder.tvSun.setTextColor(Color.parseColor("#094990"));
        }else {
            myViewHolder.tvSun.setTextColor(Color.parseColor("#CCCACA"));
        }

        myViewHolder.tvScheduleType.setText(itemList.get(i).getScheduleType());
        if (itemList.get(i).isFixedSchedule()){
            myViewHolder.lnWeekDays.setVisibility(View.VISIBLE);
            myViewHolder.lnSheduleType.setVisibility(View.GONE);
        }else {
            myViewHolder.lnWeekDays.setVisibility(View.GONE);
            myViewHolder.lnSheduleType.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassTime,tvClassName,tvSubject,tvGrade,tvRoom,tvPeriod,tvScheduleType;
        ImageView imgAttendance;
        TextView tvSun,tvMon,tvTus,tvWed,tvThu,tvFri,tvSat;
        LinearLayout lnSheduleType,lnWeekDays;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAttendance=(ImageView)itemView.findViewById(R.id.imgAttendance);
            tvClassTime=(TextView)itemView.findViewById(R.id.tvClassTime);
            tvClassName=(TextView)itemView.findViewById(R.id.tvClassName);
            tvSubject=(TextView)itemView.findViewById(R.id.tvSubject);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);
            tvRoom=(TextView)itemView.findViewById(R.id.tvRoom);
            tvPeriod=(TextView)itemView.findViewById(R.id.tvPeriod);
            tvScheduleType=(TextView)itemView.findViewById(R.id.tvScheduleType);

            tvSun=(TextView)itemView.findViewById(R.id.tvSun);
            tvMon=(TextView)itemView.findViewById(R.id.tvMon);
            tvTus=(TextView)itemView.findViewById(R.id.tvTus);
            tvWed=(TextView)itemView.findViewById(R.id.tvWed);
            tvThu=(TextView)itemView.findViewById(R.id.tvThu);
            tvFri=(TextView)itemView.findViewById(R.id.tvFri);
            tvSat=(TextView)itemView.findViewById(R.id.tvSat);


            lnSheduleType=(LinearLayout)itemView.findViewById(R.id.lnSheduleType);
            lnWeekDays=(LinearLayout)itemView.findViewById(R.id.lnWeekDays);



        }
    }

    public TeacherClassAdapter(ArrayList<TeacherClassesModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
