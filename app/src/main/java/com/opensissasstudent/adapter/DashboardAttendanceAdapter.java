package com.opensissasstudent.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.AbsentModel;
import com.opensissasstudent.model.DashboardAttendanceModel;
import com.opensissasstudent.others.utility.Util;
import com.opensissasstudent.ui.student.activity.AbsentSummaryDetailsActivity;

import java.util.ArrayList;


public class DashboardAttendanceAdapter extends RecyclerView.Adapter<DashboardAttendanceAdapter.MyViewHolder> {
    ArrayList<DashboardAttendanceModel> itemList = new ArrayList<>();
    Context context;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_attendance_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {



            myViewHolder.tvStatus.setText(itemList.get(i).getStatus());


        if (itemList.get(i).getStatus().equalsIgnoreCase("P")){
            myViewHolder.llStatus.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.green));

        }else if (itemList.get(i).getStatus().equalsIgnoreCase("A")){
            myViewHolder.llStatus.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.missing_attendance));

        }else if (itemList.get(i).getStatus().equalsIgnoreCase("H")){
            myViewHolder.llStatus.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color3));

        }else {
            myViewHolder.llStatus.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey));

        }




    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       LinearLayout llStatus;
       TextView tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);


            llStatus=(LinearLayout)itemView.findViewById(R.id.llStatus);


        }
    }

    public DashboardAttendanceAdapter(ArrayList<DashboardAttendanceModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
}
