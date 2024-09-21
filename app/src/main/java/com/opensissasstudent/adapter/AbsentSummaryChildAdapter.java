package com.opensissasstudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opensissasstudent.R;
import com.opensissasstudent.model.AbsentSummaryChildModel;
import com.opensissasstudent.model.CourseVeriableScheduleModel;

import java.util.ArrayList;


public class AbsentSummaryChildAdapter extends RecyclerView.Adapter<AbsentSummaryChildAdapter.MyViewHolder> {
    ArrayList<AbsentSummaryChildModel> itemList = new ArrayList<>();
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.absent_child_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        myViewHolder.tvStatus.setText(itemList.get(i).getAttendanceShortName());
        myViewHolder.tvPeriod.setText(itemList.get(i).getPeriodTitle());


        myViewHolder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvPeriod,tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus=(TextView)itemView.findViewById(R.id.tvStatus);
            tvPeriod=(TextView)itemView.findViewById(R.id.tvPeriod);







        }
    }

    public AbsentSummaryChildAdapter(ArrayList<AbsentSummaryChildModel> itemList) {
        this.itemList = itemList;

    }
}
