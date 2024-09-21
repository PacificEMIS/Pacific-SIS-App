package com.opensissasstudent.adapter;

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

import com.opensissasstudent.R;
import com.opensissasstudent.model.MyScheduleListModel;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.student.activity.ScheduleDetailsActivity;

import java.util.ArrayList;


public class VariableScheduleAdapter extends RecyclerView.Adapter<VariableScheduleAdapter.MyViewHolder> {
    ArrayList<MyScheduleListModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.variable_schedule_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvDay.setText(itemList.get(i).getDay());
        myViewHolder.tvPeriod.setText(itemList.get(i).getPeriodTitle());
        myViewHolder.tvTime.setText(Util.changeAnyDateFormat(itemList.get(i).getPeriodStartTime(),"yyyy-MM-dd","dd MMM,yy")+"-"+Util.changeAnyDateFormat(itemList.get(i).getPeriodEndTime(),"yyyy-MM-dd","dd MMM,yy"));
        myViewHolder.tvRoom.setText(itemList.get(i).getRoom());
        if (itemList.get(i).isAttendanceTaken()){
            myViewHolder.tvTakeAttendance.setText("Yes");
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView tvDay,tvTime,tvPeriod,tvRoom,tvTakeAttendance;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDay=(TextView)itemView.findViewById(R.id.tvDay);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvPeriod=(TextView)itemView.findViewById(R.id.tvPeriod);
            tvRoom=(TextView)itemView.findViewById(R.id.tvRoom);
            tvTakeAttendance=(TextView)itemView.findViewById(R.id.tvTakeAttendance);




        }
    }

    public VariableScheduleAdapter(ArrayList<MyScheduleListModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
