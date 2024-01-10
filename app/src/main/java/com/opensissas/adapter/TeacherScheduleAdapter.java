package com.opensissas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissas.R;
import com.opensissas.model.ScheduleModel;
import com.opensissas.others.utility.Util;

import java.util.ArrayList;


public class TeacherScheduleAdapter extends RecyclerView.Adapter<TeacherScheduleAdapter.MyViewHolder> {
    ArrayList<ScheduleModel> itemList = new ArrayList<>();
    Context context;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_schedule_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

       myViewHolder.tvPeriod.setText(itemList.get(i).getPeriod());
        myViewHolder.tvGrade.setText("Grade :- "+ Util.getFreshValue(itemList.get(i).getGrade(),"-"));
        myViewHolder.tvSubject.setText(itemList.get(i).getSubject());
        myViewHolder.tvTiming.setText(itemList.get(i).getTiming());








    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPeriod,tvTiming,tvSubject,tvGrade;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPeriod=(TextView)itemView.findViewById(R.id.tvPeriod);
            tvTiming=(TextView)itemView.findViewById(R.id.tvTiming);
            tvSubject=(TextView)itemView.findViewById(R.id.tvSubject);
            tvGrade=(TextView)itemView.findViewById(R.id.tvGrade);

        }
    }

    public TeacherScheduleAdapter(ArrayList<ScheduleModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
