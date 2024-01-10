package com.opensissas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.MissingAttendanceModel;
import com.opensissas.others.utility.Util;
import com.opensissas.ui.teacher.activity.MissingClassAttendanceActivity;

import java.util.ArrayList;


public class MissiongAttendanceAdapter extends RecyclerView.Adapter<MissiongAttendanceAdapter.MyViewHolder> {
    ArrayList<MissingAttendanceModel> itemList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.missing_attendance_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvCourseName.setText(itemList.get(i).getCourseSectionName());
        myViewHolder.tvCourseSection.setText(itemList.get(i).getCourseSection());
        myViewHolder.tvDate.setText(Util.changeAnyDateFormat(itemList.get(i).getDate(),"yyyy-MM-dd","dd MMM,yyyy"));
        myViewHolder.tvGrade.setText(itemList.get(i).getGrade());
        myViewHolder.tvPeriod.setText(itemList.get(i).getPeriod());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MissingClassAttendanceActivity.class);
                intent.putExtra("courseSectionID",itemList.get(i).getCourseSectionID());
                intent.putExtra("courseID",itemList.get(i).getCourseID());
                intent.putExtra("periodID",itemList.get(i).getPeriodId());
                intent.putExtra("attendanceCatID",itemList.get(i).getAttendanceCategoryId());
                intent.putExtra("date",itemList.get(i).getDate());
                intent.putExtra("coursesection",itemList.get(i).getCourseSectionName());
                intent.putExtra("blockID",itemList.get(i).getBlockId());
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
        TextView tvCourseName,tvDate,tvCourseSection,tvGrade,tvPeriod,tvTakeAttendance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTakeAttendance=(TextView) itemView.findViewById(R.id.tvTakeAttendance);
            tvCourseName=(TextView) itemView.findViewById(R.id.tvCourseName);
            tvDate=(TextView) itemView.findViewById(R.id.tvDate);
            tvCourseSection=(TextView) itemView.findViewById(R.id.tvCourseSection);
            tvGrade=(TextView) itemView.findViewById(R.id.tvGrade);
            tvPeriod=(TextView) itemView.findViewById(R.id.tvPeriod);


        }
    }

    public MissiongAttendanceAdapter(ArrayList<MissingAttendanceModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
